/**
 * 
 */
package shadow.system.data.wip;

import shadow.system.data.SFDataCenterListener;
import shadow.system.data.SFDataset;

/**
 * Interfaccia inutile, ho aggirato il problema delle notifiche
 * @author Luigi Pasotti
 * @param <T>
 *
 */
@Deprecated
public interface SFUpdatableDatasetListener<T extends SFDataset> extends SFDataCenterListener<T> {
	
	public void onDatasetUpdate(String name, T dataset);
}
