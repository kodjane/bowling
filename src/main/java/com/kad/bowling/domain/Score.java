package com.kad.bowling.domain;

import com.kad.bowling.domain.enums.ScoreType;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Score score = (Score) o;
        return value == score.value && type == score.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, type);
    }
}
