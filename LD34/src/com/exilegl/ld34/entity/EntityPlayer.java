package com.exilegl.ld34.entity;

import java.util.Random;

import org.lwjgl.input.Mouse;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.exilegl.ld34.ai.AiFollowLocation;
import com.exilegl.ld34.animation.Animation;
import com.exilegl.ld34.animation.EntityAnimation;
import com.exilegl.ld34.entity.enemy.EntityBullet;
import com.exilegl.ld34.entity.enemy.EntityEnemy;
import com.exilegl.ld34.entity.enemy.EntityScaledBullet;
import com.exilegl.ld34.game.Launcher;
import com.exilegl.ld34.map.Map;
import com.exilegl.ld34.map.Maps;
import com.exilegl.ld34.sound.Sound;
import com.exilegl.ld34.state.StateLevel;
import com.exilegl.ld34.text.Text;
import com.exilegl.ld34.tile.Tile;
import com.exilegl.ld34.tile.TileType;

import box2dLight.PointLight;

public class EntityPlayer extends EntityEnemy{

	//The player's original position, used to set the max offset
	private Vector2 originalLocation;
	
	private float elapsed;
	
	private int lives;
	private Texture heart;
	
	private int coins;
	private int requiredCoins = 5;
	private Texture coin;
	
	private Vector2 mouseCurrent;
	private Vector2 mousePast;
	
	private Text text;
	
	private boolean spawnedKey;
	
	private float elapsedDeath;
	private float rotation;
	private Vector2 scale;
	
	private boolean trapped;
	private boolean trappedTile;
	
	private float elapsedScale;
	
	private boolean trappedLava;
	
	private boolean trappedPurple;
	
	public EntityPlayer(Vector2 location) {
		super(new EntityAnimation(new Texture(Gdx.files.internal("assets/texture/entity/playersheet2.png")), .3f), location, 2, 5, true);
		this.lives = 3;
		this.getAnimation().addDirectionFrame(EntityDirection.LEFT, 0, 64, 64, 64);
		this.getAnimation().addDirectionFrame(EntityDirection.LEFT, 64, 64, 64, 64);
		this.getAnimation().addDirectionFrame(EntityDirection.RIGHT, 0, 0, 64, 64);
		this.getAnimation().addDirectionFrame(EntityDirection.RIGHT, 64, 0, 64, 64);
		this.getAnimation().addDirectionFrame(EntityDirection.UP, 0, 64, 64, 64);
		this.getAnimation().addDirectionFrame(EntityDirection.UP, 64, 64, 64, 64);
		this.getAnimation().addDirectionFrame(EntityDirection.DOWN, 0, 0, 64, 64);
		this.getAnimation().addDirectionFrame(EntityDirection.DOWN, 64, 0, 64, 64);
		
		this.heart = new Texture(Gdx.files.internal("assets/texture/misc/heart.png"));
		this.coin = new Texture(Gdx.files.internal("assets/texture/misc/coin.png"));
		
		this.originalLocation = new Vector2(location.x, location.y);
		this.mouseCurrent = new Vector2();
		this.mousePast = new Vector2();
		scale = new Vector2();
		
		this.addAction(new AiFollowLocation(this, new Vector2(this.getLocation()), this.getSpeed(), true, true));
		
		text = new Text("assets/font/blow.ttf", 32, Color.WHITE, Color.LIGHT_GRAY, 3);
	}

	@Override
	public void draw(SpriteBatch batch){
		Animation animation = (this.getAnimation().getDirections()[this.getDirection().ID]);
		Sprite sprite = new Sprite(animation.getCurrentFrame().getTexture());
		sprite.setPosition(this.getLocation().x, this.getLocation().y);
		sprite.setSize(animation.getWidth() + scale.x, animation.getHeight() + scale.y);
		sprite.setRotation(rotation);
		sprite.draw(batch);
		
		for(int i = 0; i < lives; i++){
			batch.draw(heart, this.getLocation().x + 64 - heart.getWidth() * i * 1.5f, this.getLocation().y + 72);
		}
		
		Vector2 position = new Vector2(Launcher.DEFAULT_WIDTH / 2 + 32 + this.getMap().getOffset().x, Launcher.DEFAULT_HEIGHT - 64 + this.getMap().getOffset().y);
		batch.draw(coin, Launcher.DEFAULT_WIDTH / 2 + 32 + this.getMap().getOffset().x, Launcher.DEFAULT_HEIGHT - 64 + this.getMap().getOffset().y);
		text.draw(batch, coins + "/" + requiredCoins, new Vector2(position.x - 8, position.y - 8));
	
		if(trapped){
			Vector2 position2 = new Vector2(Launcher.DEFAULT_WIDTH / 4 + this.getMap().getOffset().x, Launcher.DEFAULT_HEIGHT / 2 + this.getMap().getOffset().y);
			text.draw(batch, "You've been trapped!", new Vector2(position2.x, position2.y));
			text.draw(batch, "Dark floor tiles will kill you", new Vector2(position2.x, position2.y - 64));
		}
		
		if(trappedTile){
			Vector2 position2 = new Vector2(Launcher.DEFAULT_WIDTH / 4 + this.getMap().getOffset().x, Launcher.DEFAULT_HEIGHT / 2 + this.getMap().getOffset().y);
			text.draw(batch, "You've been burned!", new Vector2(position2.x, position2.y));
			text.draw(batch, "Moving furnaces will kill you", new Vector2(position2.x, position2.y - 64));
		}
		
		if(trappedLava){
			Vector2 position2 = new Vector2(Launcher.DEFAULT_WIDTH / 4 + this.getMap().getOffset().x, Launcher.DEFAULT_HEIGHT / 2 + this.getMap().getOffset().y);
			text.draw(batch, "You've been melted!", new Vector2(position2.x, position2.y));
			text.draw(batch, "Lava will kill you", new Vector2(position2.x, position2.y - 64));
		}
		
		if(trappedPurple){
			Vector2 position2 = new Vector2(Launcher.DEFAULT_WIDTH / 4 + this.getMap().getOffset().x, Launcher.DEFAULT_HEIGHT / 2 + this.getMap().getOffset().y);
			text.draw(batch, "You've been purpled!", new Vector2(position2.x, position2.y));
			text.draw(batch, "Purple walls will kill you", new Vector2(position2.x, position2.y - 64));
		}
		
		elapsedScale = (elapsedScale + 1 * Gdx.graphics.getDeltaTime());
		
		/**if(elapsedScale > 1){
		this.scale.add(.5f, .5f);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(5 + this.getAnimation().getDirections()[this.getDirection().ID].getWidth() / 2, 5 + this.getAnimation().getDirections()[this.getDirection().ID].getHeight() / 2);
		this.getBody().destroyFixture(this.getBody().getFixtureList().get(0));
		//this.setBody(this.getMap().getWorld().createBody(this.getBodyDef()));
		this.getBody().createFixture(shape, 0.0f);
		elapsedScale = 0;
		shape.dispose();
		System.out.println("Rescaled");
		}**/
	}
	
	private int size = 0;
	
	@Override
	public void update() {
		Vector3 m = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		this.getMap().getCamera().unproject(m);
		/**this.mouseCurrent.set(m.x, m.y);
		Vector2 difference = new Vector2(mouseCurrent.x - mousePast.x, mouseCurrent.y - mousePast.y);
		mousePast = new Vector2(mouseCurrent.x, mouseCurrent.y);
		this.move(new Vector2(difference.x * this.getSpeed(), difference.y * this.getSpeed()), true);**/
		
		//if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
			((AiFollowLocation) this.getActions().get(0)).getDestination().set(m.x, m.y);
		
		this.updateRectangle();
		this.performAi();
		
		if(lives < 1){
		elapsedDeath = (elapsedDeath + 1 * Gdx.graphics.getDeltaTime());
		this.scale.add(-3f * Gdx.graphics.getDeltaTime(), -3f * Gdx.graphics.getDeltaTime());
		this.rotation++;
		if(elapsedDeath > 3){
			((StateLevel) this.getMap().getManager().getCurrentState()).reinitiate(true);
		}
		}
		
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
			size++;
		}else{
			size = 0;
		}
		this.getMap().getManager().setScale(1 + size / 300f);
		//System.out.println(1 + size / 300f);
		if(1 + size / 300f > 2.5f){
			size = 0;
		}
		
		/**if(Gdx.input.isKeyPressed(Input.Keys.W) && !Gdx.input.isKeyPressed(Input.Keys.A) && !Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.UP) && !Gdx.input.isKeyPressed(Input.Keys.A) && !Gdx.input.isKeyPressed(Input.Keys.D)){
			this.move(EntityDirection.UP, true, true);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S)  && !Gdx.input.isKeyPressed(Input.Keys.A) && !Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.DOWN) && !Gdx.input.isKeyPressed(Input.Keys.A) && !Gdx.input.isKeyPressed(Input.Keys.D)){
			this.move(EntityDirection.DOWN, true, true);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT) && !Gdx.input.isKeyPressed(Input.Keys.DOWN) && !Gdx.input.isKeyPressed(Input.Keys.UP)){
			this.move(EntityDirection.LEFT, true, true);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT) && !Gdx.input.isKeyPressed(Input.Keys.DOWN) && !Gdx.input.isKeyPressed(Input.Keys.UP)){
			this.move(EntityDirection.RIGHT, true, true);
		}**/
		
		elapsed = (elapsed + 1 * Gdx.graphics.getDeltaTime());
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && elapsed > 1){
			Vector3 mouse = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			this.getMap().getCamera().unproject(mouse);
			this.getMap().spawnEntity(new EntityScaledBullet(new Vector2(this.getLocation().x, this.getLocation().y), Color.RED, this.getDirection(), 250, true, 0.4f, true, false, false));
			Sound.play(Sound.Shoot);
		}
		
		if(elapsed > 1){
			elapsed = 0;
		}
		
		if(this.getLight() == null){
			this.setLight(new PointLight(this.getMap().getRay(), Map.RAYS, Color.WHITE, 64 * 4, this.getLocation().x, this.getLocation().y));
		}
		
		this.updatePhysics();
		this.flickerLight(30, 200, 120);
		this.getBody().setSleepingAllowed(false);
		
		float diffX = (this.getLocation().x - this.originalLocation.x);
		float diffY = (this.getLocation().y - this.originalLocation.y);
		this.getMap().moveOffset(diffX, diffY, true);
		
		for(Entity e : this.getMap().getEntities()){
			if(e instanceof EntityCoin && !e.isDead()){
				e.update();
				if(e.getRectangle().overlaps(this.getRectangle())){
				e.kill();
				this.coins++;
				Sound.play(Sound.Coin);
				}
			}
		}
		
		if(this.coins == this.requiredCoins && !spawnedKey){
			Random r = new Random();
			Vector2 position = new Vector2(r.nextInt(Maps.MAP_WIDTH * 64), r.nextInt(Maps.MAP_HEIGHT * 64));
			this.getMap().spawnEntity(new EntityKey(position));
			
			for(Tile t : this.getMap().getTiles()){
				if(t.getType().SOLID){
					if(r.nextBoolean() && r.nextBoolean()){
					t.setType(TileType.FloorTrap);
					}else{
						t.setType(TileType.Floor);
					}
					if(t.getLight() != null){
					t.getLight().setActive(false);
					}
					t.setSingleAnimation(t.getType().ANIMATION);
					t.getBody().setActive(false);
				}
			}
			for(Entity e : this.getMap().getEntities()){
				if(e instanceof EntityEnemy && !(e instanceof EntityPlayer)){
					e.kill();
				}
			}
			System.out.println("Spawned key at x" + position.x + "/ y " + position.y);
			this.spawnedKey = true;
			
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
		this.getPolygonShape().setAsBox(this.getAnimation().getDirections()[this.getDirection().ID].getWidth() / 2, this.getAnimation().getDirections()[this.getDirection().ID].getHeight() / 2);
		this.getBody().createFixture(this.getPolygonShape(), 0.0f);
		this.getPolygonShape().dispose();
	}
	
	@Override
	public void kill(){
		this.lives--;
		System.out.println("hit");
		if(this.lives == 0){
			Sound.play(Sound.Death);
		}
	}
	
	public void trap(){
		this.lives = 0;
		Sound.play(Sound.Death);
		trapped = true;
	}
	
	public void trapTile(){
		this.lives = 0;
		Sound.play(Sound.Death);
		trappedTile = true;
	}
	
	public void trapLava(){
		this.lives = 0;
		Sound.play(Sound.Death);
		trappedLava = true;
	}
	
	public void trapPurple(){
		this.lives = 0;
		Sound.play(Sound.Death);
		trappedPurple = true;
	}

}