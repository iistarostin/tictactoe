package com.demo.tictactoe.model;

import lombok.Value;

@Value
public class Move {
    Players player;
    int row;
    int column;
}
