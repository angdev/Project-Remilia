package choy.yoon.chul.Shape;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Rect;
import choy.yoon.chul.GLESHelper;
import choy.yoon.chul.State.EditEnumType;

public class ShapeEditor {
	
	private Shape shape_;
	private EditEnumType editType_;
	//가지고 있는 도형의 바운드
	private Rect bound_;
	private ArrayList<float[]> boundVertices_;
	private float[] selectedVertex_ = null;
	
	public ShapeEditor() {
		editType_ = EditEnumType.kEditFreeTransform;
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
		}
	}
	
	public void DeselectVertex() {
		selectedVertex_ = null;
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
			GLESHelper.DrawRect(gl, shape_.GetBound());
			Rect r = shape_.GetBound();
			GLESHelper.DrawPoint(gl, r.left, r.top);
			GLESHelper.DrawPoint(gl, r.left, r.bottom);
			GLESHelper.DrawPoint(gl, r.right, r.bottom);
			GLESHelper.DrawPoint(gl, r.right, r.top);
			break;
		}
	}
}
