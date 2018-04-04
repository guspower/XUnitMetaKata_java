package com.codemanship.rockpaperscissors;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.codemanship.rockpaperscissors.Move.PAPER;
import static com.codemanship.rockpaperscissors.Move.ROCK;
import static com.codemanship.rockpaperscissors.Move.SCISSORS;
import static com.codemanship.rockpaperscissors.Result.DRAW;
import static com.codemanship.rockpaperscissors.Result.LOSE;
import static com.codemanship.rockpaperscissors.Result.WIN;

public class Round
{

    final static Map<Move, Move> WINNING_MOVES;

    static {
        WINNING_MOVES = new HashMap();
        WINNING_MOVES.put(ROCK, SCISSORS);
        WINNING_MOVES.put(PAPER, ROCK);
        WINNING_MOVES.put(SCISSORS, PAPER);
    }

    public int play(String player1, String player2) throws InvalidMoveException {
        Optional<Move> move1 = Move.find(player1);
        Optional<Move> move2 = Move.find(player2);

        if(move1.isPresent() && move2.isPresent()) {
            return compare(move1.get(), move2.get()).code;
        } else {
            throw new InvalidMoveException();
        }
    }

    private Result compare(Move move1, Move move2) {
        return move1 == move2 ? DRAW : WINNING_MOVES.get(move1) == move2 ? WIN: LOSE;
    }

}
