package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

public class Bullet {
    float xPosition, yPosition;
    float width, height;

    float movementSpeed;

    Texture bulletTexture;

    public Bullet(float xCentre, float yBottom,
                  float width, float height,
                  float movementSpeed, Texture bulletTexture) {
        this.xPosition = xCentre - width/2;
        this.yPosition = yBottom;
        this.width = width;
        this.height = height;
        this.movementSpeed = movementSpeed;
        this.bulletTexture = bulletTexture;
    }

    public void draw(Batch batch){
        batch.draw(bulletTexture, xPosition - width/2, yPosition, width, height);
    }

    public Rectangle getBoundingBox(){
        return new Rectangle(xPosition,yPosition,width,height);
    }
}
