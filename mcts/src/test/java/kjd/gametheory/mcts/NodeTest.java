package kjd.gametheory.mcts;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;

import kjd.gametheory.game.Game;

@SuppressWarnings("rawtypes")
@RunWith(JUnit4.class)
public class NodeTest {
	
	private Node<Game> node;
	
	@Mock
	private Game game;
	
	@Test
	public void selectPromisingNodeNoChildren_returnsSelf() {
		node = new Node<>(game);
		
		Node<Game> best = node.selectPromisingChild();
		assertEquals(node, best);
	}

	@Test
	public void selectPromisingNode_returnsFirstChild() {
		node = new Node<>(game);
		node.setPlayed(30);
		node.setWon(15);
		
		Node<Game> first = new Node<>(node);
		first.setPlayed(10);
		first.setWon(10);
		node.getChildren().add(first);
		
		Node<Game> middle = new Node<>(node);
		middle.setPlayed(10);
		middle.setWon(5);
		node.getChildren().add(middle);
		
		Node<Game> last = new Node<>(node);
		last.setPlayed(10);
		last.setWon(0);
		node.getChildren().add(last);
		
		Node<Game> best = node.selectPromisingChild();
		assertEquals(first, best);
	}

	@Test
	public void selectPromsingNode_returnsLastChild() {
		node = new Node<>(game);
		node.setPlayed(30);
		node.setWon(15);
		
		Node<Game> first = new Node<>(node);
		first.setPlayed(10);
		first.setWon(0);
		node.getChildren().add(first);
		
		Node<Game> middle = new Node<>(node);
		middle.setPlayed(10);
		middle.setWon(5);
		node.getChildren().add(middle);
		
		Node<Game> last = new Node<>(node);
		last.setPlayed(10);
		last.setWon(10);
		node.getChildren().add(last);
		
		Node<Game> best = node.selectPromisingChild();
		assertEquals(last, best);		
	}
	
}
