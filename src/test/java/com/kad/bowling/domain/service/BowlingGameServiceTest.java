package com.kad.bowling.domain.service;

import com.kad.bowling.domain.Attempt;
import com.kad.bowling.domain.Frame;
import com.kad.bowling.domain.Player;
import com.kad.bowling.domain.enums.AttemptName;
import com.kad.bowling.domain.exception.BowlingGameException;
import com.kad.bowling.domain.exception.RollingBallException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.kad.bowling.domain.enums.AttemptName.FIRST_ATTEMPT;
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
    void create_bowling_game_with_less_than_2_players_throws_BowlingGameException() {
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
        Frame result = bowlingGameService.rollsBall(0, player1, frame, new Attempt(1, FIRST_ATTEMPT));

        // Then
        assertThat(result.getScoreAt(new Attempt(1, FIRST_ATTEMPT)).getValue())
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
        Frame result = bowlingGameService.rollsBall(1, player1, frame, new Attempt(1, FIRST_ATTEMPT));

        // Then
        assertThat(result.getScoreAt(new Attempt(1, FIRST_ATTEMPT)).getValue())
                .isEqualTo(1);
    }

    @Test
    void should_return_a_score_of_0_for_a_gutter_game() {
        Player player1 = new Player();
        Player player2 = new Player();
        BowlingGameService bowlingGameService = new BowlingGameService(player1, player2);

        assertThat(scoreAllInGutter(player2, bowlingGameService))
                .isEqualTo(0);
    }

    private int scoreAllInGutter(Player aPlayer, BowlingGameService bowlingGameService) {
        int score = 0;

        for (Frame aFrame : aPlayer.getFrames()) {
            for (AttemptName attemptName : AttemptName.getInitialValues()) {
                Attempt attempt = new Attempt(attemptName.getId(), attemptName);
                Frame result = bowlingGameService.rollsBall(0, aPlayer, aFrame, attempt);
                score += result.getScoreAt(attempt).getValue();
            }
        }

        return score;
    }

    @Test
    void should_throw_an_RollingBallException_when_trying_to_roll_illegal_Pins_number() {
        Player player1 = new Player();
        Player player2 = new Player();
        BowlingGameService bowlingGameService = new BowlingGameService(player1, player2);
        Frame aFrame = player2.getFrames().get(0);

        assertThatThrownBy(() -> bowlingGameService.rollsBall(20, player2, aFrame, new Attempt(1, FIRST_ATTEMPT)))
                .isExactlyInstanceOf(RollingBallException.class)
                .hasMessage("The number of pins should be between 0 and 15");

        assertThatThrownBy(() -> bowlingGameService.rollsBall(-1, player2, aFrame, new Attempt(1, FIRST_ATTEMPT)))
                .isExactlyInstanceOf(RollingBallException.class)
                .hasMessage("The number of pins should be between 0 and 15");
    }
}