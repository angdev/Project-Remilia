package choy.yoon.chul.Shape;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

public class DrawableShapeList {
	private static DrawableShapeList instance_ = null;
	private ArrayList<DrawableShape> shapes_;
	
	private DrawableShapeList() {
		shapes_ = new ArrayList<DrawableShape>();
	}
	
	//귀찮으니 일단 이 정도로 싱글턴 구현.
	public static synchronized DrawableShapeList getInstance() {
		if(instance_ == null) {
			instance_ = new DrawableShapeList();
		}
		return instance_;
	}
	
	public void AddShape(DrawableShape shape) {
		shapes_.add(shape);
	}
	
	public DrawableShape PickShape(float x, float y) {
		//최근거부터 찾는다 -> 역순 탐색
		for(int i=shapes_.size()-1; i>=0; --i) {
			if(shapes_.get(i).GetType() == ShapeEnumType.kShapeLine) {
				//직선거리
				
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
