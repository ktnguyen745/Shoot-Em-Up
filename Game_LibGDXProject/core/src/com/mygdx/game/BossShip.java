package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BossShip extends EnemyShip{

    private GameScreen.Difficulty difficulty;
    private double shotPatterns;

    public BossShip(float movementSpeed, int shield, float width, float height,
                    float xCentre, float yCentre, float reloadTime,
                    String shipTexture, String shieldTexture,
                    GameScreen.Difficulty difficulty) {
        super(movementSpeed, shield, width, height, xCentre, yCentre, reloadTime, shipTexture, shieldTexture);
        bullets = new BulletManager("bullet_pink.png", 5.0f, 3f, 3f);
        this.difficulty = difficulty;
        switch(difficulty){
            case EASY:
                shotPatterns = 3;
                break;
            case MEDIUM:
                shotPatterns = 4;
                break;
            case HARD:
                shotPatterns = 5;
                break;
        }
    }

    // Boss has more complicated attack patterns so it refers to multiple prviate shoot methods to simplify the code
    public void shoot(){
        double random = Math.random() * shotPatterns;
        if(random <= 1){
            shootPatternA();
        } else if (random <= 2){
            shootPatternB();
        } else if (random <= 3){
            shootPatternC();
        } else if (random <= 4){
            shootPatternD();
        } else {
            shootPatternE();
        }
    }

    // This is the easiest attack the boss uses, appears in all difficulties.
    private void shootPatternA(){
        float posX = (boundingBox.x + boundingBox.width * 0.5f) - 16;
        float posY = boundingBox.y + 8;
        float velX = 0;
        float velY = -40;
        bullets.spawnBullet(BulletManager.Type.BULLET, posX, posY, velX, velY);
        posX = (boundingBox.x + boundingBox.width * 0.5f) - 21;
        posY = boundingBox.y + 8;
        bullets.spawnBullet(BulletManager.Type.BULLET, posX, posY, velX, velY);
        posX = (boundingBox.x + boundingBox.width * 0.5f) + 14;
        posY = boundingBox.y + 8;
        bullets.spawnBullet(BulletManager.Type.BULLET, posX, posY, velX, velY);
        posX = (boundingBox.x + boundingBox.width * 0.5f) + 19;
        posY = boundingBox.y + 8;
        bullets.spawnBullet(BulletManager.Type.BULLET, posX, posY, velX, velY);
    }

    // This is a moderate attack, appearing in all difficulties
    private void shootPatternB(){
        float posX = (boundingBox.x + boundingBox.width * 0.5f) - 1;
        float posY = boundingBox.y + 6;
        float velX = 0;
        float velY = -40;
        bullets.spawnBullet(BulletManager.Type.BULLET, posX, posY, velX, velY);
        posX = (boundingBox.x + boundingBox.width * 0.5f) + 6;
        posY = boundingBox.y;
        bullets.spawnBullet(BulletManager.Type.BULLET, posX, posY, velX, velY);
        posX = (boundingBox.x + boundingBox.width * 0.5f) - 8;
        posY = boundingBox.y;
        bullets.spawnBullet(BulletManager.Type.BULLET, posX, posY, velX, velY);
        posX = (boundingBox.x + boundingBox.width * 0.5f) - 15;
        posY = boundingBox.y + 9;
        bullets.spawnBullet(BulletManager.Type.BULLET, posX, posY, velX, velY);
        posX = (boundingBox.x + boundingBox.width * 0.5f) + 13;
        posY = boundingBox.y + 9;
        bullets.spawnBullet(BulletManager.Type.BULLET, posX, posY, velX, velY);
    }

    // This is a challenging attack, appears in all difficulties
    private void shootPatternC(){
        float posX = (boundingBox.x + boundingBox.width * 0.5f) - 1;
        float posY = boundingBox.y + 6;
        float velX = 0;
        float velY = -40;
        bullets.spawnBullet(BulletManager.Type.BULLET, posX, posY, velX, velY);
        velX = -15;
        velY = -37;
        bullets.spawnBullet(BulletManager.Type.BULLET, posX, posY, velX, velY);
        velX = 15;
        velY = -37;
        bullets.spawnBullet(BulletManager.Type.BULLET, posX, posY, velX, velY);
        velX = -25;
        velY = -31;
        bullets.spawnBullet(BulletManager.Type.BULLET, posX, posY, velX, velY);
        velX = 25;
        velY = -31;
        bullets.spawnBullet(BulletManager.Type.BULLET, posX, posY, velX, velY);

    }

    // This is a difficult attack, appears in medium and hard difficulties
    private void shootPatternD(){
        float posX = (boundingBox.x + boundingBox.width * 0.5f) - 8;
        float posY = boundingBox.y;
        float velX = 0;
        float velY = -40;
        bullets.spawnBullet(BulletManager.Type.BULLET, posX, posY, velX, velY);
        velX = -20;
        velY = -35;
        bullets.spawnBullet(BulletManager.Type.BULLET, posX, posY, velX, velY);
        velX = 20;
        velY = -35;
        bullets.spawnBullet(BulletManager.Type.BULLET, posX, posY, velX, velY);

        posX = (boundingBox.x + boundingBox.width * 0.5f) + 6;
        posY = boundingBox.y;
        velX = 0;
        velY = -40;
        bullets.spawnBullet(BulletManager.Type.BULLET, posX, posY, velX, velY);
        velX = -20;
        velY = -35;
        bullets.spawnBullet(BulletManager.Type.BULLET, posX, posY, velX, velY);
        velX = 20;
        velY = -35;
        bullets.spawnBullet(BulletManager.Type.BULLET, posX, posY, velX, velY);
    }

    // This is an insane attack, appears in hard difficulty only.
    private void shootPatternE(){
        float posX = (boundingBox.x + boundingBox.width * 0.5f) - 21;
        float posY = boundingBox.y + 8;
        float velX = 0;
        float velY = -40;
        bullets.spawnBullet(BulletManager.Type.BULLET, posX, posY, velX, velY);
        velX = 15;
        velY = -37;
        bullets.spawnBullet(BulletManager.Type.BULLET, posX, posY, velX, velY);
        velX = 25;
        velY = -31;
        bullets.spawnBullet(BulletManager.Type.BULLET, posX, posY, velX, velY);

        posX = (boundingBox.x + boundingBox.width * 0.5f) + 19;
        posY = boundingBox.y + 8;
        velX = 0;
        velY = -40;
        bullets.spawnBullet(BulletManager.Type.BULLET, posX, posY, velX, velY);
        velX = -15;
        velY = -37;
        bullets.spawnBullet(BulletManager.Type.BULLET, posX, posY, velX, velY);
        velX = -25;
        velY = -31;
        bullets.spawnBullet(BulletManager.Type.BULLET, posX, posY, velX, velY);
    }
}
