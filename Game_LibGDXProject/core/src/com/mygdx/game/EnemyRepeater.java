package com.mygdx.game;

public class EnemyRepeater extends EnemyShip{
    int numShots = 0;

    public EnemyRepeater(float movementSpeed, int shield, float width, float height, float xCentre, float yCentre, float reloadTime, String shipTexture, String shieldTexture) {
        super(movementSpeed, shield, width, height, xCentre, yCentre, reloadTime, shipTexture, shieldTexture);
        bullets = new BulletManager("bullet_dblue.png", 5.0f, 3f, 3f);
    }

    public void shoot() {
        if(numShots == 0){
            reloadTime = 0.15f;
        }
        if(numShots == 3){
            numShots = -1;
            reloadTime = 3f;
        }
        float posX = (boundingBox.x + boundingBox.width * 0.5f) - 1;
        float posY = boundingBox.y;
        float velX = 0;
        float velY = -50;
        bullets.spawnBullet(BulletManager.Type.BULLET, posX, posY, velX, velY);
        SoundManager.ENEMY_SHOOT_3.play();
        numShots++;
    }

    @Override
    public int getScore() { return 175; }
}
