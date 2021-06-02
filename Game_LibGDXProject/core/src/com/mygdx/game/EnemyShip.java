package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class EnemyShip extends Ship {
    public EnemyShip(float movementSpeed, int shield, float width, float height,
                     float xCentre, float yCentre, float reloadTime,
                     Texture shipTexture, Texture shieldTexture) {
        super(movementSpeed, shield, width, height, xCentre, yCentre, reloadTime, shipTexture, shieldTexture);
        bullets = new BulletManager("bullet_blue.png", 5.0f, 3f, 3f);
    }

    @Override
    public void shoot() {
        float posX = boundingBox.x + boundingBox.width * 0.5f;
        float posY = boundingBox.y;
        float velX = 0;
        float velY = -50;
        bullets.spawnBullet(BulletManager.Type.BULLET, posX, posY, velX, velY);
    }
}
