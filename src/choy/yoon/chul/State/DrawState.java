package choy.yoon.chul.State;

import android.view.MotionEvent;
import choy.yoon.chul.Shape.DrawableShapeList;
import choy.yoon.chul.Shape.Shape;
import choy.yoon.chul.Shape.ShapeDot;
import choy.yoon.chul.Shape.ShapeEllipse;
import choy.yoon.chul.Shape.ShapeEnumType;
import choy.yoon.chul.Shape.ShapeLine;
import choy.yoon.chul.Shape.ShapePolygon;
import choy.yoon.chul.Shape.ShapePolyline;
import choy.yoon.chul.Shape.ShapeRectangle;
import choy.yoon.chul.State.PaintStateManager.StateType;

public class DrawState implements IState {
	private Shape currentShape_;
	private float[] startPoint_;
	
	public DrawState() {
		startPoint_ = new float[]{0, 0, 0};
	}

	@Override
	public void onTouch(MotionEvent event) {
		if(currentShape_ == null) {
			return;
		}
		
		if(event.getAction() == MotionEvent.ACTION_DOWN) {
			startPoint_[0] = event.getX();
			startPoint_[1] = event.getY();
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
		else if(currentShape_.GetType() == ShapeEnumType.kShapeRectangle) {
			ShapeRectangle rect = (ShapeRectangle)currentShape_;
			switch(event.getAction()) {
			case MotionEvent.ACTION_MOVE:
				rect.SetCenter((event.getX() + startPoint_[0])/2, (event.getY() + startPoint_[1])/2);
				rect.SetSize(Math.abs(event.getX() - startPoint_[0]), Math.abs(event.getY() - startPoint_[1]));
				break;
			case MotionEvent.ACTION_UP:
				currentShape_ = null;
				PaintStateManager.GetInstance().SetState(StateType.kStateInit);
				break;
			}
		}
		else if(currentShape_.GetType() == ShapeEnumType.kShapeEllipse) {
			ShapeEllipse ellipse = (ShapeEllipse)currentShape_;
			switch(event.getAction()) {
			case MotionEvent.ACTION_MOVE:
				ellipse.SetCenter((event.getX() + startPoint_[0])/2, (event.getY() + startPoint_[1])/2);
				ellipse.SetAxis(Math.abs(event.getX() - startPoint_[0])/2, Math.abs(event.getY() - startPoint_[1])/2);
				break;
			case MotionEvent.ACTION_UP:
				currentShape_ = null;
				PaintStateManager.GetInstance().SetState(StateType.kStateInit);
				break;
			}
		}
		else if(currentShape_.GetType() == ShapeEnumType.kShapePolygon) {
			ShapePolygon polygon = (ShapePolygon)currentShape_;
			switch(event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				polygon.AddVertex(event.getX(), event.getY());
				break;
			case MotionEvent.ACTION_UP:
				if(!polygon.IsEditing()) {
					currentShape_ = null;
					PaintStateManager.GetInstance().SetState(StateType.kStateInit);
				}
				break;
			}
		}
		else if(currentShape_.GetType() == ShapeEnumType.kShapePolyline) {
			ShapePolyline polyline = (ShapePolyline)currentShape_;
			switch(event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				polyline.AddVertex(event.getX(), event.getY());
				break;
			case MotionEvent.ACTION_UP:
				if(!polyline.IsEditing()) {
					currentShape_ = null;
					PaintStateManager.GetInstance().SetState(StateType.kStateInit);
				}
				break;
			}
		}
	}
	
	public void SetShape(ShapeEnumType type) {
		switch(type) {
		case kShapeDot:
			currentShape_ = new ShapeDot();
			break;
		case kShapeLine:
			currentShape_ = new ShapeLine();
			break;
		case kShapePolyline:
			currentShape_ = new ShapePolyline();
			break;
		case kShapeRectangle:
			currentShape_ = new ShapeRectangle();
			break;
		case kShapePolygon:
			currentShape_ = new ShapePolygon();
			break;
		case kShapeEllipse:
			currentShape_ = new ShapeEllipse();
			break;
		default:
			break;
		}
		//리스트에 추가
		DrawableShapeList.getInstance().AddShape(currentShape_);	
	}
}
