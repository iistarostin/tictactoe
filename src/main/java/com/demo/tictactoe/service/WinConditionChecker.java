package com.demo.tictactoe.service;

import com.demo.tictactoe.model.GameBoard;
import com.demo.tictactoe.service.WinCondition.IntPair;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.demo.tictactoe.model.GameBoard.BOARD_SIZE;

@Component
public class WinConditionChecker {
    private final List<WinCondition> winConditions;

    public WinConditionChecker() {
        var horizontalAndVertical = IntStream.range(0, BOARD_SIZE).boxed().flatMap(first ->
                Stream.of(
                    IntStream.range(0, BOARD_SIZE)
                        .mapToObj(second -> new IntPair(first, second)),
                    IntStream.range(0, BOARD_SIZE)
                        .mapToObj(second -> new IntPair(second, first))));
        var diagonal = Stream.of(
                IntStream.range(0, BOARD_SIZE).mapToObj(index -> new IntPair(index, index)),
                IntStream.range(0, BOARD_SIZE).mapToObj(index -> new IntPair(index, BOARD_SIZE - 1 - index))
        );
        winConditions = Stream.concat(horizontalAndVertical, diagonal)
                .map(s -> new WinCondition(s.toList()))
                .toList();
    }

    public boolean isGameEnded(GameBoard board) {
        return winConditions.stream()
                .anyMatch(condition -> condition.checkOnBoard(board));
    }
}
