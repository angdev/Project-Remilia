package choy.yoon.chul.Shape;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.graphics.Rect;

import choy.yoon.chul.GLESHelper;
import choy.yoon.chul.MathHelper;

public class ShapePolygon extends Shape {
	
	private static ShapeEnumType type_ = ShapeEnumType.kShapePolygon;
	private boolean isEditing_;
	//triangle list from vertices_.
	private ArrayList<float[]> triangles_;
	
	public ShapePolygon() {
		super();
		triangles_ = new ArrayList<float[]>();
		isEditing_ = true;
	}
	
	public synchronized void AddVertex(float x, float y) {
		if(vertices_.size() > 0 && vertices_.get(0).equals(GetNearVertex(x, y))) {
			endEditing();
			return;
		}
		vertices_.add(new float[]{x, y, 0});
	}
	
	public boolean IsEditing() {
		return isEditing_;
	}
	
	private void endEditing() {
		isEditing_ = false;
		//정점 구성
		MathHelper.Triangulate(vertices_, triangles_);
	}

	@Override
	public void Draw(GL10 gl) {
		if(isEditing_) {
			GLESHelper.DrawOpenPolygon(gl, vertices_);
			for(float[] v : vertices_) {
				GLESHelper.DrawPoint(gl, v[0], v[1]);
			}
			return;
		}
		BindTexture(gl);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, GLESHelper.UVArrayToBuffer(uvs_.toArray(new float[][]{})));
		gl.glColor4f(color_[1], color_[2], color_[3], color_[0]);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, GLESHelper.ArrayToBuffer(triangles_.toArray(new float[][]{})));
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, triangles_.size());
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisable(GL10.GL_TEXTURE_2D);
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	}
	
	@Override
	public void SetTexture(Bitmap bitmap) {
		Rect r = GetRect();
		int width = r.right - r.left;
		int height = r.bottom - r.top;
		for(int i=0; i<triangles_.size(); ++i) {
			uvs_.add(new float[]{
				(triangles_.get(i)[0] - r.left)/width,
				(triangles_.get(i)[1] - r.top)/height
			});
		}
		bitmap_ = bitmap;
	    texBinded_ = true;
	}

	@Override
	public ShapeEnumType GetType() {
		return type_;
	}

	@Override
	public boolean IsFreeTransformable() {
		return true;
	}

	@Override
	public boolean IsScalable() {
		return true;
	}

	@Override
	public boolean IsRotatable() {
		return true;
	}

}
