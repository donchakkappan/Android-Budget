package com.kites.dbsettings;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBconnection {

	SQLiteDatabase database;

	public DBconnection(Context context){
		try {
			// TODO Auto-generated constructor stub
			database=context.openOrCreateDatabase("budget",SQLiteDatabase.OPEN_READWRITE,null);
			database.execSQL("create table if not exists envelopes(envelopename varchar(40) PRIMARY KEY,day varchar(40))");
			database.execSQL("create table if not exists daily(envelopename varchar(100) ,amount varchar(80),purpose varchar(80),upordown varchar(100),day varchar(100))");
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	
	public int putData(String sql) {
		
		int i;
		try {
			database.execSQL(sql); 
			 i=1;
		} catch (Exception e) {
			 i=0;
			// TODO: handle exception
		}
		return i;
		
	}
	
	
	public Cursor getData(String sql) {		
		Cursor cursor=null;
		try {
		cursor=database.rawQuery(sql, null);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		 
		 return cursor;
	}
	
	
}
