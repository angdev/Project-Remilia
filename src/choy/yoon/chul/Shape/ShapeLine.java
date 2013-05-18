package choy.yoon.chul.Shape;

import javax.microedition.khronos.opengles.GL10;

import choy.yoon.chul.GLESHelper;

public class ShapeLine extends Shape {

	private static ShapeEnumType type_ = ShapeEnumType.kShapeLine;
	
	public ShapeLine() {
		super();
		vertices_.add(new float[]{0, 0, 0});
		vertices_.add(new float[]{0, 0, 0});
	}
	
	public void SetStartPoint(float x, float y) {
		vertices_.get(0)[0] = x;
		vertices_.get(0)[1] = y;
	}
	
	public void SetEndPoint(float x, float y) {
		vertices_.get(1)[0] = x;
		vertices_.get(1)[1] = y;
	}
	
	@Override
	public boolean IsSelected(float x, float y) {
		//직선거리
		float x1 = vertices_.get(0)[0];
		float y1 = vertices_.get(0)[1];
		float x2 = vertices_.get(1)[0];
		float y2 = vertices_.get(1)[1];
		
		//기울기 구하기에는 너무 좁은 x 간격
		if(Math.abs(x2 - y1) < 0.001f) {
			//y값 사이에 있는지 검사
			return ((y1 > y) != (y2 > y));
		}
		
		float slope = (y2 - y1) / (x2 - x1);
		float distance = (float) (Math.abs(slope * x - y - slope * x1 + y1) / Math.sqrt(slope * slope + 1));
		return (((y1 > y) != (y2 > y)) && distance < 70);
	}
	
	@Override
	public void Draw(GL10 gl) {
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glLineWidth(5);
		gl.glColor4f(color_[1], color_[2], color_[3], color_[0]);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, GLESHelper.ArrayToBuffer(vertices_.toArray(new float[][]{})));
		gl.glDrawArrays(GL10.GL_LINES, 0, 2);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}
	
	@Override
	public ShapeEnumType GetType() {
		return type_;
	}

	@Override
	public boolean IsScalable() {
		return true;
	}

	@Override
	public boolean IsRotatable() {
		return true;
	}

	@Override
	public boolean IsFreeTransformable() {
		return true;
	}
}
