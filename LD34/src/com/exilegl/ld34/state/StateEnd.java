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

public class StateEnd extends State{
	
	private Texture tile;
	
	private float offset;
	
	private float offset2;
	
	private Random random;
	
	Text small;
	Text large;
	Text header;
	
	private OrthographicCamera camera;
	
	public StateEnd(StateManager manager, OrthographicCamera camera) {
		super(manager);
		camera.position.set(new Vector2(Launcher.DEFAULT_WIDTH / 2, Launcher.DEFAULT_HEIGHT / 2), 0);
		camera.update();
		
		tile = new Texture(Gdx.files.internal("assets/texture/tile/water.png"));
		random = new Random();
		
		small = new Text("assets/font/blow.ttf", 22, Color.WHITE, Color.LIGHT_GRAY, 3);
		large = new Text("assets/font/blow.ttf", 22, Color.WHITE, Color.BLACK, 2);
		header = new Text("assets/font/blow.ttf", 64, Color.WHITE, Color.LIGHT_GRAY, 3);
		
		Gdx.input.setCursorPosition(Launcher.DEFAULT_WIDTH / 2, Launcher.DEFAULT_HEIGHT / 2);
		
		this.camera = camera;
	}

	@Override
	public void draw(SpriteBatch batch) {
		for(int layer = 0; layer < Gdx.graphics.getHeight() / tile.getHeight() + tile.getHeight() + offset * tile.getHeight(); layer++){
			for(int row = 0; row < Gdx.graphics.getWidth() / tile.getWidth() + 1; row++){
					batch.draw(tile, row * tile.getWidth(), layer * tile.getHeight() - offset);
			}
		}
		
		if(offset * tile.getHeight() > Launcher.DEFAULT_HEIGHT * Launcher.DEFAULT_HEIGHT / tile.getHeight()){
			offset = 0;
		}
		
		offset = (offset + .5f);
		
		String[] txt = {
				"Congratulations, player.",
				"You have tamed the dungeons",
				"for all humans to return to",
				"for safety in times of danger.",
				"",
				"Humans may now go to the",
				"dungeons to protect themselves",
				"from wars and beasts.",
				"",
				"The beasts fought hard, turned",
				"dungeons into water and lava,",
				"and even made themselves larger,",
				"but their tactics were no match for you."
		};
		
		for(int i = 0; i < txt.length; i++){
			Vector2 position = new Vector2(Launcher.DEFAULT_WIDTH / 2 - txt[i].length() * 4, Launcher.DEFAULT_HEIGHT - i * 22 + offset2);
			small.draw(batch, txt[i], position);
		}
		
		offset2 = offset2 - .4f;
		
		System.out.println(offset2);
		
		if(offset2 < -450){
			this.getManager().setCurrentState(new StateMenu(this.getManager(), this.camera));
		}
	}

	@Override
	public void update() {
		
	}

	@Override
	public void updateAfterDraw() {
		// TODO Auto-generated method stub
		
	}

}