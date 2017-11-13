package com.oguogu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.FacebookSdk;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.oguogu.R;
import com.oguogu.util.LogUtil;
import com.oguogu.vo.VoMyInfo;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 김민정 on 2017-10-10.
 */

public class SettingActivity extends AppCompatActivity {

    @Bind(R.id.btn_logout) Button btnLogout;
    private GoogleApiClient mGoogleApiClient;
    private AccessTokenTracker accessTokenTracker;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_setting);

        ButterKnife.bind(this);

        initGoogleSignIn();

        if (VoMyInfo.getInstance().getLoginType() == VoMyInfo.LOGIN_GOOGLE) {
            //btnGoogleSignOut.setVisibility(View.VISIBLE);
        } else if (VoMyInfo.getInstance().getLoginType() == VoMyInfo.LOGIN_KAKAO) {
            //btnKakaoSignOut.setVisibility(View.VISIBLE);
        } else if (VoMyInfo.getInstance().getLoginType() == VoMyInfo.LOGIN_FACEBOOK) {
            //btnFacebookSignOut.setVisibility(View.VISIBLE);

            FacebookSdk.sdkInitialize(this);

            accessTokenTracker = new AccessTokenTracker() {
                @Override
                protected void onCurrentAccessTokenChanged(
                        AccessToken oldAccessToken,
                        AccessToken currentAccessToken) {

                    if (currentAccessToken == null) {
                        //startLoginActivity();
                    }
                }
            };

            if (accessTokenTracker != null && !accessTokenTracker.isTracking()) {
                accessTokenTracker.startTracking();
            }
        }
    }

    @OnClick({R.id.btn_logout})
    public void onClickButton(View view) {
        switch (view.getId()) {
            case R.id.btn_logout:
                FirebaseAuth.getInstance().signOut();

                startActivity(new Intent(SettingActivity.this, LoginActivity.class));
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (accessTokenTracker != null)
            accessTokenTracker.stopTracking();
    }

    private void initGoogleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        LogUtil.d("onConnectionFailed:" + connectionResult);
                    };
                } /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    private void signOutForGoogle() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        LogUtil.d("구글 로그아웃 onResult : " + status.getStatusMessage());

                        //startLoginActivity();
                    }
                });
    }
}
