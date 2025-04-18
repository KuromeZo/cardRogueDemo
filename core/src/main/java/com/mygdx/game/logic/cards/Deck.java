package com.mygdx.game.logic.cards;

import com.mygdx.game.view.FieldRenderer;
import com.mygdx.game.view.HandRenderer;

import java.util.*;

public class Deck {
    private List<Card> deck;
    private List<Card> hand;
    private List<Card> field;
    private PlayCards playCards;

    private static final float INITIAL_X_POSITION = 100; // Начальная позиция по X
    private static final float INITIAL_Y_POSITION = 20;  // Фиксированная позиция по Y
    private static final float CARD_X_OFFSET = 100;

    public Deck(List<Card> initialCards) {
        this.deck = new ArrayList<>(initialCards);
        this.hand = new ArrayList<>();
        this.field = new ArrayList<>(Collections.nCopies(4, null));
        shuffle(); // Перемешиваем колоду при создании
    }

    public void setPlayCards(PlayCards playCards) {
        this.playCards = playCards;
    }

    public void shuffle() {
        Collections.shuffle(deck);
    }

    public void drawHand(){
        hand.clear();
        Map<String, Integer> cardCounts = new HashMap<>();

        float currentX = INITIAL_X_POSITION; // Начинаем с начальной позиции по X

        Random random = new Random();
        while(hand.size() < 5 && !deck.isEmpty()) {
            int randomIndex = random.nextInt(deck.size());
            Card originalCard = deck.get(randomIndex);
            String cardType = originalCard.getName();

            if(cardCounts.getOrDefault(cardType, 0) < 2) {
                Card card = originalCard.clone();
                card.setX(currentX);
                card.setY(INITIAL_Y_POSITION);
                hand.add(card);
                cardCounts.put(cardType, cardCounts.getOrDefault(cardType, 0) + 1);
                currentX += CARD_X_OFFSET;
            }
        }
    }

    public void startNewTurn() {
        if (playCards != null) {
            playCards.applyEffects();
        }

        returnHandToDeck(); // Возвращаем карты в колоду и перемешиваем
        drawHand(); // Берем новые карты
    }

    public List<Card> getHand() {
        return hand;
    }

    public void returnHandToDeck() {
        shuffle();
        hand.clear();
    }

    public List<Card> getDeck() {
        return deck;
    }

    public List<Card> getField() {
        return field;
    }
}
