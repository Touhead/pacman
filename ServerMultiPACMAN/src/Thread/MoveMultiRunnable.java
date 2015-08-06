package Thread;

import java.io.IOException;
import java.util.ArrayList;

import Controller.AbstractController;
import Network.Client;

/**
 * This runnable is used to get the movement made by the clients
 * It does it each 40ms and then runs the model
 * 
 * @author touhead
 *
 */
public class MoveMultiRunnable implements IRunnable{

	private ArrayList<Client> clients;
	private AbstractController controller;
	private boolean running;
	
	private ArrayList<String> input;
	
	/**
	 * Create a new Move Multi Runnable
	 * 
	 * @param clients
	 * @param controller
	 * @throws Exception
	 */
	public MoveMultiRunnable(ArrayList<Client> clients, AbstractController controller) throws Exception {
		super();

		if (clients.size() != 3)
			throw new Exception();
		
		this.clients = clients;
		this.controller = controller;
	}

	public void run(){
			
		running = true;
		input = new ArrayList<String>(3);
		input.add("");
		input.add("");
		input.add("");
		
		while(running){
						
			try {
				
				for (int i = 0; i < clients.size(); i++){
					input.add(i, clients.get(i).readMove(input.get(i)));
					input.remove(i+1);
				}
			} 
			catch (IOException e1) {
				
				e1.printStackTrace();
			}
			
			controller.setMove(input.get(0), input.get(1), input.get(2));
			
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
