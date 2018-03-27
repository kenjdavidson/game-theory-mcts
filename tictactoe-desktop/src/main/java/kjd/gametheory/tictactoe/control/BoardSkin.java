package kjd.gametheory.tictactoe.control;

import java.util.stream.IntStream;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.SkinBase;
import javafx.scene.shape.Line;

/**
 * Handles drawing of the Board Control.
 * 
 * @author kendavidson
 *
 */
public class BoardSkin extends SkinBase<Board> {
	
	private static final double MINIMUM_SIZE = 50;
	private static final double PREFERRED_SIZE = 300;

	private Line[] rowDividers;
	private Line[] colDividers;
	private Canvas[] squares;
	
	protected BoardSkin(Board control) {
		super(control);
		initialize();
	}
	
	/**
	 * Initializes the board by setting values for row/column line
	 * dividers and board squares.  This method only creates and
	 * adds the Nodes.  Drawing is completed within the two methods
	 * {@link #layoutChildren(double, double, double, double)}.
	 */
	protected void initialize() {
		Board board = getSkinnable();
		
		if (Double.compare(board.getPrefWidth(), 0.0) <= 0.0
				|| Double.compare(board.getPrefHeight(), 0.0) <= 0.0
				|| Double.compare(board.getWidth(), 0.0) <= 0.0
				|| Double.compare(board.getHeight(), 0.0) <= 0.0) {
			board.setPrefSize(PREFERRED_SIZE, PREFERRED_SIZE);
		}
		
		if (Double.compare(board.getMinWidth(), 0.0) <= 0.0
				|| Double.compare(board.getMinHeight(), 0.0) <= 0.0) {
			board.setMinSize(MINIMUM_SIZE, MINIMUM_SIZE);
		}
		
		rowDividers = new Line[2];
		colDividers = new Line[2];
		IntStream.range(0, 2)
			.forEach(i -> {			
				rowDividers[i] = new Line();
				rowDividers[i].strokeWidthProperty().bind(board.dividerWidthProperty());
				colDividers[i] = new Line();
				colDividers[i].strokeWidthProperty().bind(board.dividerWidthProperty());
			});
		
		squares = new Canvas[9];
		IntStream.range(0, 9)
			.forEach(i -> {
				squares[i] = new Canvas();
			});
		
		getChildren().addAll(rowDividers);
		getChildren().addAll(colDividers);
		getChildren().addAll(squares);
	}

	/**
	 * Lays the line dividers and squares out in the appropriate
	 * positions.
	 * 
	 * @param contentX
	 * @param contentY
	 * @param contentWidth
	 * @param contentHeight
	 */
	@Override
	protected void layoutChildren(final double contentX, final double contentY,
            final double contentWidth, final double contentHeight) {
		setLine(rowDividers[0], 0, 0, contentWidth, 0, 0, 0.333);
		setLine(rowDividers[1], 0, 0, contentWidth, 0, 0, 0.666);
		setLine(colDividers[0], 0, 0, contentWidth, 0, 0.333, 0);
		setLine(colDividers[0], 0, 0, contentWidth, 0, 0.666, 0);
	}	
	
	/**
	 * Updates a Line to the start and end points provided.
	 * 
	 * @param line
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	private void setLine(Line line, 
			double x1, double y1, double x2, double y2,
			double translateX, double translateY) {
		line.setStartX(x1);
		line.setStartY(y1);
		line.setEndX(x2);
		line.setEndY(y2);
	}
}
