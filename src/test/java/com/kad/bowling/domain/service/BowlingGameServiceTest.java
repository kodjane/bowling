package com.kad.bowling.domain.service;

import com.kad.bowling.domain.Frame;
import com.kad.bowling.domain.Player;
import com.kad.bowling.domain.exception.BowlingGameException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/*
 * Created By
 * @author Aime D. Kodjane
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class BowlingGameServiceTest {
    @Test
    void can_add_many_players_to_the_game() {
        // Given
        Player player1 = new Player();
        Player player2 = new Player();
        Player player3 = new Player();
        BowlingGameService bowlingGameService = new BowlingGameService(player1, player2, player3);

        // When
        List<Player> results = bowlingGameService.getPlayers();

        // Then
        assertThat(results)
                .hasSize(3)
                .contains(player1, player2, player3);
    }

    @Test
    void create_bowling_game_with_less_than_2_players_throws_RollingBallException() {
        assertThatThrownBy(() -> new BowlingGameService(new Player()))
                .isExactlyInstanceOf(BowlingGameException.class)
                .hasMessage("A bowling game should have at least 2 players before starting");
    }

    @Test
    void should_return_0_when_a_player_rolls_a_ball_in_gutter() {
        // Given
        Player player1 = new Player();
        BowlingGameService bowlingGameService = new BowlingGameService(player1, new Player());
        Frame frame = player1.getFrames().get(0);

        // When
        Frame result = bowlingGameService.rollsBall(0, player1, frame, 1);

        // Then
        assertThat(result.getScoreAt(1).getValue())
                .isEqualTo(0);
        assertThat(result.getPins())
                .isEqualTo(15);
    }

    @Test
    void should_return_1_when_a_player_rolls_1_pin_down() {
        // Given
        Player player1 = new Player();
        BowlingGameService bowlingGameService = new BowlingGameService(player1, new Player());
        Frame frame = player1.getFrames().get(0);

        // When
        Frame result = bowlingGameService.rollsBall(1, player1, frame, 1);

        // Then
        assertThat(result.getScoreAt(1).getValue())
                .isEqualTo(1);
    }
}