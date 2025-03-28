package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.logic.cards.*;
import com.mygdx.game.logic.entities.Enemy;
import com.mygdx.game.logic.entities.Player;
import com.mygdx.game.view.EnemyRenderer;
import com.mygdx.game.view.FieldRenderer;
import com.mygdx.game.view.HandRenderer;
import com.mygdx.game.view.PlayerRenderer;

import java.util.ArrayList;
import java.util.Arrays;

public class TestGameScreen implements Screen {
    private final Game game;
    private Stage stage;
    private SpriteBatch batch;
    private Texture background;
    private Skin skin;
    private OrthographicCamera camera;
    private Viewport viewport;
    private boolean isPaused = false;
    private Image darkOverlay;
    private Table buttonPauseTable;
    private Table buttonMoveTable;
    private Table pauseTable;

    private Player player;
    private PlayerRenderer playerRenderer;

    private Enemy enemy;
    private EnemyRenderer enemyRenderer;

    private Deck deck;
    private HandRenderer handRenderer;

    private EnemyDeck enemyDeck;

    private PlayCards playCards;

    private PlayEnemyCards playEnemyCards;

    private FieldRenderer fieldRenderer;

    public TestGameScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show(){
        camera = new OrthographicCamera();
        viewport = new FillViewport(640, 480, camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        batch = new SpriteBatch();
        background = new Texture("testback.png");
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        TextButton pauseButton = new TextButton("Pause", skin);
        buttonPauseTable = new Table();
        buttonPauseTable.setFillParent(true);
        buttonPauseTable.top().left();
        buttonPauseTable.add(pauseButton).pad(10);
        stage.addActor(buttonPauseTable);

        TextButton moveButton = new TextButton("Move", skin);
        buttonMoveTable = new Table();
        buttonMoveTable.setFillParent(true);
        buttonMoveTable.top(); // Размещаем сверху по центру
        buttonMoveTable.add(moveButton).pad(10);
        stage.addActor(buttonMoveTable);

        darkOverlay = new Image(new Texture("darkoverlay.png"));
        darkOverlay.setColor(0,0,0,0.5f);
        darkOverlay.setSize(viewport.getWorldWidth(), viewport.getWorldHeight());
        darkOverlay.setVisible(false);
        stage.addActor(darkOverlay);

        pauseTable = new Table();
        pauseTable.setFillParent(true);
        pauseTable.center();
        pauseTable.setVisible(false);

        TextButton continueButton = new TextButton("Continue", skin);
        TextButton exitButton = new TextButton("Exit", skin);

        pauseTable.add(continueButton).padBottom(20).row();
        pauseTable.add(exitButton);
        stage.addActor(pauseTable);

        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                togglePause();
            }
        });

        continueButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                togglePause();
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.exit();
            }
        });

        moveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                deck.startNewTurn();
            }
        });

        player = new Player("Hero", 100, 0.0f, false);
        player.setPosition(50, viewport.getWorldHeight() / 2 - 25, 50, 50);

        playerRenderer = new PlayerRenderer(player, camera);

        enemy = new Enemy("Enemy", 50, false, true);
        enemy.setPosition(540, viewport.getWorldHeight() / 2 - 25, 50, 50);

        enemyRenderer = new EnemyRenderer(enemy, camera);

        deck = new Deck(Arrays.asList(
            new RangedAttackCard("Fireball", "Deals 5 damage", false, 5,
                0.5f, 1.5f, 1.5f, 0.4f, 3),
            new MeleeAttackCard("Slash", "Deals 10 damage", false,10,
                0.5f, 1.5f, 2.0f, 0.2f, 1),
            new HealCard("Heal", "Heals 15 hp", false,15),
            new DodgeCard("Dodge", "Dodge attack", false,0.7f),
            new CoverCard("Cover", "Cover", false,true),
            new CounterAttackCard("CounterAttack", "------", false),
            new ArmorCard("Armor", "Adds 5 armor", false, 5)
        ));

        enemyDeck = new EnemyDeck(Arrays.asList(
            new RangedAttackCard("SmallFireball", "Deals 3 damage", false, 3,
                0.5f, 1.5f, 1.5f, 0.4f, 3),
            new MeleeAttackCard("Slash", "Deals 5 damage", false,6,
                0.5f, 1.5f, 2.0f, 0.2f, 1),
            new HealCard("Heal", "Heals 9 hp", false,9),
            new ArmorCard("Armor", "Adds 5 armor", false, 5),
            new DodgeCard("Dodge", "Dodge attack", false,0.7f)
            ));

        handRenderer = new HandRenderer(deck, camera, player, enemy);
        deck.drawHand();

        playCards = new PlayCards(deck, player, enemy);

        playEnemyCards = new PlayEnemyCards(enemyDeck, player, enemy);

        fieldRenderer = new FieldRenderer(handRenderer, camera);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.input.getY();
        playerRenderer.updateHover(mouseX, mouseY);
        enemyRenderer.updateHover(mouseX, mouseY);
        handRenderer.updateHover(mouseX, mouseY);
        fieldRenderer.updateHover(mouseX, mouseY);
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            handRenderer.onClick();
        }
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            fieldRenderer.onClick();
        }


        batch.begin();
        batch.draw(background, 0,0, viewport.getWorldWidth(), viewport.getWorldHeight());
        batch.end();

        playerRenderer.render();
        enemyRenderer.render();
        handRenderer.render();
        fieldRenderer.render();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        background.dispose();
        batch.dispose();
        playerRenderer.dispose();
        enemyRenderer.dispose();
        handRenderer.dispose();
        //fieldRenderer.dispose();
    }

    private void togglePause() {
        isPaused = !isPaused;
        pauseTable.setVisible(isPaused);
        darkOverlay.setVisible(isPaused);

        if (isPaused) {
            Gdx.input.setInputProcessor(stage);
        } else {
            Gdx.input.setInputProcessor(stage);
        }
    }
}
