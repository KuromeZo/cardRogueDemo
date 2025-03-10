package com.mygdx.game.logic.cards;

import com.mygdx.game.logic.entities.Enemy;
import com.mygdx.game.logic.entities.Entity;
import com.mygdx.game.logic.entities.Player;

public class RangedAttackCard extends Card {
    private int flatDamage;  // Плоский урон
    private float critChance;  // Шанс на крит
    private float critMultiplier;  // Множитель критического урона
    private float comboMultiplier;  // Множитель комбо
    private float missChance;  // Шанс промаха
    private int numHits;  // Количество выстрелов

    public RangedAttackCard(String name, String description,boolean isPlayer, int flatDamage,
                            float critChance, float critMultiplier, float comboMultiplier,
                            float missChance, int numHits) {
        super(name, description, isPlayer);
        this.flatDamage = flatDamage;
        this.critChance = critChance;
        this.critMultiplier = critMultiplier;
        this.comboMultiplier = comboMultiplier;
        this.missChance = missChance;
        this.numHits = numHits;
    }

    @Override
    public void applyEffect(Entity target, Entity attacker) {

        if (attacker instanceof Enemy && ((Enemy) attacker).hasMeleeWeapon()) {
            System.out.println("not range enemy");
            return;
        }
        for (int i = 0; i < numHits; i++) {
            if (Math.random() < missChance) {
                System.out.println(target.getName() + " dodged the attack!");
            } else {
                if (target instanceof Player && ((Player) target).isInCover()) {
                    if (Math.random() < 0.85) {
                        System.out.println(target.getName() + " dodged the attack in cover!");
                    } else {
                        int finalDamage = flatDamage;
                        if (Math.random() < critChance) {
                            finalDamage *= critMultiplier;
                        }
                        target.takeDamage(finalDamage);
                        System.out.println(target.getName() + " takes " + finalDamage + " damage!");
                    }
                } else {
                    int finalDamage = flatDamage;
                    if (Math.random() < critChance) {
                        finalDamage *= critMultiplier;
                    }
                    target.takeDamage(finalDamage);
                    System.out.println(target.getName() + " takes " + finalDamage + " damage!");
                }
            }
        }
        if (target instanceof Player && ((Player) target).isInCover()) {
            ((Player) target).exitCover();
        }
    }
}
