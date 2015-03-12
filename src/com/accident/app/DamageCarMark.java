package com.accident.app;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

import com.accident.app.util.Config;

public class DamageCarMark extends BaseFragment implements OnClickListener {
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
	private static final String TAG = DamageCarMark.class.getSimpleName();

	private HashMap<Integer, Bitmap> fileMap = new HashMap<Integer, Bitmap>();
	ImageAdapter imgAdapter;

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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.damagecarmark, container,
				false);

		// The initialization of a brush, brush width is 5, color is red
		paint = new Paint();
		paint.setStrokeWidth(5);
		paint.setColor(Color.RED);

		iv_canvas = (ImageView) rootView.findViewById(R.id.iv_canvas);
		btn_save = (Button) rootView.findViewById(R.id.btn_save);
		btn_resume = (Button) rootView.findViewById(R.id.btn_resume);
		
		Gallery ga = (Gallery)rootView.findViewById(R.id.gallery01);
		imgAdapter = new ImageAdapter(getActivity());
		ga.setAdapter(imgAdapter);

		btn_save.setOnClickListener(this);
		btn_resume.setOnClickListener(this);
		iv_canvas.setOnTouchListener(touch);

		bt = BitmapFactory.decodeResource(getResources(), R.drawable.dot);
		return rootView;
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
						//saveBitmap();
						fileMap.put(i, baseBitmap);
						imgAdapter.notifyDataSetChanged();
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

		//Toast.makeText(getActivity(), fileMap.toString(), 1).show();
		if (i < 35) {
			i++;
			// iv_canvas.setImageLevel(i);
			// iv_canvas.setBackgroundResource(ferrari_image[i]);
			
			if (fileMap.containsKey(i)) {
				iv_canvas.setImageBitmap(fileMap.get(i));
			} else {
				iv_canvas.setImageBitmap(BitmapFactory.decodeResource(
						getResources(), ferrari_image[i]));
			}
		} else {
			i = 0;
			if (fileMap.containsKey(i)) {
				iv_canvas.setImageBitmap(fileMap.get(i));
			} else {
				iv_canvas.setImageBitmap(BitmapFactory.decodeResource(
						getResources(), ferrari_image[i]));
			}
		}
	}

	private void RotationDown() {
		//Toast.makeText(getActivity(), fileMap.toString(), 1).show();

		if (i > 0) {
			i--;
			if (fileMap.containsKey(i)) {
				iv_canvas.setImageBitmap(fileMap.get(i));
			} else {
				iv_canvas.setImageBitmap(BitmapFactory.decodeResource(
						getResources(), ferrari_image[i]));
			}
		} else {
			i = 35;
			if (fileMap.containsKey(i)) {
				iv_canvas.setImageBitmap(fileMap.get(i));
			} else {
				iv_canvas.setImageBitmap(BitmapFactory.decodeResource(
						getResources(), ferrari_image[i]));
			}
		}
	}

	/**
	 * 114 * Save the image to the SD card. 115
	 */
	protected void saveBitmap() {
		try {
			// Save the image to the SD card.

			File mediaStorageDir = new File(
					Environment
							.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
					Config.IMAGE_TEMP_DIRECTORY_NAME);

			// Create the storage directory if it does not exist
			if (!mediaStorageDir.exists()) {
				if (!mediaStorageDir.mkdirs()) {
					Log.d(TAG, "Oops! Failed create "
							+ Config.IMAGE_DIRECTORY_NAME + " directory");
					return;
				}
			}
			// Create a media file name
			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
					Locale.getDefault()).format(new Date());

			String filename = mediaStorageDir.getPath() + File.separator
					+ "IMG_" + timeStamp + ".jpg";

			File file = new File(filename);
			FileOutputStream stream = new FileOutputStream(file);
			baseBitmap.compress(CompressFormat.PNG, 100, stream);
			Toast.makeText(getActivity(), "Save the picture of success", 0)
					.show();
			
			// Android equipment Gallery application will only at boot time
			// scanning system folder
			// The simulation of a media loading broadcast, for the preservation
			// of images can be viewed in Gallery
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_MEDIA_MOUNTED);
			intent.setData(Uri.fromFile(Environment
					.getExternalStorageDirectory()));
			getActivity().sendBroadcast(intent);

			
		} catch (Exception e) {
			Toast.makeText(getActivity(), "Save failed", 0).show();
			e.printStackTrace();
		}
	}

	/**
	 ** Clear the drawing board 
	 */
	protected void resumeCanvas() {
		// Manually clear the drawing board, to create a drawing board
		if (baseBitmap != null) {

			baseBitmap = BitmapFactory.decodeResource(getResources(),
					ferrari_image[i]).copy(Bitmap.Config.ARGB_8888, true);
			canvas = new Canvas(baseBitmap);
			// canvas.drawColor(Color.WHITE);
			iv_canvas.setImageBitmap(baseBitmap);
			Toast.makeText(getActivity(),
					"Clear the drawing board, can be re started drawing", 0)
					.show();
		}
	}

	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
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
	
	
	public class ImageAdapter extends BaseAdapter {

        private Context ctx;
        int imageBackground;
       
        public ImageAdapter(Context c) {
            ctx = c;
            TypedArray ta = getActivity().obtainStyledAttributes(R.styleable.Gallery1);
            imageBackground = ta.getResourceId(R.styleable.Gallery1_android_galleryItemBackground, 1);
            ta.recycle();
        }

        @Override
        public int getCount() {
           
            return fileMap.size();
        }

        @Override
        public Object getItem(int arg0) {
           
            return arg0;
        }

        @Override
        public long getItemId(int arg0) {
           
            return arg0;
        }

        @Override
        public View getView(int arg0, View arg1, ViewGroup arg2) {
            ImageView iv = new ImageView(ctx);
            //iv.setImageResource(fileMap[arg0]);
            iv.setImageBitmap(fileMap.get(arg0));
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            iv.setLayoutParams(new Gallery.LayoutParams(70,70));
            iv.setBackgroundResource(imageBackground);
            return iv;
        }

    }
}