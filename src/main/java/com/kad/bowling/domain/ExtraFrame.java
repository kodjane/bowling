package com.kad.bowling.domain;

import com.kad.bowling.domain.enums.Attempt;

import java.util.HashMap;

/*
 * Created By
 * @author Aime D. Kodjane
 */
public class ExtraFrame implements Frame {
    @Override
    public int getPins() {
        return 0;
    }

    @Override
    public void knockPinsAndUpdateScoreAt(int pinsDown, Attempt attempt) {

    }

    @Override
    public int getTotalScore() {
        return 0;
    }

    @Override
    public boolean isNeitherAStrikeNorASpare() {
        return false;
    }

    @Override
    public boolean isStrike(Attempt attempt) {
        return false;
    }

    @Override
    public boolean isSpare(Attempt attempt) {
        return false;
    }

    @Override
    public HashMap<Attempt, Score> initializeBoard() {
        return null;
    }

    @Override
    public HashMap<Attempt, Score> getScoreBoard() {
        return null;
    }
}
