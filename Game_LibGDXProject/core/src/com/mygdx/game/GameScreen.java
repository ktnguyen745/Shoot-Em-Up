package com.mygdx.game;

import com.badlogic.gdx.Screen;

public class GameScreen implements Screen {

    MyGdxGame game;

    public enum Difficulty{EASY, MEDIUM, HARD};

    private Difficulty difficulty;

    public GameScreen(MyGdxGame game){
        this.game = game;
    }

    public void create(Difficulty difficulty){
        this.difficulty = difficulty;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
