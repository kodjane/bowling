package com.kad.bowling.domain;

import java.util.ArrayList;
import java.util.List;

/*
 * Created By
 * @author Aime D. Kodjane
 */
public class Player {
    private final List<NormalFrame> frames;
    private final int INITIAL_FRAME_NUMBER = 5;
    public Player() {
        this.frames = initializeFrames();
    }

    private List<NormalFrame> initializeFrames() {
        ArrayList<NormalFrame> frames = new ArrayList<>();
        for (int i = 0; i < INITIAL_FRAME_NUMBER; i++) {
            frames.add(new NormalFrame());
        }
        return frames;
    }

    public List<NormalFrame> getFrames() {
        return this.frames;
    }
}
