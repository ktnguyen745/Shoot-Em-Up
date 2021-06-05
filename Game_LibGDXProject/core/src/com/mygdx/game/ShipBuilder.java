package com.mygdx.game;

public class ShipBuilder {

    GameScreen.Difficulty difficulty;
    private float movespeedMultiplier;

    public ShipBuilder(GameScreen.Difficulty difficulty){
        this.difficulty = difficulty;
        if(difficulty == GameScreen.Difficulty.EASY){
            movespeedMultiplier = 1f;
        } else if (difficulty == GameScreen.Difficulty.MEDIUM){
            movespeedMultiplier = 1.25f;
        } else {
            movespeedMultiplier = 1.5f;
        }
    }

    public EnemyShip buildEnemy(){
        EnemyShip enemyShip = new EnemyShip(40 * movespeedMultiplier, 2, 10, 10,
                (float) Math.random() * 62 + 5, (float) Math.random() * 64 + 66,
                2f, "enemy_a.png", "shield2.png");
        return enemyShip;
    }

    public EnemyInvisible buildInvisibleEnemy(){
        EnemyInvisible invisible = new EnemyInvisible(20 * movespeedMultiplier, 4, 15, 10,
                (float) Math.random() * 62 + 5, (float) Math.random() * 64 + 66,
                2.5f, "enemy_invisible.png", "shield2.png");
        return invisible;
    }

    public EnemyTripleshot buildTripleShot(){
        EnemyTripleshot enemyTripleshot = new EnemyTripleshot(24 * movespeedMultiplier, 4, 15, 11,
                (float) Math.random() * 62 + 5, (float) Math.random() * 64 + 66,
                2.25f, "enemy_b.png", "shield2.png");
        return enemyTripleshot;
    }

    public BossShip buildBoss(){
        BossShip boss = new BossShip(30 * movespeedMultiplier, 30, 38, 30,
                (float) Math.random() * 62 + 5, (float) Math.random() * 64 + 66,
                1.5f, "boss_ship.png", "shield2.png", difficulty);
        return boss;
    }
}
