package com.mygdx.game.logic.battle;

import com.mygdx.game.logic.cards.*;
import com.mygdx.game.logic.entities.*;

import java.util.Arrays;

public class BattleManager {
    private Player player;
    private Enemy enemy;
    private Deck deck;
    private EnemyDeck enemyDeck;
    private PlayCards playCards;
    private PlayEnemyCards playEnemyCards;

    public BattleManager() {
        initializeEntities();
        initializeDecks();
        setupConnections();
    }

    private void initializeEntities() {
        player = new Player("Hero", 100, 0.0f, false);
        enemy = new Enemy("Enemy", 50, false, true);
    }

    private void initializeDecks() {
        // Код инициализации колод из TestGameScreen
        deck = new Deck(Arrays.asList(
            new RangedAttackCard("Fireball", "Deals 5 damage", false, 5,
                0.5f, 1.5f, 1.5f, 0.4f, 3),
            new MeleeAttackCard("Slash", "Deals 10 damage", false, 10,
                0.5f, 1.5f, 2.0f, 0.2f, 1),
            new HealCard("Heal", "Heals 15 hp", false, 15),
            new DodgeCard("Dodge", "Dodge attack", false, 0.7f),
            new CoverCard("Cover", "Cover", false, true),
            new CounterAttackCard("CounterAttack", "------", false),
            new ArmorCard("Armor", "Adds 5 armor", false, 5)
        ));

        enemyDeck = new EnemyDeck(Arrays.asList(
            new RangedAttackCard("SmallFireball", "Deals 3 damage", false, 3,
                0.5f, 1.5f, 1.5f, 0.4f, 3),
            new MeleeAttackCard("Slash", "Deals 5 damage", false, 6,
                0.5f, 1.5f, 2.0f, 0.2f, 1),
            new HealCard("Heal", "Heals 9 hp", false, 9),
            new ArmorCard("Armor", "Adds 5 armor", false, 5),
            new DodgeCard("Dodge", "Dodge attack", false, 0.7f)
        ), deck);
    }

    private void setupConnections() {
        playCards = new PlayCards(deck, player, enemy);
        deck.setPlayCards(playCards);

        playEnemyCards = new PlayEnemyCards(enemyDeck, player, enemy);
    }

    public void startNewTurn() {
        enemyDeck.drawField();
        playEnemyCards.applyEffects();
        deck.startNewTurn();
        enemy.unstun();
    }

    // Геттеры для доступа к игровым объектам
    public Player getPlayer() { return player; }
    public Enemy getEnemy() { return enemy; }
    public Deck getPlayerDeck() { return deck; }
    public EnemyDeck getEnemyDeck() { return enemyDeck; }
}
