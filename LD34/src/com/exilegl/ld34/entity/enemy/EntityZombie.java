package com.exilegl.ld34.entity.enemy;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.exilegl.ld34.ai.AiGuardArea;
import com.exilegl.ld34.animation.EntityAnimation;
import com.exilegl.ld34.entity.Entity;
import com.exilegl.ld34.entity.EntityDirection;
import com.exilegl.ld34.game.Launcher;
import com.exilegl.ld34.map.Map;

import box2dLight.PointLight;

public class EntityZombie extends EntityEnemy{

	private float elapsed;
	
	public EntityZombie(Vector2 location) {
		super(new EntityAnimation(new Texture(Gdx.files.internal("assets/texture/entity/zombieSheet.png")), .3f), location, 2, 5, true);
		//this.setSpeed(0.1f);
		
		this.getAnimation().addDirectionFrame(EntityDirection.LEFT, 0, 32, 32, 32);
		this.getAnimation().addDirectionFrame(EntityDirection.LEFT, 32, 32, 32, 32);
		this.getAnimation().addDirectionFrame(EntityDirection.RIGHT, 0, 0, 32, 32);
		this.getAnimation().addDirectionFrame(EntityDirection.RIGHT, 32, 0, 32, 32);
		this.getAnimation().addDirectionFrame(EntityDirection.UP, 0, 32, 32, 32);
		this.getAnimation().addDirectionFrame(EntityDirection.UP, 32, 32, 32, 32);
		this.getAnimation().addDirectionFrame(EntityDirection.DOWN, 0, 0, 32, 32);
		this.getAnimation().addDirectionFrame(EntityDirection.DOWN, 32, 0, 32, 32);
		
		this.addAction(new AiGuardArea(this, new Vector2(this.getLocation()), new Vector2(this.getLocation().x + Launcher.DEFAULT_WIDTH / 3, this.getLocation().y), false));
	}

	@Override
	public void update() {
		
		elapsed = (elapsed + 1 * Gdx.graphics.getDeltaTime());
		if(elapsed > 2){
			this.getMap().spawnEntity(new EntityBullet(new Vector2(this.getLocation().x, this.getLocation().y), Color.GREEN, this.getDirection(), 200, false, 1f, false, false, false));
			elapsed = 0;
		}
		
		if(this.getLight() == null){
			this.setLight(new PointLight(this.getMap().getRay(), Map.RAYS, Color.OLIVE, 64, this.getLocation().x, this.getLocation().y));
		}
		
		this.updatePhysics();
		this.flickerLight(30, 200, 120);
		this.getBody().setSleepingAllowed(false);
		
		this.performAi();
	}

	@Override
	public void create(Map map) {
		this.setMap(map);
		this.setBodyDef(new BodyDef());
		this.getBodyDef().type = BodyType.DynamicBody;
		this.getBodyDef().position.set(this.getLocation().x, this.getLocation().y);
		this.setBody(this.getMap().getWorld().createBody(this.getBodyDef()));
		this.setPolygonShape(new PolygonShape());
		this.getPolygonShape().setAsBox(this.getAnimation().getDirections()[this.getDirection().ID].getWidth() / 2, this.getAnimation().getDirections()[this.getDirection().ID].getHeight() / 2);
		this.getBody().createFixture(this.getPolygonShape(), 0.0f);
		this.getPolygonShape().dispose();
	}
	
	@Override
	public void kill(){
		this.spawnCoin();
		this.setDead(true);
		if(this.getLight() != null){
			this.getLight().setActive(false);
		}
		if(this.getBody() != null){
			this.getBody().setActive(false);
		}
		
	}

}
