package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Ship {
    // Ship characteristics
    float movementSpeed;
    int shield;
    public boolean isDestroyed = false;
    public boolean wasDestroyed = false;
    public boolean deletable = false;

    // Position
    Rectangle boundingBox;

    // Laser
    protected BulletManager bullets;
    float reloadTime;
    float lastShotTime = 0;

    // Graphics
    Texture shipTexture, shieldTexture;

    public Ship(float movementSpeed, int shield, float width, float height,
                float xCentre, float yCentre, float reloadTime,
                String shipTexture, String shieldTexture) {
        this.movementSpeed = movementSpeed;
        this.shield = shield;
        this.boundingBox = new Rectangle( xCentre - width/2, yCentre - height/2,width,height);

        this.reloadTime = reloadTime;
        this.shipTexture = new Texture(shipTexture);
        this.shieldTexture = new Texture(shieldTexture);

        this.bullets = new BulletManager("bullet_red.png", 5.0f, 2f, 2f);
    }

    public void update(float deltaTime){
        lastShotTime += deltaTime;

        if(canFireBullet() == true && isDestroyed == false) shoot();
        if(isDestroyed == true && bullets.countBullets() == 0) deletable = true;

        bullets.update(deltaTime);
    }

    public boolean canFireBullet(){
        if(lastShotTime - reloadTime >= 0){
            lastShotTime = 0;
            return true;
        }

        return false;
    }

    public abstract void shoot();

    public boolean intersects(Rectangle otherRectangle){
        return boundingBox.overlaps(otherRectangle);
    }

    public void hit(){
        if (shield > 0){
            shield--;
            SoundManager.HIT_COLLISION.play();
        } else {
            isDestroyed = true;
            SoundManager.DESTROYED_COLLISION.play();
        }
    }

    public void translate(float xChange, float yChange){
        boundingBox.setPosition(boundingBox.x + xChange, boundingBox.y + yChange);
    }

    public void collisionCheck(Ship ship){
        bullets.collisionCheck(ship);
    }

    // Draws ship and shield together
    public void draw(SpriteBatch batch){
        if(isDestroyed == false) batch.draw(shipTexture, boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
        if (shield > 0) {
            batch.draw(shieldTexture, boundingBox.x, boundingBox.y,boundingBox.width,boundingBox.height);
        }
        bullets.render(batch);
    }

    public Rectangle getBoundingBox(){
        return boundingBox;
    }

    public int getScore() { return 0; }
}
