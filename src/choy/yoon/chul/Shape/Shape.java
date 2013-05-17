package choy.yoon.chul.Shape;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Rect;

public abstract class Shape {
	
	//float[][3] ������ ��ų ��.
	protected ArrayList<float[]> vertices_;
	
	public Shape() {
		vertices_ = new ArrayList<float[]>();
	}
	
	abstract public void Draw(GL10 gl);
	abstract public ShapeEnumType GetType();
	//���� ���� ����
	abstract public boolean IsFreeTransformable();
	abstract public boolean IsScalable();
	abstract public boolean IsRotatable();

	public void Update() {
		
	}
	
	//���� �����ϴ��� �˻��Ѵ�. �⺻�����δ� ���� ���� ���̽��� ���� ������ ��쿡�� �������̵��� ����.
	public boolean IsSelected(float x, float y) {
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
	public ArrayList<float[]> GetVertices() {
		return vertices_;
	}
	
	//�⺻���� rect ���ϴ� ���
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
