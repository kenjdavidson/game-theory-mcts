package kjd.gametheory.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import kjd.gametheory.game.test.TestPosition;

@RunWith(JUnit4.class)
public class BoardPositionTest {

	@Test
	public void createNewPosition_successful() {
		TestPosition tp = new TestPosition(1,1);
		
		assertEquals(1, tp.getX());
		assertEquals(1, tp.getY());	
	}

	@Test
	public void copyPosition_successfulCopy() {
		TestPosition tp = new TestPosition(2,3);
		TestPosition copy = new TestPosition(tp);
		
		assertNotSame(copy, tp);
		assertEquals(copy.getX(), tp.getX());
		assertEquals(copy.getY(), tp.getY());
	}
}
