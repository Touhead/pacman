package Model;

/**
 * Manage events.
 * 
 * @author Touhead
 * @version 1.0
 */
public class EventManager {

	private final int baseTime = 30;
	private ResourceManager rm;
	private int dot;
	private int life;
	private int score;
	private int energizedTime;
	private int stateTimer;
	private GhostState mode;
	private int scatterTimer;
	private int chaseTimer;
	private int scatterNbSwitch;
	private int chaseNbSwitch;
	private int pinkyDotCounter;
	private int pinkyDotLimit;
	private boolean pinkyDotLimitEnable;
	private int inkyDotCounter;
	private int inkyDotLimit;
	private boolean inkyDotLimitEnable;
	private int clydeDotCounter;
	private int clydeDotLimit;
	private boolean clydeDotLimitEnable;
	private int timeWithoutDot;
	private int globalDotLimit;
	private boolean isFrigthned;
	private boolean isReset;
		
	public EventManager(ResourceManager rm) {
		super();
		this.rm = rm;
		dot = 0;
		life = 2;
		score = 0;
		energizedTime = 0;
		stateTimer = 0;
		mode = GhostState.SCATTER;
		scatterTimer = 7;
		chaseTimer = 20;
		scatterNbSwitch = 0;
		chaseNbSwitch = 0;
		pinkyDotCounter = 0;
		pinkyDotLimit = 0;
		pinkyDotLimitEnable = true;
		inkyDotCounter = 0;
		inkyDotLimit = 30;
		inkyDotLimitEnable = false;
		clydeDotCounter = 0;
		clydeDotLimit = 60;
		clydeDotLimitEnable = false;
		timeWithoutDot = 0;
		globalDotLimit = -1;
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
	 */
	public void eventDot(){
		
		/**
		 * If the dot counter is enable for Pinky, then look if its dot counter reach its dot limit.
		 * If true, then change Pinky state to the actual mode or to the FRIGHTENED state.
		 * Then activate the next counter in this priority : Pinky / Inky / Clyde
		 */
		if (pinkyDotLimitEnable){
			
			if (pinkyDotCounter == pinkyDotLimit){
				
				if (rm.getPinky().getState().equals(GhostState.IDLE))
					rm.getPinky().setState(mode);
				else if (rm.getPinky().getState().equals(GhostState.FRIGTHENED_IDLE))
					rm.getPinky().setState(GhostState.FRIGTHENED);
				
				pinkyDotLimitEnable = false;
				
				if (rm.getInky().getState().equals(GhostState.IDLE))
					inkyDotLimitEnable = true;
				else if (rm.getClyde().getState().equals(GhostState.IDLE))
					clydeDotLimitEnable = true;
				
				pinkyDotCounter = 0;
			}
		}
		else if (inkyDotLimitEnable){
							
			if (inkyDotCounter == inkyDotLimit){
				
				if (rm.getInky().getState().equals(GhostState.IDLE))
					rm.getInky().setState(mode);
				else if (rm.getInky().getState().equals(GhostState.FRIGTHENED_IDLE))
					rm.getInky().setState(GhostState.FRIGTHENED);
				
				inkyDotLimitEnable = false;
				
				if (rm.getPinky().getState().equals(GhostState.IDLE))
					pinkyDotLimitEnable = true;
				else if (rm.getClyde().getState().equals(GhostState.IDLE))
					clydeDotLimitEnable = true;
				
				inkyDotCounter = 0;
			}
		}
		else if (clydeDotLimitEnable){
							
			if (clydeDotCounter == clydeDotLimit){
				
				if (rm.getClyde().getState().equals(GhostState.IDLE))
					rm.getClyde().setState(mode);
				else if (rm.getClyde().getState().equals(GhostState.FRIGTHENED_IDLE))
					rm.getClyde().setState(GhostState.FRIGTHENED);
				
				clydeDotLimitEnable = false;
				
				if (rm.getPinky().getState().equals(GhostState.IDLE))
					pinkyDotLimitEnable = true;
				else if (rm.getInky().getState().equals(GhostState.IDLE))
					inkyDotLimitEnable = true;
				
				clydeDotCounter = 0;
			}
		}
		/**
		 * If the global dot limit is enable (Pacman has lost a life).
		 */
		else if (globalDotLimit != -1){
					
			if (globalDotLimit == 7)
				if (rm.getPinky().getState().equals(GhostState.IDLE))
					rm.getPinky().setState(mode);
				else if (rm.getPinky().getState().equals(GhostState.FRIGTHENED_IDLE))
					rm.getPinky().setState(GhostState.FRIGTHENED);
			else if (globalDotLimit == 17)
				if (rm.getInky().getState().equals(GhostState.IDLE))
					rm.getInky().setState(mode);
				else if (rm.getInky().getState().equals(GhostState.FRIGTHENED_IDLE))
					rm.getInky().setState(GhostState.FRIGTHENED);
			else if (globalDotLimit == 32)
				if (rm.getClyde().getState().equals(GhostState.IDLE))
					rm.getClyde().setState(mode);
				else if (rm.getClyde().getState().equals(GhostState.FRIGTHENED_IDLE))
					rm.getClyde().setState(GhostState.FRIGTHENED);
		}
		
		/**
		 * Blinky leave immediately the ghost house.
		 */
		if (rm.getBlinky().getState().equals(GhostState.IDLE))
			rm.getBlinky().setState(mode);
		else if (rm.getBlinky().getState().equals(GhostState.FRIGTHENED_IDLE))
			rm.getBlinky().setState(GhostState.FRIGTHENED);
		
		
		/**
		 * If Pacman eats a dot.
		 */
		if (rm.getMaze().removeDot(rm.getPacman().getPos())){
			
			dot++;
			score += 10;
			
			if (globalDotLimit != -1)
				globalDotLimit++;
			
			/**
			 * If a ghost counter is enable, then increment it.
			 */
			if ((pinkyDotLimitEnable)&&(!rm.getPinky().getState().equals(GhostState.LEAKED)))
				pinkyDotCounter++;
			else if ((inkyDotLimitEnable)&&(!rm.getInky().getState().equals(GhostState.LEAKED)))
				inkyDotCounter++;
			else if ((clydeDotLimitEnable)&&(!rm.getClyde().getState().equals(GhostState.LEAKED)))
				clydeDotCounter++;
			
			timeWithoutDot = 0;
		}
		/**
		 * In the case of Pacman avoid to eat dot.
		 */
		else{
			
			timeWithoutDot++;
			
			if (timeWithoutDot > 4*baseTime){
				if (rm.getPinky().getState().equals(GhostState.IDLE))
					rm.getPinky().setState(mode);
				else if (rm.getPinky().getState().equals(GhostState.FRIGTHENED_IDLE))
					rm.getPinky().setState(GhostState.FRIGTHENED);	
				else if (rm.getInky().getState().equals(GhostState.IDLE))
					rm.getInky().setState(mode);
				else if (rm.getInky().getState().equals(GhostState.FRIGTHENED_IDLE))
					rm.getInky().setState(GhostState.FRIGTHENED);
				else if (rm.getClyde().getState().equals(GhostState.IDLE))
					rm.getClyde().setState(mode);
				else if (rm.getClyde().getState().equals(GhostState.FRIGTHENED_IDLE))
					rm.getClyde().setState(GhostState.FRIGTHENED);
				
				timeWithoutDot = 0;
			}
		}
		
		return;
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
				if (g.getState().equals(GhostState.IDLE))
					g.setState(GhostState.FRIGTHENED_IDLE);
				else if (!g.getState().equals(GhostState.LEAKED))
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
						g.setState(GhostState.IDLE);
				
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
	 * @return
	 */
	public boolean eventLife(){
			
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
					
					if (g == rm.getPinky())
						pinkyDotLimitEnable = true;
					else if (g == rm.getInky())
						inkyDotLimitEnable = true;
					else if (g == rm.getClyde())
						clydeDotLimitEnable = true;
					
					score += 200;
				}
				else if(!g.getState().equals(GhostState.LEAKED)){
					life--;
					resetGame();
				}
						
		return true;
	}
	
	/**
	 * Manage mode/time events.
	 */
	public void eventTime(){
			
		if (isFrigthned)
			return;
		
		if (mode.equals(GhostState.SCATTER)){
			
			if ((scatterTimer != -1)&&(stateTimer == scatterTimer*baseTime)){

				for (Ghost g : rm.getGhost())
					if (g.getState().equals(GhostState.SCATTER))
						g.setState(GhostState.CHASE);
				
				stateTimer = 0;
				scatterNbSwitch++;
				mode = GhostState.CHASE;
				
				if (scatterNbSwitch == 2)
					scatterTimer = 5;
				else if (scatterNbSwitch == 4)
					scatterTimer = -1;
			}
		}
		else if (mode.equals(GhostState.CHASE)){
			
			if ((chaseTimer != -1)&&(stateTimer == chaseTimer*baseTime)){
				for (Ghost g : rm.getGhost())
					if (g.getState().equals(GhostState.CHASE))
						g.setState(GhostState.SCATTER);
				
				stateTimer = 0;
				chaseNbSwitch++;
				mode = GhostState.SCATTER;
				
				if (chaseNbSwitch == 3)
					chaseTimer = -1;
			}
		}
		
		stateTimer++;
						
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
	 * @return
	 */
	public boolean resetGame(){
		
		for(MovableObject mo : rm.getMovableObject())
			mo.resetPos();
		
		for(Ghost g : rm.getGhost())
			g.resetState();
		
		pinkyDotLimitEnable = false;
		inkyDotLimitEnable = false;
		clydeDotLimitEnable = false;
		
		stateTimer = 0;
		scatterNbSwitch = 0;
		chaseNbSwitch = 0;
		
		mode = GhostState.SCATTER;
		
		globalDotLimit = 0;
		
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
