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
public class GameTest {

	public Game createGame(Board board) {		
		Player p1 = new Player(1, "Player1"); 		
		Player p2 = new Player(2, "Player2");
		
		List<Player> players = new ArrayList<>(2);
		players.add(p1);
		players.add(p2);		
		
		return new Game(board, players);		
	}
	
	@Test
	public void createGameFromPlayers_createsGame() {
		Game g = new Game(new Player(1, "Player1"), new Player(2, "Player2"));
		
		assertNotNull(g);
		assertSame(9, g.getOpenPositions().size());
	}
	
	@Test
	public void createGameFromPlayersList_createGame() {
		List<Player> players = new ArrayList<>();
		players.add(new Player(1, "Player1"));
		players.add(new Player(2, "Player2"));
		Game g = new Game(players);
		
		assertNotNull(g);
		assertSame(9, g.getOpenPositions().size());
	}
	
	@Test
	public void createGameFromGame_createGame() {
		List<Player> players = new ArrayList<>();
		players.add(new Player(1, "Player1"));
		players.add(new Player(2, "Player2"));
		Game g = new Game(players);
		g.startGame();
		
		Game copy = new Game(g);
		assertNotSame(g, copy);
		assertSame(g.getOpenPositions().size(), copy.getOpenPositions().size());
		assertSame(1, copy.getCurrentPlayer().getId());
	}
	
	@Test
	public void createGameFromGameNextPlayer_createGame() {
		List<Player> players = new ArrayList<>();
		players.add(new Player(1, "Player1"));
		players.add(new Player(2, "Player2"));
		Game g = new Game(players);
		g.startGame();
		
		Game copy = new Game(g);

		assertNotSame(g, copy);
		assertSame(g.getOpenPositions().size(), copy.getOpenPositions().size());
		assertSame(1, copy.getCurrentPlayer().getId());
	}	
	
	@Test
	public void startGame_statusReturnsInProgress() {
		Game g = createGame(new Board());
		g.startGame();
		assertSame(GameStatus.IN_PROGRESS, g.getGameStatus());
	}
	
	@Test(expected=IllegalStateException.class)
	public void startGameNoPlayers_illegalState() {
		Game g = createGame(new Board());
		g.getPlayers().clear();
		g.startGame();
	}
	
	@Test(expected=IllegalStateException.class)
	public void startGameNullPlayers_illegalState() {
		Game g = createGame(new Board());
		g.setPlayers(null);
		g.startGame();
	}
	
	@Test
	public void cancelGame_statusReturnsCancelled() {
		Game g = createGame(new Board());
		g.startGame();
		g.endGame();
		assertSame(GameStatus.CANCELLED, g.getGameStatus());
	}
	
	@Test(expected=IllegalStateException.class)
	public void currentPlayerBeforeStart_throwsException() {
		Game g = createGame(new Board());
		g.getCurrentPlayer();
		fail("IllegalStateException not thrown");
	}
	
	@Test(expected=IllegalStateException.class)
	public void currentPlayerAfterCancel_illegalState() {
		Game g = createGame(new Board());
		g.startGame();
		g.endGame();
		g.getCurrentPlayer();
		fail("IllegalStateException not thrown");
	}	

	@Test(expected=IllegalStateException.class)
	public void performMoveBeforeStart_illegalState() {
		Game g = createGame(new Board());
		
		Player p1 = new Player(1, "Player1");
		BoardSquare s1 = new BoardSquare(1,1);
		g.performMove(p1, s1);
		fail("IllegalStateException not thrown");
	}
	
	@Test(expected=IllegalStateException.class)
	public void performMoveAfterCancel_illegalState() {
		Game g = createGame(new Board());
		g.startGame();		
		Player p1 = g.getCurrentPlayer();
		
		g.endGame();
		BoardSquare s1 = new BoardSquare(1,1, p1.getTokens().get(0));
		g.performMove(p1, s1);
		fail("IllegalStateException not thrown");		
	}
	
	@Test(expected=IllegalArgumentException.class) 
	public void performMoveWrongPlayer_illegalArgument() {
		Game g = createGame(new Board());
		g.startGame();		
		
		Player p2 = g.getPlayers().get(1);
		BoardSquare s1 = new BoardSquare(1,1);
		
		g.performMove(p2, s1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void performMoveNullPlayer_illegalArgument() {
		Game g = createGame(new Board());
		g.startGame();		
		
		BoardSquare s1 = new BoardSquare(1,1);
		
		g.performMove(null, s1);	
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void performMoveTakenSquare_illegalArgument() {
		Game g = createGame(new Board());
		g.startGame();		
		
		Player p1 = g.getPlayers().get(0);
		Player p2 = g.getPlayers().get(1);
		BoardSquare s1 = new BoardSquare(1,1);		
		g.performMove(p1, s1);
		g.performMove(p2, s1);
	}

	@Test(expected=IllegalArgumentException.class)
	public void performMoveNullToken_illegalArgument() {
		Game g = createGame(new Board());
		g.startGame();		
		
		Player p1 = g.getPlayers().get(0);
		BoardSquare s1 = new BoardSquare(1,1);		
		g.performMove(p1, s1);
	}
	
	@Test
	public void completeGameWinner_gameOver() {
		List<Player> players = new ArrayList<>();
		players.add(new Player(1, "Player1"));
		players.add(new Player(2, "Player2"));
		Game g = new Game(players);
		g.startGame();
		
		int[] playPositions = new int[] {
				0,1,2,3,4,5,6,7,8
		};
		List<BoardSquare> plays = g.getOpenPositions();
		Player cp = null;
		
		for (int i = 0; i < playPositions.length; i++) {
			BoardSquare nextPlay = plays.get(playPositions[i]);
			
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
		List<Player> players = new ArrayList<>();
		players.add(new Player(1, "Player1"));
		players.add(new Player(2, "Player2"));
		Game g = new Game(players);
		g.startGame();
		
		int[] playPositions = new int[] {
				0,2,1,3,5,4,6,8,7
		};
		List<BoardSquare> plays = g.getOpenPositions();
		Player cp = null;
		
		for (int i = 0; i < playPositions.length; i++) {
			BoardSquare nextPlay = plays.get(playPositions[i]);
			
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
