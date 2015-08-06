package Model;

import java.util.List;

/**
 * Manage the object's move.
 * @author Touhead
 *
 */
public class MovementManager {

	private EventManager em;
	private Pacman pacman;
	private List<MovableObject> movableObject;

	public MovementManager(EventManager em, Pacman pacman, List<MovableObject> movableObject) {
		super();
		this.em = em;
		this.pacman = pacman;
		this.movableObject = movableObject;
	}
	
	/**
	 * Move all movable object, and then launch events.
	 * @param move
	 * @return true if all the move was done
	 */
	public boolean move(Move move){

		pacman.setMove(move);
		
		for (MovableObject mo : movableObject)
			mo.move();
		
		em.event();
		
		return true;
	}
}
