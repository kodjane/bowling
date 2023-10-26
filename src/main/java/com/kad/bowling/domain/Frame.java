package com.kad.bowling.domain;

import com.kad.bowling.domain.enums.AttemptName;
import com.kad.bowling.domain.enums.FrameName;
import com.kad.bowling.domain.enums.ScoreType;

import java.util.HashMap;

import static com.kad.bowling.domain.enums.AttemptName.getInitialValues;

/*
 * Created By
 * @author Aime D. Kodjane
 */
public class Frame {
    private final int INITIAL_SCORE = 0;
    public final static int INITIAL_PINS_PER_GAME = 15;
    private int remainingPins;
    private FrameName name;
    private final HashMap<Attempt, Score> scoreBoard;
    private int totalScore;

    public Frame(FrameName name) {
        this.name = name;
        remainingPins = INITIAL_PINS_PER_GAME;
        scoreBoard = initializeBoard();
    }

    /**
     * This method allows to initialize the score board
     *
     * @return The score board
     */
    public HashMap<Attempt, Score> initializeBoard() {
        HashMap<Attempt, Score> scoreBoard = new HashMap<>();

        for (int i = 1; i <= getInitialValues().size(); i++) {
            scoreBoard.put(new Attempt(i, AttemptName.getById(i)), new Score(INITIAL_SCORE));
        }

        return scoreBoard;
    }

    public HashMap<Attempt, Score> getScoreBoard() {
        return this.scoreBoard;
    }

    /**
     * This method allows to get the remaining pins of the frame. At start up of the frame the remaining pins are 15
     *
     * @return The remaining pins of the frame
     */
    public int getPins() {
        return this.remainingPins;
    }

    /**
     * This method Allows to get the score at a specific attempt
     *
     * @param attempt The specific attempt
     * @return The score
     */
    public Score getScoreAt(Attempt attempt) {
        return this.scoreBoard.get(attempt);
    }

    public boolean isNeitherAStrikeNorASpare() {
        return this.remainingPins > 0;
    }

    public boolean isStrike(Attempt attempt) {
        return attempt.id() == 1
                && this.remainingPins == 0
                && this.getScoreAt(attempt).getValue() == ScoreType.STRIKE.getValue();
    }

    public boolean isSpare(Attempt attempt) {
        return isSecondOrThirdAttempt(attempt)
                && remainingPins == 0;
    }

    private boolean isSecondOrThirdAttempt(Attempt attempt) {
        return attempt.id() == 2 || attempt.id() == 3;
    }

    public FrameName getName() {
        return name;
    }

    public int getRemainingPins() {
        return remainingPins;
    }

    public void setRemainingPins(int remainingPins) {
        this.remainingPins = remainingPins;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }
}
