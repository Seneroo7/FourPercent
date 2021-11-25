package com.example.base;

import android.annotation.SuppressLint;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class IsHoliday {
	
	public static void append_data(){
		String url1 = "http://timor.tech/api/holiday/info/";
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		String time = f.format(new Date()); 
		String url = url1 + time;
		time_data(url);
	}
	
	public static void time_data(final String url){
		new Thread() {
			public void run(){				
				try {
		            String html = HtmlService.getHtml(url);
		            saveJsonString(html);  
		        } catch (Exception e) {
		        }     
			}
		}.start();
    	
    }
	
	public static void saveJsonString(String substring){
		final String Filename = "nowtime.xml";
        FileHelper fHelper = new FileHelper();
		try {
			fHelper.savaFileToSD(Filename, substring);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
	}
	
	public static void loadJsonString(){
		String data = "";
		final String Filename = "nowtime.xml";
        FileHelper Helper2 = new FileHelper();
        
        try {
			data = Helper2.readFromSD(Filename);
			getJsonString(data);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
		}
	}

	public static void getJsonString(String data){
		JsonParser jp = new JsonParser();
		//将json字符串转化成json对象
        JsonObject jo = jp.parse(data).getAsJsonObject();
        
        //获取type对应的值  0：工作日  1：周末  2:节日  
        int type = jo.get("type").getAsJsonObject().get("type").getAsInt();
        sys.day_type = type;
        
        String name = jo.get("type").getAsJsonObject().get("name").getAsString();
        sys.day_name = name;
	}
	
}