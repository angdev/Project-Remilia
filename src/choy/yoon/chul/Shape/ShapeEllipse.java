package choy.yoon.chul.Shape;

import javax.microedition.khronos.opengles.GL10;

import choy.yoon.chul.GLESHelper;

public class ShapeEllipse extends Shape {
	
	private static ShapeEnumType type_ = ShapeEnumType.kShapeEllipse;
	//TODO: 생성 이후 필요없어지는 문제.
	private float[] center_;
	//장축, 단축
	private float majorAxis_, minorAxis_;

	public ShapeEllipse() {
		super();
		center_ = new float[]{0, 0, 0};
		
		for(int i=0; i<50; ++i) {
			vertices_.add(new float[]{0, 0, 0});
		}
	}
	
	public void SetCenter(float x, float y) {
		center_[0] = x;
		center_[1] = y;
	}
	
	public float[] GetCenter() {
		return center_;
	}
	
	public void SetAxis(float major, float minor) {
		majorAxis_ = major;
		minorAxis_ = minor;
		
		for(int i=0; i<50; ++i) {
			vertices_.get(i)[0] = (float) (majorAxis_ * Math.cos(i * 2 * Math.PI / 50) + center_[0]);
			vertices_.get(i)[1] = (float) (minorAxis_ * Math.sin(i * 2 * Math.PI / 50) + center_[1]);
		}
	}

	@Override
	public void Draw(GL10 gl) {
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glColor4f(color_[1], color_[2], color_[3], color_[0]);
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
