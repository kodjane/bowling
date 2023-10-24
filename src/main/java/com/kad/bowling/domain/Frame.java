package com.kad.bowling.domain;

import java.util.HashMap;

import static com.kad.bowling.domain.enums.ScoreType.SPARE;
import static com.kad.bowling.domain.enums.ScoreType.STRIKE;

/*
 * Created By
 * @author Aime D. Kodjane
 */
public class Frame {
    private final int INITIAL_SCORE = 0;
    private final int INITIAL_PINS_PER_GAME = 15;
    private int remainingPins;
    private final HashMap<Integer, Score> scoreBoard;
    public Frame() {
        remainingPins = INITIAL_PINS_PER_GAME;
        scoreBoard = initializeBoard();
    }

    /**
     * This method allows to initialize the score board
     * @return The score board
     */
    public HashMap<Integer, Score> initializeBoard() {
        HashMap<Integer, Score> scoreBoard = new HashMap<>();

        for (int i = 1; i <= 3; i++) {
            scoreBoard.put(i, new Score(INITIAL_SCORE));
        }

        return scoreBoard;
    }

    public HashMap<Integer, Score> getScoreBoard() {
        return this.scoreBoard;
    }

    public int getPins() {
        return this.remainingPins;
    }

    /**
     * This method Allows to get the score at a specific attempt
     * @param attempt The specific attempt
     * @return The score
     */
    public Score getScoreAt(int attempt) {
        return this.scoreBoard.get(attempt);
    }

    public void knockPinsAndUpdateScoreAt(int pinsDown, Player player, int attempt) {
        this.remainingPins = remainingPins - pinsDown;

        if (isNeitherAStrikeNorASpare()) {
            this.scoreBoard.put(attempt, new Score(pinsDown));
        }

        if (isStrike(attempt)){
            this.scoreBoard.put(attempt, new Score(STRIKE.getValue(), STRIKE));
            player.addAnExtraAttemptToTheLastFrame();
        }

        if (isSpare(attempt)) {
            this.scoreBoard.put(attempt, new Score(SPARE.getValue(), SPARE));
            player.addAnExtraAttemptToTheLastFrame();
        }
    }

    /**
     * This methods allows to get the total score of a frame
     * @return The total score
     */
    public int getTotalScore() {
        return this.scoreBoard.values().stream()
                .mapToInt(Score::getValue)
                .sum();
    }

    public boolean isNeitherAStrikeNorASpare() {
        return this.remainingPins > 0;
    }

    public boolean isStrike(int attempt) {
        return attempt == 1 && this.remainingPins == 0;
    }

    public boolean isSpare(int attempt) {
        return isSecondOrThirdAttempt(attempt) && remainingPins == 0;
    }

    private boolean isSecondOrThirdAttempt(int attempt) {
        return attempt == 2 || attempt == 3;
    }

}
