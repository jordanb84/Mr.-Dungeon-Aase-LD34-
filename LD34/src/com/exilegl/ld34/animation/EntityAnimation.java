package com.exilegl.ld34.animation;

import org.lwjgl.util.vector.Vector4f;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.exilegl.ld34.entity.EntityDirection;

public class EntityAnimation {
	
	private Animation[] directions = new Animation[EntityDirection.values().length];
	
	private Texture sheet;
	
	private float duration;
	
	public EntityAnimation(Texture sheet, float duration){
		for(int i = 0; i < EntityDirection.values().length; i++){
			this.directions[i] = new Animation();
		}
		this.sheet = sheet;
		this.duration = duration;
	}
	
	/**
	 * Draws the animation in a given direction
	 */
	public void draw(SpriteBatch batch, EntityDirection direction, float x, float y){
		this.directions[direction.ID].draw(batch, new Vector2(x, y));
	}
	
	/**
	 * Updates the animation in a given direction
	 */
	public void update(EntityDirection direction){
		this.directions[direction.ID].update();
	}
	
	/**
	 * Initiates the animation for a given direction
	 * @param frames The list of frame locations to be loaded, in array format (xywidthheight), one array index per frame in format Vector4f.x/y/z/w
	 */
	public void addDirection(EntityDirection direction, Vector4f[] frames){
		for(int f = 0; f < frames.length; f++){
			this.directions[direction.ID].addFrame(new Frame(new TextureRegion(sheet, frames[f].x, frames[f].y, frames[f].z, frames[f].w), this.getDuration()));
		}	
	}
	
	public void addDirectionFrame(EntityDirection direction, int x, int y, int width, int height){
		this.directions[direction.ID].addFrame(new Frame(new TextureRegion(sheet, x, y, width, height), this.getDuration()));
	}

	public Animation[] getDirections() {
		return directions;
	}

	public void setDirections(Animation[] directions) {
		this.directions = directions;
	}

	public float getDuration() {
		return duration;
	}

	public void setDuration(float duration) {
		this.duration = duration;
	}
}
