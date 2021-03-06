package kjd.gametheory.tictactoe;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class PlayerTest {

	@Test
	public void createPlayerByIdName() {
		Player p = new Player(1, "Player1");
		
		assertEquals(1, p.getId());
		assertEquals("Player1", p.getName());
		assertEquals(0, p.getTokens().size());
	}

	@Test
	public void createPlayerByIdNameToken() {
		PlayerMark x = new PlayerMark("X");
		Player p = new Player(1, "Player1", x);
		
		assertEquals(1, p.getId());
		assertEquals("Player1", p.getName());
		assertEquals(1, p.getTokens().size());
		assertEquals("X", p.getTokens().get(0).getName());
	}
	
	@Test
	public void createPlayerFromPlayer() {
		PlayerMark x = new PlayerMark("X");
		Player p1 = new Player(1, "Player1", x);
		Player p2 = new Player(p1);
		
		assertNotSame(p1, p2);
		assertEquals(p1.getId(), p2.getId());
		assertEquals(p1.getName(), p2.getName());
		
		assertNotSame(p1.getTokens(), p2.getTokens());
		assertSame(p1.getTokens().size(), p2.getTokens().size());		
	}
}
