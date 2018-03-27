package kjd.gametheory.tictactoe.component;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

public class GameScreen extends BorderPane {

	@FXML
	private ResourceBundle resources;
	
	public GameScreen() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(MainMenu.class.getSimpleName() + ".fxml"));
        fxmlLoader.setResources(ResourceBundle.getBundle(MainMenu.class.getName(), Locale.getDefault()));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
	}
}
