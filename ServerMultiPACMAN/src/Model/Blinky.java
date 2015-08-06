package Model;

/** 
 * Define a specifics Ghost.
 * 
 * @author Touhead
 * @version 1.0 
 * */
public class Blinky extends Ghost{
	
	private Pacman pacman;

	public Blinky(Position pos, Position leakedPos, Pacman pacman, AbstractMaze maze) {
		
		super(pos, leakedPos, maze);		
		this.pacman = pacman;
		setUpCorner();
		setState(GhostState.SCATTER);
	}

	/**
	 * Should be in Ghost, but needed to keep trace of ghosts's state.
	 */
	@Override
	public void setState(GhostState state) {
		
		super.setState(state);
	}
	
	/**
	 * Return target's position specifics to the IA of this ghost.
	 * Here it's the Pacman position.
	 */
	@Override
	public Position getTarget() {
		
		return pacman.getPos();
	}

	/**
	 * Reset the ghost's state to its original value.
	 */
	@Override
	public void resetState() {
		
		setState(GhostState.SCATTER);
	}
	
	/**
	 * Set the corner of this specifics ghost.
	 * Here it's the RIGHT TOP corner.
	 */
	@Override
	public void setUpCorner() {
		
		setCorner(new Position(getMaze().getLength()-1, 0));
	}
}
