package choy.yoon.chul.Shape;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;
import choy.yoon.chul.GLESHelper;

public class ShapeRectangle extends Shape {

	private static ShapeEnumType type_ = ShapeEnumType.kShapeRectangle;
	private float[] center_;
	
	public ShapeRectangle() {
		super();
		for(int i=0; i<4; ++i) {
			vertices_.add(new float[]{0, 0, 0});
		}
		center_ = new float[]{0, 0, 0};
	}
	
	public void SetCenter(float centerX, float centerY) {
		center_[0] = centerX;
		center_[1] = centerY;
	}
	
	public void SetSize(float width, float height) {
		vertices_.get(0)[0] = center_[0] - width / 2;
		vertices_.get(0)[1] = center_[1] - height / 2;
		vertices_.get(1)[0] = center_[0] - width / 2;
		vertices_.get(1)[1] = center_[1] + height / 2;
		vertices_.get(2)[0] = center_[0]+ width / 2;
		vertices_.get(2)[1] = center_[1] + height / 2;
		vertices_.get(3)[0] = center_[0] + width / 2;
		vertices_.get(3)[1] = center_[1] - height / 2;
	}
	
	@Override
	public void Draw(GL10 gl) {
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glColor4f(color_[1], color_[2], color_[3], color_[0]);
		Log.d("", color_[1] + ", " + color_[2] + ", " + color_[3] + "," + color_[0]);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, GLESHelper.ArrayToBuffer(vertices_.toArray(new float[][]{})));
		gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, vertices_.size());
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}

	@Override
	public ShapeEnumType GetType() {
		return type_;
	}

	@Override
	public boolean IsFreeTransformable() {
		return false;
	}

	@Override
	public boolean IsScalable() {
		return true;
	}

	@Override
	public boolean IsRotatable() {
		return true;
	}

}
