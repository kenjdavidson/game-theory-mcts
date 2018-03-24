package kjd.gametheory.tictactoe;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import kjd.gametheory.game.GameStatus;

@RunWith(JUnit4.class)
public class TTTGameTest {

	public TTTGame createGame(TTTBoard board) {		
		TTTPlayer p1 = new TTTPlayer(1, "Player1", new TTTMark("X")); 		
		TTTPlayer p2 = new TTTPlayer(2, "Player2", new TTTMark("O"));
		
		List<TTTPlayer> players = new ArrayList<>(2);
		players.add(p1);
		players.add(p2);		
		
		return new TTTGame(board, players);		
	}
	
	@Test
	public void startGame_statusReturnsInProgress() {
		TTTGame g = createGame(new TTTBoard());
		g.startGame();
		assertSame(GameStatus.IN_PROGRESS, g.getGameStatus());
	}
	
	@Test
	public void cancelGame_statusReturnsCancelled() {
		TTTGame g = createGame(new TTTBoard());
		g.startGame();
		g.endGame();
		assertSame(GameStatus.CANCELLED, g.getGameStatus());
	}
	
	@Test(expected=IllegalStateException.class)
	public void currentPlayerBeforeStart_throwsException() {
		TTTGame g = createGame(new TTTBoard());
		g.getCurrentPlayer();
		fail("IllegalStateException not thrown");
	}
	
	@Test(expected=IllegalStateException.class)
	public void currentPlayerAfterCancel_illegalState() {
		TTTGame g = createGame(new TTTBoard());
		g.startGame();
		g.endGame();
		g.getCurrentPlayer();
		fail("IllegalStateException not thrown");
	}	
	
	@Test(expected=IllegalStateException.class)
	public void performMoveAfterCancel_illegalState() {
		TTTGame g = createGame(new TTTBoard());
		g.startGame();
		g.endGame();
		
		TTTPlayer p1 = g.getCurrentPlayer();
		TTTSquare s1 = new TTTSquare(1,1, p1.getTokens().get(0));
		g.performMove(p1, s1);
		fail("IllegalStateException not thrown");		
	}
	
	@Test(expected=IllegalStateException.class)
	public void performMoveBeforeStart_illegalState() {
		TTTGame g = createGame(new TTTBoard());
		
		TTTPlayer p1 = g.getCurrentPlayer();
		TTTSquare s1 = new TTTSquare(1,1, p1.getTokens().get(0));
		g.performMove(p1, s1);
		fail("IllegalStateException not thrown");
	}
}
