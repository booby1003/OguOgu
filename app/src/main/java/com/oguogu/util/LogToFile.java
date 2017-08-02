package com.oguogu.util;

import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogToFile
{

    private String                        mPath;
    private Writer                        mWriter;

    private static final SimpleDateFormat TIMESTAMP_FMT = new SimpleDateFormat("[HH:mm:ss] ");

    /**
     * 
     * @throws IOException
     */
    public LogToFile() throws IOException
    {
        File sdcard = Environment.getExternalStorageDirectory();
        open(makeDir("SmartMM") + "SmartMM");
    }

    public LogToFile(String basePath) throws IOException
    {
        open(basePath);
    }

    public static String makeDir(String dirName)
    {

        String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + dirName;

        try
        {
            File root = new File(rootPath);
            if (root.exists() == false)
            {
                if (root.mkdirs() == false)
                {
                    throw new Exception("");
                }
            }
        }
        catch (Exception e)
        {
            // LogUtil.e(e.getLocalizedMessage());
            rootPath = "-1";
        }

        return rootPath + "/";
    }

    protected void open(String basePath) throws IOException
    {
        File f = new File(basePath + "-" + getTodayString() + ".txt");
        // mPath = f.getAbsolutePath();
        mWriter = new BufferedWriter(new FileWriter(f, true), 2048);
    }

    private static String getTodayString()
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
        return df.format(new Date());
    }

    public String getPath()
    {
        return mPath;
    }

    public void println(String message) throws IOException
    {
        mWriter.write(TIMESTAMP_FMT.format(new Date()));
        mWriter.write(message);
        mWriter.write('\n');
        mWriter.flush();
    }

    public void close() throws IOException
    {
        mWriter.close();
    }

}
