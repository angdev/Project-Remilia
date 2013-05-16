package choy.yoon.chul.Shape;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

public class DrawableShapeList {
	private static DrawableShapeList instance_ = null;
	private ArrayList<Shape> shapes_;
	private ShapeEditor shapeEditor_ = null;

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

	public void SetShapeEditor(ShapeEditor shapeEditor) {
		shapeEditor_ = shapeEditor;
	}

	public Shape PickShape(float x, float y) {
		//�ֱٰź��� ã�´� -> ���� Ž��
		for(int i=shapes_.size()-1; i>=0; --i) {
			if(shapes_.get(i).IsSelected(x, y)) {
				return shapes_.get(i);
			}
		}
		return null;
	}

	public void DrawShapes(GL10 gl) {
		for(Shape shape : shapes_) {
			shape.Draw(gl);
		}
		if(shapeEditor_ != null) {
			shapeEditor_.Draw(gl);
		}
	}
}
