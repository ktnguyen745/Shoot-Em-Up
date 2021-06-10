package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;


public class SoundManager {

    // initiate all sounds
    public static Sound CLICK_BUTTON = Gdx.audio.newSound(Gdx.files.internal("sound/Bleep_03.ogg"));
    public static Sound START_BUTTON = Gdx.audio.newSound(Gdx.files.internal("sound/Complete_02.ogg"));
    public static Sound DESTROYED_COLLISION = Gdx.audio.newSound(Gdx.files.internal("sound/distant_explosion.wav"));
    public static Sound ENEMY_SHOOT = Gdx.audio.newSound(Gdx.files.internal("sound/Sequence_01.ogg"));
    public static Sound ENEMY_SHOOT_2 = Gdx.audio.newSound(Gdx.files.internal("sound/Sequence_07.ogg"));
    public static Sound ENEMY_SHOOT_3 = Gdx.audio.newSound(Gdx.files.internal("sound/Sequence_02.ogg"));
    public static Sound ENEMY_SHOOT_4 = Gdx.audio.newSound(Gdx.files.internal("sound/Data_Point_02.ogg"));
    public static Sound PLAYER_SHOOT = Gdx.audio.newSound(Gdx.files.internal("sound/Execute_01.ogg"));
    public static Sound HIT_COLLISION = Gdx.audio.newSound(Gdx.files.internal("sound/explosion.wav"));
    public static Sound QUIT_BUTTON = Gdx.audio.newSound(Gdx.files.internal("sound/Data_Point_01.ogg"));
    public static Sound BOSS_SHOOT = Gdx.audio.newSound(Gdx.files.internal("sound/Sequence_03.ogg"));
    public static Sound WIN = Gdx.audio.newSound(Gdx.files.internal("sound/level_complete.wav"));
    public static Sound LOSE = Gdx.audio.newSound(Gdx.files.internal("sound/Denied_02.ogg"));
    public static Sound BOSS_LAUGH = Gdx.audio.newSound(Gdx.files.internal("sound/Sequence_05.ogg"));

    // background music
    private static Music BACKGROUND_MUSIC = Gdx.audio.newMusic(Gdx.files.internal("sound/Laser Age level 1 theme.mp3"));

    // boss music
    private static Music BOSS_MUSIC = Gdx.audio.newMusic(Gdx.files.internal("sound/Laser Age level 3 theme.mp3"));

    public void playBackgroundMusic() {
        if (BOSS_MUSIC.isPlaying()) {
            stopBossMusic();
        }
        if (!BACKGROUND_MUSIC.isPlaying()) {
            BACKGROUND_MUSIC.isLooping();
            BACKGROUND_MUSIC.setVolume(0.2f);
            BACKGROUND_MUSIC.play();
        }
    }

    public void playBossMusic() {
        if (BACKGROUND_MUSIC.isPlaying()) {
            stopBackgroundMusic();
        }
        BOSS_MUSIC.isLooping();
        BOSS_MUSIC.setVolume(0.8f);
        if (!BOSS_MUSIC.isPlaying()) {
            BOSS_MUSIC.play();
        }
    }

    public void stopBossMusic() {BOSS_MUSIC.stop();}

    public void pauseBackgroundMusic() {
        BACKGROUND_MUSIC.pause();
    }

    public void stopBackgroundMusic() {
        BACKGROUND_MUSIC.stop();
    }

    // dispose all sounds
    public void dispose() {
        CLICK_BUTTON.dispose();
        START_BUTTON.dispose();
        PLAYER_SHOOT.dispose();
        ENEMY_SHOOT.dispose();
        DESTROYED_COLLISION.dispose();
        HIT_COLLISION.dispose();
        ENEMY_SHOOT_2.dispose();
        ENEMY_SHOOT_3.dispose();
        ENEMY_SHOOT_4.dispose();
        BACKGROUND_MUSIC.dispose();
        QUIT_BUTTON.dispose();
        BOSS_SHOOT.dispose();
        WIN.dispose();
        LOSE.dispose();
        BOSS_MUSIC.dispose();
        BOSS_LAUGH.dispose();
    }

}
