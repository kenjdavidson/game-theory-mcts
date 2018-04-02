package kjd.gametheory.tictactoe;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class BoardSquareTest {

	@Test
	public void createSquareByPosition() {
		BoardSquare s = new BoardSquare(1,1);
		
		assertEquals(1, s.getX());
		assertEquals(1, s.getY());
		assertEquals(null, s.getToken());
	}
	
	@Test
	public void createSquareByPositionAndToken() {
		PlayerMark x = new PlayerMark("X");
		BoardSquare s = new BoardSquare(1,1,x);
		
		assertEquals(1, s.getX());
		assertEquals(1, s.getY());
		assertNotSame(null, s.getToken());
		assertEquals("X", s.getToken().getName());
	}
	
	@Test 
	public void createSquareBySquare() {
		PlayerMark x = new PlayerMark("X");
		BoardSquare s1 = new BoardSquare(1,1,x);
		BoardSquare s2 = new BoardSquare(s1);
		
		assertNotSame(s1, s2);
		assertEquals(s1.getX(), s2.getX());
		assertEquals(s2.getY(), s2.getY());
		
		assertNotSame(s1.getToken(), s2.getToken());
		assertEquals(s1.getToken().getName(), s2.getToken().getName());
	}
}
