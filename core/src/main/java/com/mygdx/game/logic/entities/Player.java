package com.mygdx.game.logic.entities;

public class Player extends Entity{
    private int comboMultiplier;
    private float resistance;
    private boolean inCover;
    private float x, y, width, height;

    public Player(String name, int maxHealth, float resistance, boolean inCover) {
        super(name, maxHealth);
        this.comboMultiplier = 1;
        this.inCover = inCover;
        this.resistance = resistance;

    }

    public void setResistance(float resistance) {
        this.resistance = resistance;
    }

    public float getResistance() {
        return resistance;
    }

    public void increaseCombo() {
        comboMultiplier++;
    }

    public void resetCombo() {
        comboMultiplier = 1;
    }

    public int getComboMultiplier() {
        return comboMultiplier;
    }

    public void addCoverProtection() {
        inCover = true;
    }

    public void exitCover() {
        inCover = false;
    }

    public boolean isInCover() {
        return inCover;
    }

    public void setPosition(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public float getX() { return x; }
    public float getY() { return y; }
    public float getWidth() { return width; }
    public float getHeight() { return height; }
}
