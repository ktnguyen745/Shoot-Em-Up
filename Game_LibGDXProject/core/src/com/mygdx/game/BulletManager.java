package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class BulletManager {

    public enum Type {NONE, BULLET}
    private static final int max_bullets = 128;

    private Texture bulletTexture;
    private float bulletLifespan;
    private float width;
    private float height;

    Type[] bullets = new Type[max_bullets];
    Vector2[] position = new Vector2[max_bullets];
    Vector2[] velocity = new Vector2[max_bullets];
    float[] lifespan = new float[max_bullets];

    public BulletManager(String bulletTexture, float bulletLifespan, float width, float height){
        this.bulletTexture = new Texture(Gdx.files.internal(bulletTexture));
        this.bulletLifespan = bulletLifespan;
        this.width = width;
        this.height = height;
        for(int i = 0; i < max_bullets; i++){
            bullets[i] = Type.NONE;
            position[i] = new Vector2();
            velocity[i] = new Vector2();
        }
    }

    public int spawnBullet(Type type, float posX, float posY, float velX, float velY){
        // If no bullet is to be spawned,return a fail result
        if(type == Type.NONE) return -1;

        // Find empty spot in bullets array
        int index = -1;
        for(int free = 0; free < max_bullets; free++){
            if(bullets[free] == Type.NONE){
                index = free;
                break;
            }
        }
        // If no free spots were found
        if(index < 0) return -1;

        // If a free spot was found
        bullets[index] = type;
        position[index].x = posX;
        position[index].y = posY;
        velocity[index].x = velX;
        velocity[index].y = velY;
        lifespan[index] = bulletLifespan;

        return index;
    }

    public void update(float dt){
        for(int i = 0; i < max_bullets; i++){
            if(bullets[i] != Type.NONE){
                lifespan[i] -= dt;
                if(lifespan[i] < 0){
                    bullets[i] = Type.NONE;
                } else {
                    position[i].mulAdd(velocity[i], dt);
                }
            }
        }
    }

    public void render(SpriteBatch batch){
        for(int i = 0; i < max_bullets; i++){
            if(bullets[i] != Type.NONE){
                batch.draw(bulletTexture, position[i].x, position[i].y, width, height);
            }
        }
    }

    public void collisionCheck(Ship ship){
        Rectangle hitbox = new Rectangle();
        hitbox.setHeight(height);
        hitbox.setWidth(width);
        for(int i = 0; i < max_bullets; i++){
            if(bullets[i] != Type.NONE){
                hitbox.setX(position[i].x);
                hitbox.setY(position[i].y);
                if(ship.intersects(hitbox)){
                    ship.hit();
                    bullets[i] = Type.NONE;
                }
            }
        }
    }

    public int countBullets(){
        int count = 0;
        for(int i = 0; i < max_bullets; i++){
            if(bullets[i] != Type.NONE) count++;
        }
        return count;
    }

    public void dispose(){
        bulletTexture.dispose();
    }

}
