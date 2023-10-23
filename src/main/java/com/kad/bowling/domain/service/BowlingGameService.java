package com.kad.bowling.domain.service;

import com.kad.bowling.domain.Frame;
import com.kad.bowling.domain.Player;
import com.kad.bowling.domain.enums.Attempt;
import com.kad.bowling.domain.exception.RollingBallException;

import java.util.Arrays;
import java.util.List;

/*
 * Created By
 * @author Aime D. Kodjane
 */
public class BowlingGameService {

    private final List<Player> players;
    private final int MINIMUM_PLAYER_PER_GAME = 2;

    public BowlingGameService(Player... players) {
        List<Player> addedPlayers = Arrays.asList(players);

        if (hasMinimumTwoPlayers(addedPlayers))
            throw new RollingBallException("A bowling game should have at least 2 players before starting");

        this.players = addedPlayers;
    }

    /**
     * This method Allows a Player to roll a ball
     *
     * @param pinsDown Number of pins down
     * @param player   The player that rolls the ball
     * @param frame    The id of the frame in which the ball is rolled
     * @param attempt  the specific attempt inside the frame
     * @return The frameId
     */
    public Frame rollsBall(int pinsDown, Player player, Frame frame, Attempt attempt) {
        // TODO in progress
        updateFrame(pinsDown, attempt, frame);

        return frame;
    }

    private boolean hasMinimumTwoPlayers(List<Player> players) {
        return players.size() < MINIMUM_PLAYER_PER_GAME;
    }

    private void updateFrame(int pinsDown, Attempt attempt, Frame frame) {
        frame.knockPinsAndUpdateScoreAt(pinsDown, attempt);
    }

    public List<Player> getPlayers() {
        return players;
    }
}
