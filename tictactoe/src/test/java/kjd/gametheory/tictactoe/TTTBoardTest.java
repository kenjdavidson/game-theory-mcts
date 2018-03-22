package kjd.gametheory.tictactoe;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TTTBoardTest {
	
	TTTBoard b;
	
	@Before
	public void createBoard() {
		b = new TTTBoard();
	}
	
	public TTTSquare getSquare(int i) {
		return b.getPositions().get(i);
	}
	
	public TTTSquare getSquare(int x, int y) {
		return b.getPosition(new TTTSquare(x, y));
	}

	@Test
	public void newBoard_isEmpty() {		
		assertSame(TTTBoard.SQUARES, b.getPositions().size());
		assertSame(TTTBoard.SQUARES, b.getOpenPositions().size());
		assertSame(0, b.getOccupiedPositions().size());
	}
	
	@Test
	public void newBoard_squaresTestCorrectly() {		
		TTTSquare s = getSquare(1,1);
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
		TTTSquare s = getSquare(1,1);
		s.setToken(new TTTMark("X"));
		assertTrue(b.isOccupied(s));
		assertFalse(b.isOpen(s));
		
		s = getSquare(2,2);
		s.setToken(new TTTMark("Y"));
		assertTrue(b.isOccupied(s));
		assertFalse(b.isOpen(s));
	}
}
