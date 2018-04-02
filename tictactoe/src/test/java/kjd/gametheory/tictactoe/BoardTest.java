package kjd.gametheory.tictactoe;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class BoardTest {
	
	Board b;
	
	@Before
	public void createBoard() {
		b = new Board();
	}
	
	public BoardSquare getSquare(int i) {
		return b.getPositions().get(i);
	}
	
	public BoardSquare getSquare(int x, int y) {
		return b.getPosition(new BoardSquare(x, y));
	}

	@Test
	public void newBoard_isEmpty() {		
		assertSame(Board.SQUARES, b.getPositions().size());
		assertSame(Board.SQUARES, b.getOpenPositions().size());
		assertSame(0, b.getOccupiedPositions().size());
	}
	
	@Test
	public void newBoard_squaresTestCorrectly() {		
		BoardSquare s = getSquare(1,1);
		assertTrue(b.isOpen(s));
		assertFalse(b.isOccupied(s));
		
		s = getSquare(2,2);
		assertTrue(b.isOpen(s));
		assertFalse(b.isOccupied(s));
	}
	
	@Test
	public void newBoard_isBlank() {
		assertEquals(9, b.getOpenPositions().size());
		assertEquals(0, b.getOccupiedPositions().size());
	}
	
	@Test
	public void playSingleMove_updatesBoardCorrectly() {
		BoardSquare s = getSquare(1,1);
		s.setToken(new PlayerMark("X"));
		assertTrue(b.isOccupied(s));
		assertFalse(b.isOpen(s));
		
		s = getSquare(2,2);
		s.setToken(new PlayerMark("Y"));
		assertTrue(b.isOccupied(s));
		assertFalse(b.isOpen(s));
	}
}
