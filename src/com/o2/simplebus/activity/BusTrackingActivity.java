package com.o2.simplebus.activity;

import com.o2.simplebus.R;
import com.o2.simplebus.SimpleBusApplication;
import com.o2.simplebus.controller.HomeController;


import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class BusTrackingActivity extends Activity {

	private HomeController homeController;
    private Button logoff_button;

    
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_tracking_screen);
        
        SimpleBusApplication app = (SimpleBusApplication)getApplicationContext();
        homeController = app.getHomeController();
		homeController.setActivity(this);
		
		logoff_button = (Button)findViewById(R.id.logoff_button);
		logoff_button.setOnClickListener(homeController);
	  
        String busId = app.getBusid();
    
        TextView busidText = (TextView) findViewById(R.id.bus_id);
        
        busidText.setText("Bus " + busId);
        
    }
    
    @Override
	protected void onStart() {
		super.onStart();
		this.homeController.startListening();
	}

	public void updateLastStatus(String updateStr) {
		
		TextView lastupdate =  (TextView)findViewById(R.id.lastupdate);
		
		lastupdate.setText(updateStr);
		
		
		
		
	}

	

    
    
    
	
}
