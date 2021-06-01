package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen implements Screen {
    MyGdxGame game;

    public enum Difficulty{EASY, MEDIUM, HARD};

    // Game Setting
    private Difficulty difficulty;

    // World parameters
    private final int WORLD_WIDTH = 72; // 72
    private final int WORLD_HEIGHT = 128; //128

    // Screen
    private Camera camera;
    private Viewport viewport;

    // Graphic
    private SpriteBatch batch;
    private Texture background;

    private Texture playerShipTexture, playerShieldTexture, playerLaserTexture,
            enemyLaserTexture, enemyShipTexture, enemyShieldTexture;

    // Game Object
    private Ship playerShip;
    private Ship enemyShip;

    // Timing
    int backgroundOffset; // moves background

    public GameScreen(MyGdxGame game){
        this.game = game;

        this.camera = new OrthographicCamera();
        this.viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        this.background = new Texture("bg.png");
        this.playerShipTexture =  new Texture("P-yellow-a.png");
        this.playerShieldTexture =  new Texture("shield1.png");
        this.playerLaserTexture =  new Texture("laserRed02.png");
        this.enemyShipTexture =  new Texture("Enemy2b.png");
        this.enemyLaserTexture =  new Texture("laserBlue02.png");
        this.enemyShieldTexture = new Texture("shield2.png");

        this.backgroundOffset = 0;

        this.playerShip = new Ship(2, 3, 10, 10,
                WORLD_WIDTH/2, WORLD_HEIGHT/4,
                playerShipTexture, playerShieldTexture);

        this.enemyShip = new Ship(2, 1, 10, 10,
                WORLD_WIDTH/2, WORLD_HEIGHT * 3/4,
                enemyShipTexture, enemyShieldTexture);


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
        if(backgroundOffset % (WORLD_HEIGHT * 2) == 0){
            backgroundOffset = 0;
        }

        // Initial background in view
        batch.draw(background, 0 , -(backgroundOffset/2), WORLD_WIDTH, WORLD_HEIGHT);
        // Offscreen background that will come in view
        batch.draw(background, 0 , -(backgroundOffset/2) + WORLD_HEIGHT, WORLD_WIDTH, WORLD_HEIGHT);

        // Enemy Ship
        enemyShip.draw(batch);

        // Player Ship
        playerShip.draw(batch);

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
