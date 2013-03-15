/**
 * 
 */
package sfrc.base.communication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Luigi Pasotti
 * A class for handle the ServerSocket.
 * I really need this code? I don't know, but I don't want delete it...
 *
 */
public class SFServerConnection {
	private ServerSocket serverSocket = null;
	private int port;
	
	public SFServerConnection(int port) throws IOException{
		this.port = port;
		try{
			serverSocket = new ServerSocket(this.port);
		} catch (IOException e) {
			System.err.println("Could not listen on selected port.");
			e.printStackTrace();
			throw e;
		}
	}
	
	public Socket acceptConnection() throws IOException {
		Socket clientSocket = null;
		try{
			clientSocket = serverSocket.accept();
		} catch (IOException e) {
			System.err.println("Accept failed");
			e.printStackTrace();
			throw e;
		}
		return clientSocket;
	}
	
	public SFServerConnectionStatus getStatus(){
		//TODO: do a serious check on the connection status
		return SFServerConnectionStatus.SOCKET_CONNECTED;
	}
	

}
