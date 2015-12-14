package com.exilegl.ld34.sound;

import com.badlogic.gdx.Gdx;

public enum Sound {

	Coin("coin.wav"), Shoot("shoot.wav"), Kill_Enemy("killenemy.wav"), Key_Collect("keycollect.wav"),
	Death("death.wav"), Enemy_Shoot("enemyshoot.wav")
	
	;
	
	Sound(String path){
		this.SOUND = Gdx.audio.newSound(Gdx.files.internal("assets/audio/" + path));
	}
	
	private final com.badlogic.gdx.audio.Sound SOUND;
	
	public static void play(Sound sound){
		sound.SOUND.play();
	}
	
	public static void play(Sound sound, float volume){
		sound.SOUND.play(volume);
	}
}
