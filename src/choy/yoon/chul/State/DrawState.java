package choy.yoon.chul.State;

import android.view.MotionEvent;
import choy.yoon.chul.Shape.DrawableShapeList;
import choy.yoon.chul.Shape.Shape;
import choy.yoon.chul.Shape.ShapeEnumType;
import choy.yoon.chul.Shape.ShapeLine;
import choy.yoon.chul.State.PaintStateManager.StateType;

public class DrawState implements IState {
	private ShapeEnumType shapeType_;
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
	
	//ShapeType ������ ��������
	public void SetShape(ShapeEnumType type) {
		shapeType_ = type;
		
		//factory?
		if(type == ShapeEnumType.kShapeLine) {
			currentShape_ = new ShapeLine();
			//����Ʈ�� �߰�
			DrawableShapeList.getInstance().AddShape(currentShape_);
		}
	}
}
