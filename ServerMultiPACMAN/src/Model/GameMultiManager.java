package Model;

import Exception.ExceptionInvalidFile;
import Observer.Observer;

/**
 * Manage the game.
 * @author Touhead
 *
 */
public class GameMultiManager extends AbstractMultiModel{
	
	private ResourceMultiManager rm;
	private DisplayMultiManager dm;
	private MovementMultiManager mm;
	private EventMultiManager em;
	
	/**
	 * Set up the ressource needed for the game.
	 */
	@Override
	public boolean gameSetUp(String fileName) throws ExceptionInvalidFile{
		
		rm = new ResourceMultiManager(fileName);
		em = new EventMultiManager(rm);
		mm = new MovementMultiManager(em, rm.getPacman(), rm.getBlinky(), rm.getPinky());
		dm = new DisplayMultiManager(rm);
		notifyObserverMaze(dm.display());
		notifyObserverEvent(em.getLife(), em.getScore(), em.isReset(), em.isLose(), em.isWin());
		return true;
	}
	
	@Override
	public boolean move(String move, String moveBlinky, String movePinky) {
		
		mm.move(Move.getMoveFromASCII(move), Move.getMoveFromASCII(moveBlinky), Move.getMoveFromASCII(movePinky));
		notifyObserverMaze(dm.display());
		notifyObserverEvent(em.getLife(), em.getScore(), em.isReset(), em.isLose(), em.isWin());
		return true;
	}
	
	/**
	 * Notify the View that the maze has changed.
	 */
	public void notifyObserverMaze(int[][] maze) {

		for(Observer obs : getListObserver()){
			
			obs.updateMaze(maze);
		}
	}

	/**
	 * Notify the View that the life, score has changed and a reset or the end of the game.
	 */
	@Override
	public void notifyObserverEvent(int life, int score, boolean isReset, boolean lose, boolean win) {
		
		for(Observer obs : getListObserver())
			obs.updateEvent(life, score, isReset, lose, win);
	}
}
