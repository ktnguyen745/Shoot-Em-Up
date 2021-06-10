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
	public enum gameState{MENU, WIN, LOSE}
	private gameState state;

	public int score = 0;

	// random number generator
	public static Random random = new Random();
	
	@Override
	public void create () {
		state = gameState.MENU;
		menu = new MenuScreen(this);
		game = new GameScreen(this, GameScreen.Difficulty.EASY);
		menu.show();

		// TODO: Changed to game to make dev easier. Change back to menu for final rendition.
		setScreen(menu);
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

	public void setState(gameState state) {
		this.state = state;
	}

	public gameState getState() {
		return this.state;
	}
}
