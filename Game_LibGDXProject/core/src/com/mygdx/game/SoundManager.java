package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;


public class SoundManager {

    // initiate all sounds
    public static Sound click_button = Gdx.audio.newSound(Gdx.files.internal("sound/Bleep_03.ogg"));
    public static Sound start_button = Gdx.audio.newSound(Gdx.files.internal("sound/Complete_02.ogg"));
    public static Sound enemy_collision = Gdx.audio.newSound(Gdx.files.internal("sound/Click_04.ogg"));
    public static Sound enemy_shoot = Gdx.audio.newSound(Gdx.files.internal("sound/Sequence_01.ogg"));
    public static Sound enemy_shoot2 = Gdx.audio.newSound(Gdx.files.internal("sound/Sequence_07.ogg"));
    public static Sound player_shoot = Gdx.audio.newSound(Gdx.files.internal("sound/Execute_01.ogg"));
    public static Sound player_collision = Gdx.audio.newSound(Gdx.files.internal("sound/Denied_01.ogg"));

    // dispose all sounds
    public void dispose() {
        click_button.dispose();
        start_button.dispose();
        player_shoot.dispose();
        enemy_shoot.dispose();
        enemy_collision.dispose();
        player_collision.dispose();
        enemy_shoot2.dispose();
    }

}
