package com.kad.bowling.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static com.kad.bowling.domain.enums.Attempt.*;
import static org.assertj.core.api.Assertions.assertThat;

/*
 * Created By
 * @author Aime D. Kodjane
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class FrameTest {
    private Frame aFrame;

    @BeforeEach
    void setUp() {
        aFrame = createFrame();
    }

    @Test
    void a_frame_has_3_initial_attempts() {
        assertThat(aFrame.getScoreBoard().entrySet())
                .hasSize(3);
    }

    @Test
    void a_frame_has_15_initial_pins() {
        assertThat(aFrame.getPins())
                .isEqualTo(15);
    }

    @Test
    void a_frame_has_an_initial_score_of_0() {
        assertThat(aFrame.getTotalScore())
                .isEqualTo(0);
    }

    @Test
    void should_return_the_score_at_a_specific_attempt_inside_a_frame() {
        // Given
        fillFrameScoreBoard();

        // When
        int result = aFrame.getScoreAt(FIRST_ATTEMPT);

        // Then
        assertThat(result)
                .isEqualTo(3);
    }

    @Test
    void should_return_the_remaining_pins_after_many_rolls() {
        aFrame.knockPinsAndUpdateScoreAt(5, FIRST_ATTEMPT);

        assertThat(aFrame.getPins())
                .isEqualTo(10);

        aFrame.knockPinsAndUpdateScoreAt(3, SECOND_ATTEMPT);

        assertThat(aFrame.getPins())
                .isEqualTo(7);
    }

    @Test
    void should_sum_scores_after_all_attempts_in_a_frame_without_a_strike_or_a_spare() {
        // Given
        fillFrameScoreBoard();

        // When
        int result = aFrame.getTotalScore();

        // Then
        assertThat(result)
                .isEqualTo(12);
    }

    @Test
    void should_return_true_if_it_is_a_strike_at_first_attempt() {
        aFrame.knockPinsAndUpdateScoreAt(15, FIRST_ATTEMPT);

        assertThat(aFrame.isStrike(FIRST_ATTEMPT))
                .isTrue();
    }

    @Test
    void should_return_true_if_it_is_a_spare_at_the_second_attempt() {
        aFrame.knockPinsAndUpdateScoreAt(3, FIRST_ATTEMPT);
        aFrame.knockPinsAndUpdateScoreAt(12, SECOND_ATTEMPT);

        assertThat(aFrame.isSpare(SECOND_ATTEMPT))
                .isTrue();
    }

    @Test
    void should_return_true_if_it_is_a_spare_at_the_third_attempt() {
        aFrame.knockPinsAndUpdateScoreAt(3, FIRST_ATTEMPT);
        aFrame.knockPinsAndUpdateScoreAt(2, SECOND_ATTEMPT);
        aFrame.knockPinsAndUpdateScoreAt(10, THIRD_ATTEMPT);

        assertThat(aFrame.isSpare(THIRD_ATTEMPT))
                .isTrue();
    }

    private void fillFrameScoreBoard() {
        aFrame.knockPinsAndUpdateScoreAt(3, FIRST_ATTEMPT);
        aFrame.knockPinsAndUpdateScoreAt(5, SECOND_ATTEMPT);
        aFrame.knockPinsAndUpdateScoreAt(4, THIRD_ATTEMPT);
    }

    private Frame createFrame() {
        return new Frame();
    }
}