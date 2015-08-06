package Model;

/**
 * Define a Movable Object.
 * @author Touhead
 *
 */
public abstract class MovableObject extends Object {

	private AbstractMaze maze;
	private Position startPos;
	private Move move;

	public MovableObject(Position pos, AbstractMaze maze) {
		super(new Position(pos));
		startPos = new Position(pos);
		this.setMaze(maze);
	}

	public AbstractMaze getMaze() {
		return maze;
	}

	public void setMaze(AbstractMaze maze) {
		this.maze = maze;
	}
	
	/**
	 * Move this object.
	 * @return true if the object was moved, false otherwise
	 */
	public abstract boolean move();

	public Position getStartPos() {
		return startPos;
	}

	public void setStartPos(Position startPos) {
		this.startPos = startPos;
	}
	
	/**
	 * Reset this object's position to its initial position.
	 */
	public void resetPos() {
		
		setPos(new Position(startPos));
		move = null;
	}

	public Move getMove() {
		return move;
	}

	public void setMove(Move move) {
		this.move = move;
	}
}
