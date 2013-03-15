package shadow.system.data.remote.wip;

import shadow.system.data.SFDataset;

public interface IRequest {
	public void executeRequest(String name, SFDataset dataset);
	public void failedRequest(String name);
}
