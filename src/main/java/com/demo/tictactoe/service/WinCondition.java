package com.demo.tictactoe.service;

import com.demo.tictactoe.model.GameBoard;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class WinCondition {
    private final List<IntPair> cellsToCheck;

    @Value
    public static class IntPair {
        int row;
        int column;
    }

    public boolean checkOnBoard(GameBoard board) {
        var valuesInCells = cellsToCheck.stream()
                .map(indices -> board.getValue(indices.row, indices.column))
                .distinct()
                .collect(Collectors.toList());
        return (valuesInCells.size() == 1 && valuesInCells.get(0) != null);
    }
}
