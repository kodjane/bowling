package com.kad.bowling.domain;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * Created By
 * @author Aime D. Kodjane
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PlayerTest {

    @Test
    void each_player_has_five_initial_frames_per_game() {
        int INITIAL_FRAME_PER_GAME = 5;

        Player player1 = createPlayer();

        assertThat(player1.getFrames())
                .hasSize(INITIAL_FRAME_PER_GAME);

        Player player2 = createPlayer();

        assertThat(player2.getFrames())
                .hasSize(INITIAL_FRAME_PER_GAME);
    }

    private Player createPlayer() {
        return new Player();
    }
}