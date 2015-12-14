package com.exilegl.ld34.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.exilegl.ld34.animation.Animation;
import com.exilegl.ld34.animation.EntityAnimation;
import com.exilegl.ld34.animation.Frame;
import com.exilegl.ld34.entity.Entity;
import com.exilegl.ld34.map.Map;
import com.exilegl.ld34.state.State;
import com.exilegl.ld34.text.Text;

public class Button extends Entity{

	private State state;
	
	private boolean hovering;
	
	private OrthographicCamera camera;
	
	private String buttonText;
	private Text text;
	
	private int size = 64;
	
	private int level;
	
	public Button(String buttonText, Vector2 location, State state, OrthographicCamera camera, int level) {
		super(null, location, 0, 0, false);
		this.setSingleAnimation(new Animation());
		this.getSingleAnimation().addFrame(new Frame(new TextureRegion(new Texture(Gdx.files.internal("assets/texture/button/button.png"))), 1));
		this.camera = camera;
		this.buttonText = buttonText;
		this.state = state;
		this.level = level;
		
		text = new Text("assets/font/blow.ttf", size, Color.WHITE, Color.LIGHT_GRAY, 3);
	}

	@Override
	public void update() {
		Vector3 m = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		this.camera.unproject(m);
		
		Rectangle mouse = new Rectangle(m.x, m.y, 1, 1);
		Rectangle button = new Rectangle(this.getLocation().x, this.getLocation().y + 200, this.getSingleAnimation().getWidth(), this.getSingleAnimation().getHeight());
	
		if(mouse.overlaps(button)){
			this.hovering = true;
		}else{
			this.hovering = false;
		}
		if(this.isHovering() && Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
			this.getState().getManager().setCurrentState(this.getState());
		}
	}

	@Override
	public void create(Map map) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void draw(SpriteBatch batch){
		Animation animation = (this.getSingleAnimation());
		Sprite sprite = new Sprite(animation.getCurrentFrame().getTexture());
		sprite.setPosition(this.getLocation().x, this.getLocation().y + 200);
		sprite.setSize(animation.getWidth(), animation.getHeight());
		if(this.isHovering()){
		sprite.setRotation(30);
		}
		sprite.draw(batch);
		
		float y = (this.getLocation().y + this.getSingleAnimation().getHeight() / 1.5f);
		text.draw(batch, level - level / 5+ "", new Vector2(this.getLocation().x + this.getSingleAnimation().getWidth() / 2 - this.buttonText.length() * size / 4, y + 200));
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public boolean isHovering() {
		return hovering;
	}

	public void setHovering(boolean hovering) {
		this.hovering = hovering;
	}

}
