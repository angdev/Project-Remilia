package choy.yoon.chul.State;

import choy.yoon.chul.PaintStateManager;
import choy.yoon.chul.PaintStateManager.StateType;
import choy.yoon.chul.Shape.DrawableShapeList;
import choy.yoon.chul.Shape.Shape;
import choy.yoon.chul.Shape.ShapeEnumType;
import choy.yoon.chul.Shape.ShapeLine;
import choy.yoon.chul.Shape.ShapeType;
import android.view.MotionEvent;

public class DrawState implements IState {
	private ShapeType shapeType_;
	private Shape currentShape_;
	
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
