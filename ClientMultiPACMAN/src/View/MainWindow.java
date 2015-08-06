package View;

import Network.Server;

/**
 * Window which implements Main Window are dedicated to manager the other panels
 * @author touhead
 *
 */
public interface MainWindow {

	/**
	 * Launch a connection for a 1 player mode
	 * @param server
	 */
	public void launchConnection(Server server);
	/**
	 * Launch a connection for a multiplayer mode
	 * Determine if this user is the first one to be connected to this session
	 * And open the right window in accordance with it
	 * 
	 * @param server
	 */
	public void launchConnectionMulti(Server server);
	/**
	 * Launch the Game Window
	 */
	public void launchGame();
	/**
	 * Reset the view at the first window : the Connection Window
	 */
	public void resetGame();	
}
