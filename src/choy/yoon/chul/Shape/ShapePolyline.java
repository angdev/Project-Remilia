package choy.yoon.chul.Shape;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

import choy.yoon.chul.GLESHelper;

public class ShapePolyline extends Shape {

	private static ShapeEnumType type_ = ShapeEnumType.kShapePolyline;
	private boolean isEditing_;

	public ShapePolyline() {
		super();
		isEditing_ = true;
	}
	
	public synchronized void AddVertex(float x, float y) {
		if(vertices_.size() > 0 && vertices_.get(vertices_.size()-1).equals(GetNearVertex(x, y))) {
			endEditing();
			return;
		}
		vertices_.add(new float[]{x, y, 0});
	}
	
	public boolean IsEditing() {
		return isEditing_;
	}
	
	private void endEditing() {
		isEditing_ = false;
	}
	
	@Override
	public boolean IsSelected(float x, float y) {
		//�����Ÿ�
		for(float[] v : vertices_) {
			Log.d("", ""+((v[0] - x) * (v[0] - x) + (v[1] - y) * (v[1] - y)));
			if((v[0] - x) * (v[0] - x) + (v[1] - y) * (v[1] - y) < 5000) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void Draw(GL10 gl) {
		GLESHelper.DrawOpenPolygon(gl, vertices_);
		if(isEditing_) {
			for(float[] v : vertices_) {
				GLESHelper.DrawPoint(gl, v[0], v[1]);
			}
			return;
		}
	}

	@Override
	public ShapeEnumType GetType() {
		return type_;
	}

	@Override
	public boolean IsFreeTransformable() {
		return true;
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