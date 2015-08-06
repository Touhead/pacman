package Network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 
 * A client represents a connection with a client
 * 
 * @author touhead
 *
 */
public class Client {

	private Socket socketDisplay;
	private Socket socketEvent;
	
	private ObjectOutputStream outDisplay;
	private ObjectInputStream inDisplay;
	
	private ObjectOutputStream outEvent;
	private ObjectInputStream inEvent;
	
	/**
	 * 
	 * Create a new client for the specified sockets
	 * 
	 * @param socketDisplay
	 * @param socketEvent
	 * @throws IOException
	 */
	public Client(Socket socketDisplay, Socket socketEvent) throws IOException {
		super();

		this.socketDisplay = socketDisplay;
		this.socketEvent = socketEvent;
		
		outDisplay = new ObjectOutputStream(socketDisplay.getOutputStream());
		outEvent = new ObjectOutputStream(socketEvent.getOutputStream());

		inDisplay = new ObjectInputStream(socketDisplay.getInputStream());
		inEvent = new ObjectInputStream(socketEvent.getInputStream());
	}
	
	/**
	 * 
	 * Read event from the event socket
	 * 
	 * @return return the reading event, an empty string otherwise
	 * @throws IOException
	 */
	public String readEvent() throws IOException{
		
		if (socketEvent.isClosed())
			return "CLOSE";
				
		String inputLine;
		
		if ((inputLine = inEvent.readUTF()) != null)
				return inputLine;
		
		return "";
	}

	/**
	 * 
	 * Read move from the display socket
	 * 
	 * @param move
	 * 		the previous move
	 * @return the reading move if any, the previous move otherwise
	 * @throws IOException
	 */
	public String readMove(String move) throws IOException{
		
		if (socketDisplay.isClosed())
			return "";
		
		if (inDisplay.available() == 0)
			return move;
		
		String inputLine;
				
		if ((inputLine = inDisplay.readUTF()) != null)
				return inputLine;
		
		return move;
	}
	
	/**
	 * Read an information from the display socket
	 * 
	 * @return the reading info if any, an empty string otherwise
	 * @throws IOException
	 */
	public String readInfo() throws IOException{
		
		if (socketDisplay.isClosed())
			return "";
				
		String inputLine;
		
		if ((inputLine = inDisplay.readUTF()) != null)
				return inputLine;
		
		return "";
	}
	
	/**
	 * Send the maze to the client
	 * @param maze
	 * @throws IOException
	 */
	public void sendDisplay(int[][] maze) throws IOException{
	
		if (socketDisplay.isClosed())
			return;
		
		outDisplay.flush();
		outDisplay.writeObject(maze);
	}
	
	/**
	 * 
	 * Send the event to the client
	 * 
	 * @param event
	 * @throws IOException
	 */
	public void sendEvent(Object[] event) throws IOException{
		
		if (socketEvent.isClosed())
			return;
		
		outEvent.flush();
		outEvent.writeObject(event);
	}
	
	/**
	 * 
	 * Send information to the client
	 * 
	 * @param info
	 * @throws IOException
	 */
	public void sendInfo(String info) throws IOException{
		
		if (socketEvent.isClosed())
			return;
		
		outEvent.flush();
		outEvent.writeObject(info);
	}
	
	/**
	 * Close the connection to the client
	 */
	public void close(){
		
		try {
			
			socketDisplay.close();
			socketEvent.close();
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString(){
		
		return socketDisplay.getInetAddress().toString();
	}
}
