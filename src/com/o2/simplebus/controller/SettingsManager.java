package com.o2.simplebus.controller;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingsManager {
	public static final String COUNTRY = "COUNTRY";
	public static final String TOKEN = "TOKEN";
	public static final String ID = "ID";
	public static final String FRIENDS = "FRIENDS";
	public static final String LAST_UPDATE = "LAST_FULL_UPDATE";
	private Context context;
	SharedPreferences settings ;
	
	
	public SettingsManager(Context context) {
		this.context = context;
		settings = context.getSharedPreferences("FBCALL", context.MODE_PRIVATE);
	}
	
	public String getValue(String key) {
		String country = settings.getString(key, "");
	    return country;
	}
	
	public long getLongValue(String key) {
		long v = settings.getLong(key,0L);
		return v;
	}
	
	
	public void saveValue(String key, String val) {
		 SharedPreferences.Editor editor = settings.edit();
	     editor.putString(key, val);
	     editor.commit();
	
	     
	}

	public void saveValue(String key, long val ) {
		 SharedPreferences.Editor editor = settings.edit();
	     editor.putLong(key, val);
	     editor.commit();
	}

	
	

}
