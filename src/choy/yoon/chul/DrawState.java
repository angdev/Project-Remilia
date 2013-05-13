package choy.yoon.chul;

import choy.yoon.chul.PaintStateManager.StateType;
import android.view.MotionEvent;

public class DrawState implements IState {
	private ShapeType shapeType_;
	private IShape currentShape_;
	
	public DrawState() {
		
	}

	@Override
	public void onTouch(MotionEvent event) {
		if(currentShape_.GetType() == ShapeEnumType.kShapeLine) {
			switch(event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				((ShapeLine)currentShape_).SetStartPoint(event.getX(), event.getY());
				((ShapeLine)currentShape_).SetEndPoint(event.getX(), event.getY());
				break;
			case MotionEvent.ACTION_MOVE:
				((ShapeLine)currentShape_).SetEndPoint(event.getX(), event.getY());
				break;
			case MotionEvent.ACTION_UP:
				currentShape_ = null;
				PaintStateManager.GetInstance().SetState(StateType.kStateInit);
				break;
			}
		}
	}
	
	public void SetShape(ShapeType type) {
		shapeType_ = type;
		
		//factory?
		if(type.GetType() == ShapeEnumType.kShapeLine) {
			currentShape_ = new ShapeLine();
			//리스트에 추가
			DrawableShapeList.getInstance().AddShape(currentShape_);
		}
	}
}
