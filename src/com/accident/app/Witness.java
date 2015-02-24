package com.accident.app;

public class Witness {

	String id;
	String Name;
	String Adress;
	String Phoneno;
	String Age;

	public Witness(String id, String name, String adress, String phoneno,
			String age) {
		super();
		this.id = id;
		Name = name;
		Adress = adress;
		Phoneno = phoneno;
		Age = age;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getAdress() {
		return Adress;
	}

	public void setAdress(String adress) {
		Adress = adress;
	}

	public String getPhoneno() {
		return Phoneno;
	}

	public void setPhoneno(String phoneno) {
		Phoneno = phoneno;
	}

	public String getAge() {
		return Age;
	}

	public void setAge(String age) {
		Age = age;
	}

}
