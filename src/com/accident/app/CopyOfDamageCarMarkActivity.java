package com.accident.app;

import java.io.File;
import java.io.FileOutputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class CopyOfDamageCarMarkActivity extends Activity {
	private Button btn_save, btn_resume;
	private ImageView iv_canvas;
	private Bitmap baseBitmap;
	private Canvas canvas;
	private Paint paint;
	public Bitmap bt;
	boolean ismove = false;
	boolean isDraw = false;
	private static final int SWIPE_MIN_DISTANCE = 50;
	int i = 0;
	private float x = 0;

	int[] ferrari_image = { R.drawable.b_ferrari_f152_000_0000,
			R.drawable.b_ferrari_f152_000_0001,
			R.drawable.b_ferrari_f152_000_0002,
			R.drawable.b_ferrari_f152_000_0003,
			R.drawable.b_ferrari_f152_000_0004,
			R.drawable.b_ferrari_f152_000_0005,
			R.drawable.b_ferrari_f152_000_0006,
			R.drawable.b_ferrari_f152_000_0007,
			R.drawable.b_ferrari_f152_000_0008,
			R.drawable.b_ferrari_f152_000_0009,
			R.drawable.b_ferrari_f152_000_0010,
			R.drawable.b_ferrari_f152_000_0011,
			R.drawable.b_ferrari_f152_000_0012,
			R.drawable.b_ferrari_f152_000_0013,
			R.drawable.b_ferrari_f152_000_0014,
			R.drawable.b_ferrari_f152_000_0015,
			R.drawable.b_ferrari_f152_000_0016,
			R.drawable.b_ferrari_f152_000_0017,
			R.drawable.b_ferrari_f152_000_0018,
			R.drawable.b_ferrari_f152_000_0019,
			R.drawable.b_ferrari_f152_000_0020,
			R.drawable.b_ferrari_f152_000_0021,
			R.drawable.b_ferrari_f152_000_0022,
			R.drawable.b_ferrari_f152_000_0023,
			R.drawable.b_ferrari_f152_000_0024,
			R.drawable.b_ferrari_f152_000_0025,
			R.drawable.b_ferrari_f152_000_0026,
			R.drawable.b_ferrari_f152_000_0027,
			R.drawable.b_ferrari_f152_000_0028,
			R.drawable.b_ferrari_f152_000_0029,
			R.drawable.b_ferrari_f152_000_0030,
			R.drawable.b_ferrari_f152_000_0031,
			R.drawable.b_ferrari_f152_000_0032,
			R.drawable.b_ferrari_f152_000_0033,
			R.drawable.b_ferrari_f152_000_0034,
			R.drawable.b_ferrari_f152_000_0035 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.damagecarmark);

		// The initialization of a brush, brush width is 5, color is red
		paint = new Paint();
		paint.setStrokeWidth(5);
		paint.setColor(Color.RED);

		iv_canvas = (ImageView) findViewById(R.id.iv_canvas);
		btn_save = (Button) findViewById(R.id.btn_save);
		btn_resume = (Button) findViewById(R.id.btn_resume);

		btn_save.setOnClickListener(click);
		btn_resume.setOnClickListener(click);
		iv_canvas.setOnTouchListener(touch);

		bt = BitmapFactory.decodeResource(getResources(), R.drawable.dot);
	}

	private View.OnTouchListener touch = new OnTouchListener() {
		// Coordinate definition finger touch
		float startX;
		float startY;

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			// Press the user action
			case MotionEvent.ACTION_DOWN:
				// The first drawing initializes the memory image, specify the
				// background is white
				if (baseBitmap == null) {
					

					baseBitmap = BitmapFactory.decodeResource(getResources(),
							ferrari_image[i]).copy(Bitmap.Config.ARGB_8888,
							true);

					canvas = new Canvas(baseBitmap);

					// canvas.drawColor(Color.WHITE);
				}
				// Recording began to touch point coordinate
				startX = event.getX();
				startY = event.getY();
				break;
			// The user's finger on the screen of mobile action
			case MotionEvent.ACTION_MOVE:

				x = event.getX();
				// y = event.getY();

				if (startX - x > SWIPE_MIN_DISTANCE) {
					RotationDown();
				} else if (x - startX > SWIPE_MIN_DISTANCE) {

					RotationUp();
					// Toast.makeText(RotateTest.this, "Right Swipe",
					// Toast.LENGTH_SHORT).show();
				}

				break;
			case MotionEvent.ACTION_UP:

				float stopX = event.getX();
				float stopY = event.getY();
				if (startX == stopX) {
					// According to the coordinates of the two points, drawing
					// lines
					// canvas.drawLine(startX, startY, stopX, stopY, paint);

					canvas.drawBitmap(bt, startX, startY, paint);
					isDraw = true;

					// The pictures to the ImageView
					iv_canvas.setImageBitmap(baseBitmap);
				} else if (startX - stopX > 50 || stopX - startX > 50) {

					if (isDraw) {
						saveBitmap();
						isDraw = false;
					}

					baseBitmap = null;
				}
				break;
			default:
				break;
			}
			return true;
		}
	};

	private void RotationUp() {

		if (i < 35) {
			i++;
			// iv_canvas.setImageLevel(i);
			// iv_canvas.setBackgroundResource(ferrari_image[i]);
			iv_canvas.setImageBitmap(BitmapFactory.decodeResource(
					getResources(), ferrari_image[i]));
		} else {
			i = 0;
			iv_canvas.setImageBitmap(BitmapFactory.decodeResource(
					getResources(), ferrari_image[i]));
		}
	}

	private void RotationDown() {

		if (i > 0) {
			i--;
			iv_canvas.setImageBitmap(BitmapFactory.decodeResource(
					getResources(), ferrari_image[i]));
		} else {
			i = 35;
			iv_canvas.setImageBitmap(BitmapFactory.decodeResource(
					getResources(), ferrari_image[i]));
		}
	}

	private View.OnClickListener click = new OnClickListener() {

		@Override
		public void onClick(View v) {

			switch (v.getId()) {
			case R.id.btn_save:
				saveBitmap();
				break;
			case R.id.btn_resume:
				resumeCanvas();
				break;
			default:
				break;
			}
		}
	};

	/**
	 * 114 * Save the image to the SD card. 115
	 */
	protected void saveBitmap() {
		try {
			// Save the image to the SD card.
			
			File file = new File(Environment.getExternalStorageDirectory(),
					System.currentTimeMillis() + ".png");
			FileOutputStream stream = new FileOutputStream(file);
			baseBitmap.compress(CompressFormat.PNG, 100, stream);
			Toast.makeText(CopyOfDamageCarMarkActivity.this,
					"Save the picture of success", 0).show();

			// Android equipment Gallery application will only at boot time
			// scanning system folder
			// The simulation of a media loading broadcast, for the preservation
			// of images can be viewed in Gallery
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_MEDIA_MOUNTED);
			intent.setData(Uri.fromFile(Environment
					.getExternalStorageDirectory()));
			sendBroadcast(intent);
		} catch (Exception e) {
			Toast.makeText(CopyOfDamageCarMarkActivity.this, "Save failed", 0).show();
			e.printStackTrace();
		}
	}

	/**
	 * 139 * Clear the drawing board 140
	 */
	protected void resumeCanvas() {
		// Manually clear the drawing board, to create a drawing board
		if (baseBitmap != null) {

			baseBitmap = BitmapFactory.decodeResource(getResources(),
					ferrari_image[i]).copy(Bitmap.Config.ARGB_8888, true);
			canvas = new Canvas(baseBitmap);
			// canvas.drawColor(Color.WHITE);
			iv_canvas.setImageBitmap(baseBitmap);
			Toast.makeText(CopyOfDamageCarMarkActivity.this,
					"Clear the drawing board, can be re started drawing", 0)
					.show();
		}
	}
}