package com.demo.tictactoe.repository;

import com.demo.tictactoe.TestUtil;
import com.demo.tictactoe.exceptions.GameNotFoundException;
import com.demo.tictactoe.model.GameBoard;
import com.demo.tictactoe.model.GameEntity;
import com.demo.tictactoe.model.Players;
import com.demo.tictactoe.service.WinConditionChecker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GamesRepositoryTest {
    private GamesRepository gamesRepository;

    @BeforeEach
    void setUp() {
        gamesRepository = new GamesRepository();
    }

    @Test
    void createCreateAndFetch() {
        assertThrows(GameNotFoundException.class, () -> gamesRepository.fetchById(1));
        GameEntity game = gamesRepository.createNewGame();
        assertEquals(1, game.getId());
        assertEquals(TestUtil.createFromJson("""
                {"data":[
                    [null, null, null],
                    [null, null, null],
                    [null, null, null]
                ]}
                """), game.getGameBoard());
        assertEquals(Players.X, game.getNextToMove());

        assertEquals(game, gamesRepository.fetchById(1));
        assertThrows(GameNotFoundException.class, () -> gamesRepository.fetchById(2));

    }

    @Test
    void update() {
        gamesRepository.createNewGame();
        GameEntity game = gamesRepository.fetchById(1);
        GameBoard board = game.getGameBoard();

        GameBoard changedBoard = board.withCellUpdated(1, 1, Players.X);
        assertNotEquals(game.getGameBoard(), changedBoard);
        assertEquals(game, gamesRepository.fetchById(1));

        GameEntity updatedEntity = game.toBuilder()
                .gameBoard(changedBoard)
                .build();
        gamesRepository.update(updatedEntity);

        assertNotEquals(game, gamesRepository.fetchById(1));
        assertEquals(updatedEntity, gamesRepository.fetchById(1));
    }
}