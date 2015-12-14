package com.exilegl.ld34.text;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public enum TextType {

	;
	
	public static BitmapFont generateBitmapFont(String path, int size){
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(path));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		
		parameter.size = size;
		
		BitmapFont font = generator.generateFont(parameter);
		generator.dispose();
		
		return font;
	}
	
	public static BitmapFont generateBitmapFont(String path, int size, int shadowOffset, Color textColor, Color shadowColor){
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(path));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		
		parameter.size = size;
		parameter.color = textColor;
		parameter.shadowColor = shadowColor;
		parameter.shadowOffsetX = shadowOffset;
		parameter.shadowOffsetY = shadowOffset;
		
		BitmapFont font = generator.generateFont(parameter);
		generator.dispose();
		
		return font;
	}
	
}