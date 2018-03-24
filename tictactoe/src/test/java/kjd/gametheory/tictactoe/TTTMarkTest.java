package kjd.gametheory.tictactoe;

import static org.junit.Assert.*;

import org.junit.Test;

public class TTTMarkTest {

	@Test
	public void createMarkFromName() {
		TTTMark x = new TTTMark("X");
		
		assertEquals("X", x.getName());
		assertEquals("X", x.getType());
		assertEquals(null, x.getPlayer());
	}

	@Test
	public void createMarkFromNameAndType() {
		TTTMark x = new TTTMark("X", "TicTacToe");
		
		assertEquals("X", x.getName());
		assertEquals("TicTacToe", x.getType());
		assertEquals(null, x.getPlayer());
	}
	
	@Test
	public void createMarkFromNameTypeAndPlayer() {
		TTTPlayer p = new TTTPlayer(1, "Player1");
		TTTMark x = new TTTMark("X", "TicTacToe", p);
		
		assertEquals("X", x.getName());
		assertEquals("TicTacToe", x.getType());
		
		assertNotSame(null, x.getPlayer());		
		assertEquals("Player1", x.getPlayer().getName());
	}	
}
