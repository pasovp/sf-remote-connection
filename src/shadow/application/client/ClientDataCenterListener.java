package shadow.application.client;

import shadow.system.data.SFDataCenterListener;
import shadow.renderer.SFObjectModel;
import shadow.renderer.contents.tests.common.CommonMaterial;
import shadow.renderer.data.SFObjectModelData;
import shadow.renderer.viewer.SFViewer;

public class ClientDataCenterListener<T extends SFObjectModelData> implements SFDataCenterListener<SFObjectModelData> {

	@Override
	public void onDatasetAvailable(String name, SFObjectModelData dataset) {
		SFObjectModel model=(SFObjectModel)dataset.getResource();
		SFViewer.generateFrame(model,
				CommonMaterial.generateColoursController(model),
				SFViewer.getLightStepController(),
				SFViewer.getRotationController(),
				SFViewer.getWireframeController(),
				SFViewer.getZoomController());
	}
}
