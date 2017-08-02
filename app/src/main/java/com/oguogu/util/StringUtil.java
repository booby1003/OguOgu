package com.oguogu.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;


public class StringUtil {
	
	public static String getString(Context context, int resId) {   
		return context.getResources().getString(resId);
	}
	
	public static boolean isNull(String str){
		if(str == null || "".equals(str.trim())){
			return true;
		}
		return false;
	}
	
	public static boolean isNull(EditText view){
		String txt = view.getText().toString().trim();
		if(txt == null || "".equals(txt)){
			return true;
		}
		return false;
	}

	public static String getString(TextView v){
		return v.getText().toString().trim();
	}
	public static String getString(EditText v){
		return v.getText().toString().trim();
	}

	public static String getData(Context context, String fileName) throws IOException {

		String result = "";
		
		AssetManager am = context.getResources().getAssets();
		InputStream is = null;
		
		try {
			is = am.open(fileName);
			int size = is.available();

			if (size > 0) {
				byte[] data = new byte[size];
				is.read(data);
				result = new String(data);

				return result;
			}
		} catch (IOException e) {
				e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
					is = null;
				} catch (Exception e) {
				}
			}
		}
		
		return result;
	}
	
}
