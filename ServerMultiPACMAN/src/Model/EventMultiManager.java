package Model;

public class EventMultiManager {

	private final int baseTime = 30;
	private ResourceMultiManager rm;
	private int dot;
	private int life;
	private int score;
	private int energizedTime;
	private GhostState mode;
	private boolean isFrigthned;
	private boolean isReset;
		
	public EventMultiManager(ResourceMultiManager rm) {
		super();
		this.rm = rm;
		dot = 0;
		life = 2;
		score = 0;
		energizedTime = 0;
		mode = GhostState.CHASE;
		isFrigthned = false;
		isReset = false;
	}

	/**
	 * Launch all events.
	 */
	public void event(){
		
		eventTime();
		eventDot();
		eventEnergizer();
		eventLife();
		
		return;
	}
	
	/**
	 * Manage dot's events.
	 * @return
	 */
	public boolean eventDot(){
		
		/**
		 * If Pacman eats a dot.
		 */
		if (rm.getMaze().removeDot(rm.getPacman().getPos())){
			
			dot++;
			score += 10;
		}
		
		return true;
	}
	
	/**
	 * Manage energizer events.
	 * @return
	 */
	public boolean eventEnergizer(){
		
		/**
		 * If Pacman eats a energizer.
		 */
		if (rm.getMaze().removeEnergizer(rm.getPacman().getPos())){
						
			for (Ghost g : rm.getGhost())
				if (!g.getState().equals(GhostState.LEAKED))
					g.setState(GhostState.FRIGTHENED);
			
			isFrigthned = true;
			energizedTime = 1;
		}
		
		/**
		 * If Pacman is in the energized state.
		 */
		if (energizedTime != 0){
			/**
			 * The energizer lost effect after 10sec.
			 */
			if (energizedTime > 10*baseTime){
				
				for (Ghost g : rm.getGhost())
					if (g.getState().equals(GhostState.FRIGTHENED))
						g.setState(mode);
					else if (g.getState().equals(GhostState.FRIGTHENED_IDLE))
						g.setState(GhostState.CHASE);
				
				isFrigthned = false;
				energizedTime = 0;
			}
			else{
				
				energizedTime++;
			}
		}
		
		return true;
	}
		
	
	/**
	 * Manage life events.
	 */
	public void eventLife(){
			
		isReset = false;
		
		/**
		 * If a ghost is on the same tile than Pacman.
		 */
		for (Ghost g : rm.getGhost())
			if (g.getPos().equals(rm.getPacman().getPos()))
				/**
				 * If the ghost was FRIGTHENED then the ghost is eaten.
				 */
				if (g.getState().equals(GhostState.FRIGTHENED)){
					
					g.setState(GhostState.LEAKED);
					
					score += 200;
				}
				else if(!g.getState().equals(GhostState.LEAKED)){
					life--;
					resetGame();
				}
						
		return;
	}
	
	/**
	 * Manage mode/time events.
	 */
	public void eventTime(){
			
		if (isFrigthned)
			return;
								
		return;
	}
	
	public boolean isWin(){
		
		if (dot == rm.getMaze().getNbDot())
			return true;
		
		return false;
	}
	
	public boolean isLose(){
		
		if (life == 0)
			return true;
		
		return false;
	}
	
	/**
	 * Reset game after lose a life.
	 * @return true if the game is reset
	 */
	public boolean resetGame(){
		
		for(MovableObject mo : rm.getMovableObject())
			mo.resetPos();
		
		for(Ghost g : rm.getGhost())
			g.setState(GhostState.CHASE);
		
		mode = GhostState.CHASE;
				
		isReset = true;
		
		return true;
	}

	public int getdot() {
		return dot;
	}

	public void setdot(int dot) {
		this.dot = dot;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getEnergizedTime() {
		return energizedTime;
	}

	public void setEnergizedTime(int energizedTime) {
		this.energizedTime = energizedTime;
	}

	public boolean isReset() {
		return isReset;
	}
}
