package kjd.gametheory.game;

import java.util.List;

import org.apache.commons.lang3.Validate;

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
public abstract class GameManager<G extends GameManager<G,B,P,T,U>,
							B extends GameBoard<P,T,U>,
							P extends Position<T,U>,
							T extends PlayerToken<U,T>,
							U extends Player<T,U>> {
	
	/**
	 * @param the Board being played upon
	 * @return the Board being played upon
	 */
	@Getter(value=AccessLevel.PROTECTED)
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
	
	@Getter
	@Setter(value=AccessLevel.PRIVATE)
	private boolean cancelled;
	
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
	public GameManager(B board, List<U> players) {
		this.board = board;
		this.players = players;
	}
	
	/**
	 * Creates a new Game, which is a copy of the one provided.  When copying
	 * a Game, the players stay the same.
	 * 
	 * @param game
	 */
	public GameManager(GameManager<G,B,P,T,U> game, boolean next) {
		this(ObjectCopier.copyOf(game.getBoard()), game.getPlayers());
		if (next) {
			nextPlayer();
		}
	}
	
	/**
	 * Starts the current game.  The basic implementation initializes the 
	 * the first player.
	 * 
	 * @return
	 * @throws IllegalStateException
	 */
	public void startGame() throws IllegalStateException{		
		Validate.validState(!isStarted());
		
		previousPlayerIndex = -1;
		playerIndex = 0;
		started = true;
		cancelled = false;
	}
	
	/**
	 * Ends the current game, setting the status to cancelled.
	 */
	public void endGame() throws IllegalStateException {
		Validate.validState(isStarted());
		
		started = false;
		cancelled = true;
		nextPlayer();
	}
	
	/**
	 * Returns the current player.  If there is no current player
	 * 
	 * @return
	 */
	public U getCurrentPlayer() throws IllegalStateException {	
		Validate.validState(isStarted() && !isCancelled());
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
		setPlayerIndex(players.size() / (getPlayerIndex() + 1) - 1);
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
	 * Provides access to the open positions on the board.
	 * 
	 * @return
	 */
	public List<P> getOpenPositions() {
		return getBoard().getOpenPositions();
	}
	
	/**
	 * Returns the GameStatus
	 * 
	 * @return
	 */
	public abstract GameStatus getGameStatus();
	
	/**
	 * Game should be aware of all it's possible plays at a particular state.  This can be
	 * used to both validate moves, and provide a list of options.  When creating the next possible
	 * moves, it's important that Players are pushed to the next in line.  This ensures that 
	 * validating moves and performing simulations can check that the appropriate user has gone. 
	 * 
	 * @return
	 */
	public abstract List<G> getAllPossibleMoves();
	
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
	 * @param currentPlayer
	 * @param position
	 * @return 
	 * @throws IllegalArgumentExcpetion if the player or position provided is not valid
	 */
	public abstract GameStatus performMove(U currentPlayer, P position) 
			throws IllegalArgumentException, IllegalStateException;
	
}
