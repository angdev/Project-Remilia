package choy.yoon.chul;

import java.math.BigDecimal;
import java.util.ArrayList;

public class MathHelper {

	//정점을 이동시킴
	public static void TranslateVertices(ArrayList<float[]> vertices, 
			float translateX, float translateY) {
		for(float[] v : vertices) {
			v[0] += translateX;
			v[1] += translateY;
		}
	}

	//정점 크기 변경
	public static void ScaleVertices(ArrayList<float[]> vertices,
			float scaleX, float scaleY) {
		for(float[] v : vertices) {
			v[0] *= scaleX;
			v[1] *= scaleY;
		}
	}

	//정점을 기준 점으로부터 크기 변경(?)
	public static void ScaleVertices(ArrayList<float[]> vertices, float scaleX, float scaleY, float originX, float originY) {
		for(float[] v : vertices) {
			v[0] = (v[0] - originX) * scaleX + originX;
			v[1] = (v[1] - originY) * scaleY + originY;
		}
	}

	//정점 회전 변환
	public static void RotateVertices(ArrayList<float[]> vertices, 
			float radian) {
		for(float[] v : vertices) {
			BigDecimal x = new BigDecimal((v[0] * Math.cos((double)radian) - 
					v[1] * Math.sin((double)radian)));
			BigDecimal y = new BigDecimal((v[0] * Math.sin((double)radian) + 
					v[1] * Math.cos((double)radian)));
			v[0] = x.floatValue();
			v[1] = y.floatValue();
		}
	}

	//정점을 한 점을 기준으로 회전 시킴.
	public static void RotateVertices(ArrayList<float[]> vertices, float radian, float originX, float originY) {
		for(float[] v : vertices) {
			BigDecimal x = new BigDecimal((v[0] - originX) * Math.cos(radian) - (v[1] - originY) * Math.sin(radian) + originX);
			BigDecimal y = new BigDecimal((v[0] - originX) * Math.sin(radian) + (v[1] - originY) * Math.cos(radian) + originY);
			v[0] = x.floatValue();
			v[1] = y.floatValue();
		}
	}

	
	//귀 자르기 삼각화 알고리즘
	// http://www.flipcode.com/archives/Efficient_Polygon_Triangulation.shtml
	//get polygon area (inner product)
	public static float GetArea(ArrayList<float[]> vertices) {
		int size = vertices.size();
		float area = 0;
		for(int p = size-1, q = 0; q < size; p = q++) {
			area += vertices.get(p)[0] * vertices.get(q)[1] - vertices.get(q)[0] * vertices.get(p)[1];
		}
		area /= 2;
		return area;
	}

	//A, B, C => Triangle
	//P => Point
	public static boolean IsInsideTriangle(float Ax, float Ay,	float Bx, float By,
			float Cx, float Cy,	float Px, float Py) {
		float ax, ay, bx, by, cx, cy, apx, apy, bpx, bpy, cpx, cpy;
		float cCROSSap, bCROSScp, aCROSSbp;

		ax = Cx - Bx;  ay = Cy - By;
		bx = Ax - Cx;  by = Ay - Cy;
		cx = Bx - Ax;  cy = By - Ay;
		apx= Px - Ax;  apy= Py - Ay;
		bpx= Px - Bx;  bpy= Py - By;
		cpx= Px - Cx;  cpy= Py - Cy;

		aCROSSbp = ax*bpy - ay*bpx;
		cCROSSap = cx*apy - cy*apx;
		bCROSScp = bx*cpy - by*cpx;

		return ((aCROSSbp >= 0.0f) && (bCROSScp >= 0.0f) && (cCROSSap >= 0.0f));
	};

	private static boolean Snip(ArrayList<float[]> vertices,int u, int v, int w, int n, int[] V)
	{
		int p;
		float Ax, Ay, Bx, By, Cx, Cy, Px, Py;

		Ax = vertices.get(V[u])[0];
		Ay = vertices.get(V[u])[1];

		Bx = vertices.get(V[v])[0];
		By = vertices.get(V[v])[1];

		Cx = vertices.get(V[w])[0];
		Cy = vertices.get(V[w])[1];

		if ( 0.0000000001f > (((Bx-Ax)*(Cy-Ay)) - ((By-Ay)*(Cx-Ax))) ) return false;

		for (p=0;p<n;p++)
		{
			if( (p == u) || (p == v) || (p == w) ) continue;
			Px = vertices.get(V[p])[0];
			Py = vertices.get(V[p])[1];
			if (IsInsideTriangle(Ax,Ay,Bx,By,Cx,Cy,Px,Py)) return false;
		}

		return true;
	}

	//input -> vertex list (triangle_fans)
	//output -> vertex list (triangles)
	public static boolean Triangulate(ArrayList<float[]> vertices, ArrayList<float[]> result) {

		int n = vertices.size();
		if ( n < 3 ) return false;

		int[] V = new int[n];

		/* we want a counter-clockwise polygon in V */

		if ( 0.0f < GetArea(vertices) )
			for (int v=0; v<n; v++) V[v] = v;
		else
			for(int v=0; v<n; v++) V[v] = (n-1)-v;

		int nv = n;

		/*  remove nv-2 Vertices, creating 1 triangle every time */
		int count = 2*nv;   /* error detection */

		for(int v=nv-1; nv>2;)
		{
			/* if we loop, it is probably a non-simple polygon */
			if (0 >= (count--))
			{
				//** Triangulate: ERROR - probable bad polygon!
				return false;
			}

			/* three consecutive vertices in current polygon, <u,v,w> */
			int u = v  ; if (nv <= u) u = 0;     /* previous */
			v = u+1; if (nv <= v) v = 0;     /* new v    */
			int w = v+1; if (nv <= w) w = 0;     /* next     */

			if ( Snip(vertices,u,v,w,nv,V) )
			{
				int a,b,c,s,t;

				/* true names of the vertices */
				a = V[u]; b = V[v]; c = V[w];

				/* output Triangle */
				result.add(vertices.get(a));
				result.add(vertices.get(b));
				result.add(vertices.get(c));

				/* remove v from remaining polygon */
				for(s=v,t=v+1;t<nv;s++,t++) V[s] = V[t]; nv--;

				/* resest error detection counter */
				count = 2*nv;
			}
		}

		return true;
	}
}
