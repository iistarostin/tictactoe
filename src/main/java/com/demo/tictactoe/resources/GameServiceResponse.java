package com.demo.tictactoe.resources;

import com.demo.tictactoe.model.GameBoard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class GameServiceResponse {
    private int id;
    private GameBoard board;
    private String message;
    private String errorMessage;
}
