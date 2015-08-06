package View;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.io.IOException;

import Network.Server;

/**
 * 
 * Main applet which manages all the other panels
 * 
 * @author touhead
 *
 */
public class MainApplet extends Applet implements MainWindow{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4713644016722461822L;
	
	private ConnectionWindowApplet connectWin;
	private GetFileWindow getFileWin;
	private GameWindow gameWin;
	
	private Server server;
	
	public MainApplet(){
		super();
	
		gameWin = null;
		server = null;
		connectWin = new ConnectionWindowApplet(this);
		
		this.setLayout(new BorderLayout());
		this.add(connectWin);
	    
		this.setVisible(true);
	}
	
	public void launchConnection(Server server){
		
		this.server = server;
		
		connectWin.setVisible(false);
		this.remove(connectWin);
		
		getFileWin = new GetFileWindow(server, this);
		this.add(getFileWin);
		this.revalidate();
		this.repaint();
	}
	
	public void launchConnectionMulti(Server server){
		
		this.server = server;
		
		connectWin.setVisible(false);
		this.remove(connectWin);
		
		System.out.println("IN");
		try {
			if (!server.readInfo().equals("MAZE")){
				System.out.println("MAZE");
				launchGameMulti();
				return;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("OUT");
		getFileWin = new GetFileWindow(server, this);
		this.add(getFileWin);
		this.revalidate();
		this.repaint();
	}
	
	public void launchGame(){
		
		getFileWin.setVisible(false);
		this.remove(getFileWin);
		gameWin = new GameWindow(server, this);
		this.add(gameWin);
		this.revalidate();
		this.repaint();
		gameWin.requestFocus();
		gameWin.start();
	}
	
	/**
	 * Launch the game for multiplayer if the player is not the first connected to the session
	 * we don't open the Get File Window
	 */
	public void launchGameMulti(){
		
		gameWin = new GameWindow(server, this);
		this.add(gameWin);
		this.revalidate();
		this.repaint();
		gameWin.requestFocus();
		gameWin.start();
	}
	
	public void resetGame(){
		
		System.out.println("RESET GAME");
		gameWin.setVisible(false);
		this.remove(gameWin);
		gameWin = null;
		
		this.add(connectWin);
		connectWin.setVisible(true);
		this.revalidate();
		this.repaint();
		
		System.out.println("END GAME");
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
		System.out.println("CLOSE APPLET");

		if (gameWin != null)
			gameWin.close();
		if (server != null)
			server.close();
	}
}
