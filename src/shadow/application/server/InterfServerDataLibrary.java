/**
 * 
 */
package shadow.application.server;

import shadow.system.data.SFDataset;

/**
 * @author Luigi Pasotti
 *
 */
public interface InterfServerDataLibrary {
	/**
	 * @param datasetName The name of the dataset;
	 * @return The SFDataset
	 */
	public SFDataset getDataset(String datasetName);
}
