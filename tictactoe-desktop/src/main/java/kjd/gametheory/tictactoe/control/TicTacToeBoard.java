package kjd.gametheory.tictactoe.control;

import java.util.List;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Callback;
import kjd.gametheory.game.GameStatus;
import kjd.gametheory.tictactoe.BoardSquare;
import kjd.gametheory.tictactoe.Game;
import kjd.gametheory.tictactoe.control.skin.TicTacToeBoardSkin;

/**
 * Tic tac toe game board.  Board creates a 3x3 grid used to 
 * represent all the available positions on the TTTBoard.
 * 
 * @author kendavidson
 *
 */
public class TicTacToeBoard extends Control {
	
	private final Paint DEFAULT_LINE_COLOR = Color.BLACK;
	private final double DEFAULT_LINE_WIDTH = 8.0;
	private final String CSS_CLASS_NAME = "tic-tac-toe-board";
	
	/**
	 * Allows binding to the BoardSquares
	 */
	private ListProperty<BoardSquare> squares
			= new SimpleListProperty<>(this, "squares");
	
	/**
	 * Allows binding to lineWidth
	 */
	private DoubleProperty lineWidth
			= new SimpleDoubleProperty(this, "lineWidth", DEFAULT_LINE_WIDTH);
	
	/**
	 * Allows binding to lineColor
	 */
	private ObjectProperty<Paint> lineColor
			= new SimpleObjectProperty<>(this, "lineColor", DEFAULT_LINE_COLOR);
	
	/**
	 * Allows binding to XColor
	 */
	private ObjectProperty<Paint> XColor
			= new SimpleObjectProperty<>(this, "XColor", DEFAULT_LINE_COLOR);
	
	/**
	 * Allows binding to OColor
	 */
	private ObjectProperty<Paint> OColor
			= new SimpleObjectProperty<>(this, "OColor", DEFAULT_LINE_COLOR);

	/**
	 * Provides the board with an EventHandler.  It's possible to create a board
	 * with no handler, which would be pointless, but I guess it's possible.
	 */
	private ObjectProperty<Callback<Integer, GameStatus>> squareClicked 
			= new SimpleObjectProperty<>(this, "squareClicked");
	
	/**
	 * Creates a new TicTacToeBoard.
	 */
	public TicTacToeBoard() {		
		initialize();
	}
	
	public TicTacToeBoard(Game game) {
		initialize();
		resetBoard(game);
	}
	
	@FXML
	protected void initialize() {
		getStyleClass().add(CSS_CLASS_NAME);
	}
	
	public void resetBoard(Game game) {
		squares.set(FXCollections.observableArrayList(game.getPositions()));
	}
	
   @Override
    protected Skin<?> createDefaultSkin() {
        return new TicTacToeBoardSkin(this);
    }
      
   	public final ListProperty<BoardSquare> squaresProperty() {
   		return squares;
   	}

    public final ObservableList<BoardSquare> getSquares() {
    		return squares.get();
    }
    
    public final void setSquares(List<BoardSquare> squares) {
    		this.squares.set(FXCollections.observableArrayList(squares));
    }

	public final DoubleProperty lineWidthProperty() {
		return this.lineWidth;
	}
	
	public final double getLineWidth() {
		return this.lineWidthProperty().get();
	}

	public final void setLineWidth(final double dividerWidth) {
		this.lineWidthProperty().set(dividerWidth);
	}

	public final ObjectProperty<Paint> lineColorProperty() {
		return this.lineColor;
	}
	
	public final Paint getLineColor() {
		return this.lineColorProperty().get();
	}
	
	public final void setLineColor(final Paint lineColor) {
		this.lineColorProperty().set(lineColor);
	}

	public final ObjectProperty<Paint> XColorProperty() {
		return this.XColor;
	}

	public final Paint getXColor() {
		return this.XColorProperty().get();
	}
	
	public final void setXColor(final Paint XColor) {
		this.XColorProperty().set(XColor);
	}
	
	public final ObjectProperty<Paint> OColorProperty() {
		return this.OColor;
	}

	public final Paint getOColor() {
		return this.OColorProperty().get();
	}
	
	public final void setOColor(final Paint OColor) {
		this.OColorProperty().set(OColor);
	}

	public final ObjectProperty<Callback<Integer,GameStatus>> squareClickedProperty() {
		return this.squareClicked;
	}
	
	public final Callback<Integer,GameStatus> getSquareClicked() {
		return this.squareClickedProperty().get();
	}
	
	public final void setSquareClicked(final Callback<Integer,GameStatus> selectSquare) {
		this.squareClickedProperty().set(selectSquare);
	}

}
