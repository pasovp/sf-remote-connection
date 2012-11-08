/**
 * 
 */
package shadow.underdevelopment;

/**
 * @author Luigi Pasotti
 * A listener of exception for the ClientCommunicatorOld.
 */
public interface CommunicatorExceptionListener {
	
	/**
	 * Callback for a catched exception in ClienComunicator
	 * @param e the exception catched
	 */
	public void exceptionCatched(Exception e);
}
