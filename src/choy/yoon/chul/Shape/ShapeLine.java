package choy.yoon.chul.Shape;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import choy.yoon.chul.GLESHelper;

import android.util.Log;

public class ShapeLine extends Shape {

	private static ShapeEnumType type_ = ShapeEnumType.kShapeLine;
	
	public ShapeLine() {
		super();
		vertices_.add(new float[]{0, 0, 0});
		vertices_.add(new float[]{0, 0, 0});
	}
	
	public void SetStartPoint(float x, float y) {
		vertices_.get(0)[0] = x;
		vertices_.get(0)[1] = y;
	}
	
	public void SetEndPoint(float x, float y) {
		vertices_.get(1)[0] = x;
		vertices_.get(1)[1] = y;
		RefreshBound();
	}
	
	@Override
	public void Draw(GL10 gl) {
		if(!IsVisiable()) {
			return;
		}
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glLineWidth(5);
		gl.glColor4f(1, 1, 1, 1);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, GLESHelper.ArrayToBuffer(vertices_.toArray(new float[][]{})));
		gl.glDrawArrays(GL10.GL_LINES, 0, 2);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}
	
	@Override
	public ShapeEnumType GetType() {
		return type_;
	}
}
