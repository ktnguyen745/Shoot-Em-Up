package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Button {

    private float x;
    private float y;
    private float width;
    private float height;

    private boolean down;
    private boolean wasDown;

    private Texture textureDown;
    private Texture textureUp;

    public Button(float x, float y, float width, float height, Texture textureDown, Texture textureUp){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.textureDown = textureDown;
        this.textureUp = textureUp;

        down = false;
        wasDown = false;
    }

    /**
     * update()
     * This method checks if the screen was pressed within the area of the button, updating the
     * down and wasDown variables appropriately.
     */
    public void update(boolean touched, int touchX, int touchY) {
        if (this.down == true) this.wasDown = true;
        this.down = false;

        if (touched == true) {
            int screenHeight = Gdx.graphics.getHeight();

            if (touchX >= this.x && touchX <= this.x + this.width && screenHeight - touchY >= this.y && screenHeight - touchY <= this.y + this.height) {
                this.down = true;
                this.wasDown = false;
            }
        }
    }

    /**
     * draw()
     * Used to render the button.
      */
    public void draw(SpriteBatch batch) {
        if (this.down == true) {
            batch.draw(this.textureDown, this.x, this.y, this.width, this.height);
        }
        else {
            batch.draw(this.textureUp, this.x, this.y, this.width, this.height);
        }
    }

    public boolean isDown() { return down; }

    public boolean wasDown() {
        if (this.wasDown == true) {
            this.wasDown = false;
            return true;
        }
        return false;
    }

    public void dispose() {
        this.textureDown.dispose();
        this.textureUp.dispose();
    }
}
