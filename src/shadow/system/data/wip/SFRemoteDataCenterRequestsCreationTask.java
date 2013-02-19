package shadow.system.data.wip;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import shadow.system.data.SFDataCenter;

/**
 * @author Luigi Pasotti
 *
 */
public class SFRemoteDataCenterRequestsCreationTask implements Runnable {
	SFRemoteRequests requests;
	private ExecutorService threadExecutor;
	
	
	public SFRemoteDataCenterRequestsCreationTask() {
		this.requests = ((SFRemoteDataCenter)SFDataCenter.getDataCenter().getDataCenterImplementation()).getRequests();
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		while (true) {
			synchronized (requests) {
				if (requests.hasNewRequests()) {
					if (threadExecutor == null) {
						threadExecutor = Executors.newCachedThreadPool();
					}
					threadExecutor.execute(new SFRemoteDataCenterRequestTask());
				}
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
