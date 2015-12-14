package com.exilegl.ld34.state;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class State {
	
	//The state's containing manager
	private StateManager manager;
	
	public State(StateManager manager){
		this.setManager(manager);
	}
	
	public abstract void draw(SpriteBatch batch);
	
	public abstract void update();
	
	public abstract void updateAfterDraw();

	public StateManager getManager() {
		return manager;
	}

	public void setManager(StateManager manager) {
		this.manager = manager;
	}

}
