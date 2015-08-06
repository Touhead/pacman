package Thread;

import java.io.IOException;

import Network.Server;
import View.GameWindow;

/**
 * 
 * This runnable fetch the maze sent by the server
 * 
 * @author touhead
 *
 */
public class DisplayRunnable implements IRunnable {

	private Server server;
	private GameWindow gw;
	
	private boolean running;
	
	/**
	 * @param server
	 * @param gw
	 * 		the game window
	 */
	public DisplayRunnable(Server server, GameWindow gw) {
		super();
		
		this.server = server;
		this.gw = gw;
		running = true;
	}

	public void run(){
		
		while(running){
								
			try {
				
				gw.updateMaze(server.readDisplay());
			}
			catch (ClassNotFoundException e1) {
				
				terminate();
				e1.printStackTrace();
			}
			catch (IOException e1) {
				
				terminate();
				e1.printStackTrace();
			}
		}
	}
	
	public void terminate(){
		
		running = false;
	}

	@Override
	public boolean isRunning() {
		// TODO Auto-generated method stub
		return running;
	}
}
