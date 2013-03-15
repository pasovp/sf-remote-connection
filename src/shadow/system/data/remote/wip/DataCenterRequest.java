package shadow.system.data.remote.wip;

import shadow.system.data.SFDataCenterListener;
import shadow.system.data.SFDataset;

public class DataCenterRequest implements IRequest{

	private SFDataCenterListener<SFDataset> listener;
	private IFailedRequestListener fListener;

	public DataCenterRequest(SFDataCenterListener<SFDataset> listener, IFailedRequestListener fListener) {
		super();
		this.listener = listener;
		this.fListener = fListener;
	}
	
	public DataCenterRequest(SFDataCenterListener<SFDataset> listener) {
		super();
		this.listener = listener;
		this.fListener = null;
	}

	@Override
	public void executeRequest(String name, SFDataset dataset) {
		
		listener.onDatasetAvailable(name, dataset);
		System.err.println("Time:" + System.currentTimeMillis() + " Update for:"+ name);
	}

	@Override
	public void failedRequest(String name) {
		if(fListener!=null){
			fListener.onFailedRequest();
		}
		System.err.println("Time:" + System.currentTimeMillis() + " Failed Req for:"+ name);
	}
}
