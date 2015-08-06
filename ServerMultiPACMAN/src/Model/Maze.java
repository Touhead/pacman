package Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import Exception.ExceptionInvalidFile;

/**
 * Define the maze
 * @author Touhead
 *
 */
public class Maze implements AbstractMaze {

	private int length;
	private int width;
	private int[][] maze;
	
	private int nbDot;
	
	/**
	 * Create a maze for a .txt file.
	 */
	public boolean mazeFromFile(String fileName) throws IOException, ExceptionInvalidFile{
		
		try{
			length = 0;
			width = 0;
			nbDot = 0;
			
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			
			length = br.readLine().length();
			width++;
			while (br.readLine() != null)
				width++;
			
			int s;
			int[][] mazeTemp;
			
			fr = new FileReader(fileName);
			br = new BufferedReader(fr);
			mazeTemp = new int[width][length];
			
			int i = 0;
			int ii = 0;
			
			while ((s = br.read()) != -1){
				
				if (s == 13){
					
					i++;
					ii = 0;
				}
				else if (s != 10){
					
					mazeTemp[i][ii] = s;
					ii++;
				}
			}
			
			br.close();
			
			// ENERGIZERS POINTS FORGHOST RIGTH UP LEFT DOWN
			maze = new int[width][length];
			
			for (i = 0; i < maze.length; i++){
				for (ii = 0; ii < maze[i].length; ii++){
					
					maze[i][ii] = 0;
					
					if (mazeTemp[i][ii] == 49)
						maze[i][ii] += 16;
					else if (mazeTemp[i][ii] == 56){
						nbDot++;
						maze[i][ii] += 32;
					}
					else if (mazeTemp[i][ii] == 57)
						maze[i][ii] += 64;
					
					if (mazeTemp[i][(ii+1)%length] == 45)
						maze[i][ii] += 8;
					if (mazeTemp[(i-1+width)%width][ii] == 45)					
						maze[i][ii] += 4;
					if (mazeTemp[i][(ii-1+length)%length] == 45)
						maze[i][ii] += 2;
					if (mazeTemp[(i+1)%width][ii] == 45)
						maze[i][ii] += 1;
				}
			}
			
			Position.setLength(length);
			Position.setWidth(width);
			PixelPosition.setLength(length);
			PixelPosition.setWidth(width);
			
			return true;
		}
		catch (Exception e){
			
			throw new ExceptionInvalidFile();
		}
	}

	/**
	 * Return if a position is free.
	 */
	@Override
	public boolean isFree(Position pos) {

		String binStr = Integer.toBinaryString(maze[(pos.getY()-1+width)%width][pos.getX()]);
		
		if ((binStr.length() < 1)||(binStr.charAt(binStr.length()-1) != '1'))
			return true;
		
		return false;
	}

	/**
	 * Return if a position is in the ghost house.
	 */
	@Override
	public boolean isForGhost(Position pos) {
		
		String binStr = Integer.toBinaryString(maze[pos.getY()][pos.getX()]);
		
		if ((binStr.length() > 4)&&(binStr.charAt(binStr.length()-5) != '0'))
			return true;
		
		return false;
	}
	
	/**
	 * Return if a position contains a dot.
	 */
	@Override
	public boolean isDot(Position pos) {

		String binStr = Integer.toBinaryString(maze[pos.getY()][pos.getX()]);
		
		if ((binStr.length() > 5)&&(binStr.charAt(binStr.length()-6) != '0'))
			return true;
		
		return false;
	}
	
	/**
	 * Remove dot from a position.
	 */
	@Override
	public boolean removeDot(Position pos) {
		
		if (isDot(pos)){
			
			maze[pos.getY()][pos.getX()] -= 32;
			return true;
		}
		
		return false;
	}
	
	
	/**
	 * Return if a position contains a energizer.
	 */
	@Override
	public boolean isEnergizer(Position pos) {

		String binStr = Integer.toBinaryString(maze[pos.getY()][pos.getX()]);
		
		if ((binStr.length() > 6)&&(binStr.charAt(binStr.length()-7) != '0'))
			return true;
		
		return false;
	}

	/**
	 * Remove a energizer from a position.
	 */
	@Override
	public boolean removeEnergizer(Position pos) {

		if (isEnergizer(pos)){
			
			maze[pos.getY()][pos.getX()] -= 64;
			return true;
		}
		
		return false;
	}

	/**
	 * Return a new position accordingly to the Move parameter.
	 * This movement is done in pixel (by 2).
	 */
	@Override
	public boolean move(Position pos, Move move) {

		String binStr = Integer.toBinaryString(maze[pos.getY()][pos.getX()]);
		
		if (!pos.isMovable(move))
			return false;
		
		if (move.equals(Move.RIGTH)){
			if ((pos.getPixelX()%8 == 2)||(binStr.length() < 4)||(binStr.charAt(binStr.length()-4) != '1')){
				
				pos.setPixelX(pos.getPixelX()+2);
				return true;
			}
		}
		
		if (move.equals(Move.UP)){
			if (((pos.getPixelY()+2)%8 < 3)||(binStr.length() < 3)||(binStr.charAt(binStr.length()-3) != '1')){

				pos.setPixelY(pos.getPixelY()-2);
				return true;
			}
		}
		
		if (move.equals(Move.LEFT)){
			if (((pos.getPixelX()+2)%8 < 3)||(binStr.length() < 2)||(binStr.charAt(binStr.length()-2) != '1')){
				
				pos.setPixelX(pos.getPixelX()-2);
				return true;
			}
		}
		
		if (move.equals(Move.DOWN)){ 
			if ((pos.getPixelY()%8 == 2)||(binStr.length() < 1)||(binStr.charAt(binStr.length()-1) != '1')){

				pos.setPixelY(pos.getPixelY()+2);
				return true;
			}
		}
				
		return false;
	}
	
	/**
	 * Return a new position accordingly to the Move parameter.
	 * This movement is done in tile.
	 */
	public boolean moveTile(Position pos, Move move){
		
		String binStr = Integer.toBinaryString(maze[pos.getY()][pos.getX()]);
		
		if (move.equals(Move.RIGTH)){
			if ((binStr.length() < 4)||(binStr.charAt(binStr.length()-4) != '1')){
				
				pos.setX(pos.getX()+1);
				return true;
			}
		}
		
		if (move.equals(Move.UP)){
			if ((binStr.length() < 3)||(binStr.charAt(binStr.length()-3) != '1')){

				pos.setY(pos.getY()-1);
				return true;
			}
		}
		
		if (move.equals(Move.LEFT)){
			if ((binStr.length() < 2)||(binStr.charAt(binStr.length()-2) != '1')){
				
				pos.setX(pos.getX()-1);
				return true;
			}
		}
		
		if (move.equals(Move.DOWN)){
			if ((binStr.length() < 1)||(binStr.charAt(binStr.length()-1) != '1')){
				
				pos.setY(pos.getY()+1);
				return true;
			}
		}
				
		return false;
	}
	
	/**
	 * Implement the A* search algorithm.
	 * Return the path from the start position to the target position.
	 * If pastMove is not null, the the path cannot begin with the inverse move of pastMove.
	 */
	@Override
	public List<Move> AStar(Position start, Position target, Move pastMove, int lookAhead) {
				
		LinkedList<Position> open = new LinkedList<Position>();
		LinkedList<Position> closed = new LinkedList<Position>();
		Hashtable<Position, Move> cameFrom = new Hashtable<Position, Move>();
		
		open.add(new Position(start));
		if (pastMove != null)
			cameFrom.put(start, pastMove);
		
		Hashtable<Position, Cost> gScore = new Hashtable<Position, Cost>();
		Hashtable<Position, Cost> fScore = new Hashtable<Position, Cost>();
		
		gScore.put(start, new Cost(0));
		fScore.put(start, new Cost(gScore.get(start).getCost() + Position.euclideanDistance(start, target)));
		
		int depth = 0;
		double tentativeGScore;
		boolean inOpen;
		boolean inClosed;
		
		Position current = null;
		Position neighbor = null;
		
		while (!open.isEmpty()){
			
			current = open.getFirst();
			for (Position pos: open)
				if (fScore.get(pos).getCost() < fScore.get(current).getCost())
					current = pos;
			
			if (current.equals(target)){
				
				cameFrom.remove(start);
				return reconstructPath(cameFrom,current);
			}
			
			open.remove(current);
			closed.add(current);
			
			if (gScore.get(current).getCost() != depth){
				
				depth++;
				
				if (depth > lookAhead){
						
					cameFrom.remove(start);
					return reconstructPath(cameFrom,current);
				}
			}
			
			pastMove = cameFrom.get(current);

			for (Move move: Move.values()){
				
				if ((pastMove != null)&&(move.equals(pastMove.inverse())))
						continue;
				
				neighbor = new Position(current);
				if ((moveTile(neighbor,move))&&((!isForGhost(neighbor)||(isForGhost(current))||(isForGhost(target))))){
					
					if (closed.contains(neighbor))
						continue;
					
					tentativeGScore = gScore.get(current).getCost() + 1;
					
					inOpen = open.contains(neighbor);
					inClosed = closed.contains(neighbor);
					
					if (((inOpen)||(inClosed))&&(tentativeGScore < gScore.get(neighbor).getCost())){
						
						if (inOpen)
							open.remove(neighbor);
						
						if (inClosed)
							closed.remove(neighbor);
						
						inOpen = inClosed = false;
					}
					if ((!inOpen)&&(!inClosed)){
						
						cameFrom.put(neighbor, move);
						gScore.put(neighbor, new Cost(tentativeGScore));
						fScore.put(neighbor, new Cost(gScore.get(neighbor).getCost() + Position.euclideanDistance(neighbor, target)));
						
						open.add(neighbor);
					}
				}
			}
		}
		
		return reconstructPath(cameFrom,current);
	}
	
	/**
	 * Use for the A* algorithm.
	 * Reconstruct the path from the target.
	 * @param cameFrom
	 * @param current
	 * @return the path to the target
	 */
	public List<Move> reconstructPath(Hashtable<Position, Move> cameFrom, Position current){
		
		LinkedList<Move> totalPath = new LinkedList<Move>();
		
		while (cameFrom.containsKey(current)){
						
			totalPath.push(cameFrom.get(current));
			moveTile(current, cameFrom.get(current).inverse());
		}
		
		return totalPath;
	}

	/**
	 * Return the maze to a standard format to be displayed in the View.
	 */
	@Override
	public int[][] display() {
		
		int[][] displayedMaze = new int[width][length];
		Position pos = new Position(0, 0);
		
		for (int i = 0; i < width; i++){
			for (int ii = 0; ii < length; ii++){
				
				pos.setY(i);
				pos.setX(ii);
				
				if (isFree(pos))
					if (isForGhost(pos))
						displayedMaze[i][ii] = 2;
					else if (isDot(pos))
						displayedMaze[i][ii] = 8;
					else if (isEnergizer(pos))
						displayedMaze[i][ii] = 9;
					else
						displayedMaze[i][ii] = 1;
				else
					displayedMaze[i][ii] = 0;
			}
		}
		
		return displayedMaze;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getLength() {
		return length;
	}
	
	@Override
	public int getNbDot() {
		return nbDot;
	}
	
	/**
	 * Represent the cost of a path for the A* algorithm.
	 * @author Touhead
	 *
	 */
	public class Cost {

		private double cost;

		public Cost(double cost) {
			super();
			this.cost = cost;
		}

		public double getCost() {
			return cost;
		}

		public void setCost(double cost) {
			this.cost = cost;
		}
	}
}
