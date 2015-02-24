package com.accident.app.adapter;

import java.util.ArrayList;

import com.accident.app.R;
import com.accident.app.R.id;
import com.accident.app.R.layout;
import com.accident.app.util.Comments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CommentList extends ArrayAdapter<Comments> {

	private final Activity context;
	private final ArrayList<Comments> comment;
	RecordHolder holder = null;
	View row;

	public CommentList(Activity context, ArrayList<Comments> comment) {
		super(context, R.layout.commentrow, comment);
		this.context = context;
		this.comment = comment;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		row = convertView;
		holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(R.layout.commentrow, parent, false);
			holder = new RecordHolder();
			holder.imgName = (TextView) row.findViewById(R.id.image_name);
			holder.image = (ImageView) row.findViewById(R.id.camera_img);
			row.setTag(holder);
		} else {
			holder = (RecordHolder) row.getTag();
		}
		Comments cmt = comment.get(position);
		holder.imgName.setText(cmt.getName() + ": ");

		BitmapFactory.Options options = new BitmapFactory.Options();

		// down sizing image as it throws OutOfMemory Exception for larger
		// images
		options.inSampleSize = 8;

		Bitmap bitmap = BitmapFactory.decodeFile(cmt.getFilePath(), options);

		holder.image.setImageBitmap(bitmap);

		return row;
	}

	static class RecordHolder {
		TextView imgName;
		ImageView image;
	}
}