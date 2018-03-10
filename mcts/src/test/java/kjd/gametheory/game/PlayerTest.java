package kjd.gametheory.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import kjd.gametheory.game.test.TestPlayer;

@RunWith(JUnit4.class)
public class PlayerTest {

	@Test
	public void createNewPlayer_successful() {
		TestPlayer p = new TestPlayer(1, "Player1");
		
		assertEquals(1, p.getId());
		assertEquals("Player1", p.getName());
		assertEquals(0, p.getTokens().size());
	}

	@Test
	public void copyPlayer_successfulCopy() {
		TestPlayer p = new TestPlayer(1, "Player1");
		TestPlayer copy = new TestPlayer(p);
		
		assertNotSame(copy, p);
		assertEquals(copy.getId(), p.getId());
		assertEquals(copy.getName(), p.getName());
		assertEquals(0, p.getTokens().size());
	}
}
