package com.mygdx.game;

public class PowerupBuilder {

    public static PowerUp buildRandomPowerup(float x, float y){
        double random = Math.random() * 100;
        if(random < 33){
            return buildDoubleshot(x, y);
        } else if (random >= 33 && random < 66){
            return buildShotgun(x, y);
        } else {
            return buildSpeed(x, y);
        }
    }

    public static PowerUp buildDoubleshot(float x, float y){
        PowerUp powerUp = new PowerUp(PlayerShip.PowerUpState.DOUBLE_SHOT, "powerup_double.png",
                6f, 6f, x, y, -25, 10);
        return powerUp;
    }

    public static PowerUp buildSpeed(float x, float y){
        PowerUp powerUp = new PowerUp(PlayerShip.PowerUpState.SPEED, "powerup_speed.png",
                6f, 6f, x, y, -25, 10);
        return powerUp;
    }

    public static PowerUp buildShotgun(float x, float y){
        PowerUp powerUp = new PowerUp(PlayerShip.PowerUpState.SHOTGUN, "powerup_shotgun.png",
                6f, 6f, x, y, -25, 10);
        return powerUp;
    }

}
