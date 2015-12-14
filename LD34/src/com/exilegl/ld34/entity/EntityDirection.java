package com.exilegl.ld34.entity;

public enum EntityDirection {
	NONE(4), UP(0), DOWN(1), LEFT(2), RIGHT(3)
	
	;
	
	EntityDirection(int id){
		this.ID = id;
	}
	
	public final int ID;
	
	public static EntityDirection getOpposite(EntityDirection direction){
		switch(direction){
		case UP:
			return DOWN;
		case DOWN:
			return UP;
		case LEFT:
			return RIGHT;
		case RIGHT:
			return LEFT;
		default:
			return NONE;
		}
	}
}
