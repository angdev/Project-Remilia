package choy.yoon.chul.Shape;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import choy.yoon.chul.GLESHelper;

public class HelperShape {
	//rect
	private float[][] vertices_;
	
	public HelperShape() {
		vertices_ = new float[8][3];
		for(int i=0; i<vertices_.length; ++i) {
			for(int j=0; j<vertices_[i].length; ++j) {
				vertices_[i][j] = 0;
			}
		}
	}
	
	public void Draw(GL10 gl) {
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glLineWidth(5);
		gl.glColor4f(0, 1, 0, 1);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, GLESHelper.ArrayToBuffer(vertices_));
		gl.glDrawArrays(GL10.GL_LINES, 0, 8);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	
	}
}
