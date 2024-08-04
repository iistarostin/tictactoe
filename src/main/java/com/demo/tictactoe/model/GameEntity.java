package com.demo.tictactoe.model;

import lombok.*;

@Value
@Builder(toBuilder = true)
public class GameEntity {
    int id;
    GameBoard gameBoard;
    Players nextToMove;
}
