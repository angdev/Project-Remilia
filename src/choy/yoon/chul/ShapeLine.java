package choy.yoon.chul;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

public class ShapeLine implements IShape {

	private float[][] vertices_;
	private static ShapeEnumType type_ = ShapeEnumType.kShapeLine;
	
	public ShapeLine() {
		vertices_ = new float[2][3];
		for(int i=0; i<vertices_.length; ++i) {
			for(int j=0; j<vertices_[i].length; ++j) {
				vertices_[i][j] = 0;
			}
		}
	}
	
	public void SetStartPoint(float x, float y) {
		vertices_[0][0] = x;
		vertices_[0][1] = y;
	}
	
	public void SetEndPoint(float x, float y) {
		vertices_[1][0] = x;
		vertices_[1][1] = y;
	}
	
	@Override
	public void Draw(GL10 gl) {
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glLineWidth(5);
		gl.glColor4f(0, 1, 0, 1);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, GLESHelper.ArrayToBuffer(vertices_));
		gl.glDrawArrays(GL10.GL_LINES, 0, 2);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}

	@Override
	public ShapeEnumType GetType() {
		return type_;
	}
}
