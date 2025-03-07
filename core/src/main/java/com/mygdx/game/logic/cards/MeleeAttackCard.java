package com.mygdx.game.logic.cards;

import com.mygdx.game.logic.entities.Enemy;
import com.mygdx.game.logic.entities.Entity;
import com.mygdx.game.logic.entities.Player;
import com.sun.imageio.plugins.tiff.TIFFDecompressor;

public class MeleeAttackCard extends Card {
    private int flatDamage;  // Плоский урон
    private float critChance;  // Шанс на крит
    private float critMultiplier;  // Множитель критического урона
    private float comboMultiplier;  // Множитель комбо
    private float missChance;  // Шанс промаха
    private int numHits;  // Количество ударов

    public MeleeAttackCard(String name, String description,boolean isPlayer, int flatDamage,
                           float critChance, float critMultiplier, float comboMultiplier,
                           float missChance, int numHits){
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
        if(target.isInDodge()){
            missChance = missChance + 0.27f;
        }
        if(attacker instanceof Enemy && ((Enemy) attacker).hasRangedWeapon()){
            return;
        }
        for (int i = 0; i < numHits; i++) {
            if (Math.random() < missChance) {
                System.out.println(target.getName() + " dodged the attack!");
            } else {
                int finalDamage = flatDamage;
                int backDamage = flatDamage;
                if (Math.random() < critChance) {
                    finalDamage *= critMultiplier;
                }
                if(target instanceof Player && ((Player) target).getResistance() != 0.0f){
                    finalDamage = (int) (finalDamage - finalDamage*((Player) target).getResistance());
                    target.takeDamage(finalDamage);
                    System.out.println(target.getName() + " takes " + finalDamage + " damage!");
                    backDamage = (int) (backDamage*1.2f);
                    attacker.takeDamage(backDamage);
                    System.out.println(attacker.getName() + " (attacker) takes " + backDamage + " damage!");
                    ((Player) target).setResistance(0.0f);

                    if (Math.random() < 0.2f) {
                        attacker.stun();
                        System.out.println(attacker.getName() + " is stunned!");
                    }
                } else {
                    target.takeDamage(finalDamage);
                    System.out.println(target.getName() + " takes " + finalDamage + " damage!");
                }
            }
        }
        if(target.isInDodge()){
            missChance = missChance - 0.27f;
            target.isNotDodging();
        }
    }
}
