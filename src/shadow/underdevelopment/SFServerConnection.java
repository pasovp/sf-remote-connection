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
 *
 */
public class SFServerConnection {
	private ServerSocket serverSocket = null;
	private Socket clientSocket = null;
	private int port;
	
	public SFServerConnection(int port){
		this.port = port;
	}
	
	//TODO: architettura di risposta asincrona
	public void openConnections() throws IOException{
		try{
			serverSocket = new ServerSocket(this.port);
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
		try {
			clientSocket.close();
			serverSocket.close();
		} catch (IOException e) {
			System.err.println("An I/O error occurs when closing this socket.");
			e.printStackTrace();
			throw e;
		}
	}
	
	public SFServerConnectionStatus getStatus(){
		//TODO: do a serious check on the connection status
		return SFServerConnectionStatus.SOCKET_CONNECTED;
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

}
