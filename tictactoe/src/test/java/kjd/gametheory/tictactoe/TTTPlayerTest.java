package kjd.gametheory.tictactoe;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TTTPlayerTest {

	@Test
	public void createPlayerByIdName() {
		TTTPlayer p = new TTTPlayer(1, "Player1");
		
		assertEquals(1, p.getId());
		assertEquals("Player1", p.getName());
		assertEquals(0, p.getTokens().size());
	}

	@Test
	public void createPlayerByIdNameToken() {
		TTTMark x = new TTTMark("X");
		TTTPlayer p = new TTTPlayer(1, "Player1", x);
		
		assertEquals(1, p.getId());
		assertEquals("Player1", p.getName());
		assertEquals(1, p.getTokens().size());
		assertEquals("X", p.getTokens().get(0).getName());
	}
	
	@Test
	public void createPlayerFromPlayer() {
		TTTMark x = new TTTMark("X");
		TTTPlayer p1 = new TTTPlayer(1, "Player1", x);
		TTTPlayer p2 = new TTTPlayer(p1);
		
		assertNotSame(p1, p2);
		assertEquals(p1.getId(), p2.getId());
		assertEquals(p1.getName(), p2.getName());
		
		assertNotSame(p1.getTokens(), p2.getTokens());
		assertSame(p1.getTokens().size(), p2.getTokens().size());		
	}
}
