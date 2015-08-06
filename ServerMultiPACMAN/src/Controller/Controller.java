package Controller;

import Exception.ExceptionInvalidFile;
import Model.AbstractModel;
import Model.AbstractMultiModel;

/**
 * Controller to check the validity of the View's input.
 * @author Touhead
 *
 */
public class Controller extends AbstractController{

	public Controller(AbstractModel model) {
		super(model);
	}
	
	public Controller(AbstractMultiModel modelMulti) {
		super(modelMulti);
	}

	@Override
	public void controlMove(){
		
		if (getListMove().contains(getMove())){
			
			getModel().movePacman(getMove());
			resetMove();
		}
	}
	
	@Override
	public void controlMoveMulti(){
		
		if ((getListMove().contains(getMove()))
			&&(getListMove().contains(getMoveBlinky()))
			&&(getListMove().contains(getMovePinky()))){
			
			getModelMulti().move(getMove(), getMoveBlinky(), getMovePinky());
			resetMove();
		}
	}
	
	@Override
	public void controlCmd() throws ExceptionInvalidFile {

		if ((getCmd() == "START")&&(!getFileName().isEmpty())){
			
			getModel().gameSetUp(getFileName());
			resetCmd();
		}
	}
	
	@Override
	public void controlCmdMulti() throws ExceptionInvalidFile {

		if ((getCmd() == "START")&&(!getFileName().isEmpty())){
			
			getModelMulti().gameSetUp(getFileName());
			resetCmd();
		}
	}
}
