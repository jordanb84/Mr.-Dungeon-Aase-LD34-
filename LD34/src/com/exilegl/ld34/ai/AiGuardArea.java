package com.exilegl.ld34.ai;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.exilegl.ld34.entity.Entity;
import com.exilegl.ld34.entity.EntityDirection;

public class AiGuardArea extends Ai{

	//The location at which the guard area starts
	private Vector2 start;
	
	//The location at which the guard area ends
	private Vector2 end;
	
	//Whether or not we're going to the end. If not, we're going to the start
	private boolean ending;
	
	//The start and end rectangles
	private Rectangle startRectangle;
	private Rectangle endRectangle;
	
	//The entity's rectangle
	private Rectangle entityRectangle;
	
	private boolean moveY;
	
	public AiGuardArea(Entity entity, Vector2 guardStart, Vector2 guardEnd, boolean moveY) {
		super(entity);
		this.start = guardStart;
		this.end = guardEnd;
		startRectangle = new Rectangle(start.x, start.y, 1, 1);
		endRectangle = new Rectangle(end.x, end.y, 1, 1);
		if(entity.getRectangle() == null){
			entity.updateRectangle();
		}
		entityRectangle = (entity.getRectangle());
		this.moveY = moveY;
	}

	@Override
	public void update() {
		startRectangle.set(start.x, start.y, 1, 1);
		endRectangle.set(end.x, end.y, 1, 1);
		if(this.getEntity().getAnimation() != null){
		entityRectangle.set(this.getEntity().getLocation().x, this.getEntity().getLocation().y, this.getEntity().getAnimation().getDirections()[this.getEntity().getDirection().ID].getWidth(), this.getEntity().getAnimation().getDirections()[this.getEntity().getDirection().ID].getHeight());
		}else{
			entityRectangle.set(this.getEntity().getLocation().x, this.getEntity().getLocation().y, this.getEntity().getSingleAnimation().getWidth(), this.getEntity().getSingleAnimation().getHeight());
		}
		float x = (this.getEntity().getLocation().x);
		float y = (this.getEntity().getLocation().y);
		
		float endX = (endRectangle.x);
		float endY = (endRectangle.y);
		
		float startX = (startRectangle.x);
		float startY = (startRectangle.y);
		
		Vector2 movement = new Vector2(0, 0);
		if(ending){
			//move closer to end point
			if(x < endX){
				this.getEntity().move(EntityDirection.RIGHT, true, true);
			}
			if(x > endX){
				this.getEntity().move(EntityDirection.LEFT, true, true);
			}
			if(moveY){
			if(y < endY){
				this.getEntity().move(EntityDirection.UP, true, true);
			}
			if(y > endY){
				this.getEntity().move(EntityDirection.DOWN, true, true);
			}
			}
		}else{
			//move closer to start point
			if(x < startX){
				this.getEntity().move(EntityDirection.RIGHT, true, true);
			}
			if(x > startX){
				this.getEntity().move(EntityDirection.LEFT, true, true);
			}
			if(moveY){
			if(y < startY){
				this.getEntity().move(EntityDirection.UP, true, true);
			}
			if(y > startY){
				this.getEntity().move(EntityDirection.DOWN, true, true);
			}
			}
		}
		
		if(entityRectangle.overlaps(startRectangle)){
			//go back to end
			this.ending = true;
		}
		if(entityRectangle.overlaps(endRectangle)){
			//go back to start
			this.ending = false;
		}
	}

	public Vector2 getStart() {
		return start;
	}

	public void setStart(Vector2 start) {
		this.start = start;
	}

	public Vector2 getEnd() {
		return end;
	}

	public void setEnd(Vector2 end) {
		this.end = end;
	}

	public boolean isEnding() {
		return ending;
	}

	public void setEnding(boolean ending) {
		this.ending = ending;
	}

	public Rectangle getStartRectangle() {
		return startRectangle;
	}

	public void setStartRectangle(Rectangle startRectangle) {
		this.startRectangle = startRectangle;
	}

	public Rectangle getEndRectangle() {
		return endRectangle;
	}

	public void setEndRectangle(Rectangle endRectangle) {
		this.endRectangle = endRectangle;
	}

	public Rectangle getEntityRectangle() {
		return entityRectangle;
	}

	public void setEntityRectangle(Rectangle entityRectangle) {
		this.entityRectangle = entityRectangle;
	}
	
}
