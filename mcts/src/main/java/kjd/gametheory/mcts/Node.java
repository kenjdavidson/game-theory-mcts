package kjd.gametheory.mcts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import kjd.gametheory.game.Board;
import kjd.gametheory.game.Game;
import kjd.gametheory.util.ObjectCopier;
import lombok.Getter;
import lombok.Setter;

/**
 * Implements a {@link Node} on the Monte Carlo Tree.  {@link Node}s contain 
 * links to their parent {@link Node}, a list of their child {@link Node} and
 * information regarding the Game {@link Board} and {@link Playout}. 
 * 
 * @author kendavidson
 *
 */
@SuppressWarnings("rawtypes")
public class Node<G extends Game> {

	/**
	 * @param the number of times this Node has been played
	 * @return the number of times this Node has been played
	 */
	@Getter
	@Setter
	private int played;
	
	/**
	 * @param the number of times this Node has won
	 * @return the number of times this Node has won
	 */
	@Getter
	@Setter
	private int won;
	
	/**
	 * @param the current {@link Node}s parent
	 * @return the current {@link Node}s parent
	 */
	@Getter
	@Setter
	private Node<G> parent;
	
	/**
	 * @param the Game (state) of the current Node
	 * @return the Game (state) of the current Node
	 */
	@Getter
	@Setter
	private G game;
	
	/**
	 * @param the {@link Node}s children
	 * @return the {@link Node}s children 
	 */
	@Getter
	@Setter
	private ArrayList<Node<G>> children = new ArrayList<>();
	
	/**
	 * Creates a new Node with a provided Game and no parent.
	 * 
	 * @param game
	 */
	public Node(G game) {
		this(null, null);
	}
	
	/**
	 * Creates a new Node with a provided parent.
	 * 
	 * @param parent
	 * @param game
	 */
	public Node(Node<G> parent, G game) {
		this.parent = parent;
		this.game = game;		
	}
	
	/**
	 * Creates a new Node by copying a current one.
	 * 
	 * @param toCopy
	 */
	public Node(Node<G> toCopy) {
		this(toCopy.getParent(), ObjectCopier.copyOf(toCopy.getGame()));
	}

	/**
	 * Determines the most promising node.
	 * 
	 * @return
	 */
	public Node<G> selectPromisingChild() {		
		Node<G> promising = this;
		while(promising.getChildren().size() > 0) {
			promising = compareUsingUTC(promising);
		}
		return promising;		
	}
	
	/**
	 * Compares all the Nodes children using the UTC value.
	 * 
	 * @param node
	 * @return
	 */
	private Node<G> compareUsingUTC(Node<G> node) {
		int parentPlayed = node.getPlayed();
		return Collections.max(node.getChildren(),
				Comparator.comparing(n -> utcValue(parentPlayed, n.getWon(), n.getPlayed())));
	}
	
	/**
	 * Calculate the UTC value of the specified Node and it's parent.
	 * 
	 * @param totalVisits
	 * @param node
	 * @return
	 */
	private static double utcValue(double totalVisits, double won, double played) {
        if (totalVisits == 0) {
            return Double.MAX_VALUE;
        }
        
        return (won / played) + 1.41 * Math.sqrt(Math.log(totalVisits) / played);
    }
	
	/**
	 * Implements the Expansion step within the Monte Carlo search.  Expansion is performed
	 * when a node cannot be made by the Selection process.  This happens when we finally find
	 * a leaf node, and can then begin simulation.
	 * 
	 * @param node
	 */
	@SuppressWarnings("unchecked")
	public void expand() {
		getGame().getAllPossibleMoves()
			.forEach(game -> {
				Node<G> n = new Node<G>(this, (G) game);
				getChildren().add(n);
			});
	}

	/**
	 * Chooses a random node from the curren Nodes children.
	 * 
	 * @return
	 */
	public Node<G> selectRandomNode() {
		Random r = new Random();
		int index = r.nextInt(getChildren().size()-1);
		return getChildren().get(index);
	}

	/**
	 * Increments the played number of this Node
	 */
	public void incrementPlayed() {
		played++;
	}	

	/**
	 * Increments the won score by the score provided.
	 * 
	 * @param score
	 */
	public void incrementWon(int score) {
		won += score;
	}
}
