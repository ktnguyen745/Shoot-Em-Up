package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class EnemyTripleshot extends EnemyShip{

    public EnemyTripleshot(float movementSpeed, int shield, float width, float height,
                     float xCentre, float yCentre, float reloadTime,
                     String shipTexture, String shieldTexture) {
        super(movementSpeed, shield, width, height, xCentre, yCentre, reloadTime, shipTexture, shieldTexture);

        bullets = new BulletManager("bullet_green.png", 5.0f, 3f, 3f);
    }

    @Override
    public void shoot() {
        float posX = (boundingBox.x + boundingBox.width * 0.5f) - 1;
        float posY = boundingBox.y;
        float velX = 0;
        float velY = -40;
        bullets.spawnBullet(BulletManager.Type.BULLET, posX, posY, velX, velY);
        velX = -15;
        velY = -37;
        bullets.spawnBullet(BulletManager.Type.BULLET, posX, posY, velX, velY);
        velX = 15;
        velY = -37;
        bullets.spawnBullet(BulletManager.Type.BULLET, posX, posY, velX, velY);

        SoundManager.ENEMY_SHOOT_2.play();
    }

    @Override
    public int getScore() { return 150; }
}
