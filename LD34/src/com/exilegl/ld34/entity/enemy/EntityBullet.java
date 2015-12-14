package com.exilegl.ld34.entity.enemy;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.exilegl.ld34.ai.AiFollowLocation;
import com.exilegl.ld34.entity.Entity;
import com.exilegl.ld34.entity.EntityCoin;
import com.exilegl.ld34.entity.EntityDirection;
import com.exilegl.ld34.entity.EntityPlayer;
import com.exilegl.ld34.map.Map;
import com.exilegl.ld34.sound.Sound;
import com.exilegl.ld34.tile.Tile;

import box2dLight.ChainLight;
import box2dLight.ConeLight;
import box2dLight.PointLight;

public class EntityBullet extends EntityEnemy{

	//The bullet's light color
	private Color color;
	
	private float distance;
	
	private Vector2 offset;
	
	private float age;
	
	private boolean textured;
	
	private Texture texture;
	
	//Whether or not the bullet kills enemies. If not, it kills non enemies.
	private boolean killsEnemies;
	
	private float duration;
	
	private boolean ranged;
	
	public EntityBullet(Vector2 location, Color color, EntityDirection direction, float distance, boolean killsEnemies, float duration, boolean textured, boolean ranged, boolean moveY) {
		super(null, location, 10, 10, true);
		this.setColor(color);
		this.setDistance(distance);
		this.offset = new Vector2(0, 0);
		float y = (this.getLocation().y);
		if(ranged){
		Random r = new Random();
		if(r.nextBoolean()){
			y = (y + 64);
		}else{
			y = (y - 64);
		}
		}
		if(!moveY){
		if(direction == EntityDirection.LEFT){
		this.addAction(new AiFollowLocation(this, new Vector2(this.getLocation().x - this.getDistance() * 5, y), this.getSpeed(), false, false));
		}else{
			this.addAction(new AiFollowLocation(this, new Vector2(this.getLocation().x + this.getDistance() * 5, y), this.getSpeed(), false, false));
		}
		}else{
			if(direction == EntityDirection.LEFT){
				this.addAction(new AiFollowLocation(this, new Vector2(this.getLocation().x * 5, y + distance * 3), this.getSpeed(), false, false));
				}else{
					this.addAction(new AiFollowLocation(this, new Vector2(this.getLocation().x, y - distance * 3), this.getSpeed(), false, false));
				}
		}
		setAge(0);
		
		if(this.isTextured()){
			this.setTexture(new Texture(Gdx.files.internal("assets/texture/entity/redbullet.png")));
		}
		
		this.setKillsEnemies(killsEnemies);
		this.setDuration(duration);
		
		if(textured){
			this.setTexture(new Texture(Gdx.files.internal("assets/texture/misc/bullet.png")));
		}
		this.setTextured(textured);
		
		if(!killsEnemies){
			Sound.play(Sound.Enemy_Shoot, 0.5f);
		}
		
		this.ranged = ranged;
		if(ranged){
			this.setDistance(distance * 1.5f);
		}
	}

	@Override
	public void update() {
		setAge((getAge() + 1 * Gdx.graphics.getDeltaTime()));
		if(this.getLight() == null){
			this.setLight(new PointLight(this.getMap().getRay(), Map.RAYS, this.getColor(), this.getDistance() / 5, this.getLocation().x, this.getLocation().y));
		}
		
		this.flickerLight(20, (int) ((int) getDistance() * 1.5f), (int) getDistance());
		
		this.getLight().setPosition(this.getLocation().x + offset.x, this.getLocation().y + offset.y);
		this.setRectangle(new Rectangle(this.getLocation().x, this.getLocation().y, this.getLight().getDistance(), this.getLight().getDistance()));
		
		this.performAi();
		
		if(this.getAge() > this.getDuration()){
			this.kill();
		}
		
		for(Entity e : this.getMap().getEntities()){
			if(this.isKillsEnemies()){
				if(e instanceof EntityEnemy && !(e instanceof EntityPlayer) && !(e instanceof EntityBullet) && this.getRectangle().overlaps(e.getRectangle()) && !(e instanceof EntityDeathTile)){
					e.kill();
					this.kill();
					Sound.play(Sound.Kill_Enemy);
				}
			}else{
				try{
				if(!(e instanceof EntityEnemy) && !(e instanceof EntityCoin) && this.getRectangle().overlaps(e.getRectangle())){
					e.kill();
					this.kill();
				}
				}catch(NullPointerException n){
					
				}
			}
			if(e instanceof EntityPlayer && this.getRectangle().overlaps(e.getRectangle()) && !this.isKillsEnemies()){
				e.kill();
				this.kill();
			}
		}
		
		for(Tile t : this.getMap().getTiles()){
			if(t.getType().SOLID){
				Rectangle tileRect = new Rectangle(t.getLocation().x, t.getLocation().y, t.getSingleAnimation().getWidth(), t.getSingleAnimation().getHeight());
				Rectangle bulletRect = new Rectangle(this.getLocation().x, this.getLocation().y, this.getLight().getDistance(), this.getLight().getDistance());
				if(bulletRect.overlaps(tileRect)){
					this.kill();
				}
			}
		}
	}
	
	@Override
	public void draw(SpriteBatch batch){
		if(this.isTextured()){
			batch.draw(this.getTexture(), this.getLocation().x, this.getLocation().y);
		}
	}

	@Override
	public void create(Map map) {
		this.setMap(map);
		if(isKillsEnemies()){
			Vector3 m = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			this.getMap().getCamera().unproject(m);
				((AiFollowLocation) this.getActions().get(0)).getDestination().set(m.x, m.y);
		}
	}

	public boolean isTextured() {
		return textured;
	}

	public void setTextured(boolean textured) {
		this.textured = textured;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public float getAge() {
		return age;
	}

	public void setAge(float age) {
		this.age = age;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public float getDuration() {
		return duration;
	}

	public void setDuration(float duration) {
		this.duration = duration;
	}

	public boolean isKillsEnemies() {
		return killsEnemies;
	}

	public void setKillsEnemies(boolean killsEnemies) {
		this.killsEnemies = killsEnemies;
	}
	
	public Vector2 getOffset(){
		return this.offset;
	}

	
	
}