package Controller;

import java.util.ArrayList;

import Exception.ExceptionInvalidFile;
import Model.AbstractModel;
import Model.AbstractMultiModel;

/**
 * Abstract Controller to link the Model and the View.
 * @author Touhead
 *
 */
public abstract class AbstractController {

	private AbstractModel model;
	private AbstractMultiModel modelMulti;
	private String move;
	private String moveBlinky;
	private String movePinky;
	private ArrayList<String> listMove;
	private String cmd;
	private String fileName;

	public AbstractController(AbstractModel model) {
		super();
		this.model = model;
		move = "";
		listMove = new ArrayList<String>();
		listMove.add("65");
		listMove.add("81");
		listMove.add("37");
		listMove.add("83");
		listMove.add("40");
		listMove.add("68");
		listMove.add("39");
		listMove.add("87");
		listMove.add("90");
		listMove.add("38");
		cmd = "";
		fileName = "";
	}
	
	public AbstractController(AbstractMultiModel modelMulti) {
		super();
		this.modelMulti = modelMulti;
		move = "";
		listMove = new ArrayList<String>();
		listMove.add("65");
		listMove.add("81");
		listMove.add("37");
		listMove.add("83");
		listMove.add("40");
		listMove.add("68");
		listMove.add("39");
		listMove.add("87");
		listMove.add("90");
		listMove.add("38");
		cmd = "";
		fileName = "";
	}

	public AbstractModel getModel() {
		return model;
	}
	
	public AbstractMultiModel getModelMulti() {
		return modelMulti;
	}

	public void setModel(AbstractModel model) {
		this.model = model;
	}
	
	public void setModelMulti(AbstractMultiModel modelMulti) {
		this.modelMulti = modelMulti;
	}

	public String getMove() {
		return move;
	}
	
	public String getMoveBlinky() {
		return moveBlinky;
	}
	
	public String getMovePinky() {
		return movePinky;
	}
	
	public void setMove(String move) {
		
		this.move = move;
		controlMove();
	}
	
	public void setMove(String move, String moveBlinky, String movePinky) {
		
		this.move = move;
		this.moveBlinky = moveBlinky;
		this.movePinky = movePinky;
		controlMoveMulti();
	}

	public void resetMove() {
		 move = "";
	}
	
	public void resetMoveMutli() {
		 move = "";
		 moveBlinky = "";
		 movePinky = "";
	}

	public ArrayList<String> getListMove() {
		return listMove;
	}	
	
	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) throws ExceptionInvalidFile {
		this.cmd = cmd;
		controlCmd();
	}
	
	public void setCmdMulti(String cmd) throws ExceptionInvalidFile {
		this.cmd = cmd;
		controlCmdMulti();
	}
	
	public void resetCmd() {
		cmd = "";
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	abstract void controlMove();
	
	abstract void controlMoveMulti();
	
	abstract void controlCmd() throws ExceptionInvalidFile;
	
	abstract void controlCmdMulti() throws ExceptionInvalidFile;
}
