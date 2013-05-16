package choy.yoon.chul.Shape;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Rect;
import choy.yoon.chul.GLESHelper;
import choy.yoon.chul.MathHelper;
import choy.yoon.chul.State.EditEnumType;

public class ShapeEditor {
	
	private Shape shape_;
	private EditEnumType editType_;
	//가지고 있는 도형의 바운드
	private BoundRect bound_;
	private ArrayList<float[]> boundVertices_;
	private int selectedVertexIndex_;
	private float[] selectedVertexOld_ = null;
	private float[] selectedVertex_ = null;
	private float[] selectedVertexCounter_ = null;
	
	public ShapeEditor() {
		editType_ = EditEnumType.kEditFreeTransform;
		selectedVertexOld_ = new float[3];
	}
	
	public void SetShape(Shape shape) {
		shape_ = shape;
		if(shape != null) {
			bound_ = shape_.GetBound();
		}
	}
	
	public void SetEditType(EditEnumType type) {
		editType_ = type;
	}
	
	public boolean SelectVertex(float x, float y) {
		switch(editType_) {
		case kEditFreeTransform:
			selectedVertex_ = shape_.GetNearVertex(x, y);
			break;
		case kEditRotate:
		case kEditScale:
		case kEditTranslate:
			selectedVertexIndex_ = bound_.GetNearVertexIndex(x, y);
			selectedVertex_ = bound_.GetVertices()[selectedVertexIndex_];
			selectedVertexOld_[0] = selectedVertex_[0];
			selectedVertexOld_[1] = selectedVertex_[1];
			selectedVertexCounter_ = bound_.GetVertices()[(selectedVertexIndex_+2)%4];
			break;
		}
		return (selectedVertex_ != null);
	}
	
	public void MoveVertex(float x, float y) {
		switch(editType_) {
		case kEditFreeTransform:
			if(selectedVertex_ != null) {
				selectedVertex_[0] = x;
				selectedVertex_[1] = y;
			}
			break;
		case kEditScale:
			if(selectedVertex_ == null) {
				return;
			}
			float scaleX = (x - selectedVertexCounter_[0]) / (selectedVertexOld_[0] - selectedVertexCounter_[0]);
			float scaleY = (y - selectedVertexCounter_[1]) / (selectedVertexOld_[1] - selectedVertexCounter_[1]);
			MathHelper.TranslateVertices(shape_.GetVertices(), 
					-selectedVertexCounter_[0], -selectedVertexCounter_[1]);
			MathHelper.ScaleVertices(shape_.GetVertices(), scaleX, scaleY);
			MathHelper.TranslateVertices(shape_.GetVertices(), 
					+selectedVertexCounter_[0], +selectedVertexCounter_[1]);
			selectedVertexOld_[0] = x;
			selectedVertexOld_[1] = y;
			shape_.RefreshBound();
			break;
		case kEditRotate:
			if(selectedVertex_ == null) {
				return;
			}
			break;
		case kEditTranslate:
			if(selectedVertex_ == null) {
				return;
			}
			break;
		}
	}
	
	public void DeselectVertex() {
		selectedVertex_ = null;
		selectedVertexOld_[0] = 0;
		selectedVertexOld_[0] = 1;
		selectedVertexIndex_ = 0;
		selectedVertexCounter_ = null;
	}
	
	public void Draw(GL10 gl) {
		if(shape_ == null) {
			return;
		}
		switch(editType_) {
		case kEditFreeTransform:
			ArrayList<float[]> vertices = shape_.GetVertices();
			for(float[] v : vertices) {
				GLESHelper.DrawPoint(gl, v[0], v[1]);
			}
			break;
		case kEditRotate:
		case kEditScale:
		case kEditTranslate:
			GLESHelper.DrawRect(gl, shape_.GetBound().GetRect());
			Rect r = shape_.GetBound().GetRect();
			GLESHelper.DrawPoint(gl, r.left, r.top);
			GLESHelper.DrawPoint(gl, r.left, r.bottom);
			GLESHelper.DrawPoint(gl, r.right, r.bottom);
			GLESHelper.DrawPoint(gl, r.right, r.top);
			break;
		}
	}
}
