package com.mygdx.game.logic.cards;

import com.mygdx.game.logic.entities.Entity;
import com.mygdx.game.view.HandRenderer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Deck {
    private List<Card> deck;
    private List<Card> hand;
    private List<Card> field;

    private static final float INITIAL_X_POSITION = 100; // Начальная позиция по X
    private static final float INITIAL_Y_POSITION = 20;  // Фиксированная позиция по Y
    private static final float CARD_X_OFFSET = 100;

    public Deck(List<Card> initialCards) {
        this.deck = new ArrayList<>(initialCards);
        this.hand = new ArrayList<>();
        this.field = new ArrayList<>();
        shuffle(); // Перемешиваем колоду при создании
    }

    public void shuffle() {
        Collections.shuffle(deck);
    }

    public void drawHand(){
        hand.clear();
        HandRenderer.resetEnergy();

        float currentX = INITIAL_X_POSITION; // Начинаем с начальной позиции по X
        float currentY = INITIAL_Y_POSITION; // Начальная позиция по Y (фиксированная)

        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            if (deck.isEmpty()) break;

            int randomIndex = random.nextInt(deck.size());
            Card originalCard = deck.get(randomIndex);
            Card card = originalCard.clone(); // Создаем копию карты

            card.setX(currentX);
            card.setY(currentY);
            hand.add(card);

            currentX += CARD_X_OFFSET;
        }
    }

    public void startNewTurn() {
        PlayCards.applyEffects();
        //activateFieldCards();
        HandRenderer.enemyTurn();
        returnHandToDeck(); // Возвращаем карты в колоду и перемешиваем
        System.out.println("-");
        for (Card card : deck) {
            System.out.println(card.getName());
        }
        System.out.println("-");
        drawHand();         // Берем новые карты
        HandRenderer.removeStun();
    }

    public List<Card> getHand() {
        return hand;
    }

    public void returnHandToDeck() {
        shuffle();
        hand.clear();
    }

    public List<Card> getField() {
        return field;
    }
}
