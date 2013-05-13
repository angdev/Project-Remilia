package choy.yoon.chul;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class PaintView extends GLSurfaceView {

	private PaintRenderer paintRenderer;
	
	public PaintView(Context context) {
		super(context);
		paintRenderer = new PaintRenderer();
		setRenderer(paintRenderer);
	}

}
