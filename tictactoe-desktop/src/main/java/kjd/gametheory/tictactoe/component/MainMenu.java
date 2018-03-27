package kjd.gametheory.tictactoe.component;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 * Main menu includes options for single player, two player and
 * online (single player random).
 * 
 * @author kendavidson
 *
 */
public class MainMenu extends VBox {
	
	@FXML
	private Button btnSingle;
	
	@FXML
	private Button btnDouble;
	
	@FXML
	private Button btnOnline;
	
	@FXML
	private ResourceBundle resources;	
	
	public MainMenu() {
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

	@FXML
	private void initialize() {
		
	}
		
	public void setSingleOnAction(EventHandler<ActionEvent> event) {
		btnSingle.setOnAction(event);
	}
	
	public void setDoubleOnAction(EventHandler<ActionEvent> event) {
		btnDouble.setOnAction(event);
	}
	
	public void setOnlineOnAction(EventHandler<ActionEvent> event) {
		btnOnline.setOnAction(event);
	}
	
}
