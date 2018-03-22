package kjd.gametheory.mcts;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;

import kjd.gametheory.game.GameManager;

@SuppressWarnings("rawtypes")
@RunWith(JUnit4.class)
public class NodeTest {
	
	private Node<GameManager> node;
	
	@Mock
	private GameManager game;
	
	@Test
	public void selectPromisingNodeNoChildren_returnsSelf() {
		node = new Node<>(game);
		
		Node<GameManager> best = node.selectPromisingChild();
		assertEquals(node, best);
	}

	@Test
	public void selectPromisingNode_returnsFirstChild() {
		node = new Node<>(game);
		node.setPlayed(30);
		node.setWon(15);
		
		Node<GameManager> first = new Node<>(node);
		first.setPlayed(10);
		first.setWon(10);
		node.getChildren().add(first);
		
		Node<GameManager> middle = new Node<>(node);
		middle.setPlayed(10);
		middle.setWon(5);
		node.getChildren().add(middle);
		
		Node<GameManager> last = new Node<>(node);
		last.setPlayed(10);
		last.setWon(0);
		node.getChildren().add(last);
		
		Node<GameManager> best = node.selectPromisingChild();
		assertEquals(first, best);
	}

	@Test
	public void selectPromsingNode_returnsLastChild() {
		node = new Node<>(game);
		node.setPlayed(30);
		node.setWon(15);
		
		Node<GameManager> first = new Node<>(node);
		first.setPlayed(10);
		first.setWon(0);
		node.getChildren().add(first);
		
		Node<GameManager> middle = new Node<>(node);
		middle.setPlayed(10);
		middle.setWon(5);
		node.getChildren().add(middle);
		
		Node<GameManager> last = new Node<>(node);
		last.setPlayed(10);
		last.setWon(10);
		node.getChildren().add(last);
		
		Node<GameManager> best = node.selectPromisingChild();
		assertEquals(last, best);		
	}
	
}
