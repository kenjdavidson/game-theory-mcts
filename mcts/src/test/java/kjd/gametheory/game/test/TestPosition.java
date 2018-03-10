package kjd.gametheory.game.test;

import kjd.gametheory.game.Position;

public class TestPosition extends Position<TestToken, TestPlayer> {

	public TestPosition(int x, int y) {
		super(x, y);
	}

	public TestPosition(TestPosition position) {
		super(position);
	}
	
}
