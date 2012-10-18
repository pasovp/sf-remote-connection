/**
 * 
 */
package shadow.underdevelopment;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Luigi Pasotti
 *
 */
public class SFServerConnection {
	private ServerSocket serverSocket = null;
	private Socket clientSocket = null;
	
	public SFServerConnection(){
		
	}
	
	public void openConnections(int port) throws IOException{
		try{
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			System.err.println("Could not listen on selected port.");
			e.printStackTrace();
			throw e;
		}
		try{
			clientSocket = serverSocket.accept();
		} catch (IOException e) {
			System.err.println("Accept failed");
			e.printStackTrace();
			throw e;
		}
	}
	
	public void closeConnection() throws IOException {
		clientSocket.close();
		serverSocket.close();
	}
	
	public InputStream getInputStream() throws IOException {
		InputStream input = null;
		try {
			input = this.clientSocket.getInputStream();
		} catch (IOException e) {
			System.err.println("Couldn't get an InputStream from socket.");
			e.printStackTrace();
			throw e;
		}
		return input;
	}
	
	public OutputStream getOutputStream() throws IOException{
		OutputStream output = null;
		try {
			output = this.clientSocket.getOutputStream();
		} catch (IOException e) {
			System.err.println("Couldn't get an OutputStream from socket.");
			e.printStackTrace();
			throw e;
		}
		return output;
	}

}
