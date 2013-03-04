/**
 * 
 */
package shadow.underdevelopment;

/**
 * @author Luigi Pasotti
 * A listener of exception for the {@link GenericCommunicator}.
 */
public interface CommunicatorExceptionListener {
	
	/**
	 * Callback for a catched exception in {@link GenericCommunicator}
	 * @param e the exception catched
	 */
	public void exceptionCatched(Exception e);
}
