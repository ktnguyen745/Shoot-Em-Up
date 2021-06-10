package com.mygdx.game;

public class ShipBuilder {

    GameScreen.Difficulty difficulty;
    private float movespeedMultiplier;
    private float infiniteMultiplier = 1f;
    private float shipsBuilt = 0;

    public ShipBuilder(GameScreen.Difficulty difficulty){
        this.difficulty = difficulty;
        if(difficulty == GameScreen.Difficulty.EASY || difficulty == GameScreen.Difficulty.INFINITE){
            movespeedMultiplier = 1f;
        } else if (difficulty == GameScreen.Difficulty.MEDIUM){
            movespeedMultiplier = 1.25f;
        } else {
            movespeedMultiplier = 1.5f;
        }
    }

    public EnemyShip buildEnemy(){
        if(difficulty == GameScreen.Difficulty.INFINITE) update();
        EnemyShip enemyShip = new EnemyShip(40 * (movespeedMultiplier * infiniteMultiplier),
                (int) Math.floor(2 * infiniteMultiplier), 10, 10,
                (float) Math.random() * 62 + 5, (float) Math.random() * 64 + 66,
                2f / infiniteMultiplier, "enemy_a.png", "shield2.png");
        return enemyShip;
    }

    public EnemyInvisible buildInvisibleEnemy(){
        if(difficulty == GameScreen.Difficulty.INFINITE) update();
        EnemyInvisible invisible = new EnemyInvisible(20 * (movespeedMultiplier * infiniteMultiplier),
                (int) Math.floor(3 * infiniteMultiplier), 15, 10,
                (float) Math.random() * 62 + 5, (float) Math.random() * 64 + 66,
                2.5f / infiniteMultiplier, "enemy_invisible.png", "shield2.png");
        return invisible;
    }

    public EnemyTripleshot buildTripleShot(){
        if(difficulty == GameScreen.Difficulty.INFINITE) update();
        EnemyTripleshot enemyTripleshot = new EnemyTripleshot(24 * (movespeedMultiplier * infiniteMultiplier),
                (int) Math.floor(4 * infiniteMultiplier), 15, 11,
                (float) Math.random() * 62 + 5, (float) Math.random() * 64 + 66,
                2.25f / infiniteMultiplier, "enemy_b.png", "shield2.png");
        return enemyTripleshot;
    }

    public EnemyRepeater buildRepeater(){
        if(difficulty == GameScreen.Difficulty.INFINITE) update();
        EnemyRepeater repeater = new EnemyRepeater(32 * (movespeedMultiplier * infiniteMultiplier),
                (int) Math.floor(3 * infiniteMultiplier), 12, 12,
                (float) Math.random() * 62 + 5, (float) Math.random() * 64 + 66,
                2.5f / infiniteMultiplier, "enemy_c.png", "shield2.png");
        return repeater;
    }

    public BossShip buildBoss(){
        BossShip boss = new BossShip(30 * (movespeedMultiplier * infiniteMultiplier),
                (int) Math.floor(30 * infiniteMultiplier), 38, 30,
                (float) Math.random() * 62 + 5, (float) Math.random() * 64 + 66,
                1.5f / infiniteMultiplier, "boss_ship.png", "shield2.png", difficulty);
        return boss;
    }

    private void update(){
        shipsBuilt++;
        if(shipsBuilt % 10 == 0){
            infiniteMultiplier += 0.15;
        }
    }
}
