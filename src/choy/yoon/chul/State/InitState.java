package choy.yoon.chul.State;

import android.view.MotionEvent;
import choy.yoon.chul.Shape.DrawableShapeList;
import choy.yoon.chul.Shape.Shape;

//초기 상태
public class InitState implements IState {
	
	private static StateType type_ = StateType.kStateInit;
	
	public InitState() {
		
	}

	@Override
	public void onTouch(MotionEvent event) {
		//초기 상태에서 도형을 선택하면 편집 상태로 들어간다.
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
