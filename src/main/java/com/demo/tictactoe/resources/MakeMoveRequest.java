package com.demo.tictactoe.resources;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MakeMoveRequest {
    private String player;
    private int row;
    private int column;
}
