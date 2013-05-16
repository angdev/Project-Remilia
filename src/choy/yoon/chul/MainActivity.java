package choy.yoon.chul;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.Toast;
import choy.yoon.chul.State.PaintStateManager;

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
		PaintStateManager.GetInstance().OnDraw(item);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		PaintStateManager.GetInstance().OnTouch(event);
		return true;
	}

	public void onEdit(MenuItem item) {
		PaintStateManager.GetInstance().OnEdit(item);
	}

	public void onFill(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.action_fill_color:
			Toast.makeText(this, "Color", Toast.LENGTH_SHORT).show();
			new ColorPickerDialog(this, this, selectedColor).show();
			break;
		case R.id.action_fill_texture:
			Toast.makeText(this, "Texture", Toast.LENGTH_SHORT).show();
			break;
		}
	}

	@Override
	public void colorChanged(int color) {
		selectedColor = color;
	}
}
