package shadow.application.client;

import shadow.renderer.SFObjectModel;
import shadow.renderer.contents.tests.common.CommonMaterial;
import shadow.renderer.data.SFObjectModelData;
import shadow.renderer.viewer.SFViewer;
import shadow.system.data.SFDataCenterListener;

public class ClientDataCenterListener<T extends SFObjectModelData> implements SFDataCenterListener<SFObjectModelData> {
	//private SFObjectModel mainNode = new SFObjectModel();
	private boolean windowOpened = false;
	private SFViewer viewer;
	//private static int counter;
	
	public ClientDataCenterListener() {
	}
	
	@Override
	public synchronized void onDatasetAvailable(String name, SFObjectModelData dataset) {
		SFObjectModel model=(SFObjectModel)dataset.getResource();
		
		try {
			if(windowOpened == false) {
				viewer = SFViewer.generateFrame(model,
					CommonMaterial.generateColoursController(model),
					SFViewer.getLightStepController(),
					SFViewer.getRotationController(),
					SFViewer.getWireframeController(),
					SFViewer.getZoomController());
//				counter++;
//				if(counter>=2){
//					throw new RuntimeException("2 finestre");
//				}
				windowOpened = true;
			} else {
				viewer.setNode(model);
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
}
