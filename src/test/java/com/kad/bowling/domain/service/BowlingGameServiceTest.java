package com.kad.bowling.domain.service;

import com.kad.bowling.domain.Attempt;
import com.kad.bowling.domain.Frame;
import com.kad.bowling.domain.Player;
import com.kad.bowling.domain.enums.AttemptName;
import com.kad.bowling.domain.exception.BowlingGameException;
import com.kad.bowling.domain.exception.RollingBallException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.kad.bowling.domain.enums.AttemptName.*;
import static com.kad.bowling.domain.enums.FrameName.FIRST_FRAME;
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
        bowlingGameService.rollsBall(0, player1, frame, new Attempt(1, FIRST_ATTEMPT));

        // Then
        assertThat(frame.getScoreAt(new Attempt(1, FIRST_ATTEMPT)).getValue())
                .isEqualTo(0);
        assertThat(frame.getPins())
                .isEqualTo(15);
    }

    @Test
    void should_return_1_when_a_player_rolls_1_pin_down() {
        // Given
        Player player1 = new Player();
        BowlingGameService bowlingGameService = new BowlingGameService(player1, new Player());
        Frame frame = player1.getFrames().get(0);

        // When
        bowlingGameService.rollsBall(1, player1, frame, new Attempt(1, FIRST_ATTEMPT));

        // Then
        assertThat(frame.getScoreAt(new Attempt(1, FIRST_ATTEMPT)).getValue())
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
                bowlingGameService.rollsBall(0, aPlayer, aFrame, attempt);
                score += aFrame.getScoreAt(attempt).getValue();
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

    @Test
    @Disabled
    void result_of_spare_frame_should_be_equal_to_the_sum_of_the_2_attempts_of_the_next_frame_2_attempts_plus_the_previous_frame() {
//      scenario => ( |8|1|1| ## |8|/| | ## |1|2|1| )
//      results =>       10    |   28     |    31

        // Given
        Player player1 = new Player();
        Player player2 = new Player();
        Frame currentFrame = player1.getFrame(FIRST_FRAME.getId());
        Frame nextFrame = player1.getFrame(currentFrame.getName().getId() + 1);
        Frame secondNextFrame = player1.getFrame(nextFrame.getName().getId() + 1);


        // When
        BowlingGameService bowlingGameService = new BowlingGameService(player1, player2);
        bowlingGameService.rollsBall(8, player1, currentFrame, new Attempt(FIRST_ATTEMPT.getId(), FIRST_ATTEMPT));
        bowlingGameService.rollsBall(1, player1, currentFrame, new Attempt(SECOND_ATTEMPT.getId(), SECOND_ATTEMPT));
        bowlingGameService.rollsBall(1, player1, currentFrame, new Attempt(THIRD_ATTEMPT.getId(), THIRD_ATTEMPT));
        bowlingGameService.rollsBall(8, player1, nextFrame, new Attempt(FIRST_ATTEMPT.getId(), FIRST_ATTEMPT));
        bowlingGameService.rollsBall(7, player1, nextFrame, new Attempt(SECOND_ATTEMPT.getId(), SECOND_ATTEMPT));
        bowlingGameService.rollsBall(1, player1, secondNextFrame, new Attempt(FIRST_ATTEMPT.getId(), FIRST_ATTEMPT));
        bowlingGameService.rollsBall(2, player1, secondNextFrame, new Attempt(SECOND_ATTEMPT.getId(), SECOND_ATTEMPT));
        bowlingGameService.rollsBall(1, player1, secondNextFrame, new Attempt(THIRD_ATTEMPT.getId(), THIRD_ATTEMPT));

        // Then
        assertThat(currentFrame.getTotalScore())
                .isEqualTo(10);
        assertThat(nextFrame.getTotalScore())
                .isEqualTo(28);
        assertThat(secondNextFrame.getTotalScore())
                .isEqualTo(31);
    }
}