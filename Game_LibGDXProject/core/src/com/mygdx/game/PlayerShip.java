package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PlayerShip extends Ship {

    public enum PowerUpState {NORMAL, DOUBLE_SHOT, SPEED};
    private PowerUpState powerUp;
    float powerupDuration = 10f;
    float powerupTime = 0f;

    int maxShield;
    float shieldRecharge = 10f;
    float rechargeTime = 0f;
    Texture shieldIcon;

    public PlayerShip(float movementSpeed, int shield, float width, float height,
                      float xCentre, float yCentre, float reloadTime,
                      String shipTexture, String shieldTexture) {
        super(movementSpeed, shield, width, height, xCentre, yCentre, reloadTime, shipTexture, shieldTexture);
        bullets = new BulletManager("bullet_red.png", 5.0f, 2.5f, 2.5f);
        maxShield = shield;
        powerUp = PowerUpState.NORMAL;
        shieldIcon = new Texture("shield_icon.png");
    }

    @Override
    public void update(float deltaTime){
        lastShotTime += deltaTime;

        // Update bullets
        if(canFireBullet() == true) shoot();
        bullets.update(deltaTime);

        if(powerUp != PowerUpState.NORMAL){
            powerupTime += deltaTime;
            if(powerupTime >= powerupDuration){
                powerUp = PowerUpState.NORMAL;
                reloadTime = 0.5f;
                shipTexture = new Texture("player_ship.png");
            }
        }

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
        if (powerUp == PowerUpState.DOUBLE_SHOT){
            shoot_double();
        } else {
            shoot_normal();
        }
        SoundManager.PLAYER_SHOOT.play();
    }

    private void shoot_normal(){
        float posX = (boundingBox.x + boundingBox.width * 0.5f) - 1;
        float posY = boundingBox.y + boundingBox.height;
        float velX = 0;
        float velY = 60;
        bullets.spawnBullet(BulletManager.Type.BULLET, posX, posY, velX, velY);
    }

    private void shoot_double(){
        float posX = (boundingBox.x + boundingBox.width * 0.5f) - 3;
        float posY = boundingBox.y + boundingBox.height;
        float velX = 0;
        float velY = 60;
        bullets.spawnBullet(BulletManager.Type.BULLET, posX, posY, velX, velY);
        posX = (boundingBox.x + boundingBox.width * 0.5f) + 1;
        bullets.spawnBullet(BulletManager.Type.BULLET, posX, posY, velX, velY);
    }

    public void setPowerUp(PowerUpState powerUp){
        this.powerUp = powerUp;
        powerupTime = 0f;
        if(shield < 5) shield++;
        if(powerUp == PowerUpState.DOUBLE_SHOT){
            shipTexture = new Texture("player_ship_double.png");
            reloadTime = 0.5f;
        } else if (powerUp == PowerUpState.SPEED){
            shipTexture = new Texture("player_ship_shield.png");
            reloadTime = 0.3f;
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);

        int x = 65;

        for (int count = 0; count < shield; count++){
            batch.draw(shieldIcon, x, 2, 5, 5);

            x -= 5;
        }
    }
}
