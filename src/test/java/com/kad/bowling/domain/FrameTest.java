package com.kad.bowling.domain;

import com.kad.bowling.domain.enums.FrameName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static com.kad.bowling.domain.enums.FrameName.FIFTH_FRAME;
import static com.kad.bowling.domain.enums.FrameName.FIRST_FRAME;
import static org.assertj.core.api.Assertions.assertThat;

/*
 * Created By
 * @author Aime D. Kodjane
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class FrameTest {

    @Test
    void a_frame_has_a_name() {
        FrameName name = FIFTH_FRAME;
        Frame frame = new Frame(name);

        assertThat(frame.getName())
                .isEqualTo(name);
    }

    @Test
    void a_frame_has_3_initial_attempts() {
        Frame aFrame = new Frame(FIRST_FRAME);
        assertThat(aFrame.getScoreBoard().entrySet())
                .hasSize(3);
    }

    @Test
    void a_frame_has_15_initial_pins() {
        Frame aFrame = new Frame(FIRST_FRAME);
        assertThat(aFrame.getPins())
                .isEqualTo(15);
    }

    @Test
    void a_frame_has_an_initial_score_of_0() {
        Frame aFrame = new Frame(FIRST_FRAME);
        assertThat(aFrame.getTotalScore())
                .isEqualTo(0);
    }

}