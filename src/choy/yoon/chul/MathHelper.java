package choy.yoon.chul;

import java.util.ArrayList;

public class MathHelper {
	
	public static void TranslateVertices(float[][] vertices, 
			float translateX, float translateY) {
		for(float[] v : vertices) {
			v[0] += translateX;
			v[1] += translateY;
		}
	}
	
	public static void TranslateVertices(ArrayList<float[]> vertices, 
			float translateX, float translateY) {
		for(float[] v : vertices) {
			v[0] += translateX;
			v[1] += translateY;
		}
	}
	
	public static void ScaleVertices(float[][] vertices,
			float scaleX, float scaleY) {
		for(float[] v : vertices) {
			v[0] *= scaleX;
			v[1] *= scaleY;
		}
	}
	
	public static void ScaleVertices(ArrayList<float[]> vertices,
			float scaleX, float scaleY) {
		for(float[] v : vertices) {
			v[0] *= scaleX;
			v[1] *= scaleY;
		}
	}
	
	public static void RotateVertices(float[][] vertices, 
			float radian) {
		for(float[] v : vertices) {
			v[0] = (float) (v[0] * Math.cos((double)radian) - 
					v[1] * Math.sin((double)radian));
			v[1] = (float) (v[0] * Math.sin((double)radian) + 
					v[1] * Math.cos((double)radian));
		}
	}
	
	public static void RotateVertices(ArrayList<float[]> vertices, 
			float radian) {
		for(float[] v : vertices) {
			v[0] = (float) (v[0] * Math.cos((double)radian) - 
					v[1] * Math.sin((double)radian));
			v[1] = (float) (v[0] * Math.sin((double)radian) + 
					v[1] * Math.cos((double)radian));
		}
	}
}
