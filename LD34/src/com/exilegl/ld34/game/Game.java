package com.exilegl.ld34.game;

import org.lwjgl.opengl.GL11;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.exilegl.ld34.state.StateLevel;
import com.exilegl.ld34.state.StateManager;
import com.exilegl.ld34.state.StateMenu;

public class Game extends ApplicationAdapter{

	private StateManager manager;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	@Override
	public void create(){
		
		camera = new OrthographicCamera();
		batch = new SpriteBatch();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.setProjectionMatrix(camera.combined);
		//this.manager.setCurrentState(new StateLevel(manager, 0, camera));
		manager = new StateManager(camera);
		this.manager.setCurrentState(new StateMenu(manager, camera));
	}
	
	@Override
	public void render(){
		Gdx.gl.glClearColor(0f, .1f, .1f, 1);
		Gdx.gl.glClear(GL11.GL_COLOR_BUFFER_BIT);
		manager.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		manager.draw(batch);
		batch.end();
		manager.updateAfterDraw();
	}
	
}
