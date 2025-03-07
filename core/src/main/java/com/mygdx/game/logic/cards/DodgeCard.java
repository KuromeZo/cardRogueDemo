package com.mygdx.game.logic.cards;

import com.mygdx.game.logic.entities.Entity;

public class DodgeCard extends Card {
    private float dodgeChance;

    public DodgeCard(String name, String description, boolean isPlayer, float dodgeChance) {
        super(name, description, isPlayer);
        this.dodgeChance = dodgeChance;
    }
    @Override
    public void applyEffect(Entity target, Entity attacker) {
        System.out.println("Dodge - " + target.getName());
        target.isDodging();
    }
}
