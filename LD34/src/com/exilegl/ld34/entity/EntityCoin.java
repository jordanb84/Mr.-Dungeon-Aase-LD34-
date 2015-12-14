package com.exilegl.ld34.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.exilegl.ld34.animation.Animation;
import com.exilegl.ld34.animation.EntityAnimation;
import com.exilegl.ld34.animation.Frame;
import com.exilegl.ld34.map.Map;

import box2dLight.PointLight;

public class EntityCoin extends Entity{

	public boolean noLight;
	
	public EntityCoin(Vector2 location) {
		super(null, location, 0, 0, false);
		this.setSingleAnimation(new Animation());
		this.getSingleAnimation().addFrame(new Frame(new TextureRegion(new Texture(Gdx.files.internal("assets/texture/misc/coin.png"))), 1));
	}

	@Override
	public void update() {
		this.updateRectangle();
		
		if(this.getLight() == null && !noLight){
			this.setLight(new PointLight(this.getMap().getRay(), Map.RAYS, Color.YELLOW, 64, this.getLocation().x + this.getSingleAnimation().getWidth() / 2, this.getLocation().y + this.getSingleAnimation().getHeight() / 2));
		}
		
		if(!noLight){
		this.flickerLight(20, 84, 64);
		}
	}

	@Override
	public void create(Map map) {
		this.setMap(map);
	}
	
	public void setNoLight(boolean noLight){
		this.noLight = noLight;
	}

}
