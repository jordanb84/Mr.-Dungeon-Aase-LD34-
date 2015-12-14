package com.exilegl.ld34.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Launcher {

	public static final int DEFAULT_WIDTH = 896;
	public static final int DEFAULT_HEIGHT = 736;
	
	public static void main(String args[]){
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = DEFAULT_WIDTH;
		config.height = DEFAULT_HEIGHT;
		config.vSyncEnabled = false;
		config.foregroundFPS = 60;
		config.backgroundFPS = 60;
		config.resizable = false;
		config.title = ("Mr. Dungeon Aase - Ludum Dare 34 - 'Two button controls' and 'It grows'");
		
		new LwjglApplication(new Game(), config);
	}
	
	/**
	 * 1: Almost every tile
	 * is lava, you must go along a small
	 * path and try not to be killed by the lava.
	 * Along the path, you can find a certain non moving enemy,
	 * a lava beast.
	 * 
	 * 2: Lake of Water. Much like the lake of fire map,
	 * but with a water beast which you must push into the
	 * water to kill. You cannot shoot them.
	 * 
	 * 3: Map consisting of purple wall tiles
	 *    which not only move, but will kill you if
	 *    you touch them
	 * 
	 * 4: The last level? If it is, I must do what
	 * I've mentioned below, but then what will I do for
	 * the next ~5ish hours?
	 * 
	 * I'll make it the last level, then when I'm done
	 * with it I will look at the remaining time and decide what to do
	 * 
	 * "The hostiles have turned the dungeons back into lakes!
	 * To tame the land for your kind, you must destroy
	 * the last of the hostiles. Here they will be tough."
	 * 
	 * Add large water enemy which acts like the other
	 * water enemy, and a large zombie which walks a great distance
	 * and shoots blue bullets fast gentleman
	 * 
	 * Once the last level is beat, have
	 * a state displayed which is scrolling
	 * tiles much like the menu, but water instead,
	 * and with scrolling text explaining how you've
	 * tamed the dungeons, turned the lake of fire into
	 * safe water, and tamed the water lake for humans
	 * to move into.
	 * 
	 * Note somewhere on the main menu and in the LD post
	 * that there is a storyline mostly displayed by the last level.
	 */
	
	/**
	 * Fixed:
	 * 
	 * -Bullets work much better now,
	 * before they were getting stuck in walls
	 * (including invisible walls) and were pretty
	 * buggy but player bullets no longer get stuck with walls
	 * 
	 * -Bullets grow the longer you hold your mouse up to 2.5x
	 * 
	 * -Mouse is grabbed and replaced with ingame cursor
	 * 
	 * TODO
	 * 
	 * -Add more levels:
	 * 
	 * -Level where you must shoot multiple enemies at once
	 * using your scaled up bullets, also has enemies which are extremely
	 * slow but shoot dangerously fast so you can't pass them without killing them [x]
	 * 
	 * -Level where almost every wall is a shooting/spawning wall
	 * and there is a new entity which moves all over including the y axis
	 * 
	 * -Level
	 * 
	 * -Level
	 * 
	 * -Level
	 * 
	 * Flame shoots flames like the snowman,
	 * and turns tiles under it into lava pits
	 */
	
	/**
	 * TODO
	 * 
	 * Introduce traps somewhere. Maybe I don't have to
	 * since level 2 somewhat introduces them at the beginning?
	 * 
	 * Make bullets not get stuck inside the player when
	 * shot too close, maybe by having them shot at an offset
	 * away from the player
	 */
	
	/**
	 * TODO
	 * 
	 * You have a "power", which allows you to
	 * shoot a large bullet once in every level.
	 * Each level, this bullet is larger.
	 * 
	 * Or
	 * 
	 * There is a "boss" type of
	 * enemy at the end of each level. On each
	 * level, it is larger than the previous.
	 * 
	 * This will incorporate the "It Grows" theme
	 */
	
	/**
	 * TODO
	 * 
	 * Enemy with shooting tiles all around it (x)
	 * 
	 * Enemy like odd zombie but places moving/gravity
	 * tiles which will kill you
	 */
	
	/**
	 * TODO
	 * 
	 * Tiles you must shoot to spawn an enemy
	 * and get the coin from said enemy (x)
	 * 
	 * Pushable tiles (x)
	 * 
	 * Wall tiles that slowly move by gravity so you must be quick
	 * 
	 * Wall tiles that shoot at you, making certain rooms non enterable:
	 * You must go around outside of the map to enter [x, but not making rooms not enterable]
	 */
	
	/**
	 * Each enemy drops one coin.
	 * 
	 * You have a bar for the amount of coins
	 * you have in the top center.
	 * 
	 * You must collect a certain number of coins
	 * in each level to win the level; each level differs
	 * in required coins.
	 * 
	 * Once you collect all coins, all solid map tiles turn into floors
	 * and you must collect the key which spawns randomly in the map
	 * with enemies guarding it.
	 * 
	 * I should add much more content and maybe another
	 * mechanic as I go.
	 * 
	 * TODO animate player near the end
	 * 
	 * TODO Add trap floors. Trap floors are the wood texture
	 * but somewhat darker. If you step on one, you die and the
	 * text "You've been trapped!" is displayed on the screen
	 * 
	 * TODO Make zombie speed random within a small range so they don't move at
	 * the exact same x at the same time, looks odd
	 */
	
}
