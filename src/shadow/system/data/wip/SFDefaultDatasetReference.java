/**
 * 
 */
package shadow.system.data.wip;

import shadow.system.data.SFDataObject;
import shadow.system.data.SFDataset;
import shadow.system.data.SFInputStream;
import shadow.system.data.SFOutputStream;
import shadow.system.data.object.wip.SFLong;
import shadow.system.data.objects.SFString;

/**
 * @author Luigi Pasotti
 *
 */
public class SFDefaultDatasetReference implements SFDataset, SFDataObject {
	
	private SFString name;
	private SFLong timestamp;
	
	public SFDefaultDatasetReference() {
		super();
		name = new SFString("");
		timestamp = new SFLong(0);
	}
	
	public SFDefaultDatasetReference(String name, long timestamp) {
		super();
		this.name = new SFString(name);
		this.timestamp = new SFLong(timestamp);
	}
	
	/**
	 * @return the name
	 */
	public SFString getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(SFString name) {
		this.name = name;
	}

	/**
	 * @return the timestamp
	 */
	public SFLong getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(SFLong timestamp) {
		this.timestamp = timestamp;
	}
	
	/* (non-Javadoc)
	 * @see shadow.system.data.SFDataObject#clone()
	 */
	@Override
	public SFDefaultDatasetReference clone() {
		return new SFDefaultDatasetReference(this.name.getString(),this.timestamp.getLongValue());
	}
	
	/* (non-Javadoc)
	 * @see shadow.system.data.SFDataObject#readFromStream(shadow.system.data.SFInputStream)
	 */
	@Override
	public void readFromStream(SFInputStream stream) {
		name.readFromStream(stream);
		timestamp.readFromStream(stream);
	}

	/* (non-Javadoc)
	 * @see shadow.system.data.SFDataObject#writeOnStream(shadow.system.data.SFOutputStream)
	 */
	@Override
	public void writeOnStream(SFOutputStream stream) {
		name.writeOnStream(stream);
		timestamp.writeOnStream(stream);
	}

	/* (non-Javadoc)
	 * @see shadow.system.data.SFDataset#getType()
	 */
	@Override
	public String getType() {
		return this.getClass().getSimpleName();
	}

	/* (non-Javadoc)
	 * @see shadow.system.data.SFDataset#getSFDataObject()
	 */
	@Override
	public SFDataObject getSFDataObject() {
		return this;
	}

	/* (non-Javadoc)
	 * @see shadow.system.data.SFDataset#generateNewDatasetInstance()
	 */
	@Override
	public SFDataset generateNewDatasetInstance() {
		return new SFDefaultDatasetReference();
	}

	/* (non-Javadoc)
	 * @see shadow.system.data.SFDataset#invalidate()
	 */
	@Override
	public void invalidate() {
		// TODO Auto-generated method stub
	}

}
