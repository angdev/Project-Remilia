package choy.yoon.chul.Shape;

import android.graphics.Rect;

public class BoundRect {
	
	//0 -> lt, 1 -> lb, 2 -> rb, 3 -> rt
	private float[][] vertices_;
	private Rect rect_;
	
	public BoundRect() {
		vertices_ = new float[4][3];
		rect_ = null;
	}
	
	public void SetRect(Rect r) {
		rect_ = r;
		vertices_[0][0] = r.left;
		vertices_[0][1] = r.top;
		vertices_[1][0] = r.left;
		vertices_[1][1] = r.bottom;
		vertices_[2][0] = r.right;
		vertices_[2][1] = r.bottom;
		vertices_[3][0] = r.right;
		vertices_[3][1] = r.top;
	}
	
	public Rect GetRect() {
		return rect_;
	}
	
	public float[][] GetVertices() {
		return vertices_;
	}
	
	public int GetNearVertexIndex(float x, float y) {
		float nearestDist = Float.MAX_VALUE;
		int nearestIndex = 0;
		for(int i=0; i<4; ++i) {
			float dist = (vertices_[i][0] - x) * (vertices_[i][0] - x)
					+ (vertices_[i][1] - y) * (vertices_[i][1] - y);
			if(nearestDist > dist) {
				nearestDist = dist;
				nearestIndex = i;
			}
		}
		return nearestIndex;
	}
}
