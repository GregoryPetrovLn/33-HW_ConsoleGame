package telran.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import telran.utils.ConsoleGame;

class GameTests {
	ConsoleGame game = new ConsoleGame();

	@Test
	void test() {
		try {
			game.game();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}
