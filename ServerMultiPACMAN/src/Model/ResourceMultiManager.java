package Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Exception.ExceptionInvalidFile;

public class ResourceMultiManager {

	private AbstractMaze maze;
	private Pacman pacman;
	private Blinky blinky;
	private Pinky pinky;
	
	private List<MovableObject> movableObject;
	private List<Ghost> ghost;
	
	/**
	 * Create all resources needed for the game.
	 * @param fileName
	 * @throws ExceptionInvalidFile
	 */
	public ResourceMultiManager(String fileName) throws ExceptionInvalidFile{
		super();
		
		try {
			
			maze = new Maze();
			maze.mazeFromFile(fileName);
			Position[] iniPos = getIniPos(fileName + "_option");
			pacman = new Pacman(iniPos[0], maze);
			blinky = new Blinky(iniPos[1],iniPos[1], pacman, maze);
			pinky = new Pinky(iniPos[2], iniPos[2], pacman, maze);
			blinky.setState(GhostState.CHASE);
			pinky.setState(GhostState.CHASE);
			
			Ghost.setExitPos(iniPos[1]);
			
			movableObject = new ArrayList<MovableObject>();
			movableObject.add(pacman);
			movableObject.add(blinky);
			movableObject.add(pinky);
			
			ghost = new ArrayList<Ghost>();
			ghost.add(blinky);
			ghost.add(pinky);
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

	public AbstractMaze getMaze() {
		return maze;
	}

	public void setMaze(AbstractMaze maze) {
		this.maze = maze;
	}

	public Pacman getPacman() {
		return pacman;
	}

	public void setPacman(Pacman pacman) {
		this.pacman = pacman;
	}

	public List<Ghost> getGhost(){
		
		return ghost;
	}
	
	public Blinky getBlinky() {
		return blinky;
	}

	public Pinky getPinky() {
		return pinky;
	}

	public List<MovableObject> getMovableObject() {
		return movableObject;
	}

	public void setMovableObject(List<MovableObject> movableObject) {
		this.movableObject = movableObject;
	}
	
	/**
	 * Get the movable objets's position for the .txt file.
	 * @param fileName
	 * @return the position of the pacman and ghost
	 * @throws IOException
	 */
	public Position[] getIniPos(String fileName) throws IOException{
		
		FileReader fr = new FileReader(fileName);
		BufferedReader br = new BufferedReader(fr);
		int s;
		
		Position[] movableObjectPos = new Position[3];
		
		int i = 0;
		int ii = 0;
		
		while ((s = br.read()) != -1){
			
			if (s == 13){
				
				i++;
				ii = 0;
			}
			else if (s != 10){
				
				if (s == 51)
					movableObjectPos[0] = new Position(ii, i);
				else if (s == 52)
					movableObjectPos[1] = new Position(ii, i);
				else if (s == 53){
					movableObjectPos[2] = new Position(ii, i);
				}
				
				ii++;
			}
		}
		
		br.close();
		
		return movableObjectPos;
	}
}
