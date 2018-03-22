package kjd.gametheory.tictactoe;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import static org.mockito.Mockito.mock;

import kjd.gametheory.game.GameStatus;

/**
 * Parameterized tests
 * 
 * @author kendavidson
 *
 */
@RunWith(Parameterized.class)
public class TTTGameWinTest {

	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
			{
				TTTGame.BOARD_WINS[0],
				GameStatus.GAME_OVER
			},{
				TTTGame.BOARD_WINS[1],
				GameStatus.GAME_OVER
			},{
				TTTGame.BOARD_WINS[2],
				GameStatus.GAME_OVER
			},{
				TTTGame.BOARD_WINS[3],
				GameStatus.GAME_OVER
			},{
				TTTGame.BOARD_WINS[4],
				GameStatus.GAME_OVER
			},{
				TTTGame.BOARD_WINS[5],
				GameStatus.GAME_OVER
			},{
				TTTGame.BOARD_WINS[6],
				GameStatus.GAME_OVER
			},{
				TTTGame.BOARD_WINS[7],
				GameStatus.GAME_OVER
			}			
		});
	}
	
	TTTGame game;
	TTTBoard board = mock(TTTBoard.class);
	
	private int[] squares;	
	private GameStatus status;
	
	public TTTGameWinTest(int[] squares, GameStatus status) {
		this.squares = squares;
		this.status = status;	
	}
	
	private void configureMockBoard(int[] squares) {
		
	}
	
	@Test
	public void winCondition_returnsWinConditionRow() {
		TTTPlayer p1 = new TTTPlayer(1, "Player1", new TTTMark("X"));
		TTTPlayer p2 = new TTTPlayer(2, "Player2", new TTTMark("O"));
		game = new TTTGame(p1,p2);		
		game.startGame();
		
		configureMockBoard(squares);
		
		final List<TTTSquare> openSquares = game.getOpenPositions();
		Arrays.stream(squares)
			.forEach(i -> {
				TTTSquare s = openSquares.get(i);
				s.setToken(game.getCurrentPlayer().getTokens().get(0));
			});
		Validate.isTrue(GameStatus.GAME_OVER.equals(game.getGameStatus()));
	}
}
