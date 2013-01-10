/**
 * 
 */
package shadow.system.data.wip;

import shadow.system.data.SFDataCenterListener;
import shadow.system.data.SFDataset;

/**
 * @author Luigi Pasotti
 * @param <T>
 *
 */
public interface SFUpdatableDatasetListener<T extends SFDataset> extends SFDataCenterListener<T> {
	
	public void onDatasetUpdate(String name, T dataset);
}
