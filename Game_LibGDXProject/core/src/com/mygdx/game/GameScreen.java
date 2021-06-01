package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
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
    private final float MOVEMENT_THRESHOLD = 0.5f;

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

        this.background = new Texture("background.png");
        this.playerShipTexture =  new Texture("player_ship.png");
        this.playerShieldTexture =  new Texture("shield1.png");
        this.enemyShipTexture =  new Texture("enemy_a.png");
        this.enemyShieldTexture = new Texture("shield2.png");

        this.playerBulletTexture =  new Texture("laserRed02.png");
        this.enemyBulletTexture =  new Texture("laserBlue02.png");

        this.backgroundOffset = 0;

        this.playerShip = new PlayerShip(48, 3, 10, 10,
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

        renderBackground(delta);

        // Enemy Ship
        enemyShip.draw(batch);

        // Player Ship
        playerShip.draw(batch);

        // Laser
        renderBullet(delta);

        detectInput(delta);

        detectCollision();

        batch.end();
    }

    private  void detectInput(float delta){
        float leftLimit, rightLimit, upLimit, downLimit;

        leftLimit = -playerShip.boundingBox.x;
        downLimit = -playerShip.boundingBox.y;
        rightLimit = WORLD_WIDTH - playerShip.boundingBox.x - playerShip.boundingBox.width;
        upLimit = WORLD_HEIGHT / 2 - playerShip.boundingBox.y - playerShip.boundingBox.height;

        if (Gdx.input.isTouched()){
            // Get screen position
            float xTouch =  Gdx.input.getX(); // pixel coord
            float yTouch = Gdx.input.getY();

            // Convert to world coord
            Vector2 touchPoint = new Vector2(xTouch, yTouch);
            touchPoint = viewport.unproject(touchPoint);

            Vector2 playerShipCentre = new Vector2(
                    playerShip.boundingBox.x + playerShip.boundingBox.width,
                    playerShip.boundingBox.y + playerShip.boundingBox.height/2
            );

            float touchDistance = touchPoint.dst(playerShipCentre);

            // Prevents the ship if the touch is very close to the ship. Makes game more playable and prevents unintended minor movements
            if (touchDistance > MOVEMENT_THRESHOLD){
                float xDistance = touchPoint.x - playerShipCentre.x;
                float yDistance = touchPoint.y - playerShipCentre.y;

                float xMove = xDistance / touchDistance * playerShip.movementSpeed * delta;
                float yMove = yDistance / touchDistance * playerShip.movementSpeed * delta;

                // Makes sure ship stays in screen
                if (xMove > 0) xMove = Math.min(xMove, rightLimit);
                else xMove = Math.max(xMove, leftLimit);

                if (yMove > 0) yMove = Math.min(yMove, upLimit);
                else yMove = Math.max(yMove, downLimit);

                playerShip.translate(xMove,yMove);
            }
        }
    }

    private void detectCollision(){
        ListIterator<Bullet> iterator = playerBullet.listIterator();
        while(iterator.hasNext()) {
            Bullet bullet = iterator.next();
            if(enemyShip.intersects(bullet.boundingBox)){
                enemyShip.hit(bullet);
                iterator.remove(); // removes last item
            }
        }

        iterator = enemyBullet.listIterator();
        while(iterator.hasNext()) {
            Bullet bullet = iterator.next();
            if(playerShip.intersects(bullet.boundingBox)){
                iterator.remove(); // removes last item
            }
        }
    }

    private void renderBullet(float delta){
        if(playerShip.canFireBullet()){
            Bullet[] pBullet = playerShip.shootBullet();
            for (Bullet bullet : pBullet){
                playerShip.hit(bullet);
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
            bullet.boundingBox.y += bullet.movementSpeed * delta;
            if(bullet.boundingBox.y > WORLD_HEIGHT){
                iterator.remove();
            }
        }

        iterator = enemyBullet.listIterator();
        while(iterator.hasNext()){
            Bullet bullet = iterator.next();
            bullet.draw(batch);
            bullet.boundingBox.y -= bullet.movementSpeed * delta;
            if(bullet.boundingBox.y + bullet.boundingBox.height < 0){
                iterator.remove();
            }
        }
    }

    private void renderBackground(float delta){
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
