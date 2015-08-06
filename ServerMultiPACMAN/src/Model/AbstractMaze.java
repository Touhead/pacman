package Model;

import java.io.IOException;
import java.util.List;

import Exception.ExceptionInvalidFile;

/** 
 * Interface to be able to implement different data structure for the Maze.
 * 
 * @author Touhead
 * @version 1.0
 * */
public interface AbstractMaze {

	public int getWidth();
	
	public int getLength();
	
	public int getNbDot();
	
	public boolean mazeFromFile(String fileName) throws IOException, ExceptionInvalidFile;
	
	public boolean isFree(Position pos);
	
	public boolean isForGhost(Position pos);
	
	public boolean isDot(Position pos);
	
	public boolean removeDot(Position pos);
	
	public boolean isEnergizer(Position pos);
	
	public boolean removeEnergizer(Position pos);
	
	public boolean move(Position pos, Move move);
		
	public boolean moveTile(Position pos, Move move);
	
	public List<Move> AStar(Position start, Position target, Move pastMove, int lookAhead);
	
	public int[][] display();
}
