package com.kad.bowling.domain;

import java.util.ArrayList;
import java.util.List;

/*
 * Created By
 * @author Aime D. Kodjane
 */
public class Player {
    private final List<Frame> frames;
    private final int INITIAL_FRAME_NUMBER = 5;
    public Player() {
        this.frames = initializeFrames();
    }

    private List<Frame> initializeFrames() {
        ArrayList<Frame> frames = new ArrayList<>();
        for (int i = 0; i < INITIAL_FRAME_NUMBER; i++) {
            frames.add(new Frame());
        }
        return frames;
    }

    public List<Frame> getFrames() {
        return this.frames;
    }

    public void addAnExtraAttemptToTheLastFrame() {
        Frame frame = getlastFrame();
        int lastAttempt = frame.getScoreBoard().size() + 1;
        frame.getScoreBoard().put(lastAttempt, new Score(0));
    }

    public Frame getlastFrame() {
        int lastFrameIndex = this.getFrames().size() - 1;
        return this.frames.get(lastFrameIndex);
    }
}
