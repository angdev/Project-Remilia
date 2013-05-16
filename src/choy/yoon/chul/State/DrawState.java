package choy.yoon.chul.State;

import android.view.MotionEvent;
import choy.yoon.chul.Shape.DrawableShapeList;
import choy.yoon.chul.Shape.Shape;
import choy.yoon.chul.Shape.ShapeDot;
import choy.yoon.chul.Shape.ShapeEnumType;
import choy.yoon.chul.Shape.ShapeLine;
import choy.yoon.chul.State.PaintStateManager.StateType;

public class DrawState implements IState {
	private Shape currentShape_;
	
	public DrawState() {
		
	}

	@Override
	public void onTouch(MotionEvent event) {
		if(currentShape_ == null) {
			return;
		}
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
		else if(currentShape_.GetType() == ShapeEnumType.kShapeDot) {
			ShapeDot dot = (ShapeDot)currentShape_;
			switch(event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				dot.SetPosition(event.getX(), event.getY());
				break;
			case MotionEvent.ACTION_MOVE:
				
				dot.SetPosition(event.getX(), event.getY());
				break;
			case MotionEvent.ACTION_UP:
				currentShape_ = null;
				PaintStateManager.GetInstance().SetState(StateType.kStateInit);
				break;
			}
		}
	}
	
	public void SetShape(ShapeEnumType type) {
		switch(type) {
		case kShapeLine:
			currentShape_ = new ShapeLine();
			break;
		case kShapeDot:
			currentShape_ = new ShapeDot();
			break;
		default:
			break;
		}
		//리스트에 추가
		DrawableShapeList.getInstance().AddShape(currentShape_);	
	}
}
