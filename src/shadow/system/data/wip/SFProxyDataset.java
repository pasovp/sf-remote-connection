/**
 * 
 */
package shadow.system.data.wip;

import shadow.system.data.SFDataObject;
import shadow.system.data.SFDataset;

/**
 * @author Luigi Pasotti
 *
 */
public class SFProxyDataset implements SFDataset {
	
	private SFDataset dataset;
	/**
	 * 
	 */
	public SFProxyDataset() {
		super();
		this.dataset = new SFDataset() {
			
			@Override
			public void invalidate() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public String getType() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public SFDataObject getSFDataObject() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public SFDataset generateNewDatasetInstance() {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}

	/**
	 * @param dataset
	 */
	public void setSFDataset(SFDataset dataset) {
		this.dataset = dataset;
	}

	/* (non-Javadoc)
	 * @see shadow.system.data.SFDataset#getType()
	 */
	@Override
	public String getType() {
		return dataset.getType();
	}

	/* (non-Javadoc)
	 * @see shadow.system.data.SFDataset#getSFDataObject()
	 */
	@Override
	public SFDataObject getSFDataObject() {
		return dataset.getSFDataObject();
	}

	/* (non-Javadoc)
	 * @see shadow.system.data.SFDataset#generateNewDatasetInstance()
	 */
	@Override
	public SFDataset generateNewDatasetInstance() {
		return dataset.generateNewDatasetInstance();
	}

	/* (non-Javadoc)
	 * @see shadow.system.data.SFDataset#invalidate()
	 */
	@Override
	public void invalidate() {
		dataset.invalidate();
	}

}
