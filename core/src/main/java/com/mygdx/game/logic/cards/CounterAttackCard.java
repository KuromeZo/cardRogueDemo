package com.mygdx.game.logic.cards;

import com.mygdx.game.logic.entities.Enemy;
import com.mygdx.game.logic.entities.Entity;
import com.mygdx.game.logic.entities.Player;


public class CounterAttackCard extends Card {
    float resist = 0.7f;
    public CounterAttackCard(String name, String description, boolean isPlayer) {
        super(name, description, isPlayer);
    }

    @Override
    public void applyEffect(Entity target, Entity attacker) {
        if(target instanceof Player){
            ((Player) target).setResistance(resist);
            System.out.println(target.getName() + " Resist: " + resist);
        } else if(target instanceof Enemy) {
            int health = target.getCurrentHealth();
            int armorGain = (int) (health * (Math.random() * 0.1 + 0.05));
            target.addArmor(armorGain);
        }
    }
}
