package kjd.gametheory.game;

import kjd.gametheory.util.ObjectCopier;
import lombok.Getter;
import lombok.Setter;

/**
 * {@link PlayerToken}s are owned by {@link Player}s and can be placed upon a {@link GameBoard}
 * by setting it to a position.  The {@link PlayerToken} provides some standard fields
 * that may be applied to all types of {@link GameManager} Objects.
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
	
	@Override
	public String toString() {
		return new StringBuffer(getName())
				.append(" [").append(getType()).append("]")
				.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((player == null) ? 0 : player.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	/**
	 * Equals based on the Name, Player and Type.  Name and Type are Strings and
	 * can be based on the normal equals, Player can as well, since there should only
	 * be a single reference to Players.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		T other = (T) obj;
		if (name == null) {
			if (other.getName() != null)
				return false;
		} else if (!name.equals(other.getName()))
			return false;
		if (player == null) {
			if (other.getPlayer() != null)
				return false;
		} else if (!player.equals(other.getPlayer()))
			return false;
		if (type == null) {
			if (other.getType() != null)
				return false;
		} else if (!type.equals(other.getType()))
			return false;
		return true;
	}
	
}
