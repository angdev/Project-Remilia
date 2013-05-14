package choy.yoon.chul;

import choy.yoon.chul.State.PaintStateManager;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.widget.Toast;

public class MainActivity extends Activity implements ColorPickerDialog.OnColorChangedListener {
	private int selectedColor;
	private PaintView paintView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		PaintStateManager.GetInstance().SetContext(this);
		paintView = new PaintView(this);
		setContentView(paintView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.actions, menu);

		return true;
	}

	public void onDraw(MenuItem item) {
		Log.d("id", Integer.toString(item.getItemId()));
		//Toast.makeText(this, "Selected Item: " + item.getTitle(), Toast.LENGTH_SHORT).show();
		PaintStateManager.GetInstance().OnDraw(item);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		super.onTouchEvent(event);
		PaintStateManager.GetInstance().OnTouch(event);
		return true;
	}

	public void onFill(MenuItem item) {
		Toast.makeText(this, "Selected Item: " + item.getTitle(),
				Toast.LENGTH_SHORT).show();

		new ColorPickerDialog(this, this, selectedColor).show();
	}

	public void onEdit(MenuItem item) {
		Toast.makeText(this, "Selected Item: " + item.getTitle(),
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public void colorChanged(int color) {
		selectedColor = color;
	}
}
