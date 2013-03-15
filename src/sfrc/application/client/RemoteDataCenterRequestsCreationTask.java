package sfrc.application.client;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import shadow.system.data.remote.wip.SFRemoteDataCenterRequests;

/**
 * @author Luigi Pasotti
 *
 */
public class RemoteDataCenterRequestsCreationTask implements Runnable {
	private ExecutorService threadExecutor = Executors.newCachedThreadPool();

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		while (true) {
			if (SFRemoteDataCenterRequests.getRequest().hasNewRequests()) {
				threadExecutor.execute(new RemoteDataCenterRequestTask());
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
