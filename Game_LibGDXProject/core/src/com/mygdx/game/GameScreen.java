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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

public class GameScreen implements Screen {
    MyGdxGame game;

    // Game difficulty
    public enum Difficulty{EASY, MEDIUM, HARD}
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
    private Texture background;

    // Object (ships and bullets)
    private Ship playerShip;
    private ArrayList<EnemyShip> enemyShips;

    // Game timer
    int backgroundOffset; // Used to scroll along background

    public GameScreen(MyGdxGame game){
        this.game = game;

        // Setup screen settings
        this.camera = new OrthographicCamera();
        this.viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        // Setup textures and batch
        this.background = new Texture(Gdx.files.internal("background.png"));
        this.batch = new SpriteBatch();

        // Start background offset at the bottom of the background image
        this.backgroundOffset = 0;

        // Setup ships
        this.playerShip = new PlayerShip(48, 3, 10, 10,
                (float) WORLD_WIDTH/2, (float)WORLD_HEIGHT/4,
                0.5f, "player_ship.png", "shield1.png");

        EnemyShip enemyShip = new EnemyShip(48, 1, 10, 10,
                MyGdxGame.random.nextFloat() * (WORLD_WIDTH - 10) + 5, WORLD_HEIGHT -5,
                0.8f, "enemy_a.png", "shield2.png");

        EnemyTripleshot enemyTripleshot = new EnemyTripleshot(2, 2, 15, 11,
                (float) (WORLD_WIDTH / 4) * 3, (float) WORLD_HEIGHT * 3/4,
                1.5f, "enemy_b.png", "shield2.png");

        enemyShips = new ArrayList<EnemyShip>();
        enemyShips.add(enemyShip);
        enemyShips.add(enemyTripleshot);
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
        for(Ship enemy : enemyShips){
            enemy.update(delta);
        }

        // Check for collisions
        for(Ship enemy : enemyShips){
            playerShip.collisionCheck(enemy);
            enemy.collisionCheck(playerShip);
        }

        // Render ships
        playerShip.draw(batch);
        for(Ship enemy : enemyShips){
            enemy.draw(batch);
        }

        // Check for user input
        detectInput(delta);

        // Enemy movement
        moveEnemies(delta);

        batch.end();
    }

    private void moveEnemies(float delta) {
        float leftLimit, rightLimit, upLimit, downLimit;
        // TODO: implement for multiple enemies
        leftLimit = -enemyShips.get(0).boundingBox.x;
        downLimit = (float) WORLD_HEIGHT / 2 - enemyShips.get(0).boundingBox.y;
        rightLimit = WORLD_WIDTH - enemyShips.get(0).boundingBox.x - enemyShips.get(0).boundingBox.width;
        upLimit = WORLD_HEIGHT - enemyShips.get(0).boundingBox.y - enemyShips.get(0).boundingBox.height;

        float xMove = enemyShips.get(0).getDirectionVector().x * enemyShips.get(0).movementSpeed * delta;
        float yMove = enemyShips.get(0).getDirectionVector().y * enemyShips.get(0).movementSpeed * delta;

        // Makes sure ship doesn't move off screen
        if (xMove > 0) xMove = Math.min(xMove, rightLimit);
        else xMove = Math.max(xMove, leftLimit);

        if (yMove > 0) yMove = Math.min(yMove, upLimit);
        else yMove = Math.max(yMove, downLimit);

        // Update player ship position
        enemyShips.get(0).translate(xMove,yMove);

    }

    private  void detectInput(float delta){
        // Ensure the player ship stays in the screen
        float leftLimit, rightLimit, upLimit, downLimit;

        leftLimit = -playerShip.boundingBox.x;
        downLimit = -playerShip.boundingBox.y;
        rightLimit = WORLD_WIDTH - playerShip.boundingBox.x - playerShip.boundingBox.width;
        upLimit = (float) WORLD_HEIGHT / 2 - playerShip.boundingBox.y - playerShip.boundingBox.height;

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
        batch.draw(background, 0 , -((float) backgroundOffset/2), WORLD_WIDTH, WORLD_HEIGHT);
        batch.draw(background, 0 , -((float) backgroundOffset/2) + WORLD_HEIGHT, WORLD_WIDTH, WORLD_HEIGHT);

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
