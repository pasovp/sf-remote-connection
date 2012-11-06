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
public class ServerComunicationTask implements Runnable {
	private ServerComunicator comunicator;
	private InterfServerDataLibrary library;
	
	private static final int IDLE = 0;
	private static final int REPLY = 1;
	private static final int CLOSING = 2;
	private static final int CLOSE = 3;
	
	private int state = IDLE;
	
	/**
	 * @param connection
	 */
	public ServerComunicationTask(SFConnection connection, InterfServerDataLibrary library) {
		super();
		this.comunicator = new ServerComunicator(connection);
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
				String input = comunicator.readLine();
				if ((input==null)|| (input=="close")) {
					state = CLOSING;
				} else {
					StringTokenizer tokenizer = new StringTokenizer(input, ",");
					String token = tokenizer.nextToken();
					if (token=="request") {
						while (tokenizer.hasMoreTokens()) {
							token = tokenizer.nextToken();
							requests.add(token);
							state = REPLY;
						}
					}
				}
				break;
			
			case REPLY:
				while (!requests.isEmpty()) {
					String datasetName = requests.remove(0);
					SFDataset dataset = library.getDataset(datasetName);
					if (dataset!=null) {
						comunicator.sendLine(datasetName);
						comunicator.sendDataset(dataset);
					} else {
						comunicator.sendLine("fail," + datasetName);
					}
				}
				comunicator.sendLine("idle");
				state = IDLE;
				
				break;
				
			case CLOSING:
				comunicator.closeComunication();
				state = CLOSE;
				break;

			default:
				break;
			}
		}
		

	}

}
