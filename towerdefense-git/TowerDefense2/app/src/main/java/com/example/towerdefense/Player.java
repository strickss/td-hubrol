package com.example.towerdefense;

/**
 * Created by Brieuc on 24-11-15.
 */
public class Player {
    private int gold;
    private int income;
    private int life;

    public Player(int gold, int income, int life) {
        this.gold = gold;
        this.income = income;
        this.life = life;
    }

    public int getGold(){
        return this.gold;
    }

    public void increaseIncome(int inc){
        this.income = this.income + inc;
    }

    public void cost(int i) {
        this.gold = this.gold - i;
    }

    public void increaseGold(int value) {
        this.cost(-value);
    }

    public int getIncome() {
        return income;
    }

    public void looseLife() {
        this.life = this.life - 1;
    }
}
