package Network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 
 * Represent a connection with the server
 * 
 * @author touhead
 *
 */
public class Server {
	
	private String host;
	private int port;
	
	private Socket socketDisplay;
	private Socket socketEvent;
	
	private ObjectOutputStream outDisplay;
	private ObjectInputStream inDisplay;
	
	private ObjectOutputStream outEvent;
	private ObjectInputStream inEvent;
			
	/**
	 * @param host
	 * @param port
	 */
	public Server(String host, int port){
		
		this.host = host;
		this.port = port;
	}
	
	/**
	 * 
	 * Set up the sockets and the input / output streams
	 * 
	 * @throws IOException
	 */
	public void open() throws IOException{
		
		System.out.println("BEGIN CREATION SERVER : " + host + " " + port);
		
		socketDisplay = new Socket(host, port);
		socketEvent = new Socket(host, port+100);
		
		System.out.println("CONNECTEION SERVER OK : " + host + " " + port);
		
		outDisplay = new ObjectOutputStream(socketDisplay.getOutputStream());
		outEvent = new ObjectOutputStream(socketEvent.getOutputStream());
		
		inDisplay = new ObjectInputStream(socketDisplay.getInputStream());
		inEvent = new ObjectInputStream(socketEvent.getInputStream());
		
		System.out.println("END CREATION SERVER");
	}
	
	/**
	 * 
	 * Send a move to the server with the display socket
	 * 
	 * @param input
	 * @throws IOException
	 */
	public void sendMove(String input) throws IOException{
		
		if (socketDisplay.isClosed())
			return;
		
		if (input != null){
			outDisplay.writeUTF(input);
			outDisplay.flush();
		}
	}
	
	/**
	 * 
	 * Send an event to the server with the event socket
	 * @param input
	 * @throws IOException
	 */
	public void sendEvent(String input) throws IOException{
		
		if (socketEvent.isClosed())
			return;
		
		if ((input != null)&&(!input.equals(""))){
				outEvent.writeUTF(input);
				outEvent.flush();
		}
	}
	
	/**
	 * 
	 * Send an info to the server with the display socket
	 * 
	 * @param input
	 * @throws IOException
	 */
	public void sendInfo(String input) throws IOException{
		
		if (socketDisplay.isClosed())
			return;
		
		if (input != null){
			outDisplay.writeUTF(input);
			outDisplay.flush();
		}
	}

	/**
	 * @return the maze sent by the server
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public int[][] readDisplay() throws IOException, ClassNotFoundException{
		
		Object object;
		
		if (socketDisplay.isClosed())
			return null;

		if ((object = inDisplay.readObject()) != null)
				return (int[][])object;
		
		return null;
	}
	
	/**
	 * @return the events sent by the server
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public Object[] readEvent() throws IOException, ClassNotFoundException{
		
		Object object;

		if (socketEvent.isClosed())
			return null;
		
		if ((object = inEvent.readObject()) != null)
				return (Object[])object;
		
				
		return null;
	}
	
	/**
	 * @return the info sent by the server
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public String readInfo() throws IOException, ClassNotFoundException{
		
		Object file;
		
		if (socketEvent.isClosed())
			return null;
		
		if ((file = inEvent.readObject()) != null)
				return (String)file;
		
		return null;
	}
	
	/**
	 * Close the connection with the server
	 */
	public void close(){
		
		System.out.println("CLOSE SERVER");
		
		try {	
				socketDisplay.close();
				sendEvent("CLOSE");
				socketEvent.close();
		} 
		catch (IOException e) {

			e.printStackTrace();
		}
		
		System.out.println("SERVER CLOSED");
	}
}
