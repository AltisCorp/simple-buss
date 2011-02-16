package com.o2.simplebus;

import com.o2.simplebus.controller.HomeController;
import com.o2.simplebus.controller.SettingsManager;

import android.app.Application;

public class SimpleBusApplication extends Application {

	
	public String getBusid() {
		
		SettingsManager sm = new SettingsManager(this);
		return sm.getValue("BUS_ID");
		
		
	}

	public String getBuskey() {
		return buskey;
	}

	public String getRouteid() {
		return routeid;
	}

	private String busid ;
	private String buskey ;
	private String routeid;


	private HomeController homeController ;

	public HomeController getHomeController() {
		return homeController;
	}
	
	public void onCreate() {

		this.homeController = new HomeController();

	}

	public void saveSettings(String busid, String buskey, String routeid) {
		this.busid = busid ;
		this.buskey = buskey;
		this.routeid = routeid;
		
		SettingsManager sm = new SettingsManager(this);
		
		sm.saveValue("BUS_ID", busid);
		
	}
	
	
	

	
}
