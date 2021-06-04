package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class EnemyShip extends Ship {

    Vector2 directionVector;
    float timeSinceLastDirectionChange = 0;
    float directionChangeFrequency = 0.75f;

    public EnemyShip(float movementSpeed, int shield, float width, float height,
                     float xCentre, float yCentre, float reloadTime,
                     String shipTexture, String shieldTexture) {
        super(movementSpeed, shield, width, height, xCentre, yCentre, reloadTime, shipTexture, shieldTexture);
        bullets = new BulletManager("bullet_blue.png", 5.0f, 3f, 3f);

        directionVector = new Vector2(0,-1);
    }

    public Vector2 getDirectionVector() {
        return directionVector;
    }

    // allow the direction only towards right
    public Vector2 getDirectionVectorRight() {
        if (directionVector.x < 0) directionVector.x = -directionVector.x;
        return directionVector;
    }

    // allow the direction only towards left
    public Vector2 getDirectionVectorLeft() {
        if (directionVector.x > 0) directionVector.x = -directionVector.x;
        return directionVector;
    }

    // allow the direction only towards down
    public Vector2 getDirectionVectorDown() {
        if (directionVector.y > 0) directionVector.y = -directionVector.y;
        return directionVector;
    }

    // allow the direction only towards up
    public Vector2 getDirectionVectorUp() {
        if (directionVector.y < 0) directionVector.y = -directionVector.y;
        return directionVector;
    }


    private void randomizeDirectionVector() {
        double bearing = (MyGdxGame.random.nextDouble() + 1 ) * 6.283185;  // between 0 and 2*PI
        directionVector.x = (float)Math.sin(bearing);
        directionVector.y = (float)Math.cos(bearing);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        timeSinceLastDirectionChange += deltaTime;
        if (timeSinceLastDirectionChange > directionChangeFrequency) {
            randomizeDirectionVector();
            timeSinceLastDirectionChange -= directionChangeFrequency;
        }
    }

    @Override
    public void shoot() {
        float posX = (boundingBox.x + boundingBox.width * 0.5f) - 1;
        float posY = boundingBox.y;
        float velX = 0;
        float velY = -50;
        bullets.spawnBullet(BulletManager.Type.BULLET, posX, posY, velX, velY);

        SoundManager.ENEMY_SHOOT.play();
    }
}
