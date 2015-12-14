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
import com.exilegl.ld34.game.Launcher;
import com.exilegl.ld34.map.Map;

import box2dLight.PointLight;

public class EntityEvilTile extends EntityEnemy{

	private float elapsed;
	
	private float hp;
	
	private EnemyType enemy;
	
	public EntityEvilTile(Vector2 location, EnemyType enemy) {
		super(null, location, 2, 5, true);
		//this.setSpeed(0.1f);
		this.enemy = enemy;
		
		this.setSingleAnimation(new Animation());
		this.getSingleAnimation().addFrame(new Frame(new TextureRegion(new Texture(Gdx.files.internal("assets/texture/tile/furnaceOrange.png"))), 1));
		
		this.addAction(new AiGuardArea(this, new Vector2(this.getLocation()), new Vector2(this.getLocation().x + Launcher.DEFAULT_WIDTH / 3, this.getLocation().y), false));
	
		this.hp = 2;
	}

	@Override
	public void update() {
		
		elapsed = (elapsed + 1 * Gdx.graphics.getDeltaTime());
		if(elapsed > 2){
			this.getMap().spawnEntity(new EntityBullet(new Vector2(this.getLocation().x, this.getLocation().y), Color.CYAN, this.getDirection(), 250, false, 1f, false, false, false));
			elapsed = 0;
		}
		
		if(this.getLight() == null){
			this.setLight(new PointLight(this.getMap().getRay(), Map.RAYS, Color.ROYAL, 64, this.getLocation().x, this.getLocation().y));
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
		this.getPolygonShape().setAsBox(this.getSingleAnimation().getWidth() / 2, this.getSingleAnimation().getHeight() / 2);
		this.getBody().createFixture(this.getPolygonShape(), 0.0f);
		this.getPolygonShape().dispose();
	}
	
	@Override
	public void kill(){
		//this.spawnCoin();
		this.hp--;
		if(hp < 1){
		if(!this.isDead()){
		switch(enemy){
		case Zombie:
			this.getMap().spawnEntity(new EntityZombie(new Vector2(this.getLocation().x, this.getLocation().y)));
			break;
		case Creep:
			this.getMap().spawnEntity(new EntityCreep(new Vector2(this.getLocation().x, this.getLocation().y)));
			break;
		case OddZombie:
			this.getMap().spawnEntity(new EntityOddZombie(new Vector2(this.getLocation().x, this.getLocation().y)));
			break;
		case EvilTile:
			this.getMap().spawnEntity(new EntityEvilTile(new Vector2(this.getLocation().x, this.getLocation().y), EnemyType.EvilTile));
			break;
		}
		//this.getMap().spawnEntity(new EntityZombie(new Vector2(this.getLocation().x, this.getLocation().y)));
		}
		this.setDead(true);
		if(this.getLight() != null){
			this.getLight().setActive(false);
		}
		if(this.getBody() != null){
			this.getBody().setActive(false);
		}
		}
	}

}
