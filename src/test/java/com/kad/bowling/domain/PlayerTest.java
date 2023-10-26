package com.kad.bowling.domain;

import org.junit.jupiter.api.*;

import static com.kad.bowling.domain.enums.AttemptName.*;
import static com.kad.bowling.domain.enums.FrameName.*;
import static com.kad.bowling.domain.enums.ScoreType.SPARE;
import static com.kad.bowling.domain.enums.ScoreType.STRIKE;
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

        Player player1 = new Player();

        assertThat(player1.getFrames())
                .hasSize(INITIAL_FRAME_PER_GAME);

        Player player2 = new Player();

        assertThat(player2.getFrames())
                .hasSize(INITIAL_FRAME_PER_GAME);
    }

    @Test
    void can_add_an_extra_attempt_to_the_last_frame() {
        Player aPlayer = new Player();
        Frame lastFrame = aPlayer.getlastFrame();

        aPlayer.addAnExtraAttemptToTheLastFrame();

        assertThat(lastFrame.getScoreBoard())
                .hasSize(4);
    }

    @Test
    void should_return_the_previous_frame_of_a_game() {
        Player aPlayer = new Player();
        Frame aFrame = aPlayer.getFrame(SECOND_FRAME.getId());
        Frame expectedFrame = aPlayer.getFrame(1);

        Frame result = aPlayer.getPreviousFrame(aFrame);

        assertThat(result.getName())
                .isEqualTo(expectedFrame.getName());
    }

    @Test
    void should_return_null_when_trying_to_get_the_previous_frame_of_the_first_frame() {
        Player aPlayer = new Player();
        Frame aFrame = aPlayer.getFrame(FIRST_FRAME.getId());

        Frame result = aPlayer.getPreviousFrame(aFrame);

        assertThat(result)
                .isNull();
    }

    @Test
    void should_return_the_next_frame_of_the_game() {
        Player aPlayer = new Player();
        Frame currentFrame = aPlayer.getFrame(2);
        Frame nextFrame = aPlayer.getFrame(3);

        Frame result = aPlayer.getNextFrame(currentFrame);

        assertThat(result.getName())
                .isEqualTo(nextFrame.getName());
    }

    @Test
    void should_return_null_when_trying_to_get_the_next_frame_of_fifth_frame() {
        Player aPlayer = new Player();
        Frame aFrame = aPlayer.getFrame(FIFTH_FRAME.getId());

        Frame result = aPlayer.getNextFrame(aFrame);

        assertThat(result)
                .isNull();
    }

    @Nested
    class KnockingPinsTest {
        @Test
        void should_return_the_score_at_a_specific_attempt_inside_a_frame() {
            // Given
            Player aPlayer = new Player();
            Frame aFrame = aPlayer.getFrames().get(2);
            Attempt firstAttempt = new Attempt(1, FIRST_ATTEMPT);

            // When
            aPlayer.knockPinsAndUpdateScoreAt(3, aFrame, firstAttempt);
            Score result = aFrame.getScoreAt(firstAttempt);

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
            aPlayer.knockPinsAndUpdateScoreAt(5, aFrame, new Attempt(1, FIRST_ATTEMPT));

            // then
            assertThat(aFrame.getPins())
                    .isEqualTo(10);

            // When
            aPlayer.knockPinsAndUpdateScoreAt(3, aFrame, new Attempt(2, SECOND_ATTEMPT));

            // then
            assertThat(aFrame.getPins())
                    .isEqualTo(7);
        }

        @Test
        void should_return_true_if_it_is_a_strike_at_first_attempt() {
            // Given
            Player aPlayer = new Player();
            Frame aFrame = aPlayer.getFrames().get(0);
            Attempt firstAttempt = new Attempt(1, FIRST_ATTEMPT);
            int pinsDown = 15;

            // When
            aPlayer.knockPinsAndUpdateScoreAt(pinsDown, aFrame, firstAttempt);

            // Then
            assertThat(aFrame.isStrike(firstAttempt))
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
            aPlayer.knockPinsAndUpdateScoreAt(3, aFrame, new Attempt(1, FIRST_ATTEMPT));
            Attempt secondAttempt = new Attempt(2, SECOND_ATTEMPT);
            aPlayer.knockPinsAndUpdateScoreAt(12, aFrame, secondAttempt);

            // then
            assertThat(aFrame.isSpare(secondAttempt))
                    .isTrue();
            assertThat(aFrame.getPins())
                    .isEqualTo(0);
        }

        @Test
        void should_return_true_if_it_is_a_spare_at_the_third_attempt() {
            // Given
            Player aPlayer = new Player();
            Frame aFrame = aPlayer.getFrames().get(0);
            Attempt thirdAttempt = new Attempt(3, THIRD_ATTEMPT);

            // When
            aPlayer.knockPinsAndUpdateScoreAt(3, aFrame, new Attempt(1, FIRST_ATTEMPT));
            aPlayer.knockPinsAndUpdateScoreAt(2, aFrame, new Attempt(2, SECOND_ATTEMPT));
            aPlayer.knockPinsAndUpdateScoreAt(10, aFrame, thirdAttempt);

            // Then
            assertThat(aFrame.isSpare(thirdAttempt))
                    .isTrue();
            assertThat(aFrame.getPins())
                    .isEqualTo(0);
        }

        @Test
        void should_return_the_score_of_15_and_the_symbol_of_X_if_an_attempt_is_a_strike() {
            // Given
            Player aPlayer = new Player();
            Frame aFrame = aPlayer.getFrames().get(3);
            Attempt firstAttempt = new Attempt(1, FIRST_ATTEMPT);

            // When
            aPlayer.knockPinsAndUpdateScoreAt(15, aFrame, firstAttempt);
            Score result = aFrame.getScoreAt(firstAttempt);

            //Then
            assertThat(result)
                    .isEqualTo(new Score(STRIKE.getValue(), STRIKE));
        }

        @Test
        void should_return_the_score_of_15_and_the_symbol_of_spare_if_an_attempt_is_a_spare() {
            // Given
            Player aPlayer = new Player();
            Frame aFrame = aPlayer.getFrames().get(3);
            Attempt secondAttempt = new Attempt(2, SECOND_ATTEMPT);

            // When
            aPlayer.knockPinsAndUpdateScoreAt(10, aFrame, new Attempt(1, FIRST_ATTEMPT));
            aPlayer.knockPinsAndUpdateScoreAt(5, aFrame, secondAttempt);
            Score result = aFrame.getScoreAt(secondAttempt);

            //Then
            assertThat(result)
                    .isEqualTo(new Score(SPARE.getValue(), SPARE));
        }

        @Test
        void should_sum_scores_after_all_attempts_in_a_frame_without_a_strike_or_a_spare() {
//          scenario => ( 8|1|1 )
//          result => 26
            // Given
            Player aPlayer = new Player();
            Frame aFrame = aPlayer.getFrame(1);

            // When
            aPlayer.knockPinsAndUpdateScoreAt(8, aFrame, new Attempt(1, FIRST_ATTEMPT));
            aPlayer.knockPinsAndUpdateScoreAt(1, aFrame, new Attempt(2, SECOND_ATTEMPT));
            aPlayer.knockPinsAndUpdateScoreAt(1, aFrame, new Attempt(3, THIRD_ATTEMPT));

            // Then
            assertThat(aFrame.getTotalScore())
                    .isEqualTo(10);
        }

        @Test
        void result_of_a_strike_frame_equals_to_the_sum_of_the_3_attempts_of_the_next_frame_plus_the_result_of_the_current_frame() {
//          scenario => ( X| | | ## 8|2|1 )
//          result => 26

            // Given
            Player aPlayer = new Player();
            int currentFramePosition = 1;
            Frame currentFrame = aPlayer.getFrame(currentFramePosition);
            int nextFramePosition = currentFramePosition + 1;
            Frame nextFrame = aPlayer.getFrame(nextFramePosition);

            // When
            aPlayer.knockPinsAndUpdateScoreAt(15, currentFrame, new Attempt(FIRST_ATTEMPT.getId(), FIRST_ATTEMPT));
            aPlayer.knockPinsAndUpdateScoreAt(8, nextFrame, new Attempt(FIRST_ATTEMPT.getId(), FIRST_ATTEMPT));
            aPlayer.knockPinsAndUpdateScoreAt(2, nextFrame, new Attempt(SECOND_ATTEMPT.getId(), SECOND_ATTEMPT));
            aPlayer.knockPinsAndUpdateScoreAt(1, nextFrame, new Attempt(THIRD_ATTEMPT.getId(), THIRD_ATTEMPT));

            // Then
            assertThat(currentFrame.getTotalScore())
                    .isEqualTo(26);
        }

        @Test
        void result_of_two_successive_strike_equals_to_the_sum_of_the_3_attempts_of_the_next_frame_plus_the_sum_all_the_previous_strike() {
//          scenario => ( X| | | ## X| | | ## |8|2|1| )
//          result => 41
            // Given
            Player aPlayer = new Player();
            Frame currentFrame = aPlayer.getFrame(FIRST_FRAME.getId());
            Frame nextFrame = aPlayer.getFrame(currentFrame.getName().getId() + 1);
            Frame secondNextFrame = aPlayer.getFrame(nextFrame.getName().getId() + 1);

            // Given
            aPlayer.knockPinsAndUpdateScoreAt(15, currentFrame, new Attempt(FIRST_ATTEMPT.getId(), FIRST_ATTEMPT));
            aPlayer.knockPinsAndUpdateScoreAt(15, nextFrame, new Attempt(FIRST_ATTEMPT.getId(), FIRST_ATTEMPT));
            aPlayer.knockPinsAndUpdateScoreAt(8, secondNextFrame, new Attempt(FIRST_ATTEMPT.getId(), FIRST_ATTEMPT));
            aPlayer.knockPinsAndUpdateScoreAt(2, secondNextFrame, new Attempt(SECOND_ATTEMPT.getId(), SECOND_ATTEMPT));
            aPlayer.knockPinsAndUpdateScoreAt(1, secondNextFrame, new Attempt(THIRD_ATTEMPT.getId(), THIRD_ATTEMPT));

            // Then
            assertThat(currentFrame.getTotalScore())
                    .isEqualTo(41);
        }

        @Test
        void result_of_many_successive_strike_equals_to_the_sum_of_the_3_attempts_of_the_next_frame_plus_the_sum_all_the_previous_strike() {
//          scenario => ( X| | | ## X| | | ## X| | | ## |8|2|1| )
//          result => 56
            // Given
            Player aPlayer = new Player();
            Frame currentFrame = aPlayer.getFrame(FIRST_FRAME.getId());
            Frame nextFrame = aPlayer.getFrame(currentFrame.getName().getId() + 1);
            Frame secondFrame = aPlayer.getFrame(nextFrame.getName().getId() + 1);
            Frame thirdFrame = aPlayer.getFrame(secondFrame.getName().getId() + 1);

            // Given
            aPlayer.knockPinsAndUpdateScoreAt(15, currentFrame, new Attempt(FIRST_ATTEMPT.getId(), FIRST_ATTEMPT));
            aPlayer.knockPinsAndUpdateScoreAt(15, nextFrame, new Attempt(FIRST_ATTEMPT.getId(), FIRST_ATTEMPT));
            aPlayer.knockPinsAndUpdateScoreAt(15, secondFrame, new Attempt(FIRST_ATTEMPT.getId(), FIRST_ATTEMPT));
            aPlayer.knockPinsAndUpdateScoreAt(8, thirdFrame, new Attempt(FIRST_ATTEMPT.getId(), FIRST_ATTEMPT));
            aPlayer.knockPinsAndUpdateScoreAt(2, thirdFrame, new Attempt(SECOND_ATTEMPT.getId(), SECOND_ATTEMPT));
            aPlayer.knockPinsAndUpdateScoreAt(1, thirdFrame, new Attempt(THIRD_ATTEMPT.getId(), THIRD_ATTEMPT));

            // Then
            assertThat(currentFrame.getTotalScore())
                    .isEqualTo(56);
        }

        @Test
        @Disabled
        void result_should_be_60_when_there_is_a_strike_at_a_frame_and_the_next_3_attempts_of_the_frame_are_strikes() {
            // TODO enable this test for testing the last frame, because only the last frame allow to strike and stay in the same frame
            Player aPlayer = new Player();
            Frame currentFrame = aPlayer.getFrame(FIRST_FRAME.getId());
            Frame nextFrame = aPlayer.getFrame(currentFrame.getName().getId() + 1);

            aPlayer.knockPinsAndUpdateScoreAt(15, currentFrame, new Attempt(FIRST_ATTEMPT.getId(), FIRST_ATTEMPT));
            aPlayer.knockPinsAndUpdateScoreAt(15, nextFrame, new Attempt(FIRST_ATTEMPT.getId(), FIRST_ATTEMPT));
            aPlayer.knockPinsAndUpdateScoreAt(15, nextFrame, new Attempt(SECOND_ATTEMPT.getId(), SECOND_ATTEMPT));
            aPlayer.knockPinsAndUpdateScoreAt(15, nextFrame, new Attempt(THIRD_ATTEMPT.getId(), THIRD_ATTEMPT));

            assertThat(currentFrame.getTotalScore())
                    .isEqualTo(60);
        }

        @Test
        void result_of_a_spare_should_be_equals_to_the_sum_of_the_next_frame_2_attempts_plus_the_result_of_the_current_frame() {
            Player aPlayer = new Player();
            Frame currentFrame = aPlayer.getFrame(FIRST_FRAME.getId());
            Frame nextFrame = aPlayer.getFrame(currentFrame.getName().getId() + 1);

            aPlayer.knockPinsAndUpdateScoreAt(5, currentFrame, new Attempt(FIRST_ATTEMPT.getId(), FIRST_ATTEMPT));
            aPlayer.knockPinsAndUpdateScoreAt(10, currentFrame, new Attempt(SECOND_ATTEMPT.getId(), SECOND_ATTEMPT));
            aPlayer.knockPinsAndUpdateScoreAt(10, nextFrame, new Attempt(FIRST_ATTEMPT.getId(), FIRST_ATTEMPT));
            aPlayer.knockPinsAndUpdateScoreAt(1, nextFrame, new Attempt(SECOND_ATTEMPT.getId(), SECOND_ATTEMPT));
            aPlayer.knockPinsAndUpdateScoreAt(1, nextFrame, new Attempt(THIRD_ATTEMPT.getId(), THIRD_ATTEMPT));

            assertThat(currentFrame.getTotalScore())
                    .isEqualTo(27);

        }
    }

}
