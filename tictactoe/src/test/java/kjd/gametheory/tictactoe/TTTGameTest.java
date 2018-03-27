package kjd.gametheory.tictactoe;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
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
		TTTPlayer p1 = new TTTPlayer(1, "Player1"); 		
		TTTPlayer p2 = new TTTPlayer(2, "Player2");
		
		List<TTTPlayer> players = new ArrayList<>(2);
		players.add(p1);
		players.add(p2);		
		
		return new TTTGame(board, players);		
	}
	
	@Test
	public void createGameFromPlayers_createsGame() {
		TTTGame g = new TTTGame(new TTTPlayer(1, "Player1"), new TTTPlayer(2, "Player2"));
		
		assertNotNull(g);
		assertSame(9, g.getOpenPositions().size());
	}
	
	@Test
	public void createGameFromPlayersList_createGame() {
		List<TTTPlayer> players = new ArrayList<>();
		players.add(new TTTPlayer(1, "Player1"));
		players.add(new TTTPlayer(2, "Player2"));
		TTTGame g = new TTTGame(players);
		
		assertNotNull(g);
		assertSame(9, g.getOpenPositions().size());
	}
	
	@Test
	public void createGameFromGame_createGame() {
		List<TTTPlayer> players = new ArrayList<>();
		players.add(new TTTPlayer(1, "Player1"));
		players.add(new TTTPlayer(2, "Player2"));
		TTTGame g = new TTTGame(players);
		g.startGame();
		
		TTTGame copy = new TTTGame(g);
		assertNotSame(g, copy);
		assertSame(g.getOpenPositions().size(), copy.getOpenPositions().size());
		assertSame(1, copy.getCurrentPlayer().getId());
	}
	
	@Test
	public void createGameFromGameNextPlayer_createGame() {
		List<TTTPlayer> players = new ArrayList<>();
		players.add(new TTTPlayer(1, "Player1"));
		players.add(new TTTPlayer(2, "Player2"));
		TTTGame g = new TTTGame(players);
		g.startGame();
		
		TTTGame copy = new TTTGame(g);

		assertNotSame(g, copy);
		assertSame(g.getOpenPositions().size(), copy.getOpenPositions().size());
		assertSame(1, copy.getCurrentPlayer().getId());
	}	
	
	@Test
	public void startGame_statusReturnsInProgress() {
		TTTGame g = createGame(new TTTBoard());
		g.startGame();
		assertSame(GameStatus.IN_PROGRESS, g.getGameStatus());
	}
	
	@Test(expected=IllegalStateException.class)
	public void startGameNoPlayers_illegalState() {
		TTTGame g = createGame(new TTTBoard());
		g.getPlayers().clear();
		g.startGame();
	}
	
	@Test(expected=IllegalStateException.class)
	public void startGameNullPlayers_illegalState() {
		TTTGame g = createGame(new TTTBoard());
		g.setPlayers(null);
		g.startGame();
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
	public void performMoveBeforeStart_illegalState() {
		TTTGame g = createGame(new TTTBoard());
		
		TTTPlayer p1 = new TTTPlayer(1, "Player1");
		TTTSquare s1 = new TTTSquare(1,1);
		g.performMove(p1, s1);
		fail("IllegalStateException not thrown");
	}
	
	@Test(expected=IllegalStateException.class)
	public void performMoveAfterCancel_illegalState() {
		TTTGame g = createGame(new TTTBoard());
		g.startGame();		
		TTTPlayer p1 = g.getCurrentPlayer();
		
		g.endGame();
		TTTSquare s1 = new TTTSquare(1,1, p1.getTokens().get(0));
		g.performMove(p1, s1);
		fail("IllegalStateException not thrown");		
	}
	
	@Test(expected=IllegalArgumentException.class) 
	public void performMoveWrongPlayer_illegalArgument() {
		TTTGame g = createGame(new TTTBoard());
		g.startGame();		
		
		TTTPlayer p2 = g.getPlayers().get(1);
		TTTSquare s1 = new TTTSquare(1,1);
		
		g.performMove(p2, s1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void performMoveNullPlayer_illegalArgument() {
		TTTGame g = createGame(new TTTBoard());
		g.startGame();		
		
		TTTSquare s1 = new TTTSquare(1,1);
		
		g.performMove(null, s1);	
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void performMoveTakenSquare_illegalArgument() {
		TTTGame g = createGame(new TTTBoard());
		g.startGame();		
		
		TTTPlayer p1 = g.getPlayers().get(0);
		TTTPlayer p2 = g.getPlayers().get(1);
		TTTSquare s1 = new TTTSquare(1,1);		
		g.performMove(p1, s1);
		g.performMove(p2, s1);
	}

	@Test(expected=IllegalArgumentException.class)
	public void performMoveNullToken_illegalArgument() {
		TTTGame g = createGame(new TTTBoard());
		g.startGame();		
		
		TTTPlayer p1 = g.getPlayers().get(0);
		TTTSquare s1 = new TTTSquare(1,1);		
		g.performMove(p1, s1);
	}
	
	@Test
	public void completeGameWinner_gameOver() {
		List<TTTPlayer> players = new ArrayList<>();
		players.add(new TTTPlayer(1, "Player1"));
		players.add(new TTTPlayer(2, "Player2"));
		TTTGame g = new TTTGame(players);
		g.startGame();
		
		int[] playPositions = new int[] {
				0,1,2,3,4,5,6,7,8
		};
		List<TTTSquare> plays = g.getOpenPositions();
		TTTPlayer cp = null;
		
		for (int i = 0; i < playPositions.length; i++) {
			TTTSquare nextPlay = plays.get(playPositions[i]);
			
			cp = g.getCurrentPlayer();			
			nextPlay.setToken(cp.getTokens().get(0));
			GameStatus status = g.performMove(cp, nextPlay);
			
			if (GameStatus.IN_PROGRESS != status) {
				break;
			}
		}
		
		assertSame(GameStatus.GAME_OVER, g.getGameStatus());
		assertSame(cp.getId(), players.get(0).getId());
	}	
	
	@Test
	public void completeGameNoWinner_draw() {
		List<TTTPlayer> players = new ArrayList<>();
		players.add(new TTTPlayer(1, "Player1"));
		players.add(new TTTPlayer(2, "Player2"));
		TTTGame g = new TTTGame(players);
		g.startGame();
		
		int[] playPositions = new int[] {
				0,2,1,3,5,4,6,8,7
		};
		List<TTTSquare> plays = g.getOpenPositions();
		TTTPlayer cp = null;
		
		for (int i = 0; i < playPositions.length; i++) {
			TTTSquare nextPlay = plays.get(playPositions[i]);
			
			cp = g.getCurrentPlayer();			
			nextPlay.setToken(cp.getTokens().get(0));
			GameStatus status = g.performMove(cp, nextPlay);
			
			if (GameStatus.IN_PROGRESS != status) {
				break;
			}
		}
		
		assertSame(GameStatus.DRAW, g.getGameStatus());
		assertSame(cp.getId(), players.get(0).getId());
	}
}
