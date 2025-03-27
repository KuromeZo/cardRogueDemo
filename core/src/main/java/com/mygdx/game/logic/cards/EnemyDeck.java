package com.mygdx.game.logic.cards;

import com.mygdx.game.logic.entities.Entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static com.mygdx.game.view.HandRenderer.deck;

public class EnemyDeck {
    private static List<Card> eDeck;
    private static List<Card> field;


    public EnemyDeck(List<Card> eCards) {
        this.eDeck = eCards;
        this.field = new ArrayList<>(Collections.nCopies(4, null));
        shuffle(); // Перемешиваем колоду при создании
    }

    public void shuffle() {
        Collections.shuffle(eDeck);
    }

    public static void drawField(){
        field.clear();
        Random random = new Random();

        List<Card> remainingPlayerCards = new ArrayList<>(deck.getHand());
        System.out.println("--");
        for (Card card : remainingPlayerCards) {
            System.out.println(card.getName());
        }
        System.out.println("--");

        int enemyCardCount = 2;

        for (int i = 0; i < enemyCardCount; i++) {
            if (!eDeck.isEmpty()) {
                int randomIndex = random.nextInt(eDeck.size()); // Берем случайную карту из eDeck
                field.add(eDeck.get(randomIndex)); // Копируем карту (не удаляем!)
            }
        }

        while (field.size() < 4 && !remainingPlayerCards.isEmpty()) {
            field.add(remainingPlayerCards.remove(0));
        }
    }

    public List<Card> getFieldE() {
        return field;
    }
}
