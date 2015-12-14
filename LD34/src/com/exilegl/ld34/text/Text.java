package com.exilegl.ld34.text;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Text {
	
	private BitmapFont font;
	
	public Text(String path, int size){
		this.setFont(TextType.generateBitmapFont(path, size));
	}
	
	public Text(String path, int size, Color textColor, Color shadowColor, int shadowOffset){
		this.setFont(TextType.generateBitmapFont(path, size, shadowOffset, textColor, shadowColor));
	}
	
	public void draw(SpriteBatch batch, String text, Vector2 position){
		this.getFont().draw(batch, text, position.x, position.y);
	}
	
	public BitmapFont getFont() {
		return font;
	}

	public void setFont(BitmapFont font) {
		this.font = font;
	}
	
	
}
