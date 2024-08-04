package com.demo.tictactoe.exceptions;

public class IllegalMoveException extends RuntimeException {
    public IllegalMoveException(String s) {
        super(s);
    }
}
