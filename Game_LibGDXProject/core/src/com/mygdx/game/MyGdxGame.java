package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends Game {
	public MenuScreen menu;
	public GameScreen game;
	
	@Override
	public void create () {
		menu = new MenuScreen(this);
		game = new GameScreen(this);
		menu.show();
		setScreen(menu);
	}

	@Override
	public void render () {
		this.getScreen().render(Gdx.graphics.getDeltaTime());
	}
	
	@Override
	public void dispose () {

	}
}
