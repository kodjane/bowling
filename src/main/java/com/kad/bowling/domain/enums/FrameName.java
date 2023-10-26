package com.kad.bowling.domain.enums;

/*
 * Created By
 * @author Aime D. Kodjane
 */
public enum FrameName {
    FIRST_FRAME(1),
    SECOND_FRAME(2),
    THIRD_FRAME(3),
    FOURTH_FRAME(4),
    FIFTH_FRAME(5);
    private int id;

    FrameName(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
