/**
 * 
 */
package shadow.underdevelopment;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import shadow.system.data.SFInputStream;
import shadow.system.data.SFOutputStream;
import shadow.system.data.java.SFIOExceptionKeeper;
import shadow.system.data.java.SFInputStreamJava;
import shadow.system.data.java.SFOutputStreamJava;

/**
 * @author Luigi Pasotti
 * 
 */
public class SFClientConnection {
	
	private Socket socket = null;
	private String host = null;
	private InetAddress address = null;
	private int port;
	
	/**
	 * @param address the IP address.
	 * @param port the port number.
	 */
	public SFClientConnection(InetAddress address, int port) {
		super();
		this.address = address;
		this.port = port;
	}

	/**
	 * @param host the host name, or null for the loopback address.
	 * @param port the port number.
	 */
	public SFClientConnection(String host, int port) throws UnknownHostException {
		super();
		setHost(host);
		this.port = port;
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
	 * @throws IOException
	 */
	public void closeConnection() throws IOException {
		if((socket!=null)&&(!socket.isClosed())){
			try {
				socket.close();
			} catch (IOException e) {
				System.err.println("An I/O error occurs when closing this socket.");
				e.printStackTrace();
				throw e;
			}
		}
	}
	
	/**
	 * @return the status of the connection.
	 */
	public SFClientConnectionStatus getStatus(){
		//TODO: do a serious check on the connection status
		return SFClientConnectionStatus.SOCKET_CONNECTED;
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
	
	public SFInputStream getSFInputStream() throws IOException{
		SFInputStream input = null;
		try {
			if (this.socket!=null) {
				InputStream stream = this.socket.getInputStream();
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
	
	public SFOutputStream getSFOutputStream() throws IOException{
		SFOutputStream output = null;
		try {
			if (this.socket!=null) {
				OutputStream stream = this.socket.getOutputStream();
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

//	/**
//	 * @param address the address to set
//	 */
//	public void setAddress(InetAddress address) {
//		//TODO:Must create a new SocketAddress with the address and the port number
//	}

	/**
	 * Returns the remote port to which this socket is connected. 
	 * @return the port the remote port number to which this socket is connected, or 0 if the socket is not connected yet.
	 */
	public int getPort() {
		return this.port;
	}
	
	
}
