package kjd.gametheory.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import kjd.gametheory.util.ObjectCopier;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a {@link GameManager} {@code Player}.  In almost all games {@code Player}s
 * control any number of Tokens, which are used to play the {@link GameManager}.
 * 
 * @author kendavidson
 * 
 * @param <T> 	the type of Token in which this player will have associated to them in the context
 * 				of the Game.
 */
public abstract class GamePlayer<T extends PlayerToken<P,T>,
					P extends GamePlayer<T,P>> {

	/**
	 * @param the Player's new Id
	 * @return the Player's Id
	 */
	@Getter
	@Setter
	private int id;
	
	/**
	 * @param the Player's new name
	 * @return the Player's name
	 */
	@Getter
	@Setter
	private String name;
	
	/**
	 * List of available {@link PlayerToken}s in which the Player can use or lay on the 
	 * Board during the context of the game.  The Game is responsible for 
	 * adding or removing tokens from the player.
	 */
	@Getter
	private List<T> tokens;
	
	/**
	 * Creates a new Player, which is a copy of the one supplied.
	 * 
	 * @param player
	 */
	public GamePlayer(GamePlayer<T,P> player) {
		this(player.getId(), player.getName());
		this.tokens = player.getTokens().stream()
				.map(t -> ObjectCopier.copyOf(t))
				.collect(Collectors.toCollection(ArrayList<T>::new));
	}
	
	/**
	 * Creates a Player with a specific Id and name.
	 * 
	 * @param id
	 * @param name
	 */
	public GamePlayer(int id, String name) {
		this(id, name, null);
	}
	
	/**
	 * Creates a Player with an Id, name and starting Token.
	 * @param id
	 * @param name
	 * @param token
	 */
	public GamePlayer(int id, String name, T token) {
		this.setId(id);
		this.setName(name);
		this.tokens = new ArrayList<>();
		
		if (token != null) {
			tokens.add(token);
		}
	}
		
	/**
	 * Adds a token to the Players Token list.  Convenience method to 
	 * {@link #getTokens()} in order to add.
	 *  
	 * @param token
	 * @return
	 */
	public boolean addToken(T token) {
		return tokens.add(token);		
	}
	
	@Override
	public String toString() {
		return new StringBuffer(getName())
				.append(" [").append(getId()).append("]: ")
				.append(getTokens().toString())
				.toString();
	}
}
