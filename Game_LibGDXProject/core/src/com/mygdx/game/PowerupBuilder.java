package com.mygdx.game;

public class PowerupBuilder {

    public static PowerUp buildDoubleshot(float x, float y){
        PowerUp powerUp = new PowerUp(PlayerShip.PowerUpState.DOUBLE_SHOT, "powerup_double.png",
                5f, 5f, x, y, -25, 10);
        return powerUp;
    }

}
