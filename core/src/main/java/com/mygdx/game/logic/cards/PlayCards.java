package com.mygdx.game.logic.cards;

import com.mygdx.game.logic.entities.Entity;

public class PlayCards {
    private static Deck deck;
    private static Entity player;
    private static Entity enemy;

    public PlayCards(Deck deck, Entity player, Entity enemy) {
        this.deck = deck;
        this.player = player;
        this.enemy = enemy;
    }

    public static void applyEffects() {
        for (Card card : deck.getField()) {
            Entity target = null;
            Entity attacker = null;

            if(card.isPlayer) {
                if (card instanceof MeleeAttackCard || card instanceof RangedAttackCard) {
                    target = enemy;
                    attacker = player;
                } else {
                    target = player;
                    attacker = enemy;
                }
            } else {
                if(enemy.isStunned()){
                    break;
                }
                if (card instanceof MeleeAttackCard || card instanceof RangedAttackCard) {
                    target = player;
                    attacker = enemy;
                } else {
                    target = enemy;
                    attacker = player;
                }
            }


            card.applyEffect(target, attacker);

            card.setPlayerF();

            System.out.println("Applied effect of " + card.getName() + " to " + target.getName());
        }

        deck.getField().clear(); // Очищаем поле после применения эффектов
    }
}
