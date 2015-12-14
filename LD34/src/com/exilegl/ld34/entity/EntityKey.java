package com.exilegl.ld34.entity;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.exilegl.ld34.animation.Animation;
import com.exilegl.ld34.animation.EntityAnimation;
import com.exilegl.ld34.animation.Frame;
import com.exilegl.ld34.game.Launcher;
import com.exilegl.ld34.map.Map;
import com.exilegl.ld34.sound.Sound;
import com.exilegl.ld34.state.StateEnd;
import com.exilegl.ld34.state.StateLevel;
import com.exilegl.ld34.state.StateMenu;
import com.exilegl.ld34.text.Text;
import com.exilegl.ld34.tile.Tile;
import com.exilegl.ld34.tile.TileType;

import box2dLight.PointLight;

public class EntityKey extends Entity{

	private boolean collided;
	
	private float elapsedDeath;
	private float rotation;
	private Vector2 scale;
	
	private float elapsedSpawn;
	
	Text text;
	
	private boolean coins;
	
	private Random random;
	
	public EntityKey(Vector2 location) {
		super(null, location, 0, 0, false);
		this.setSingleAnimation(new Animation());
		this.getSingleAnimation().addFrame(new Frame(new TextureRegion(new Texture(Gdx.files.internal("assets/texture/misc/key.png"))), 1));
		
		scale = new Vector2();
		
		text = new Text("assets/font/blow.ttf", 32, Color.WHITE, Color.LIGHT_GRAY, 3);
		
		random = new Random();
	}

	@Override
	public void update() {
		this.updateRectangle();
		
		if(this.getLight() == null){
			this.setLight(new PointLight(this.getMap().getRay(), Map.RAYS, Color.YELLOW, 64, this.getLocation().x + this.getSingleAnimation().getWidth() / 2, this.getLocation().y + this.getSingleAnimation().getHeight() / 2));
		}
		
		this.flickerLight(20, 84, 64);
		
		for(Entity e : this.getMap().getEntities()){
			if(e instanceof EntityPlayer && e.getRectangle().overlaps(this.getRectangle()) && !collided){
				this.collided = true;
				Sound.play(Sound.Key_Collect);
			}
		}
		
		if(collided){
			elapsedDeath = (elapsedDeath + 1 * Gdx.graphics.getDeltaTime());
			this.scale.add(3f * Gdx.graphics.getDeltaTime(), 3f * Gdx.graphics.getDeltaTime());
			this.getLight().setDistance(this.getLight().getDistance() + 10);
			this.rotation++;
			if(elapsedDeath > 3){
				if(!(this.getMap().getLevel() == 18)){
				this.getMap().getManager().setCurrentState(new StateMenu(this.getMap().getManager(), this.getMap().getCamera()));
				}else{
					this.getMap().getManager().setCurrentState(new StateEnd(this.getMap().getManager(), this.getMap().getCamera()));
				}
			}
		}
		
		for(Tile t : this.getMap().getTiles()){
			if(t.getType() == TileType.FloorTrap){
				Rectangle tileRect = new Rectangle(t.getLocation().x, t.getLocation().y, t.getSingleAnimation().getWidth(), t.getSingleAnimation().getHeight());
				if(tileRect.overlaps(this.getRectangle())){
					t.setType(TileType.Floor);
					t.setSingleAnimation(t.getType().ANIMATION);
				}
			}
			if(t.getType() == TileType.Lava){
				t.setType(TileType.Floor);
				t.setSingleAnimation(t.getType().ANIMATION);
			}
		}
		
		if(!coins){
			for(Tile t : this.getMap().getTiles()){
				if(t.getType() == TileType.Floor){
					if(random.nextBoolean()){
					EntityCoin coin = new EntityCoin(new Vector2(t.getLocation().x, t.getLocation().y));
					coin.setNoLight(true);
					this.getMap().spawnEntity(coin);
					coins = true;
					}
				}
			}
		}
		
		elapsedSpawn = (elapsedSpawn + 1 * Gdx.graphics.getDeltaTime());
	}
	
	@Override
	public void draw(SpriteBatch batch){
		Animation animation = (this.getSingleAnimation());
		Sprite sprite = new Sprite(animation.getCurrentFrame().getTexture());
		sprite.setPosition(this.getLocation().x, this.getLocation().y);
		sprite.setSize(animation.getWidth() + scale.x, animation.getHeight() + scale.y);
		sprite.setRotation(rotation);
		sprite.draw(batch);
		
		if(elapsedSpawn < 7){
			Vector2 position = new Vector2(Launcher.DEFAULT_WIDTH / 4 + this.getMap().getOffset().x, Launcher.DEFAULT_HEIGHT / 2 + this.getMap().getOffset().y);
			text.draw(batch, "The key has spawned!", position);
			text.draw(batch, "Avoid traps and find the key", new Vector2(position.x, position.y - 64));
		}
	}

	@Override
	public void create(Map map) {
		this.setMap(map);
	}

}
