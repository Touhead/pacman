package Model;

/**
 * Define a Pacman.
 * @author Touhead
 *
 */
public class Pacman extends MovableObject{
	
	private Move pastMove;
	
	public Pacman(Position pos, AbstractMaze maze) {
		super(pos, maze);
	}

	/**
	 * Move the Pacman if it's not in the ghost house.
	 */
	@Override
	public boolean move() {
		
		Position tempPos1 = new Position(getPos());
		
		getMaze().moveTile(tempPos1, getMove());
		
		if ((!getMaze().isForGhost(tempPos1))&&(getMaze().move(getPos(), getMove())))
				return true;
		else if ((!getMaze().isForGhost(tempPos1))&&(getMaze().move(getPos(), pastMove)))
			return true;
		
		return false;
	}
	
	/**
	 * Set the Pacman's move if it's possible to move accordingly to the Move parameter.
	 * Also test if the Pacman won't move in the ghost house.
	 */
	@Override
	public void setMove(Move move){
		
		if (getMove() == null){
			super.setMove(move);
			pastMove = move;
			return;
		}
		
		Position tempPos1 = new Position(getPos());
		
		getMaze().moveTile(tempPos1, move);
		
		Position tempPos = new Position(getPos());			
		if ((!getMaze().isForGhost(tempPos1))&&(getMaze().move(new Position(getPos()), move))){
			pastMove = getMove();
			super.setMove(move);
			return;
		}
	
		if ((!getMaze().isForGhost(tempPos1))&&(getMaze().move(tempPos, getMove()))&&(getMaze().move(tempPos, move))){
			pastMove = getMove();
			super.setMove(move);
			return;
		}
	}
}
