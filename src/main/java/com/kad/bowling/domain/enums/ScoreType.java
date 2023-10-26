package com.kad.bowling.domain.enums;

/*
 * Created By
 * @author Aime D. Kodjane
 */
public enum ScoreType {
    STRIKE(15, "X"),
    SPARE(15, "/"),
    GUTTER(0, "-");

    ScoreType(int value, String symbol) {
        this.value = value;
        this.symbol = symbol;
    }

    private final int value;
    private final String symbol;

    public int getValue() {
        return value;
    }

    public String getSymbol() {
        return symbol;
    }
}
