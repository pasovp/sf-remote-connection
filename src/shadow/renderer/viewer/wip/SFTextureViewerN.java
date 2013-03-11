package shadow.renderer.viewer.wip;

import shadow.image.SFTexture;
import shadow.renderer.viewer.SFDrawableFrame;
import shadow.renderer.viewer.SFFrameController;
import shadow.renderer.viewer.SFTextureViewer;

public class SFTextureViewerN extends SFTextureViewer {
	private SFDrawableFrame frame;
	
	public static SFTextureViewerN generateFrame(SFTexture texture,SFFrameController... controllers){
		SFTextureViewerN viewer=new SFTextureViewerN();
		viewer.setTexture(texture);
		viewer.setFrame(new SFDrawableFrame("Scene Viewer", 600, 600, viewer,controllers));
		viewer.getFrame().setVisible(true);
		
		return viewer;
	}

	public SFDrawableFrame getFrame() {
		return frame;
	}

	public void setFrame(SFDrawableFrame frame) {
		this.frame = frame;
	}
	
	
}
