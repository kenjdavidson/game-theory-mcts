package kjd.gametheory.tictactoe.control;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import kjd.gametheory.tictactoe.TTTBoard;

/**
 * Tic tac toe game board.  Board creates a 3x3 grid used to 
 * represent all the available positions on the TTTBoard.
 * 
 * @author kendavidson
 *
 */
public class Board extends Control {
	
	private final Paint DEFAULT_BOARD_COLOR = Color.WHITE;
	private final Paint DEFAULT_LINE_COLOR = Color.BLACK;
	
	private ObjectProperty<TTTBoard> board
			= new SimpleObjectProperty<>(this, "board");
	
	private DoubleProperty dividerWidth
			= new SimpleDoubleProperty(this, "dividerWidth");

	public Board(TTTBoard board) {
		this.board.set(board);
		initialize();
	}
	
	@FXML
	protected void initialize() {

	}
	
   @Override
    protected Skin<?> createDefaultSkin() {
        return new BoardSkin(this);
    }

	public final ObjectProperty<TTTBoard> boardProperty() {
		return this.board;
	}
	
	public final TTTBoard getBoard() {
		return this.boardProperty().get();
	}
	
	public final void setBoard(final TTTBoard board) {
		this.boardProperty().set(board);
	}

	public final DoubleProperty dividerWidthProperty() {
		return this.dividerWidth;
	}
	
	public final double getDividerWidth() {
		return this.dividerWidthProperty().get();
	}

	public final void setDividerWidth(final double dividerWidth) {
		this.dividerWidthProperty().set(dividerWidth);
	}
	
}
