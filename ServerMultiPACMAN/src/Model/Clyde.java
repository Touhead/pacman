package Model;

/**
 * See Blinky class for further informations.
 * 
 * @author Touhead
 * @version 1.0
 * */
public class Clyde extends Ghost{

	private Pacman pacman;

	public Clyde(Position pos, Position leakedPos, Pacman pacman, AbstractMaze maze) {
		
		super(pos, leakedPos, maze);		
		this.pacman = pacman;
		setUpCorner();
		setState(GhostState.IDLE);
	}
	
	@Override
	public void setState(GhostState state) {
		
		super.setState(state);
	}

	/**
	 * Return the Pacman's position if the distance to the Pacman is lower than 8 tiles (64 pixels).
	 * Otherwise the target's position is set to the ghost's corner.
	 */
	@Override
	public Position getTarget() {
				
		if (Position.euclideanDistance(getPos(), pacman.getPos()) > 8)
			return pacman.getPos();
		else
			return getCorner();
	}
	
	@Override
	public void setUpCorner() {
		
		setCorner(new Position(0, getMaze().getWidth()-1));
	}
}
