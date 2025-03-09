package com.mygdx.game.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.logic.cards.Card;
import com.mygdx.game.logic.cards.Deck;
import com.mygdx.game.logic.cards.MeleeAttackCard;
import com.mygdx.game.logic.cards.RangedAttackCard;
import com.mygdx.game.logic.entities.Entity;

import java.util.List;

public class HandRenderer {
    private static Deck deck = null;
    private final ShapeRenderer shapeRenderer;
    private final SpriteBatch batch;
    private final BitmapFont font;
    private final OrthographicCamera camera;
    private int selectedCardIndex = -1;
    private static int energy = 4;
    private int selectedPosition = -1;

    private static Entity player = null;
    private static Entity enemy = null;

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
        if (energy != 0 && selectedCardIndex != -1) {
            Card selectedCard = deck.getHand().get(selectedCardIndex);

            selectedCard.setPlayerT();

            // Если позиция выбрана, то карта размещается в соответствующем месте на поле
            if (selectedPosition != -1) {
                deck.getField().add(selectedPosition, selectedCard); // Используем выбранную позицию
                deck.getHand().remove(selectedCard);
                energy--;
                System.out.println("Card added to field in position " + (selectedPosition + 1) + ": " + selectedCard.getName());
                selectedPosition = -1;
            } else {
                System.out.println("No position selected");
            }
        } else {
            System.out.println("No energy");
        }
    }

    public void setSelectedPosition(int position) {
        this.selectedPosition = position;
    }

    /*public void onClick() {
        if(energy != 0) {
            if (selectedCardIndex != -1) {
                Card selectedCard = deck.getHand().get(selectedCardIndex);

                // Проверяем тип карты и решаем, кто будет целью
                Entity target = null;
                Entity attacker = null;

                // Если это карта атаки (например, MeleeAttackCard или RangedAttackCard)
                if (selectedCard instanceof MeleeAttackCard || selectedCard instanceof RangedAttackCard) {
                    target = enemy;  // Враг становится целью
                    attacker = player;
                } else {
                    target = player;  // В противном случае цель - игрок
                    attacker = enemy;
                }

                // Применяем эффект карты на цель
                selectedCard.applyEffect(target, attacker);
                deck.getHand().remove(selectedCard);  // Удаляем карту из руки после использования
                energy--;
            }
        } else if (energy == 0) {
            System.out.println("No energy");
        }
    }*/

    public static void resetEnergy() {
        energy = 4;
    }

    public static void removeStun(){
        enemy.unstun();
    }

    public static void enemyTurn(){
        for (int i = 0; i < deck.getHand().size(); i++) {
            if(enemy.isStunned()){
                break;
            }
            Card selectedCard = deck.getHand().get(i);

            System.out.println(selectedCard.getName());
            // Проверяем тип карты и решаем, кто будет целью
            Entity target = null;
            Entity attacker = null;

            // Если это карта атаки (например, MeleeAttackCard или RangedAttackCard)
            if (selectedCard instanceof MeleeAttackCard || selectedCard instanceof RangedAttackCard) {
                target = player;  // Герой становится целью
                attacker = enemy;
            } else {
                target = enemy;  // В противном случае цель - враг
                attacker = player;
            }

            // Применяем эффект карты на цель
            selectedCard.applyEffect(target, attacker);
        }
    }

    public void dispose() {
        shapeRenderer.dispose();
        batch.dispose();
        font.dispose();
    }
}
