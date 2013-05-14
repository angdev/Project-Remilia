package choy.yoon.chul.State;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import android.view.MotionEvent;
import choy.yoon.chul.Shape.DrawableShape;

public class EditState implements IState {

	enum EditType {
		kEditTransform,
		kEditScale,
		kEditRotate,
	};
	private DrawableShape shape_;
	private EditType editType_;
	private float[] selectedVertex_;

	public EditState() {
		editType_ = EditType.kEditTransform;
		shape_ = null;
		selectedVertex_ = null;
	}

	@Override
	public void onTouch(MotionEvent event) {
		// TODO Auto-generated method stub
		if(shape_ != null) {
			if(editType_ == EditType.kEditTransform) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					selectedVertex_ = shape_.GetNearVertex(event.getX(), event.getY());
					break;
				case MotionEvent.ACTION_MOVE:
					if(selectedVertex_ != null) {
						selectedVertex_[0] = event.getX();
						selectedVertex_[1] = event.getY();
					}
					break;
				case MotionEvent.ACTION_UP:
					selectedVertex_ = null;
					break;
				}
			}
		}
	}

	public void SetShape(DrawableShape shape) {
		shape_ = shape;
	}

	public void DrawEditHelper(GL10 gl) {
		if(shape_ != null) {
			if(editType_ == EditType.kEditTransform) {
				ArrayList<float[]> vertices = shape_.GetVertices();
				for(float[] vertex : vertices) {
					
				}
			}
		}
	}
	
}
