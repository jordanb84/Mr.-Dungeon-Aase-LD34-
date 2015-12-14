package com.exilegl.ld34.ai;

import com.badlogic.gdx.math.Vector2;
import com.exilegl.ld34.entity.Entity;

public class AiFollowEntity extends Ai{

	private Entity followEntity;
	
	public AiFollowEntity(Entity entity, Entity followEntity) {
		super(entity);
		this.setFollowEntity(followEntity);
	}

	@Override
	public void update() {
		float x = this.getEntity().getLocation().x;
		float y = this.getEntity().getLocation().y;
		float eX = this.getFollowEntity().getLocation().x;
		float eY = this.getFollowEntity().getLocation().y;
		
		Vector2 force = new Vector2();
		float speed = (this.getEntity().getSpeed());
		if(x < eX){
			force.set(speed, 0);
		}
		if(x > eX){
			force.set(-speed, 0);
		}
		if(y < eY){
			force.set(0, speed);
		}
		if(y > eY){
			force.set(0, -speed);
		}
		this.getEntity().move(force, true);
	}

	public Entity getFollowEntity() {
		return followEntity;
	}

	public void setFollowEntity(Entity followEntity) {
		this.followEntity = followEntity;
	}

}