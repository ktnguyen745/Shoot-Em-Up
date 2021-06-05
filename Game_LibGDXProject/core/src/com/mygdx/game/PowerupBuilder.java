package com.mygdx.game;

public class PowerupBuilder {

    public static PowerUp buildRandomPowerup(float x, float y){
        double random = Math.random() * 100;
        if(random < 50){
            return buildDoubleshot(x, y);
        } else {
            return buildShield(x, y);
        }
    }

    public static PowerUp buildDoubleshot(float x, float y){
        PowerUp powerUp = new PowerUp(PlayerShip.PowerUpState.DOUBLE_SHOT, "powerup_double.png",
                6f, 6f, x, y, -25, 10);
        return powerUp;
    }

    public static PowerUp buildShield(float x, float y){
        PowerUp powerUp = new PowerUp(PlayerShip.PowerUpState.SPEED, "powerup_speed.png",
                6f, 6f, x, y, -25, 10);
        return powerUp;
    }

}
