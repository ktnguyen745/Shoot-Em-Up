package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Ship {
    // Ship characteristics
    float movementSpeed;
    int shield;

    // Position
    float xPosition, yPosition;
    float width, height;

    // Graphics
    Texture shipTexture, shieldTexture;

    public Ship(float movementSpeed, int shield,
                float width, float height,
                float xCentre, float yCentre,
                Texture shipTexture, Texture shieldTexture) {
        this.movementSpeed = movementSpeed;
        this.shield = shield;
        this.xPosition = xCentre - width/2;
        this.yPosition = yCentre - height/2;
        this.width = width;
        this.height = height;
        this.shipTexture = shipTexture;
        this.shieldTexture = shieldTexture;
    }

    // Draws ship and shield together
    public void draw(Batch batch){
        batch.draw(shipTexture, xPosition, yPosition, width, height);
        if (shield > 0) {
            batch.draw(shieldTexture,xPosition,yPosition,width,height);
        }
    }
}
