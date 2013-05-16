package choy.yoon.chul;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;

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
	
	public static void ScaleVertices(float[][] vertices, float scaleX, float scaleY, float originX, float originY) {
		for(float[] v : vertices) {
			v[0] = (v[0] - originX) * scaleX + originX;
			v[1] = (v[1] - originY) * scaleY + originY;
		}
	}
	
	public static void ScaleVertices(ArrayList<float[]> vertices, float scaleX, float scaleY, float originX, float originY) {
		for(float[] v : vertices) {
			v[0] = (v[0] - originX) * scaleX + originX;
			v[1] = (v[1] - originY) * scaleY + originY;
		}
	}
	
	public static void RotateVertices(float[][] vertices, 
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
	
	public static void RotateVertices(float[][] vertices, float radian, float originX, float originY) {
		for(float[] v : vertices) {
			BigDecimal x = new BigDecimal((v[0] - originX) * Math.cos(radian) - (v[1] - originY) * Math.sin(radian) + originX);
			BigDecimal y = new BigDecimal((v[0] - originX) * Math.sin(radian) + (v[1] - originY) * Math.cos(radian) + originY);
			v[0] = x.floatValue();
			v[1] = y.floatValue();
		}
	}
	
	public static void RotateVertices(ArrayList<float[]> vertices, float radian, float originX, float originY) {
		for(float[] v : vertices) {
			BigDecimal x = new BigDecimal((v[0] - originX) * Math.cos(radian) - (v[1] - originY) * Math.sin(radian) + originX);
			BigDecimal y = new BigDecimal((v[0] - originX) * Math.sin(radian) + (v[1] - originY) * Math.cos(radian) + originY);
			v[0] = x.floatValue();
			v[1] = y.floatValue();
		}
	}
	
}
