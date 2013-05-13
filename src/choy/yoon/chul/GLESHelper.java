package choy.yoon.chul;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class GLESHelper {
	public static FloatBuffer ArrayToBuffer(float[][] arr) {
		ByteBuffer vbb = ByteBuffer.allocateDirect(arr.length * 3 * 4);
		vbb.order(ByteOrder.nativeOrder());
		FloatBuffer fb = vbb.asFloatBuffer();
		for(int i=0; i < arr.length; ++i) {
			fb.put(arr[i]);
		}
		fb.position(0);
		return fb;
	}
}
