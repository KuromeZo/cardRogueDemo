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
import com.mygdx.game.logic.battle.BattleManager;
import com.mygdx.game.view.EnemyRenderer;
import com.mygdx.game.view.FieldRenderer;
import com.mygdx.game.view.HandRenderer;
import com.mygdx.game.view.PlayerRenderer;

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

    private BattleManager battleManager;

    // Рендереры
    private PlayerRenderer playerRenderer;
    private EnemyRenderer enemyRenderer;
    private HandRenderer handRenderer;
    private FieldRenderer fieldRenderer;

    public TestGameScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        setupCamera();
        setupStage();
        setupResources();
        setupUI();
        initializeGameSystem();
    }

    private void setupCamera() {
        camera = new OrthographicCamera();
        viewport = new FillViewport(640, 480, camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
    }

    private void setupStage() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
    }

    private void setupResources() {
        batch = new SpriteBatch();
        background = new Texture("testback.png");
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
    }

    private void setupUI() {
        // Кнопка паузы
        TextButton pauseButton = new TextButton("Pause", skin);
        buttonPauseTable = new Table();
        buttonPauseTable.setFillParent(true);
        buttonPauseTable.top().left();
        buttonPauseTable.add(pauseButton).pad(10);
        stage.addActor(buttonPauseTable);

        // Кнопка хода
        TextButton moveButton = new TextButton("Move", skin);
        buttonMoveTable = new Table();
        buttonMoveTable.setFillParent(true);
        buttonMoveTable.top();
        buttonMoveTable.add(moveButton).pad(10);
        stage.addActor(buttonMoveTable);

        // Темный оверлей для паузы
        darkOverlay = new Image(new Texture("darkoverlay.png"));
        darkOverlay.setColor(0, 0, 0, 0.5f);
        darkOverlay.setSize(viewport.getWorldWidth(), viewport.getWorldHeight());
        darkOverlay.setVisible(false);
        stage.addActor(darkOverlay);

        // Меню паузы
        pauseTable = new Table();
        pauseTable.setFillParent(true);
        pauseTable.center();
        pauseTable.setVisible(false);

        TextButton continueButton = new TextButton("Continue", skin);
        TextButton exitButton = new TextButton("Exit", skin);

        pauseTable.add(continueButton).padBottom(20).row();
        pauseTable.add(exitButton);
        stage.addActor(pauseTable);

        // Обработчики событий для кнопок
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                togglePause();
            }
        });

        continueButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                togglePause();
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        moveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                battleManager.startNewTurn();
                fieldRenderer.clearAllPositionLabels();
            }
        });
    }

    private void initializeGameSystem() {
        // Создаем BattleManager
        battleManager = new BattleManager();

        // Устанавливаем позиции для игрока и врага
        battleManager.getPlayer().setPosition(50, viewport.getWorldHeight() / 2 - 25, 50, 50);
        battleManager.getEnemy().setPosition(540, viewport.getWorldHeight() / 2 - 25, 50, 50);

        // Инициализируем рендереры
        playerRenderer = new PlayerRenderer(battleManager.getPlayer(), camera);
        enemyRenderer = new EnemyRenderer(battleManager.getEnemy(), camera);
        handRenderer = new HandRenderer(battleManager.getPlayerDeck(), camera,
            battleManager.getPlayer(), battleManager.getEnemy());

        // Отрисовываем начальную руку
        battleManager.getPlayerDeck().drawHand();

        // Инициализируем рендерер поля
        fieldRenderer = new FieldRenderer(handRenderer, camera);
        handRenderer.setFieldRenderer(fieldRenderer);
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
            fieldRenderer.onClick();
        }

        batch.begin();
        batch.draw(background, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
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
        fieldRenderer.dispose();
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
