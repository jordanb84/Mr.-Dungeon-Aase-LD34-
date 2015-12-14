package com.exilegl.ld34.entity;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.exilegl.ld34.animation.Animation;
import com.exilegl.ld34.animation.EntityAnimation;
import com.exilegl.ld34.map.Map;

import box2dLight.PointLight;

public abstract class Entity {
	
	//Used for the direction based animation
	private EntityAnimation animation;
	
	//For entities which do not have directional animations
	private Animation singleAnimation;
	
	//The entity's location
	private Vector2 location;
	
	//The entity's basic traits
	private float health;
	private float speed;
	
	//Whether or not the entity collides with tiles
	private boolean collides;
	
	//The entity's direction
	private EntityDirection direction;

	//The entity's rectangle for collision
	private Rectangle rectangle;
	
	//The entity's containing map
	private Map map;
	
	private PointLight light;
	
	//The entity's Box2D body, shape, etc
	private BodyDef bodyDef;
	private Body body;
	private CircleShape circleShape;
	private PolygonShape polygonShape;
	private FixtureDef fixtureDef;
	private Fixture fixture;
	
	private boolean dead;
	
	public Entity(EntityAnimation animation, Vector2 location, float speed, float health, boolean collides){
		this.setAnimation(animation);
		this.setLocation(location);
		this.setSpeed(speed);
		this.setHealth(health);
		this.setCollides(collides);
		this.setDirection(EntityDirection.DOWN);
	}
	
	public void draw(SpriteBatch batch){
		if(this.animation != null){
			this.animation.draw(batch, this.direction, this.location.x, this.location.y);
		}else{
			this.singleAnimation.draw(batch, new Vector2(this.location.x, this.location.y));
		}
	}
	
	public abstract void update();
	
	public abstract void create(Map map);
	
	public void kill(){
		this.setDead(true);
		if(this.getLight() != null){
			this.getLight().setActive(false);
		}
		if(this.getBody() != null){
			this.getBody().setActive(false);
		}
	}
	
	/**
	 * Updates the entity's rectangle
	 */
	public void updateRectangle(){
		if(this.rectangle == null){
			this.rectangle = new Rectangle();
		}
		int width;
		int height;
		if(this.animation != null){
			width = (this.animation.getDirections()[this.direction.ID].getWidth());
			height = (this.animation.getDirections()[this.direction.ID].getHeight());
		}else{
			width = (this.singleAnimation.getWidth());
			height = (this.singleAnimation.getHeight());
		}
		this.rectangle.set(this.location.x, this.location.y, width, height);
	}
	
	/**
	 * Flickers the light
	 * @param amount Lower means more flicker
	 */
	public void flickerLight(int amount){
		try{
		Random r = new Random();
		int fAmount = (r.nextInt((int) this.getLight().getDistance() / amount));
		if(r.nextBoolean()){
			this.getLight().setDistance(this.getLight().getDistance() + fAmount);
		}else{
			this.getLight().setDistance(this.getLight().getDistance() - fAmount);
		}
		}catch(IllegalArgumentException e){
			//e.printStackTrace();
		}
	}
	
	public void flickerLight(int amount, int maxDistance, int minDistance){
		try{
		Random r = new Random();
		int fAmount = (r.nextInt((int) this.getLight().getDistance() / amount));
		if(r.nextBoolean()){
			this.getLight().setDistance(this.getLight().getDistance() + fAmount);
			if(this.getLight().getDistance() > maxDistance){
				this.getLight().setDistance(this.getLight().getDistance() - fAmount);
			}
		}else{
			this.getLight().setDistance(this.getLight().getDistance() - fAmount);
			if(this.getLight().getDistance() < minDistance){
				this.getLight().setDistance(this.getLight().getDistance() + fAmount);
			}
		}
		}catch(IllegalArgumentException e){
			//e.printStackTrace();
		}
		
	}
	
	public void updatePhysics(){
		this.updateRectangle();
		int width;
		int height;
		if(this.animation != null){
			width = (this.animation.getDirections()[this.direction.ID].getWidth());
			height = (this.animation.getDirections()[this.direction.ID].getHeight());
		}else{
			width = (this.singleAnimation.getWidth());
			height = (this.singleAnimation.getHeight());
		}
		if(this.getLight() != null){
			this.getLight().setPosition(new Vector2(this.getRectangle().x + width / 2 , this.getRectangle().y + height / 2));
			if(this.getBody() != null){
				this.getBody().getPosition().set(new Vector2(this.getBody().getPosition().x, this.getBody().getPosition().y));
			}
		}
		
		if(this.getBody() != null){
			this.getLocation().set(new Vector2(this.getBody().getPosition().x - width / 2, this.getBody().getPosition().y - height / 2));
		}
	}
	
	public void move(EntityDirection direction, boolean updateAnimation, boolean moveBody){
		this.direction = direction;
		float delta = (Gdx.graphics.getDeltaTime());
		float moveSpeed;
		if(delta > 0){
			moveSpeed = (this.speed + 1 * Gdx.graphics.getDeltaTime());
		}else{
			System.out.println("FAIL");
			moveSpeed = 0;
		}
		Vector2 force = new Vector2();
		
		switch(direction){
		case UP:
			force.set(0, moveSpeed);
			break;
		case DOWN:
			force.set(0, -moveSpeed);
			break;
		case LEFT:
			force.set(-moveSpeed, 0);
			break;
		case RIGHT:
			force.set(moveSpeed, 0);
			break;
		default:
			force.set(0, 0);
			break;
		}
		
		//this.getBody().applyForce(force, this.getLocation(), true);
		this.location.add(force);
		
		if(updateAnimation && !(this.getAnimation() == null)){
			this.getAnimation().update(this.getDirection());
		}
		
		if(moveBody){
			this.getBody().applyForce(new Vector2(force.x * 10000, force.y * 10000), this.getLocation(), false);
		}
	}
	
	public void move(Vector2 force, boolean moveBody){
		float delta = (Gdx.graphics.getDeltaTime());
		float moveSpeed;
		if(delta > 0){
			moveSpeed = (this.speed + 1 * Gdx.graphics.getDeltaTime());
		}else{
			System.out.println("FAIL @ MOVEMENT, DELTA LESS THAN 0 (force)");
			moveSpeed = 0;
		}
		
		this.location.add(force);
		
		if(moveBody){
		this.getBody().applyForce(new Vector2(force.x * 10000, force.y * 10000), this.getLocation(), false);
		}
	}

	public EntityAnimation getAnimation() {
		return animation;
	}

	public void setAnimation(EntityAnimation animation) {
		this.animation = animation;
	}

	public Animation getSingleAnimation() {
		return singleAnimation;
	}

	public void setSingleAnimation(Animation singleAnimation) {
		this.singleAnimation = singleAnimation;
	}

	public Vector2 getLocation() {
		return location;
	}

	public void setLocation(Vector2 location) {
		this.location = location;
	}

	public float getHealth() {
		return health;
	}

	public void setHealth(float health) {
		this.health = health;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public boolean isCollides() {
		return collides;
	}

	public void setCollides(boolean collides) {
		this.collides = collides;
	}

	public EntityDirection getDirection() {
		return direction;
	}

	public void setDirection(EntityDirection direction) {
		this.direction = direction;
	}

	public Rectangle getRectangle() {
		return rectangle;
	}

	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public PointLight getLight() {
		return light;
	}

	public void setLight(PointLight light) {
		this.light = light;
	}

	public BodyDef getBodyDef() {
		return bodyDef;
	}

	public void setBodyDef(BodyDef bodyDef) {
		this.bodyDef = bodyDef;
	}

	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}

	public CircleShape getCircleShape() {
		return circleShape;
	}

	public void setCircleShape(CircleShape circleShape) {
		this.circleShape = circleShape;
	}

	public PolygonShape getPolygonShape() {
		return polygonShape;
	}

	public void setPolygonShape(PolygonShape polygonShape) {
		this.polygonShape = polygonShape;
	}

	public FixtureDef getFixtureDef() {
		return fixtureDef;
	}

	public void setFixtureDef(FixtureDef fixtureDef) {
		this.fixtureDef = fixtureDef;
	}

	public Fixture getFixture() {
		return fixture;
	}

	public void setFixture(Fixture fixture) {
		this.fixture = fixture;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}
}