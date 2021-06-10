package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EnemyInvisible extends EnemyShip{

    public EnemyInvisible(float movementSpeed, int shield, float width, float height, float xCentre, float yCentre, float reloadTime, String shipTexture, String shieldTexture) {
        super(movementSpeed, shield, width, height, xCentre, yCentre, reloadTime, shipTexture, shieldTexture);
        bullets = new BulletManager("bullet_yellow.png", 5f, 3f, 3f);
    }

    @Override
    public void shoot() {
        float posX = (boundingBox.x + boundingBox.width * 0.5f) - 4;
        float posY = boundingBox.y;
        float velX = 0;
        float velY = -50;
        bullets.spawnBullet(BulletManager.Type.BULLET, posX, posY, velX, velY);
        posX = (boundingBox.x + boundingBox.width * 0.5f) + 2;
        bullets.spawnBullet(BulletManager.Type.BULLET, posX, posY, velX, velY);
        SoundManager.ENEMY_SHOOT.play();
    }

    @Override
    public void draw(SpriteBatch batch) {
        if(isDestroyed == false && isInvisible() == false){
            batch.draw(shipTexture, boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
            if (shield > 0) {
                batch.draw(shieldTexture, boundingBox.x, boundingBox.y,boundingBox.width,boundingBox.height);
            }
        }
        bullets.render(batch);
    }

    private boolean isInvisible(){
        if (reloadTime - lastShotTime > reloadTime * 2/3 || reloadTime - lastShotTime < reloadTime * 1/3){
            return false;
        }
        return true;
    }

    @Override
    public int getScore() { return 200; }
}
