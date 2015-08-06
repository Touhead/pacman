package Observer;

/**
 * Implement methods for an observer.
 * @author Touhead
 *
 */
public interface Observer {

	public void updateMaze(int[][] maze);
	public void updateEvent(int life, int score, boolean isReset, boolean lose, boolean win);
}
