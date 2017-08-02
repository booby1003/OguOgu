package com.oguogu.util;

import android.os.Debug;
import android.util.Log;

import java.io.IOException;

public class LogUtil
{
    private static final String APP_NAME      = "SmartMM";
    private static final String LOG_TAG_COMM  = "MMCommLog";
    private static final int    STACK_NUMBUER = 2;
    private static boolean      mDebug        = true;    // 최종 릴리즈시 false로
    private static boolean      mWriteToFile  = false;    // 로그를 파일로 쓰거나 쓰지 않거나..
    private static boolean COMM_LOG = false;
    
    private enum logtype{verbose,info,debug,warn,error};
    
    /**
     * 로그를 logcat에 표시 할 것인지 설정 한다. 
     * @param isDebug
     */
    public static void setDebugMode(boolean isDebug){
        mDebug = isDebug;
    }
    
    /**
     * 로그를 파일로 남길 것인지 설정 한다. 
     * @param onoff
     */
    public static void isDebugToFileOnOff(boolean onoff){
        mDebug = onoff;
    }

    private static void writeToFile(String level, String log)
    {
        LogToFile logToFile = null;

        try
        {
            logToFile = new LogToFile();
            logToFile.println("[" + level + "]" + log);
        }
        catch (IOException e)
        {
        }
        finally
        {
            if (logToFile != null){
                try
                {
                    logToFile.close();
                }
                catch (IOException e)
                {
                }
            }
        }
    }

    private static void log(logtype type, String message){

//    	if (Constant.APP_MODE == Constant.APP_OPEN_MODE)
//    		return;
    	
        if(mDebug == false && !COMM_LOG){
            return;
        }
        
        String logText = "";

        try {
            String tag = "";
            String temp = new Throwable().getStackTrace()[STACK_NUMBUER].getClassName();
            if (temp != null)
            {
                int lastDotPos = temp.lastIndexOf(".");
                tag = temp.substring(lastDotPos + 1);
            }
            String methodName = new Throwable().getStackTrace()[STACK_NUMBUER].getMethodName();
            int lineNumber = new Throwable().getStackTrace()[STACK_NUMBUER].getLineNumber();

            logText = "[" + tag + "] " + methodName + "()" + "[" + lineNumber + "]" + " >> " + message;

            if (mWriteToFile == true)
            {
                writeToFile(type.name(), logText);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logText = message;
        }

//        if(type == logtype.verbose){
//            Log.v(APP_NAME, logText);
//        }else if(type == logtype.info){
//            Log.i(APP_NAME, logText);
//        }else if(type == logtype.warn){
//            Log.w(APP_NAME, logText);
//        }else if(type == logtype.error){
//            Log.e(APP_NAME, logText);
//        }else{
//            Log.d(APP_NAME, logText);
//        }            

        int maxlen = 4000;
        if (logText.length() > maxlen) {
            int chunkCount = logText.length() / maxlen;     // integer division
            for (int i = 0; i <= chunkCount; i++) {
                int max = maxlen * (i + 1);
                if (max >= logText.length()) {
                	print(type, "("+ i + "/" + chunkCount + ")" + logText.substring(maxlen * i));
                } else {
                	print(type, "("+ i + "/" + chunkCount + ")" + logText.substring(maxlen * i, max));
                }
            }
        } else {
        	print(type, logText.toString());
        }
        COMM_LOG = false;
    }

    private static void print(logtype type, String logText) {
    	String tag = COMM_LOG ? LOG_TAG_COMM:APP_NAME;
    	
    	if (COMM_LOG) {
    		logText = logText + "\n\n";
    	}
    	
    	if(type == logtype.verbose){
            Log.v(tag, logText);
        }else if(type == logtype.info){
            Log.i(tag, logText);
        }else if(type == logtype.warn){
            Log.w(tag, logText);
        }else if(type == logtype.error){
            Log.e(tag, logText);
        }else{
            Log.d(tag, logText);
        }
    }
    
    public static void d(String message, boolean commLog){
    	COMM_LOG = true;
    	log(logtype.debug,message);
    }
    public static void w(String message, boolean commLog){
    	COMM_LOG = true;
    	log(logtype.warn,message);
    }
    public static void i(String message, boolean commLog){
    	COMM_LOG = true;
    	log(logtype.info,message);
    }
    public static void e(String message, boolean commLog){
    	COMM_LOG = true;
    	log(logtype.error,message);
    }
    
    public static void v(String message)
    {
    	COMM_LOG = false;
        log(logtype.verbose,message);
    }

    public static void i(String message)
    {
    	COMM_LOG = false;
        log(logtype.info,message);
    }

    public static void d(String message)
    {
    	COMM_LOG = false;
        log(logtype.debug,message);
    }

    public static void w(String message)
    {
    	COMM_LOG = false;
        log(logtype.warn,message);
    }

    public static void e(String message)
    {
    	COMM_LOG = false;
        log(logtype.error,message);        
    }

    public static void debugNativeHeap()
    {
        String tag = "";
        String temp = new Throwable().getStackTrace()[1].getClassName();
        if (temp != null)
        {
            int lastDotPos = temp.lastIndexOf(".");
            tag = temp.substring(lastDotPos + 1);
        }
        String methodName = new Throwable().getStackTrace()[1].getMethodName();
        int lineNumber = new Throwable().getStackTrace()[1].getLineNumber();

        Log.i(APP_NAME,
                "[" + tag + "] " + methodName + "()" + "[" + lineNumber + "]" + " >> "
                        + "NativeHeapSize=" + Debug.getNativeHeapSize()
                        + " NativeHeapFreeSize=" + Debug.getNativeHeapFreeSize()
                        + " NativeHeapAllocatedSize()=" + Debug.getNativeHeapAllocatedSize());
    }

}
