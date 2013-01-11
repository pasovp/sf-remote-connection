package shadow.system.data.wip;

import java.util.ArrayList;
import java.util.HashMap;

import javax.sql.rowset.spi.SyncResolver;

import shadow.system.data.SFDataCenter;
import shadow.system.data.SFDataCenterListener;
import shadow.system.data.SFDataset;

public class SFRemoteRequests {
	private ArrayList<String> requests = new ArrayList<String>();
	@Deprecated private HashMap<String, ArrayList<SFUpdatableDatasetListener<SFDataset>>> listenersOld = new HashMap<String, ArrayList<SFUpdatableDatasetListener<SFDataset>>>();
	private HashMap<String, ArrayList<SFDataCenterListener<SFDataset>>> listeners = new HashMap<String, ArrayList<SFDataCenterListener<SFDataset>>>();
	
	public synchronized void addRequest(String name) {
		this.requests.add(name);
	}
	
	public synchronized ArrayList<String> getRequests() {
		ArrayList<String> tmp = new ArrayList<String>(this.requests);
		this.requests.clear();
		return tmp;
	}
	
	public synchronized boolean hasNewRequests() {
		return !this.requests.isEmpty();
	}
	
//	public synchronized <T extends SFDataset> void addUpdatableRequest(String name, SFUpdatableDatasetListener<T> listener) {
//		this.requests.add(name);
//		if(this.listeners.get(name) == null) {
//			this.listeners.put(name, new ArrayList<SFUpdatableDatasetListener<SFDataset>>());
//		}
//		this.listeners.get(name).add((SFUpdatableDatasetListener<SFDataset>)listener);
//	}
	@SuppressWarnings("unchecked")
	@Deprecated
	public synchronized <T extends SFDataset> void addUpdateListenerOld(String name, SFUpdatableDatasetListener<T> listener) {
		if(this.listenersOld.get(name) == null) {
			this.listenersOld.put(name, new ArrayList<SFUpdatableDatasetListener<SFDataset>>());
		}
		this.listenersOld.get(name).add((SFUpdatableDatasetListener<SFDataset>)listener);
	}
	
	@Deprecated
	public synchronized void onRequestUpdateOld(String name) {
		ArrayList<SFUpdatableDatasetListener<SFDataset>> listenersList = this.listenersOld.get(name);
		
		if (listenersList != null) {
			for (SFUpdatableDatasetListener<SFDataset> listener : listenersList ) {
				listener.onDatasetUpdate(name, SFDataCenter.getDataCenter().getAlreadyAvailableDataset(name));
			}
			listenersList.clear();
		}
	}
	
	public synchronized <T extends SFDataset> void addUpdateListener(String name, SFDataCenterListener<T> listener) {
		if(this.listeners.get(name) == null) {
			this.listeners.put(name, new ArrayList<SFDataCenterListener<SFDataset>>());
		}
		this.listeners.get(name).add((SFDataCenterListener<SFDataset>)listener);
	}
	
	public synchronized void onRequestUpdate(String name) {
		ArrayList<SFDataCenterListener<SFDataset>> listenersList = this.listeners.get(name);
		
		if (listenersList != null) {
			for (SFDataCenterListener<SFDataset> listener : listenersList ) {
				listener.onDatasetAvailable(name, SFDataCenter.getDataCenter().getAlreadyAvailableDataset(name));
			}
			listenersList.clear();
		}
	}
	
}
