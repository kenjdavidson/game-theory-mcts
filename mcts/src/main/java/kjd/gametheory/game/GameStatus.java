package kjd.gametheory.game;

/**
 * There are three game status' by default:
 * <ul>
 * 	<li>IN_PROGRESS - the game is still going on, there are still moves available</li>
 * 	<li>GAME_OVER - the game ended with a winner</li>
 * 	<li>DRAW - the game ended with a no winner</li>
 * </ul>
 * 
 * @author kendavidson
 *
 */
public enum GameStatus {
	IN_PROGRESS,
	GAME_OVER,
	DRAW, 
	CANCELLED,
	CUSTOM_STATUS_1,
	CUSTOM_STATUS_2,
	CUSTOM_STATUS_3
}
