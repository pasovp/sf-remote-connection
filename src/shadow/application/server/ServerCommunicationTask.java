/**
 * 
 */
package shadow.application.server;

import java.util.ArrayList;
import java.util.StringTokenizer;

import shadow.system.data.SFDataset;
import shadow.underdevelopment.SFConnection;

/**
 * @author Luigi Pasotti
 *
 */
public class ServerCommunicationTask implements Runnable {
	private ServerCommunicator communicator;
	private InterfServerDataLibrary library;
	
	private static final int IDLE = 0;
	private static final int REPLY = 1;
	private static final int CLOSING = 2;
	private static final int CLOSE = 3;
	
	private int state = IDLE;
	
	/**
	 * @param connection
	 */
	public ServerCommunicationTask(SFConnection connection, InterfServerDataLibrary library) {
		super();
		this.communicator = new ServerCommunicator(connection);
		this.library = library;
	}


	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		ArrayList<String> requests = new ArrayList<String>();
		
		while (state != CLOSE) {
			switch (state) {
			case IDLE:
				System.out.println("state: idle");
				String input = communicator.readLine();
				System.out.println("input: "+input);
				if ( (input==null) || (input.compareTo("close") == 0) ) {	
					state = CLOSING;
				} else {
					StringTokenizer tokenizer = new StringTokenizer(input, ",");
					if (tokenizer.hasMoreTokens()) {
						String token = tokenizer.nextToken();
						if (token.compareTo("request") == 0) {
							while (tokenizer.hasMoreTokens()) {
								token = tokenizer.nextToken();
								requests.add(token);
								state = REPLY;
							}
						} else {
							System.out.println("error: not a request");
						}
					}
				}
				break;
			
			case REPLY:
				System.out.println("state: reply");
				while (!requests.isEmpty()) {
					String datasetName = requests.remove(0);
					SFDataset dataset = library.getDataset(datasetName);
					if (dataset!=null) {
						communicator.sendLine(datasetName);
						System.out.println("sending: "+datasetName);
						communicator.sendDataset(dataset);
						communicator.sendLine("");
						System.out.println("sent: "+datasetName);
					} else {
						communicator.sendLine("fail," + datasetName);
						System.out.println("fail: "+ datasetName);
					}
				}
				communicator.sendLine("idle");
				state = IDLE;
				
				break;
				
			case CLOSING:
				System.out.println("state: closing");
				communicator.closeComunication();
				state = CLOSE;
				break;

			default:
				break;
			}
		}
		

	}

}
