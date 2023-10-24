package com.kad.bowling.domain;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static com.kad.bowling.domain.enums.ScoreType.SPARE;
import static com.kad.bowling.domain.enums.ScoreType.STRIKE;
import static org.assertj.core.api.Assertions.assertThat;

/*
 * Created By
 * @author Aime D. Kodjane
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class FrameTest {
    @Test
    void a_frame_has_3_initial_attempts() {
        Frame aFrame = new Frame();
        assertThat(aFrame.getScoreBoard().entrySet())
                .hasSize(3);
    }

    @Test
    void a_frame_has_15_initial_pins() {
        Frame aFrame = new Frame();
        assertThat(aFrame.getPins())
                .isEqualTo(15);
    }

    @Test
    void a_frame_has_an_initial_score_of_0() {
        Frame aFrame = new Frame();
        assertThat(aFrame.getTotalScore())
                .isEqualTo(0);
    }

    @Test
    void should_return_the_score_at_a_specific_attempt_inside_a_frame() {
        // Given
        Player aPlayer = new Player();
        Frame aFrame = aPlayer.getFrames().get(2);

        // When
        aFrame.knockPinsAndUpdateScoreAt(3, aPlayer, 1);
        Score result = aFrame.getScoreAt(1);

        // Then
        assertThat(result.getValue())
                .isEqualTo(3);
    }

    @Test
    void should_return_the_remaining_pins_after_many_rolls() {
        // Given
        Player aPlayer = new Player();
        Frame aFrame = aPlayer.getFrames().get(0);

        // When
        aFrame.knockPinsAndUpdateScoreAt(5, aPlayer, 1);

        // then
        assertThat(aFrame.getPins())
                .isEqualTo(10);

        // When
        aFrame.knockPinsAndUpdateScoreAt(3, aPlayer, 2);

        // then
        assertThat(aFrame.getPins())
                .isEqualTo(7);
    }

    @Test
    void should_sum_scores_after_all_attempts_in_a_frame_without_a_strike_or_a_spare() {
        // Given
        Player aPlayer = new Player();
        Frame aFrame = aPlayer.getFrames().get(2);
        aFrame.knockPinsAndUpdateScoreAt(3, aPlayer, 1);
        aFrame.knockPinsAndUpdateScoreAt(5, aPlayer, 2);
        aFrame.knockPinsAndUpdateScoreAt(4, aPlayer, 3);

        // When
        int result = aFrame.getTotalScore();

        // Then
        assertThat(result)
                .isEqualTo(12);
    }

    @Test
    void should_return_true_if_it_is_a_strike_at_first_attempt() {
        Player aPlayer = new Player();
        Frame aFrame = aPlayer.getFrames().get(0);
        aFrame.knockPinsAndUpdateScoreAt(15, aPlayer, 1);

        assertThat(aFrame.isStrike(1))
                .isTrue();
        assertThat(aFrame.getPins())
                .isEqualTo(0);
    }

    @Test
    void should_return_true_if_it_is_a_spare_at_the_second_attempt() {
        // Given
        Player aPlayer = new Player();
        Frame aFrame = aPlayer.getFrames().get(0);

        // When
        aFrame.knockPinsAndUpdateScoreAt(3, aPlayer, 1);
        aFrame.knockPinsAndUpdateScoreAt(12, aPlayer, 2);

        // then
        assertThat(aFrame.isSpare(2))
                .isTrue();
        assertThat(aFrame.getPins())
                .isEqualTo(0);
    }

    @Test
    void should_return_true_if_it_is_a_spare_at_the_third_attempt() {
        Player aPlayer = new Player();
        Frame aFrame = aPlayer.getFrames().get(0);

        aFrame.knockPinsAndUpdateScoreAt(3, aPlayer, 1);
        aFrame.knockPinsAndUpdateScoreAt(2, aPlayer, 2);
        aFrame.knockPinsAndUpdateScoreAt(10, aPlayer, 3);

        assertThat(aFrame.isSpare(3))
                .isTrue();
        assertThat(aFrame.getPins())
                .isEqualTo(0);
    }

    @Test
    void should_return_the_score_of_15_and_the_symbol_of_X_if_an_attempt_is_a_strike() {
        // Given
        Player aPlayer = new Player();
        Frame aFrame = aPlayer.getFrames().get(3);

        // When
        aFrame.knockPinsAndUpdateScoreAt(15, aPlayer, 1);
        Score result = aFrame.getScoreAt(1);

        //Then
        assertThat(result).isEqualTo(new Score(STRIKE.getValue(), STRIKE));
    }

    @Test
    void should_return_the_score_of_15_if_an_attempt_is_a_spare() {
        // Given
        Player aPlayer = new Player();
        Frame aFrame = aPlayer.getFrames().get(3);

        // When
        aFrame.knockPinsAndUpdateScoreAt(10, aPlayer, 1);
        aFrame.knockPinsAndUpdateScoreAt(5, aPlayer, 2);
        Score result = aFrame.getScoreAt(2);

        //Then
        assertThat(result).isEqualTo(new Score(SPARE.getValue(), SPARE));
    }

    @Test
    void should_add_an_extra_attempt_to_the_last_frame_when_there_is_a_strike() {
        // Given
        Player aPlayer = new Player();
        Frame frame1 = aPlayer.getFrames().get(3);
        Frame frame2 = aPlayer.getFrames().get(4);

        // When
        frame1.knockPinsAndUpdateScoreAt(15, aPlayer, 1);
        frame2.knockPinsAndUpdateScoreAt(15, aPlayer, 2);

        //Then
        assertThat(aPlayer.getlastFrame().getScoreBoard())
                .hasSize(5);
    }

    @Test
    void should_add_an_extra_attempt_to_the_last_frame_when_there_is_a_spare_at_the_second_attempt() {
        // Given
        Player aPlayer = new Player();
        Frame aFrame = aPlayer.getFrames().get(3);

        // When
        aFrame.knockPinsAndUpdateScoreAt(5, aPlayer, 1);
        aFrame.knockPinsAndUpdateScoreAt(10, aPlayer, 2);

        //Then
        assertThat(aPlayer.getlastFrame().getScoreBoard())
                .hasSize(4);
    }

    @Test
    void should_add_an_extra_attempt_to_the_last_frame_when_there_is_a_spare_at_the_third_attempt() {
        // Given
        Player aPlayer = new Player();
        Frame aFrame = aPlayer.getFrames().get(3);

        // When
        aFrame.knockPinsAndUpdateScoreAt(5, aPlayer, 1);
        aFrame.knockPinsAndUpdateScoreAt(5, aPlayer, 2);
        aFrame.knockPinsAndUpdateScoreAt(5, aPlayer, 3);

        //Then
        assertThat(aPlayer.getlastFrame().getScoreBoard())
                .hasSize(4);
    }
}