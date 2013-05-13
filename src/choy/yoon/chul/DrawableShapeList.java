package choy.yoon.chul;

import java.util.ArrayList;
import java.util.Stack;

import javax.microedition.khronos.opengles.GL10;

public class DrawableShapeList {
	private static DrawableShapeList instance_ = null;
	private ArrayList<IShape> shapes_;
	
	private DrawableShapeList() {
		shapes_ = new ArrayList<IShape>();
	}
	
	//귀찮으니 일단 이 정도로 싱글턴 구현.
	public static synchronized DrawableShapeList getInstance() {
		if(instance_ == null) {
			instance_ = new DrawableShapeList();
		}
		return instance_;
	}
	
	public void AddShape(IShape shape) {
		shapes_.add(shape);
	}
	
	public void DrawShapes(GL10 gl) {
		for(IShape shape : shapes_) {
			shape.Draw(gl);
		}
	}
}
