package com.kad.bowling.domain.service;

import com.kad.bowling.domain.Attempt;
import com.kad.bowling.domain.Frame;
import com.kad.bowling.domain.Player;
import com.kad.bowling.domain.exception.BowlingGameException;
import com.kad.bowling.domain.exception.RollingBallException;

import java.util.Arrays;
import java.util.List;

import static com.kad.bowling.domain.Frame.INITIAL_PINS_PER_GAME;

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
            throw new BowlingGameException("A bowling game should have at least 2 players before starting");

        this.players = addedPlayers;
    }

    /**
     * This method Allows a Player to roll a ball in a frame
     * @param pinsDown Number of pins down
     * @param player   The player that rolls the ball
     * @param frame    The id of the frame in which the ball is rolled
     * @param attempt  the specific attempt inside the frame
     * @return The frameId
     */
    public void rollsBall(int pinsDown, Player player, Frame frame, Attempt attempt) {

        if (pinsDown < 0 || pinsDown > INITIAL_PINS_PER_GAME )
            throw new RollingBallException("The number of pins should be between 0 and 15");
        // TODO in progress
        updateFrame(pinsDown, player, frame, attempt);

    }

    /**
     * This methods allows to verify that the game has the minimum amount of players before starting
     * @param players The list of the players that participate in the game
     * @return true is the condition is satisfied
     */
    private boolean hasMinimumTwoPlayers(List<Player> players) {
        return players.size() < MINIMUM_PLAYER_PER_GAME;
    }

    private void updateFrame(int pinsDown, Player player, Frame frame, Attempt attempt) {
        player.knockPinsAndUpdateScoreAt(pinsDown, frame, attempt);
    }

    public List<Player> getPlayers() {
        return players;
    }
}
