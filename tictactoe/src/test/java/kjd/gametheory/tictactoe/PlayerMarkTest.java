package kjd.gametheory.tictactoe;

import static org.junit.Assert.*;

import org.junit.Test;

public class PlayerMarkTest {

	@Test
	public void createMarkFromName() {
		PlayerMark x = new PlayerMark("X");
		
		assertEquals("X", x.getName());
		assertEquals("X", x.getType());
		assertEquals(null, x.getPlayer());
	}

	@Test
	public void createMarkFromNameAndType() {
		PlayerMark x = new PlayerMark("X", "TicTacToe");
		
		assertEquals("X", x.getName());
		assertEquals("TicTacToe", x.getType());
		assertEquals(null, x.getPlayer());
	}
	
	@Test
	public void createMarkFromNameTypeAndPlayer() {
		Player p = new Player(1, "Player1");
		PlayerMark x = new PlayerMark("X", "TicTacToe", p);
		
		assertEquals("X", x.getName());
		assertEquals("TicTacToe", x.getType());
		
		assertNotSame(null, x.getPlayer());		
		assertEquals("Player1", x.getPlayer().getName());
	}	
}
