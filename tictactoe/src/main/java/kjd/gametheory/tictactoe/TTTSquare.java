package kjd.gametheory.tictactoe;

import kjd.gametheory.game.Position;

/**
 * Each Tic Tac Toe square can have one mark associated to it.
 * 
 * @author kendavidson
 *
 */
public class TTTSquare extends Position<TTTMark, TTTPlayer> {

	public TTTSquare(int x, int y) {
		super(x, y);
	}
	
	public TTTSquare(int x, int y, TTTMark token) {
		super(x, y, token);
	}
	
	public TTTSquare(Position<TTTMark, TTTPlayer> position) {
		super(position);
	}
	
}
