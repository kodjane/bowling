package com.kad.bowling.domain;

import com.kad.bowling.domain.enums.Attempt;

import java.util.HashMap;

/*
 * Created By
 * @author Aime D. Kodjane
 */
public interface Frame {
    final int INITIAL_SCORE = 0;
    final int INITIAL_PINS_PER_GAME = 15;

    int getPins();

    void knockPinsAndUpdateScoreAt(int pinsDown, Attempt attempt);

    int getTotalScore();

    boolean isNeitherAStrikeNorASpare();

    boolean isStrike(Attempt attempt);

    boolean isSpare(Attempt attempt);

    /**
     * This method allows to initialize the score board
     * @return The score board
     */
    HashMap<Attempt, Score> initializeBoard();
    HashMap<Attempt, Score> getScoreBoard();
}
