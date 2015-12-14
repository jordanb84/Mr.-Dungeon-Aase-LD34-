package com.exilegl.ld34.map;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.exilegl.ld34.entity.Entity;
import com.exilegl.ld34.entity.EntityPlayer;
import com.exilegl.ld34.game.Launcher;
import com.exilegl.ld34.state.StateManager;
import com.exilegl.ld34.tile.Tile;
import com.exilegl.ld34.tile.TileType;

import box2dLight.RayHandler;

public class Map {

	private List<Entity> entities;
	private List<Entity> entityQueue;
	private List<Tile> tiles;
	
	private OrthographicCamera camera;
	
	private World world;
	private RayHandler ray;
	private Box2DDebugRenderer debug;
	
	private StateManager manager;
	
	public static final int RAYS = 100;
	
	private Vector2 offset;
	private Vector2 diffOffset;
	
	private int level;
	
	public Map(List<Entity> entities, List<Tile> tiles, OrthographicCamera camera, StateManager manager, int level){
		offset = new Vector2(0, 0);
		this.diffOffset = new Vector2(0, 0);
		this.setEntities(entities);
		this.setTiles(tiles);
		this.entityQueue = new ArrayList<Entity>();
		this.setCamera(camera);
		this.setLevel(level);
		
		ray = new RayHandler(this.getWorld());
		ray.useDiffuseLight(true);
		ray.setShadows(true);
		ray.setBlurNum(2);
		ray.setCulling(true);
		ray.setAmbientLight(1.0f);
		ray.setAmbientLight(Color.LIGHT_GRAY);
		
		this.setManager(manager);


		this.world = new World(new Vector2(0, -1), true);
		debug = new Box2DDebugRenderer();
		
		for(Entity e : this.getEntities()){
			e.setMap(this);
			e.create(this);
		}
		for(Tile t : this.getTiles()){
			t.setMap(this);
			t.create(this);
		}
		
	}
	
	public void draw(SpriteBatch batch){
		for(Tile t : this.getTiles()){
			t.draw(batch);
		}
		EntityPlayer player = null;
		for(Entity e : this.getEntities()){
			if(!e.isDead()){
			e.draw(batch);
			if(e instanceof EntityPlayer){
				player = (EntityPlayer) e;
			}
			}
		}
		
		if(player != null && !player.isDead()){
			player.draw(batch);
		}
		//Set position based on launcher size so we do not start the camera offset centered as it is by default
		camera.position.set(new Vector2(offset.x + Launcher.DEFAULT_WIDTH / 2, offset.y + Launcher.DEFAULT_HEIGHT / 2), 0);
		camera.update();
		//System.out.println(offset.x);
		//debug.render(world, camera.combined);
	}
	
	public void update(){
		this.world.step(1/60f, 6, 2);
		this.getEntities().addAll(entityQueue);
		entityQueue.clear();
		
		for(Entity e : this.getEntities()){
			if(!e.isDead()){
			e.update();
			}
		}
		for(Tile t : this.getTiles()){
			t.update();
		}
		
		//System.out.println("Level" + this.level);
	}
	
	public void moveOffset(float diffX, float diffY, boolean redefine){
		if(!redefine){
		this.diffOffset.add(diffX, diffY);
		this.getOffset().add(diffX, diffY);
		}else{
			this.diffOffset.set(diffX, diffY);
			this.getOffset().set(diffX, diffY);
		}
	}
	
	public void spawnEntity(Entity entity){
		//entity.create(world, camera, this);
		entity.create(this);
		this.entityQueue.add(entity);
	}
	
	public void updateAfterRender(){
		this.ray.setCombinedMatrix(camera.combined);
		this.ray.updateAndRender();
	}

	public List<Entity> getEntities() {
		return entities;
	}

	public void setEntities(List<Entity> entities) {
		this.entities = entities;
	}

	public List<Entity> getEntityQueue() {
		return entityQueue;
	}

	public void setEntityQueue(List<Entity> entityQueue) {
		this.entityQueue = entityQueue;
	}

	public List<Tile> getTiles() {
		return tiles;
	}

	public void setTiles(List<Tile> tiles) {
		this.tiles = tiles;
	}

	public OrthographicCamera getCamera() {
		return camera;
	}

	public void setCamera(OrthographicCamera camera) {
		this.camera = camera;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public Box2DDebugRenderer getDebug() {
		return debug;
	}

	public void setDebug(Box2DDebugRenderer debug) {
		this.debug = debug;
	}

	public StateManager getManager() {
		return manager;
	}

	public void setManager(StateManager manager) {
		this.manager = manager;
	}
	
	public RayHandler getRay(){
		return this.ray;
	}

	public static int getRays() {
		return RAYS;
	}

	public void setRay(RayHandler ray) {
		this.ray = ray;
	}

	public Vector2 getOffset() {
		return offset;
	}

	public void setOffset(Vector2 offset) {
		this.offset = offset;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	
}