package com.kad.bowling.domain;

import com.kad.bowling.domain.enums.AttemptName;
import com.kad.bowling.domain.enums.FrameName;

import java.util.ArrayList;
import java.util.List;

import static com.kad.bowling.domain.enums.AttemptName.*;
import static com.kad.bowling.domain.enums.FrameName.FIRST_FRAME;
import static com.kad.bowling.domain.enums.ScoreType.SPARE;
import static com.kad.bowling.domain.enums.ScoreType.STRIKE;

/*
 * Created By
 * @author Aime D. Kodjane
 */
public class Player {
    private final List<Frame> frames;
    private final int INITIAL_FRAME_NUMBER = 5;
    private final List<Frame> processedFrames = new ArrayList<>();

    public Player() {
        this.frames = initializeFrames();
//        previousFrames = new ArrayList<>();
    }

    private List<Frame> initializeFrames() {
        ArrayList<Frame> frames = new ArrayList<>();
        for (FrameName name : FrameName.values()) {
            frames.add(new Frame(name));
        }
        return frames;
    }


    public void knockPinsAndUpdateScoreAt(int pinsDown, Frame frame, Attempt attempt) {

        calculateTotalScore(pinsDown, frame, attempt);

    }

    public List<Frame> getFrames() {
        return this.frames;
    }

    public void addAnExtraAttemptToTheLastFrame() {
        Frame frame = getlastFrame();
        int lastAttempt = frame.getScoreBoard().size() + 1;
        frame.getScoreBoard().put(new Attempt(lastAttempt, EXTRA_ATTEMPT), new Score(0));
    }

    /**
     * This methods allows to get the total score of a currentFrame
     *
     * @return The total score
     */
    public void calculateTotalScore(int pinsDown, Frame currentFrame, Attempt attempt) {
        currentFrame.setRemainingPins(currentFrame.getRemainingPins() - pinsDown);
        currentFrame.getScoreBoard().put(attempt, new Score(pinsDown));
        Frame previousFrame = this.getPreviousFrame(currentFrame);
        if (isFirstAttempt(attempt)) {
            if (previousFrame != null)
                processedFrames.add(previousFrame);
        }
        Frame nextFrame = this.getNextFrame(currentFrame);


        if (currentFrame.isNeitherAStrikeNorASpare()) {
            currentFrame.getScoreBoard().put(attempt, new Score(pinsDown));
            if (0 == processedFrames.size()) {
                currentFrame.setTotalScore(getTotalSumAttempt(currentFrame));
            } else {
                if (isThirdAttempt(attempt)) {
                    int previousFramesScore = 0;
                    for (Frame processedFrame : processedFrames) {

                        if (processedFrame.isStrike(getAttempt(FIRST_ATTEMPT))) {
                            if (1 == processedFrames.size()) {
                                processedFrame.setTotalScore(processedFrame.getScoreAt(getAttempt(FIRST_ATTEMPT)).getValue() + getTotalSumAttempt(currentFrame));
                                currentFrame.setTotalScore(processedFrame.getTotalScore() + getTotalSumAttempt(currentFrame));
                            } else {
                                previousFramesScore += STRIKE.getValue();
                                Frame firstProcessedFrame = this.processedFrames.get(0);
                                firstProcessedFrame.setTotalScore(getTotalSumAttempt(currentFrame) + previousFramesScore);
                            }

                        } else if (processedFrame.isSpare(new Attempt(SECOND_ATTEMPT.getId(), SECOND_ATTEMPT))) {
                            Frame firstProcessedFrame = getPreviousFrame(processedFrame);
                            int firstProcessedScore = firstProcessedFrame != null ? getTotalSumAttempt(firstProcessedFrame) : 0;
                            int totalScore = getTotalSumAttempt(processedFrame) +
                                    firstProcessedScore +
                                    getTotalSumAttemptForSpareFrame(currentFrame);
                            processedFrame.setTotalScore(totalScore);
                            if (previousFrame != null)
                                currentFrame.setTotalScore(previousFrame.getTotalScore() + getTotalSumAttemptForSpareFrame(currentFrame));
                        } else if (processedFrame.isSpare(new Attempt(THIRD_ATTEMPT.getId(), THIRD_ATTEMPT))) {
                            Frame lastProcessedFrame = getNextFrame(this.getFrame(processedFrames.size()));
                            processedFrame.setTotalScore(getTotalSumAttemptForSpareFrame(currentFrame)
                                    + getTotalSumAttempt(processedFrame)
                                    + getTotalSumAttempt(lastProcessedFrame));
                            if (previousFrame != null)
                                currentFrame.setTotalScore(previousFrame.getTotalScore() + getTotalSumAttemptForSpareFrame(currentFrame));
                        } else {
                            if (processedFrame.getTotalScore() == 0)
                                processedFrame.setTotalScore(getTotalSumAttemptForSpareFrame(currentFrame) + getTotalSumAttempt(processedFrame));
                        }
                    }
//                    currentFrame.setTotalScore(previousFrame.getTotalScore() + getTotalSumAttempt(currentFrame));
                }
//                currentFrame.getScoreBoard().put(attempt, new Score(pinsDown));
            }
        }

        if (currentFrame.isStrike(attempt)) {
            currentFrame.getScoreBoard().put(attempt, new Score(STRIKE.getValue(), STRIKE));
//            if (previousFrame == null) {
//                currentFrame.setTotalScore(getTotalSumAttempt(nextFrame) + currentFrame.getScoreAt(attempt).getValue());
//            }
        }

        if (currentFrame.isSpare(attempt)) {
            currentFrame.getScoreBoard().put(attempt, new Score(pinsDown, SPARE));
        }
    }

    private Attempt getAttempt(AttemptName attemptName) {
        return new Attempt(attemptName.getId(), attemptName);
    }

    private boolean isThirdAttempt(Attempt attempt) {
        return attempt.id() == THIRD_ATTEMPT.getId();
    }

    private boolean isFirstAttempt(Attempt attempt) {
        return attempt.id() == FIRST_ATTEMPT.getId();
    }

    public int getTotalSumAttempt(Frame currentFrame) {
        return currentFrame.getScoreBoard().values().stream()
                .mapToInt(Score::getValue)
                .sum();
    }

    public int getTotalSumAttemptForSpareFrame(Frame currentFrame) {
        return currentFrame.getScoreBoard().get(getAttempt(FIRST_ATTEMPT)).getValue() +
                currentFrame.getScoreBoard().get(new Attempt(SECOND_ATTEMPT.getId(), SECOND_ATTEMPT)).getValue();
    }

    public Frame getlastFrame() {
        int lastFrameIndex = this.getFrames().size() - 1;
        return this.frames.get(lastFrameIndex);
    }

    public Frame getFrame(int position) {
        return this.frames.get(position - 1);
    }

    public Frame getNextFrame(Frame aFrame) {
        if (isLastFrame(aFrame))
            return null;
        return this.getFrame(aFrame.getName().getId() + 1);
    }

    private boolean isLastFrame(Frame aFrame) {
        return aFrame.getName().getId() == INITIAL_FRAME_NUMBER;
    }

    public Frame getPreviousFrame(Frame aFrame) {
        if (isFirstFrame(aFrame))
            return null;
        return this.getFrame(aFrame.getName().getId() - 1);
    }

    private boolean isFirstFrame(Frame aFrame) {
        return aFrame.getName().getId() == FIRST_FRAME.getId();
    }
}
