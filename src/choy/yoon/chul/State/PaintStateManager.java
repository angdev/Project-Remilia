package choy.yoon.chul.State;

import android.content.Context;
import android.view.MenuItem;
import android.view.MotionEvent;
import choy.yoon.chul.R;
import choy.yoon.chul.Shape.ShapeEnumType;

public class PaintStateManager {
	
	public enum StateType {
		kStateInit,
		kStateDraw,
		kStateEdit,
	}
	
	private static PaintStateManager instance_ = null;
	private IState state_;
	private InitState initState_;
	private DrawState drawState_;
	private EditState editState_;
	private Context context_;
	
	private PaintStateManager() {
		initState_ = new InitState();
		drawState_ = new DrawState();
		editState_ = new EditState();
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
		case kStateEdit:
			state_ = editState_;
			break;
		default:
			state_ = initState_;
			break;
		}
	}
	
	public IState GetState(StateType type) {
		switch(type) {
		case kStateInit:
			return initState_;
		case kStateDraw:
			return drawState_;
		case kStateEdit:
			return editState_;
		default:
			return initState_;
		}
	}
	
	public void OnDraw(MenuItem item) {
		ShapeEnumType type;
		switch(item.getItemId()) {
		case R.id.action_line:
			type = ShapeEnumType.kShapeLine;
			break;
		case R.id.action_dot:
			type = ShapeEnumType.kShapeDot;
			break;
		case R.id.action_ellipse:
			type = ShapeEnumType.kShapeEllipse;
			break;
		default:
			type = ShapeEnumType.kShapeNull;
			break;
		}
		drawState_.SetShape(type);
		state_ = drawState_;
	}
	
	public void OnEdit(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.action_edit_scale:
			editState_.SetEditType(EditEnumType.kEditScale);
			break;
		case R.id.action_edit_rotate:
			editState_.SetEditType(EditEnumType.kEditRotate);
			break;
		case R.id.action_edit_transform:
			editState_.SetEditType(EditEnumType.kEditFreeTransform);
			break;
		case R.id.action_edit_translate:
			editState_.SetEditType(EditEnumType.kEditTranslate);
			break;
		}
	}
	
	public void OnTouch(MotionEvent event) {
		state_.onTouch(event);
	}
}
