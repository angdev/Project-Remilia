package choy.yoon.chul;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import choy.yoon.chul.State.EditState;
import choy.yoon.chul.State.PaintStateManager;
import choy.yoon.chul.State.PaintStateManager.StateType;

public class MainActivity extends Activity implements
		ColorPickerDialog.OnColorChangedListener {
	private int selectedColor;
	private Bitmap selectedTexture;
	private PaintView paintView;

	private static final int ACTIVITY_SELECT_IMAGE = 1000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		PaintStateManager.GetInstance().SetContext(this);
		paintView = new PaintView(this);
		setContentView(paintView);

		selectedTexture = BitmapFactory.decodeResource(getResources(),
				R.drawable.sample);
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
		switch (item.getItemId()) {
		case R.id.action_fill_color:
			new ColorPickerDialog(this, this, selectedColor).show();
			break;
		case R.id.action_fill_texture:
			Intent intent = new Intent(
					Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(intent, ACTIVITY_SELECT_IMAGE);

			break;
		}
	}

	@Override
	public void colorChanged(int color) {
		selectedColor = color;
		EditState state = (EditState) PaintStateManager.GetInstance().GetState(
				StateType.kStateEdit);
		if (state.GetShape() != null) {
			state.GetShape().SetColor(GLESHelper.GetARGB(color));
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case ACTIVITY_SELECT_IMAGE:
			if (resultCode == RESULT_OK) {
				try {
					Uri selectedImage = data.getData();
					String[] filePathColumn = { MediaStore.Images.Media.DATA };

					Cursor cursor = getContentResolver().query(selectedImage,
							filePathColumn, null, null, null);
					cursor.moveToFirst();

					int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
					String filePath = cursor.getString(columnIndex);
					cursor.close();

					textureChanged(BitmapFactory.decodeFile(filePath));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			break;
		}
	}

	public void textureChanged(Bitmap texture) {
		selectedTexture = texture;
	}
}
