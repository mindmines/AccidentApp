package com.accident.app.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import com.accident.app.R;

public class DBhelper extends SQLiteOpenHelper{

	public static final String DATABASE_NAME = "Data.db";
	public static final String TABLE_NAME_DATE_TIME = "DateTime";
    public static final String TABLE_NAME_THIRD_PARTY = "ThirdParty";
    public static final String TABLE_NAME_VEHICLE = "Vehicle";
    public static final String TABLE_NAME_INSURANCE = "Insurance";
    public static final String TABLE_NAME_POLICE = "Police";
    public static final String TABLE_NAME_DESCRIPTION = "Description";
    public static final String TABLE_NAME_CASUALTIES = "Casualties";
    public static final String TABLE_NAME_WITNESSES = "Witnesses";

	Context context;
	Resources resorce;
	public DBhelper(Context context) {
		super(context, DATABASE_NAME, null, 1);
		// TODO Auto-generated constructor stub
	this.context = context;
	resorce = context.getResources();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String CREATE_TABLE_DATE_TIME = "CREATE TABLE "+ TABLE_NAME_DATE_TIME +"( "+
                "ids INTEGER PRIMARY KEY AUTOINCREMENT, " +
                resorce.getString(R.string.date)+" TEXT not null, "+
                resorce.getString(R.string.time)+" TEXT not null)";

		String CREATE_TABLE_THIRD_PARTY = "CREATE TABLE "+ TABLE_NAME_THIRD_PARTY +"( "+
                "ids INTEGER PRIMARY KEY AUTOINCREMENT, " +
                resorce.getString(R.string.driver_name)+" TEXT not null, "+
                resorce.getString(R.string.id)+" TEXT not null, "+
                resorce.getString(R.string.address)+" TEXT not null, "+
                resorce.getString(R.string.phone_no)+" TEXT not null, "+
                resorce.getString(R.string.license_number)+" TEXT not null)";
		
		String CREATE_TABLE_VEHICLE = "CREATE TABLE "+ TABLE_NAME_VEHICLE +"( "+
                "ids INTEGER PRIMARY KEY AUTOINCREMENT, " +
                resorce.getString(R.string.vehical_type)+" TEXT not null, "+
                resorce.getString(R.string.manufacturer)+" TEXT not null, "+
                resorce.getString(R.string.model)+" TEXT not null, "+
                resorce.getString(R.string.color)+" TEXT not null, "+
                resorce.getString(R.string.year)+" TEXT not null, "+
                resorce.getString(R.string.license_plate)+" TEXT not null)";
		
		String CREATE_TABLE_INSURANCE = "CREATE TABLE "+ TABLE_NAME_INSURANCE +"( "+
                "ids INTEGER PRIMARY KEY AUTOINCREMENT, " +
                resorce.getString(R.string.agency_name)+" TEXT not null, "+
                resorce.getString(R.string.policy_number)+" TEXT not null, "+
                resorce.getString(R.string.agent_name)+" TEXT not null, "+
                resorce.getString(R.string.agent_number)+" TEXT not null)";
		
		String CREATE_TABLE_POLICE = "CREATE TABLE "+ TABLE_NAME_POLICE +"( "+
                "ids INTEGER PRIMARY KEY AUTOINCREMENT, " +
                resorce.getString(R.string.event_number)+" TEXT not null, "+
                resorce.getString(R.string.case_number)+" TEXT not null, "+
                resorce.getString(R.string.unit_name)+" TEXT not null, "+
                resorce.getString(R.string.station_name)+" TEXT not null)";
		
		String CREATE_TABLE_DESCRIPTION = "CREATE TABLE "+ TABLE_NAME_DESCRIPTION +"( "+
                "ids INTEGER PRIMARY KEY AUTOINCREMENT, " +
                resorce.getString(R.string.description)+" TEXT not null)";
		
		String CREATE_TABLE_CASUALTIES = "CREATE TABLE "+ TABLE_NAME_CASUALTIES +"( "+
                "ids INTEGER PRIMARY KEY AUTOINCREMENT, " +
                resorce.getString(R.string.full_name)+" TEXT not null, "+
                resorce.getString(R.string.id)+" TEXT not null, "+
                resorce.getString(R.string.address)+" TEXT not null, "+
                resorce.getString(R.string.phone_no)+" TEXT not null, "+
                resorce.getString(R.string.age)+" TEXT not null)";
		
		String CREATE_TABLE_WITNESSES= "CREATE TABLE "+ TABLE_NAME_WITNESSES +"( "+
                "ids INTEGER PRIMARY KEY AUTOINCREMENT, " +
                resorce.getString(R.string.full_name)+" TEXT not null, "+
                resorce.getString(R.string.id)+" TEXT not null, "+
                resorce.getString(R.string.address)+" TEXT not null, "+
                resorce.getString(R.string.phone_no)+" TEXT not null, "+
                resorce.getString(R.string.age)+" TEXT not null)";
     
        db.execSQL(CREATE_TABLE_DATE_TIME);
        db.execSQL(CREATE_TABLE_THIRD_PARTY);
        db.execSQL(CREATE_TABLE_VEHICLE);
        db.execSQL(CREATE_TABLE_INSURANCE);
        db.execSQL(CREATE_TABLE_POLICE);
        db.execSQL(CREATE_TABLE_DESCRIPTION);
        db.execSQL(CREATE_TABLE_CASUALTIES);
        db.execSQL(CREATE_TABLE_WITNESSES);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		  db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_DATE_TIME);
		  db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_THIRD_PARTY);
		  db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_VEHICLE);
		  db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_INSURANCE);
		  db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_POLICE);
		  db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_DESCRIPTION);
		  db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_CASUALTIES);
		  db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_WITNESSES);
	      onCreate(db);
	}

    // CATEGORY
	 public void insertData (String Date,String Time)
	   {
          
	      SQLiteDatabase db1 = this.getWritableDatabase();
	      ContentValues contentValues = new ContentValues();
	      contentValues.put(resorce.getString(R.string.date), Date);
	      contentValues.put(resorce.getString(R.string.time), Time);
	      db1.insert(TABLE_NAME_DATE_TIME, null, contentValues);
	      db1.close();
	  }
   /* // CATEGORY
    public ArrayList<HashMap<String,String>> getData(){
        ArrayList<HashMap<String,String>> contantList = new ArrayList<HashMap<String,String>>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME +" WHERE "+OpenSop.TAG_IS_BOOKMARK +"=1";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        HashMap<String,String> map;
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                map = new HashMap<String,String>();
                map.put(OpenSop.TAG_ID,cursor.getString(1));
                map.put(OpenSop.TAG_PARENT_ID,cursor.getString(2));
                map.put(OpenSop.TAG_LEVEL,cursor.getString(3));
                map.put(OpenSop.TAG_TITLE,cursor.getString(4));
                map.put(OpenSop.TAG_CREATED_TIME,cursor.getString(5));
                map.put(OpenSop.TAG_IS_BOOKMARK,cursor.getString(6));
                // Adding contact to list
                contantList.add(map);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contantList;
    }

    public int getDataTitleSize(String id){
        // Select All Query
        int Size = 0;
        String selectQuery = "SELECT  * FROM " + TABLE_NAME +" WHERE "+OpenSop.TAG_PARENT_ID +"="+id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String newId = cursor.getString(1);
                Log.e("NewId",newId);
                String selectQuery2 = "SELECT * FROM " + TABLE_NAME_CONTENT +" WHERE "+OpenSop.TAG_CAT_ID +"="+newId+" " +
                        "AND "+OpenSop.TAG_STATE+"=1";
                SQLiteDatabase db2 = this.getWritableDatabase();
                Cursor cursor2 = db2.rawQuery(selectQuery2, null);
                // looping through all rows and adding to list
                if (cursor2.moveToFirst()) {
                    do {
                        Size++;
                    } while (cursor2.moveToNext());
                }
            } while (cursor.moveToNext());
        }
        // return contact list
        return Size;
    }

    public ArrayList<HashMap<String,String>> getDataFav(String level,String title){
        ArrayList<HashMap<String,String>> contantList = new ArrayList<HashMap<String,String>>();
        // Select All Query
        String selectQuery;
        if(!level.equals("0")) {
            selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + OpenSop.TAG_IS_BOOKMARK + "=1" +
                    " AND " + OpenSop.TAG_LEVEL + "=" + level + " AND " + OpenSop.TAG_TITLE + " LIKE '%"+title+"%'";
        }else {
            selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + OpenSop.TAG_IS_BOOKMARK + "=1" +
                    " AND " + OpenSop.TAG_TITLE + " LIKE '%"+title+"%'";
        }
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        HashMap<String,String> map;
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                map = new HashMap<String,String>();
                map.put(OpenSop.TAG_ID,cursor.getString(1));
                map.put(OpenSop.TAG_PARENT_ID,cursor.getString(2));
                map.put(OpenSop.TAG_LEVEL,cursor.getString(3));
                map.put(OpenSop.TAG_TITLE,cursor.getString(4));
                map.put(OpenSop.TAG_CREATED_TIME,cursor.getString(5));
                map.put(OpenSop.TAG_IS_BOOKMARK,cursor.getString(6));
                // Adding contact to list
                contantList.add(map);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contantList;
    }

    public ArrayList<HashMap<String,String>> getDataNotFav(String level,String title){
        ArrayList<HashMap<String,String>> contantList = new ArrayList<HashMap<String,String>>();
        // Select All Query
        String selectQuery;
        if(!level.equals("0")) {
            selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " +
                    OpenSop.TAG_LEVEL + " = " + level + " AND " + OpenSop.TAG_TITLE + " LIKE '%"+title+"%'";
        }else {
            selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE "+OpenSop.TAG_TITLE + " LIKE '%"+title+"%'";
        }
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        HashMap<String,String> map;
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                map = new HashMap<String,String>();
                map.put(OpenSop.TAG_ID,cursor.getString(1));
                map.put(OpenSop.TAG_PARENT_ID,cursor.getString(2));
                map.put(OpenSop.TAG_LEVEL,cursor.getString(3));
                map.put(OpenSop.TAG_TITLE,cursor.getString(4));
                map.put(OpenSop.TAG_CREATED_TIME,cursor.getString(5));
                map.put(OpenSop.TAG_IS_BOOKMARK,cursor.getString(6));
                // Adding contact to list
                contantList.add(map);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contantList;
    }*/
}
