package Thread;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import Controller.AbstractController;
import Controller.Controller;
import Exception.ExceptionInvalidFile;
import Model.AbstractModel;
import Model.GameManager;
import Network.Client;
import Observer.Observer;

/**
 * 
 * This runnable handle an 1 player session
 * When started, it does the procedure to get the maze and then starts the game
 * 
 * @author touhead
 *
 */
public class SoloModeRunnable implements IRunnable, Observer{

	private Client client;
	private ClientManager clientManager;
	
	AbstractModel model;
	AbstractController controller;
	
	private IRunnable moveRunnable;
	private IRunnable eventRunnable;
	
	private Thread moveThread;
	private Thread eventThread;
	
	/**
	 * Create a new Solo Mode Runnable with the specified client
	 * 
	 * @param client
	 * @param clientManager
	 */
	public SoloModeRunnable(Client client, ClientManager clientManager) {
		super();
		
		this.client = client;
		this.clientManager = clientManager;
	}

	public void run() {
    	
    	model = new GameManager();
		controller = new Controller(model);
		
		model.addObserver(this);
		
		eventRunnable = new EventRunnable(client);
		eventThread = new Thread(eventRunnable);
		eventThread.start();
		
		try {
			
			
			File repertoire = new File("./maze/");
			
			FilenameFilter filter = new FilenameFilter() {
			    public boolean accept(File directory, String fileName) {
			        return !fileName.endsWith("_option");
			    }
			};
			
			String[] files = repertoire.list(filter);
						
			for (int i = 0; i < files.length; i++)
				client.sendInfo(files[i]);
			client.sendInfo("FILE_END");
		
			String fileName = "";
			
			fileName = client.readInfo();
			
			controller.setFileName("./maze/" + fileName);
			
			controller.setCmd("START");
						
	    	moveRunnable = new MoveRunnable(client, controller);
	    	moveThread = new Thread(moveRunnable);
	    	moveThread.start();
			
	    	try {
	    		
				eventThread.join();
		    	moveRunnable.terminate();
		    	moveThread.interrupt();
		    	moveThread.join();
		    	clientManager.remove(client);
	    	} 
	    	catch (InterruptedException e) {
	
	    		e.printStackTrace();
			}	
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			eventRunnable.terminate();
			try {
				eventThread.join();
				clientManager.remove(client);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		catch (ExceptionInvalidFile e) {
			// TODO Auto-generated catch block
			eventRunnable.terminate();
			try {
				eventThread.join();
				clientManager.remove(client);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
    }

	@Override
	public void updateMaze(int[][] maze) {
		
		try {
			
			client.sendDisplay(maze);
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

	@Override
	public void updateEvent(int life, int score, boolean isReset,
			boolean lose, boolean win) {

		Object[] events = new Object[5];
		events[0] = (Object)Integer.valueOf(life);
		events[1] = (Object)Integer.valueOf(score);
		events[2] = (Object)Boolean.valueOf(isReset);
		events[3] = (Object)Boolean.valueOf(lose);
		events[4] = (Object)Boolean.valueOf(win);
		
		try {
			
			client.sendEvent(events);
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	@Override
	public void terminate(){
		
		eventRunnable.terminate();
	}
	
	@Override
	public boolean isRunning() {
		
		return eventRunnable.isRunning();
	}
}
