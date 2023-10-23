package com.kad.bowling.domain.enums;

import java.util.List;

/*
 * Created By
 * @author Aime D. Kodjane
 */
public enum Attempt {
    FIRST_ATTEMPT(1),
    SECOND_ATTEMPT(2),
    THIRD_ATTEMPT(3),
    FOURTH_ATTEMPT(4);
    private final int ID;

    Attempt(int ID) {
        this.ID = ID;
    }

    public static List<Attempt> getInitialValues() {
        return List.of(FIRST_ATTEMPT, SECOND_ATTEMPT, THIRD_ATTEMPT);
    }
}
