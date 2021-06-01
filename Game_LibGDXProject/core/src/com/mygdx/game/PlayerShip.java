package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class PlayerShip extends Ship {
    public PlayerShip(float movementSpeed, int shield,
                      float width, float height,
                      float xCentre, float yCentre,
                      float bulletWidth, float bulletHeight,
                      float bulletSpeed, float reloadTime,
                      Texture shipTexture, Texture shieldTexture, Texture bulletTexture) {
        super(movementSpeed, shield, width, height, xCentre, yCentre, bulletWidth, bulletHeight, bulletSpeed, reloadTime, shipTexture, shieldTexture, bulletTexture);
    }

    @Override
    public Bullet[] shootBullet() {
        Bullet[] bullet = new Bullet[1];

        bullet[0] = new Bullet(boundingBox.x + boundingBox.width * 0.5f, boundingBox.y + boundingBox.height,
                bulletWidth, bulletHeight,
                bulletSpeed, bulletTexture);

        lastShotTime = 0;

        return bullet;
    }
}
