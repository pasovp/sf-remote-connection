
package shadow.application.client.test;

import shadow.renderer.viewer.SFViewer;

/**
 * @author Luigi Pasotti
 *
 */
public class Test0002 extends AbstractClient{
	
	

	/**
	 * @param args
	 */
	public static void main(String[] args){
		execute(new Test0002());
	}

	@Override
	public void viewTestData() {
		viewNode("RedMushroom",true,SFViewer.getLightStepController());
	}
}
