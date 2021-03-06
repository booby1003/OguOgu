package com.oguogu;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kakao.auth.KakaoSDK;
import com.oguogu.signin.KakaoSDKAdapter;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Administrator on 2016-11-30.
 */

public class GlobalApplication extends Application {
    private static volatile GlobalApplication obj = null;
    private static volatile Activity currentActivity = null;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Crashlytics crashlyticsKit = new Crashlytics.Builder()
                .core(new CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build())
                .build();

        // 개발자 모드일때는 사용안하게 설정
        Fabric.with(this, crashlyticsKit);
//        Fabric.with(this, new Crashlytics());
        obj = this;
        KakaoSDK.init(new KakaoSDKAdapter());
    }

    public static GlobalApplication getGlobalApplicationContext() {
        return obj;
    }

    public static Activity getCurrentActivity() {
        return currentActivity;
    }

    // Activity가 올라올때마다 Activity의 onCreate에서 호출해줘야한다.
    public static void setCurrentActivity(Activity currentActivity) {
        GlobalApplication.currentActivity = currentActivity;
    }

    private static Gson gson;
    public static Gson getGson(){
        if(gson != null){
            return gson;
        }

        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .serializeNulls()
                .create();

        return gson;
    }

}
