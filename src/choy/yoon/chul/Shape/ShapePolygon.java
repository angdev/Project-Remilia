package choy.yoon.chul.Shape;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.graphics.Rect;

import choy.yoon.chul.GLESHelper;
import choy.yoon.chul.MathHelper;

public class ShapePolygon extends Shape {
	
	private static ShapeEnumType type_ = ShapeEnumType.kShapePolygon;
	//맨 처음에 생성할 때, 그려지고 있는 상태를 표시
	private boolean isEditing_;
	//triangle list from vertices_.
	private ArrayList<float[]> triangles_;
	//구성하고 있는 정점이 수정되었는가 (삼각화를 통한 그리기 정점 갱신을 위해서)
	private boolean isDirty_;
	
	public ShapePolygon() {
		super();
		triangles_ = new ArrayList<float[]>();
		isEditing_ = true;
		isDirty_ = true;
	}
	
	@Override
	public void FreeTransform(int index, float x, float y) {
		super.FreeTransform(index, x, y);
		isDirty_ = true;
	}

	//처음 위치를 선택하면 종료.
	public synchronized void AddVertex(float x, float y) {
		if(vertices_.size() > 0 && GetNearVertexIndex(x, y) == 0) {
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
	}
	
	//오목다각형을 위한 삼각화
	private void triangulate() {
		//정점이 변경되었을 때만 새로 삼각화한다.
		//gl 스레드만이 이 함수에 접근할 수 있다.
		if(isDirty_) {
			triangles_.clear();
			MathHelper.Triangulate(vertices_, triangles_);
			isDirty_ = false;
		}
	}

	@Override
	public void Draw(GL10 gl) {
		//그리는 도중일 떄는 선분만 보여준다.
		if(isEditing_) {
			GLESHelper.DrawOpenPolygon(gl, vertices_);
			int size = vertices_.size();
			for(int i=0; i<size; ++i) {
				GLESHelper.DrawPoint(gl, vertices_.get(i)[0], vertices_.get(i)[1]);
			}
			return;
		}
		
		triangulate();
		
		if(bitmap_ != null) {
			BindTexture(gl);
			gl.glEnable(GL10.GL_TEXTURE_2D);
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, GLESHelper.UVArrayToBuffer(uvs_.toArray(new float[][]{})));
		}
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glColor4f(color_[1], color_[2], color_[3], color_[0]);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, GLESHelper.ArrayToBuffer(triangles_.toArray(new float[][]{})));
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, triangles_.size());
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		if(bitmap_ != null) {
			gl.glDisable(GL10.GL_TEXTURE_2D);
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		}
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
