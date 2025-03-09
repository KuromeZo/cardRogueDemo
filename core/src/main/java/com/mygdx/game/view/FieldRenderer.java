package com.mygdx.game.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;

public class FieldRenderer {
    private int selectedPosition = -1; // Выбранная позиция на поле
    private final SpriteBatch batch;
    private final HandRenderer handRenderer;
    private final OrthographicCamera camera;
    private final ShapeRenderer shapeRenderer;
    private static boolean[] isSlotLocked = new boolean[4];

    public FieldRenderer(HandRenderer handRenderer, OrthographicCamera camera) {
        this.handRenderer = handRenderer;
        this.batch = new SpriteBatch();
        this.camera = camera;
        this.shapeRenderer = new ShapeRenderer();
        for (int i = 0; i < 4; i++) {
            isSlotLocked[i] = false;
        }
    }

    public void render() {
        // Отображаем кружочки для выбора позиции
        renderCircles();
    }

    private void renderCircles() {
        // Пример координат для кружочков
        int[] xPositions = {100, 200, 300, 400}; // координаты по горизонтали
        int yPosition = 300; // координата по вертикали

        // Рисуем кружочки на экране
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (int i = 0; i < 4; i++) {
            Color circleColor = (i == selectedPosition) ? Color.YELLOW : Color.GRAY;
            // Рисуем кружочек
            drawCircle(xPositions[i], yPosition, i + 1, circleColor);
        }
        shapeRenderer.end();
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
        int[] xPositions = {100, 200, 300, 400};
        for (int i = 0; i < 4; i++) {
            if (worldMouseX >= xPositions[i] - 20 && worldMouseX <= xPositions[i] + 20 &&
                worldMouseY >= 275 && worldMouseY <= 325) {
                selectedPosition = i;
                break;
            }
        }
    }

    public void onClick() {
        // Если позиция выбрана, передаем ее в HandRenderer
        if (selectedPosition != -1 && !isSlotLocked[selectedPosition]) {
            handRenderer.setSelectedPosition(selectedPosition);
            // После выбора позиции, блокируем слот для повторного выбора
            isSlotLocked[selectedPosition] = true;
        }
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public static void startTurn() {
        // Разблокируем все слоты для нового хода
        for (int i = 0; i < 4; i++) {
            isSlotLocked[i] = false;
        }
    }
}
