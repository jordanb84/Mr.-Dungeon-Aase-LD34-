package com.exilegl.ld34.entity.enemy;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.exilegl.ld34.ai.Ai;
import com.exilegl.ld34.animation.EntityAnimation;
import com.exilegl.ld34.entity.Entity;
import com.exilegl.ld34.entity.EntityCoin;

public abstract class EntityEnemy extends Entity{

	//The entity's list of actions
	private List<Ai> actions = new ArrayList<Ai>();
	
	public EntityEnemy(EntityAnimation animation, Vector2 location, float speed, float health, boolean collides) {
		super(animation, location, speed, health, collides);
		
	}

	public List<Ai> getActions() {
		return actions;
	}

	public void setActions(List<Ai> actions) {
		this.actions = actions;
	}
	
	public void addAction(Ai action){
		this.actions.add(action);
	}

	public void performAi(){
		for(Ai a : this.getActions()){
			a.update();
		}
	}
	
	/**
	 * Spawns a coin entity at the enemy's location
	 */
	public void spawnCoin(){
		if(!this.isDead()){
		this.getMap().spawnEntity(new EntityCoin(new Vector2(this.getLocation())));
		}
	}
	
}