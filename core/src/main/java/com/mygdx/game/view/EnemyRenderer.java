package com.mygdx.game.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.logic.entities.Enemy;

public class EnemyRenderer {
    private final Enemy enemy;
    private final ShapeRenderer shapeRenderer;
    private final SpriteBatch batch;
    private final BitmapFont font;
    private boolean showStats = false;
    private OrthographicCamera camera;

    public EnemyRenderer(Enemy enemy, OrthographicCamera camera) {
        this.enemy = enemy;
        this.shapeRenderer = new ShapeRenderer();
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        this.camera = camera;
        font.setColor(Color.WHITE);
    }

    public void render() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight());
        shapeRenderer.end();

        if(showStats) {
            batch.begin();
            font.draw(batch, "HP: " + enemy.getCurrentHealth(), enemy.getX(), enemy.getY() + enemy.getHeight() + 20);
            font.draw(batch, "Armor: " + enemy.getArmor(), enemy.getX(), enemy.getY() + enemy.getHeight() + 40);
            font.draw(batch, "Name: " + enemy.getName(), enemy.getX(), enemy.getY() + enemy.getHeight() + 60);
            batch.end();
        }
    }

    public void updateHover(float mouseX, float mouseY) {
        Vector3 screenToWorld = new Vector3(mouseX, mouseY, 0);
        camera.unproject(screenToWorld);  // Преобразует экранные координаты в мировые

        float worldMouseX = screenToWorld.x;
        float worldMouseY = screenToWorld.y;

        showStats = worldMouseX >= enemy.getX() && worldMouseX <= enemy.getX() + enemy.getWidth()
            && worldMouseY >= enemy.getY() && worldMouseY <= enemy.getY() + enemy.getHeight();
    }

    public void dispose() {
        shapeRenderer.dispose();
        batch.dispose();
        font.dispose();
    }
}
