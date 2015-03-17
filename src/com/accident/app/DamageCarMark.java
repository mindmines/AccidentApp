package com.accident.app;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

import com.accident.app.util.CarDamageUtiility;
import com.accident.app.util.Config;

public class DamageCarMark extends BaseFragment implements OnClickListener {
	private Button btn_save, btn_resume;

	private ImageView my_car_iv_canvas;
	public Bitmap my_car_baseBitmap;
	private Canvas my_car_canvas;
	private Paint my_car_paint;
	boolean my_car_isDraw = false;
	static int my_car_i = 0;
	private float my_car_x = 0;
	private HashMap<Integer, CarDamageUtiility> my_car_fileMap = new HashMap<Integer, CarDamageUtiility>();
	ArrayList<CarDamageUtiility> my_car_carList = new ArrayList<CarDamageUtiility>();
	HorizontalListView my_car_listview;

	private ImageView thirdparty_car_iv_canvas;
	public Bitmap thirdparty_car_baseBitmap;
	private Canvas thirdparty_car_canvas;
	private Paint thirdparty_car_paint;
	boolean thirdparty_car_isDraw = false;
	static int thirdparty_car_i = 0;
	private float thirdparty_car_x = 0;
	private HashMap<Integer, CarDamageUtiility> thirdparty_car_fileMap = new HashMap<Integer, CarDamageUtiility>();
	ArrayList<CarDamageUtiility> thirdparty_car_carList = new ArrayList<CarDamageUtiility>();
	HorizontalListView thirdparty_car_listview;

	public Bitmap bt;

	CarDamageUtiility carDamgeObj;
	private static final int SWIPE_MIN_DISTANCE = 50;
	int startindex = 0;

	private static final String TAG = DamageCarMark.class.getSimpleName();

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
		my_car_paint = new Paint();
		thirdparty_car_paint = new Paint();
		// paint.setStrokeWidth(5);
		// paint.setColor(Color.RED);

		my_car_iv_canvas = (ImageView) rootView.findViewById(R.id.iv_canvas);
		thirdparty_car_iv_canvas = (ImageView) rootView
				.findViewById(R.id.iv_canvas1);

		btn_save = (Button) rootView.findViewById(R.id.btn_save);
		btn_resume = (Button) rootView.findViewById(R.id.btn_resume);

		btn_save.setOnClickListener(this);
		btn_resume.setOnClickListener(this);

		my_car_iv_canvas.setOnTouchListener(my_car_touch);
		thirdparty_car_iv_canvas.setOnTouchListener(thirdparty_car_touch);

		// vivek work start from here
		my_car_listview = (HorizontalListView) rootView
				.findViewById(R.id.horizantallistview);
		my_car_listview.setAdapter(my_car_mAdapter);

		my_car_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				my_car_iv_canvas.setImageBitmap(my_car_carList.get(arg2)
						.getBitmap());
				my_car_baseBitmap = my_car_carList.get(arg2).getBitmap();
				my_car_i = my_car_carList.get(arg2).getIndex();
			}
		});
		
		
		thirdparty_car_listview = (HorizontalListView) rootView
				.findViewById(R.id.horizantallistview1);
		thirdparty_car_listview.setAdapter(thirdparty_car_mAdapter);

		thirdparty_car_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				thirdparty_car_iv_canvas.setImageBitmap(thirdparty_car_carList.get(arg2)
						.getBitmap());
				thirdparty_car_baseBitmap = thirdparty_car_carList.get(arg2).getBitmap();
				thirdparty_car_i = thirdparty_car_carList.get(arg2).getIndex();
			}
		});

		bt = BitmapFactory.decodeResource(getResources(),
				R.drawable.damage_mark_r_ic);
		return rootView;
	}

	private View.OnTouchListener my_car_touch = new OnTouchListener() {
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
				startindex = my_car_i;
				if (my_car_baseBitmap == null) {

					if (my_car_fileMap.containsKey(my_car_i)) {
						my_car_baseBitmap = my_car_fileMap.get(my_car_i)
								.getBitmap();
					} else {
						my_car_baseBitmap = BitmapFactory.decodeResource(
								getResources(), ferrari_image[startindex])
								.copy(Bitmap.Config.ARGB_8888, true);
					}

					my_car_canvas = new Canvas(my_car_baseBitmap);

					// canvas.drawColor(Color.WHITE);
				}
				// Recording began to touch point coordinate
				startX = event.getX();
				startY = event.getY();
				break;
			// The user's finger on the screen of mobile action
			case MotionEvent.ACTION_MOVE:

				my_car_x = event.getX();
				// y = event.getY();

				if (startX - my_car_x > SWIPE_MIN_DISTANCE) {
					RotationDownMyCar();
				} else if (my_car_x - startX > SWIPE_MIN_DISTANCE) {

					RotationUpMyCar();
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

					my_car_canvas.drawBitmap(bt, startX, startY, my_car_paint);
					my_car_isDraw = true;

					// The pictures to the ImageView
					my_car_iv_canvas.setImageBitmap(my_car_baseBitmap);

				} else if (startX - stopX > 50 || stopX - startX > 50) {

					if (my_car_isDraw && my_car_baseBitmap != null) {
						// saveBitmap();
						// baseBitmap = null;
						if (my_car_fileMap.containsKey(startindex)) {

							carDamgeObj = my_car_fileMap.get(startindex);
							carDamgeObj.setBitmap(my_car_baseBitmap);
							// carList.add(carDamgeObj);

						} else {

							carDamgeObj = new CarDamageUtiility(startindex,
									my_car_baseBitmap);
							my_car_carList.add(carDamgeObj);
						}

						my_car_fileMap.put(startindex, carDamgeObj);
						my_car_mAdapter.notifyDataSetChanged();
						my_car_isDraw = false;

						// Log.i("Hashmap Testing", fileMap.toString());
					}

					my_car_baseBitmap = null;
				}
				break;
			default:
				break;
			}
			return true;
		}
	};

	private View.OnTouchListener thirdparty_car_touch = new OnTouchListener() {
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
				startindex = thirdparty_car_i;
				if (thirdparty_car_baseBitmap == null) {

					if (thirdparty_car_fileMap.containsKey(thirdparty_car_i)) {
						thirdparty_car_baseBitmap = thirdparty_car_fileMap.get(
								thirdparty_car_i).getBitmap();
					} else {
						thirdparty_car_baseBitmap = BitmapFactory
								.decodeResource(getResources(),
										ferrari_image[thirdparty_car_i]).copy(
										Bitmap.Config.ARGB_8888, true);
					}

					thirdparty_car_canvas = new Canvas(
							thirdparty_car_baseBitmap);

					// canvas.drawColor(Color.WHITE);
				}
				// Recording began to touch point coordinate
				startX = event.getX();
				startY = event.getY();
				break;
			// The user's finger on the screen of mobile action
			case MotionEvent.ACTION_MOVE:

				thirdparty_car_x = event.getX();
				// y = event.getY();

				if (startX - thirdparty_car_x > SWIPE_MIN_DISTANCE) {
					RotationDownThirdParty();
				} else if (thirdparty_car_x - startX > SWIPE_MIN_DISTANCE) {

					RotationUpThirdParty();
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

					thirdparty_car_canvas.drawBitmap(bt, startX, startY,
							thirdparty_car_paint);
					thirdparty_car_isDraw = true;

					// The pictures to the ImageView
					thirdparty_car_iv_canvas
							.setImageBitmap(thirdparty_car_baseBitmap);

				} else if (startX - stopX > 50 || stopX - startX > 50) {

					if (thirdparty_car_isDraw
							&& thirdparty_car_baseBitmap != null) {
						// saveBitmap();
						// baseBitmap = null;
						if (thirdparty_car_fileMap.containsKey(startindex)) {

							carDamgeObj = thirdparty_car_fileMap
									.get(startindex);
							carDamgeObj.setBitmap(thirdparty_car_baseBitmap);
							// carList.add(carDamgeObj);

						} else {

							carDamgeObj = new CarDamageUtiility(startindex,
									thirdparty_car_baseBitmap);
							thirdparty_car_carList.add(carDamgeObj);
						}

						thirdparty_car_fileMap.put(startindex, carDamgeObj);
						thirdparty_car_mAdapter.notifyDataSetChanged();
						thirdparty_car_isDraw = false;

						// Log.i("Hashmap Testing", fileMap.toString());
					}

					thirdparty_car_baseBitmap = null;
				}
				break;
			default:
				break;
			}
			return true;
		}
	};

	private void RotationUpMyCar() {

		// Toast.makeText(getActivity(), fileMap.toString(), 1).show();
		if (my_car_i < 35) {
			my_car_i++;
			// iv_canvas.setImageLevel(i);
			// iv_canvas.setBackgroundResource(ferrari_image[i]);

			if (my_car_fileMap.containsKey(my_car_i)) {
				my_car_iv_canvas.setImageBitmap(my_car_fileMap.get(my_car_i)
						.getBitmap());
			} else {
				my_car_iv_canvas.setImageBitmap(BitmapFactory.decodeResource(
						getResources(), ferrari_image[my_car_i]));
			}
		} else {
			my_car_i = 0;
			if (my_car_fileMap.containsKey(my_car_i)) {
				my_car_iv_canvas.setImageBitmap(my_car_fileMap.get(my_car_i)
						.getBitmap());
			} else {
				my_car_iv_canvas.setImageBitmap(BitmapFactory.decodeResource(
						getResources(), ferrari_image[my_car_i]));
			}
		}

	}
	
	private void RotationUpThirdParty() {

		

		if (thirdparty_car_i < 35) {
			thirdparty_car_i++;
			// iv_canvas.setImageLevel(i);
			// iv_canvas.setBackgroundResource(ferrari_image[i]);

			if (thirdparty_car_fileMap.containsKey(thirdparty_car_i)) {
				thirdparty_car_iv_canvas.setImageBitmap(thirdparty_car_fileMap
						.get(thirdparty_car_i).getBitmap());
			} else {
				thirdparty_car_iv_canvas.setImageBitmap(BitmapFactory
						.decodeResource(getResources(),
								ferrari_image[thirdparty_car_i]));
			}
		} else {
			thirdparty_car_i = 0;
			if (thirdparty_car_fileMap.containsKey(thirdparty_car_i)) {
				thirdparty_car_iv_canvas.setImageBitmap(thirdparty_car_fileMap
						.get(thirdparty_car_i).getBitmap());
			} else {
				thirdparty_car_iv_canvas.setImageBitmap(BitmapFactory
						.decodeResource(getResources(),
								ferrari_image[thirdparty_car_i]));
			}
		}
	}

	private void RotationDownMyCar() {
		// Toast.makeText(getActivity(), fileMap.toString(), 1).show();

		if (my_car_i > 0) {
			my_car_i--;
			if (my_car_fileMap.containsKey(my_car_i)) {
				my_car_iv_canvas.setImageBitmap(my_car_fileMap.get(my_car_i)
						.getBitmap());
			} else {
				my_car_iv_canvas.setImageBitmap(BitmapFactory.decodeResource(
						getResources(), ferrari_image[my_car_i]));
			}
		} else {
			my_car_i = 35;
			if (my_car_fileMap.containsKey(my_car_i)) {
				my_car_iv_canvas.setImageBitmap(my_car_fileMap.get(my_car_i)
						.getBitmap());
			} else {
				my_car_iv_canvas.setImageBitmap(BitmapFactory.decodeResource(
						getResources(), ferrari_image[my_car_i]));
			}
		}

		
	}
	
	private void RotationDownThirdParty() {
		// Toast.makeText(getActivity(), fileMap.toString(), 1).show();

		

		if (thirdparty_car_i > 0) {
			thirdparty_car_i--;
			if (thirdparty_car_fileMap.containsKey(thirdparty_car_i)) {
				thirdparty_car_iv_canvas.setImageBitmap(thirdparty_car_fileMap
						.get(thirdparty_car_i).getBitmap());
			} else {
				thirdparty_car_iv_canvas.setImageBitmap(BitmapFactory
						.decodeResource(getResources(),
								ferrari_image[thirdparty_car_i]));
			}
		} else {
			thirdparty_car_i = 35;
			if (thirdparty_car_fileMap.containsKey(thirdparty_car_i)) {
				thirdparty_car_iv_canvas.setImageBitmap(thirdparty_car_fileMap
						.get(thirdparty_car_i).getBitmap());
			} else {
				thirdparty_car_iv_canvas.setImageBitmap(BitmapFactory
						.decodeResource(getResources(),
								ferrari_image[thirdparty_car_i]));
			}
		}
	}

	/**
	 * 114 * Save the image to the SD card. 115
	 */
	protected void saveAllBitmap() {
		for (int k = 0; k < my_car_carList.size(); k++) {
			saveBitmap(my_car_carList, k, "My_Car");
		}

		for (int j = 0; j < my_car_carList.size(); j++) {
			saveBitmap(thirdparty_car_carList, j, "ThirdParty_Car");
		}

		Toast.makeText(getActivity(), "Save All the picture  success", 0)
				.show();
	}

	protected void saveBitmap(ArrayList<CarDamageUtiility> carlist, int index,
			String Type) {

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

			String filename = mediaStorageDir.getPath() + File.separator + Type
					+ "_" + index + "_" + timeStamp + ".jpg";

			File file = new File(filename);
			FileOutputStream stream = new FileOutputStream(file);
			carlist.get(index).getBitmap()
					.compress(CompressFormat.PNG, 100, stream);

			carlist.get(index).setPath(filename);

			// Android equipment Gallery application will only at boot time
			// scanning system folder
			// The simulation of a media loading broadcast, for the
			// preservation
			// of images can be viewed in Gallery
			// Intent intent = new Intent();
			// intent.setAction(Intent.ACTION_MEDIA_MOUNTED);
			// intent.setData(Uri.fromFile(Environment
			// .getExternalStorageDirectory()));
			// getActivity().sendBroadcast(intent);

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
		if (my_car_baseBitmap != null) {

			my_car_baseBitmap = BitmapFactory.decodeResource(getResources(),
					ferrari_image[my_car_i])
					.copy(Bitmap.Config.ARGB_8888, true);
			my_car_canvas = new Canvas(my_car_baseBitmap);
			// canvas.drawColor(Color.WHITE);
			my_car_iv_canvas.setImageBitmap(my_car_baseBitmap);
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
			saveAllBitmap();
			break;
		case R.id.btn_resume:
			resumeCanvas();
			break;
		default:
			break;
		}
	}

	private BaseAdapter my_car_mAdapter = new BaseAdapter() {

		@Override
		public int getCount() {
			return my_car_carList.size();
		}

		@Override
		public Object getItem(int position) {
			return my_car_carList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View retval = LayoutInflater.from(parent.getContext()).inflate(
					R.layout.horizantallistrow, null);
			ImageView iv = (ImageView) retval.findViewById(R.id.rowimage);
			iv.setImageBitmap(my_car_carList.get(position).getBitmap());

			return retval;
		}

	};

	private BaseAdapter thirdparty_car_mAdapter = new BaseAdapter() {

		@Override
		public int getCount() {
			return thirdparty_car_carList.size();
		}

		@Override
		public Object getItem(int position) {
			return thirdparty_car_carList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View retval = LayoutInflater.from(parent.getContext()).inflate(
					R.layout.horizantallistrow, null);
			ImageView iv = (ImageView) retval.findViewById(R.id.rowimage);
			iv.setImageBitmap(thirdparty_car_carList.get(position).getBitmap());

			return retval;
		}

	};
}