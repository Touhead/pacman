package Thread;

import java.io.IOException;

import Network.Server;
import View.GameWindow;

/**
 * 
 * This runnable fetch the event sent by the server
 * such as the score, the life or if the player lose or win
 * 
 * @author touhead
 *
 */
public class EventRunnable implements IRunnable {

	private Server server;
	private GameWindow gw;
	
	private boolean running;
	
	public EventRunnable(Server server, GameWindow gw) {
		super();

		this.server = server;
		this.gw = gw;
		running = true;
	}

	public void run(){
		
		Object[] event;
		
		while(running){
								
			try {
				
				event = server.readEvent();
				if (event != null)
					gw.updateEvent((Integer)event[0], (Integer)event[1], (Boolean)event[2], (Boolean)event[3], (Boolean)event[4]);
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
