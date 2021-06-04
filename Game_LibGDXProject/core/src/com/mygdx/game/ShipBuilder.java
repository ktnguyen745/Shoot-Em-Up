package com.mygdx.game;

public class ShipBuilder {

    // TODO: movementSpeed changes according to the difficulty

    public static EnemyShip buildEnemy(){
        EnemyShip enemyShip = new EnemyShip(24, 2, 10, 10,
                (float) Math.random() * 62 + 5, (float) Math.random() * 64 + 66,
                1.2f, "enemy_a.png", "shield2.png");
        return enemyShip;
    }

    public static EnemyTripleshot buildTripleShot(){
        EnemyTripleshot enemyTripleshot = new EnemyTripleshot(36, 4, 15, 11,
                (float) Math.random() * 62 + 5, (float) Math.random() * 64 + 66,
                2f, "enemy_b.png", "shield2.png");
        return enemyTripleshot;
    }

    public static BossShip buildBoss(GameScreen.Difficulty difficulty){
        BossShip boss = new BossShip(48, 30, 38, 30,
                (float) Math.random() * 62 + 5, (float) Math.random() * 64 + 66,
                1.5f, "boss_ship.png", "shield2.png", difficulty);
        return boss;
    }
}
