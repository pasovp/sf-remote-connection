/**
 * 
 */
package shadow.underdevelopment;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import shadow.system.data.SFInputStream;
import shadow.system.data.SFOutputStream;
import shadow.system.data.java.SFIOExceptionKeeper;
import shadow.system.data.java.SFInputStreamJava;
import shadow.system.data.java.SFOutputStreamJava;

/**
 * @author Luigi Pasotti
 * A class for handle the ServerSocket.
 * I really need this code? I don't know, but I don't want delete it...
 *
 */
public class SFServerConnection {
	private ServerSocket serverSocket = null;
	//private Socket clientSocket = null;
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
	
	
	/* Trash?!?
	 * Don't need this
	 * 
	
	public void openConnection() throws IOException{
		
		try{
			clientSocket = serverSocket.accept();
		} catch (IOException e) {
			System.err.println("Accept failed");
			e.printStackTrace();
			throw e;
		}
	}
	
	public void closeConnection() throws IOException {
		try {
			clientSocket.close();
			//serverSocket.close();
		} catch (IOException e) {
			System.err.println("An I/O error occurs when closing this socket.");
			e.printStackTrace();
			throw e;
		}
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
	
	public SFInputStream getSFInputStream() throws IOException{
		SFInputStream input = null;
		try {
			if (this.clientSocket!=null) {
				InputStream stream = this.clientSocket.getInputStream();
				input = new SFInputStreamJava(stream, new SFIOExceptionKeeper() {
					
					@Override
					public void launch(IOException exception) {
						System.err.println("Couldn't create an SFInputStream from ImputStream.");
						exception.printStackTrace();
					}
				});
			}
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
	
	public SFOutputStream getSFOutputStream() throws IOException{
		SFOutputStream output = null;
		try {
			if (this.clientSocket!=null) {
				OutputStream stream = this.clientSocket.getOutputStream();
				output = new SFOutputStreamJava(stream, new SFIOExceptionKeeper() {
					
					@Override
					public void launch(IOException exception) {
						System.err.println("Couldn't create an SFInputStream from ImputStream.");
						exception.printStackTrace();
					}
				});
			}
		} catch (IOException e) {
			System.err.println("Couldn't get an InputStream from socket.");
			e.printStackTrace();
			throw e;
		}
		return output;
	}
	*/

}
