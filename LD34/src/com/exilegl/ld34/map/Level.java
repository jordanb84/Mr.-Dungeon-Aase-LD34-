package com.exilegl.ld34.map;

public class Level {

	private int[] tiles;
	
	public Level(int[] tiles){
		this.setTiles(tiles);
	}
	
	public int[] getTiles() {
		return tiles;
	}

	public void setTiles(int[] tiles) {
		this.tiles = tiles;
	}
	
}
