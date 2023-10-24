package com.kad.bowling.domain;

import com.kad.bowling.domain.enums.Attempt;

import java.util.HashMap;

import static com.kad.bowling.domain.enums.Attempt.*;

/*
 * Created By
 * @author Aime D. Kodjane
 */
public class Frame {
    private final int INITIAL_SCORE = 0;
    private final int INITIAL_PINS_PER_GAME = 15;
    private final HashMap<Attempt, Score> scoreBoard;
    private int remainingPins;

    public Frame() {
        remainingPins = INITIAL_PINS_PER_GAME;
        this.scoreBoard = initializeBoard();
    }

    /**
     * This method allows to initialize the score board
     * @return The score board
     */
    private HashMap<Attempt, Score> initializeBoard() {
        HashMap<Attempt, Score> scoreBoard = new HashMap<>();

        for (Attempt attempt : Attempt.getInitialValues()) {
            scoreBoard.put(attempt, new Score(INITIAL_SCORE));
        }

        return scoreBoard;
    }

    public HashMap<Attempt, Score> getScoreBoard() {
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
    public Score getScoreAt(Attempt attempt) {
        return this.scoreBoard.get(attempt);
    }

    public void knockPinsAndUpdateScoreAt(int pinsDown, Attempt attempt) {
        //TODO requires more logics, but let's keep like that for now
        this.remainingPins = remainingPins - pinsDown;
        int scoreValue = 0;

        if (isNeitherAStrikeNorASpare())
            scoreValue = pinsDown;

        if (isStrike(attempt)){
            // TODO implement score calculation for a strike
            System.out.println("DO some staff");
        }

        if (isSpare(attempt)) {
            // TODO implement score calculation for a spare
            System.out.println("DO some staff");
        }

        this.scoreBoard.put(attempt, new Score(scoreValue));
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

    private boolean isNeitherAStrikeNorASpare() {
        // TODO Should be public and tested in isolation
        // TODO We should also do the same for spare and strike cases
        return this.remainingPins > 0;
    }

    public boolean isStrike(Attempt attempt) {
        return attempt == FIRST_ATTEMPT && this.remainingPins == 0;
    }

    public boolean isSpare(Attempt attempt) {
        return isSecondOrThirdAttempt(attempt) && remainingPins == 0;
    }

    private boolean isSecondOrThirdAttempt(Attempt attempt) {
        // TODO put some precision about where does the spare happened
        return attempt == SECOND_ATTEMPT || attempt == THIRD_ATTEMPT;
    }
}
