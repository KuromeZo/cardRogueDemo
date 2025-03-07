package com.mygdx.game.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.logic.entities.Player;

public class PlayerRenderer {
    private final Player player;
    private final ShapeRenderer shapeRenderer;
    private final SpriteBatch batch;
    private final BitmapFont font;
    private boolean showStats = false;
    private OrthographicCamera camera;

    public PlayerRenderer(Player player, OrthographicCamera camera) {
        this.player = player;
        this.shapeRenderer = new ShapeRenderer();
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        this.camera = camera;
        font.setColor(Color.WHITE);
    }


    public void render() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(player.getX(), player.getY(), player.getWidth(), player.getHeight());
        shapeRenderer.end();

        if (showStats) {
            batch.begin();
            font.draw(batch, "HP: " + player.getCurrentHealth(), player.getX(), player.getY() + player.getHeight() + 20);
            font.draw(batch, "Armor: " + player.getArmor(), player.getX(), player.getY() + player.getHeight() + 40);
            font.draw(batch, "Name: " + player.getName(), player.getX(), player.getY() + player.getHeight() + 60);
            batch.end();
        }
    }

    public void updateHover(float mouseX, float mouseY) {
        Vector3 screenToWorld = new Vector3(mouseX, mouseY, 0);
        camera.unproject(screenToWorld);  // Преобразует экранные координаты в мировые

        float worldMouseX = screenToWorld.x;
        float worldMouseY = screenToWorld.y;

        showStats = worldMouseX >= player.getX() && worldMouseX <= player.getX() + player.getWidth()
            && worldMouseY >= player.getY() && worldMouseY <= player.getY() + player.getHeight();
    }

    public void dispose() {
        shapeRenderer.dispose();
        batch.dispose();
        font.dispose();
    }
}
