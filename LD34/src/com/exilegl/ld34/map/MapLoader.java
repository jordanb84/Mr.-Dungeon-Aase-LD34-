package com.exilegl.ld34.map;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.exilegl.ld34.entity.Entity;
import com.exilegl.ld34.state.StateManager;
import com.exilegl.ld34.tile.Tile;
import com.exilegl.ld34.tile.TileType;

public class MapLoader {

	public Map getMapFromArray(int[] tiles, Entity[] entities, OrthographicCamera camera, StateManager manager, int level){
		//Load tiles
		int layerWidth = (Maps.MAP_WIDTH); //width of each row
		int layerRows = (Maps.MAP_HEIGHT); //amount of rows
		List<Tile> tileList = new ArrayList<Tile>();
		int total = 0;
		for(int layer = 0; layer < layerRows; layer++){
			for(int index = 0; index < layerWidth; index++){
				TileType type = (TileType.TYPES[tiles[total]]);
				int x;
				int y;
				x = (type.ANIMATION.getWidth() * index);
				y = (type.ANIMATION.getHeight() * layer);
				
				tileList.add(new Tile(type, new Vector2(x, y)));
				total++;
			}
		}
		
		List<Entity> entityList = new ArrayList<Entity>();
		for(Entity e : entities){
			entityList.add(e);
		}
		
		return new Map(entityList, tileList, camera, manager, level);
	}
	
}
