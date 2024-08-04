package com.demo.tictactoe.service;

import com.demo.tictactoe.TestUtil;
import com.demo.tictactoe.model.GameBoard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WinConditionCheckerTest {
    private WinConditionChecker winConditionChecker = new WinConditionChecker();

    @Test
    void testWinningStates() {
        GameBoard board;
        board = TestUtil.createFromJson(
                """
                {"data":[
                    ["X", "O", "O"],
                    [null, "X", null],
                    [null, null, "X"]
                ]}
                """);
        assertTrue(winConditionChecker.isGameEnded(board));

        board = TestUtil.createFromJson(
                """
                {"data":[
                    ["X", null, null],
                    ["X", null, "O"],
                    ["X", null, "O"]
                ]}
                """);
        assertTrue(winConditionChecker.isGameEnded(board));

        board = TestUtil.createFromJson(
                """
                {"data":[
                    ["O", "X", null],
                    [null, "X", null],
                    [null, "X", "O"]
                ]}
                """);
        assertTrue(winConditionChecker.isGameEnded(board));

        board = TestUtil.createFromJson(
                """
                {"data":[
                    ["O", null, "X"],
                    [null, "O", "X"],
                    [null, null, "X"]
                ]}
                """);
        assertTrue(winConditionChecker.isGameEnded(board));

        board = TestUtil.createFromJson(
                """
                {"data":[
                    ["X", "X", "X"],
                    [null, "O", "O"],
                    [null, null, null]
                ]}
                """);
        assertTrue(winConditionChecker.isGameEnded(board));

        board = TestUtil.createFromJson(
                """
                {"data":[
                    [null, null, null],
                    ["X", "X", "X"],
                    [null, null, null]
                ]}
                """);
        assertTrue(winConditionChecker.isGameEnded(board));

        board = TestUtil.createFromJson(
                """
                {"data":[
                    ["X", "X", null],
                    [null, "X", "X"],
                    ["O", "O", "O"]
                ]}
                """);
        assertTrue(winConditionChecker.isGameEnded(board));

        board = TestUtil.createFromJson(
                """
                {"data":[
                    ["X", "X", "O"],
                    [null, "O", "X"],
                    ["O", null, null]
                ]}
                """);
        assertTrue(winConditionChecker.isGameEnded(board));
    }

    @Test
    void testMixedStates() {
        GameBoard board;
        board = TestUtil.createFromJson(
                """
                {"data":[
                    ["X", "O", "O"],
                    [null, "O", null],
                    [null, null, "X"]
                ]}
                """);
        assertFalse(winConditionChecker.isGameEnded(board));

        board = TestUtil.createFromJson(
                """
                {"data":[
                    ["X", null, null],
                    ["O", null, "O"],
                    ["X", null, "O"]
                ]}
                """);
        assertFalse(winConditionChecker.isGameEnded(board));

        board = TestUtil.createFromJson(
                """
                {"data":[
                    ["O", "O", null],
                    [null, "X", null],
                    [null, "X", "O"]
                ]}
                """);
        assertFalse(winConditionChecker.isGameEnded(board));

        board = TestUtil.createFromJson(
                """
                {"data":[
                    ["O", null, "X"],
                    [null, "O", "O"],
                    [null, null, "X"]
                ]}
                """);
        assertFalse(winConditionChecker.isGameEnded(board));

        board = TestUtil.createFromJson(
                """
                {"data":[
                    ["X", "O", "X"],
                    [null, "O", "O"],
                    [null, null, null]
                ]}
                """);
        assertFalse(winConditionChecker.isGameEnded(board));

        board = TestUtil.createFromJson(
                """
                {"data":[
                    [null, null, null],
                    ["X", "O", "X"],
                    [null, null, null]
                ]}
                """);
        assertFalse(winConditionChecker.isGameEnded(board));

        board = TestUtil.createFromJson(
                """
                {"data":[
                    ["X", "X", null],
                    [null, "X", "X"],
                    ["X", "O", "O"]
                ]}
                """);
        assertFalse(winConditionChecker.isGameEnded(board));

        board = TestUtil.createFromJson(
                """
                {"data":[
                    ["X", "X", "O"],
                    [null, "X", "X"],
                    ["O", null, null]
                ]}
                """);
        assertFalse(winConditionChecker.isGameEnded(board));
    }

    @Test
    void testStatesWithBlanks() {
        GameBoard board;
        board = TestUtil.createFromJson(
                """
                {"data":[
                    ["X", "O", "O"],
                    [null, null, null],
                    [null, null, "X"]
                ]}
                """);
        assertFalse(winConditionChecker.isGameEnded(board));

        board = TestUtil.createFromJson(
                """
                {"data":[
                    ["X", null, null],
                    [null, null, "O"],
                    ["X", null, "O"]
                ]}
                """);
        assertFalse(winConditionChecker.isGameEnded(board));

        board = TestUtil.createFromJson(
                """
                {"data":[
                    ["O", "X", null],
                    [null, null, null],
                    [null, "X", "O"]
                ]}
                """);
        assertFalse(winConditionChecker.isGameEnded(board));

        board = TestUtil.createFromJson(
                """
                {"data":[
                    ["O", null, "X"],
                    [null, "O", null],
                    [null, null, "X"]
                ]}
                """);
        assertFalse(winConditionChecker.isGameEnded(board));

        board = TestUtil.createFromJson(
                """
                {"data":[
                    ["X", null, "X"],
                    [null, "O", "O"],
                    [null, null, null]
                ]}
                """);
        assertFalse(winConditionChecker.isGameEnded(board));

        board = TestUtil.createFromJson(
                """
                {"data":[
                    [null, null, null],
                    ["X", null, "X"],
                    [null, null, null]
                ]}
                """);
        assertFalse(winConditionChecker.isGameEnded(board));

        board = TestUtil.createFromJson(
                """
                {"data":[
                    ["X", "X", null],
                    [null, "X", "X"],
                    ["O", null, "O"]
                ]}
                """);
        assertFalse(winConditionChecker.isGameEnded(board));

        board = TestUtil.createFromJson(
                """
                {"data":[
                    ["X", "X", "O"],
                    [null, "O", "X"],
                    [null, null, null]
                ]}
                """);
        assertFalse(winConditionChecker.isGameEnded(board));
    }

}