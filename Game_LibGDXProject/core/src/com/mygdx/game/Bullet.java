package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Bullet {
    float movementSpeed;

    float xPosition, yPosition;
    float width, height;

    Texture bulletTexture;

    public Bullet(float movementSpeed, float xPosition, float yPosition, float width, float height, Texture bulletTexture) {
        this.movementSpeed = movementSpeed;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = width;
        this.height = height;
        this.bulletTexture = bulletTexture;
    }

    public void draw(Batch batch){
        batch.draw(bulletTexture, xPosition - width/2, yPosition, width, height);
    }
}
