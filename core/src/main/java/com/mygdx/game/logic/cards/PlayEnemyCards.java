package com.mygdx.game.logic.cards;

import com.mygdx.game.logic.entities.Entity;
import java.util.Collections;
import java.util.List;

public class PlayEnemyCards {
    private final EnemyDeck deck;
    private final Entity player;
    private final Entity enemy;

    public PlayEnemyCards(EnemyDeck deck, Entity player, Entity enemy) {
        this.deck = deck;
        this.player = player;
        this.enemy = enemy;
    }

    public void applyEffects(){
        List<Card> fieldCards = deck.getFieldE();
        System.out.println("---");
        // Выводим имена карт на поле (проверяем, что карта не null)
        for (Card card : deck.getFieldE()) {
            if (card != null) {
                System.out.println(card.getName());
            }
        }
        System.out.println("---");

        // Применяем эффекты карт
        for(Card card : fieldCards){
            if (card == null) {
                continue;
            }
            if(enemy.isStunned()){
                break;
            }
            Entity target = null;
            Entity attacker = null;

            if (!card.isPlayerCard()) {
                if (card instanceof MeleeAttackCard || card instanceof RangedAttackCard) {
                    target = enemy;
                    attacker = player;
                } else {
                    target = player;
                    attacker = enemy;
                }

                card.applyEffect(attacker, target);  // Применяем эффект карты
            }
        }

        // Очищаем поле и заполняем его пустыми картами
        fieldCards.clear();
        fieldCards.addAll(Collections.nCopies(4, null));

        // Проверяем, что поле очищено (если нужно, выводим список карт)
        System.out.println("---");
        for (Card card : deck.getFieldE()) {
            if (card != null) {
                System.out.println(card.getName());
            }
        }
        System.out.println("---");
    }

}
