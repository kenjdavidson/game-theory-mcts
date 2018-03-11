package kjd.gametheory.tictactoe;

import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.lang3.Validate;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import kjd.gametheory.game.Board;

/**
 * Tic tac toe board contains nine squares laid out in a 3 x 3 block.  Rules of the 
 * board are:
 * <ul>
 * 	<li>A board square can only have one token/user associated</li>
 * 	<li>A board square can not be replaced</li>
 * </ul>
 * Positions are created List where the mapping is:
 * <pre>
 * 0 = (1,1)
 * 1 = (1,2)
 * 2 = (1,3)
 * 3 = (2,1)
 * ...
 * 8 = (3,3)
 * </pre>
 * <p>
 * 
 * @author kendavidson
 *
 */
public class TTTBoard extends Board<TTTSquare, TTTMark, TTTPlayer> {

	/**
	 * Tic Tac Toe board has 9 total positions
	 */
	public static final int NUM_POSITIONS = 9;
	
	/**
	 * There are 3 rows
	 */
	public static final int ROWS = 3;
	
	/**
	 * There are 3 columns
	 */
	public static final int COLS = 3;
	
	/**
	 * Creates a new TTTBoard.
	 */
	public TTTBoard() {
		super();
		
		// X = ceil(i / 3) (1, 1, 1, ...)
		// Y = i mod 3 (1, 2, 3, ...)
		IntStream.range(0,NUM_POSITIONS)
			.mapToObj(i -> new TTTSquare((int) Math.ceil((i+1) / ROWS), (i % COLS) + 1))
			.forEach(p -> getPositions().add(p));
	}
	
	@Override
	public List<TTTSquare> getOpenPositions() {
		return getPositions().stream()
			.filter(p -> p.getToken() == null)
			.collect(Collectors.toList());
	}

	@Override
	public boolean isOpen(TTTSquare position) {
		Validate.inclusiveBetween(1, ROWS, position.getX());
		Validate.inclusiveBetween(1, COLS, position.getY());
		
		int index = (COLS * (position.getX()-1) % ROWS) + (position.getY() - 1);		
		TTTSquare found = getPositions().get(index);
		return found.getToken() == null;
	}

	@Override
	public List<TTTSquare> getOccupiedPositions() {
		return getPositions().stream()
				.filter(p -> p.getToken() != null)
				.collect(Collectors.toList());
	}

	@Override
	public boolean isOccupied(TTTSquare position) {
		return !isOpen(position);
	}

}
