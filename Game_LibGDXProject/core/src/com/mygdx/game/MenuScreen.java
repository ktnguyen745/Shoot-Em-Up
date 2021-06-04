package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import javax.xml.soap.Text;

public class MenuScreen implements Screen {

    private MyGdxGame game;

    private enum menuState{MAIN, LEVEL};
    private menuState state;

    private SpriteBatch batch;

    private Button playButton;
    private Button quitButton;
    private Button backButton;
    private Button level1Button;
    private Button level2Button;
    private Button level3Button;
    private Button title;
    private Button levelSelect;

    public MenuScreen(MyGdxGame game){
        this.game = game;
    }

    public void create(){

        batch = new SpriteBatch();
        state = menuState.MAIN;

        // Get screen width and height
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        // Get textures for play, quit and back buttons
        Texture playUp = new Texture(Gdx.files.internal("menu_play_up.png"));
        Texture playDown = new Texture(Gdx.files.internal("menu_play_down.png"));
        Texture quitUp = new Texture(Gdx.files.internal("menu_quit_up.png"));
        Texture quitDown = new Texture(Gdx.files.internal("menu_quit_down.png"));
        Texture backUp = new Texture(Gdx.files.internal("menu_back_up.png"));
        Texture backDown = new Texture(Gdx.files.internal("menu_back_down.png"));

        // Get dimensions of button textures
        int buttonWidth = playUp.getWidth();
        int buttonHeight = playUp.getHeight();

        // Calculate position of play button and create button
        int x = (screenWidth / 2) - (buttonWidth / 2);
        int y = (screenHeight / 2) - (buttonHeight / 2);
        playButton = new Button(x, y, buttonWidth, buttonHeight, playDown, playUp);

        // Calculate position of quit button and create button
        x = (screenWidth / 2) - (buttonWidth / 2);
        y = (screenHeight / 4) - (buttonHeight / 2);
        quitButton = new Button(x, y, buttonWidth, buttonHeight, quitDown, quitUp);

        // Calculate position of back button and create button
        x = (screenWidth / 2) - (buttonWidth / 2);
        y = (screenHeight / 4) - (buttonHeight / 2);
        backButton = new Button(x, y, buttonWidth, buttonHeight, backDown, backUp);

        // Get textures for level select buttons
        Texture level1 = new Texture(Gdx.files.internal("level1_button.png"));
        Texture level2 = new Texture(Gdx.files.internal("level2_button.png"));
        Texture level3 = new Texture(Gdx.files.internal("level3_button.png"));

        // Calculate sizes for buttons
        buttonWidth = screenWidth / 4;
        buttonHeight = screenHeight / 8; //(buttonWidth / level1.getWidth()) * level1.getHeight();

        // Calculate position of level 1 button and create button
        x = screenWidth / 16;
        y = (screenHeight / 2) - (buttonHeight / 2);
        level1Button = new Button(x, y, buttonWidth, buttonHeight, level1, level1);

        // Calculate position of level 2 button and create button
        x = (screenWidth / 2) - (buttonWidth / 2);
        level2Button = new Button(x, y, buttonWidth, buttonHeight, level2, level2);

        // Calculate position of level 3 button and create button
        x = screenWidth - ((screenWidth / 16) + buttonWidth);
        level3Button = new Button(x, y, buttonWidth, buttonHeight, level3, level3);

        // Get textures for title and level select
        Texture titleTexture = new Texture(Gdx.files.internal("title.png"));
        Texture levelSelectTexture = new Texture(Gdx.files.internal("level_select.png"));

        // Calculate size and position for title then make the button
        buttonWidth = screenWidth - (screenWidth / 4);
        buttonHeight = buttonWidth / 5;
        x = screenWidth / 8;
        y = (screenHeight / 4) * 3;
        title = new Button(x, y, buttonWidth, buttonHeight, titleTexture, titleTexture);

        // Calculate size and position for level select then make the button
        buttonHeight = buttonWidth / 6;
        levelSelect = new Button(x, y, buttonWidth, buttonHeight, levelSelectTexture, levelSelectTexture);
    }

    @Override
    public void show() {
        this.create();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(state == menuState.MAIN){
            // Update play button
            playButton.update(Gdx.input.isTouched(), Gdx.input.getX(), Gdx.input.getY());
            if(playButton.wasDown() == true){
                state = menuState.LEVEL;
            }

            // Update quit button
            quitButton.update(Gdx.input.isTouched(), Gdx.input.getX(), Gdx.input.getY());
            if (quitButton.wasDown() == true) {
                Gdx.app.exit();
            }
        } else {
            backButton.update(Gdx.input.isTouched(), Gdx.input.getX(), Gdx.input.getY());
            if (backButton.wasDown() == true){
                state = menuState.MAIN;
            }

            level1Button.update(Gdx.input.isTouched(), Gdx.input.getX(), Gdx.input.getY());
            if(level1Button.wasDown() == true){
                game.game = new GameScreen(game, GameScreen.Difficulty.EASY);
                game.setScreen(game.game);
            }
            level2Button.update(Gdx.input.isTouched(), Gdx.input.getX(), Gdx.input.getY());
            if(level2Button.wasDown() == true){
                game.game = new GameScreen(game, GameScreen.Difficulty.MEDIUM);
                game.setScreen(game.game);
            }
            level3Button.update(Gdx.input.isTouched(), Gdx.input.getX(), Gdx.input.getY());
            if(level3Button.wasDown() == true){
                game.game = new GameScreen(game, GameScreen.Difficulty.HARD);
                game.setScreen(game.game);
            }
        }

        batch.begin();
        if(state == menuState.MAIN){
            playButton.draw(batch);
            quitButton.draw(batch);
            title.draw(batch);
        } else {
            backButton.draw(batch);
            level1Button.draw(batch);
            level2Button.draw(batch);
            level3Button.draw(batch);
            levelSelect.draw(batch);
        }

        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        playButton.dispose();
        quitButton.dispose();
        backButton.dispose();
        level3Button.dispose();
        level2Button.dispose();
        level1Button.dispose();
    }
}
