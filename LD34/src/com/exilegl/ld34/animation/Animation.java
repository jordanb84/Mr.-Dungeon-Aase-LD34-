package com.exilegl.ld34.animation;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Animation {
	
	private float elapsed;
	
	private int currentFrameId;
	
	private List<Frame> frames = new ArrayList<Frame>();
	
	public void draw(SpriteBatch batch, Vector2 location){
		this.getCurrentFrame().draw(batch, location);
	}
	
	public void update(){
		float delta = Gdx.graphics.getRawDeltaTime();
		elapsed = (elapsed + 1 * delta);
		
		if(elapsed > this.getCurrentFrame().getDuration()){
			try{
				this.frames.get(currentFrameId + 1);
				this.currentFrameId++;
			}catch(IndexOutOfBoundsException i){
				this.currentFrameId = 0;
			}
			elapsed = 0;
		}
	}
	
	public void addFrame(Frame frame){
		this.frames.add(frame);
	}
	
	public int getWidth(){
		return this.getCurrentFrame().getTexture().getRegionWidth();
	}
	
	public int getHeight(){
		return this.getCurrentFrame().getTexture().getRegionHeight();
	}
	
	public Frame getCurrentFrame(){
		return this.frames.get(this.currentFrameId);
	}

}
