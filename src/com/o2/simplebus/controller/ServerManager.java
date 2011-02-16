package com.o2.simplebus.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

import org.json.JSONObject;

import android.content.Context;



public class ServerManager {

	private static final String DOMAIN= "http://simple-bus.appspot.com/";
	private Context context;
	
	public ServerManager(Context context) {
		this.context = context;
	}

	private URL getURL(String path) throws MalformedURLException {
		
		return new URL(DOMAIN + path);
	}
	
	public MyResult postLocation(String busid, String buskey, double lat, double lon) {
		
		BufferedReader reader = null;
        try {
            URL url = getURL("locupdate");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
          
            StringBuffer data = new StringBuffer();
       	    
       	    data.append(URLEncoder.encode("busid", "UTF-8"));
        	data.append("=");
        	data.append(URLEncoder.encode(busid, "UTF-8"));
        	data.append("&amp;");
        	data.append(URLEncoder.encode("buskey", "UTF-8"));
         	data.append("=");
         	data.append(URLEncoder.encode(buskey, "UTF-8"));
         	data.append("&amp;");
         	data.append(URLEncoder.encode("lat", "UTF-8"));
         	data.append("=");
         	data.append(URLEncoder.encode(lat+"", "UTF-8"));
         	data.append("&amp;");
         	data.append(URLEncoder.encode("lon", "UTF-8"));
         	data.append("=");
         	data.append(URLEncoder.encode(lon+"", "UTF-8"));
         	
       	    
           
            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
         	writer.write(data.toString());
         	writer.flush();
            
            int retCode = conn.getResponseCode();
            
            StringBuffer response = new StringBuffer();
            
            String line;
            while ((line = reader.readLine()) != null) {
            	response.append(line);
            }
            
           
              
            
            return new MyResult(retCode,response.toString());
            
        } catch(Exception e) {
        	return new MyResult(-1,e.toString());
        }
            
		
	}

}
