package com.oguogu.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPrefUtil {
	private static final String SHARED_FILE_TITLE = "pref_oguogu";
    private static final String KEY_SCREEN_WIDTH		= "KEY_SCREEN_WIDTH";
    private static final String KEY_SCREEN_HEIGHT		= "KEY_SCREEN_HEIGHT";
    private static final String KEY_STATUS_BAR_HEIGHT	= "KEY_STATUS_BAR_HEIGHT";
    private static final String KEY_IS_TABLE			= "KEY_IS_TABLE";

    public static final String KEY_MY_SEARCH_WORDS		= "KEY_MY_SEARCH_WORDS";

    public static boolean getBooleanSharedData(Context context, String key, boolean defaultData) {
        SharedPreferences pref = context.getSharedPreferences(SHARED_FILE_TITLE, Context.MODE_PRIVATE);
        return pref.getBoolean(key, defaultData);
    }
    public static void setBooleanSharedData(Context context, String key, boolean flag) {
        SharedPreferences p = context.getSharedPreferences(SHARED_FILE_TITLE, Context.MODE_PRIVATE);
        Editor e = p.edit();
        e.putBoolean(key, flag);
        e.commit();
    }
    public static int getIntSharedData(Context context, String key, int defaultData) {
        SharedPreferences pref = context.getSharedPreferences(SHARED_FILE_TITLE, Context.MODE_PRIVATE);
        return pref.getInt(key, defaultData);
    }
    public static void setIntSharedData(Context context, String _key, int _data) {
        SharedPreferences p = context.getSharedPreferences(SHARED_FILE_TITLE, Context.MODE_PRIVATE);
        Editor e = p.edit();
        e.putInt(_key, _data);
        e.commit();
    }
    public static long getLongSharedData(Context context, String key, long defaultData) {
        SharedPreferences pref = context.getSharedPreferences(SHARED_FILE_TITLE, Context.MODE_PRIVATE);
        return pref.getLong(key, defaultData);
    }
    public static void setLongSharedData(Context context, String _key, long _data) {
        SharedPreferences p = context.getSharedPreferences(SHARED_FILE_TITLE, Context.MODE_PRIVATE);
        Editor e = p.edit();
        e.putLong(_key, _data);
        e.commit();
    }
    public static String getStringSharedData(Context context, String key, String defaultData) {
    	if (context == null)
    		return "";

        SharedPreferences pref = context.getSharedPreferences(SHARED_FILE_TITLE, Context.MODE_PRIVATE);
        return pref.getString(key, defaultData);
    }
    public static void setStringSharedData(Context context, String key, String data) {
        SharedPreferences p = context.getSharedPreferences(SHARED_FILE_TITLE, Context.MODE_PRIVATE);
        Editor e = p.edit();
        e.putString(key, data);
        e.commit();
    }

    public static void setScreenWidth(Context cxt, int width){
        SharedPreferences sp = cxt.getSharedPreferences(SHARED_FILE_TITLE, Context.MODE_PRIVATE);
        if(null == sp) {
            return;
        }
        Editor et = sp.edit();
        et.putInt(KEY_SCREEN_WIDTH, width);
        et.commit();
    }

    public static int getScreenWidth(Context cxt) {
        SharedPreferences sp = cxt.getSharedPreferences(SHARED_FILE_TITLE, Context.MODE_PRIVATE);

        if(null == sp) {
            return 0;
        }

        return sp.getInt(KEY_SCREEN_WIDTH, 0);
    }

    public static void setScreenHeight(Context cxt, int height){
        SharedPreferences sp = cxt.getSharedPreferences(SHARED_FILE_TITLE, Context.MODE_PRIVATE);
        if(null == sp) {
            return;
        }
        Editor et = sp.edit();
        et.putInt(KEY_SCREEN_HEIGHT, height);
        et.commit();
    }

    public static int getScreenHeight(Context cxt) {
        SharedPreferences sp = cxt.getSharedPreferences(SHARED_FILE_TITLE, Context.MODE_PRIVATE);

        if(null == sp) {
            return 0;
        }

        return sp.getInt(KEY_SCREEN_HEIGHT, 0);
    }

    public static void setStatusBarHeight(Context cxt, int height){
        SharedPreferences sp = cxt.getSharedPreferences(SHARED_FILE_TITLE, Context.MODE_PRIVATE);
        if(null == sp) {
            return;
        }
        Editor et = sp.edit();
        et.putInt(KEY_STATUS_BAR_HEIGHT, height);
        et.commit();
    }

    public static int getStatusBarHeight(Context cxt) {
        SharedPreferences sp = cxt.getSharedPreferences(SHARED_FILE_TITLE, Context.MODE_PRIVATE);

        if(null == sp) {
            return 0;
        }

        return sp.getInt(KEY_STATUS_BAR_HEIGHT, 0);
    }

    public static boolean getIsTable(Context cxt) {
        SharedPreferences sp = cxt.getSharedPreferences(SHARED_FILE_TITLE, Context.MODE_PRIVATE);

        if(null == sp) {
            return false;
        }

        return sp.getBoolean(KEY_IS_TABLE, false);
    }

    public static void setIsTable(Context cxt, boolean isTable){
        SharedPreferences sp = cxt.getSharedPreferences(SHARED_FILE_TITLE, Context.MODE_PRIVATE);
        if(null == sp) {
            return;
        }
        Editor et = sp.edit();
        et.putBoolean(KEY_IS_TABLE, isTable);
        et.commit();
    }

}
