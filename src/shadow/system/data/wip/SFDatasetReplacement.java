/**
 * 
 */
package shadow.system.data.wip;

import shadow.renderer.data.SFAssetModule;
import shadow.system.data.SFNamedParametersObject;
import shadow.system.data.object.wip.SFLong;
import shadow.system.data.objects.SFString;

/**
 * @author luigi
 *
 */
public class SFDatasetReplacement extends SFAssetModule {
	
	private SFString replacement = new SFString("");
	private SFLong timestamp = new SFLong(0);
	
	public SFDatasetReplacement() {
		SFNamedParametersObject parameters=new SFNamedParametersObject();
		parameters.addObject("replacement", this.replacement);
		parameters.addObject("timestamp", this.timestamp);
		setData(parameters);
	}
	
	public SFDatasetReplacement(String replacement, Long timestamp) {
		this();
		this.setReplacement(replacement, timestamp);
		
	}
	
	public void setReplacement(String replacement, Long timestamp) {
		this.replacement.setString(replacement);
		this.timestamp.setLongValue(timestamp);
	}
	
	public String getReplacementName() {
		return replacement.getString();
	}
	
	public long getTimestampValue(){
		return timestamp.getLongValue();
	}

}
