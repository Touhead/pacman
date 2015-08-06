package Model;

import java.util.List;
import java.util.Random;

/**
 * Define a Ghost.
 * @author Touhead
 *
 */
public abstract class Ghost extends MovableObject {
	
	private static Position exitPos;
	private GhostState state;
	private Position corner;
	private Position leakedPos;
	private Random rand;
	
	public Ghost(Position pos, Position leakedPos, AbstractMaze maze) {
		super(pos, maze);
		rand = new Random(System.currentTimeMillis());
		this.leakedPos = new Position(leakedPos);
	}

	public GhostState getState() {
		return state;
	}

	/**
	 * Change the direction of this ghost if its state changes to FRIGHTHENED.
	 * @param state
	 */
	public void setState(GhostState state) {
		
		if (state.equals(GhostState.FRIGTHENED))
			if (getMove() != null)
				setMove(getMove().inverse());
		
		this.state = state;
	}
	
	/**
	 * Reset this ghost's state.
	 */
	public void resetState(){
			
		setState(GhostState.SCATTER);
	}

	public abstract Position getTarget();

	public Position getCorner() {
		return corner;
	}

	public void setCorner(Position corner) {
		this.corner = corner;
	}
	
	public Position getLeakedPos() {
		return leakedPos;
	}

	public void setLeakedPos(Position leakedPos) {
		this.leakedPos = leakedPos;
	}

	public static Position getExitPos() {
		return exitPos;
	}

	public static void setExitPos(Position exitPos) {
		Ghost.exitPos = exitPos;
	}

	public abstract void setUpCorner();
	
	/**
	 * Move this ghost accordingly with its state.
	 */
	@Override
	public boolean move() {

		List<Move> path = null;
		
		if (getState().equals(GhostState.CHASE))
			path = moveChase();
		else if (getState().equals(GhostState.SCATTER))
			path = moveScatter();
		else if (getState().equals(GhostState.FRIGTHENED))
			path = moveFrigthened();
		else if (getState().equals(GhostState.LEAKED))
			path = moveLeaked();
		else if ((getState().equals(GhostState.IDLE))||(getState().equals(GhostState.FRIGTHENED_IDLE)))
			path = moveIdle();
		
		if ((path == null)||(path.isEmpty()))
			return false;
		
		if (getMaze().move(getPos(), path.get(0))){
			setMove(path.get(0));
			return true;
		}
		else{
			if (getMove() == null)
				return false;
			return getMaze().move(getPos(), getMove());
		}
	}
	
	public boolean move(Move move) {
		
		if (getMove() == null)
			setMove(move);
		
		if (getState().equals(GhostState.IDLE))
			setState(GhostState.CHASE);
		
		if (getState().equals(GhostState.CHASE)){
			
			if ((move == null)||(move.equals(getMove().inverse())))
				return getMaze().move(getPos(), getMove());
			
			if (!getMaze().move(getPos(), move))
				return getMaze().move(getPos(), getMove());
			
			setMove(move);
			return true;
		}
		
		return move();
	}
	
	/**
	 * Return the path in the CHASE mode.
	 * @return
	 */
	public List<Move> moveChase(){
		
		if (getMaze().isForGhost(getPos()))
			return getMaze().AStar(getPos(), getExitPos(), getMove(), 10);
		
		/**
		 * The target in the specifics ghost's target.
		 */
		return getMaze().AStar(getPos(), getTarget(), getMove(), 10);
	}
	
	/**
	 * Return the path in the SCATTER mode.
	 * @return the path in the SCATTER mode
	 */
	public List<Move> moveScatter(){
		
		if (getMaze().isForGhost(getPos()))
			return getMaze().AStar(getPos(), getExitPos(), getMove(), 2);
		
		/**
		 * The target in the ghost's corner.
		 */
		return getMaze().AStar(getPos(), getCorner(), getMove(), 2);
	}
	
	/**
	 * Return the path in the FRIGHTHENED mode.
	 * @return the path in the FRIGHTHENED mode
	 */
	public List<Move> moveFrigthened(){
		
		if (getMaze().isForGhost(getPos()))
			return getMaze().AStar(getPos(), getExitPos(), getMove(), 2);
		

		/**
		 * The target is a random position which's not in the ghost house.
		 */
		Position target = new Position(0,0);
		do{
			target.setX(rand.nextInt((int)Math.pow(2, 10)));
			target.setY(rand.nextInt((int)Math.pow(2, 10)));
		}while (getMaze().isForGhost(target));
		
		return getMaze().AStar(getPos(), target, getMove(), 2);
	}
	
	/**
	 * Return the path in the LEAKED mode.
	 * @return the path in the LEAKED mode
	 */
	public List<Move> moveLeaked(){
		
		/**
		 * The target is the ghost's leaked position.
		 */
		if (getPos().equals(getLeakedPos())){	
			setState(GhostState.IDLE);
			setMove(null);
			return moveIdle();
		}
	
		List<Move> path = getMaze().AStar(getPos(), getLeakedPos(), getMove(), 20);
				
		return path;
	}
	
	/**
	 * Return the path in the IDLE / FRIGTHENED_IDLE mode.
	 * @return the path in the IDLE / FRIGTHENED_IDLE mode
	 */
	public List<Move> moveIdle(){
		
		/**
		 * Reverse the direction each time to only go DOWN, then UP, then DOWN etc...
		 */
		/*if (getMove() == null)
			setMove(Move.DOWN);
		
		if (Position.manhattanDistancePixel(getPos(), getLeakedPos()) > 8)
			setMove(getMove().inverse());
		
		return getMaze().AStar(getPos(), getLeakedPos(), getMove(), 1);*/
		
		return null;
	}
}
