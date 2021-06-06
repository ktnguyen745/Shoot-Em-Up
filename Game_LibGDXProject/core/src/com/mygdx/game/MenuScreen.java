package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuScreen implements Screen {

    private MyGdxGame game;

    public enum menuState{MAIN, LEVEL, WIN, LOSE}
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
    private Button winButton;
    private Button loseButton;

    public MenuScreen(MyGdxGame game){
        this.game = game;
    }

    public void create(){

        batch = new SpriteBatch();

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


        // Get textures for win and lose screen
        Texture winTexture = new Texture(Gdx.files.internal("youWin.jpg"));
        Texture loseTexture = new Texture(Gdx.files.internal("tryAgain.jpg"));

        // Calculate size and position for win and lose screen
        x = screenWidth / 2 - screenWidth / 4;
        y = screenHeight / 2 - screenHeight / 6 ;
        winButton = new Button(x, y, (float) screenWidth/2, (float) screenHeight/3, winTexture, winTexture);
        loseButton = new Button(x, y, (float) screenWidth/2, (float) screenHeight/3, loseTexture, loseTexture);

        setMenuState();

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
            if(playButton.wasDown()){
                SoundManager.CLICK_BUTTON.play();
                state = menuState.LEVEL;
            }

            // Update quit button
            quitButton.update(Gdx.input.isTouched(), Gdx.input.getX(), Gdx.input.getY());
            if (quitButton.wasDown()) {
                SoundManager.QUIT_BUTTON.play();
                Gdx.app.exit();
            }
        } else if (state == menuState.LEVEL) {
            backButton.update(Gdx.input.isTouched(), Gdx.input.getX(), Gdx.input.getY());
            if (backButton.wasDown()){
                SoundManager.CLICK_BUTTON.play();
                state = menuState.MAIN;
            }

            level1Button.update(Gdx.input.isTouched(), Gdx.input.getX(), Gdx.input.getY());
            if(level1Button.wasDown()){
                SoundManager.START_BUTTON.play();
                SoundManager.StopBackgroundMusic();
                game.game = new GameScreen(game, GameScreen.Difficulty.EASY);
                game.setScreen(game.game);
            }
            level2Button.update(Gdx.input.isTouched(), Gdx.input.getX(), Gdx.input.getY());
            if(level2Button.wasDown()){
                SoundManager.START_BUTTON.play();
                SoundManager.StopBackgroundMusic();
                game.game = new GameScreen(game, GameScreen.Difficulty.MEDIUM);
                game.setScreen(game.game);
            }
            level3Button.update(Gdx.input.isTouched(), Gdx.input.getX(), Gdx.input.getY());
            if(level3Button.wasDown()){
                SoundManager.START_BUTTON.play();
                SoundManager.StopBackgroundMusic();
                game.game = new GameScreen(game, GameScreen.Difficulty.HARD);
                game.setScreen(game.game);
            }
        } else if (state == menuState.WIN) {
            winButton.update(Gdx.input.isTouched(), Gdx.input.getX(), Gdx.input.getY());
            if(winButton.wasDown()){
                SoundManager.CLICK_BUTTON.play();
                state = menuState.MAIN;
            }
        } else if (state == menuState.LOSE) {
            loseButton.update(Gdx.input.isTouched(), Gdx.input.getX(), Gdx.input.getY());
            if (loseButton.wasDown()) {
                SoundManager.CLICK_BUTTON.play();
                state = menuState.MAIN;
            }
        }

        batch.begin();
        if(state == menuState.MAIN){
            playButton.draw(batch);
            quitButton.draw(batch);
            title.draw(batch);
        } else if (state == menuState.LEVEL) {
            backButton.draw(batch);
            level1Button.draw(batch);
            level2Button.draw(batch);
            level3Button.draw(batch);
            levelSelect.draw(batch);
        } else if (state == menuState.WIN) {
            winButton.draw(batch);
        } else if (state == menuState.LOSE) {
            loseButton.draw(batch);
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
        winButton.dispose();
        loseButton.dispose();
    }

    public void setMenuState() {
        switch (this.game.getState()) {
            case WIN:
                this.state = menuState.WIN;
                break;
            case LOSE:
                this.state = menuState.LOSE;
                break;
            default:
                this.state = menuState.MAIN;
                break;
        }
    }
}
