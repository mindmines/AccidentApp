package com.accident.app;

import java.util.ArrayList;

import com.accident.app.util.GridItem;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

/**
 * 
 * @author manish.s
 * 
 */

public class HomeActivity extends Activity {
	GridView gridView;
	ArrayList<GridItem> gridArray = new ArrayList<GridItem>();
	CustomGridViewAdapter customGridAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.grid);

		// set grid view item

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

		gridArray.add(new GridItem(fluIcon, ""));
		gridArray.add(new GridItem(reportIcon, ""));
		gridArray.add(new GridItem(newsIcon, ""));
		gridArray.add(new GridItem(vacIcon, ""));
		gridArray.add(new GridItem(aboutIcon, ""));
		gridArray.add(new GridItem(feedbackIcon, ""));

		gridView = (GridView) findViewById(R.id.gridView1);
		customGridAdapter = new CustomGridViewAdapter(this, R.layout.row_grid,
				gridArray);
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

				default:
					break;
				}

			}
		});
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		this.finish();
	}

}
