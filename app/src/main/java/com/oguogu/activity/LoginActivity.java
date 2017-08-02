package com.oguogu.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;
import com.oguogu.R;
import com.oguogu.signin.SignIn;
import com.oguogu.util.LogUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016-11-30.
 */

public class LoginActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;

    @Bind(R.id.btn_google_sign_in) SignInButton btnGoogleSignIn;
    @Bind(R.id.btn_facebook_sign_in) LoginButton btnFacebookSignIn;

    private SignIn signIn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d("onCraete");

        FacebookSdk.sdkInitialize(this);

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        AccessToken token;
        token = AccessToken.getCurrentAccessToken();

        LogUtil.d("token : " + token);

        signIn = new SignIn(this);
        signIn.initGoogleSignIn();
        signIn.initKakaoSignIn();
        signIn.initFacebookSignIn(btnFacebookSignIn);
        signIn.setResultCallback(new SignIn.ResultCallback() {
            @Override
            public void signInSccuess(int loginType) {
                startActivity(new Intent(LoginActivity.this, OguOguActivity.class));
                finish();
            }
            @Override
            public void signInFail(int loginType) {
            }
        });

    }

    @OnClick({R.id.btn_google_sign_in, R.id.btn_facebook_sign_in})
    public void onClickButton(View view) {
        switch (view.getId()) {
            case R.id.btn_google_sign_in:
                signIn.googleSignIn();
                break;
        }
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
