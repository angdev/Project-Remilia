package choy.yoon.chul.Shape;

import javax.microedition.khronos.opengles.GL10;

import choy.yoon.chul.GLESHelper;

public class ShapePolygon extends Shape {
	
	private static ShapeEnumType type_ = ShapeEnumType.kShapePolygon;
	private boolean isEditing_;
	
	public ShapePolygon() {
		super();
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
		/*
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glColor4f(color_[1], color_[2], color_[3], color_[0]);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, GLESHelper.ArrayToBuffer(vertices_.toArray(new float[][]{})));
		gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, vertices_.size());
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		*/
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		if(texBinded_) {
			gl.glEnable(GL10.GL_TEXTURE_2D);
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, GLESHelper.UVArrayToBuffer(uvs_.toArray(new float[][]{})));
		}
		gl.glColor4f(color_[1], color_[2], color_[3], color_[0]);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, GLESHelper.ArrayToBuffer(vertices_.toArray(new float[][]{})));
		gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, vertices_.size());
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		if(texBinded_) {
			gl.glDisable(GL10.GL_TEXTURE_2D);
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		}
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
