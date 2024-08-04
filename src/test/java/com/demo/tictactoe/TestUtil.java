package com.demo.tictactoe;

import com.demo.tictactoe.model.GameBoard;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtil {
    public static GameBoard createFromJson(String json) {
        try {
            return new ObjectMapper().readValue(json, GameBoard.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
