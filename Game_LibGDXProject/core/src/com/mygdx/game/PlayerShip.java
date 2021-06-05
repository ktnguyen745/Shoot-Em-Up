package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PlayerShip extends Ship {

    int maxShield;
    float shieldRecharge = 10f;
    float rechargeTime = 0f;

    public PlayerShip(float movementSpeed, int shield, float width, float height,
                      float xCentre, float yCentre, float reloadTime,
                      String shipTexture, String shieldTexture) {
        super(movementSpeed, shield, width, height, xCentre, yCentre, reloadTime, shipTexture, shieldTexture);
        bullets = new BulletManager("bullet_red.png", 5.0f, 2f, 2f);
        maxShield = shield;
    }

    @Override
    public void update(float deltaTime){
        lastShotTime += deltaTime;

        // Update bullets
        if(canFireBullet() == true) shoot();
        bullets.update(deltaTime);

        // Update shield
        if(shield < maxShield){
            rechargeTime += deltaTime;
            if(rechargeTime >= shieldRecharge){
                shield++;
                rechargeTime = 0f;
            }
        }
    }

    @Override
    public void shoot() {
        float posX = (boundingBox.x + boundingBox.width * 0.5f) - 1;
        float posY = boundingBox.y + boundingBox.height;
        float velX = 0;
        float velY = 60;
        bullets.spawnBullet(BulletManager.Type.BULLET, posX, posY, velX, velY);

        SoundManager.PLAYER_SHOOT.play();
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);

        int x = 65;

        for (int count = 0; count < shield; count++){
            batch.draw(shipTexture, x, 2, 3, 3);

            x -= 5;
        }
    }
}
