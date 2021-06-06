package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Teleport {
    private Animation<TextureRegion> teleportAnimation;
    private float teleportTimer;

    private Rectangle boundingBox;

    Teleport(Texture texture, Rectangle boundingBox, float totalAnimationTime) {
        this.boundingBox = boundingBox;

        //split texture
        TextureRegion[][] textureRegion2D = TextureRegion.split(texture, texture.getWidth()/5, texture.getHeight()/2);

        //convert to 1D array
        TextureRegion[] textureRegion1D = new TextureRegion[10];
        int index = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 5; j++) {
                textureRegion1D[index] = textureRegion2D[i][j];
                index++;
            }
        }

        teleportAnimation = new Animation<TextureRegion>(totalAnimationTime/10, textureRegion1D);
        teleportTimer = 0;
    }

    public void update(float delta) {
        teleportTimer += delta;
    }

    public void draw (SpriteBatch batch) {
        batch.draw(teleportAnimation.getKeyFrame(teleportTimer),
                boundingBox.x,
                boundingBox.y,
                boundingBox.width,
                boundingBox.height);
    }

    public boolean isFinished() {
        return teleportAnimation.isAnimationFinished(teleportTimer);
    }
}
