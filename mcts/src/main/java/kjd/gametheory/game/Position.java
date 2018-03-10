package kjd.gametheory.game;

import kjd.gametheory.util.ObjectCopier;
import lombok.Getter;
import lombok.Setter;

/**
 * Provides details regarding positions on a Game board.  In most cases positions will be
 * denoted by X,Y values so that they can be placed on a physical board.  Although it's 
 * more than possible that a {@link Game} {@link Board} is a different shape (hexagon, 
 * triangle, etc) and the position will need to manage those locations correctly.
 * 
 * @author kendavidson
 *
 */
public abstract class Position<T extends PlayerToken<P,T>,
						P extends Player<T,P>> {

	/**
	 * @param the position specifier along the X axis
	 * @return the position specifier along the Y axis
	 */
	@Getter
	@Setter
	private int x;
	
	/**
	 * @param the position specifier along the Y axis
	 * @return the position specifier along the Y axis
	 */
	@Getter
	@Setter
	private int y;
	
	/**
	 * @param the Token in which to assign to this Position
	 * @return the Token assigned to this position
	 */
	@Getter
	@Setter
	private T token;
	
	/**
	 * Creates a new Position with no associated Token.
	 * 
	 * @param x
	 * @param y
	 */
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Crates a new Position which is a copy of the one provided.  
	 * 
	 * @param position
	 */
	public Position(Position<T,P> position) {
		this(position.getX(), position.getY());				
		this.setToken(ObjectCopier.copyOf(position.getToken()));		
	}
		
}
