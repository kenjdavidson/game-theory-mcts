package kjd.gametheory.mcts;

import java.util.List;

import org.apache.commons.lang3.Validate;

import kjd.gametheory.game.GameManager;
import kjd.gametheory.game.GameStatus;
import kjd.gametheory.game.GamePlayer;
import kjd.gametheory.game.BoardPosition;
import kjd.gametheory.util.ObjectCopier;
import lombok.Getter;

/**
 * Provides an implementation of the Monte Carlo Tree Search:
 * <ul>
 * 	<li>Selection</li>
 * 	<li>Expansion</li>
 * 	<li>Simulation</li>
 * 	<li>Back propagation</li>
 * </ul>
 * 
 * @author kendavidson
 *
 * @param <G> Should implement a Game of a particular type.
 */
@SuppressWarnings(value="rawtypes")
public class MonteCarloSearch<G extends GameManager> {
	
	/**
	 * Default win score, 10.
	 */
	public static final int DEFAULT_WIN_SCORE = 10;
	
	/**
	 * Default player time, 30 seconds.
	 */
	public static final int DEFAULT_PLAYER_TIME = 30000;
	
	/**
	 * @return the value associated to a win
	 */
	@Getter
	private int winScore;
	
	/**
	 * @return the time, in ms, that a Player has to make a choice
	 */
	@Getter
	private int playerTime;
	
	/**
	 * Stores the current Player requesting the next move. 
	 */
	private GamePlayer currentPlayer;
	
	/**
	 * Creates a default MonteCarloSearch.
	 */
	public MonteCarloSearch() {
		this(DEFAULT_WIN_SCORE, DEFAULT_PLAYER_TIME);
	}
	
	/**
	 * Creates a new MonteCarloSearch with a customized win score.
	 * 
	 * @param winScore
	 */
	public MonteCarloSearch(int winScore, int playerTime) {
		this.winScore = winScore;
		this.playerTime = playerTime;
	}
	
	/**
	 * Performs the four steps in Monte Carlo Tree Search until the time period of 
	 * has elapsed.
	 * 
	 * TODO: Look into Threading the requests to increase the number of lookups available 
	 * 
	 * @param currentGame
	 * @return
	 */
	public G findNextMove(G currentGame) {
		Validate.notNull(currentGame);
		
		long start = System.currentTimeMillis(),
				end = start + playerTime;
		
		// Grab the current Player to test against later
		currentPlayer = currentGame.getCurrentPlayer();
		
		// Creates the MCTS Tree by creating a root node with a copy of the 
		// current game state.  The current game state is technically the
		// previous players turn, so we need to set the Game state accordingly
		Node<G> rootNode = new Node<>(ObjectCopier.copyOf(currentGame));
		rootNode.getGame().previousPlayer();		
		
		while (end > System.currentTimeMillis()) {
			
			// Step 1 - Selection along the tree of the best promising node.
			Node<G> promisingNode = rootNode.selectPromisingChild();
			
			// Step 2 - Expansion of the promising node as long as we're still 
			// playing.
			if (promisingNode.getGame().getGameStatus() == GameStatus.IN_PROGRESS) {
				promisingNode.expand();
			}
			
			// Step 3 - Simulation of the games down this node.  If the games down the 
			// node to explore end in a win then a win score of 1 is found, otherwise
			// 0.
			Node<G> nodeToExplore = promisingNode;
			while (promisingNode.getChildren().size() > 0) {
				nodeToExplore = promisingNode.selectRandomNode();
			}
			int winScore = simulatePlayout(nodeToExplore);
			
			// Step 4 - Back population of the Tree nodes.
			backPopulate(nodeToExplore, winScore);
 		}
		
		return currentGame;		
	}
	
	/**
	 * Simulates a playout from the provided Node.   If the game is over and the current player
	 * is not the player which initiated the request, then the play resulted in a loss, otherwise
	 * we keep playing.  Once the game is finally over, return a 0 win score of the current player
	 * was a loser, or a 1 if the current player was the winner.
	 * 
	 * @param toExplore
	 * @return
	 */
	private int simulatePlayout(Node<G> toExplore) {
		Node<G> tempNode = ObjectCopier.copyOf(toExplore);
		
		if (tempNode.getGame().getGameStatus() == GameStatus.IN_PROGRESS 
				&& !currentPlayer.equals(tempNode.getGame().getCurrentPlayer())) {
			tempNode.setWon(Integer.MIN_VALUE);
			return 0;
		} 
		
		while (tempNode.getGame().getGameStatus() != GameStatus.IN_PROGRESS) {			
			simulateRandomPlay(tempNode.getGame());
		}
		
		return (currentPlayer == tempNode.getGame().getCurrentPlayer()) ? 1 : 0;
	}
	
	/**
	 * Performs a random move.  This is done by checking the available moves left on
	 * the board, picking one and performing it.  This is continued until the game
	 * is over.
	 * 
	 * @param game
	 */
	@SuppressWarnings("unchecked")
	private void simulateRandomPlay(G game) {
		List<BoardPosition> open = game.getOpenPositions();
		int next = (int) (Math.random() * (open.size()-1)) + 1;
		game.performMove(game.getCurrentPlayer(), open.get(next));
	}
	
	/**
	 * Back populate the nodes with the win score.  This is done by traversing 
	 * up the Tree and incrementing the play score, while adding the winScore.
	 * 
	 * @param node
	 * @param winScore
	 */
	private void backPopulate(Node node, int winScore) {
		Node tempNode = node;
		while (tempNode != null) {
			tempNode.incrementPlayed();
			
			if (currentPlayer.equals(tempNode.getGame().getCurrentPlayer())) {
				tempNode.incrementWon(winScore);
			}
			
			tempNode = tempNode.getParent();
		}
	}
}
