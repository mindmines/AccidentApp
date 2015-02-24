package com.accident.app;

import java.util.ArrayList;

import com.accident.app.util.GridItem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;

public class GridFragment extends Fragment {

	GridView gridView;
	ArrayList<GridItem> gridArray = new ArrayList<GridItem>();
	CustomGridViewAdapter customGridAdapter;
	AlertDialog.Builder alert;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.grid, container, false);

		gridArray.clear();

		Bitmap fluIcon = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.description);

		Bitmap reportIcon = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.plice);

		Bitmap newsIcon = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.description);
		Bitmap vacIcon = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.plice);

		Bitmap aboutIcon = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.description);
		Bitmap feedbackIcon = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.plice);

		Bitmap witnessIcon = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.witness);
		Bitmap casualIcon = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.casualties);

		gridArray.add(new GridItem(fluIcon, ""));
		gridArray.add(new GridItem(reportIcon, ""));
		gridArray.add(new GridItem(newsIcon, ""));
		gridArray.add(new GridItem(vacIcon, ""));
		gridArray.add(new GridItem(aboutIcon, ""));
		gridArray.add(new GridItem(feedbackIcon, ""));

		gridArray.add(new GridItem(casualIcon, ""));
		gridArray.add(new GridItem(witnessIcon, ""));

		gridView = (GridView) rootView.findViewById(R.id.gridView1);
		customGridAdapter = new CustomGridViewAdapter(getActivity(),
				R.layout.row_grid, gridArray);
		gridView.setAdapter(customGridAdapter);

		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

				Intent i;
				switch (arg2) {
				case 0:

					break;

				case 1:

					break;

				case 2:

					break;

				case 3:

					break;

				case 4:

					break;

				case 5:

					break;

				case 6:
					i = new Intent(getActivity(),WittnessHolder.class);
					startActivity(i);
					break;
				case 7:
					i = new Intent(getActivity(),WittnessHolder.class);
					startActivity(i);
					break;

				default:
					break;
				}

			}
		});

		

		return rootView;
	}
}
