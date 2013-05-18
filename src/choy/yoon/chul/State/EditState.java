package choy.yoon.chul.State;

import android.view.MotionEvent;
import choy.yoon.chul.Shape.DrawableShapeList;
import choy.yoon.chul.Shape.Shape;
import choy.yoon.chul.Shape.ShapeEditor;

public class EditState implements IState {

	private static StateType type_ = StateType.kStateEdit;
	
	private Shape shape_;
	private ShapeEditor shapeEditor_;

	public EditState() {
		shape_ = null;
		shapeEditor_ = new ShapeEditor();
		DrawableShapeList.getInstance().SetShapeEditor(shapeEditor_);
	}
	
	void SetEditType(EditEnumType type) {
		if(shapeEditor_ != null) {
			shapeEditor_.SetEditType(type);
		}
	}
	
	public void SetShape(Shape shape) {
		shape_ = shape;
		shapeEditor_.SetShape(shape);
	}
	
	public Shape GetShape() {
		return shape_;
	}

	@Override
	public void onTouch(MotionEvent event) {
		// TODO Auto-generated method stub
		if(shape_ != null) {
			switch(event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if(!shapeEditor_.Select(event.getX(), event.getY())) {
					shapeEditor_.SetShape(null);
					PaintStateManager.GetInstance().SetState(StateType.kStateInit);
					PaintStateManager.GetInstance().GetState(StateType.kStateInit).onTouch(event);
				}
				break;
			case MotionEvent.ACTION_MOVE:
				shapeEditor_.Move(event.getX(), event.getY());
				break;
			case MotionEvent.ACTION_UP:
				shapeEditor_.Deselect();
			}
		}
	}

	@Override
	public StateType GetType() {
		return type_;
	}
}
