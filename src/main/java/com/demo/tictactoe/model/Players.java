package com.demo.tictactoe.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Players {
    X("X"), O("O");

    private final String playerText;

    public Players getOther() {
        return this == X ? O : X;
    }

    public static Players getByText(String playerText) {
        if ("X".equalsIgnoreCase(playerText))
            return X;
        if ("O".equalsIgnoreCase(playerText))
            return O;
        return null;
    }
}
