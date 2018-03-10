package kjd.gametheory.game;

import kjd.gametheory.util.ObjectCopier;
import lombok.Getter;
import lombok.Setter;

/**
 * {@link PlayerToken}s are owned by {@link Player}s and can be placed upon a {@link Board}
 * by setting it to a position.  The {@link PlayerToken} provides some standard fields
 * that may be applied to all types of {@link Game} Objects.
 * <p>
 * 
 * @author kendavidson
 *
 */
public abstract class PlayerToken<P extends Player<T,P>,
					T extends PlayerToken<P,T>> {

	/**
	 * @return the Tokens name
	 */
	@Getter
	private String name;
	
	/**
	 * @return a String describing the type of Token
	 */
	@Getter
	private String type;
	
	/**
	 * @param the Payer to which this token belongs
	 */
	@Getter
	@Setter
	private P player;
	
	/**
	 * Creates a new PlayerToken with name and type.
	 * 
	 * @param name
	 */
	public PlayerToken(String name, String type) {
		this(name, type, null);
	}
	
	/**
	 * Creates a new Token.
	 * 
	 * @param name
	 * @param type
	 */
	public PlayerToken(String name, String type, P player) {
		this.name = name;
		this.type = type;
		this.player = player;
	}
	
	/**
	 * Copy constructor.
	 * 
	 * @param token
	 */
	public PlayerToken(PlayerToken<P,T> token) {
		this.name = token.getName();
		this.type = token.getType();
		this.player = ObjectCopier.copyOf(token.getPlayer());
	}

}
