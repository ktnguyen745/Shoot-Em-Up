package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen implements Screen {
    MyGdxGame game;

    public enum Difficulty{EASY, MEDIUM, HARD};

    // Game Setting
    private Difficulty difficulty;

    // Screen
    private Camera camera;
    private Viewport viewport;

    // Graphic
    private SpriteBatch batch;
    private Texture background;

    // Timing
    int backgroundOffset; // moves background

    // World parameters
    private final int WORLD_WIDTH = 72; // 72
    private final int WORLD_HEIGHT = 128; //128

    public GameScreen(MyGdxGame game){
        this.game = game;

        this.camera = new OrthographicCamera();
        this.viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        this.background = new Texture("bg.png");
        this.backgroundOffset = 0;

        this.batch = new SpriteBatch();
    }

    public void create(Difficulty difficulty){
        this.difficulty = difficulty;
    }

    @Override
    public void render(float delta) {
        batch.begin();

        // Scrolling component
        backgroundOffset ++;

        // If background offset gets bigger than the screen, reset to 0
        if(backgroundOffset % WORLD_HEIGHT == 0){
            backgroundOffset = 0;
        }

        // Initial background in view
        batch.draw(background, 0 , -backgroundOffset, WORLD_WIDTH, WORLD_HEIGHT);
        // Offscreen background that will come in view
        batch.draw(background, 0 , -backgroundOffset + WORLD_HEIGHT, WORLD_WIDTH, WORLD_HEIGHT);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        batch.setProjectionMatrix(camera.combined);
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
    public void show() {


    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
