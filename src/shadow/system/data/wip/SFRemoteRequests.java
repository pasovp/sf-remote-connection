package shadow.system.data.wip;

import java.util.ArrayList;
import java.util.HashMap;

import shadow.system.data.SFDataCenter;
import shadow.system.data.SFDataCenterListener;
import shadow.system.data.SFDataset;

public class SFRemoteRequests {
	private ArrayList<String> requests = new ArrayList<String>();
	private HashMap<String, ArrayList<SFDataCenterListener<SFDataset>>> listeners = new HashMap<String, ArrayList<SFDataCenterListener<SFDataset>>>();
	
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
	
	public synchronized <T extends SFDataset> void addUpdateListener(String name, SFDataCenterListener<T> listener) {
		if(this.listeners.get(name) == null) {
			this.listeners.put(name, new ArrayList<SFDataCenterListener<SFDataset>>());
		}
		this.listeners.get(name).add((SFDataCenterListener<SFDataset>)listener);
		System.err.println("Time:" + System.currentTimeMillis() + " Listener for:"+ name);
	}
	
	public synchronized void onRequestUpdate(String name) {
		ArrayList<SFDataCenterListener<SFDataset>> listenersList = this.listeners.get(name);
		
		
		if (listenersList != null) {
			for (SFDataCenterListener<SFDataset> listener : listenersList ) {
				listener.onDatasetAvailable(name, ((SFRemoteDataCenter)SFDataCenter.getDataCenter().getDataCenterImplementation()).getUpdatedDataset(name));
				System.err.println("Time:" + System.currentTimeMillis() + " Update for:"+ name);
			}
			listenersList.clear();
			//this.listeners.remove(name);
		}
	}
	
	public synchronized boolean updatePending(String name) {
		ArrayList<SFDataCenterListener<SFDataset>> listenersList = this.listeners.get(name);
		if ((listenersList != null) && (listenersList.size()>0)) {
			return true;
		}
		return false;
	}
	
}
