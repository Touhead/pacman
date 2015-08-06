package Thread;

import java.io.IOException;

import Network.Client;

/**
 * This runnable get event from the client, especially the CLOSE event
 * If a CLOSE event happens, then the parents is also terminated
 * 
 * @author touhead
 *
 */
public class EventMultiRunnable implements IRunnable{

	private Client client;
	private MultiModeRunnable parents;
	
	private boolean running = true;
	
	private String input;
	
	/**
	 * 
	 * Create a new Event Multi Runnable
	 * 
	 * 
	 * @param client
	 * @param parents
	 */
	public EventMultiRunnable(Client client, MultiModeRunnable parents) {
		super();

		this.client = client;
		this.parents = parents;
	}

	public void run(){
		
		input = "";
		
		while(running){
			
			try {
				
				input = client.readEvent();
			} 
			catch (IOException e1) {
				
				e1.printStackTrace();
			}
			
			if (input.equals("CLOSE")){
				
				terminate();
				parents.terminate();
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
