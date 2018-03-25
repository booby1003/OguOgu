package com.oguogu.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.oguogu.R;
import com.oguogu.vo.VoPlaceDetail;

/**
 * Created by 김민정 on 2017-10-11.
 */

public class UIUtil {

    /**
     * 현재버전 가져오기
     */
    public static int getAppVersion(Context context) {

        PackageInfo pi = null;
        try {
            pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
        return pi.versionCode;
    }

    /**
     * 현재버전이름 가져오기
     */
    public static String getAppVersionName(Context context) {

        PackageInfo pi = null;
        try {
            pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "1.0.0";
        }
        return pi.versionName;
    }

    /**
     * BoardType 별 이미지
     * @param storeType
     * @return
     */
    public static int getPlaceTypeDrawable(int storeType) {
        int boardTypeDrawable = 0;
        if (storeType == VoPlaceDetail.PLACE_TYPE_RESTAURANT)
            boardTypeDrawable = R.drawable.icon_type_cafe;
        else if (storeType == VoPlaceDetail.PLACE_TYPE_HOSPITAL)
            boardTypeDrawable = R.drawable.icon_type_hospital;
        else if (storeType == VoPlaceDetail.PLACE_TYPE_PLAYGROUND)
            boardTypeDrawable = R.drawable.icon_type_gowalk;
        else if (storeType == VoPlaceDetail.PLACE_TYPE_HOTEL)
            boardTypeDrawable = R.drawable.icon_type_gowalk;

        return boardTypeDrawable;
    }

    private static float m_screenWidthDp = -1;
    private static float m_screenHeightDp = -1;

    /**
     * 폰의 가로 dp
     * @param ctx
     * @return
     */
    public static float getScreenWidthDp(Context ctx) {

        if(m_screenWidthDp == -1) {
            DisplayMetrics displayMetrics = ctx.getResources().getDisplayMetrics();
            m_screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;
        }
        return m_screenWidthDp;
    }

    /**
     * 폰의 세로 dp
     * @param ctx
     * @return
     */
    public static float getScreenHeightDp(Context ctx) {

        if(m_screenHeightDp == -1) {
            DisplayMetrics displayMetrics = ctx.getResources().getDisplayMetrics();
            m_screenHeightDp = displayMetrics.heightPixels / displayMetrics.density;
        }
        return m_screenHeightDp;
    }

    private static int m_screenWidthPixcels = -1;
    private static int m_screenHeightPixcels = -1;

    /**
     * 폰의 가로 pixel
     * @param ctx
     * @return
     */
    public static int getScreenWidthPixcels(Context ctx)
    {
        if(m_screenWidthPixcels == -1) {
            DisplayMetrics metrics = ctx.getResources().getDisplayMetrics();
            m_screenWidthPixcels = metrics.widthPixels;
        }
        return m_screenWidthPixcels;
    }

    /**
     * 폰의 세로 pixel
     * @param ctx
     * @return
     */
    public static int getScreenHeightPixcels(Context ctx)
    {
        if(m_screenHeightPixcels == -1) {
            DisplayMetrics metrics = ctx.getResources().getDisplayMetrics();
            m_screenHeightPixcels = metrics.heightPixels;
        }
        return m_screenHeightPixcels;
    }

    /**
     *  dp --> pixel 로 변환
     * @param number
     * @param context
     * @return
     */
    public static int dpToPixel(float number, Context context) {

        int num = (int) TypedValue.applyDimension(  TypedValue.COMPLEX_UNIT_DIP
                , number
                , context.getResources().getDisplayMetrics());

        return num;
    }

    // pixel -> dp
    public static int pixelToDp(float pixel, Context context) {
        float density = context.getResources().getDisplayMetrics().densityDpi;
        int dp = (int) (pixel / ((float)density / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }


}
