package com.demo.tictactoe.model;

import lombok.Value;

@Value
public class GameBoard {
    public static final int BOARD_SIZE = 3;

    Players[][] data;

    public GameBoard() {
        data = new Players[BOARD_SIZE][BOARD_SIZE];
    }

    private GameBoard(Players[][] data) {
        this.data = data;
    }

    public GameBoard withCellUpdated(int row, int column, Players value) {
        Players[][] newData = data.clone();
        newData[row][column] = value;
        return new GameBoard(newData);
    }

    public Players getValue(int row, int column) {
        return data[row][column];
    }
}
