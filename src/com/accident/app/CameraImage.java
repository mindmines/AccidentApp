package com.accident.app;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.accident.app.adapter.CommentList;
import com.accident.app.util.Comments;
import com.accident.app.util.Config;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class CameraImage extends BaseFragment {

	Button button;
	ImageView imageView;

	// LogCat tag
	private static final String TAG = CameraImage.class.getSimpleName();

	// Camera activity request codes
	private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;

	public static final int MEDIA_TYPE_IMAGE = 1;

	Button btnCapturePicture;
	private Uri fileUri; // file url to store image/video
	ListView imageList;
	static String filename;

	CommentList cmtlist;

	public static ArrayList<Comments> imgList = new ArrayList<Comments>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		final View rootView = inflater.inflate(R.layout.camera, container,
				false);
		button = (Button) rootView.findViewById(R.id.button);

		btnCapturePicture = (Button) rootView
				.findViewById(R.id.btnCapturePicture);
		imageList = (ListView) rootView.findViewById(R.id.listCapturePicture);

		cmtlist = new CommentList(getActivity(), imgList);
		imageList.setAdapter(cmtlist);
		if (imgList.size() > 0) {
			button.setVisibility(View.GONE);
			btnCapturePicture.setVisibility(View.VISIBLE);
			imageList.setVisibility(View.VISIBLE);
		}

		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {

				captureImage();
			}
		});

		btnCapturePicture.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// capture picture
				captureImage();
			}
		});

		return rootView;
	}

	/**
	 * Launching camera app to capture image
	 */
	private void captureImage() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

		intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

		// start the image capture Intent
		startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
	}

	public Uri getOutputMediaFileUri(int type) {
		return Uri.fromFile(getOutputMediaFile(type));
	}

	private static File getOutputMediaFile(int type) {

		// External sdcard location
		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				Config.IMAGE_DIRECTORY_NAME);

		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d(TAG, "Oops! Failed create " + Config.IMAGE_DIRECTORY_NAME
						+ " directory");
				return null;
			}
		}

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
				Locale.getDefault()).format(new Date());
		File mediaFile;
		if (type == MEDIA_TYPE_IMAGE) {
			filename = mediaStorageDir.getPath() + File.separator + "IMG_"
					+ timeStamp + ".jpg";
			mediaFile = new File(filename);
		} else {
			return null;
		}

		return mediaFile;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// if the result is capturing Image
		if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
			if (resultCode == Activity.RESULT_OK) {

				// successfully captured the image
				// launching upload activity
				// launchUploadActivity(true);

				button.setVisibility(View.GONE);
				btnCapturePicture.setVisibility(View.VISIBLE);
				imageList.setVisibility(View.VISIBLE);

				imgList.add(new Comments(filename, fileUri.getPath()));
				cmtlist.notifyDataSetChanged();

			} else if (resultCode == Activity.RESULT_CANCELED) {

				// user cancelled Image capture
				Toast.makeText(getActivity(), "User cancelled image capture",
						Toast.LENGTH_SHORT).show();

			} else {
				// failed to capture image
				Toast.makeText(getActivity(), "Sorry! Failed to capture image",
						Toast.LENGTH_SHORT).show();
			}

		}
	}

}