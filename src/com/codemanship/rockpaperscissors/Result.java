package com.codemanship.rockpaperscissors;

public enum Result {

    DRAW(0),
    WIN(1),
    LOSE(2);

    final int code;

    Result(int code) {
        this.code = code;
    }

}
