package com.oguogu.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
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

	/**
	 * text에 color 입히기
	 */
	public static void setTextByResColor(View view, String text, String colorStr, int color) {
		LogUtil.i("text :: " + text);
		LogUtil.i("colorStr :: " + colorStr);
		int start = text.indexOf(colorStr);
		int end = start + colorStr.length();

		if(start == -1) return;

		Spannable spannable = new SpannableStringBuilder(text);
		spannable.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

		if(view instanceof TextView) {
			TextView tv = (TextView) view;
			tv.setText(spannable);
		}else if(view instanceof  EditText) {
			EditText et = (EditText) view;
			et.setText(spannable);
		}
	}
	
}
