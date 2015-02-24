package com.accident.app;

public class Comments {

	String name;
	String filePath;

	public Comments(String name, String filePath) {
		super();
		this.name = name;
		this.filePath = filePath;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
