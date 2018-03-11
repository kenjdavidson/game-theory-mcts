package kjd.gametheory.tictactoe;

import kjd.gametheory.game.Player;

/**
 * Tic Tac Toe player.
 * 
 * @author kendavidson
 *
 */
public class TTTPlayer extends Player<TTTMark, TTTPlayer> {

	public TTTPlayer(int id, String name) {
		super(id, name);
	}

	public TTTPlayer(Player<TTTMark, TTTPlayer> player) {
		super(player);
	}

}
