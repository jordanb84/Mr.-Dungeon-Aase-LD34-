package com.exilegl.ld34.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.exilegl.ld34.entity.Entity;
import com.exilegl.ld34.entity.EntityPlayer;
import com.exilegl.ld34.game.Launcher;
import com.exilegl.ld34.map.Level;
import com.exilegl.ld34.map.Map;
import com.exilegl.ld34.map.MapLoader;
import com.exilegl.ld34.map.Maps;

public class StateLevel extends State{

	//The attached level for reinitiation
	private Level level;
	
	//The level's map
	private Map map;
	
	OrthographicCamera camera;
	
	private int levelNumber;
	
	public StateLevel(StateManager manager, int level, OrthographicCamera camera) {
		super(manager);
		try{
		this.level = (Maps.LEVELS[level]);
		this.levelNumber = level;
		}catch(IndexOutOfBoundsException e){
			this.level = (Maps.LEVELS[0]);
			//e.printStackTrace();
		}
		this.camera = camera;
		
		this.reinitiate(false);
	}

	@Override
	public void draw(SpriteBatch batch) {
		this.map.draw(batch);
	}

	@Override
	public void update() {
		this.map.update();
		
		if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
			this.getManager().setCurrentState(new StateMenu(this.getManager(), this.camera));
		}
	}

	@Override
	public void updateAfterDraw() {
		this.map.updateAfterRender();
	}
	
	/**
	 * Used to reinitiate the level
	 * in the beginning and when the player dies
	 */
	public void reinitiate(boolean reinitiate){
		MapLoader loader = new MapLoader();
		Entity[] entities = {new EntityPlayer(new Vector2(Launcher.DEFAULT_WIDTH / 2 - 64, 100))};
		map = (loader.getMapFromArray(this.level.getTiles(), entities, camera, this.getManager(), levelNumber));
	}

}