package kjd.gametheory.game.test;

import kjd.gametheory.game.GamePlayer;

public class TestPlayer extends GamePlayer<TestToken, TestPlayer> {

	public TestPlayer(int id, String name) {
		super(id, name);
	}

	public TestPlayer(TestPlayer player) {
		super(player);
	}
	
}
