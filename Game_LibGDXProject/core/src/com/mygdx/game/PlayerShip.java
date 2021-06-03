package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class PlayerShip extends Ship {
    public PlayerShip(float movementSpeed, int shield, float width, float height,
                      float xCentre, float yCentre, float reloadTime,
                      String shipTexture, String shieldTexture) {
        super(movementSpeed, shield, width, height, xCentre, yCentre, reloadTime, shipTexture, shieldTexture);
        bullets = new BulletManager("bullet_red.png", 5.0f, 2f, 2f);
    }


    @Override
    public void shoot() {
        float posX = boundingBox.x + boundingBox.width / 2;
        float posY = boundingBox.y + boundingBox.height;
        float velX = 0;
        float velY = 45;
        bullets.spawnBullet(BulletManager.Type.BULLET, posX, posY, velX, velY);

        SoundManager.player_shoot.play();
    }
}
