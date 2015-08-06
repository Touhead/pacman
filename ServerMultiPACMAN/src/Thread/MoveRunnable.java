package Thread;

import java.io.IOException;

import Controller.AbstractController;
import Network.Client;

/**
 * 
 * This runnable is used to get the movement made by the clients
 * It does it each 40ms and then runs the model
 * 
 * @author touhead
 *
 */
public class MoveRunnable implements IRunnable{

	private Client client;
	private AbstractController controller;
	private boolean running;
	
	private String input;
	
	/**
	 * Create a new Move Multi Runnable
	 * 
	 * @param client
	 * @param controller
	 * @throws Exception
	 */
	public MoveRunnable(Client client, AbstractController controller) {
		super();

		this.client = client;
		this.controller = controller;
	}

	public void run(){
			
		running = true;
		input = "";
		
		while(running){
			
			try {
				
				input = client.readMove(input);
			} 
			catch (IOException e1) {
				
				e1.printStackTrace();
			}
			
			controller.setMove(input);
			
	    	try {
	    		
				Thread.sleep(40);
			}
	    	catch (InterruptedException e) {
				
				Thread.currentThread().interrupt();
				running = false;
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
