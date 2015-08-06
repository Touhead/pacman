package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import Network.Server;

/**
 * The Main Window to select a file and start the game.
 * @author Touhead
 *
 */
public class GetFileWindow extends JPanel implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel winGetFile;
		
	private DefaultComboBoxModel<String> fileComboBoxModel;
	private JComboBox<String> fileComboBox;
	private JButton startButton;
	
	private String fileName;
	
	private Server server;
	
	private MainWindow mainWindow;
	
	
	/**
	 * @param server
	 * @param mainWindow
	 */
	public GetFileWindow(Server server, MainWindow mainWindow){
		
		super();
	    
		this.server = server;
		this.mainWindow = mainWindow;
		
		fileName = new String();
		
	    
	    fileComboBoxModel = new DefaultComboBoxModel<String>();
	    getFile();
	    fileComboBox = new JComboBox<String>(fileComboBoxModel);
	    fileComboBox.addActionListener(this);
	    startButton = new JButton("START");
	    startButton.addActionListener(this);
	    startButton.setPreferredSize(new Dimension(100,80));

	    /**
	     * Create the winGetFile panel which contains the START and OPEN FILE button.
	     */
	    winGetFile = new JPanel();
	    winGetFile.setLayout(new GridBagLayout());

	    /**
	     * Place buttons in the winGetFile panel.
	     */
	    GridBagConstraints layoutConstraints = new GridBagConstraints();
	    
	    layoutConstraints.gridwidth = 1;
	    layoutConstraints.gridheight = 1;
	    layoutConstraints.weightx = 1;
	    layoutConstraints.weighty = 1;
	    layoutConstraints.fill = GridBagConstraints.HORIZONTAL;

	    
	    layoutConstraints.gridx = 0;
	    layoutConstraints.gridy = 0;

	    winGetFile.add(fileComboBox, layoutConstraints);
	    
	    layoutConstraints.gridx = 0;
	    layoutConstraints.gridy = 1;

	    winGetFile.add(startButton, layoutConstraints);
	    
	    /**
	     * Add the winGetFile panel in this Frame and display it.
	     */
	    this.setLayout(new BorderLayout());
	    this.add(winGetFile);
	    this.setVisible(true);
	}
	
	/**
	 * Get the maze's name from the server to display it in the combo box
	 */
	public void getFile(){
		
		String file = "";
		
		while (!file.equals("FILE_END")){
			
			try {
				file = server.readInfo();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			fileComboBoxModel.addElement(file);
		}
		
		fileComboBoxModel.removeElement("FILE_END");
		fileName = fileComboBoxModel.getElementAt(0);
	}
	

	/**
	 * This method is called when the user clicks on the start button
	 * Send the chosen maze to the server
	 */
	public void start(){

		try {
			server.sendInfo(fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mainWindow.launchGame();
	}

	/**
	 * Action performed when a button is clicked.
	 */
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == fileComboBox){
			
			fileName = (String)fileComboBox.getSelectedItem();
		}
		else if (e.getSource() == startButton){
			
			start();
		}
	}
}
