package com.mygdx.game.logic.cards;

import com.mygdx.game.logic.entities.Entity;
import java.util.Collections;

public class PlayEnemyCards {
    private static EnemyDeck deck;
    private static Entity player;
    private static Entity enemy;

    public PlayEnemyCards(EnemyDeck deck, Entity player, Entity enemy) {
        this.deck = deck;
        this.player = player;
        this.enemy = enemy;
    }

    public static void applyEffects(){
        System.out.println("---");
        // Выводим имена карт на поле (проверяем, что карта не null)
        for (Card card : deck.getFieldE()) {
            if (card != null) {
                System.out.println(card.getName());
            }
        }
        System.out.println("---");

        // Применяем эффекты карт
        for(Card card : deck.getFieldE()){
            if (card == null) {
                continue;  // Пропускаем пустые карты
            }
            if(enemy.isStunned()){
                break;  // Если враг оглушен, пропускаем ход
            }
            Entity target = null;
            Entity attacker = null;

            // Логика для применения эффектов карты
            if (!card.isPlayer) {  // Если карта принадлежит врагу
                if (card instanceof MeleeAttackCard || card instanceof RangedAttackCard) {
                    target = enemy;  // Цель - игрок
                    attacker = player;  // Атакующий - враг
                } else {
                    target = player;  // Для других карт враг атакует сам себя
                    attacker = enemy;  // Игрок становится атакующим
                }

                card.applyEffect(attacker, target);  // Применяем эффект карты
            }
        }

        // Очищаем поле и заполняем его пустыми картами
        deck.getFieldE().clear();
        deck.getFieldE().addAll(Collections.nCopies(4, null));

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
