package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class MyGdxGame extends Game {
	public MenuScreen menu;
	public GameScreen game;

	// random number generator
	public static Random random = new Random();
	
	@Override
	public void create () {
		menu = new MenuScreen(this);
		game = new GameScreen(this, GameScreen.Difficulty.EASY);
		menu.show();

		// TODO: Changed to game to make dev easier. Change back to menu for final rendition.
		setScreen(game);
	}

	@Override
	public void render () {
		this.getScreen().render(Gdx.graphics.getDeltaTime());
	}
	
	@Override
	public void dispose () {

	}

	public void showMenu(){
		setScreen(menu);
	}
}
