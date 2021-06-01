package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

public class Bullet {
    Rectangle boundingBox;

    float movementSpeed;

    Texture bulletTexture;

    public Bullet(float xCentre, float yBottom,
                  float width, float height,
                  float movementSpeed, Texture bulletTexture) {
        this.boundingBox = new Rectangle(xCentre - width/2, yBottom, width,height);
        this.movementSpeed = movementSpeed;
        this.bulletTexture = bulletTexture;
    }

    public void draw(Batch batch){
        batch.draw(bulletTexture, boundingBox.x - boundingBox.width/2, boundingBox.y, boundingBox.width, boundingBox.height);
    }
}
