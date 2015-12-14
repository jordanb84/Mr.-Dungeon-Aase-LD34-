package com.exilegl.ld34.ai;

import com.exilegl.ld34.entity.Entity;

public abstract class Ai {

	//The entity the AI is assigned to
	private Entity entity;
	
	public Ai(Entity entity){
		this.setEntity(entity);
	}

	/**
	 * Performs the AI
	 */
	public abstract void update();
	
	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}
	
}
