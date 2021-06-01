package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.LinkedList;
import java.util.ListIterator;

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

    private Texture playerShipTexture, playerShieldTexture, playerBulletTexture,
            enemyBulletTexture, enemyShipTexture, enemyShieldTexture;

    // Game Object
    private Ship playerShip;
    private Ship enemyShip;
    private LinkedList<Bullet> playerBullet, enemyBullet;

    // Timing
    int backgroundOffset; // moves background

    public GameScreen(MyGdxGame game){
        this.game = game;

        this.camera = new OrthographicCamera();
        this.viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        this.background = new Texture("bg.png");
        this.playerShipTexture =  new Texture("P-yellow-a.png");
        this.playerShieldTexture =  new Texture("shield1.png");
        this.enemyShipTexture =  new Texture("Enemy2b.png");
        this.enemyShieldTexture = new Texture("shield2.png");

        this.playerBulletTexture =  new Texture("laserRed02.png");
        this.enemyBulletTexture =  new Texture("laserBlue02.png");

        this.backgroundOffset = 0;

        this.playerShip = new PlayerShip(2, 3, 10, 10,
                WORLD_WIDTH/2, WORLD_HEIGHT/4,
                0.3f, 3, 45, 0.5f,
                playerShipTexture, playerShieldTexture, playerBulletTexture);

        this.enemyShip = new EnemyShip(2, 1, 10, 10,
                WORLD_WIDTH/2, WORLD_HEIGHT * 3/4,
                0.4f, 4, 50, 0.8f,
                enemyShipTexture, enemyShieldTexture, enemyBulletTexture);

        this.playerBullet = new LinkedList<Bullet>();
        this.enemyBullet = new LinkedList<Bullet>();

        this.batch = new SpriteBatch();
    }

    public void create(Difficulty difficulty){
        this.difficulty = difficulty;
    }

    @Override
    public void render(float delta) {
        batch.begin();

        playerShip.update(delta);
        enemyShip.update(delta);

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

        // Laser
        if(playerShip.canFireBullet()){
            Bullet[] pBullet = playerShip.shootBullet();
            for (Bullet bullet : pBullet){
                playerBullet.add(bullet);
            }
        }

        if(enemyShip.canFireBullet()){
            Bullet[] eBullet = enemyShip.shootBullet();
            for (Bullet bullet : eBullet){
                enemyBullet.add(bullet);
            }
        }

        ListIterator<Bullet> iterator = playerBullet.listIterator();
        while(iterator.hasNext()){
            Bullet bullet = iterator.next();
            bullet.draw(batch);
            bullet.yPosition += bullet.movementSpeed * delta;
            if(bullet.yPosition > WORLD_HEIGHT){
                iterator.remove();
            }
        }

        iterator = enemyBullet.listIterator();
        while(iterator.hasNext()){
            Bullet bullet = iterator.next();
            bullet.draw(batch);
            bullet.yPosition -= bullet.movementSpeed * delta;
            if(bullet.yPosition + bullet.height < 0){
                iterator.remove();
            }
        }

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
