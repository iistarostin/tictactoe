package com.demo.tictactoe.service;

import com.demo.tictactoe.TestUtil;
import com.demo.tictactoe.exceptions.IllegalMoveException;
import com.demo.tictactoe.model.GameBoard;
import com.demo.tictactoe.model.GameEntity;
import com.demo.tictactoe.model.Move;
import com.demo.tictactoe.model.Players;
import com.demo.tictactoe.repository.GamesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Import(GameFlowService.class)
class GameFlowServiceTest {
    @MockBean
    private GamesRepository gamesRepository;

    @MockBean
    private WinConditionChecker winConditionChecker;

    @Autowired
    private GameFlowService gameFlowService;

    private GameEntity entity = GameEntity.builder()
            .id(1)
            .nextToMove(Players.O)
            .gameBoard(TestUtil.createFromJson(
            """
            {"data":[
                ["X", "X", "O"],
                [null, "O", "X"],
                [null, null, null]
            ]}
            """))
            .build();

    @BeforeEach
    void setUp() {
        Mockito.when(gamesRepository.fetchById(1)).thenReturn(entity);
    }

    @Test
    void testInvalidMove() {
        Move move = new Move(Players.O, -1, 0);
        assertThrows(IllegalMoveException.class, () -> gameFlowService.processMove(1, move));
    }

    @Test
    void testCellOccupied() {
        Move move = new Move(Players.O, 0, 0);
        assertThrows(IllegalMoveException.class, () -> gameFlowService.processMove(1, move));
    }

    @Test
    void testWrongTurn() {
        Move move = new Move(Players.X, 1, 0);
        assertThrows(IllegalMoveException.class, () -> gameFlowService.processMove(1, move));
    }

    @Test
    void testGameOver() {
        GameEntity endedEntity = entity.toBuilder().nextToMove(null).build();
        Mockito.when(gamesRepository.fetchById(2)).thenReturn(endedEntity);
        Move move = new Move(Players.X, 1, 0);
        assertThrows(IllegalMoveException.class, () -> gameFlowService.processMove(2, move));
    }

    @Test
    void testValidMove() {
        Move move = new Move(Players.O, 1, 0);
        GameEntity updatedGame = gameFlowService.processMove(1, move);

        GameBoard expectedBoard = TestUtil.createFromJson("""
            {"data":[
                ["X", "X", "O"],
                ["O", "O", "X"],
                [null, null, null]
            ]}
            """);
        assertEquals(expectedBoard, updatedGame.getGameBoard());
        assertEquals(Players.X, updatedGame.getNextToMove());
        Mockito.verify(winConditionChecker, Mockito.times(1)).isGameEnded(expectedBoard);
        Mockito.verify(gamesRepository, Mockito.times(1)).update(updatedGame);
    }
}