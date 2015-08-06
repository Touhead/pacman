package Model;

public class MovementMultiManager {

	private EventMultiManager em;
	private Pacman pacman;
	private Ghost blinky;
	private Ghost pinky;

	public MovementMultiManager(EventMultiManager em, Pacman pacman, Ghost blinky, Ghost pinky) {
		super();
		this.em = em;
		this.pacman = pacman;
		this.blinky = blinky;
		this.pinky = pinky;
	}
	
	/**
	 * Move all movable object, and then launch events.
	 * @param move
	 * @return true if all the move was done
	 */
	public boolean move(Move move, Move moveBlinky, Move movePinky){

		pacman.setMove(move);
		pacman.move();
		blinky.move(moveBlinky);
		pinky.move(movePinky);
		
		em.event();
		
		return true;
	}
}
