package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;

import Network.Server;

/**
 * The panel allows the users to connect to the server for the specified mode
 * (1 player or multiplayer)
 * 
 * @author touhead
 *
 */
public class ConnectionWindowApplet extends JPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -222735558808328289L;
	
	private JPanel winGetServer;
	
	private JTextField sessionInput;
	private JButton connectSoloButton;
	private JButton connectMultiButton;
	
	private Server server;
	
	private MainWindow mainWindow;
	
	public ConnectionWindowApplet(MainWindow mainWindow){
		super();
		
		this.mainWindow = mainWindow;

		JLabel sessionLabel = new JLabel("Session Name (only for multi-player)");
		sessionInput = new JTextField();
		connectSoloButton = new JButton("Solo Game");
		connectSoloButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, connectSoloButton.getMinimumSize().height));
		connectSoloButton.addActionListener(this);
		connectMultiButton = new JButton("Multiplayer Game");
		connectMultiButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, connectMultiButton.getMinimumSize().height));
		connectMultiButton.addActionListener(this);
		
		winGetServer = new JPanel(new GridBagLayout());
		
		GroupLayout layout = new GroupLayout(winGetServer);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
		
		hGroup.addGroup(layout.createParallelGroup().addComponent(sessionLabel));
		hGroup.addGroup(layout.createParallelGroup().addComponent(sessionInput).addComponent(connectSoloButton).addComponent(connectMultiButton));
		
		layout.setHorizontalGroup(hGroup);
		
		GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
		
		vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(sessionLabel).addComponent(sessionInput));
		vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(connectSoloButton));
		vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(connectMultiButton));
		layout.setVerticalGroup(vGroup);
		
		winGetServer.setLayout(layout);
				
		this.setFocusable(true);
		this.setLayout(new BorderLayout());
	    this.add(winGetServer);
	    this.setVisible(true);
	}
	
	/**
	 * Launch the connection to the server
	 * @throws IOException
	 */
	public void connect() throws IOException{
		
		server = new Server("vps160405.ovh.net", 6000);
		server.open();
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == connectSoloButton){
			
			try {
				
				connect();
				server.sendInfo("SOLO");
				mainWindow.launchConnection(server);
			}
			catch (IOException e1) {
				
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "Connection failed", "Warning", JOptionPane.WARNING_MESSAGE);
			}
		}
		if (e.getSource() == connectMultiButton){
			
			try {
				
				connect();
				server.sendInfo("MULTI");
				server.sendInfo(sessionInput.getText());
				mainWindow.launchConnectionMulti(server);
			}
			catch (IOException e1) {
				
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "Connection failed", "Warning", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
}
