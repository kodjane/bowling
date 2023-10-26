package com.kad.bowling.domain.enums;

import java.util.List;

/*
 * Created By
 * @author Aime D. Kodjane
 */
public enum AttemptName {
    FIRST_ATTEMPT(1),
    SECOND_ATTEMPT(2),
    THIRD_ATTEMPT(3),
    EXTRA_ATTEMPT(4);

    private int id;

    AttemptName(int id) {
        this.id = id;
    }

    public static List<AttemptName> getInitialValues() {
        return List.of(FIRST_ATTEMPT, SECOND_ATTEMPT, THIRD_ATTEMPT);
    }

    public static AttemptName getById(int id) {
        AttemptName name = null;
        for(AttemptName value : values()) {
            if(value.id == id)
                name = value;
        }
        return name;
    }

    public int getId() {
        return id;
    }
}
