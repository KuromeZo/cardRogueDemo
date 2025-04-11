package com.mygdx.game.logic.cards;

import com.mygdx.game.logic.entities.Entity;

import java.util.Collections;
import java.util.List;

public class PlayCards {
    private final Deck deck;
    private final Entity player;
    private final Entity enemy;

    public PlayCards(Deck deck, Entity player, Entity enemy) {
        this.deck = deck;
        this.player = player;
        this.enemy = enemy;
    }

    public void applyEffects() {
        List<Card> fieldCards = deck.getField();

        for (Card card : fieldCards) {
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
            }

            card.applyEffect(target, attacker);
            card.setPlayerOwnership(false);

            System.out.println("Applied effect of " + card.getName() + " to " + target.getName());
        }

        deck.getHand().removeAll(deck.getField());

        deck.getField().clear(); // Очищаем поле после применения эффектов
        deck.getField().addAll(Collections.nCopies(4, null));
    }
}
