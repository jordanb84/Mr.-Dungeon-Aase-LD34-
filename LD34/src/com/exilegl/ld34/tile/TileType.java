package com.exilegl.ld34.tile;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.exilegl.ld34.animation.Animation;
import com.exilegl.ld34.animation.Frame;
import com.exilegl.ld34.entity.EntityDirection;

interface TileTextureLoader{
	static Texture SHEET = new Texture("assets/texture/tile/sheet.png");
}

public enum TileType implements TileTextureLoader{
	Floor("wood.png", false), Brick("brick.png", true), Brick2("brick2.png", true),
	Wall("wall.png", true), Furnace("furnace.png", true, Color.RED, true), FurnaceOrange("furnaceOrange.png", true, Color.GOLDENROD, true),
	SpawnerZombie("wood.png", false), FloorTrap("woodtrap.png", false),
	Lava("lava.png", false, Color.ORANGE, true), Water("water.png", true, Color.BLUE, true),
	
	Text_Welcome("wood.png", false, "Welcome, Mr. Aase!"),
	Text_Welcome2("wood.png", false, "Hold left click to shoot red bullets"),
	Text_Welcome3("wood.png", false, "Zombies shoot green bullets which will kill you!"),
	Text_Welcome4("wood.png", false, "Shoot the zombie with your bullets to kill it"),
	Text_Welcome5("wood.png", false, "at your mouse's location and grow your bullet's size"),
	Text_Welcome6("wood.png", false, "You move according to the mouse's position"),
	Text_Welcome7("wood.png", false, "Enemies drop coins which you must pick up"),
	Text_Welcome8("wood.png", false, "This is a creep. They shoot fast!"),
	Text_Welcome9("wood.png", false, "This warped looking zombie is the"),
	Text_Welcome10("wood.png", false, "dungeon's owner. As you progress,"),
	Text_Welcome11("wood.png", false, "he will grow larger and more powerful"),
	Text_Odd("wood.png", false, "This is an odd zombie, which turns tiles"),
	Text_Odd2("wood.png", false, "under it into traps. You cannot kill it,"),
	Text_Odd3("wood.png", false, "so your best bet would be to navigate around."),
	Text_Bloody("wood.png", false, "What is this?! It seems to be a zombie"),
	Text_Bloody2("wood.png", false, "version of you! Destroy it!"),
	Text_Bloody3("wood.png", false, "It's bullets are ranged, be careful!"),
	Text_Evil("wood.png", false, "It's a tile gone bad, how shocking!"),
	Text_Evil2("wood.png", false, "You must shoot it twice to spawn a zombie"),
	Text_Evil3("wood.png", false, "Hehe, this will only respawn itself. Get around it!"),
	Text_Pushable("wood.png", false, "What's this? Green glowing tiles"),
	Text_Pushable2("wood.png", false, "can be pushed into enemies to kill them!"),
	Text_Easy("wood.png", false, "That was easy!"),
	Text_Easy2("wood.png", false, "Evil tiles can't be destroyed by green tiles"),
	Text_ShootingWall("wood.png", false, "This wall will try to kill you, shoot it!"),
	Text_Gravity("wood.png", false, "The walls will close in, allowing bullets"),
	Text_Gravity2("wood.png", false, "from other rooms to hit you. Be careful!"),
	Text_DeathTile("wood.png", false, "Since when do furnaces move?!"),
	Text_DeathTile2("wood.png", false, "It seems as if certain furnaces have"),
	Text_DeathTile3("wood.png", false, "become evil and will kill you, avoid them!"),
	Text_Madman("wood.png", false, "What?! This thing is mad! It has developed"),
	Text_Madman2("wood.png", false, "the ability to shoot BOTH ways, and FAST!"),
	Text_Madman3("wood.png", false, "Try to kill it without getting too close!"),
	Text_Demon("wood.png", false, "It's some sort of a demon, and it"),
	Text_Demon2("wood.png", false, "can move up and down! Maybe it would"),
	Text_Demon3("wood.png", false, "be easier to shoot when further away?"),
	Text_Snow("wood.png", false, "Snow monsters in the middle of a"),
	Text_Snow2("wood.png", false, "furnace filled dungeon? I suppose"),
	Text_Snow3("wood.png", false, "nothing is too odd for this place..."),
	Text_Lava("wood.png", false, "It's a trap! Avoid the orange bricks,"),
	Text_Lava2("wood.png", false, "they're burning hot! Escape to the left"),
	Text_Lava3("wood.png", false, "and find your way to the next room!"),
	Text_Fire("wood.png", false, "This is know as the 'Lake of Fire',"),
	Text_Fire2("wood.png", false, "tread carefully. Lava will kill you, and"),
	Text_Fire3("wood.png", false, "it is known that fire beasts guard the area"),
	Text_Water("wood.png", false, "Now that you have calmed the lake of fire,"),
	Text_Water2("wood.png", false, "water flows in the lake once again. You must now"),
	Text_Water3("wood.png", false, "tame the water by killing the hostile water beasts!"),
	Text_Purple("wood.png", false, "As revenge for taming the lava and water lakes,"),
	Text_Purple2("wood.png", false, "the hostiles have turned the dungeon's walls"),
	Text_Purple3("wood.png", false, "into dangerous purple! Shoot the purple tiles"),
	Text_Purple4("wood.png", false, "to remove them from your path!"),
	Text_End("wood.png", false, "The hostiles have turned the dungeons back"),
	Text_End2("wood.png", false, "into lakes! To tame the land once and for all,"),
	Text_End3("wood.png", false, "you must destroy the last of the hostiles."),
	
	SpawnerCreep("wood.png", false),
	SpawnerOddZombie("wood.png", false),
	SpawnerBloodyHuman("wood.png", false),
	SpawnerEvilTile_Zombie("wood.png", false),
	SpawnerEvilTile_Creep("wood.png", false),
	SpawnerEvilTile_OddZombie("wood.png", false),
	SpawnerEvilTile_EvilTile("wood.png", false),
	SpawnerPushableTile("wood.png", false),
	SpawnerShootingWall("wood.png", false),
	SpawnerDeathTile("wood.png", false),
	SpawnerBoss("wood.png", false),
	SpawnerMadMan("wood.png", false),
	SpawnerYZombie("wood.png", false),
	SpawnerSnowmonster("wood.png", false),
	SpawnerFlame("wood.png", false),
	SpawnerFireGuard("wood.png", false),
	SpawnerWaterGuard("wood.png", false),
	SpawnerPurple("wood.png", false),
	SpawnerWaterBeast("wood.png", false),
	
	Wall_Gravity("wall.png", true, true),
	Brick_Gravity("brick.png", true, true),
	Brick2_Gravity("brick2.png", true, true)
	
	//"What's this? Green glowing tiles can be pushed into enemies to kill them!"
	;
	
	TileType(int x, int y, int width, int height, Color color, float lightDistance, boolean solid){
		this.LIGHT_COLOR = color;
		this.LIGHT_DISTANCE = lightDistance;
		this.SOLID = solid;
		
		this.ANIMATION = new Animation();
		this.ANIMATION.addFrame(new Frame(new TextureRegion(SHEET, x, y, width, height), 1));
	}
	
	TileType(String path, boolean solid){
		this.SOLID = solid;
		
		this.ANIMATION = new Animation();
		this.ANIMATION.addFrame(new Frame(new TextureRegion(new Texture("assets/texture/tile/" + path)), 1));
	}
	
	TileType(String path, boolean solid, boolean gravity){
		this.SOLID = solid;
		this.GRAVITY = gravity;
		System.out.println("Has gravity");
		
		this.ANIMATION = new Animation();
		this.ANIMATION.addFrame(new Frame(new TextureRegion(new Texture("assets/texture/tile/" + path)), 1));
	}
	
	TileType(String path, boolean solid, String text){
		this.SOLID = solid;
		
		this.HAS_TEXT = true;
		this.TEXT = text;
		
		this.ANIMATION = new Animation();
		this.ANIMATION.addFrame(new Frame(new TextureRegion(new Texture("assets/texture/tile/" + path)), 1));
	}
	
	TileType(String path, boolean solid, Color color, boolean flicker){
		this.SOLID = solid;
		
		this.LIGHT_COLOR = color;
		this.LIGHTED = true;
		
		this.FLICKER = flicker;
		
		this.ANIMATION = new Animation();
		this.ANIMATION.addFrame(new Frame(new TextureRegion(new Texture("assets/texture/tile/" + path)), 1));
		
		this.LIGHT_DISTANCE = (this.ANIMATION.getCurrentFrame().getTexture().getRegionWidth() * 2);
	}
	
	TileType(String path, boolean solid, Color color, boolean flicker, int gravity){
		this.SOLID = solid;
		
		this.LIGHT_COLOR = color;
		this.LIGHTED = true;
		
		this.GRAVITY = true;
		
		this.FLICKER = flicker;
		
		this.ANIMATION = new Animation();
		this.ANIMATION.addFrame(new Frame(new TextureRegion(new Texture("assets/texture/tile/" + path)), 1));
		
		this.LIGHT_DISTANCE = (this.ANIMATION.getCurrentFrame().getTexture().getRegionWidth() * 2);
	}
	
	TileType(String path, boolean solid, EntityDirection offsetDirection){
		this.SOLID = solid;
		this.OFFSET = true;
		this.OFFSET_DIRECTION = offsetDirection;
		
		this.ANIMATION = new Animation();
		this.ANIMATION.addFrame(new Frame(new TextureRegion(new Texture("assets/texture/tile/" + path)), 1));
	}
	
	public boolean GRAVITY;
	public Color LIGHT_COLOR;
	public String TEXT;
	public boolean HAS_TEXT;
	public boolean LIGHTED;
	public boolean FLICKER;
	public final Animation ANIMATION;
	public float LIGHT_DISTANCE;
	public final boolean SOLID;
	public boolean OFFSET;
	public EntityDirection OFFSET_DIRECTION;
	
	//All tile types in order of map ID
	public static final TileType[] TYPES = {Floor, Brick, Brick2, Wall, Furnace, FurnaceOrange, SpawnerZombie, Text_Welcome, Text_Welcome2, Text_Welcome3, Text_Welcome4, Text_Welcome5, Text_Welcome6, Text_Welcome7, SpawnerCreep, Text_Welcome8, Text_Welcome9, Text_Welcome10, Text_Welcome11, FloorTrap,
			SpawnerOddZombie, Text_Odd, Text_Odd2, Text_Odd3, SpawnerBloodyHuman, Text_Bloody, Text_Bloody2, Text_Bloody3,
			Text_Evil, Text_Evil2, SpawnerEvilTile_Zombie, SpawnerEvilTile_Creep, SpawnerEvilTile_OddZombie, SpawnerEvilTile_EvilTile, Text_Evil3,
			SpawnerPushableTile, Text_Pushable, Text_Pushable2, Text_Easy, Text_Easy2, SpawnerShootingWall, Text_ShootingWall,
			Wall_Gravity, Brick_Gravity, Brick2_Gravity, Text_Gravity, Text_Gravity2, SpawnerDeathTile,
			Text_DeathTile, Text_DeathTile2, Text_DeathTile3, SpawnerBoss, SpawnerMadMan,
			Text_Madman, Text_Madman2, Text_Madman3, SpawnerYZombie, Text_Demon, Text_Demon2, Text_Demon3, SpawnerSnowmonster,
			Text_Snow, Text_Snow2, Text_Snow3, SpawnerFlame, Text_Lava, Text_Lava2, Text_Lava3, SpawnerFireGuard, Lava,
			Text_Fire, Text_Fire2, Text_Fire3, Water, Text_Water, Text_Water2, Text_Water3, SpawnerWaterGuard, SpawnerPurple,
			Text_Purple, Text_Purple2, Text_Purple3, Text_Purple4, SpawnerWaterBeast, Text_End, Text_End2, Text_End3};
	
}