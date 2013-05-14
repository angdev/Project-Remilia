package choy.yoon.chul.State;

import android.view.MotionEvent;
import choy.yoon.chul.Shape.DrawableShape;
import choy.yoon.chul.Shape.DrawableShapeList;
import choy.yoon.chul.State.PaintStateManager.StateType;

public class InitState implements IState {
	public InitState() {
		
	}

	@Override
	public void onTouch(MotionEvent event) {
		// TODO Auto-generated method stub
		DrawableShape shape = DrawableShapeList.getInstance().PickShape(event.getX(), event.getY());
		if(shape != null) {
			//->EditState
			EditState state = (EditState) PaintStateManager.GetInstance().GetState(StateType.kStateEdit);
			state.SetShape(shape);
			PaintStateManager.GetInstance().SetState(StateType.kStateEdit);
		}
	}
}
