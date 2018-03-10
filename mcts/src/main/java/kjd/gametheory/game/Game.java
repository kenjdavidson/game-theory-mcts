package kjd.gametheory.game;

import java.util.List;
import java.util.Optional;

import kjd.gametheory.util.ObjectCopier;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Game manager provides specific rules for player transactions, moves, order,
 * and state.
 * 
 * @author kendavidson
 *
 * @param <B> 	Board type this game manages
 * @param <P>	Position type assignable to the Board
 * @param <T>	Token type assignable to the Position
 * @param <S>	Player type assignable to the Token
 */
public abstract class Game<B extends Board<P,T,U>,
							P extends Position<T,U>,
							T extends PlayerToken<U,T>,
							U extends Player<T,U>> {

	/**
	 * @param the Board being played upon
	 * @return the Board being played upon
	 */
	@Getter
	@Setter
	private B board;
	
	/**
	 * @param the List of Players in this game
	 * @return the List of Players in this game
	 */
	@Getter
	@Setter
	private List<U> players;
	
	/**
	 * @param the current Player index
	 * @return the current Player index
	 */
	@Getter(value=AccessLevel.PROTECTED)
	@Setter(value=AccessLevel.PROTECTED)
	private int playerIndex;
	
	/**
	 * @param the previous Player index
	 * @return the previous Player index
	 */
	@Getter(value=AccessLevel.PROTECTED)
	@Setter(value=AccessLevel.PROTECTED)
	private int previousPlayerIndex;
	
	/**
	 * Whether the game is currently started.
	 */
	@Getter
	@Setter(value=AccessLevel.PRIVATE)
	private boolean started;
	
	/**
	 * Current turn.
	 */
	@Getter
	@Setter(value=AccessLevel.PROTECTED)
	private int turn;
	
	/**
	 * Creates a new Game with the provided Board and Players.
	 * 
	 * @param board
	 * @param players
	 */
	public Game(B board, List<U> players) {
		this.board = board;
		this.players = players;
	}
	
	/**
	 * Creates a new Game, which is a copy of the one provided.  When copying
	 * a Game, the players stay the same.
	 * 
	 * @param game
	 */
	public Game(Game<B,P,T,U> game, boolean next) {
		this(ObjectCopier.copyOf(game.getBoard()), game.getPlayers());
		if (next) 
			nextPlayer();
	}
	
	/**
	 * Starts the current game.  The basic implementation initializes the 
	 * the first player.
	 * 
	 * @return
	 */
	public void start() {
		if (isStarted())
			throw new IllegalStateException("Game has already been started");
		previousPlayerIndex = -1;
		playerIndex = 0;
		started = true;
	}
	
	/**
	 * Ends the current game.
	 */
	public void end() {
		if (!isStarted()) 
			throw new IllegalStateException("Game is not currently in progress");
		started = false;
	}
	
	/**
	 * Returns the current player.
	 * 
	 * @return
	 */
	public U getCurrentPlayer() {
		return players.get(getPlayerIndex());
	}
	
	/**
	 * Moves the game along to the next player, and returns it.  This is done
	 * by saving the current Player index to the previous player and then
	 * choosing the next player to return.
	 * 
	 * @return
	 */
	public U nextPlayer() {
		setPreviousPlayerIndex(getPlayerIndex());
		setPlayerIndex(players.size() % getPlayerIndex());
		return players.get(getPlayerIndex());
	}
	
	/**
	 * Returns the previous Player, or null if there was none.
	 * @return
	 */
	public U previousPlayer() {
		if (previousPlayerIndex < 0 || previousPlayerIndex >= players.size())
			return null;
		
		return players.get(getPreviousPlayerIndex());
	}
	
	/**
	 * Returns a winning player if the game is over. If the game is not over, then a null
	 * is returned.
	 * 
	 * @return
	 */
	public abstract boolean isGameOver();
	
	/**
	 * Game should be aware of all it's possible plays at a particular state.  This can be
	 * used to both validate moves, and provide a list of options.  When creating the next possible
	 * moves, it's important that Players are pushed to the next in line.  This ensures that 
	 * validating moves and performing simulations can check that the appropriate user has gone. 
	 * 
	 * @return
	 */
	public abstract List<P> getAllPossibleMoves();
	
	/**
	 * Performs a specified move by the requested player.  Perform move should handle 
	 * all the rules with regards to a move:
	 * <ul>
	 * 	<li>Performs the move on the board</li>
	 * 	<li>Moves the game to the next player, if required</li>
	 * 	<li>Any other steps that a success move entails</li>
	 * </ul>	
	 * <p>
	 * Returns whether the move was successful.  Some games may be able to block a move
	 * due to other Player moves.
	 * 
	 * @return 
	 * @throws IllegalArgumentExcpetion if the player or position provided is not valid
	 */
	public abstract boolean performMove(U currentPlayer, P position) throws IllegalArgumentException;
	
}
