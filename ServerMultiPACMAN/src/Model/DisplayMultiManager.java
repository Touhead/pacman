package Model;

public class DisplayMultiManager {

	private ResourceMultiManager rm;
	
	public DisplayMultiManager(ResourceMultiManager rm) {
		super();
		this.rm = rm;
	}
	
	/**
	 * Return the maze to a standard format to be displayed in the View.
	 * Add movable objects to the maze.
	 * @return the maze to a standard format to be displayed in the View
	 */
	public int[][] display(){
		
		int[][] display = rm.getMaze().display();
		
		display[rm.getPacman().getPos().getY()][rm.getPacman().getPos().getX()] = 3;
		
		int nb = 4;
		
		for (Ghost g : rm.getGhost()){
			
			if (g.getState().equals(GhostState.FRIGTHENED)||(g.getState().equals(GhostState.FRIGTHENED_IDLE)))
				display[g.getPos().getY()][g.getPos().getX()] = -1;
			else if (g.getState().equals(GhostState.LEAKED))
				display[g.getPos().getY()][g.getPos().getX()] = -nb;
			else 
				display[g.getPos().getY()][g.getPos().getX()] = nb;
			
			nb++;
		}
		
		return display;
	}
}
