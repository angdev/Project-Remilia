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
	
	//scale
	private int selectedVertexIndex_;
	private float[] selectedVertexOld_ = null;
	private float[] selectedVertex_ = null;
	private float[] selectedVertexCounter_ = null;
	
	public ShapeEditor() {
		editType_ = EditEnumType.kEditFreeTransform;
		selectedVertexOld_ = new float[3];
		bound_ = new BoundRect();
	}
	
	public void SetShape(Shape shape) {
		shape_ = shape;
		if(shape != null) {
			//bound set
			bound_.SetBound(shape_.GetRect());
		}
	}
	
	public void SetEditType(EditEnumType type) {
		editType_ = type;
	}
	
	public boolean SelectVertex(float x, float y) {
		if(editType_ == EditEnumType.kEditFreeTransform) {
			selectedVertex_ = shape_.GetNearVertex(x, y);
			return (selectedVertex_ != null);
		}
		selectedVertexIndex_ = bound_.GetNearVertexIndex(x, y);
		if(selectedVertexIndex_ < 0) {
			return false;
		}
		selectedVertex_ = bound_.GetVertices()[selectedVertexIndex_];
		selectedVertexOld_[0] = selectedVertex_[0];
		selectedVertexOld_[1] = selectedVertex_[1];
		selectedVertexCounter_ = bound_.GetVertices()[(selectedVertexIndex_+2)%4];
		
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
			MathHelper.ScaleVertices(shape_.GetVertices(), scaleX, scaleY,
					selectedVertexCounter_[0], selectedVertexCounter_[1]);
			MathHelper.ScaleVertices(bound_.GetVertices(), scaleX, scaleY,
					selectedVertexCounter_[0], selectedVertexCounter_[1]);
			selectedVertexOld_[0] = selectedVertex_[0];
			selectedVertexOld_[1] = selectedVertex_[1];
			//Need flip
			if(scaleX < 0 || scaleY < 0) {
				if(scaleX < 0) {
					bound_.FlipX();
				}
				if(scaleY < 0) {
					bound_.FlipY();
				}
			}
			break;
		case kEditRotate:
			if(selectedVertex_ == null) {
				return;
			}			
			float[] center = bound_.GetCenter();
			float angle = (float)Math.atan2(y - center[1], x - center[0]);
			float angleOld = (float)Math.atan2(selectedVertex_[1] - center[1], selectedVertex_[0] - center[0]);
			MathHelper.RotateVertices(shape_.GetVertices(), angle - angleOld, center[0], center[1]);
			MathHelper.RotateVertices(bound_.GetVertices(), angle - angleOld, center[0], center[1]);
			break;
		case kEditTranslate:
			if(selectedVertex_ == null) {
				return;
			}
			float dx = (x - selectedVertex_[0]);
			float dy = (y - selectedVertex_[1]);
			MathHelper.TranslateVertices(shape_.GetVertices(), dx, dy);
			MathHelper.TranslateVertices(bound_.GetVertices(), dx, dy);
			break;
		}
	}
	
	public void DeselectVertex() {
		selectedVertex_ = null;
		selectedVertexOld_[0] = 0;
		selectedVertexOld_[0] = 1;
		selectedVertexIndex_ = -1;
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
			float[][] boundVertices = bound_.GetVertices();
			GLESHelper.DrawPolygon(gl, boundVertices);
			for(float[] v : boundVertices) {
				GLESHelper.DrawPoint(gl, v[0], v[1]);
			}
			break;
		}
	}
}
