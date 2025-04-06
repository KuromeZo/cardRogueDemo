package com.mygdx.game.logic.cards;

import com.mygdx.game.logic.entities.Entity;

public abstract class Card implements Cloneable { // Добавил Cloneable
    protected String name;
    protected String description;
    protected boolean isPlayer;
    private float x, y;
    private float width = 75;
    private float height = 100;

    public Card(String name, String description, boolean isPlayer) {
        this.name = name;
        this.description = description;
        this.isPlayer = isPlayer;
    }

    @Override
    public Card clone() {
        try {
            return (Card) super.clone(); // Теперь будет работать
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Cloning not supported for Card", e);
        }
    }

    public abstract void applyEffect(Entity target, Entity attacker);

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isPlayerCard() {
        return isPlayer;
    }

    public void setPlayerOwnership(boolean isPlayer) {
        this.isPlayer = isPlayer;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}
