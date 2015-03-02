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

import com.accident.app.AppConstants;
import com.accident.app.R;

public class DBhelper extends SQLiteOpenHelper{

	public static final String DATABASE_NAME = "Data.db";
	public static final String TABLE_NAME_REPORT = "Report";
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
		
		String CREATE_TABLE_REPORT = "CREATE TABLE "+ TABLE_NAME_REPORT +"( "+
                "ids INTEGER PRIMARY KEY AUTOINCREMENT, " +
                resorce.getString(R.string.date)+" TEXT not null)";
		
		String CREATE_TABLE_DATE_TIME = "CREATE TABLE "+ TABLE_NAME_DATE_TIME +"( "+
                "ids INTEGER PRIMARY KEY, " +
                resorce.getString(R.string.date)+" TEXT not null, "+
                resorce.getString(R.string.time)+" TEXT not null)";

		String CREATE_TABLE_THIRD_PARTY = "CREATE TABLE "+ TABLE_NAME_THIRD_PARTY +"( "+
				"ids INTEGER PRIMARY KEY, " +
                resorce.getString(R.string.driver_name_dt)+" TEXT not null, "+
                resorce.getString(R.string.id)+" TEXT not null, "+
                resorce.getString(R.string.address)+" TEXT not null, "+
                resorce.getString(R.string.phone_no_dt)+" TEXT not null, "+
                resorce.getString(R.string.license_number_dt)+" TEXT not null, "+
		 		resorce.getString(R.string.is_owner)+" INTEGER DEFAULT 0)";
		
		String CREATE_TABLE_VEHICLE = "CREATE TABLE "+ TABLE_NAME_VEHICLE +"( "+
				"ids INTEGER PRIMARY KEY, " +
                resorce.getString(R.string.vehical_type_dt)+" TEXT not null, "+
                resorce.getString(R.string.manufacturer)+" TEXT not null, "+
                resorce.getString(R.string.model)+" TEXT not null, "+
                resorce.getString(R.string.color)+" TEXT not null, "+
                resorce.getString(R.string.year)+" TEXT not null, "+
                resorce.getString(R.string.license_plate_dt)+" TEXT not null)";
		
		String CREATE_TABLE_INSURANCE = "CREATE TABLE "+ TABLE_NAME_INSURANCE +"( "+
				"ids INTEGER PRIMARY KEY, " +
                resorce.getString(R.string.agency_name_dt)+" TEXT not null, "+
                resorce.getString(R.string.policy_number_dt)+" TEXT not null, "+
                resorce.getString(R.string.agent_name_dt)+" TEXT not null, "+
                resorce.getString(R.string.agent_number_dt)+" TEXT not null)";
		
		String CREATE_TABLE_POLICE = "CREATE TABLE "+ TABLE_NAME_POLICE +"( "+
				"ids INTEGER PRIMARY KEY, " +
                resorce.getString(R.string.event_number_dt)+" TEXT not null, "+
                resorce.getString(R.string.case_number_dt)+" TEXT not null, "+
                resorce.getString(R.string.unit_name_dt)+" TEXT not null, "+
                resorce.getString(R.string.station_name_dt)+" TEXT not null)";
		
		String CREATE_TABLE_DESCRIPTION = "CREATE TABLE "+ TABLE_NAME_DESCRIPTION +"( "+
				"ids INTEGER PRIMARY KEY, " +
                resorce.getString(R.string.des)+" TEXT not null)";
		
		String CREATE_TABLE_CASUALTIES = "CREATE TABLE "+ TABLE_NAME_CASUALTIES +"( "+
				"ids INTEGER PRIMARY KEY, " +
                resorce.getString(R.string.full_name_dt)+" TEXT not null, "+
                resorce.getString(R.string.id)+" TEXT not null, "+
                resorce.getString(R.string.address)+" TEXT not null, "+
                resorce.getString(R.string.phone_no_dt)+" TEXT not null, "+
                resorce.getString(R.string.age)+" TEXT not null, "+
		 		resorce.getString(R.string.is_hos)+" INTEGER DEFAULT 0)";
		
		String CREATE_TABLE_WITNESSES= "CREATE TABLE "+ TABLE_NAME_WITNESSES +"( "+
				"ids INTEGER PRIMARY KEY, " +
                resorce.getString(R.string.full_name_dt)+" TEXT not null, "+
                resorce.getString(R.string.id)+" TEXT not null, "+
                resorce.getString(R.string.address)+" TEXT not null, "+
                resorce.getString(R.string.phone_no_dt)+" TEXT not null, "+
                resorce.getString(R.string.age)+" TEXT not null)";
     
		db.execSQL(CREATE_TABLE_REPORT);
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
		  db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_REPORT);
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
	
	// Report
		 public void insertReport(String Date,boolean isUpdate)
		   {
		      SQLiteDatabase db1 = this.getWritableDatabase();
		      ContentValues contentValues = new ContentValues();
		      contentValues.put(resorce.getString(R.string.date), Date);
		      if(!isUpdate)
					db1.insert(TABLE_NAME_REPORT, null, contentValues);
		      db1.close();
		  }

    // DateTime
	 public void insertDateTime (int ids,String Date,String Time,boolean isUpdate)
	   {
	      SQLiteDatabase db1 = this.getWritableDatabase();
	      ContentValues contentValues = new ContentValues();
	      
	      contentValues.put(resorce.getString(R.string.ids), ids);
	      contentValues.put(resorce.getString(R.string.date), Date);
	      contentValues.put(resorce.getString(R.string.time), Time);
	      
	      if(isUpdate)
				db1.update(TABLE_NAME_DATE_TIME, contentValues, "ids="+ids, null);
			else
	      db1.insert(TABLE_NAME_DATE_TIME, null, contentValues);
	      db1.close();
	  }
	  // ThirdParty
	public void insertThirdParty(int ids,String DriverName, String Id, String Address,
			String PhoneNo, String LicenseNumber,boolean isOwner,boolean isUpdate) {
		SQLiteDatabase db1 = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		
		contentValues.put(resorce.getString(R.string.ids), ids);
		contentValues.put(resorce.getString(R.string.driver_name_dt), DriverName);
		contentValues.put(resorce.getString(R.string.id), Id);
		contentValues.put(resorce.getString(R.string.address), Address);
		contentValues.put(resorce.getString(R.string.phone_no_dt), PhoneNo);
		contentValues.put(resorce.getString(R.string.license_number_dt),LicenseNumber);
		contentValues.put(resorce.getString(R.string.is_owner),isOwner);

		if(isUpdate)
			db1.update(TABLE_NAME_THIRD_PARTY, contentValues, "ids="+ids, null);
		else 
		db1.insert(TABLE_NAME_THIRD_PARTY, null, contentValues);
		db1.close();
	}
	
	  // Vehical
		public void insertVehical(int ids,String vehical_type, String manufacturer, String model,
				String color, String year,String license_plate,boolean isUpdate) {
			SQLiteDatabase db1 = this.getWritableDatabase();
			ContentValues contentValues = new ContentValues();
			
			contentValues.put(resorce.getString(R.string.ids), ids);
			contentValues.put(resorce.getString(R.string.vehical_type_dt), vehical_type);
			contentValues.put(resorce.getString(R.string.manufacturer), manufacturer);
			contentValues.put(resorce.getString(R.string.model), model);
			contentValues.put(resorce.getString(R.string.color), color);
			contentValues.put(resorce.getString(R.string.year),year);
			contentValues.put(resorce.getString(R.string.license_plate_dt),license_plate);

			if(isUpdate)
				db1.update(TABLE_NAME_VEHICLE, contentValues, "ids="+ids, null);
			else 
			db1.insert(TABLE_NAME_VEHICLE, null, contentValues);
			db1.close();
		}
		
		  // Insurance
		public void insertInsurance(int ids,String agency_name, String policy_number,
				String agent_name, String agent_number,boolean isUpdate) {
			SQLiteDatabase db1 = this.getWritableDatabase();
			ContentValues contentValues = new ContentValues();

			contentValues.put(resorce.getString(R.string.ids), ids);
			contentValues.put(resorce.getString(R.string.agency_name_dt), agency_name);
			contentValues.put(resorce.getString(R.string.policy_number_dt), policy_number);
			contentValues.put(resorce.getString(R.string.agent_name_dt), agent_name);
			contentValues.put(resorce.getString(R.string.agent_number_dt),agent_number);
			
			if(isUpdate)
				db1.update(TABLE_NAME_INSURANCE, contentValues, "ids="+ids, null);
			else 
			db1.insert(TABLE_NAME_INSURANCE, null, contentValues);
			db1.close();
		}
		
		  // Police
		public void insertPolice(int ids,String event_number, String case_number,
				String unit_name, String station_name,boolean isUpdate) {
			SQLiteDatabase db1 = this.getWritableDatabase();
			ContentValues contentValues = new ContentValues();

			contentValues.put(resorce.getString(R.string.ids), ids);
			contentValues.put(resorce.getString(R.string.event_number_dt), event_number);
			contentValues.put(resorce.getString(R.string.case_number_dt), case_number);
			contentValues.put(resorce.getString(R.string.unit_name_dt), unit_name);
			contentValues.put(resorce.getString(R.string.station_name_dt),station_name);

			if(isUpdate)
				db1.update(TABLE_NAME_POLICE, contentValues, "ids="+ids, null);
			else 
			db1.insert(TABLE_NAME_POLICE, null, contentValues);
			db1.close();
		}
		
		  // Description
		public void insertDescription(int ids,String station_name,boolean isUpdate) {
			SQLiteDatabase db1 = this.getWritableDatabase();
			ContentValues contentValues = new ContentValues();

			contentValues.put(resorce.getString(R.string.ids), ids);
			contentValues.put(resorce.getString(R.string.des), station_name);

			if(isUpdate)
				db1.update(TABLE_NAME_DESCRIPTION, contentValues, "ids="+ids, null);
			else 
				db1.insert(TABLE_NAME_DESCRIPTION, null, contentValues);
			db1.close();
		}
		
		// Casulty
				public void insertCasulty (int ids,String full_name, String Id, String Address,
						String PhoneNo, String age,boolean isHospitalized,boolean isUpdate) {
					SQLiteDatabase db1 = this.getWritableDatabase();
					ContentValues contentValues = new ContentValues();

					contentValues.put(resorce.getString(R.string.ids), ids);
					contentValues.put(resorce.getString(R.string.full_name_dt), full_name);
					contentValues.put(resorce.getString(R.string.id), Id);
					contentValues.put(resorce.getString(R.string.address), Address);
					contentValues.put(resorce.getString(R.string.phone_no_dt), PhoneNo);
					contentValues.put(resorce.getString(R.string.age),age);
					contentValues.put(resorce.getString(R.string.is_hos),isHospitalized);

					if(isUpdate)
							db1.update(TABLE_NAME_CASUALTIES, contentValues, "ids="+ids, null);
					else
						db1.insert(TABLE_NAME_CASUALTIES, null, contentValues);
					
					db1.close();
				}
		
		  // Witness
		public void insertWitness (int ids,String full_name, String Id, String Address,
				String PhoneNo, String age,boolean isUpdate) {
			SQLiteDatabase db1 = this.getWritableDatabase();
			ContentValues contentValues = new ContentValues();

			contentValues.put(resorce.getString(R.string.ids), ids);
			contentValues.put(resorce.getString(R.string.full_name_dt), full_name);
			contentValues.put(resorce.getString(R.string.id), Id);
			contentValues.put(resorce.getString(R.string.address), Address);
			contentValues.put(resorce.getString(R.string.phone_no_dt), PhoneNo);
			contentValues.put(resorce.getString(R.string.age),age);

			if(isUpdate)
					db1.update(TABLE_NAME_WITNESSES, contentValues, "ids="+ids, null);
			else
				db1.insert(TABLE_NAME_WITNESSES, null, contentValues);	
			
			db1.close();
		}
		
		 public ArrayList<HashMap<String,Object>> getData(String TABLE_NAME) {
		        ArrayList<HashMap<String,Object>> contantList = new ArrayList<HashMap<String,Object>>();
		        // Select All Query
		        String selectQuery = "SELECT  * FROM " + TABLE_NAME ;//+" WHERE "+"ids=1";

		        SQLiteDatabase db = this.getWritableDatabase();
		        Cursor cursor = db.rawQuery(selectQuery, null);
		        HashMap<String,Object> map;
		        // looping through all rows and adding to list
		        if (cursor.moveToFirst()) {
		            do {
		                map = new HashMap<String,Object>();
		             if(TABLE_NAME.equals(TABLE_NAME_DESCRIPTION) || TABLE_NAME.equals(TABLE_NAME_REPORT)){
		            	 map.put(AppConstants.ITEM0,cursor.getInt(0));
		                 map.put(AppConstants.ITEM1,cursor.getString(1));
		             }else if(TABLE_NAME.equals(TABLE_NAME_DATE_TIME)){
		            	 map.put(AppConstants.ITEM0,cursor.getInt(0));
		            	 map.put(AppConstants.ITEM1,cursor.getString(1));
		            	 map.put(AppConstants.ITEM2,cursor.getString(2));  
		             } else if(TABLE_NAME.equals(TABLE_NAME_INSURANCE) || TABLE_NAME.equals(TABLE_NAME_POLICE)){
		            	 map.put(AppConstants.ITEM0,cursor.getInt(0));
		            	 map.put(AppConstants.ITEM1,cursor.getString(1));
		            	 map.put(AppConstants.ITEM2,cursor.getString(2));
		            	 map.put(AppConstants.ITEM3,cursor.getString(3));
		            	 map.put(AppConstants.ITEM4,cursor.getString(4));
		             } else if(TABLE_NAME.equals(TABLE_NAME_THIRD_PARTY) || TABLE_NAME.equals(TABLE_NAME_CASUALTIES)){
		            	 map.put(AppConstants.ITEM0,cursor.getInt(0));
		            	 map.put(AppConstants.ITEM1,cursor.getString(1));
		            	 map.put(AppConstants.ITEM2,cursor.getString(2));
		            	 map.put(AppConstants.ITEM3,cursor.getString(3));
		            	 map.put(AppConstants.ITEM4,cursor.getString(4));
		            	 map.put(AppConstants.ITEM5,cursor.getString(5));
		            	 map.put(AppConstants.ITEM6,cursor.getInt(6));
		             } else if(TABLE_NAME.equals(TABLE_NAME_VEHICLE)){
		            	 map.put(AppConstants.ITEM0,cursor.getInt(0));
		            	 map.put(AppConstants.ITEM1,cursor.getString(1));
		            	 map.put(AppConstants.ITEM2,cursor.getString(2));
		            	 map.put(AppConstants.ITEM3,cursor.getString(3));
		            	 map.put(AppConstants.ITEM4,cursor.getString(4));
		            	 map.put(AppConstants.ITEM5,cursor.getString(5));
		            	 map.put(AppConstants.ITEM6,cursor.getString(6));
		             }else if(TABLE_NAME.equals(TABLE_NAME_WITNESSES)){
		            	 map.put(AppConstants.ITEM0,cursor.getInt(0));
		            	 map.put(AppConstants.ITEM1,cursor.getString(1));
		            	 map.put(AppConstants.ITEM2,cursor.getString(2));
		            	 map.put(AppConstants.ITEM3,cursor.getString(3));
		            	 map.put(AppConstants.ITEM4,cursor.getString(4));
		            	 map.put(AppConstants.ITEM5,cursor.getString(5));
		             }
		                // Adding contact to list
		                contantList.add(map);
		            } while (cursor.moveToNext());
		        }

		        // return contact list
		        return contantList;
		    }
		
}
