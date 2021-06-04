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

    public enum GameState{ENEMY, BOSS, WIN, LOSE}
    private GameState state;

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

    // Enemy spawning
    private int totalEnemies;
    private int maxEnemiesOnScreen;
    private int currentEnemies;
    private int enemiesDestroyed;
    private float spawnDelay = 2.5f;
    private float spawnTimer = 0f;

    public GameScreen(MyGdxGame game, Difficulty difficulty){
        this.game = game;
        this.difficulty = difficulty;
        this.state = GameState.ENEMY;

        // Set up enemy spawning
        currentEnemies = 0;
        enemiesDestroyed = 0;
        switch (difficulty){
            case EASY:
                totalEnemies = 10;
                maxEnemiesOnScreen = 3;
                break;
            case MEDIUM:
                totalEnemies = 20;
                maxEnemiesOnScreen = 4;
                break;
            case HARD:
                totalEnemies = 30;
                maxEnemiesOnScreen = 5;
                break;
        }

        // Setup screen settings
        this.camera = new OrthographicCamera();
        this.viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        // Setup textures and batch
        this.background = new Texture(Gdx.files.internal("background.png"));
        this.batch = new SpriteBatch();

        // Start background offset at the bottom of the background image
        this.backgroundOffset = 0;

        // Setup ships
        this.playerShip = new PlayerShip(64, 3, 10, 10,
                (float) WORLD_WIDTH/2, (float) WORLD_HEIGHT/4,
                0.5f, "player_ship.png", "shield1.png");

        enemyShips = new ArrayList<EnemyShip>();

    }

    public void create(){ }

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

        // Remove destroyed ships, handle enemy spawning
        if(state == GameState.ENEMY){
            for(int i = 0; i < enemyShips.size(); i++){
                if(enemyShips.get(i).isDestroyed){
                    enemyShips.remove(i);
                    enemiesDestroyed++;
                }
            }
            currentEnemies = enemyShips.size();
            if(enemiesDestroyed >= totalEnemies){
                state = GameState.BOSS;
                enemyShips.clear();
            } else if(currentEnemies == 0){
                for(int i = 0; i < maxEnemiesOnScreen; i++){
                    if(currentEnemies + enemiesDestroyed < totalEnemies) spawnEnemy();
                }
                currentEnemies = maxEnemiesOnScreen;
            } else if (currentEnemies < maxEnemiesOnScreen){
                spawnTimer += delta;
                if(spawnTimer >= spawnDelay){
                    if(currentEnemies + enemiesDestroyed < totalEnemies) spawnEnemy();
                    spawnTimer = 0;
                }
            }
        } else if (state == GameState.BOSS){
            if(enemyShips.isEmpty()){
                enemyShips.add(ShipBuilder.buildBoss(difficulty));
            }
            if(enemyShips.get(0).isDestroyed == true){
                state = GameState.WIN;
                enemyShips.clear();
            }
        }
        if(playerShip.isDestroyed){
            state = GameState.LOSE;
        }

        if(state == GameState.LOSE){
            game.showMenu();
        }
        if(state == GameState.WIN){
            game.showMenu();
        }


        // Check for user input
        detectInput(delta);

        // Enemy movement
        ListIterator<EnemyShip> enemyShipListIterator = enemyShips.listIterator();
        while (enemyShipListIterator.hasNext()) {
            EnemyShip enemyShip = enemyShipListIterator.next();
            moveEnemy(enemyShip, delta);
        }

        batch.end();
    }

    private void moveEnemy(EnemyShip enemyShip, float delta) {
        float leftLimit, rightLimit, upLimit, downLimit;
        leftLimit = -enemyShip.boundingBox.x;
        downLimit = (float) WORLD_HEIGHT / 2 - enemyShip.boundingBox.y;
        rightLimit = WORLD_WIDTH - enemyShip.boundingBox.x - enemyShip.boundingBox.width;
        upLimit = WORLD_HEIGHT - enemyShip.boundingBox.y - enemyShip.boundingBox.height;

        float xMove = enemyShip.getDirectionVector().x * enemyShip.movementSpeed * delta;
        float yMove = enemyShip.getDirectionVector().y * enemyShip.movementSpeed * delta;

        // Makes sure ship doesn't move off screen
        if (xMove > 0) xMove = Math.min(xMove, rightLimit);
        else xMove = Math.max(xMove, leftLimit);

        if (yMove > 0) yMove = Math.min(yMove, upLimit);
        else yMove = Math.max(yMove, downLimit);

        // Update player ship position
        enemyShip.translate(xMove,yMove);

    }

    private void spawnEnemy(){
        double random = Math.random() * 100;
        if(random < 70){
            enemyShips.add(ShipBuilder.buildEnemy());
        } else {
            enemyShips.add(ShipBuilder.buildTripleShot());
        }
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
