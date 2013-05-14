package choy.yoon.chul.Shape;

import javax.microedition.khronos.opengles.GL10;


public abstract class DrawableShape extends Shape {
	protected HelperShape helperShape_;
	protected boolean isEditing_;
	
	
	public DrawableShape() {
		super();
		helperShape_ = new HelperShape();
		isEditing_ = false;
	}
	
	public float[] GetNearVertex(float x, float y) {
		float vx, vy;
		double minLength = Double.MAX_VALUE;
		double length;
		float[] vertex = null;
		for(int i=0; i<vertices_.size(); ++i) {
			vx = vertices_.get(i)[0];
			vy = vertices_.get(i)[1];
			length = Math.sqrt((vx-x)*(vx-x) + (vy-y)*(vy-y));
			if(length < 10.0f) {
				if(length < minLength) {
					minLength = length;
					vertex = vertices_.get(i);
				}
			}
		}
		return vertex;
	}
}
