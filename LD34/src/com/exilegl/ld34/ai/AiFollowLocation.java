package com.exilegl.ld34.ai;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.exilegl.ld34.entity.Entity;
import com.exilegl.ld34.entity.EntityDirection;

public class AiFollowLocation extends Ai{

	//The follow destination
	private Vector2 destination;
	private Rectangle destinationRectangle;
	
	//The follow speed
	private float speed;
	
	//Whether or not we've reached the destination
	private boolean reached;
	
	private boolean updateAnimation;
	
	private boolean moveBody;
	
	public AiFollowLocation(Entity entity, Vector2 destination, float speed, boolean updateAnimation, boolean moveBody) {
		super(entity);
		this.destination = destination;
		this.speed = speed;
		this.destinationRectangle = new Rectangle(destination.x, destination.y, 1, 1);
		this.updateAnimation = updateAnimation;
		
		this.moveBody = moveBody;
	}

	@Override
	public void update() {
		float eX = (this.getEntity().getLocation().x);
		float eY = (this.getEntity().getLocation().y);
		
		float dX = (this.destination.x);
		float dY = (this.destination.y);
		
		boolean hasAnimation = false;
		if(this.getEntity().getAnimation() != null){
			hasAnimation = true;
		}
		
		if(eX < dX){
			this.getEntity().move(EntityDirection.RIGHT, false, moveBody);
			if(hasAnimation){
				this.getEntity().getAnimation().update(EntityDirection.RIGHT);
			}
		}
		if(eX > dX){
			this.getEntity().move(EntityDirection.LEFT, false, moveBody);
			if(hasAnimation){
				this.getEntity().getAnimation().update(EntityDirection.LEFT);
			}
		}
		if(eY < dY){
			this.getEntity().move(EntityDirection.UP, false, moveBody);
		}
		if(eY > dY){
			this.getEntity().move(EntityDirection.DOWN, false, moveBody);
		}
	
		if(this.getEntity().getRectangle().overlaps(this.destinationRectangle)){
			this.setReached(true);
		}
		
	}
	
	public Vector2 getDestination(){
		return this.destination;
	}

	public boolean isReached() {
		return reached;
	}

	public void setReached(boolean reached) {
		this.reached = reached;
	}

}