package choy.yoon.chul;

import android.content.Context;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.Toast;

public class PaintStateManager {
	
	public enum StateType {
		kStateInit,
		kStateDraw,
	}
	
	private static PaintStateManager instance_ = null;
	private IState state_;
	private InitState initState_;
	private DrawState drawState_;
	private Context context_;
	
	private PaintStateManager() {
		initState_ = new InitState();
		drawState_ = new DrawState();
		state_ = initState_;
	}
	
	public static synchronized PaintStateManager GetInstance() {
		if(instance_ == null) {
			instance_ = new PaintStateManager();
		}
		return instance_;
	}
	
	public void SetContext(Context context) {
		context_ = context;
	}
	
	public void SetState(StateType type) {
		switch(type) {
		case kStateInit:
			state_ = initState_;
			break;
		case kStateDraw:
			state_ = drawState_;
			break;
		default:
			state_ = initState_;
			break;
		}
	}
	
	public void OnDraw(MenuItem item) {
		drawState_.SetShape(new ShapeType(item.getItemId()));
		state_ = drawState_;
	}
	
	public void OnTouch(MotionEvent event) {
		state_.onTouch(event);
	}
}
