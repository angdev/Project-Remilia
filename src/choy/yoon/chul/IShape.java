package choy.yoon.chul;

import javax.microedition.khronos.opengles.GL10;

public interface IShape {
	public void Draw(GL10 gl);
	public ShapeEnumType GetType();
}
