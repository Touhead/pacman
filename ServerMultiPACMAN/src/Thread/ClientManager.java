package Thread;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.Semaphore;

import Network.Client;

/** 
 * Handle and manage each client.
 * 
 * @author Touhead
 * @version 1.0
 * */
public class ClientManager{
 
	Semaphore lock = new Semaphore(1, true);
	
    private ServerRunnable server;
     
    private HashMap<Client, IRunnable> session = new HashMap<Client, IRunnable>();
    private HashMap<String, MultiModeRunnable> multiSession = new HashMap<String, MultiModeRunnable>();
    private Thread sessionThread;
    private HashMap<String, Thread> multiSessionThread = new HashMap<String, Thread>();
    
    /**
     * Create a new Client Manager
     *
     * @param server
     * @throws IOException
     */
    public ClientManager(ServerRunnable server) throws IOException {
        super();
         
        this.server = server;
    }
    
    /**
     * Add a new client to this manager
     *
     * @param client
     */
    public void add(Client client){
    	
    	String sessionType = "";
    	
		try {
			sessionType = client.readInfo();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
    	if (sessionType.equals("SOLO")){
    		
    		session.put(client, new SoloModeRunnable(client, this));
    		
    		sessionThread = new Thread(session.get(client));
        	sessionThread.start();
    	}
    	else if (sessionType.equals("MULTI")){
    		
    		String sessionName = null;
    		
    		try {
    			sessionName = client.readInfo();
    		} catch (IOException e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}
    		    		    			
    		try {
				lock.acquire();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    		
    		if (!multiSession.containsKey(sessionName)){
    			
    			multiSession.put(sessionName, new MultiModeRunnable(this, sessionName));
    			multiSessionThread.put(sessionName, new Thread(multiSession.get(sessionName)));
    			
    			lock.release();
    			
    			if (multiSession.get(sessionName).addPlayer(client)){
    			
	    			try {
						client.sendInfo("MAZE");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    			multiSession.get(sessionName).setMaze(client);
    			}
    			else{
					client.close();
					remove(client);
				}
    		}
    		else{
    			
    			lock.release();
    			
	    		try {
					client.sendInfo("");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		
	    		if (multiSession.get(sessionName).addPlayer(client)){
	    			if (multiSession.get(sessionName).isRunnable())
						multiSessionThread.get(sessionName).start();
	    		}
				else{
					client.close();
					remove(client);
				}
    		}
	    }
    	else{
    		
    		client.close();
    		remove(client);
    	}
    }
    
    
    
    /**
     * Remove a client from the manager
     * 
     * @param client
     */
    public void remove(Client client){
    	
    	session.remove(client);
    	server.close(client);
    }
    
    /**
     * Remove a session from the manager
     * 
     * @param session
     */
    public void removeSession(String session){
    	
    	multiSession.remove(session);
    	multiSessionThread.remove(session);
    }
}