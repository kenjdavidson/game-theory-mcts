package kjd.gametheory.tictactoe;

import kjd.gametheory.game.BoardPosition;

/**
 * Each Tic Tac Toe square can have one mark associated to it.
 * 
 * @author kendavidson
 *
 */
public class BoardSquare extends BoardPosition<PlayerMark, Player> {

	public BoardSquare(int x, int y) {
		super(x, y);
	}
	
	public BoardSquare(int x, int y, PlayerMark token) {
		super(x, y, token);
	}
	
	public BoardSquare(BoardSquare position) {
		super(position);
	}
	
	
}
