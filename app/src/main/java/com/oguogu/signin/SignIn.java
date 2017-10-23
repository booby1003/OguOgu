package com.oguogu.signin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.kakao.auth.ErrorCode;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;
import com.oguogu.GlobalApplication;
import com.oguogu.R;
import com.oguogu.util.LogUtil;
import com.oguogu.util.SharedPrefUtil;
import com.oguogu.util.StringUtil;
import com.oguogu.vo.VoMyInfo;

import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Administrator on 2016-12-01.
 */

public class SignIn {

    public interface ResultCallback {
        void signInSccuess(int loginType);
        void signInFail(int loginType);
    }

    public interface SignOutCallBack {
        void signOutSccuess();
        void signOutFail();
    }

    private static final int RC_SIGN_IN = 9001;
    private Context context;
    private ResultCallback resultCallback; // 로그인 결과 콜백
    private SignOutCallBack signOutCallback; // 로그아웃 결과 콜백
    private GoogleApiClient mGoogleApiClient; // 구글 로그인관련
    private SessionCallback callback; // 카카오톡 로그인관련
    private CallbackManager facebookCallbackManager; // 페이스북 로그인콜백
    AccessTokenTracker accessTokenTracker;

    public SignIn(Context context){
        this.context = context;
    }

    public void setResultCallback(ResultCallback resultCallback) {
        this.resultCallback = resultCallback;
    }

    /*
    구글 로그인Api 사용 준비
     */
    public void initGoogleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.server_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .enableAutoManage((FragmentActivity)context /* FragmentActivity */, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        LogUtil.d("onConnectionFailed:" + connectionResult);
                    };
                } /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    public void initKakaoSignIn() {
        callback = new SessionCallback();
        Session.getCurrentSession().addCallback(callback);
    }

    public void initFacebookSignIn() {
        FacebookSdk.sdkInitialize(context);
        facebookCallbackManager = CallbackManager.Factory.create();
    }

    public void initFacebookSignIn(LoginButton btnFacebookSignIn) {
        facebookCallbackManager = CallbackManager.Factory.create();

        btnFacebookSignIn.setReadPermissions("public_profile", "email", "user_birthday");
        btnFacebookSignIn.registerCallback(facebookCallbackManager, facebookCallBackListener);
    }

    /*
    구글 이나 카카오톡 로그인이 되어있는지 체크
     */
    public void checkSignIn() {

        if (resultCallback == null)
            return;

        LogUtil.d("로그인 체크 시작========================================================");

        LogUtil.d("페이스북 로그인 체크 시작===================");
        AccessToken fbAccessToken = AccessToken.getCurrentAccessToken();
        if (fbAccessToken != null) {
            handleSignInSccuess(VoMyInfo.LOGIN_FACEBOOK, "");
        } else {
            LogUtil.d("카카오톡 로그인 체크 시작========================================================");
            if (!Session.getCurrentSession().checkAndImplicitOpen()) {
                LogUtil.d("구글 로그인 체크 시작========================================================");
                checkGoogleSignIn();
            }
        }

    }

//    ResultCallback checkSignInListener = new ResultCallback(){
//        @Override
//        public void signInSccuess(int loginType) {
//            handleSignInSccuess(loginType);
//        }
//
//        @Override
//        public void signInFail(int loginType) {
//
////            handleSignInFail(loginType);
//
//            LogUtil.d("==================로그인 모두 실패===================");
//            handleSignInFail(loginType);
//
//            if (loginType == VoMyInfo.LOGIN_GOOGLE) {
////                LogUtil.d("3. 페이스북 로그인 체크 시작===================");
////                checkFacebookSignIn();
//            } else if (loginType == VoMyInfo.LOGIN_FACEBOOK) {
////                LogUtil.d("==================로그인 모두 실패===================");
////                handleSignInFail(loginType);
//            }
//        }
//    };

    /*
    구글 로그인 체크
     */
    private void checkGoogleSignIn() {

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        LogUtil.d("구글 로그인 체크 isDone() : " + opr.isDone());

        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            LogUtil.d("Got cached sign-in");
            GoogleSignInResult result = opr.get();
//            if (isGoogleSignIn(result))
//                handleSignInSccuess(VoMyInfo.LOGIN_GOOGLE);
//            else
//                handleSignInFail(VoMyInfo.LOGIN_GOOGLE);
            if (result.isSuccess())
                handleSignInResult(result);
            else
                handleSignInFail(VoMyInfo.LOGIN_GOOGLE);

        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
//            showProgressDialog();
            opr.setResultCallback(new com.google.android.gms.common.api.ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
//                    hideProgressDialog();
//                    if (isGoogleSignIn(googleSignInResult))
//                        handleSignInSccuess(VoMyInfo.LOGIN_GOOGLE);
//                    else
//                        handleSignInFail(VoMyInfo.LOGIN_GOOGLE);
                    if (googleSignInResult.isSuccess())
                        handleSignInResult(googleSignInResult);
                    else
                        handleSignInFail(VoMyInfo.LOGIN_GOOGLE);
                }
            });
        }

    }

    /*
    구글 로그인 체크 결과
     */
    private boolean isGoogleSignIn(GoogleSignInResult result) {
        LogUtil.d("isGoogleSignIn:" + result.isSuccess());
        if (result.isSuccess()) {
            LogUtil.d("구글 로그인 성공");
            // Signed in successfully, show authenticated UI.

            GoogleSignInAccount acct = result.getSignInAccount();

            LogUtil.d("Email : " + acct.getEmail());
            LogUtil.d("id : " + acct.getId());
            LogUtil.d("PhotoUrl : " + acct.getPhotoUrl());
            LogUtil.d("token id : " + acct.getIdToken());

            return true;
        } else {
            return false;
        }
    }

    /*
    구글 로그인 후 호출됨
     */
    private void handleSignInResult(GoogleSignInResult result) {
        LogUtil.d("handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            LogUtil.d("구글 로그인 성공");
            // Signed in successfully, show authenticated UI.

            GoogleSignInAccount acct = result.getSignInAccount();

            LogUtil.d("Email : " + acct.getEmail());
            LogUtil.d("id : " + acct.getId());
            LogUtil.d("PhotoUrl : " + acct.getPhotoUrl());
            LogUtil.d("token id : " + acct.getIdToken());

            handleSignInSccuess(VoMyInfo.LOGIN_GOOGLE, acct.getEmail());
        } else {
            handleSignInFail(VoMyInfo.LOGIN_GOOGLE);
        }

    }

    /*
    카카오톡 로그인 체크 또는 로그인 후 호출됨
     */
    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {

            LogUtil.d("onSessionOpened");

            UserManagement.requestMe(new MeResponseCallback() {
                @Override
                public void onFailure(ErrorResult errorResult) {
                    String message = "failed to get user info. msg=" + errorResult;
                    Logger.d(message);
                    LogUtil.d("카톡 로그인 실패 : " + message);

                    ErrorCode result = ErrorCode.valueOf(errorResult.getErrorCode());
                    if (result == ErrorCode.CLIENT_ERROR_CODE) {
//                        finish();
                    } else {
                        //redirectMainActivity();
                    }
                }

                @Override
                public void onSessionClosed(ErrorResult errorResult) {
                    LogUtil.d("카톡 로그인 실패 : " + errorResult.getErrorMessage());
                }

                @Override
                public void onNotSignedUp() {
                    LogUtil.d("카톡 로그인 실패 onNotSignedUp ");
                }

                @Override
                public void onSuccess(UserProfile userProfile) {
                    //로그인에 성공하면 로그인한 사용자의 일련번호, 닉네임, 이미지url등을 리턴합니다.
                    //사용자 ID는 보안상의 문제로 제공하지 않고 일련번호는 제공합니다.
                    LogUtil.e("UserProfile : " + userProfile.toString());
                    LogUtil.d("카톡 로그인 성공 : " + userProfile.toString());
                    LogUtil.d("id : " + userProfile.getId() + " / " + userProfile.getUUID());
                    handleSignInSccuess(VoMyInfo.LOGIN_KAKAO, userProfile.getUUID());
                }
            });
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            LogUtil.d("카톡 로그인 실패 onSessionOpenFailed ");
            // 세션 연결이 실패했을때
            // 어쩔때 실패되는지는 테스트를 안해보았음 ㅜㅜ
        }
    }

    public void onDestroy() {
        Session.getCurrentSession().removeCallback(callback);
    }

    public void googleSignIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        ((Activity)context).startActivityForResult(signInIntent, RC_SIGN_IN);
    }

//    public void checkFacebookSignIn() {
//
//        LoginManager.getInstance().logInWithReadPermissions((Activity)context, Arrays.asList("public_profile", "email", "user_birthday"));
//
//        LoginManager.getInstance().registerCallback(facebookCallbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                LogUtil.d("LoginManager FacebookCallback onSuccess");
//
//                AccessToken accessToken = loginResult.getAccessToken();
//                if(accessToken != null) {
//                    LogUtil.d("Access Token:: " + accessToken);
//                    LogUtil.d(" token id : " + accessToken.getToken());
//                    LogUtil.d(" user id : " + accessToken.getUserId());
//
//                    checkSignInListener.signInSccuess(VoMyInfo.LOGIN_FACEBOOK);
//                }
//            }
//
//            @Override
//            public void onCancel() {
//                Log.i(TAG, "LoginManager FacebookCallback onCancel");
//                checkSignInListener.signInFail(VoMyInfo.LOGIN_FACEBOOK);
//            }
//
//            @Override
//            public void onError(FacebookException e) {
//                Log.i(TAG, "LoginManager FacebookCallback onError");
//                checkSignInListener.signInFail(VoMyInfo.LOGIN_FACEBOOK);
//            }
//
//        });
//    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        LogUtil.d("requestCode : " + requestCode);
        LogUtil.d("resultCode : " + resultCode);

        facebookCallbackManager.onActivityResult(requestCode, resultCode, data);


        //간편로그인시 호출 ,없으면 간편로그인시 로그인 성공화면으로 넘어가지 않음
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            handleSignInResult(result);
        }
    }

    private void handleSignInSccuess(int loginType, String id) {
        LogUtil.d("handleSignInSccuess : " + loginType);
        //VoMyInfo.getInstance().setLoginType(loginType);
        //resultCallback.signInSccuess(loginType);
        getLoginInfo(id);
    }

    private void handleSignInFail(int loginType) {
        LogUtil.d("handleSignInFail");
        resultCallback.signInFail(loginType);
    }

    FacebookCallback<LoginResult> facebookCallBackListener = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            LogUtil.d("onSuccess");
            GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    LogUtil.d("result : " + object.toString());

                    handleSignInSccuess(VoMyInfo.LOGIN_FACEBOOK, "");
                }
            });

            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email,gender,birthday");
            graphRequest.setParameters(parameters);
            graphRequest.executeAsync();
        }

        @Override
        public void onCancel() {
            LogUtil.d("onCancel");
        }

        @Override
        public void onError(FacebookException error) {

            LogUtil.d("LoginErr : " + error.toString());
        }
    };

    public void logoutFacebook(SignOutCallBack callback) {
        signOutCallback = callback;

        LoginManager.getInstance().logOut();

        LoginManager.getInstance().registerCallback(facebookCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        LogUtil.d("FB login success:" + loginResult.getAccessToken().getToken());
                        signOutCallback.signOutSccuess();
                    }

                    @Override
                    public void onCancel() {
                        LogUtil.d("FB login cancel");
                        signOutCallback.signOutFail();
                    }

                    @Override
                    public void onError(FacebookException error) {
                        LogUtil.d("FB login error:" + Log.getStackTraceString(error));
                        signOutCallback.signOutFail();
                    }
                });
    }

    private void getLoginInfo(String id) {

        LogUtil.i("서버에서 로그인 정보 가져오기=============================");
        String msg = null;

        try {
            msg = StringUtil.getData(context, "login_info.json");
            VoMyInfo loginInfo = GlobalApplication.getGson().fromJson(msg, VoMyInfo.class);
            resultCallback.signInSccuess(loginInfo.getLoginType());

            LogUtil.i("loginInfo : " + loginInfo.getId());
            LogUtil.i("loginInfo : " + loginInfo.getEmail());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
