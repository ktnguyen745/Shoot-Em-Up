package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio;


public class SoundManager {

    // create all sounds
    public Sound click_button = Gdx.audio.newSound(Gdx.files.internal("sound/Bleep_03.ogg"));
    public Sound start_button = Gdx.audio.newSound(Gdx.files.internal("sound/Complete_02.ogg"));
    public Sound player_shoot = Gdx.audio.newSound(Gdx.files.internal("sound/Click_04.ogg"));
    public Sound enemy_shoot = Gdx.audio.newSound(Gdx.files.internal("sound/Sequence_01.ogg"));
    public Sound enemy_collision = Gdx.audio.newSound(Gdx.files.internal("sound/Execute_01.ogg"));
    public Sound player_collision = Gdx.audio.newSound(Gdx.files.internal("sound/Denied_01.ogg"));

    // dispose all sounds
    public void dispose() {
        click_button.dispose();
        start_button.dispose();
        player_shoot.dispose();
        enemy_shoot.dispose();
        enemy_collision.dispose();
        player_collision.dispose();
    }

}
