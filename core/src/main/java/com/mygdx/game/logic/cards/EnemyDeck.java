package com.mygdx.game.logic.cards;

import java.util.*;

public class EnemyDeck {
    private List<Card> eDeck;
    private List<Card> field;
    private final Deck deck;
    private List<Card> remainingPlayerCards;

    public EnemyDeck(List<Card> eCards, Deck deck) {
        this.eDeck = eCards;
        this.field = new ArrayList<>(Collections.nCopies(4, null));
        this.deck = deck;
        shuffle(); // Перемешиваем колоду при создании
    }

    public void shuffle() {
        Collections.shuffle(eDeck);
    }

    public void drawField(){
        field.clear();
        Random random = new Random();

        List<Card> remainingPlayerCards = new ArrayList<>(deck.getHand());

        for (Card fieldCard : deck.getField()) {
            // Ищем первую подходящую карту и удаляем её
            for (Iterator<Card> iterator = remainingPlayerCards.iterator(); iterator.hasNext(); ) {
                Card handCard = iterator.next();
                if (handCard.equals(fieldCard)) { // или handCard.getType().equals(fieldCard.getType())
                    iterator.remove();
                    break; // Удалили одну копию — выходим из внутреннего цикла
                }
            }
        }
        System.out.println("--/");
        for (Card card : remainingPlayerCards) {
            System.out.println(card.getName());
        }
        System.out.println("--/");

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
