package com.exilegl.ld34.animation;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Frame {

	private float duration;
	
	private TextureRegion texture;
	
	public Frame(TextureRegion texture, float duration){
		this.texture = texture;
		this.setDuration(duration);
	}
	
	public void draw(SpriteBatch batch, Vector2 location){
		batch.draw(this.texture, location.x, location.y);
	}
	
	public float getDuration() {
		return duration;
	}

	public void setDuration(float duration) {
		this.duration = duration;
	}
	
	public TextureRegion getTexture() {
		return texture;
	}

	public void setTexture(TextureRegion texture) {
		this.texture = texture;
	}
	
}
