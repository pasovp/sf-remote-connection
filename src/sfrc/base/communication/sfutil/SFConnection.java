package sfrc.base.communication.sfutil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import sfrc.base.communication.Connection;
import shadow.system.data.SFInputStream;
import shadow.system.data.SFOutputStream;
import shadow.system.data.java.SFIOExceptionKeeper;
import shadow.system.data.java.SFInputStreamJava;
import shadow.system.data.java.SFOutputStreamJava;

public class SFConnection extends Connection{

	public SFConnection(InetAddress address, int port) {
		super(address, port);
		// TODO Auto-generated constructor stub
	}

	public SFConnection(String host, int port) throws UnknownHostException {
		super(host, port);
		// TODO Auto-generated constructor stub
	}

	public SFConnection(Socket socket) {
		super(socket);
		// TODO Auto-generated constructor stub
	}
	
	public SFInputStream getSFInputStream() throws IOException{
		SFInputStream input = null;
		try {
			InputStream stream = this.getInputStream();
			input = new SFInputStreamJava(stream, new SFIOExceptionKeeper() {
				@Override
				public void launch(IOException exception) {
					System.err.println("Couldn't create an SFInputStream from ImputStream.");
					exception.printStackTrace();
				}
			});
		} catch (IOException e) {
			System.err.println("Couldn't get an InputStream from socket.");
			e.printStackTrace();
			throw e;
		}
		return input;
	}
	
	public SFOutputStream getSFOutputStream() throws IOException{
		SFOutputStream output = null;
		try {
			OutputStream stream = this.getOutputStream();
			output = new SFOutputStreamJava(stream, new SFIOExceptionKeeper() {
				
				@Override
				public void launch(IOException exception) {
					System.err.println("Couldn't create an SFOutputStream from OutputStream.");
					exception.printStackTrace();
				}
			});
		} catch (IOException e) {
			System.err.println("Couldn't get an OutputStream from socket.");
			e.printStackTrace();
			throw e;
		}
		return output;
	}

}
