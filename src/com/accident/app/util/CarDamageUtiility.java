package com.accident.app.util;

import android.graphics.Bitmap;

public class CarDamageUtiility {

	int index;
	String path;
	Bitmap bitmap;

	public CarDamageUtiility(int index, Bitmap bitmap) {
		super();
		this.index = index;
		this.bitmap = bitmap;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

}
