package choy.yoon.chul.Shape;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import choy.yoon.chul.MathHelper;

import android.graphics.Rect;

public abstract class Shape {
	
	//float[][3] 형식을 지킬 것.
	protected ArrayList<float[]> vertices_;
	
	public Shape() {
		vertices_ = new ArrayList<float[]>();
	}
	
	abstract public void Draw(GL10 gl);
	abstract public ShapeEnumType GetType();
	abstract public boolean IsFreeTransformable();
	abstract public boolean IsScalable();
	abstract public boolean IsRotatable();
	
	public void Translate(float dx, float dy) {
		MathHelper.TranslateVertices(vertices_, dx, dy);
	}
	
	public void Rotate(float radian, float originX, float originY) {
		MathHelper.RotateVertices(vertices_, radian, originX, originY);
	}
	
	public void Scale(float scaleX, float scaleY, float originX, float originY) {
		MathHelper.ScaleVertices(vertices_, scaleX, scaleY, originX, originY);
	}
	
	//점을 포함하는지 검사한다. 기본적으로는 닫힌 도형 베이스라 열린 도형의 경우에는 오버라이딩을 하자.
	public boolean IsSelected(float x, float y) {
		boolean including = false;
		float xi, yi, xj, yj;
		for(int i=0, j = vertices_.size()-1; i<vertices_.size(); j = i++) {
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
	public ArrayList<float[]> GetVertices() {
		return vertices_;
	}
	
	//기본적인 rect 구하는 방법
	public Rect GetRect() {
		float left = Float.MAX_VALUE, right = Float.MIN_VALUE,
				top = Float.MAX_VALUE, bottom = Float.MIN_VALUE;
		for(float[] v : vertices_) {
			if(left > v[0]) {
				left = v[0];
			}
			if(right < v[0]) {
				right = v[0];
			}
			if(top > v[1]) {
				top = v[1];
			}
			if(bottom < v[1]) {
				bottom = v[1];
			}
		}
		Rect r = new Rect();
		r.left = (int)left;
		r.right = (int)right;
		r.top = (int)top;
		r.bottom = (int)bottom;
		return r;
	}
	
	public float[] GetNearVertex(float x, float y) {
		float vx, vy;
		double minLength = Double.MAX_VALUE;
		double length;
		float[] vertex = null;
		for(int i=0; i<vertices_.size(); ++i) {
			vx = vertices_.get(i)[0];
			vy = vertices_.get(i)[1];
			length = (vx-x)*(vx-x) + (vy-y)*(vy-y);
			if(length < 5000.0f) {
				if(length < minLength) {
					minLength = length;
					vertex = vertices_.get(i);
				}
			}
		}
		return vertex;
	}
}
