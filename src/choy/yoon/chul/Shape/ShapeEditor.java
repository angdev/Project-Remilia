package choy.yoon.chul.Shape;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import choy.yoon.chul.GLESHelper;
import choy.yoon.chul.State.EditEnumType;
import choy.yoon.chul.State.PaintStateManager;
import choy.yoon.chul.State.PaintStateManager.StateType;

public class ShapeEditor {
	
	private Shape shape_;
	private EditEnumType editType_;
	//가지고 있는 도형의 바운드
	private ShapeBound bound_;
	
	//scale
	private int selectedVertexIndex_;
	private float[] selectedVertexOld_ = null;
	private float[] selectedVertex_ = null;
	private float[] selectedVertexCounter_ = null;
	
	//translate
	private float oldTouchX_;
	private float oldTouchY_;
	
	public ShapeEditor() {
		editType_ = EditEnumType.kEditFreeTransform;
		selectedVertexOld_ = new float[3];
		bound_ = new ShapeBound();
	}
	
	public void SetShape(Shape shape) {
		shape_ = shape;
		if(shape != null) {
			//bound set
			bound_.SetBound(shape_.GetRect());
		}
	}
	
	public void SetEditType(EditEnumType type) {
		if(type == EditEnumType.kEditDelete) {
			if(shape_ != null) {
				DrawableShapeList.getInstance().RemoveShape(shape_);
				Deselect();
				shape_ = null;
			}
			PaintStateManager.GetInstance().SetState(StateType.kStateInit);
			return;
		}
		editType_ = type;
		if(shape_ != null) {
			bound_.SetBound(shape_.GetRect());
		}
	}
	
	public boolean Select(float x, float y) {
		if(editType_ == EditEnumType.kEditFreeTransform) {
			selectedVertexIndex_ = shape_.GetNearVertexIndex(x, y);
			return (selectedVertexIndex_ != -1);
		}
		
		oldTouchX_ = x;
		oldTouchY_ = y;
	
		if(editType_ == EditEnumType.kEditTranslate) {
			return shape_.IsSelected(x, y);
		}
		
		selectedVertexIndex_ = bound_.GetNearVertexIndex(x, y);
		if(selectedVertexIndex_ < 0) {
			return false;
		}
		selectedVertex_ = bound_.GetVertices().get(selectedVertexIndex_);
		selectedVertexOld_[0] = selectedVertex_[0];
		selectedVertexOld_[1] = selectedVertex_[1];
		selectedVertexCounter_ = bound_.GetVertices().get((selectedVertexIndex_+2)%4);
		
		return (selectedVertex_ != null);
	}
	
	public void Move(float x, float y) {
		switch(editType_) {
		case kEditFreeTransform:
			editFreeTransform(x, y);
			break;
		case kEditScale:
			editScale(x, y);
			break;
		case kEditRotate:
			editRotate(x, y);
			break;
		case kEditTranslate:
			editTranslate(x, y);
			break;
		default:
			break;
		}
	}
	
	public void Deselect() {
		selectedVertex_ = null;
		selectedVertexOld_[0] = 0;
		selectedVertexOld_[0] = 1;
		selectedVertexIndex_ = -1;
		selectedVertexCounter_ = null;
	}
	
	private void editFreeTransform(float touchX, float touchY) {
		if(selectedVertexIndex_ < 0 || !shape_.IsFreeTransformable()) {
			return;
		}
		shape_.FreeTransform(selectedVertexIndex_, touchX, touchY);
	}
	
	private void editScale(float touchX, float touchY) {
		if(selectedVertex_ == null || !shape_.IsScalable()) {
			return;
		}
		float scaleX = (touchX - selectedVertexCounter_[0]) / (selectedVertexOld_[0] - selectedVertexCounter_[0]);
		float scaleY = (touchY - selectedVertexCounter_[1]) / (selectedVertexOld_[1] - selectedVertexCounter_[1]);
		shape_.Scale(scaleX, scaleY, selectedVertexCounter_[0], selectedVertexCounter_[1]);
		bound_.Scale(scaleX, scaleY, selectedVertexCounter_[0], selectedVertexCounter_[1]);
		selectedVertexOld_[0] = selectedVertex_[0];
		selectedVertexOld_[1] = selectedVertex_[1];
	}
	
	private void editRotate(float touchX, float touchY) {
		if(selectedVertex_ == null || !shape_.IsRotatable()) {
			return;
		}			
		float[] center = bound_.GetCenter();
		float angle = (float)Math.atan2(touchY - center[1], touchX - center[0]);
		float angleOld = (float)Math.atan2(selectedVertex_[1] - center[1], selectedVertex_[0] - center[0]);
		shape_.Rotate(angle - angleOld, center[0], center[1]);
		bound_.Rotate(angle - angleOld, center[0], center[1]);
	}
	
	private void editTranslate(float touchX, float touchY) {
		float dx = (touchX - oldTouchX_);
		float dy = (touchY - oldTouchY_);
		oldTouchX_ = touchX;
		oldTouchY_ = touchY;
		shape_.Translate(dx, dy);
		bound_.Translate(dx, dy);
	}
	
	public void Draw(GL10 gl) {
		if(shape_ == null) {
			return;
		}
		switch(editType_) {
		case kEditFreeTransform:
			//아 이거 그리는거 빼야하는데 ㅁㄴㅇㄹ
			gl.glPushMatrix();
			gl.glColor4f(0, 1, 0, 1);
			ArrayList<float[]> vertices = shape_.GetVertices();
			for(float[] v : vertices) {
				GLESHelper.DrawPoint(gl, v[0], v[1]);
			}
			gl.glPopMatrix();
			break;
		case kEditRotate:
		case kEditScale:
		case kEditTranslate:
			bound_.Draw(gl);
			break;
		default:
			break;
		}
	}
}
