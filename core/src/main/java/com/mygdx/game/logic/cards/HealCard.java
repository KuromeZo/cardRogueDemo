package com.mygdx.game.logic.cards;

import com.mygdx.game.logic.entities.Entity;

public class HealCard extends Card {
    private int healAmount;

    public HealCard(String name, String description, boolean isPlayer, int healAmount) {
        super(name, description, isPlayer);
        this.healAmount = healAmount;
    }

    @Override
    public void applyEffect(Entity target, Entity attacker) {
        target.heal(healAmount);
        System.out.println("Heal" + target.getName());
    }
}
