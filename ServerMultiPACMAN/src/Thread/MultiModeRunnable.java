package Thread;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.Semaphore;

import Controller.AbstractController;
import Controller.Controller;
import Exception.ExceptionInvalidFile;
import Model.AbstractMultiModel;
import Model.GameMultiManager;
import Network.Client;
import Observer.Observer;

/**
 * 
 * This runnable can handle 3 clients
 * When started, it launches the MoveMultiRunnable
 * 
 * @author touhead
 *
 */
public class MultiModeRunnable implements IRunnable, Observer{

	private ClientManager clientManager;
	private String session;
	
	private ArrayList<Client> clients = new ArrayList<Client>(3);
	
	private AbstractMultiModel model;
	private AbstractController controller;
	
	private IRunnable moveRunnable;
	private HashMap<Client, IRunnable> eventRunnable = new HashMap<Client, IRunnable>();
	
	private Thread moveThread;
	private HashMap<Client, Thread> eventThread = new HashMap<Client, Thread>();
	
	private Semaphore semaphore = new Semaphore(0, true);
	
	/**
	 * Create a new Multi Mode Runnable for a specific session
	 * 
	 * @param clientManager
	 * @param session
	 */
	public MultiModeRunnable(ClientManager clientManager, String session){
		super();
		
		this.clientManager = clientManager;
		this.session = session;
	}
	
	/**
	 * 
	 * Get the maze from the 1st client
	 * 
	 * @param client
	 */
	public void setMaze(Client client){
		
		model = new GameMultiManager();
		controller = new Controller(model);
		
		model.addObserver(this);
		
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
			//client.sendFile("PACMAN_MAZE");
			client.sendInfo("FILE_END");
		
			String fileName = "";
			
			fileName = client.readInfo();
			
			controller.setFileName("./maze/" + fileName);
			
			semaphore.release();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * Add a client to this runnable
	 * A Event Multi Runnable on this client is immediately launched
	 * 
	 * @param client
	 * @return true if the player is added, false otherwise
	 */
	public synchronized boolean addPlayer(Client client){
		
		if (clients.size() > 2)
			return false;
		
		eventRunnable.put(client, new EventMultiRunnable(client, this));
		eventThread.put(client, new Thread(eventRunnable.get(client)));
		eventThread.get(client).start();
		
		clients.add(client);
		return true;
	}

	public boolean isRunnable(){
		
		return (clients.size() == 3);
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		try {
			
			semaphore.acquire();
			
			controller.setCmdMulti("START");
			moveRunnable = new MoveMultiRunnable(clients, controller);
	    	moveThread = new Thread(moveRunnable);
	    	moveThread.start();
    		
	    	for (Client client : clients)
	    		eventThread.get(client).join();
	    	
	    	moveRunnable.terminate();
	    	moveThread.interrupt();
	    	moveThread.join();
	    	
	    	System.out.println("MULTI JOINED");
    	} catch (InterruptedException e) {

    		e.printStackTrace();
		} catch (ExceptionInvalidFile e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e2){
			
			e2.printStackTrace();
		}
		
		System.out.println("END");
	}

	@Override
	public void terminate() {
		// TODO Auto-generated method stub
		
		Iterator<Entry<Client, IRunnable>> iE = eventRunnable.entrySet().iterator();
		Entry<Client, IRunnable> result;
		
		while (iE.hasNext()){
			
			result = iE.next();
			result.getValue().terminate();
			result.getKey().close();
		}
		
		for (Client client : clients)
    		clientManager.remove(client);
    	
    	clientManager.removeSession(session);
	}

	@Override
	public boolean isRunning() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void updateMaze(int[][] maze) {
		
		try {
			
			for (Client client : clients)
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
			
			for (Client client : clients)
				client.sendEvent(events);
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
}
