package Model;

import java.util.ArrayList;

import Exception.ExceptionInvalidFile;
import Observer.Observable;
import Observer.Observer;

/** 
 * Abstract Model to link with the Controller.
 * 
 * @author Touhead
 * @version 1.0
 * */
public abstract class AbstractModel implements Observable{

	private ArrayList<Observer> listObserver;
	
	public AbstractModel() {
		super();
		listObserver = new ArrayList<Observer>();
	}

	public abstract boolean gameSetUp(String fileName) throws ExceptionInvalidFile;
	
	public abstract boolean movePacman(String move);
		
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
