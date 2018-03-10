package kjd.gametheory.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import kjd.gametheory.game.test.TestPlayer;
import kjd.gametheory.game.test.TestToken;

@RunWith(JUnit4.class)
public class PlayerTokenTest {

	@Test
	public void createNewPlayerToken_successful() {	
		TestToken tt = new TestToken("X","Token");	
		
		assertEquals("X", tt.getName());
		assertEquals("Token", tt.getType());
		assertNull(tt.getPlayer());
		
		TestPlayer p = new TestPlayer(1, "Player1");
		tt.setPlayer(p);
		
		assertEquals("X", tt.getName());
		assertEquals("Token", tt.getType());
		assertEquals(p, tt.getPlayer());
	}
	
	@Test 
	public void copyPlayerToken_successfulCopy() {
		TestPlayer p = new TestPlayer(1, "Player1");
		TestToken tt = new TestToken("X","Token",p);
		TestToken copy = new TestToken(tt);
		
		assertEquals(copy.getName(), tt.getName());
		assertEquals(copy.getType(), tt.getType());
		assertNotSame(copy.getPlayer(), tt.getPlayer());
		assertEquals(copy.getPlayer().getId(), tt.getPlayer().getId());
		assertEquals(copy.getPlayer().getName(), tt.getPlayer().getName());	
	}
}
