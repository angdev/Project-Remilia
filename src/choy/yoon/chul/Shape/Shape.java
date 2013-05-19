package choy.yoon.chul.Shape;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.opengl.GLUtils;
import android.util.Log;
import choy.yoon.chul.GLESHelper;
import choy.yoon.chul.MathHelper;

public abstract class Shape {
	
	//float[][3] 형식을 지킬 것.
	protected ArrayList<float[]> vertices_;
	protected ArrayList<float[]> uvs_;
	protected int[] texPtr_;
	protected boolean texBinded_;
	protected Bitmap bitmap_;
	protected float[] color_;
	
	public Shape() {
		vertices_ = new ArrayList<float[]>();
		uvs_ = new ArrayList<float[]>();
		color_ = GLESHelper.GetARGB(0xFFFFFFFF);
		texPtr_ = new int[1];
		texBinded_ = false;
		bitmap_ = null;
	}
	
	abstract public ShapeEnumType GetType();
	abstract public boolean IsFreeTransformable();
	abstract public boolean IsScalable();
	abstract public boolean IsRotatable();
	
	public void Draw(GL10 gl) {
		if(bitmap_ != null) {
			BindTexture(gl);
			gl.glEnable(GL10.GL_TEXTURE_2D);
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, GLESHelper.UVArrayToBuffer(uvs_.toArray(new float[][]{})));
		}
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glColor4f(color_[1], color_[2], color_[3], color_[0]);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, GLESHelper.ArrayToBuffer(vertices_.toArray(new float[][]{})));
		gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, vertices_.size());
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		if(bitmap_ != null) {
			gl.glDisable(GL10.GL_TEXTURE_2D);
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		}
	}
	
	public void FreeTransform(int index, float x, float y) {
		vertices_.get(index)[0] = x;
		vertices_.get(index)[1] = y;
	}
	
	public void Translate(float dx, float dy) {
		MathHelper.TranslateVertices(vertices_, dx, dy);
	}
	
	public void Rotate(float radian, float originX, float originY) {
		MathHelper.RotateVertices(vertices_, radian, originX, originY);
	}
	
	public void Scale(float scaleX, float scaleY, float originX, float originY) {
		MathHelper.ScaleVertices(vertices_, scaleX, scaleY, originX, originY);
	}
	
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
	
	public void SetColor(float[] color) {
		color_ = color;
	}
	
	public float[] GetColor() {
		return color_;
	}
	
	public void SetTexture(Bitmap bitmap) {
		Rect r = GetRect();
		int width = r.right - r.left;
		int height = r.bottom - r.top;
		for(int i=0; i<vertices_.size(); ++i) {
			uvs_.add(new float[]{
				(vertices_.get(i)[0] - r.left)/width,
				(vertices_.get(i)[1] - r.top)/height
			});
		}
		bitmap_ = bitmap;
	    texBinded_ = true;
	}
	
	public void BindTexture(GL10 gl) {
		if(texBinded_ == true) {
			gl.glGenTextures(1, texPtr_, 0);
			gl.glBindTexture(GL10.GL_TEXTURE_2D, texPtr_[0]);
			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
			GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap_, 0);
			bitmap_.recycle();
			texBinded_ = false;
		}
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
	

	public int GetNearVertexIndex(float x, float y) {
		float vx, vy;
		double minLength = Double.MAX_VALUE;
		double length;
		int index = -1;
		for(int i=0; i<vertices_.size(); ++i) {
			vx = vertices_.get(i)[0];
			vy = vertices_.get(i)[1];
			length = (vx-x)*(vx-x) + (vy-y)*(vy-y);
			if(length < 5000.0f) {
				if(length < minLength) {
					minLength = length;
					index = i;
				}
			}
		}
		return index;
	}
}
