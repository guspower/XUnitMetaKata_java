package com.codemanship.rockpaperscissors;

public class Program {

    final static class TestResults {
        int testsPassed = 0;
        int testsFailed = 0;

        void fail(String scenario, int expected, int actual) {
            testsFailed++;
            log("%s: FAIL - expected %d but was %d", scenario, expected, actual);
        }

        void fail(String scenario, String expected) {
            testsFailed++;
            log("%s: FAIL - %s", scenario, expected);
        }

        void pass(String message) {
            testsPassed++;
            log("%s: PASS", message);
        }

        private void log(String message, Object... parameters) {
            System.out.println(String.format(message, parameters));
        }
    }

    void assertRound(TestResults results, String move1, String move2, int expected, String scenarioName) throws InvalidMoveException {
        String scenario = String.format("%s (%s, %s)", scenarioName, move1, move2);

        int actual = playRound(move1, move2);
        if (actual == expected) {
            results.pass(scenario);
        } else {
            results.fail(scenario, expected, actual);
        }
    }

    int playRound(String move1, String move2) throws InvalidMoveException {
        return new Round().play(move1, move2);
    }

    TestResults runRoundTests() throws InvalidMoveException {
        TestResults results = new TestResults();

        // Round tests
        System.out.println("Round tests...");

        assertRound(results, "Rock", "Scissors", 1, "rock blunts scissors");
        assertRound(results, "Scissors", "Rock", 2, "rock blunts scissors");
        assertRound(results, "Scissors", "Paper", 1, "scissors cut paper");
        assertRound(results, "Paper", "Scissors", 2, "scissors cut paper");
        assertRound(results, "Paper", "Rock", 1, "paper wraps rock");
        assertRound(results, "Rock", "Paper", 2, "paper wraps rock");
        assertRound(results, "Rock", "Rock", 0, "round is a draw");
        assertRound(results, "Scissors", "Scissors", 0, "round is a draw");
        assertRound(results, "Paper", "Paper", 0, "round is a draw");

        String scenarioName = "invalid inputs not allowed";
        try {
            playRound("Blah", "Foo");
            results.fail(scenarioName, "expected InvalidMoveException");
        } catch (InvalidMoveException e) {
            results.pass(scenarioName);
        }

        return results;
    }

    public static void main(String[] args) throws Exception {
        int testsPassed = 0;
        int testsFailed = 0;

        TestResults roundResults = new Program().runRoundTests();
        testsPassed += roundResults.testsPassed;
        testsFailed += roundResults.testsFailed;

        // output header
        System.out.println("Running RockPaperScissors tests...");

        // Game tests
        System.out.println("Game tests...");

        // player 1 wins game
        SpyGameListener listener = new SpyGameListener();
        Game game = new Game(listener);
        try {
            game.PlayRound("Rock", "Scissors");
            game.PlayRound("Rock", "Scissors");
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        }

        int result = listener.getWinner();
        if (result == 1) {
            testsPassed++;
            System.out.println("player 1 wins game: PASS");
        } else {
            testsFailed++;
            System.out.println(String.format("player 1 wins game: FAIL - expected 1 but was %d", result));
        }

        // player 2 wins game
        listener = new SpyGameListener();
        game = new Game(listener);
        try {
            game.PlayRound("Rock", "Paper");
            game.PlayRound("Rock", "Paper");
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        }

        result = listener.getWinner();
        if (result == 2) {
            testsPassed++;
            System.out.println("player 2 wins game: PASS");
        } else {
            testsFailed++;
            System.out.println(String.format("player 2 wins game: FAIL - expected 2 but was %d", result));
        }

        // drawers not counted
        listener = new SpyGameListener();
        game = new Game(listener);
        try {
            game.PlayRound("Rock", "Rock");
            game.PlayRound("Rock", "Rock");
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        }

        result = listener.getWinner();
        if (result == 0) {
            testsPassed++;
            System.out.println("drawers not counted: PASS");
        } else {
            testsFailed++;
            System.out.println(String.format("drawers not counted: FAIL - expected 0 but was %d", result));
        }

        //invalid moves not counted
        listener = new SpyGameListener();
        game = new Game(listener);
        try {
            game.PlayRound("Blah", "Foo");
            game.PlayRound("Rock", "Scissors");
        } catch (Exception e) {

        }

        result = listener.getWinner();
        if (result == 0) {
            testsPassed++;
            System.out.println("invalid moves not counted: PASS");
        } else {
            testsFailed++;
            System.out.println(String.format("invalid moves not counted: FAIL - expected 0 but was %d", result));
        }


        System.out.println(String.format("Tests run: %d  Passed: %d  Failed: %d", testsPassed + testsFailed, testsPassed, testsFailed));
    }

    private static class SpyGameListener implements GameListener {
        private int winner = 0;

        @Override
        public void GameOver(int winner) {
            this.winner = winner;
        }

        public int getWinner() {
            return winner;
        }
    }
}
