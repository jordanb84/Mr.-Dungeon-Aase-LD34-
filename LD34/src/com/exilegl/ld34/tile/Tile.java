package com.exilegl.ld34.tile;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.exilegl.ld34.entity.Entity;
import com.exilegl.ld34.entity.EntityPlayer;
import com.exilegl.ld34.entity.enemy.EnemyType;
import com.exilegl.ld34.entity.enemy.EntityBloodyHuman;
import com.exilegl.ld34.entity.enemy.EntityBoss;
import com.exilegl.ld34.entity.enemy.EntityCreep;
import com.exilegl.ld34.entity.enemy.EntityDeathTile;
import com.exilegl.ld34.entity.enemy.EntityEvilTile;
import com.exilegl.ld34.entity.enemy.EntityFireGuard;
import com.exilegl.ld34.entity.enemy.EntityFlame;
import com.exilegl.ld34.entity.enemy.EntityOddZombie;
import com.exilegl.ld34.entity.enemy.EntityPurpleTile;
import com.exilegl.ld34.entity.enemy.EntityPushableTile;
import com.exilegl.ld34.entity.enemy.EntityShootingWall;
import com.exilegl.ld34.entity.enemy.EntitySlowMadman;
import com.exilegl.ld34.entity.enemy.EntitySnowmonster;
import com.exilegl.ld34.entity.enemy.EntityWaterBeast;
import com.exilegl.ld34.entity.enemy.EntityWaterGuard;
import com.exilegl.ld34.entity.enemy.EntityYZombie;
import com.exilegl.ld34.entity.enemy.EntityZombie;
import com.exilegl.ld34.map.Map;
import com.exilegl.ld34.text.Text;

import box2dLight.PointLight;

public class Tile extends Entity{

	private TileType type;
	
	private Text text;
	
	private boolean spawned;
	
	public Tile(TileType type, Vector2 location) {
		super(null, location, 0, 0, false);
		this.setType(type);
		this.setSingleAnimation(type.ANIMATION);
		
		if(this.getType().HAS_TEXT){
			text = new Text("assets/font/blow.ttf", 22);
		}
	}

	@Override
	public void update() {
		if(this.type.LIGHTED && this.getLight() == null){
			float width = (this.getSingleAnimation().getWidth());
			float height = (this.getSingleAnimation().getHeight());
			this.setLight(new PointLight(this.getMap().getRay(), Map.RAYS, this.getType().LIGHT_COLOR, this.getType().LIGHT_DISTANCE, this.getLocation().x + width / 2, this.getLocation().y + height / 2));
		}
		
		if(this.getType().FLICKER){
			this.flickerLight(20, 120, 100);
		}
		
		if(this.spawned == false){
		switch(this.getType().name()){
		case "SpawnerZombie":
			this.getMap().spawnEntity(new EntityZombie(new Vector2(this.getLocation())));
			this.spawned = true;
			break;
		case "SpawnerCreep":
			this.getMap().spawnEntity(new EntityCreep(new Vector2(this.getLocation())));
			this.spawned = true;
			break;
		case "SpawnerOddZombie":
			this.getMap().spawnEntity(new EntityOddZombie(new Vector2(this.getLocation())));
			this.spawned = true;
			break;
		case "SpawnerBloodyHuman":
			this.getMap().spawnEntity(new EntityBloodyHuman(new Vector2(this.getLocation())));
			this.spawned = true;
			break;
		case "SpawnerEvilTile_Zombie":
			this.getMap().spawnEntity(new EntityEvilTile(new Vector2(this.getLocation()), EnemyType.Zombie));
			this.spawned = true;
			break;
		case "SpawnerEvilTile_Creep":
			this.getMap().spawnEntity(new EntityEvilTile(new Vector2(this.getLocation()), EnemyType.Creep));
			this.spawned = true;
			break;
		case "SpawnerEvilTile_OddZombie":
			this.getMap().spawnEntity(new EntityEvilTile(new Vector2(this.getLocation()), EnemyType.OddZombie));
			this.spawned = true;
			break;
		case "SpawnerEvilTile_EvilTile":
			this.getMap().spawnEntity(new EntityEvilTile(new Vector2(this.getLocation()), EnemyType.EvilTile));
			this.spawned = true;
			break;
		case "SpawnerPushableTile":
			this.getMap().spawnEntity(new EntityPushableTile(new Vector2(this.getLocation())));
			this.spawned = true;
			break;
		case "SpawnerShootingWall":
			this.getMap().spawnEntity(new EntityShootingWall(new Vector2(this.getLocation())));
			this.spawned = true;
			break;
		case "SpawnerDeathTile":
			this.getMap().spawnEntity(new EntityDeathTile(new Vector2(this.getLocation())));
			this.spawned = true;
			break;
		case "SpawnerBoss":
			this.getMap().spawnEntity(new EntityBoss(new Vector2(this.getLocation()), this.getMap().getLevel()));
			this.spawned = true;
			break;
		case "SpawnerMadMan":
			this.getMap().spawnEntity(new EntitySlowMadman(new Vector2(this.getLocation())));
			this.spawned = true;
			break;
		case "SpawnerYZombie":
			this.getMap().spawnEntity(new EntityYZombie(new Vector2(this.getLocation())));
			this.spawned = true;
			break;
		case "SpawnerSnowmonster":
			this.getMap().spawnEntity(new EntitySnowmonster(new Vector2(this.getLocation())));
			this.spawned = true;
			break;
		case "SpawnerFlame":
			this.getMap().spawnEntity(new EntityFlame(new Vector2(this.getLocation())));
			this.spawned = true;
			break;
		case "SpawnerFireGuard":
			this.getMap().spawnEntity(new EntityFireGuard(new Vector2(this.getLocation())));
			this.spawned = true;
			break;
		case "SpawnerWaterGuard":
			this.getMap().spawnEntity(new EntityWaterGuard(new Vector2(this.getLocation())));
			this.spawned = true;
			break;
		case "SpawnerPurple":
			this.getMap().spawnEntity(new EntityPurpleTile(new Vector2(this.getLocation())));
			this.spawned = true;
			break;
		case "SpawnerWaterBeast":
			this.getMap().spawnEntity(new EntityWaterBeast(new Vector2(this.getLocation())));
			this.spawned = true;
			break;
			//SpawnerDeathTile
			//SpawnerBoss SpawnerMadMan SpawnerYZombie SpawnerWaterBeast
		}
		}
		
		if(this.getType().name().equals("FloorTrap")){
			this.updateRectangle();
			for(Entity e : this.getMap().getEntities()){
				if(e instanceof EntityPlayer && e.getRectangle().overlaps(this.getRectangle())){
					((EntityPlayer) e).trap();
				}
			}
		}
		
		if(this.getType().name().equals("Lava")){
			this.updateRectangle();
			for(Entity e : this.getMap().getEntities()){
				if(e instanceof EntityPlayer && e.getRectangle().overlaps(this.getRectangle())){
					((EntityPlayer) e).trapLava();
				}
			}
		}
		
		if(this.getType().name().equals("Purple")){
			this.updateRectangle();
			for(Entity e : this.getMap().getEntities()){
				if(e instanceof EntityPlayer && e.getRectangle().overlaps(this.getRectangle())){
					((EntityPlayer) e).trapPurple();
				}
			}
		}
		
		if(this.getType().GRAVITY){
			this.updatePhysics();
		}
		
		//System.out.println(TileType.values().length);
	}

	@Override
	public void draw(SpriteBatch batch){
		if(this.getAnimation() != null){
			this.getAnimation().draw(batch, this.getDirection(), this.getLocation().x, this.getLocation().y);
		}else{
			this.getSingleAnimation().draw(batch, new Vector2(this.getLocation().x, this.getLocation().y));
		}
		
		if(this.getType().HAS_TEXT){
			text.draw(batch, this.getType().TEXT, this.getLocation());
		}
	}
	
	public TileType getType() {
		return type;
	}

	public void setType(TileType type) {
		this.type = type;
	}

	@Override
	public void create(Map map) {
		this.setMap(map);
		if(this.type.SOLID){
		this.setBodyDef(new BodyDef());
		if(!this.getType().GRAVITY){
		this.getBodyDef().type = BodyType.StaticBody;
		}else{
			this.getBodyDef().type = BodyType.DynamicBody;
			//System.out.println("Has gravity " + this.getType().name());
		}
		if(this.getType().name() == "Purple"){
			this.getBodyDef().type = BodyType.DynamicBody;
			//System.out.println("D");
		}
		
		this.getBodyDef().position.set(this.getLocation().x + this.getSingleAnimation().getWidth() / 2, this.getLocation().y + this.getSingleAnimation().getHeight() / 2);
		this.setBody(this.getMap().getWorld().createBody(this.getBodyDef()));
		this.setPolygonShape(new PolygonShape());
		this.getPolygonShape().setAsBox(this.getSingleAnimation().getWidth() / 2, this.getSingleAnimation().getHeight() / 2);
		this.getBody().createFixture(this.getPolygonShape(), 0.0f);
		this.getPolygonShape().dispose();
		}
	}

}
