package Model;

import java.util.ArrayList;

import Exception.ExceptionInvalidFile;
import Observer.Observable;
import Observer.Observer;

/** 
 * Abstract Multi Model to link with the Controller.
 * 
 * @author Touhead
 * @version 1.0
 * */
public abstract class AbstractMultiModel implements Observable{

	private ArrayList<Observer> listObserver;
	
	public AbstractMultiModel() {
		super();
		listObserver = new ArrayList<Observer>();
	}

	public abstract boolean gameSetUp(String fileName) throws ExceptionInvalidFile;
	
	public abstract boolean move(String move, String moveBlinky, String movePinky);
		
	public void addObserver(Observer obs) {
		
		this.listObserver.add(obs);
	}
	
	public void removeObserver() {
		
		listObserver = new ArrayList<Observer>();
	}

	public ArrayList<Observer> getListObserver() {
		return listObserver;
	}
}
