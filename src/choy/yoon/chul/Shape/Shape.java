package choy.yoon.chul.Shape;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Rect;

public abstract class Shape {
	
	//float[][3] 형식을 지킬 것.
	protected ArrayList<float[]> vertices_;
	protected Rect bound_;
	protected boolean visible_;
	
	public Shape() {
		vertices_ = new ArrayList<float[]>();
		bound_ = new Rect();
		bound_.left = Integer.MAX_VALUE;
		bound_.right = Integer.MIN_VALUE;
		bound_.bottom = Integer.MIN_VALUE;
		bound_.top = Integer.MAX_VALUE;
		visible_ = true;
	}
	
	abstract public void Draw(GL10 gl);
	abstract public ShapeEnumType GetType();
	
	public boolean IsIncludingPoint(float x, float y) {
		boolean including = false;
		float xi, yi, xj, yj;
		for(int i=0, j = vertices_.size()-1; i<vertices_.size(); ++i) {
			xi = vertices_.get(i)[0];
			yi = vertices_.get(i)[1];
			xj = vertices_.get(j)[0];
			yj = vertices_.get(j)[1];
			if(((yi > y) != (yj > y)) && (x < (xj - xi) * (y - yi) / (yj - yi) + xi)) {
				including = !including;
			}
		}
		return including;
	}
	
	public boolean IsVisiable() {
		return visible_;
	}
	
	public Rect GetBound() {
		return bound_;
	}
	
	private void refreshBound() {
		for(float[] v : vertices_) {
			if(bound_.left > v[0])
				bound_.left = (int)v[0];
			if(bound_.right < v[0])
				bound_.right = (int)v[0];
			if(bound_.top > v[1])
				bound_.top = (int)v[1];
			if(bound_.bottom < v[1])
				bound_.bottom = (int)v[1];
		}
	}
}
