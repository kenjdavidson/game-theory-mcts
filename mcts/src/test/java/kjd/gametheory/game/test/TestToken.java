package kjd.gametheory.game.test;

import kjd.gametheory.game.PlayerToken;

public class TestToken extends PlayerToken<TestPlayer, TestToken> {

	public TestToken(String name, String type) {
		super(name, type);
	}

	public TestToken(String name, String type, TestPlayer playerId) {
		super(name, type, playerId);
	}

	public TestToken(PlayerToken<TestPlayer, TestToken> token) {
		super(token);
	}

}
