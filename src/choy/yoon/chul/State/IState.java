package choy.yoon.chul.State;

import android.view.MotionEvent;

public interface IState {
	public void onTouch(MotionEvent event);
	public StateType GetType();
}
