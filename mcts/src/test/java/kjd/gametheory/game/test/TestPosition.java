package kjd.gametheory.game.test;

import kjd.gametheory.game.BoardPosition;

public class TestPosition extends BoardPosition<TestToken, TestPlayer> {

	public TestPosition(int x, int y) {
		super(x, y);
	}

	public TestPosition(TestPosition position) {
		super(position);
	}
	
}
