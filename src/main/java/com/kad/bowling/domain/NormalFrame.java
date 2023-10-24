package com.kad.bowling.domain;

import com.kad.bowling.domain.enums.Attempt;

import java.util.HashMap;

import static com.kad.bowling.domain.enums.Attempt.*;

/*
 * Created By
 * @author Aime D. Kodjane
 */
public class NormalFrame implements Frame{
    private int remainingPins;
    private final HashMap<Attempt, Score> scoreBoard;
    public NormalFrame() {
        remainingPins = INITIAL_PINS_PER_GAME;
        scoreBoard = initializeBoard();
    }

    @Override
    public HashMap<Attempt, Score> initializeBoard() {
        HashMap<Attempt, Score> scoreBoard = new HashMap<>();

        for (Attempt attempt : Attempt.getInitialValues()) {
            scoreBoard.put(attempt, new Score(INITIAL_SCORE));
        }

        return scoreBoard;
    }

    @Override
    public HashMap<Attempt, Score> getScoreBoard() {
        return this.scoreBoard;
    }

    @Override
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

    @Override
    public void knockPinsAndUpdateScoreAt(int pinsDown, Attempt attempt) {
        //TODO requires more logics, but let's keep like that for now
        this.remainingPins = remainingPins - pinsDown;
        int scoreValue = 0;

        if (isNeitherAStrikeNorASpare()) {
            scoreValue = pinsDown;
            this.scoreBoard.put(attempt, new Score(scoreValue));
        }

        if (isStrike(attempt)){
            // TODO implement score calculation for a strike
            System.out.println("DO some staff");
        }

        if (isSpare(attempt)) {
            // TODO implement score calculation for a spare
            System.out.println("DO some staff");
        }

    }

    /**
     * This methods allows to get the total score of a frame
     * @return The total score
     */
    @Override
    public int getTotalScore() {
        return this.scoreBoard.values().stream()
                .mapToInt(Score::getValue)
                .sum();
    }

    @Override
    public boolean isNeitherAStrikeNorASpare() {
        // TODO Should be public and tested in isolation
        // TODO We should also do the same for spare and strike cases
        return this.remainingPins > 0;
    }

    @Override
    public boolean isStrike(Attempt attempt) {
        return attempt == FIRST_ATTEMPT && this.remainingPins == 0;
    }

    @Override
    public boolean isSpare(Attempt attempt) {
        return isSecondOrThirdAttempt(attempt) && remainingPins == 0;
    }

    private boolean isSecondOrThirdAttempt(Attempt attempt) {
        // TODO put some precision about where does the spare happened
        return attempt == SECOND_ATTEMPT || attempt == THIRD_ATTEMPT;
    }

}
