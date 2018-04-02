package kjd.gametheory.tictactoe.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import kjd.gametheory.game.GameStatus;
import kjd.gametheory.tictactoe.Board;
import kjd.gametheory.tictactoe.BoardSquare;
import kjd.gametheory.tictactoe.Game;
import kjd.gametheory.tictactoe.Player;
import kjd.gametheory.tictactoe.control.TicTacToeBoard;

/**
 * Tic tac toe BorderPane provides player selection and game management.
 * 
 * @author kendavidson
 *
 */
public class TicTacToeGame extends BorderPane {

	@FXML
	private ComboBox<String> cmbPlayer1,
			cmbPlayer2;
	
	@FXML
	private TicTacToeBoard board;
	
	@FXML
	private Label lblStatus;	
	
	@FXML
	private ResourceBundle resources;
	
	private ObjectProperty<Game> game
			= new SimpleObjectProperty<>(this, "game");
	
	private ChangeListener<String> playerListener = (obs, o, n) -> {
		Player p1 = new Player(1, cmbPlayer1.getValue());
		Player p2 = new Player(2, cmbPlayer2.getValue());
		newGame(p1, p2);
	};
	
	private Callback<Integer,GameStatus> movePlayed = (square) -> {
		return performMove(square);
	};

	private ChangeListener<Game> gameListener = (obs, o, n) -> {
		board.resetBoard(n);
	};
	 
	public TicTacToeGame() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(TicTacToeGame.class.getSimpleName() + ".fxml"));
        fxmlLoader.setResources(ResourceBundle.getBundle(TicTacToeGame.class.getName(), Locale.getDefault()));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
	}
	
	@FXML
	private void initialize() {
		board.setSquareClicked(movePlayed);
		game.addListener(gameListener);				
		cmbPlayer1.valueProperty().addListener(playerListener);
		cmbPlayer2.valueProperty().addListener(playerListener);
		
		ArrayList<String> player1Options = new ArrayList<>();
		player1Options.add(resources.getString("PlayerOne"));
		player1Options.add(resources.getString("ComputerOne"));		
		cmbPlayer1.setItems(FXCollections.observableArrayList(player1Options));
		cmbPlayer1.getSelectionModel().select(0);
		
		ArrayList<String> player2Options = new ArrayList<>();
		player2Options.add(resources.getString("PlayerTwo"));
		player2Options.add(resources.getString("ComputerTwo"));
		//player2Options.add(resources.getString("OnlineOpponent"));		
		cmbPlayer2.setItems(FXCollections.observableArrayList(player2Options));
		cmbPlayer2.getSelectionModel().select(0);				
	}
	
	/**
	 * Performs the move requested by the index of the Square played.  Responsible for
	 * determining if the move is valid and updating the board accordingly.  If there
	 * is an issue with the move, then the status is updated.
	 * 
	 * @param played
	 * @return
	 */
	public GameStatus performMove(int played) {
		
		Player current = game.get().getCurrentPlayer();
		BoardSquare toPlay = new BoardSquare(Board.getXPosition(played), 
				Board.getYPosition(played), 
				current.getToken());
		
		GameStatus status = GameStatus.IN_PROGRESS;
		
		try {
			status = game.get().performMove(current, toPlay);	
			board.squaresProperty().set(played, toPlay);
		} catch(IllegalArgumentException e) {
			// Ignored as this is the person trying to select an occupied square
		} catch(IllegalStateException e) {
			// Handle game over or cancelled appropriately
		}		
		
		return status;
	}
	
	/**
	 * Creates a new Tic Tac Toe Game with the provided players.
	 * 
	 * @param players
	 */
	public void newGame(Player... players) {
		Game g = new Game(players);
		g.startGame();		
		game.set(g);
	}

	public final ObjectProperty<Game> gameProperty() {
		return this.game;
	}
	

	public final Game getGame() {
		return this.gameProperty().get();
	}
	

	public final void setGame(final Game game) {
		this.gameProperty().set(game);
	}
		
}
