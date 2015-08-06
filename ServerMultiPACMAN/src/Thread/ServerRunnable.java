package Thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.LinkedList;

import Network.Client;

/**
 * 
 * Launch the server and accept the connection in an infinite loop
 * When a client is accepted, he's passed to the Client Manager
 * 
 * @author touhead
 *
 */
public class ServerRunnable implements IRunnable{

	private ServerSocket serverSocketDisplay;
	private ServerSocket serverSocketEvent;
	private Socket clientSocketDisplay;
	private Socket clientSocketEvent;
	private LinkedList<Client> clientList;
	private ClientManager clientManager;
	
	private boolean running;
			
	/**
	 * Create a new Server Runnable with the specified port
	 * 
	 * @param port
	 * @throws IOException
	 */
	public ServerRunnable(int port) throws IOException{
		super();		
		
		serverSocketDisplay = new ServerSocket(port);
		serverSocketEvent = new ServerSocket(port+100);
		clientList = new LinkedList<Client>();
		clientManager = new ClientManager(this);
	}
	
	@Override
	public void run(){
		
		running = true;
		
		while(running){
			
			try {
				
				clientSocketDisplay = serverSocketDisplay.accept();
			
				clientSocketEvent = serverSocketEvent.accept();
					
				Client client = new Client(clientSocketDisplay, clientSocketEvent);
				
				System.out.println(new Date() + " : " + client + " CONNECTED");
				
				clientManager.add(client);
				
				clientList.add(client);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Remove a client from the server list
	 * 
	 * @param client
	 */
	public void close(Client client){

		System.out.println(new Date() + " : " + client + " DISCONNECTED");
		clientList.remove(client);
	}

	@Override
	public void terminate() {
		
		running = false;
	}
	
	@Override
	public boolean isRunning() {
		
		return running;
	}
}
