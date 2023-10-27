package com.kad.bowling;

import com.kad.bowling.domain.Attempt;
import com.kad.bowling.domain.Frame;
import com.kad.bowling.domain.Player;
import com.kad.bowling.domain.service.BowlingGameService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.kad.bowling.domain.enums.AttemptName.*;
import static com.kad.bowling.domain.enums.FrameName.FIRST_FRAME;

@SpringBootApplication
public class BowlingGameApplication {

	public static void main(String[] args) {
		SpringApplication.run(BowlingGameApplication.class, args);

		Player player1 = new Player();
		Player player2 = new Player();
		Frame firstFrame = player1.getFrame(FIRST_FRAME.getId());
		Frame secondFrame = player1.getFrame(firstFrame.getName().getId() + 1);
		Frame thirdFrame = player1.getFrame(secondFrame.getName().getId() + 1);


		BowlingGameService bowlingGameService = new BowlingGameService(player1, player2);
		bowlingGameService.rollsBall(8, player1, firstFrame, new Attempt(FIRST_ATTEMPT.getId(), FIRST_ATTEMPT));
		bowlingGameService.rollsBall(1, player1, firstFrame, new Attempt(SECOND_ATTEMPT.getId(), SECOND_ATTEMPT));
		bowlingGameService.rollsBall(1, player1, firstFrame, new Attempt(THIRD_ATTEMPT.getId(), THIRD_ATTEMPT));
		bowlingGameService.rollsBall(8, player1, secondFrame, new Attempt(FIRST_ATTEMPT.getId(), FIRST_ATTEMPT));
		bowlingGameService.rollsBall(7, player1, secondFrame, new Attempt(SECOND_ATTEMPT.getId(), SECOND_ATTEMPT));
		bowlingGameService.rollsBall(1, player1, thirdFrame, new Attempt(FIRST_ATTEMPT.getId(), FIRST_ATTEMPT));
		bowlingGameService.rollsBall(2, player1, thirdFrame, new Attempt(SECOND_ATTEMPT.getId(), SECOND_ATTEMPT));
		bowlingGameService.rollsBall(1, player1, thirdFrame, new Attempt(THIRD_ATTEMPT.getId(), THIRD_ATTEMPT));


		System.out.println("Result of " + firstFrame.getName().name() + " is : " + firstFrame.getTotalScore());
		System.out.println("Result of " + secondFrame.getName().name() + " is : " + secondFrame.getTotalScore());
		System.out.println("Result of " + thirdFrame.getName().name() + " is : "+ thirdFrame.getTotalScore());
	}

}
