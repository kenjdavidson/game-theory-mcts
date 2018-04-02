package kjd.gametheory.tictactoe;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import kjd.gametheory.tictactoe.component.TicTacToeGame;

/**
 * Play Tic Tac Toe
 * 
 * @author kendavidson
 *
 */
public class TicTacToe extends Application {
	
	/**
	 * Primary layout
	 */
	private BorderPane layout;			

	/**
	 * Primary menu bar
	 */
	private MenuBar menuBar;
	
	/**
	 * Primary status text
	 */
	private Label status;
	
	/**
	 * Game screen
	 */
	private TicTacToeGame gameScreen;
	
	/**
	 * Resource bundle
	 */
	private ResourceBundle resources;
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		resources = ResourceBundle.getBundle(this.getClass().getName(), Locale.getDefault());
		
		initializeStage(primaryStage);
		primaryStage.sizeToScene();
		primaryStage.show();
	}
	
	protected void initializeStage(Stage stage) {
		layout = new BorderPane();
		stage.setScene(new Scene(layout, 600, 400));		
		stage.setOnCloseRequest(i -> Platform.exit());
		
		initializeMenubar(layout);	
		initializeStatus(layout);
		initializeGameScreen(layout);
	}
	
	protected void initializeMenubar(BorderPane layout) {
		menuBar = new MenuBar();
		menuBar.setUseSystemMenuBar(true);
		
		layout.setTop(menuBar);
	}
	
	protected void initializeStatus(BorderPane layout) {
		status = new Label();
		
		layout.setBottom(status);
	}

	protected void initializeGameScreen(BorderPane layout) {
		gameScreen = new TicTacToeGame();
		
		layout.setCenter(gameScreen);
	}
}
