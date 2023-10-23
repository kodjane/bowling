package com.kad.bowling;

import com.kad.bowling.domain.Frame;
import com.kad.bowling.domain.Player;
import com.kad.bowling.domain.enums.Attempt;
import com.kad.bowling.domain.service.BowlingGameService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BowlingGameApplication {

	public static void main(String[] args) {
		SpringApplication.run(BowlingGameApplication.class, args);
	}

}
