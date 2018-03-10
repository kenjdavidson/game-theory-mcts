package kjd.gametheory.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import kjd.gametheory.util.ObjectCopier;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a {@link Game} {@code Player}.  In almost all games {@code Player}s
 * control any number of Tokens, which are used to play the {@link Game}.
 * 
 * @author kendavidson
 * 
 * @param <T> 	the type of Token in which this player will have associated to them in the context
 * 				of the Game.
 */
public abstract class Player<T extends PlayerToken<P,T>,
					P extends Player<T,P>> {

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
	private Collection<T> tokens;
	
	/**
	 * Creates a new Player, which is a copy of the one supplied.
	 * 
	 * @param player
	 */
	public Player(Player<T,P> player) {
		this(player.getId(), player.getName());
		this.tokens = player.getTokens().stream()
				.map(t -> ObjectCopier.copyOf(t))
				.collect(Collectors.toCollection(ArrayList<T>::new));
	}
	
	/**
	 * Creates a Player with a specific Id and Name.
	 * 
	 * @param id
	 * @param name
	 */
	public Player(int id, String name) {
		this.setId(id);
		this.setName(name);
		this.tokens = new ArrayList<>();
	}
			
}
