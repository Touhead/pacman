package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import Network.Server;
import Thread.DisplayRunnable;
import Thread.EventRunnable;
import Thread.IRunnable;

/**
 * The Game Window to display the game.
 * @author Touhead
 *
 */
public class GameWindow extends JPanel implements KeyListener{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
     
    private MainWindow mainWindow;
 
    private JSplitPane winGame;
    private JPanel mazeWin;
    private GridLayout mazeTableLayout;
    private JPanel[][] mazeTableCase;
    private String move;
     
     
    private JPanel info;
    private JLabel life;
    private JLabel score;
     
    private IRunnable displayRunnable;
    private IRunnable eventRunnable;
    private Thread displayThread;
    private Thread eventThread;
    
    private Thread endThread;
     
    private Server server;
          
    /**
     * @param server
     * @param mainWindow
     */
    public GameWindow(Server server, MainWindow mainWindow){
         
        this.server = server;
        this.mainWindow = mainWindow;
         
        move = new String();
         
        /**
         * Create a mazeWin panel to display the game.
         */
        mazeWin = new JPanel();
        mazeWin.setBackground(Color.BLACK);
         
        /**
         * Create a info panel to display the life and the score.
         */
        info = new JPanel();
         
        GridLayout infoLayout = new GridLayout(1,4);
        info.setLayout(infoLayout);
         
        life = new JLabel();
        score = new JLabel();
        JLabel lifeTemp = new JLabel("Life : ");
        JLabel scoreTemp = new JLabel("Score : ");
         
        life.setForeground(Color.WHITE);
        life.setHorizontalAlignment(JLabel.LEFT);
        life.setVerticalAlignment(JLabel.CENTER);
        score.setForeground(Color.WHITE);
        score.setHorizontalAlignment(JLabel.LEFT);
        score.setVerticalAlignment(JLabel.CENTER);
        lifeTemp.setForeground(Color.WHITE);
        lifeTemp.setHorizontalAlignment(JLabel.RIGHT);
        lifeTemp.setVerticalAlignment(JLabel.CENTER);
        scoreTemp.setForeground(Color.WHITE);
        scoreTemp.setHorizontalAlignment(JLabel.RIGHT);
        scoreTemp.setVerticalAlignment(JLabel.CENTER);
         
        info.add(lifeTemp);
        info.add(life);
        info.add(scoreTemp);
        info.add(score);
         
        info.setBackground(Color.BLACK);
         
        /**
         * Create a winGame split pane to display the game panel above the info panel.
         */
        winGame = new JSplitPane(JSplitPane.VERTICAL_SPLIT, mazeWin, info);
        winGame.setDividerSize(0);
        winGame.setDividerLocation(500);
         
        /**
         * Add the winGame to this Frame.
         * Add a keyListener and a windowListener to this frame.
         */
        this.setLayout(new BorderLayout());
        this.add(winGame);
         
        this.addKeyListener(this);
        this.setFocusable(true);
    }
 
    /**
     * While a key is pressed, set the move.
     */
    public void keyPressed(KeyEvent keyEvent) {
        
        move = String.valueOf(keyEvent.getKeyCode());
        try {
			server.sendMove(move);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
 
    public void keyReleased(KeyEvent arg0) {
        // TODO Auto-generated method stub
    }
 
    public void keyTyped(KeyEvent keyEvent) {
        // TODO Auto-generated method stub
    }
     

    /**
     * Update the view with the maze passed in parameter
     * @param maze
     */
    public void updateMaze(int[][] maze) {
                  
        if (maze == null)
            return;
         
        if (mazeTableCase == null){
             
            JPanel tempPanel;
            mazeTableCase = new JPanel[maze.length][maze[0].length];
            mazeTableLayout = new GridLayout(maze.length, maze[0].length);
            mazeWin.setLayout(mazeTableLayout);
             
            for (int i = 0; i < maze.length; i++){
                for (int ii = 0; ii < maze[i].length; ii++){
                     
                    if (maze[i][ii] == 0){
                         
                        tempPanel = new JPanel();
                        tempPanel.setBackground(Color.getHSBColor(0, 0, 0));
                    }
                    else if (maze[i][ii] == 1){
                         
                        tempPanel = new JPanel();
                        tempPanel.setBackground(Color.getHSBColor(0, 0, (float)0.4));
                    }
                    else if (maze[i][ii] == 2){
                         
                        tempPanel = new JPanel();
                        tempPanel.setBackground(Color.getHSBColor(0, 0, (float)0.15));
 
                    }
                    else if (maze[i][ii] == 3){
                         
                        tempPanel = new JPanel();
                        tempPanel.setBackground(Color.getHSBColor((float)(60/360.0), 1, 1));
 
                    }
                    else if (maze[i][ii] == 4){
                         
                        tempPanel = new JPanel();
                        tempPanel.setBackground(Color.getHSBColor(0, 1, 1));
 
                    }
                    else if (maze[i][ii] == 5){
                         
                        tempPanel = new JPanel();
                        tempPanel.setBackground(Color.getHSBColor((float)(300/360.0), (float)0.4, 1));
 
                    }
                    else if (maze[i][ii] == 6){
                         
                        tempPanel = new JPanel();
                        tempPanel.setBackground(Color.getHSBColor((float)(220/360.0), 1, 1));
 
                    }
                    else if (maze[i][ii] == 7){
                         
                        tempPanel = new JPanel();
                        tempPanel.setBackground(Color.getHSBColor((float)(30/360.0), 1, 1));
                    }
                    else if (maze[i][ii] == 8){
                         
                        tempPanel = new JPanel();
                        tempPanel.setBackground(Color.getHSBColor(0, 0, (float)0.4));
                        BorderLayout tempLayout = new BorderLayout(1,1);
                        tempPanel.setLayout(tempLayout);
                        Dot temp = new Dot();
                        tempPanel.add(temp);
                         
                    }
                    else if (maze[i][ii] == 9){
                         
                        tempPanel = new JPanel();
                        tempPanel.setBackground(Color.getHSBColor(0, 0, (float)0.4));
                        BorderLayout tempLayout = new BorderLayout(1,1);
                        tempPanel.setLayout(tempLayout);
                        Energizer temp = new Energizer();
                        tempPanel.add(temp);
 
                    }
                    else{
                         
                        tempPanel = new JPanel();
                        tempPanel.setBackground(Color.getHSBColor(0, 0, 0));
                    }
                     
                    mazeTableCase[i][ii] = tempPanel;
                    mazeWin.add(tempPanel);
                }
            }
             
            this.revalidate();
            this.repaint();
            return;
        }
         
        for (int i = 0; i < maze.length; i++){
            for (int ii = 0; ii < maze[i].length; ii++){
                 
                if (maze[i][ii] == 0){
                     
                    mazeTableCase[i][ii].setBackground(Color.getHSBColor(0, 0, 0));
                }
                else if (maze[i][ii] == 1){
                 
                    mazeTableCase[i][ii].removeAll();
                    mazeTableCase[i][ii].setBackground(Color.getHSBColor(0, 0, (float)0.4));
                }
                else if (maze[i][ii] == -1){
                    
                    mazeTableCase[i][ii].setBackground(Color.getHSBColor(0, 0, 1));
                }
                else if (maze[i][ii] == 2){
                     
                    mazeTableCase[i][ii].setBackground(Color.getHSBColor(0, 0, (float)0.15));
                }
                else if (maze[i][ii] == 3){
                     
                    mazeTableCase[i][ii].setBackground(Color.getHSBColor((float)(60/360.0), 1, 1));
                }
                else if (maze[i][ii] == 4){
                     
                    mazeTableCase[i][ii].setBackground(Color.getHSBColor(0, 1, 1));
                }
                else if (maze[i][ii] == -4){
                    
                    mazeTableCase[i][ii].setBackground(Color.getHSBColor(0, 1, (float)0.6));
                }
                else if (maze[i][ii] == 5){
                     
                    mazeTableCase[i][ii].setBackground(Color.getHSBColor((float)(300/360.0), (float)0.4, 1));
                }
                else if (maze[i][ii] == -5){
                    
                    mazeTableCase[i][ii].setBackground(Color.getHSBColor((float)(300/360.0), (float)0.4, (float)0.6));
                }
                else if (maze[i][ii] == 6){
                     
                    mazeTableCase[i][ii].setBackground(Color.getHSBColor((float)(220/360.0), 1, 1));
                }
                else if (maze[i][ii] == -6){
                    
                    mazeTableCase[i][ii].setBackground(Color.getHSBColor((float)(220/360.0), 1, (float)0.6));
                }
                else if (maze[i][ii] == 7){
                     
                    mazeTableCase[i][ii].setBackground(Color.getHSBColor((float)(30/360.0), 1, 1));
                }
                else if (maze[i][ii] == -7){
                    
                    mazeTableCase[i][ii].setBackground(Color.getHSBColor((float)(30/360.0), 1, (float)0.6));
                }
                else if (maze[i][ii] == 8){
                     
                    mazeTableCase[i][ii].setBackground(Color.getHSBColor(0, 0, (float)0.4));
                }
                else if (maze[i][ii] == 9){
                     
                    mazeTableCase[i][ii].setBackground(Color.getHSBColor(0, 0, (float)0.4));
                }
            }
        }
         
        this.revalidate();
        this.repaint();
    }
 

    /**
     * Update the event displayed in the view
     * 
     * @param life
     * @param score
     * @param isReset
     * @param lose
     * @param win
     */
    public void updateEvent(int life, int score, boolean isReset, boolean lose, boolean win) {
         
        /**
         * If we lose then reset the mazeWin panel, hide this frame and display the Main Window.
         * It also stop the thread to move Pacman.
         */
        if (lose)
        	reset();
        else if (isReset)
			try {
				server.sendMove("");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
         
        /**
         * If we win then reset the mazeWin panel, hide this frame and display the Main Window.
         * It also stop the thread to move Pacman.
         */
        if (win)
        	reset();
         
        this.life.setText(String.valueOf(life));
        this.score.setText(String.valueOf(score));
        
        this.repaint();
    }
     
    
    /**
     * Start the game by launching the display thread and the event thread
     */
    public void start(){
    	
    	displayRunnable = new DisplayRunnable(server, this);
    	eventRunnable = new EventRunnable(server, this);
    	
    	displayThread = new Thread(displayRunnable);
    	eventThread = new Thread(eventRunnable);
    	
    	displayThread.start();
    	eventThread.start();
    	
    	endThread = new Thread(){
    		
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					displayThread.join();
					eventThread.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				reset();
			}
    	};
    	
    	endThread.start();
    }
    
    /**
     * Close the server and the thread
     * Call a method from the Main Window to hide this panel and display another
     */
    public void reset(){
    	
    	endThread.interrupt();
    	try {
			endThread.join();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
    	server.close();
    	
    	displayRunnable.terminate();
    	eventRunnable.terminate();
    	
    	displayThread.interrupt();
    	eventThread.interrupt();
    	
    	System.out.println("JOIN THREAD");
    	try {
			displayThread.join();
			eventThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println("THREAD JOINED");
    	
    	mainWindow.resetGame();
    }

    /**
     * Close the server and the thread
     */
    public void close(){
    	
    	endThread.interrupt();
    	try {
			endThread.join();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
    	server.close();
    	
    	displayRunnable.terminate();
    	eventRunnable.terminate();
    	
    	displayThread.interrupt();
    	eventThread.interrupt();
    	
    	try {
			displayThread.join();
			eventThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
     
    /**
     * Draw in a panel a dot.
     * @author Touhead
     *
     */
    private class Dot extends JPanel{
 
        /**
         * 
         */
        private static final long serialVersionUID = 1L;
 
        public void paintComponent(Graphics g){
                super.paintComponent(g);
                g.setColor(Color.YELLOW);
                g.fillOval(this.getWidth()/2 - this.getHeight()/8, this.getHeight()/2 - this.getHeight()/8, this.getHeight()/4, this.getHeight()/4);
                this.setBackground(Color.GRAY);
                super.setOpaque(false);
                this.setOpaque(false);
         }
    }
     
    /**
     * Draw in a panel a energizer.
     * @author Touhead
     *
     */
    private class Energizer extends JPanel{
 
        /**
         * 
         */
        private static final long serialVersionUID = 1L;
 
        public void paintComponent(Graphics g){
                super.paintComponent(g);
                g.setColor(Color.YELLOW);
                g.fillOval(this.getWidth()/2 - this.getHeight()/4, this.getHeight()/2 - this.getHeight()/4, this.getHeight()/2, this.getHeight()/2);
                this.setBackground(Color.GRAY);
                super.setOpaque(false);
                this.setOpaque(false);
         }
    }
}