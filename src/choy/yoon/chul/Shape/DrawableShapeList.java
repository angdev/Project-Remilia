package choy.yoon.chul.Shape;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

public class DrawableShapeList {
	private static DrawableShapeList instance_ = null;
	private ArrayList<Shape> shapes_;
	
	private DrawableShapeList() {
		shapes_ = new ArrayList<Shape>();
	}
	
	//�������� �ϴ� �� ������ �̱��� ����.
	public static synchronized DrawableShapeList getInstance() {
		if(instance_ == null) {
			instance_ = new DrawableShapeList();
		}
		return instance_;
	}
	
	public void AddShape(Shape shape) {
		shapes_.add(shape);
	}
	
	public Shape PickShape(float x, float y) {
		//�ֱٰź��� ã�´� -> ���� Ž��
		for(int i=shapes_.size()-1; i>=0; --i) {
			if(shapes_.get(i).GetType() == ShapeEnumType.kShapeLine) {
				
			}
			else {
				if(shapes_.get(i).IsIncludingPoint(x, y)) {
				Log.d("", "Pick");
				return shapes_.get(i);
				}
			}
		}
		return null;
	}
	
	public void DrawShapes(GL10 gl) {
		for(Shape shape : shapes_) {
			shape.Draw(gl);
		}
	}
}
