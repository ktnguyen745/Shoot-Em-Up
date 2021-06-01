package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public abstract class Ship {
    // Ship characteristics
    float movementSpeed;
    int shield;

    // Position
    float xPosition, yPosition;
    float width, height;

    Rectangle boundingBox;

    // Laser
    float bulletWidth, bulletHeight;
    float bulletSpeed;
    float reloadTime;
    float lastShotTime = 0;

    // Graphics
    Texture shipTexture, shieldTexture, bulletTexture;

    public Ship(float movementSpeed, int shield,
                float width, float height,
                float xCentre, float yCentre,
                float bulletWidth, float bulletHeight, float bulletSpeed,
                float reloadTime,
                Texture shipTexture, Texture shieldTexture, Texture bulletTexture) {
        this.movementSpeed = movementSpeed;
        this.shield = shield;
        this.xPosition = xCentre - width/2;
        this.yPosition = yCentre - height/2;
        this.width = width;
        this.height = height;

        this.boundingBox = new Rectangle(xPosition,yPosition,width,height);

        this.bulletWidth = bulletWidth;
        this.bulletHeight = bulletHeight;
        this.bulletSpeed = bulletSpeed;
        this.reloadTime = reloadTime;
        this.shipTexture = shipTexture;
        this.shieldTexture = shieldTexture;
        this.bulletTexture = bulletTexture;
    }

    public void update(float deltaTime){
        boundingBox.set(xPosition,yPosition,width,height);
        lastShotTime += deltaTime;
    }

    public boolean canFireBullet(){
        if(lastShotTime - reloadTime >= 0){
            return true;
        }

        return false;
    }

    public abstract Bullet[] shootBullet();

    public boolean intersects(Rectangle otherRectangle){
        return boundingBox.overlaps(otherRectangle);
    }

    public void hit(Bullet laser){
        if (shield > 0){
            shield--;
        }
    }

    // Draws ship and shield together
    public void draw(Batch batch){
        batch.draw(shipTexture, xPosition, yPosition, width, height);
        if (shield > 0) {
            batch.draw(shieldTexture,xPosition,yPosition,width,height);
        }
    }
}
