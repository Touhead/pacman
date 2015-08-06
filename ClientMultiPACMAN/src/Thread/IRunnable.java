package Thread;

/**
 * 
 * This interface extends runnable
 * 
 * @author touhead
 *
 */
public interface IRunnable extends Runnable {

	/**
	 * This method stop the infinite loop, but do not interrupt the runnable
	 * Thus if the runnable is blocked in the loop, the runnable will not reach
	 * the end of the run() method
	 */
	public void terminate();
	/**
	 * @return true if the infinite loop is activated, false otherwise
	 */
	public boolean isRunning();
}
