package choy.yoon.chul.Shape;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import choy.yoon.chul.State.PaintStateManager;
import choy.yoon.chul.State.StateType;

public class DrawableShapeList {
	private static DrawableShapeList instance_ = null;
	private ArrayList<Shape> shapes_;
	private ShapeEditor shapeEditor_ = null;

	private DrawableShapeList() {
		shapes_ = new ArrayList<Shape>();
	}

	//귀찮으니 일단 이 정도로 싱글턴 구현.
	public static synchronized DrawableShapeList getInstance() {
		if(instance_ == null) {
			instance_ = new DrawableShapeList();
		}
		return instance_;
	}

	public void AddShape(Shape shape) {
		shapes_.add(shape);
	}
	
	public void RemoveShape(Shape shape) {
		shapes_.remove(shape);
	}

	public void SetShapeEditor(ShapeEditor shapeEditor) {
		shapeEditor_ = shapeEditor;
	}
	
	public void RebindTexture() {
		int size = shapes_.size();
		for(int i=0; i<size; ++i) {
			shapes_.get(i).RebindTexture();
		}
	}

	public Shape PickShape(float x, float y) {
		//최근거부터 찾는다 -> 역순 탐색
		for(int i=shapes_.size()-1; i>=0; --i) {
			if(shapes_.get(i).IsSelected(x, y)) {
				return shapes_.get(i);
			}
		}
		return null;
	}

	//렌더러는 이 함수를 호출하여 도형들을 그림. (뷰 역할)
	public void DrawShapes(GL10 gl) {
		int size = shapes_.size();
		for(int i=0; i<size; ++i) {
			shapes_.get(i).Draw(gl);
		}
		if(shapeEditor_ != null && PaintStateManager.GetInstance().GetState().GetType() == StateType.kStateEdit) {
			shapeEditor_.Draw(gl);
		}
	}
}
