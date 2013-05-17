package choy.yoon.chul.Shape;

import javax.microedition.khronos.opengles.GL10;

import choy.yoon.chul.GLESHelper;

import android.graphics.Rect;

public class ShapeBound extends Shape {
	
	private static ShapeEnumType type_ = ShapeEnumType.kShapeBound;
	
	//0 -> lt, 1 -> lb, 2 -> rb, 3 -> rt
	public ShapeBound() {
		super();
		for(int i=0; i<4; ++i) {
			vertices_.add(new float[]{0, 0, 0});
		}
	}
	
	public void SetBound(Rect r) {
		vertices_.get(0)[0] = r.left;
		vertices_.get(0)[1] = r.top;
		vertices_.get(1)[0] = r.left;
		vertices_.get(1)[1] = r.bottom;
		vertices_.get(2)[0] = r.right;
		vertices_.get(2)[1] = r.bottom;
		vertices_.get(3)[0] = r.right;
		vertices_.get(3)[1] = r.top;
	}
	
	private void flipX() {
		//부적절할 때는 뒤집으면 안됨.
		if(vertices_.get(0)[0] < vertices_.get(2)[0]) {
			return;
		}
		for(int i=0; i<2; ++i) {
			float[] temp = vertices_.get(i);
			vertices_.set(i, vertices_.get(i^3));
			vertices_.set(i^3, temp);
		}
	}
	
	private void flipY() {
		if(vertices_.get(0)[1] > vertices_.get(2)[1]) {
			return;
		}
		for(int i=0; i<2; ++i) {
			float[] temp = vertices_.get(i);
			vertices_.set(i, vertices_.get(i^1));
			vertices_.set(i^1, temp);
		}
	}
	
	public float[] GetCenter() {
		float[] center = new float[] {
			(vertices_.get(0)[0] + vertices_.get(2)[0]) / 2,
			(vertices_.get(0)[1] + vertices_.get(2)[1]) / 2,
			0
		};
		return center;
	}
	
	@Override
	public void Scale(float scaleX, float scaleY, float originX, float originY) {
		super.Scale(scaleX, scaleY, originX, originY);
		if(scaleX < 0 || scaleY < 0) {
			if(scaleX < 0) {
				flipX();
			}
			if(scaleY < 0) {
				flipY();
			}
		}
	}

	@Override
	public void Draw(GL10 gl) {
		gl.glPushMatrix();
		gl.glColor4f(0, 1, 0, 1);
		GLESHelper.DrawPolygon(gl, vertices_);
		for(float[] v : vertices_) {
			GLESHelper.DrawPoint(gl, v[0], v[1]);
		}
		gl.glPopMatrix();
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
