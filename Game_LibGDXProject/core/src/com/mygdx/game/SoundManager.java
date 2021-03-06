package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;


public class SoundManager {

    // initiate all sounds
    public static Sound CLICK_BUTTON = Gdx.audio.newSound(Gdx.files.internal("sound/Bleep_03.ogg"));
    public static Sound START_BUTTON = Gdx.audio.newSound(Gdx.files.internal("sound/Complete_02.ogg"));
    public static Sound ENEMY_COLLISION = Gdx.audio.newSound(Gdx.files.internal("sound/Click_04.ogg"));
    public static Sound ENEMY_SHOOT = Gdx.audio.newSound(Gdx.files.internal("sound/Sequence_01.ogg"));
    public static Sound ENEMY_SHOOT_2 = Gdx.audio.newSound(Gdx.files.internal("sound/Sequence_07.ogg"));
    public static Sound PLAYER_SHOOT = Gdx.audio.newSound(Gdx.files.internal("sound/Execute_01.ogg"));
    public static Sound PLAYER_COLLISION = Gdx.audio.newSound(Gdx.files.internal("sound/Denied_01.ogg"));

    // background music
    private static Music BACKGROUND_MUSIC = Gdx.audio.newMusic(Gdx.files.internal("sound/Andromeda Journey.mp3"));

    public static void PlayBackgroundMusic() {
        if (!BACKGROUND_MUSIC.isPlaying()) {
            BACKGROUND_MUSIC.isLooping();
            BACKGROUND_MUSIC.setVolume(0.6f);
            BACKGROUND_MUSIC.play();
        }
    }

    public static void PauseBackgroundMusic() {
        BACKGROUND_MUSIC.pause();
    }

    public static void StopBackgroundMusic() {
        BACKGROUND_MUSIC.stop();
    }


    // dispose all sounds
    public static void Dispose() {
        CLICK_BUTTON.dispose();
        START_BUTTON.dispose();
        PLAYER_SHOOT.dispose();
        ENEMY_SHOOT.dispose();
        ENEMY_COLLISION.dispose();
        PLAYER_COLLISION.dispose();
        ENEMY_SHOOT_2.dispose();
        BACKGROUND_MUSIC.dispose();
    }

}
