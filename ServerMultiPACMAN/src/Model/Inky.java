package Model;

/**
 * See Blinky class for further informations.
 * @author Touhead
 *
 */
public class Inky extends Ghost{

	private Pacman pacman;
	private Blinky blinky;
	
	public Inky(Position pos, Position leakedPos, Pacman pacman, Blinky blinky, AbstractMaze maze) {
		
		super(pos, leakedPos, maze);		
		this.pacman = pacman;
		this.blinky = blinky;
		setUpCorner();
		setState(GhostState.IDLE);
	}

	@Override
	public void setState(GhostState state) {
		
		super.setState(state);
	}
	
	/**
	 * Return the double of the vector between the Blinky's position and 2 tiles ahead the Pacman's position.
	 */
	@Override
	public Position getTarget(){
		
		Position target = new Position(0,0);
		Position offset = new Position(0,0);
				
		if (pacman.getMove().equals(Move.RIGTH)){
			
			if(offset.setX(pacman.getPos().getX()+2))
				offset.setX(pacman.getPos().getX());
			offset.setY(pacman.getPos().getY());
		}
		else if (pacman.getMove().equals(Move.UP)){
			
			if(offset.setX(pacman.getPos().getX()-2))
				offset.setX(pacman.getPos().getX());
			if(offset.setY(pacman.getPos().getY()-2))
				offset.setY(pacman.getPos().getY());
		}
		else if (pacman.getMove().equals(Move.LEFT)){
			
			if(offset.setX(pacman.getPos().getX()+2))
				offset.setX(pacman.getPos().getX());
			offset.setY(pacman.getPos().getY());
		}
		else if (pacman.getMove().equals(Move.DOWN)){
			
			offset.setX(pacman.getPos().getX());
			if(offset.setY(pacman.getPos().getY()+2))
				offset.setY(pacman.getPos().getY());
		}
		
		if(!target.setX(blinky.getPos().getX() + 2*(offset.getX()-blinky.getPos().getX())))
			target.setX(blinky.getPos().getX() + offset.getX()-blinky.getPos().getX());
		if(!target.setY(blinky.getPos().getY() + 2*(offset.getY()-blinky.getPos().getY())))
			target.setY(blinky.getPos().getY() + offset.getY()-blinky.getPos().getY());

		
		return target;
	}
	
	@Override
	public void setUpCorner() {
		
		setCorner(new Position(getMaze().getLength()-1, getMaze().getWidth()-1));
	}
}
