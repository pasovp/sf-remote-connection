package shadow.system.data.remote.wip;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Luigi Pasotti
 *
 */
public class SFRemoteDataCenterRequestsCreationTask implements Runnable {
	private ExecutorService threadExecutor = Executors.newCachedThreadPool();

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		while (true) {
			if (SFRemoteDataCenterRequests.getRequest().hasNewRequests()) {
				threadExecutor.execute(new SFRemoteDataCenterRequestTask());
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}