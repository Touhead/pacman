package Observer;

/**
 * Implement methods for an observable.
 * @author Touhead
 *
 */
public interface Observable {

	public void addObserver(Observer obs);
	public void removeObserver();
	public void notifyObserverMaze(int[][] maze);
	public void notifyObserverEvent(int life, int score, boolean isReset, boolean lose, boolean win);
}
