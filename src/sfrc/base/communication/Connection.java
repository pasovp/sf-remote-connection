/**
 * 
 */
package sfrc.base.communication;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author Luigi Pasotti
 * 
 */
public class Connection {
	
	private Socket socket = null;
	private String host = null;
	private InetAddress address = null;
	private int port;
	
	/**
	 * @param address the IP address.
	 * @param port the port number.
	 */
	public Connection(InetAddress address, int port) {
		super();
		this.address = address;
		this.port = port;
	}

	/**
	 * @param host the host name, or null for the loopback address.
	 * @param port the port number.
	 */
	public Connection(String host, int port) throws UnknownHostException {
		super();
		setHost(host);
		this.port = port;
	}
	
	public Connection(Socket socket){
		this.socket = socket;
		this.port = this.socket.getPort();
		this.address = this.socket.getInetAddress();
		this.host = this.address.getHostName();
	}
	
	/**
	 * Open a socket to the host/address specified by the constructor.
	 * @throws IOException
	 */
	public void openConnection() throws IOException {
		try {
			socket = new Socket(address, port);
		} catch (IOException e) {
			System.err.println("Couldn't open a socket for desired address/host.");
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * Close the connection socket.
	 */
	public void closeConnection() throws IOException {
		if((socket!=null)&&(!socket.isClosed())){
			try {
				socket.shutdownInput();
				socket.shutdownOutput();
				socket.close();
			} catch (IOException e) {
				System.err.println("An I/O error occurs when closing this socket.");
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @return the status of the connection.
	 */
	public ConnectionStatus getStatus(){
		//TODO: do a serious check on the connection status
		return ConnectionStatus.SOCKET_CONNECTED;
	}
	
	/**
	 * @return 
	 * @throws IOException
	 */
	public InputStream getInputStream() throws IOException {
		InputStream input = null;
		try {
			if (this.socket!=null) {
				input = this.socket.getInputStream();
			}
		} catch (IOException e) {
			System.err.println("Couldn't get an InputStream from socket.");
			e.printStackTrace();
			throw e;
		}
		return input;
	}
	
	/**
	 * @return
	 * @throws IOException
	 */
	public OutputStream getOutputStream() throws IOException{
		OutputStream output = null;
		try {
			if (this.socket!=null) {
				output = this.socket.getOutputStream();
			}
		} catch (IOException e) {
			System.err.println("Couldn't get an OutputStream from socket.");
			e.printStackTrace();
			throw e;
		}
		return output;
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(String host) throws UnknownHostException {
		this.host = host;
		try {
			this.address = InetAddress.getByName(this.host);
		} catch (UnknownHostException e) {
			System.err.println("Invalid host name.");
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * @return the address
	 */
	public InetAddress getAddress() {
		return this.address;
	}

	/**
	 * Returns the remote port to which this socket is connected. 
	 * @return the port the remote port number to which this socket is connected, or 0 if the socket is not connected yet.
	 */
	public int getPort() {
		return this.port;
	}
	
	
}
