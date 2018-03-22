package kjd.gametheory.game;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import kjd.gametheory.util.ObjectCopier;
import lombok.Getter;

/**
 * Abstract {@link GameBoard} provides physical characteristics about a particular
 * game board.  A Board consists of a list of Positions, it's up to the 
 * implementation to determine how these Positions are laid out or how 
 * movement is allowed along them.
 * 
 * @author kendavidson
 * 
 * @param <P> Position type contained on the board
 * @param <T> Token type assignable to the Position
 * @param <S> Player type assignable to the Token and Position
 */
public abstract class GameBoard<P extends Position<T,S>,
							T extends PlayerToken<S,T>,
							S extends Player<T,S>> {
	
	/**
	 * @return the list of positions on the Board
	 */
	@Getter
	private List<P> positions;
	
	/**
	 * Creates a new Board.
	 */
	public GameBoard() {
		positions = new ArrayList<>();
	}
	
	/**
	 * Creates a new Board, which is a copy of the board supplied. 
	 * 
	 * @param board
	 */
	public GameBoard(GameBoard<P,T,S> board) {
		positions = board.getPositions().stream()
			.map(p -> ObjectCopier.copyOf(p))
			.collect(Collectors.toList());
	}
	
	/**
	 * Returns all the currently open positions on the {@link GameBoard}.  The positions
	 * should be returned as copies of the original, as they may have operations
	 * performed against them; we don't want the originals tainted.
	 * 
	 * @return
	 */
	public abstract List<P> getOpenPositions();
	
	/**
	 * Determines if the specified position is open.
	 * 
	 * @param position
	 * @return
	 */
	public abstract boolean isOpen(P position) throws IllegalArgumentException;
	
	/**
	 * Returns all the currently occupied positions on the {@link GameBoard}.  Occupied 
	 * positions should return the actual Board positions, since if anything happens
	 * we want to use the currently occupied position.
	 * 
	 * @return
	 */
	public abstract List<P> getOccupiedPositions();
	
	/**
	 * Determines if the specified position is occupied.
	 * 
	 * @param position
	 * @return
	 */
	public abstract boolean isOccupied(P position) throws IllegalArgumentException;
	
	/**
	 * Attempts to get the requested position from the Board.  Get position will
	 * return the actual position; when using it, ensure that you copy
	 * if making changes that will not be saved to the live game.
	 * 
	 * @return
	 */
	public abstract P getPosition(P position) throws IllegalArgumentException;
	
}
