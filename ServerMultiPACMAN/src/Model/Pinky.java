package Model;

/**
 * See Blinky class for further informations.
 * @author Touhead
 *
 */
public class Pinky extends Ghost{

	private Pacman pacman;

	public Pinky(Position pos, Position leakedPos, Pacman pacman, AbstractMaze maze) {
		
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
	 * Return the position 4 tiles ahead of the Pacman position.
	 */
	@Override
	public Position getTarget(){
		
		Position target = new Position(0,0);
		
		if (pacman.getMove().equals(Move.RIGTH)){
			
			if(!target.setX(pacman.getPos().getX()+4))
				target.setX(pacman.getPos().getX());
			target.setY(pacman.getPos().getY());
		}
		else if (pacman.getMove().equals(Move.UP)){
			
			if(!target.setX(pacman.getPos().getX()-4))
				target.setX(pacman.getPos().getX());
			if(!target.setY(pacman.getPos().getY()-4))
				target.setY(pacman.getPos().getY());
		}
		else if (pacman.getMove().equals(Move.LEFT)){
			
			if(!target.setX(pacman.getPos().getX()-4))
				target.setX(pacman.getPos().getX());
			target.setY(pacman.getPos().getY());
		}
		else if (pacman.getMove().equals(Move.DOWN)){
			
			target.setX(pacman.getPos().getX());
			if(!target.setY(pacman.getPos().getY()+4))
				target.setY(pacman.getPos().getY());
		}
				
		return target;
	}
	
	@Override
	public void setUpCorner() {
		
		setCorner(new Position(0,0));
	}
}
