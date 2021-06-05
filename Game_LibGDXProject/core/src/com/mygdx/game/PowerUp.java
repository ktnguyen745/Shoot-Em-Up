package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class PowerUp {

    // Powerup type, texture, rectangle and speed
    public PlayerShip.PowerUpState type;
    private Rectangle boundingBox;
    private Texture texture;
    private float speed;
    private float lifetime;
    public boolean expired = false;

    public PowerUp(PlayerShip.PowerUpState type, String texture, float width, float height, float x, float y, float speed, float lifetime){
        this.type = type;
        this.texture = new Texture(texture);
        this.speed = speed;
        this.lifetime = lifetime;
        boundingBox = new Rectangle(x - width / 2, y - height / 2, width, height);
    }

    public void update(float dt){
        boundingBox.setY(boundingBox.y + (speed * dt));
    }

    public void draw(SpriteBatch batch){
        batch.draw(texture, boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
    }

    public Rectangle boundingBox(){
        return boundingBox;
    }

}
