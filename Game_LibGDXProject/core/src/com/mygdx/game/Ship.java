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
        this.boundingBox = new Rectangle( xCentre - width/2, yCentre - height/2,width,height);

        this.bulletWidth = bulletWidth;
        this.bulletHeight = bulletHeight;
        this.bulletSpeed = bulletSpeed;
        this.reloadTime = reloadTime;
        this.shipTexture = shipTexture;
        this.shieldTexture = shieldTexture;
        this.bulletTexture = bulletTexture;
    }

    public void update(float deltaTime){
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

    public void translate(float xChange, float yChange){
        boundingBox.setPosition(boundingBox.x + xChange, boundingBox.y + yChange);
    }

    // Draws ship and shield together
    public void draw(Batch batch){
        batch.draw(shipTexture, boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
        if (shield > 0) {
            batch.draw(shieldTexture, boundingBox.x, boundingBox.y,boundingBox.width,boundingBox.height);
        }
    }
}
