package kjd.gametheory.tictactoe.control.skin;

import java.util.stream.IntStream;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.SkinBase;
import javafx.scene.shape.Line;
import kjd.gametheory.tictactoe.Board;
import kjd.gametheory.tictactoe.BoardSquare;
import kjd.gametheory.tictactoe.control.TicTacToeBoard;

/**
 * Handles drawing of the Board Control.
 * 
 * @author kendavidson
 *
 */
public class TicTacToeBoardSkin extends SkinBase<TicTacToeBoard> {
	
	private static final double MINIMUM_SIZE = 150;
	private static final double PREFERRED_SIZE = 300;
	private static final double MARGIN = 8;
	private static final double MARGINS = MARGIN * 2;

	private Line[] rowDividers;
	private Line[] colDividers;
	private Canvas[] squares;
	
	/**
	 * Listener handling when the Squares list is replaced completely.  This should mean
	 * that the Board needs a complete redrawing due to restart or a replay. 
	 */
	private ChangeListener<ObservableList<BoardSquare>> boardListener = (obs,o,n) -> {
		initializeLines(getSkinnable());
		initializeSquares(getSkinnable());
	};
	
	/**
	 * Listener handling when a board square is replaced.  This happens when a play
	 * is completed or undone.
	 */
	private ListChangeListener<BoardSquare> squareListener = (c) -> {
		System.out.print(c.toString());
	};
	
	public TicTacToeBoardSkin(TicTacToeBoard control) {
		super(control);
		initialize(control);
		initializeLines(control);
		initializeSquares(control);
	}
	
	/**
	 * Initializes the board by setting default sizes (ensuring square) and adding
	 * the appropriate Control listeners.
	 * 
	 * @param board
	 */
	protected void initialize(TicTacToeBoard board) {

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
		
		board.squaresProperty().addListener(boardListener);
		board.squaresProperty().addListener(squareListener);
	}
	
	/**
	 * Initializes the board by setting values for row/column line
	 * dividers. 
	 * 
	 * @param board
	 */
	protected void initializeLines(TicTacToeBoard board) {
		
		rowDividers = new Line[2];
		colDividers = new Line[2];
		IntStream.range(0, 2)
			.forEach(i -> {			
				rowDividers[i] = new Line();
				rowDividers[i].strokeWidthProperty().bind(board.lineWidthProperty());
				rowDividers[i].fillProperty().bind(board.lineColorProperty());
				colDividers[i] = new Line();
				colDividers[i].strokeWidthProperty().bind(board.lineWidthProperty());
				colDividers[i].fillProperty().bind(board.lineColorProperty());
			});		
				
		getChildren().addAll(rowDividers);
		getChildren().addAll(colDividers);			
	}	
	
	/**
	 * Initializes the squares.  Directs the square to notify the Board if it was clicked; as
	 * long as it doesn't already have a token associated to it.
	 * 
	 * @param board
	 */
	private void initializeSquares(TicTacToeBoard board) {
		
		squares = new Canvas[9];
		IntStream.range(0, 9)
			.forEach(i -> {
				squares[i] = new Canvas(board.getPrefWidth() * 0.333, board.getPrefHeight() * 0.333);	
				squares[i].setOnMouseClicked(e -> {
					if (board.getSquares().get(i) != null
							&& board.getSquares().get(i).getToken() == null
							&& board.getSquareClicked() != null) {
						board.getSquareClicked().call(i);
					}
				});
			});
		
		getChildren().addAll(squares);
	}

	@Override
	protected void layoutChildren(final double contentX, final double contentY,
            final double contentWidth, final double contentHeight) {
		
		double lineOffset = getSkinnable().getLineWidth() / 2;
		double centerX = (contentX + contentWidth) / 2,
				centerY = (contentY + contentHeight) / 2;
		double box = Double.min(contentWidth, contentHeight) - MARGINS;
		double half = box / 2;
		
		if (squares != null) {
			IntStream.range(0, 9)
			.forEach(i -> {
				double x = Board.getXPosition(i);
				double y = Board.getYPosition(i);
				double startX = (centerX - half) + (((y-1) * 0.333) * box);
				double startY = (centerY - half) + (((x-1) * 0.333) * box);
								
				squares[i].resizeRelocate(startX, startY, 
						box * 0.333, box * 0.333);								
			});			
		}
		
		IntStream.range(0, 2)
			.forEach(i -> {	
				double thirds = box * ((i + 1) * 0.333);
				
				rowDividers[i].setStartX(0.0);
				rowDividers[i].setStartY(0.0);
				rowDividers[i].setEndX(box);
				rowDividers[i].setEndY(0.0);	
				layoutInArea(rowDividers[i], 						
						(centerX - half), (centerY - half + thirds - lineOffset), 
						box, rowDividers[i].getStrokeWidth(), 
						0.0, HPos.CENTER, VPos.CENTER);
				
				colDividers[i].setStartX(0.0);
				colDividers[i].setStartY(0.0);
				colDividers[i].setEndX(0.0);
				colDividers[i].setEndY(box);		
				layoutInArea(colDividers[i], 
						(centerX - half + thirds - lineOffset), (centerY - half - lineOffset), 
						colDividers[i].getStrokeWidth(), box, 
						0.0, HPos.CENTER, VPos.CENTER);
			});					
	}	
	
}
