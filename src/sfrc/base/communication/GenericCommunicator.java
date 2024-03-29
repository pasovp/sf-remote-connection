package sfrc.base.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class GenericCommunicator {

	private Connection connection;
	private CommunicatorExceptionListener listener = null;

	public GenericCommunicator(Connection connection) {
		this.connection = connection;
	}
	
	public GenericCommunicator(Connection connection, CommunicatorExceptionListener listener) {
		this.connection = connection;
		this.listener = listener;
	}

	/**
	 * Reads a line of text. A line is considered to be terminated by any one of a line feed ('\n'), a carriage return ('\r'), or a carriage return followed immediately by a linefeed.
	 * @return A String containing the contents of the line, not including any line-termination characters, or null if the end of the stream has been reached 
	 */
	public String readLine() {
		
		BufferedReader in;
		String line = null;
		try {
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			line = in.readLine();
		} catch (IOException e) {
			handleIOException(e);
		}
		return line;
	}

	public void sendLine(String line) {
		PrintWriter out;
		try {
			out = new PrintWriter(connection.getOutputStream(), true);
			out.println(line);
		} catch (IOException e) {
			handleIOException(e);
		}
	}

	public void closeCommunication() {
		try {
			connection.closeConnection();
		} catch (IOException e) {
			handleIOException(e);
		}
	}
	
	public void handleIOException(IOException e){
		e.printStackTrace();
		if (listener!=null) {
			listener.exceptionCatched(e);
		}
	}

	/**
	 * @return the connection
	 */
	public Connection getConnection() {
		return connection;
	}

	/**
	 * @param connection the connection to set
	 */
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	/**
	 * @return the listener
	 */
	public CommunicatorExceptionListener getListener() {
		return listener;
	}

}