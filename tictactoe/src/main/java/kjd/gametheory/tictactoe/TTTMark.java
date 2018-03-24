package kjd.gametheory.tictactoe;

import kjd.gametheory.game.PlayerToken;

/**
 * Tic Tac Toe mark of either X or O.
 * 
 * @author kendavidson
 *
 */
public class TTTMark extends PlayerToken<TTTPlayer, TTTMark> {

	public TTTMark(TTTMark token) {
		super(token);
	}

	public TTTMark(String name, String type, TTTPlayer player) {
		super(name, type, player);
	}

	public TTTMark(String name, String type) {
		super(name, type);
	}

	public TTTMark(String name) {
		super(name, name);
	}
}
