package choy.yoon.chul;

import android.content.Context;
import android.graphics.Point;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

public class PaintView extends GLSurfaceView {

	private PaintRenderer paintRenderer;
	
	public PaintView(Context context) {
		super(context);
		WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		paintRenderer = new PaintRenderer(size.x, size.y);
		Log.d("", ""+size.x +","+size.y);
		setRenderer(paintRenderer);
	}

}
