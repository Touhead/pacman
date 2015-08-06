package Thread;

import java.io.IOException;

import Network.Client;

/**
 * 
 * This runnable get event from the client, especially the CLOSE event
 * 
 * @author touhead
 *
 */
public class EventRunnable implements IRunnable{

	private Client client;
	private boolean running;
	
	private String input;
	
	/**
	 * 
	 * Create a new Event Multi Runnable
	 * 
	 * @param client
	 */
	public EventRunnable(Client client) {
		super();

		this.client = client;
	}

	public void run(){
		
		running = true;
		input = "";
		
		while(running){
			
			try {
				
				input = client.readEvent();
			} 
			catch (IOException e1) {
				
				e1.printStackTrace();
			}
			
			if (input.equals("CLOSE")){
				client.close();
				terminate();
			}
		}
	}
	
	@Override
	public void terminate(){
		
		running = false;
	}

	@Override
	public boolean isRunning() {
		
		return running;
	}
}
