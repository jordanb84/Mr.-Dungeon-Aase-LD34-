package com.exilegl.ld34.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.exilegl.ld34.animation.Animation;
import com.exilegl.ld34.game.Launcher;

public class StateManager {

	private State currentState;
	
	private Texture cursor;
	
	private OrthographicCamera camera;
	
	private float scale = 1;
	
	public StateManager(OrthographicCamera camera){
		Gdx.input.setCursorCatched(true);
		this.cursor = new Texture(Gdx.files.internal("assets/texture/misc/cursor2.png"));
		this.camera = camera;
	}
	
	/**
	 * Draws the current state
	 */
	public void draw(SpriteBatch batch){
		this.getCurrentState().draw(batch);
		
		Vector3 m = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		camera.unproject(m);
		
		//System.out.println(m.x + "/" + m.y);
		
		Sprite sprite = new Sprite(cursor);
		sprite.setPosition(m.x, m.y);
		sprite.setSize(cursor.getWidth() * scale, cursor.getHeight() * scale);
		sprite.draw(batch);
		
	}
	
	public void update(){
		this.getCurrentState().update();
		

	}
	
	public void updateAfterDraw(){
		this.getCurrentState().updateAfterDraw();
	}

	public State getCurrentState() {
		return currentState;
	}

	public void setCurrentState(State currentState) {
		this.currentState = currentState;
	}
	
	public void setScale(float scale){
		this.scale = scale;
	}
	
	public float getScale(){
		return this.scale;
	}
}
