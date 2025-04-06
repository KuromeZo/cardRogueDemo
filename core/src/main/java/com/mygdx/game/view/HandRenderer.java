package com.mygdx.game.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.logic.cards.Card;
import com.mygdx.game.logic.cards.Deck;
import com.mygdx.game.logic.entities.Entity;

import java.util.List;

public class HandRenderer {
    private final Deck deck;
    private final ShapeRenderer shapeRenderer;
    private final SpriteBatch batch;
    private final BitmapFont font;
    private final OrthographicCamera camera;
    private int selectedCardIndex = -1;
    private int selectedPosition = -1;

    private final Entity player;
    private final Entity enemy;
    private FieldRenderer fieldRenderer;

    public HandRenderer(Deck deck, OrthographicCamera camera, Entity player, Entity enemy) {
        this.deck = deck;
        this.camera = camera;
        this.player = player;
        this.enemy = enemy;
        this.shapeRenderer = new ShapeRenderer();
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        font.setColor(Color.WHITE);
    }

    public void render() {
        List<Card> hand = deck.getHand();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (int i = 0; i < hand.size(); i++) {
            Card card = hand.get(i);
            shapeRenderer.setColor(Color.GRAY);
            shapeRenderer.rect(card.getX(), card.getY(), card.getWidth(), card.getHeight());

            if(i == selectedCardIndex) {
                shapeRenderer.setColor(Color.YELLOW);
                shapeRenderer.rect(card.getX() - 5, card.getY() - 5, card.getWidth() + 10, card.getHeight() + 10);
            }
        }
        shapeRenderer.end();

        batch.begin();
        for (int i = 0; i < hand.size(); i++) {
            Card card = hand.get(i);
            font.draw(batch, card.getName(), card.getX() + 10, card.getY() + card.getHeight() / 2);
        }
        batch.end();
    }

    public void updateHover(float mouseX, float mouseY) {
        Vector3 worldMouse = camera.unproject(new Vector3(mouseX, mouseY, 0));
        float worldMouseX = worldMouse.x;
        float worldMouseY = worldMouse.y;

        selectedCardIndex = -1;
        for (int i = 0; i < deck.getHand().size(); i++) {
            Card card = deck.getHand().get(i);
            if (worldMouseX >= card.getX() && worldMouseX <= card.getX() + card.getWidth() &&
                worldMouseY >= card.getY() && worldMouseY <= card.getY() + card.getHeight()) {
                selectedCardIndex = i;
                break;
            }
        }
    }

    public void onClick() {
        if (selectedCardIndex != -1) {
            Card selectedCard = deck.getHand().get(selectedCardIndex);

            selectedCard.setPlayerOwnership(true);

            // Если позиция выбрана, то карта размещается в соответствующем месте на поле
            if (selectedPosition != -1) {
                // Удаляем предыдущую версию этой карты из поля, если она уже есть
                for (int i = 0; i < deck.getField().size(); i++) {
                    if (deck.getField().get(i) != null && deck.getField().get(i).equals(selectedCard)) {
                        deck.getField().set(i, null); // Очищаем старую позицию
                        fieldRenderer.clearPositionLabel(i);
                        break; // Прерываем, так как карта может быть только в одном месте
                    }
                }

                deck.getField().set(selectedPosition, selectedCard); // Добавляем карту на новую позицию
                System.out.println("Card added to field in position " + (selectedPosition + 1) + ": " + selectedCard.getName());
                fieldRenderer.placeCard(selectedPosition, selectedCard.getName());
                selectedPosition = -1;
            } else {
                System.out.println("No position selected");
            }
        }
    }

    public void setSelectedPosition(int position) {
        this.selectedPosition = position;
    }

    public void setFieldRenderer(FieldRenderer fieldRenderer) {
        this.fieldRenderer = fieldRenderer;
    }

    public void dispose() {
        shapeRenderer.dispose();
        batch.dispose();
        font.dispose();
    }
}
