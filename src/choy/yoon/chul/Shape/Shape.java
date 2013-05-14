package choy.yoon.chul.Shape;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Point;
import android.graphics.Rect;

public abstract class Shape {
	
	//float[][3] 형식을 지킬 것.
	protected ArrayList<float[]> vertices_;
	protected boolean visible_;
	
	public Shape() {
		vertices_ = new ArrayList<float[]>();
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
	
	public void SetVisible(boolean visible) {
		visible_ = visible;
	}
	
	public boolean IsVisiable() {
		return visible_;
	}
	
	public ArrayList<float[]> GetVertices() {
		return vertices_;
	}
}
