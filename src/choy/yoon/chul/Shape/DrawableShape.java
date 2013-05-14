package choy.yoon.chul.Shape;

import javax.microedition.khronos.opengles.GL10;

public abstract class DrawableShape extends Shape {
	private HelperShape helperShape_;
	public DrawableShape() {
		super();
		helperShape_ = new HelperShape();
	}
}
