package com.mygdx.game;

import com.badlogic.gdx.Gdx;
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

    // Game difficulty
    public enum Difficulty{EASY, MEDIUM, HARD};

    private Difficulty difficulty;

    // Constants
    private final int WORLD_WIDTH = 72;
    private final int WORLD_HEIGHT = 128;
    private final float MINIMUM_MOVEMENT_THRESHOLD = 0.5f; // Used to reduce unintended movements by decreasing game sensitivity

    // Screen
    private Camera camera;
    private Viewport viewport;

    // Graphics
    private SpriteBatch batch;

    private Texture background, playerShipTexture,
            playerShieldTexture, playerBulletTexture,
            enemyBulletTexture, enemyShipTexture,
            enemyShieldTexture;

    // Object (ships and bullets)
    private Ship playerShip, enemyShip;
    private LinkedList<Bullet> playerBullet, enemyBullet;

    // Game timer
    int backgroundOffset; // Used to scroll along background

    public GameScreen(MyGdxGame game){
        this.game = game;

        // Setup screen settings
        this.camera = new OrthographicCamera();
        this.viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        // Setup textures and batch
        this.background = new Texture("background.png");
        this.playerShipTexture =  new Texture("player_ship.png");
        this.playerShieldTexture =  new Texture("shield1.png");
        this.enemyShipTexture =  new Texture("enemy_a.png");
        this.enemyShieldTexture = new Texture("shield2.png");
        this.playerBulletTexture =  new Texture("laserRed02.png");
        this.enemyBulletTexture =  new Texture("laserBlue02.png");

        this.batch = new SpriteBatch();

        // Start background offset at the bottom of the background image
        this.backgroundOffset = 0;

        // Setup ships
        this.playerShip = new PlayerShip(48, 3, 10, 10,
                WORLD_WIDTH/2, WORLD_HEIGHT/4,
                0.3f, 3, 45, 0.5f,
                playerShipTexture, playerShieldTexture, playerBulletTexture);

        this.enemyShip = new EnemyShip(2, 1, 10, 10,
                WORLD_WIDTH/2, WORLD_HEIGHT * 3/4,
                0.4f, 4, 50, 0.8f,
                enemyShipTexture, enemyShieldTexture, enemyBulletTexture);

        // Setup bullets
        this.playerBullet = new LinkedList<Bullet>();
        this.enemyBullet = new LinkedList<Bullet>();
    }

    public void create(Difficulty difficulty){
        this.difficulty = difficulty;
    }

    @Override
    public void render(float delta) {
        batch.begin();

        // Generate background
        renderBackground(delta);

        // Update ships
        playerShip.update(delta);
        enemyShip.update(delta);

        // Generate ships
        enemyShip.draw(batch);
        playerShip.draw(batch);

        // Generate bullets
        renderBullet(delta);

        // Check for user input
        detectInput(delta);

        // Check if there's been a collision
        detectCollision();

        batch.end();
    }

    private  void detectInput(float delta){
        // Ensure the player ship stays in the screen
        float leftLimit, rightLimit, upLimit, downLimit;

        leftLimit = -playerShip.boundingBox.x;
        downLimit = -playerShip.boundingBox.y;
        rightLimit = WORLD_WIDTH - playerShip.boundingBox.x - playerShip.boundingBox.width;
        upLimit = WORLD_HEIGHT / 2 - playerShip.boundingBox.y - playerShip.boundingBox.height;

        if (Gdx.input.isTouched()){
            // Determine what part of the screen was touched and get the (pixel) coordinates
            float xTouch =  Gdx.input.getX();
            float yTouch = Gdx.input.getY();

            // Convert the pixel coordinates into world coordinates
            Vector2 touchPoint = new Vector2(xTouch, yTouch);
            touchPoint = viewport.unproject(touchPoint);

            // Get the player's ship world coordinates
            Vector2 playerShipCentre = new Vector2(
                    playerShip.boundingBox.x + playerShip.boundingBox.width,
                    playerShip.boundingBox.y + playerShip.boundingBox.height/2
            );

            // Determine the distance between the touch input and the player ship
            float touchDistance = touchPoint.dst(playerShipCentre);

            // Move the ship if it is above the minimum movement threshold
            if (touchDistance > MINIMUM_MOVEMENT_THRESHOLD){
                float xDistance = touchPoint.x - playerShipCentre.x;
                float yDistance = touchPoint.y - playerShipCentre.y;

                float xMove = xDistance / touchDistance * playerShip.movementSpeed * delta;
                float yMove = yDistance / touchDistance * playerShip.movementSpeed * delta;

                // Makes sure ship doesn't move off screen
                if (xMove > 0) xMove = Math.min(xMove, rightLimit);
                else xMove = Math.max(xMove, leftLimit);

                if (yMove > 0) yMove = Math.min(yMove, upLimit);
                else yMove = Math.max(yMove, downLimit);

                // Update player ship position
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
        // Scroll up on the background image
        backgroundOffset ++;

        // Reset background offset once the second background is completely in view.
        if(backgroundOffset % (WORLD_HEIGHT * 2) == 0){
            backgroundOffset = 0;
        }

        // Generate two backgrounds images. The first one starts in view and scrolls offscreen.
        // The second image is generated offscreen but scrolls into view.
        // Background offset is divided by two to slow scrolling speed.
        batch.draw(background, 0 , -(backgroundOffset/2), WORLD_WIDTH, WORLD_HEIGHT);
        batch.draw(background, 0 , -(backgroundOffset/2) + WORLD_HEIGHT, WORLD_WIDTH, WORLD_HEIGHT);

    }

    @Override
    public void resize(int width, int height) {
        // Resize the background image to fit the dimension of the device
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
