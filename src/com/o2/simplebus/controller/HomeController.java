package com.o2.simplebus.controller;



import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.o2.simplebus.R;
import com.o2.simplebus.SimpleBusApplication;
import com.o2.simplebus.activity.BusTrackingActivity;
import com.o2.simplebus.activity.HomeActivity;


public class HomeController implements OnClickListener, LocationListener {

	private static final long LOCATION_TIMEOUT = 60000;
	private static final float LOCATION_MIN_ACCURACY = 30;
	private HomeActivity homeActivity ;
	private BusTrackingActivity busTrackingActivity;
	private long lastUpdate = 0 ;
	private double lat;
	private double lon;
	private LocationManager myManager;
	
	public void setActivity(HomeActivity homeActivity) {
		this.homeActivity = homeActivity;
		
	}
	
	

	public void onClick(View v) {
		switch (v.getId()) {
		
		
		case R.id.logon_button: { 
			
			String busid = homeActivity.getBusId();
		
			
			if((busid).trim().equals("")) {
				showToast(homeActivity, "please enter a value for busid");
				return;
			}
			
			
			
			SimpleBusApplication app = (SimpleBusApplication)homeActivity.getApplicationContext();
		
			app.saveSettings(busid,"","");
		
			Intent intent = new Intent(homeActivity, BusTrackingActivity.class);
			homeActivity.startActivity(intent);
			this.homeActivity.finish();
			
			
	
			break;
		
		
		}
		
		case R.id.logoff_button : {
			
			this.stopListening();
			Intent intent = new Intent(busTrackingActivity, HomeActivity.class);
			busTrackingActivity.startActivity(intent);
			
			this.busTrackingActivity.finish();
			
			break; 
		}
		
		
		
	}
	}
	
	
	private void showToast(Context context, String mess) {
		if(context!=null) {
			Toast toast = Toast.makeText(context, mess, Toast.LENGTH_SHORT);
			toast.show();
		}
	
		
	}

	public void setActivity(BusTrackingActivity busTrackingActivity) {
		this.busTrackingActivity = busTrackingActivity;
		
	}
	
	private void updateLocation(Location location) {
	
		Log.d("SIMPLEBUS", location.getLatitude()+"");
		
		long rightnow = System.currentTimeMillis();
		
		if((rightnow - lastUpdate) < LOCATION_TIMEOUT ) {
			return;
		}
		
		float acc = location.getAccuracy();
		
		if(acc >  LOCATION_MIN_ACCURACY ) {
			showToast(busTrackingActivity, "ignore accuracy=" +acc );
			return;
		}
		
		lat = location.getLatitude();
		lon = location.getLongitude();
		
		this.lastUpdate = rightnow;
		
		new PostLocatonTask().execute();
		
		
	}
	
	
	public void onLocationChanged(Location location) {
		
		updateLocation(location);
		
	}

	

	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	
	class PostLocatonTask extends AsyncTask {	

		
		private boolean errors = false;
		private Exception exception ;
		private MyResult result ;
		
	    
		@Override
		protected void onPostExecute(Object xresult) {
			
			
			
		  showToast(busTrackingActivity,result.getRetcode() + "=lat:" + lat + " lon:" + lon);
	      try {
	    	  Date dt = new Date();
              int hours = dt.getHours();
              int minutes = dt.getMinutes();
              int seconds = dt.getSeconds();
              String curTime = hours + ":"+minutes + ":"+ seconds;
 
	    	  busTrackingActivity.updateLastStatus("Last Update: " + curTime);
	    	  
	      } catch(Exception e) {
	    	  //ignore for now
	      }
		  
		  
			
		}

		
		protected void onPreExecute (){
			errors = false;
			result = new MyResult(99,"didn't happen");
			
		}
		
		protected Object doInBackground(Object... params) {
			
			
			try{

				SimpleBusApplication application = (SimpleBusApplication)homeActivity.getApplicationContext();
				
				ServerManager serverManager = new ServerManager(busTrackingActivity);
				result = serverManager.postLocation(application.getBusid(),application.getBuskey(),lat,lon);
			
				
			}
			catch(Exception e){
				errors = true;
				exception = e;
			}

			return "";
		}
		
	
		
	}

	public void startListening() {
		Log.d("SIMPLEBUS", "listening");
		
		if(myManager==null) {
			myManager = (LocationManager) busTrackingActivity.getSystemService(Activity.LOCATION_SERVICE);
			this.showToast(busTrackingActivity, "listening");
		}
		
		myManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this);
		
	}

	public void stopListening() {
		Log.d("SIMPLEBUS", "stop listening");
		
		if(myManager!=null) {
			myManager.removeUpdates(this);
		}
		this.showToast(busTrackingActivity, "stopped listening");
	}
	
	
	
	

}
