package Model;

import Exception.ExceptionInvalidFile;
import Observer.Observer;

/**
 * Manage the game.
 * @author Touhead
 *
 */
public class GameManager extends AbstractModel{

	private ResourceManager rm;
	private DisplayManager dm;
	private MovementManager mm;
	private EventManager em;
	
	/**
	 * Set up the ressource needed for the game.
	 */
	@Override
	public boolean gameSetUp(String fileName) throws ExceptionInvalidFile{
		
		rm = new ResourceManager(fileName);
		em = new EventManager(rm);
		mm = new MovementManager(em, rm.getPacman(), rm.getMovableObject());
		dm = new DisplayManager(rm);
		notifyObserverMaze(dm.display());
		notifyObserverEvent(em.getLife(), em.getScore(), em.isReset(), em.isLose(), em.isWin());
		return true;
	}

	/**
	 * Move Pacman and all other ghost accordingly.
	 */
	public boolean movePacman(String move){
		
		mm.move(Move.getMoveFromASCII(move));
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
