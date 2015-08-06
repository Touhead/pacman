package View;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.NumberFormat;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Network.Server;

/**
 * The panel allows the users to connect to a server for the specified mode
 * (1 player or multiplayer)
 * 
 * @author touhead
 *
 */
public class ConnectionWindow extends JPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -222735558808328289L;
	
	private JPanel winGetServer;
	
	private JTextField hostInput;
	private JFormattedTextField portInput;
	private JTextField sessionInput;
	private JButton connectSoloButton;
	private JButton connectMultiButton;
	
	private Server server;
	
	private MainWindow mainWindow;
	
	/**
	 * @param mainWindow
	 */
	public ConnectionWindow(MainWindow mainWindow){
		super();
		
		this.mainWindow = mainWindow;
		
		JLabel hostLabel = new JLabel("Host");
		hostInput = new JTextField("127.0.0.1");
		JLabel portLabel = new JLabel("Port");
		portInput = new JFormattedTextField(NumberFormat.getIntegerInstance());
		portInput.setValue(6000);
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
		
		hGroup.addGroup(layout.createParallelGroup().addComponent(hostLabel).addComponent(portLabel).addComponent(sessionLabel));
		hGroup.addGroup(layout.createParallelGroup().addComponent(hostInput).addComponent(portInput).addComponent(sessionInput).addComponent(connectSoloButton).addComponent(connectMultiButton));
		
		layout.setHorizontalGroup(hGroup);
		
		GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
		
		vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(hostLabel).addComponent(hostInput));
		vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(portLabel).addComponent(portInput));
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
	 * Launch the connection to the server with the current value for the server / port
	 * @throws IOException
	 */
	public void connect() throws IOException{
		
		server = new Server(hostInput.getText(), ((Number)portInput.getValue()).intValue());
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