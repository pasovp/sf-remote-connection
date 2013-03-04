package shadow.system.data.wip;

import shadow.system.data.SFDataset;

public interface IRequest {
	public void executeRequest(String name, SFDataset dataset);
	public void failedRequest(String name);
}
