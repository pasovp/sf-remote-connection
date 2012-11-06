/**
 * 
 */
package shadow.application.client;

/**
 * @author Luigi Pasotti
 * A listener of exception for the ClientCommunicator.
 */
public interface ClientCommunicatorExceptionListener {
	
	/**
	 * Callback for a catched exception in ClienComunicator
	 * @param e the exception catched
	 */
	public void exceptionCatched(Exception e);
}
