package choy.yoon.chul;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Rect;

public class GLESHelper {
		
	//배열을 버퍼로 만들어주는 함수
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
	
	//uv 배열을 버퍼로 만들어주는 함수
	public static FloatBuffer UVArrayToBuffer(float[][] arr) {
		ByteBuffer vbb = ByteBuffer.allocateDirect(arr.length * 2 * 4);
		vbb.order(ByteOrder.nativeOrder());
		FloatBuffer fb = vbb.asFloatBuffer();
		for(int i=0; i < arr.length; ++i) {
			fb.put(arr[i]);
		}
		fb.position(0);
		return fb;
	}
	
	//정점 리스트로부터 닫힌 다각형의 외곽선을 그릴 수 있는 정점 형태로 반환.
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
	
	
	//점 위치를 입력받아 점을 그릴 수 있게 정점을 반환.
	public static ArrayList<float[]> GetRectVerticesFromPoint(float x, float y, float size) {
		ArrayList<float[]> arr = new ArrayList<float[]>();
		arr.add(new float[]{ x-size, y-size, 0 });
		arr.add(new float[]{ x-size, y+size, 0 });
		arr.add(new float[]{ x+size, y+size, 0 });
		arr.add(new float[]{ x+size, y-size, 0 });
		return arr;
	}
	
	//점을 바로 그려줌.
	public static void DrawPoint(GL10 gl, float x, float y) {
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glLineWidthx(1);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0,
				PolygonBufferFromVertices(GetRectVerticesFromPoint(x, y, 10)));
		gl.glDrawArrays(GL10.GL_LINES, 0, 8);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}
	
	//outline only
	public static void DrawPolygon(GL10 gl, ArrayList<float[]> vertices) {
		if(vertices.size() < 2) {
			return;
		}
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glLineWidthx(1);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0,
				PolygonBufferFromVertices(vertices));
		gl.glDrawArrays(GL10.GL_LINES, 0, vertices.size() * 2);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}
	

	//outline only
	public static void DrawOpenPolygon(GL10 gl, ArrayList<float[]> vertices) {
		if(vertices.size() < 2) {
			return;
		}

		ArrayList<float[]> points = new ArrayList<float[]>();
		for(int i=0; i<vertices.size()-1; ++i) {
			points.add(vertices.get(i));
			points.add(vertices.get(i+1));
		}

		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0,
				ArrayToBuffer(points.toArray(new float[][]{})));
		gl.glDrawArrays(GL10.GL_LINES, 0, vertices.size() * 2 - 2);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}
	
	//사각형 그림.
	public static void DrawRect(GL10 gl, Rect r) {
		ArrayList<float[]> arr = new ArrayList<float[]>();
		arr.add(new float[]{ r.left, r.top, 0 });
		arr.add(new float[]{ r.left, r.bottom, 0 });
		arr.add(new float[]{ r.right, r.bottom, 0 });
		arr.add(new float[]{ r.right, r.top, 0 });
		DrawPolygon(gl, arr);
	}
	
	//int 값으로 들어있는 컬러값을 float로 반환.
	public static float[] GetARGB(int color) {
		float[] argb = new float[4];
		argb[0] = (color >>> 24) / (float)0xFF;
		argb[1] = ((color << 8) >>> 24) / (float)0xFF;
		argb[2] = ((color << 16) >>> 24) / (float)0xFF;
		argb[3] = ((color << 24) >>> 24) / (float)0xFF;
		return argb;
	}
}
