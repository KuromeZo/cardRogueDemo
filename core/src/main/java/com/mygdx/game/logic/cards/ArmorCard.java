package com.mygdx.game.logic.cards;

import com.mygdx.game.logic.entities.Entity;

public class ArmorCard extends Card {
    private int armorValue;  // Сколько брони даёт карта

    public ArmorCard(String name, String description, boolean isPlayer, int armorValue) {
        super(name, description, isPlayer);
        this.armorValue = armorValue;
    }

    @Override
    public void applyEffect(Entity target, Entity attacker) {
        target.addArmor(armorValue);
        System.out.println(target.getName() + " Armor: " + armorValue);
    }
}
