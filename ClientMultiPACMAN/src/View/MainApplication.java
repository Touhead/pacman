package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.JFrame;

import Network.Server;

/**
 * 
 * Main application which manages all other panels
 * @author touhead
 *
 */
public class MainApplication extends JFrame implements MainWindow, WindowListener{

private static final long serialVersionUID = 1L;
	
	private ConnectionWindow connectWin;
	private GetFileWindow getFileWin;
	private GameWindow gameWin;
	
	private Server server;
	
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub

		new MainApplication();
		return;
	}
	
	public MainApplication(){
		super();
	
		gameWin = null;
		server = null;
		connectWin = new ConnectionWindow(this);
		
		this.setLayout(new BorderLayout());
		
	    this.add(connectWin);
	    this.addWindowListener(this);
	    
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setSize(new Dimension(600,800));
	    setLocationRelativeTo(null);
	    
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
		
		this.add(connectWin);
		connectWin.setVisible(true);
		this.revalidate();
		this.repaint();
		
		System.out.println("END GAME");
	}

	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
	}

	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
		System.out.println("CLOSE APP");
		if (gameWin != null)
			gameWin.close();
		if (server != null)
			server.close();
	}

	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
	}

	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
	}

	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
	}

	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
	}
}
