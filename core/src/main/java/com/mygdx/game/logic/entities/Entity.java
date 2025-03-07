package com.mygdx.game.logic.entities;

import com.badlogic.gdx.Gdx;

public abstract class Entity {
    protected String name;
    protected int maxHealth;
    protected int currentHealth;
    protected int armor;
    protected boolean stunned;
    protected boolean isDodge;

    public Entity(String name, int maxHealth) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.armor = 0;
        this.stunned = false;
        this.isDodge = false;
    }

    public void takeDamage(int damage) {
        if (armor > 0) {
            int absorbed = Math.min(damage, armor);
            armor -= absorbed;
            damage -= absorbed;
        }

        if (damage > 0) {
            currentHealth -= damage;
        }

        if (currentHealth < 0) {
            currentHealth = 0;
            Gdx.app.exit();
        }
    }

    public void heal(int amount) {
        if(currentHealth > 0) {
            currentHealth += amount;
            if (currentHealth > maxHealth) {
                currentHealth = maxHealth;
            }
        }
    }

    public void isDodging(){
        isDodge = true;
    }

    public boolean isInDodge() {
        return isDodge;
    }

    public void isNotDodging(){
        isDodge = false;
    }

    public void addArmor(int amount) {
        armor += amount;
    }

    public void stun() {
        stunned = true;
    }

    public void unstun() {
        stunned = false;
    }

    public boolean isStunned() {
        return stunned;
    }

    public String getName() {
        return name;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getArmor() {
        return armor;
    }

    public boolean isAlive() {
        return currentHealth > 0;
    }
}
