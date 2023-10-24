package com.kad.bowling.domain;

import com.kad.bowling.domain.enums.ScoreType;

/*
 * Created By
 * @author Aime D. Kodjane
 */
public class Score {
    private final int value;
    private ScoreType type;

    public Score(int value) {
        this.value = value;
    }

    public Score(int value, ScoreType type) {
        this.value = value;
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public ScoreType getType() {
        return type;
    }
}
