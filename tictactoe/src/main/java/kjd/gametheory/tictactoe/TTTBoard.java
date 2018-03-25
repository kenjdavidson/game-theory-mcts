package kjd.gametheory.tictactoe;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.Validate;

import kjd.gametheory.game.GameBoard;

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
public class TTTBoard extends GameBoard<TTTSquare, TTTMark, TTTPlayer> {

	/**
	 * Tic Tac Toe board has 9 total positions
	 */
	public static final int SQUARES = 9;
	
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
		IntStream.range(0,SQUARES)
			.mapToObj(i -> new TTTSquare(getXPosition(i), getYPosition(i)))
			.forEach(p -> getPositions().add(p));
	}
	
	/**
	 * Creates a new TTTBoard, which is a copy of the one provided.
	 * @param board
	 */
	public TTTBoard(TTTBoard board) {
		super(board);
	}
	
	@Override
	public List<TTTSquare> getOpenPositions() {
		return getPositions().stream()
			.filter(p -> p.getToken() == null)
			.map(p -> new TTTSquare(p))
			.collect(Collectors.toList());
	}

	@Override
	public boolean isOpen(TTTSquare position) throws IllegalArgumentException {
		Validate.inclusiveBetween(1, ROWS, position.getX());
		Validate.inclusiveBetween(1, COLS, position.getY());
		
		int index = getPositionIndex(position);				
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
	public boolean isOccupied(TTTSquare position) throws IllegalArgumentException {
		return !isOpen(position);
	}

	@Override
	public TTTSquare getPosition(TTTSquare position) throws IllegalArgumentException {
		int index = getPositionIndex(position);
		Validate.inclusiveBetween(0, SQUARES-1, index);
		return getPositions().get(index);
	}
	
	/**
	 * Performs a lookup of the position index from the provided Position 
	 * locations.  Performs validation of the Index after lookup.
	 * 
	 * @param position
	 * @return
	 */
	private int getPositionIndex(TTTSquare position) throws IllegalArgumentException {
		return getPositionIndex(position.getX(), position.getY());		
	}

	/**
	 * Performs a lookup of the position index from the provided Position 
	 * locations.  Performs validation of the Index after lookup.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	private int getPositionIndex(int x, int y) throws IllegalArgumentException {
		return ((x - 1) * ROWS) + ((y - 1) % COLS); 
	}
	
	/**
	 * Calculates the X Board position given the List index.
	 * 
	 * @param index
	 * @return
	 */
	private int getXPosition(int index) {
		return (index / ROWS) + 1 ;
	}
	
	/**
	 * Calculates the Y Board position given the List index.
	 * 
	 * @param index
	 * @return
	 */
	private int getYPosition(int index) {
		return (index % COLS) + 1;
	}	
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				sb.append("[ ")
					.append(getPositions().get(getPositionIndex(i, j)))
					.append(" ]");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
