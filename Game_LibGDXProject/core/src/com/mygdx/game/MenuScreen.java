package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Align;

import java.util.Locale;

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

    SoundManager soundManager;

    // Heads-Up Display
    BitmapFont font;
    float hudVerticalMargin, hudHorizontalMargin, hudCenterX, hudRow1Y, hudRow2Y, hudRow3Y, hudSectionWidth;


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
        //winButton = new Button(x, y, (float) screenWidth/2, (float) screenHeight/3, winTexture, winTexture);
        //loseButton = new Button(x, y, (float) screenWidth/2, (float) screenHeight/3, loseTexture, loseTexture);

        // HUD
        prepareHUD();

        setMenuState();

        soundManager = new SoundManager();

    }

    private void prepareHUD() {
        // Create a BitmapFont from our font file
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("font/EdgeOfTheGalaxyRegular-OVEa6.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 72;
        fontParameter.borderWidth = 3.6f;
        fontParameter.color = new Color(255,165,0,0.5f);
        fontParameter.borderColor = new Color(0,0,0,0.3f);

        font = fontGenerator.generateFont(fontParameter);

        // scale the font to fit world
        font.getData().setScale(0.8f);

        // calculate hud margins, etc.
        hudVerticalMargin = Gdx.graphics.getHeight() / 8;
        hudRow1Y = hudVerticalMargin * 5;
        hudRow2Y = hudRow1Y - hudVerticalMargin;
        hudRow3Y = hudRow2Y - hudVerticalMargin + (hudVerticalMargin / 4);

        hudHorizontalMargin = Gdx.graphics.getWidth() / 4;
        hudCenterX = hudHorizontalMargin;
        hudSectionWidth = Gdx.graphics.getWidth() / 2;
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
                soundManager.stopBackgroundMusic();
                game.game = new GameScreen(game, GameScreen.Difficulty.EASY);
                game.setScreen(game.game);
                game.score = 0;
            }
            level2Button.update(Gdx.input.isTouched(), Gdx.input.getX(), Gdx.input.getY());
            if(level2Button.wasDown()){
                SoundManager.START_BUTTON.play();
                soundManager.stopBackgroundMusic();
                game.game = new GameScreen(game, GameScreen.Difficulty.MEDIUM);
                game.setScreen(game.game);
                game.score = 0;
            }
            level3Button.update(Gdx.input.isTouched(), Gdx.input.getX(), Gdx.input.getY());
            if(level3Button.wasDown()){
                SoundManager.START_BUTTON.play();
                soundManager.stopBackgroundMusic();
                game.game = new GameScreen(game, GameScreen.Difficulty.HARD);
                game.setScreen(game.game);
                game.score = 0;
            }
        } else if (state == menuState.WIN) {
            if(Gdx.input.isTouched()){
                SoundManager.CLICK_BUTTON.play();
                state = menuState.MAIN;
            }
        } else if (state == menuState.LOSE) {
            if (Gdx.input.isTouched()) {
                SoundManager.CLICK_BUTTON.play();
                state = menuState.MAIN;
            }
        }

        batch.begin();

        renderHUD();

        if(state == menuState.MAIN){
            playButton.draw(batch);
            quitButton.draw(batch);
            title.draw(batch);
            soundManager.playBackgroundMusic();
        } else if (state == menuState.LEVEL) {
            backButton.draw(batch);
            level1Button.draw(batch);
            level2Button.draw(batch);
            level3Button.draw(batch);
            levelSelect.draw(batch);
        } else if (state == menuState.WIN) {
            soundManager.stopBossMusic();
        } else if (state == menuState.LOSE) {
            soundManager.stopBossMusic();
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
        soundManager.dispose();
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

    private void renderHUD() {
        if (this.state == menuState.WIN) {
            font.draw(batch, "YOU WIN", hudCenterX, hudRow1Y, hudSectionWidth, Align.center, false);
            font.draw(batch, "Score", hudCenterX, hudRow2Y, hudSectionWidth, Align.center, false);
            font.draw(batch, String.format(Locale.getDefault(), "%06d", game.score), hudCenterX, hudRow3Y, hudSectionWidth, Align.center, false);
        } else if (this.state == menuState.LOSE) {
            font.draw(batch, "TRY AGAIN", hudCenterX, hudRow1Y, hudSectionWidth, Align.center, false);
            font.draw(batch, "Score", hudCenterX, hudRow2Y, hudSectionWidth, Align.center, false);
            font.draw(batch, String.format(Locale.getDefault(), "%06d", game.score), hudCenterX, hudRow3Y, hudSectionWidth, Align.center, false);
        } else {
            return;
        }

    }
}
