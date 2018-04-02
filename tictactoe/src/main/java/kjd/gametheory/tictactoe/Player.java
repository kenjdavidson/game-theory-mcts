package kjd.gametheory.tictactoe;

import kjd.gametheory.game.GamePlayer;

/**
 * Tic Tac Toe player.
 * 
 * @author kendavidson
 *
 */
public class Player extends GamePlayer<PlayerMark, Player> {

	public Player(int id, String name, PlayerMark token) {
		super(id, name, token);
	}

	public Player(int id, String name) {
		super(id, name);
	}

	public Player(GamePlayer<PlayerMark, Player> player) {
		super(player);
	}

	@Override
	public boolean addToken(PlayerMark token) {
		token.setPlayer(this);
		return super.addToken(token);
	}
		
	/**
	 * Tic Tac Toe players only have one Token, this helper gets them
	 * @return
	 */
	public PlayerMark getToken() {
		return getTokens().get(0);
	}
}
