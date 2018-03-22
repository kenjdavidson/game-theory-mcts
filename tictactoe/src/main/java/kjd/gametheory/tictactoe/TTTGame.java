package kjd.gametheory.tictactoe;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.Validate;

import kjd.gametheory.game.GameManager;
import kjd.gametheory.game.GameStatus;

/**
 * Tic Tac Toe game manager. 
 * 
 * @author kendavidson
 *
 */
public class TTTGame extends GameManager<TTTGame, TTTBoard, TTTSquare, TTTMark, TTTPlayer> {
	
	public static final int[][] BOARD_WINS = {
			{ 0, 1, 2 },		// Row 1
			{ 3, 4, 5 },		// Row 2
			{ 6, 7, 8 },		// Row 3
			{ 0, 3, 6 },		// Column 1
			{ 1, 4, 7 },		// Column 2
			{ 2, 5, 8 },		// Column 3
			{ 1, 4, 8 },		// Diagonal 1
			{ 6, 5, 2 }		// Diagonal 2
	};
	
	public TTTGame(TTTPlayer... players) {
		super(new TTTBoard(), Arrays.stream(players).collect(Collectors.toList()));
	}
	
	public TTTGame(List<TTTPlayer> players) {
		this(new TTTBoard(), players);
	}

	public TTTGame(TTTGame game, boolean next) {
		super(game, next);
	}

	public TTTGame(TTTBoard board, List<TTTPlayer> players) {
		super(board, players);
	}

	@Override
	public GameStatus getGameStatus() {
		return determineStatus();
	}

	@Override
	public List<TTTGame> getAllPossibleMoves() {
		List<TTTSquare> open = getOpenPositions();
		
		return open.stream()
				.map(position -> {
					TTTGame play = new TTTGame(this, true);	// Move to next player	
					position.getToken().setPlayer(play.getCurrentPlayer());
					play.performMove(position.getToken().getPlayer(), position);
					return play;
				})
				.collect(Collectors.toList());
	}		

	/**
	 * Performs a Tic Tac Toe move.  When attempting to perform moves, the current player must provided must
	 * be the current player and the square must not be already in play.  clients should pre-check this 
	 * state, but it will also be validated here.
	 * <p>
	 * Once the move is confirmed, the game moves to the next player.
	 * 
	 * @param player
	 * @param position
	 * @throws IllegalArgumentException
	 * @throws IllegalStateException
	 */
	@Override
	public GameStatus performMove(TTTPlayer player, TTTSquare position) 
			throws IllegalArgumentException, IllegalStateException {
		TTTSquare found = getBoard().getPosition(position);
		
		Validate.validState(isStarted() && !isCancelled());
		Validate.isTrue(player != null && player.getId() == getCurrentPlayer().getId());
		Validate.isTrue(found.getToken() != null);
		found.setToken(position.getToken());
		found.getToken().setPlayer(player);
		GameStatus status = determineStatus();
		
		if (GameStatus.IN_PROGRESS == status) {
			nextPlayer();	
		}
		
		return status;
	}

	/**
	 * Determines the game status based on the positions.  Tic Tac Toe games can be one of a few available 
	 * statuses: Done with no winner (DRAW), Done with a winner (GAME_OVER) or still in progress (IN_PROGRESS).
	 * 
	 * @return
	 */
	private GameStatus determineStatus() {
		final List<TTTSquare> marks = getBoard().getPositions();
		Optional<int[]> winner = checkForWin(marks);
		
		if (winner.isPresent()) {
			return GameStatus.GAME_OVER;
		} else if (isCancelled()) {
			return GameStatus.CANCELLED;
		} else if (getBoard().getOpenPositions().isEmpty()) {
			return GameStatus.DRAW;
		} 
		
		return GameStatus.IN_PROGRESS;
	}
	
	/**
	 * Determines if the {@link TTTBoard} contains the same mark in a specified win
	 * configuration.  Returns an Optional<int[]> which is the specific row
	 * which caused a win to occur.
	 * 
	 * @param positions
	 * @param winIndices
	 * @return
	 */
	private Optional<int[]> checkForWin(List<TTTSquare> positions) {
		return Arrays.stream(BOARD_WINS)
			.filter(win -> {
				TTTSquare s1 = positions.get(win[0]);
				TTTSquare s2 = positions.get(win[1]);
				TTTSquare s3 = positions.get(win[2]);
				
				return (s1.getToken() != null 
						&& s2.getToken() != null 
						&& s2.getToken() != null
						&& s1.getToken().equals(s2.getToken())
						&& s1.getToken().equals(s3.getToken()));
			})
			.findFirst();		
	}
}
