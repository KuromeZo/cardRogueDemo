package com.mygdx.game.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;

import java.util.HashMap;
import java.util.Map;

public class FieldRenderer {
    private int selectedPosition = -1; // Выбранная позиция на поле
    private final SpriteBatch batch;
    private final BitmapFont font;
    private final HandRenderer handRenderer;
    private final OrthographicCamera camera;
    private final ShapeRenderer shapeRenderer;
    private int[] xPositions = {188, 288, 388, 488};
    private int yPosition = 300;

    private static final Map<Integer, String> positionLabels = new HashMap<>();

    public FieldRenderer(HandRenderer handRenderer, OrthographicCamera camera) {
        this.handRenderer = handRenderer;
        this.batch = new SpriteBatch();
        this.camera = camera;
        this.font = new BitmapFont();
        this.shapeRenderer = new ShapeRenderer();

    }

    public void render() {
        // Отображаем кружочки для выбора позиции
        renderCircles();
    }

    private void renderCircles() {
        // Рисуем кружочки на экране
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (int i = 0; i < 4; i++) {
            Color circleColor = (i == selectedPosition) ? Color.YELLOW : Color.GRAY;
            // Рисуем кружочек
            drawCircle(xPositions[i], yPosition, i + 1, circleColor);
        }
        shapeRenderer.end();

        batch.begin();
        for (int i = 0; i < 4; i++) {
            if (positionLabels.containsKey(i)) {
                font.draw(batch, positionLabels.get(i), xPositions[i] - 10, yPosition + 5);
            }
        }
        batch.end();
    }

    private void drawCircle(int x, int y, int position, Color color) {
        // Устанавливаем цвет для рисования
        shapeRenderer.setColor(color);
        // Рисуем круг (x, y) - координаты центра, 20 - радиус
        shapeRenderer.circle(x, y, 20);
    }

    public void updateHover(float mouseX, float mouseY) {
        // Получаем мировые координаты из экранных
        Vector3 worldMouse = camera.unproject(new Vector3(mouseX, mouseY, 0));
        float worldMouseX = worldMouse.x;
        float worldMouseY = worldMouse.y;

        // Проверяем, на какой кружок кликнули
        selectedPosition = -1;
        for (int i = 0; i < 4; i++) {
            if (worldMouseX >= xPositions[i] - 20 && worldMouseX <= xPositions[i] + 20 &&
                worldMouseY >= yPosition-25 && worldMouseY <= yPosition+25) {
                selectedPosition = i;
                break;
            }
        }
    }

    public void onClick() {
        // Если позиция выбрана, передаем ее в HandRenderer
        if (selectedPosition != -1) {
            handRenderer.setSelectedPosition(selectedPosition);
        }
    }

    public void placeCard(int position, String name) {
        if (position >= 0 && position < 4) {
            positionLabels.put(position, name.substring(0, Math.min(2, name.length()))); // Берем 2 буквы
        }
    }

    public void clearPositionLabel(int position) {
        positionLabels.remove(position);
    }

    public static void clearAllPositionLabels() {
        positionLabels.clear();
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
        shapeRenderer.dispose();
    }
}
