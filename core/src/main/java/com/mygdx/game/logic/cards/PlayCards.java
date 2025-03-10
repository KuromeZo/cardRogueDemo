package com.mygdx.game.logic.cards;

import com.mygdx.game.logic.entities.Entity;

import java.util.Collections;

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
            if (card == null) {
                continue;
            }
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

        deck.getHand().removeAll(deck.getField());

        deck.getField().clear(); // Очищаем поле после применения эффектов
        deck.getField().addAll(Collections.nCopies(4, null));
    }
}
