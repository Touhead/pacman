package Exception;

/**
 * Exception is a .txt file is invalid.
 * @author Touhead
 *
 */
public class ExceptionInvalidFile extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8303697501349802163L;

	public ExceptionInvalidFile(){
		super("Invalid File");
	}
}
