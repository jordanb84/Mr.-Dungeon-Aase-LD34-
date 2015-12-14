package com.exilegl.ld34.entity.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.exilegl.ld34.entity.Entity;
import com.exilegl.ld34.entity.EntityCoin;
import com.exilegl.ld34.entity.EntityDirection;
import com.exilegl.ld34.entity.EntityPlayer;
import com.exilegl.ld34.map.Map;
import com.exilegl.ld34.sound.Sound;
import com.exilegl.ld34.tile.Tile;

import box2dLight.PointLight;

public class EntityScaledBullet extends EntityBullet{

	public EntityScaledBullet(Vector2 location, Color color, EntityDirection direction, float distance,
			boolean killsEnemies, float duration, boolean textured, boolean ranged, boolean moveY) {
		super(location, color, direction, distance, killsEnemies, duration, textured, ranged, moveY);
		
	}

	@Override
	public void draw(SpriteBatch batch){
		if(this.isTextured()){
			Sprite sprite = new Sprite(this.getTexture());
			sprite.setPosition(this.getLocation().x, this.getLocation().y);
			sprite.setSize(this.getTexture().getWidth() * this.getMap().getManager().getScale(), this.getTexture().getHeight() * this.getMap().getManager().getScale());
			sprite.draw(batch);
			//batch.draw(this.getTexture(), this.getLocation().x, this.getLocation().y);
		}
		
		/**for(Tile t : this.getMap().getTiles()){
			if(t.getType().SOLID){
				Rectangle tileRect = new Rectangle(t.getLocation().x, t.getLocation().y, t.getSingleAnimation().getWidth(), t.getSingleAnimation().getHeight());
				Rectangle bulletRect = new Rectangle(this.getLocation().x, this.getLocation().y, this.getLight().getDistance() * this.getMap().getManager().getScale(), this.getLight().getDistance() * this.getMap().getManager().getScale());
				//System.out.println(bulletRect.width);
				if(bulletRect.overlaps(tileRect)){
					this.kill();
				}
			}
		}**/
	}
	
	@Override
	public void update() {
		setAge((getAge() + 1 * Gdx.graphics.getDeltaTime()));
		if(this.getLight() == null){
			this.setLight(new PointLight(this.getMap().getRay(), Map.RAYS, this.getColor(), this.getDistance() / 5, this.getLocation().x, this.getLocation().y));
		}
		
		this.flickerLight(20, (int) ((int) getDistance() * 1.5f), (int) getDistance());
		
		this.getLight().setPosition(this.getLocation().x + this.getOffset().x, this.getLocation().y + this.getOffset().y);
		this.setRectangle(new Rectangle(this.getLocation().x, this.getLocation().y, this.getLight().getDistance(), this.getLight().getDistance()));
		
		float scale = (this.getMap().getManager().getScale());
		this.getRectangle().set(this.getRectangle().x, this.getRectangle().y, this.getRectangle().width * scale, this.getRectangle().height * scale);
		
		this.performAi();
		
		if(this.getAge() > this.getDuration()){
			this.kill();
		}
		
		for(Entity e : this.getMap().getEntities()){
			if(!e.isDead()){
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
		}
		
	}
	
}
