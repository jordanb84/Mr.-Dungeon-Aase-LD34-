package com.exilegl.ld34.entity.enemy;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.exilegl.ld34.ai.AiGuardArea;
import com.exilegl.ld34.animation.Animation;
import com.exilegl.ld34.animation.EntityAnimation;
import com.exilegl.ld34.animation.Frame;
import com.exilegl.ld34.entity.Entity;
import com.exilegl.ld34.entity.EntityDirection;
import com.exilegl.ld34.entity.EntityPlayer;
import com.exilegl.ld34.game.Launcher;
import com.exilegl.ld34.map.Map;
import com.exilegl.ld34.sound.Sound;

import box2dLight.PointLight;

public class EntityPurpleTile extends EntityEnemy{

	private float elapsed;
	
	private float hp;
	
	public EntityPurpleTile(Vector2 location) {
		super(null, location, 2, 5, true);
		//this.setSpeed(0.1f);
		
		this.setSingleAnimation(new Animation());
		this.getSingleAnimation().addFrame(new Frame(new TextureRegion(new Texture(Gdx.files.internal("assets/texture/tile/wallPurple.png"))), 1));
	
		this.hp = 2;
	}

	@Override
	public void update() {
		
		if(this.getLight() == null){
			this.setLight(new PointLight(this.getMap().getRay(), Map.RAYS, Color.PURPLE, 64, this.getLocation().x, this.getLocation().y));
		}
		
		this.updatePhysics();
		this.flickerLight(30, 200, 120);
		this.getBody().setSleepingAllowed(false);
		
		this.performAi();
		
		for(Entity e : this.getMap().getEntities()){
			if(e instanceof EntityPlayer && this.getRectangle().overlaps(e.getRectangle())){
				((EntityPlayer) e).trapPurple();
			}
		}
	}

	@Override
	public void create(Map map) {
		this.setMap(map);
		this.setBodyDef(new BodyDef());
		this.getBodyDef().type = BodyType.DynamicBody;
		this.getBodyDef().position.set(this.getLocation().x, this.getLocation().y);
		this.setBody(this.getMap().getWorld().createBody(this.getBodyDef()));
		this.setPolygonShape(new PolygonShape());
		this.getPolygonShape().setAsBox(this.getSingleAnimation().getWidth() / 2, this.getSingleAnimation().getHeight() / 2);
		this.getBody().createFixture(this.getPolygonShape(), 0.0f);
		this.getPolygonShape().dispose();
	}
	
	@Override
	public void kill(){
		//this.spawnCoin();
		this.hp--;
		this.setDead(true);
		if(this.getLight() != null){
			this.getLight().setActive(false);
		}
		if(this.getBody() != null){
			this.getBody().setActive(false);
		}
	}

}