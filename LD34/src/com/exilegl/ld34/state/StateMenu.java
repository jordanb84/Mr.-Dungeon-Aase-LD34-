package com.exilegl.ld34.state;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.exilegl.ld34.game.Launcher;
import com.exilegl.ld34.map.Maps;
import com.exilegl.ld34.text.Text;
import com.exilegl.ld34.ui.Button;

public class StateMenu extends State{

	private List<Button> buttons = new ArrayList<Button>();
	
	private Texture tile;
	private Texture tile2;
	
	private float offset;
	
	private Random random;
	
	Text small;
	Text large;
	Text header;
	
	public StateMenu(StateManager manager, OrthographicCamera camera) {
		super(manager);
		camera.position.set(new Vector2(Launcher.DEFAULT_WIDTH / 2, Launcher.DEFAULT_HEIGHT / 2), 0);
		camera.update();
		int levels = 20;
		int width = levels / 5;
		int number = 1;
		for(int layer = 0; layer < levels / 5; layer++){
			for(int index = 0; index < levels / width; index++){
				String text;
				text = ("" + number);
				this.buttons.add(new Button(text, new Vector2(50 + Gdx.graphics.getWidth() / 4 * index, Gdx.graphics.getHeight() / 2 - 136 * layer - Gdx.graphics.getHeight() / 6), new StateLevel(this.getManager(), number - 1, camera), camera, number));
				number++;
			}
		}
		
		tile = new Texture(Gdx.files.internal("assets/texture/tile/brick2.png"));
		tile2 = new Texture(Gdx.files.internal("assets/texture/tile/brick.png"));
		random = new Random();
		
		small = new Text("assets/font/blow.ttf", 22, Color.WHITE, Color.LIGHT_GRAY, 3);
		large = new Text("assets/font/blow.ttf", 22, Color.WHITE, Color.BLACK, 2);
		header = new Text("assets/font/blow.ttf", 64, Color.WHITE, Color.LIGHT_GRAY, 3);
		
		Gdx.input.setCursorPosition(Launcher.DEFAULT_WIDTH / 2, Launcher.DEFAULT_HEIGHT / 2);
	}

	@Override
	public void draw(SpriteBatch batch) {
		for(int layer = 0; layer < Gdx.graphics.getHeight() / tile.getHeight() + tile.getHeight() + offset * tile.getHeight(); layer++){
			for(int row = 0; row < Gdx.graphics.getWidth() / tile.getWidth() + 1; row++){
				if(random.nextBoolean() && random.nextBoolean() && random.nextBoolean() && random.nextBoolean() && random.nextBoolean()){
				batch.draw(tile2, row * tile.getWidth(), layer * tile.getHeight() - offset);
				}else{
					batch.draw(tile, row * tile.getWidth(), layer * tile.getHeight() - offset);
				}
			}
		}
		
		if(offset * tile.getHeight() > Launcher.DEFAULT_HEIGHT * Launcher.DEFAULT_HEIGHT / tile.getHeight()){
			offset = 0;
		}
		
		for(Button b : this.buttons){
			b.draw(batch);
		}
		
		offset = (offset + .5f);
		
		small.draw(batch, "Stuck? Press ESC in any level to return", new Vector2(0, 22));
		small.draw(batch, "Made for LD34 'Two Button Controls'", new Vector2(Launcher.DEFAULT_WIDTH - 380, 22));
		header.draw(batch, "Mr. Dungeon Aase", new Vector2(Launcher.DEFAULT_WIDTH / 4, Launcher.DEFAULT_HEIGHT - 64));
		String s = "Something new on each level!";
		large.draw(batch, "Something new on each level!", new Vector2(Launcher.DEFAULT_WIDTH / 4 + s.length() * 3, Launcher.DEFAULT_HEIGHT - 128));
	}

	@Override
	public void update() {
		for(Button b : this.buttons){
			b.update();
		}
	}

	@Override
	public void updateAfterDraw() {
		// TODO Auto-generated method stub
		
	}

}
