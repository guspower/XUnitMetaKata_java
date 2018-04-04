package com.codemanship.rockpaperscissors;

import java.util.Arrays;
import java.util.Optional;

public enum Move {

    ROCK,
    PAPER,
    SCISSORS;

    public static Optional<Move> find(String name) {
        return Arrays.stream(Move.values()).filter((Move move) ->
            move.name().equalsIgnoreCase(name)
        ).findFirst();
    }

}