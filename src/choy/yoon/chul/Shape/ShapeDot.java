package choy.yoon.chul.Shape;

import javax.microedition.khronos.opengles.GL10;

import choy.yoon.chul.GLESHelper;

public class ShapeDot extends Shape {
	
	private static ShapeEnumType type_ = ShapeEnumType.kShapeDot;
	
	public ShapeDot() {
		super();
		vertices_.add(new float[]{0, 0, 0});
	}
	
	public void SetPosition(float x, float y) {
		vertices_.get(0)[0] = x;
		vertices_.get(0)[1] = y;
	}
	
	@Override
	public boolean IsSelected(float x, float y) {
		float x_ = vertices_.get(0)[0];
		float y_ = vertices_.get(0)[1];
		if( (x - x_) * (x - x_) + (y - y_) * (y - y_) < 5000) {
			return true;
		}
		return false;
	}
	
	@Override
	public void Draw(GL10 gl) {
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glColor4f(color_[1], color_[2], color_[3], color_[0]);
		//임의로 lineWidth * 5를 사이즈로 주었다.
		//glPointSize 같은게 있었다.
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, GLESHelper.ArrayToBuffer(
				GLESHelper.GetRectVerticesFromPoint(vertices_.get(0)[0], vertices_.get(0)[1], stroke_ * 5).toArray(new float[][]{})));
		gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, 4);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}

	@Override
	public ShapeEnumType GetType() {
		return type_;
	}

	@Override
	public boolean IsScalable() {
		return false;
	}

	@Override
	public boolean IsRotatable() {
		return false;
	}

	@Override
	public boolean IsFreeTransformable() {
		return false;
	}

}
