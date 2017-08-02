package com.oguogu.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.oguogu.R;
import com.oguogu.activity.LoginActivity;
import com.oguogu.signin.SignIn;
import com.oguogu.util.LogUtil;
import com.oguogu.vo.VoMyInfo;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016-12-06.
 */

public class MyInfoFragment extends BaseFragment {

    private GoogleApiClient mGoogleApiClient;
    private AccessTokenTracker accessTokenTracker;

    @Bind(R.id.btn_google_sign_out) Button btnGoogleSignOut;
    @Bind(R.id.btn_kakao_sign_out) Button btnKakaoSignOut;
    @Bind(R.id.btn_facebook_sign_out) Button btnFacebookSignOut;

    SignIn signIn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_ogu_ogu, container, false);

        ButterKnife.bind(this, view);

//        signIn = new SignIn(getActivity());
//        signIn.initFacebookSignIn();

        initGoogleSignIn();

        if (VoMyInfo.getInstance().getLogin_type() == VoMyInfo.LOGIN_GOOGLE) {
            btnGoogleSignOut.setVisibility(View.VISIBLE);
        } else if (VoMyInfo.getInstance().getLogin_type() == VoMyInfo.LOGIN_KAKAO) {
            btnKakaoSignOut.setVisibility(View.VISIBLE);
        } else if (VoMyInfo.getInstance().getLogin_type() == VoMyInfo.LOGIN_FACEBOOK) {
            btnFacebookSignOut.setVisibility(View.VISIBLE);

            FacebookSdk.sdkInitialize(getActivity());

            accessTokenTracker = new AccessTokenTracker() {
                @Override
                protected void onCurrentAccessTokenChanged(
                        AccessToken oldAccessToken,
                        AccessToken currentAccessToken) {

                    if (currentAccessToken == null)
                        startLoginActivity();
                }
            };

            if (accessTokenTracker != null && !accessTokenTracker.isTracking()) {
                accessTokenTracker.startTracking();
            }
        }

        return view;
    }

    private void initGoogleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity() /* FragmentActivity */, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        LogUtil.d("onConnectionFailed:" + connectionResult);
                    };
                } /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @OnClick({R.id.btn_google_sign_out, R.id.btn_kakao_sign_out, R.id.btn_facebook_sign_out})
    public void onClickButton(View view) {
        switch (view.getId()) {
            case R.id.btn_google_sign_out:
                signOutForGoogle();
                break;
            case R.id.btn_kakao_sign_out:
                UserManagement.requestLogout(new LogoutResponseCallback() {
                    @Override
                    public void onCompleteLogout() {
                        LogUtil.d("카카오 로그아웃");
                        startLoginActivity();
                    }
                });
                break;
            case R.id.btn_facebook_sign_out:

                LoginManager.getInstance().logOut();

//                signIn.logoutFacebook(new SignIn.SignOutCallBack() {
//                    @Override
//                    public void signOutSccuess() {
//                        startLoginActivity();
//                    }
//
//                    @Override
//                    public void signOutFail() {
//
//                    }
//                });

                break;

        }
    }

    private void signOutForGoogle() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        LogUtil.d("구글 로그아웃 onResult : " + status.getStatusMessage());

                        startLoginActivity();
                    }
                });
    }

    private void startLoginActivity() {
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (accessTokenTracker != null)
            accessTokenTracker.stopTracking();
    }

    @Override
    public void refresh() {

    }

    @Override
    public void willBeHidden() {

    }

    @Override
    public void willBeDisplayed() {

    }


}
