package shadow.system.data.remote.wip;

import java.util.ArrayList;
import java.util.HashMap;

import shadow.renderer.data.SFAsset;
import shadow.system.SFUpdatable;
import shadow.system.SFUpdater;
import shadow.system.data.SFDataCenter;
import shadow.system.data.SFDataset;

public class SFRemoteRequests<R extends IRequest> {

	private ArrayList<String> requests = new ArrayList<String>();
	private HashMap<String, ArrayList<R>> listeners = new HashMap<String, ArrayList<R>>();
	
	public synchronized void addRequest(String name) {
		this.requests.add(name);
	}
	
	public synchronized ArrayList<String> removeRequests() {
		ArrayList<String> tmp = new ArrayList<String>(this.requests);
		this.requests.clear();
		return tmp;
	}
	
	public synchronized boolean hasNewRequests() {
		return !this.requests.isEmpty();
	}
	
	public synchronized void addUpdateListener(String name, R listener) {
		if(this.listeners.get(name) == null) {
			this.listeners.put(name, new ArrayList<R>());
		}
		this.listeners.get(name).add(listener);
		System.err.println("Time:" + System.currentTimeMillis() + " Listener for:"+ name);
	}
	
	public synchronized void onRequestUpdate(String name, SFDataset dataset) {
		
		final ArrayList<R> listenersList = this.listeners.get(name);
		final String reqName = name;
		final SFDataset reqDataset = dataset;
		
		if (listenersList != null) {
			SFUpdater.addUpdatable(new SFUpdatable() {
				
				@Override
				public void update() {
					if(reqDataset instanceof SFAsset<?>) {
						((SFAsset<?>)reqDataset).getResource();
					}
					((SFRemoteDataCenter)SFDataCenter.getDataCenter().getDataCenterImplementation()).addDatasetToLibraty(reqName, reqDataset);
					
					for (R listener : listenersList ) {
						listener.executeRequest(reqName, reqDataset);
					}
					listenersList.clear();
				}
			});
		}
	}
	
	public synchronized void onRequestFail(String name){
		final ArrayList<R> listenersList = this.listeners.get(name);
		final String reqName = name;
		
		if (listenersList != null) {
			SFUpdater.addUpdatable(new SFUpdatable() {
				
				@Override
				public void update() {
					for (R listener : listenersList ) {
						listener.failedRequest(reqName);
					}
					listenersList.clear();
				}
			});
		}
	}
	
	public synchronized boolean updatePending(String name) {
		ArrayList<R> listenersList = this.listeners.get(name);
		if ((listenersList != null) && (listenersList.size()>0)) {
			return true;
		}
		return false;
	}
	
}
