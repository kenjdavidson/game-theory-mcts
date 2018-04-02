package kjd.gametheory.tictactoe;

import kjd.gametheory.game.PlayerToken;

/**
 * Tic Tac Toe mark of either X or O.
 * 
 * @author kendavidson
 *
 */
public class PlayerMark extends PlayerToken<Player, PlayerMark> {

	public PlayerMark(PlayerMark token) {
		super(token);
	}

	public PlayerMark(String name, String type, Player player) {
		super(name, type, player);
	}

	public PlayerMark(String name, String type) {
		super(name, type);
	}

	public PlayerMark(String name) {
		super(name, name);
	}
}
