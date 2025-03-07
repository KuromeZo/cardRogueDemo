package com.mygdx.game.logic.entities;

public class Enemy extends Entity{
    private boolean hasMeleeWeapon;
    private boolean hasRangedWeapon;
    private float x, y, width, height;

    public Enemy(String name, int maxHealth, boolean hasMeleeWeapon, boolean hasRangedWeapon) {
        super(name, maxHealth);
        this.hasMeleeWeapon = hasMeleeWeapon;
        this.hasRangedWeapon = hasRangedWeapon;
    }

    public boolean hasMeleeWeapon() {
        return hasMeleeWeapon;
    }

    public boolean hasRangedWeapon() {
        return hasRangedWeapon;
    }

    public void setPosition(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void addArmor(int amount) {
        this.armor += amount;
        System.out.println(getName() + " gains " + amount + " armor!");
    }

    public float getX() { return x; }
    public float getY() { return y; }
    public float getWidth() { return width; }
    public float getHeight() { return height; }
}
