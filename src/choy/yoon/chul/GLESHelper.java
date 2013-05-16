package choy.yoon.chul;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Rect;

public class GLESHelper {
	public static FloatBuffer ArrayToBuffer(float[][] arr) {
		ByteBuffer vbb = ByteBuffer.allocateDirect(arr.length * 3 * 4);
		vbb.order(ByteOrder.nativeOrder());
		FloatBuffer fb = vbb.asFloatBuffer();
		for(int i=0; i < arr.length; ++i) {
			fb.put(arr[i]);
		}
		fb.position(0);
		return fb;
	}
	
	public static FloatBuffer PolygonBufferFromVertices(ArrayList<float[]> vertices) {
		if(vertices.size() < 2) {
			return null;
		}
		ArrayList<float[]> points = new ArrayList<float[]>();
		points.add(vertices.get(0));
		for(int i=1; i<vertices.size(); ++i) {
			points.add(vertices.get(i));
			points.add(vertices.get(i));
		}
		points.add(vertices.get(0));
		return ArrayToBuffer(points.toArray(new float[][]{}));
	}
	
	public static FloatBuffer PolygonBufferFromVertices(float[][] vertices) {
		if(vertices.length < 2) {
			return null;
		}
		ArrayList<float[]> points = new ArrayList<float[]>();
		points.add(vertices[0]);
		for(int i=1; i<vertices.length; ++i) {
			points.add(vertices[i]);
			points.add(vertices[i]);
		}
		points.add(vertices[0]);
		return ArrayToBuffer(points.toArray(new float[][]{}));
	}
	
	public static ArrayList<float[]> GetRectVerticesFromPoint(float x, float y) {
		ArrayList<float[]> arr = new ArrayList<float[]>();
		arr.add(new float[]{ x-10, y-10, 0 });
		arr.add(new float[]{ x-10, y+10, 0 });
		arr.add(new float[]{ x+10, y+10, 0 });
		arr.add(new float[]{ x+10, y-10, 0 });
		return arr;
	}
	
	public static void DrawPoint(GL10 gl, float x, float y) {
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glColor4f(1, 1, 1, 1);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0,
				PolygonBufferFromVertices(GetRectVerticesFromPoint(x, y)));
		gl.glDrawArrays(GL10.GL_LINES, 0, 8);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}
	
	//outline only
	public static void DrawPolygon(GL10 gl, ArrayList<float[]> vertices) {
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glColor4f(0, 1, 0, 1);
		gl.glLineWidthx(1);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0,
				PolygonBufferFromVertices(vertices));
		gl.glDrawArrays(GL10.GL_LINES, 0, vertices.size() * 2);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}
	
	public static void DrawPolygon(GL10 gl, float[][] vertices) {
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glColor4f(0, 1, 0, 1);
		gl.glLineWidthx(1);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0,
				PolygonBufferFromVertices(vertices));
		gl.glDrawArrays(GL10.GL_LINES, 0, vertices.length * 2);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}
	
	public static void DrawRect(GL10 gl, Rect r) {
		ArrayList<float[]> arr = new ArrayList<float[]>();
		arr.add(new float[]{ r.left, r.top, 0 });
		arr.add(new float[]{ r.left, r.bottom, 0 });
		arr.add(new float[]{ r.right, r.bottom, 0 });
		arr.add(new float[]{ r.right, r.top, 0 });
		DrawPolygon(gl, arr);
	}
}
