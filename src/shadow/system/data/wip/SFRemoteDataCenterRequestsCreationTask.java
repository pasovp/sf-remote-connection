/**
 * 
 */
package shadow.system.data.wip;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Luigi Pasotti
 *
 */
public class SFRemoteDataCenterRequestsCreationTask implements Runnable {
	private HashMap<String, SFProxyDataset> requests;
	private ExecutorService threadExecutor;
	

	public SFRemoteDataCenterRequestsCreationTask(HashMap<String, SFProxyDataset> requests) {
		this.requests = requests;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		synchronized (requests) {
			if (!requests.isEmpty()) {
				if (threadExecutor == null) {
					threadExecutor = Executors.newCachedThreadPool();
				}
				threadExecutor.execute(new SFRemoteDataCenterRequestTask(requests));
			}
		}
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
