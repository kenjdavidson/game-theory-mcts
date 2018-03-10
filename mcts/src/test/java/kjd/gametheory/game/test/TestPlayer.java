package kjd.gametheory.game.test;

import kjd.gametheory.game.Player;

public class TestPlayer extends Player<TestToken, TestPlayer> {

	public TestPlayer(int id, String name) {
		super(id, name);
	}

	public TestPlayer(TestPlayer player) {
		super(player);
	}
	
}
