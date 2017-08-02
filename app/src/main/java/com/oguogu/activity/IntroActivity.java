package com.oguogu.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.api.GoogleApiClient;
import com.oguogu.R;
import com.oguogu.signin.SignIn;
import com.oguogu.util.LogUtil;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016-11-30.
 */

public class IntroActivity extends AppCompatActivity {

    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;
    private SignIn signIn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_intro);

        ButterKnife.bind(this);

        LogUtil.d("onCreate");

        signIn = new SignIn(this);
        signIn.initGoogleSignIn();
        signIn.initKakaoSignIn();
        signIn.initFacebookSignIn();
        signIn.setResultCallback(new SignIn.ResultCallback() {
            @Override
            public void signInSccuess(int loginType) {
                startActivity(new Intent(IntroActivity.this, OguOguActivity.class));
                finish();
            }
            @Override
            public void signInFail(int loginType) {
                startActivity(new Intent(IntroActivity.this, LoginActivity.class));
                finish();
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();

        signIn.checkSignIn();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        signIn.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (signIn != null)
            signIn.onDestroy();;

    }

}
