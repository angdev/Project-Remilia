package choy.yoon.chul.State;

import android.view.MotionEvent;
import choy.yoon.chul.Shape.DrawableShapeList;
import choy.yoon.chul.Shape.Shape;

public class InitState implements IState {
	
	private static StateType type_ = StateType.kStateInit;
	
	public InitState() {
		
	}

	@Override
	public void onTouch(MotionEvent event) {
		// TODO Auto-generated method stub
		Shape shape = DrawableShapeList.getInstance().PickShape(event.getX(), event.getY());
		if(shape != null) {
			//->EditState
			EditState state = (EditState) PaintStateManager.GetInstance().GetState(StateType.kStateEdit);
			state.SetShape(shape);
			PaintStateManager.GetInstance().SetState(StateType.kStateEdit);
			PaintStateManager.GetInstance().GetState().onTouch(event);
		}
	}

	@Override
	public StateType GetType() {
		return type_;
	}
}
