package choy.yoon.chul.Shape;

import android.graphics.Rect;

public class BoundRect {
	
	//0 -> lt, 1 -> lb, 2 -> rb, 3 -> rt
	private float[][] vertices_;
	
	public BoundRect() {
		vertices_ = new float[4][3];
	}
	
	public void SetBound(Rect r) {
		vertices_[0][0] = r.left;
		vertices_[0][1] = r.top;
		vertices_[1][0] = r.left;
		vertices_[1][1] = r.bottom;
		vertices_[2][0] = r.right;
		vertices_[2][1] = r.bottom;
		vertices_[3][0] = r.right;
		vertices_[3][1] = r.top;
	}
	
	public void FlipX() {
		//부적절할 때는 뒤집으면 안됨.
		if(vertices_[0][0] < vertices_[2][0]) {
			return;
		}
		for(int i=0; i<2; ++i) {
			float[] temp = vertices_[i];
			vertices_[i] = vertices_[i^3];
			vertices_[i^3] = temp;
		}
	}
	
	public void FlipY() {
		if(vertices_[0][1] > vertices_[2][1]) {
			return;
		}
		for(int i=0; i<2; ++i) {
			float[] temp = vertices_[i];
			vertices_[i] = vertices_[i^1];
			vertices_[i^1] = temp;
		}
	}
	
	public float[] GetCenter() {
		float[] center = new float[] {
			(vertices_[0][0] + vertices_[2][0]) / 2,
			(vertices_[0][1] + vertices_[2][1]) / 2,
			0
		};
		return center;
	}
	
	public float[][] GetVertices() {
		return vertices_;
	}
	
	public int GetNearVertexIndex(float x, float y) {
		float nearestDist = Float.MAX_VALUE;
		int nearestIndex = -1;
		for(int i=0; i<4; ++i) {
			float dist = (vertices_[i][0] - x) * (vertices_[i][0] - x)
					+ (vertices_[i][1] - y) * (vertices_[i][1] - y);
			if(dist < 5000 && nearestDist > dist) {
				nearestDist = dist;
				nearestIndex = i;
			}
		}
		return nearestIndex;
	}
}
