/**
 * 
 */
package shadow.application.client;

/**
 * @author Luigi Pasotti
 * A listener of exception for the ClientComunicator.
 */
public interface ClientComunicatorExceptionListener {
	
	/**
	 * Callback for a catched exception in ClienComunicator
	 * @param e the exception catched
	 */
	public void exceptionCatched(Exception e);
}
