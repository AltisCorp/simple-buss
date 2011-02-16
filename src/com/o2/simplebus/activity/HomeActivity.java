package com.o2.simplebus.activity;


import com.o2.simplebus.R;
import com.o2.simplebus.SimpleBusApplication;
import com.o2.simplebus.controller.HomeController;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class HomeActivity extends Activity {
    private HomeController homeController;
    private Button logon_button;
    
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     
        SimpleBusApplication app = (SimpleBusApplication)getApplicationContext();
        
        String busId = app.getBusid();
        
       
        /*
        if(busId !=null && !busId.equals("")){
			Intent intent = new Intent();
			intent.setClass(app,BusTrackingActivity.class);
			startActivity(intent);
			return;
		}
        */
        
        setContentView(R.layout.home_screen);
        
        
      
        EditText busid = (EditText) findViewById(R.id.busid);
        
        busid.setText(busId);
        
        homeController = app.getHomeController();
		homeController.setActivity(this);
		
		logon_button = (Button)findViewById(R.id.logon_button);
		logon_button.setOnClickListener(homeController);

		
        
    }

	public String getBusId() {
		
		return ((EditText)findViewById(R.id.busid)).getText().toString();
		
	}

	
}