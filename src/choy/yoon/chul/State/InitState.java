package choy.yoon.chul.State;

import choy.yoon.chul.Shape.DrawableShapeList;
import android.view.MotionEvent;

public class InitState implements IState {
	public InitState() {
		
	}

	@Override
	public void onTouch(MotionEvent event) {
		// TODO Auto-generated method stub
		DrawableShapeList.getInstance().PickShape(event.getX(), event.getY());
	}
}
