package com.oguogu.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.oguogu.R;
import com.oguogu.signin.SignIn;
import com.oguogu.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016-11-30.
 */

public class LoginActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;

    @Bind(R.id.btn_facebook_sign_in) Button btnFacebookSignIn;
    @Bind(R.id.btn_login_email) Button btnLogin;
    @Bind(R.id.btn_google_sign_in) Button btnGoogleLogin;
    @Bind(R.id.btn_email_sign_in) Button btnEmailLogin;


    FirebaseAuth mFirebaseAuth;
    FirebaseAuth.AuthStateListener mFirebaseAuthListener;

    GoogleApiClient mGoogleApiClient;

    CallbackManager mFacebookCallbackManager;
    static final int RC_GOOGLE_SIGN_IN = 9001;

    private SignIn signIn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d("onCraete");

        FacebookSdk.sdkInitialize(this);

        setContentView(R.layout.activity_login_new);
        ButterKnife.bind(this);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if ( user != null ) {
                    Intent intent = new Intent(LoginActivity.this, OguOguActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                }

            }
        };

        initGoogleSignIn();
        initFacebookSignIn();

//        signIn = new SignIn(this);
//        signIn.initGoogleSignIn();
//        signIn.initKakaoSignIn();
//        signIn.initFacebookSignIn(btnFacebookSignIn);
//        signIn.setResultCallback(new SignIn.ResultCallback() {
//            @Override
//            public void signInSccuess(int loginType) {
//                startActivity(new Intent(LoginActivity.this, OguOguActivity.class));
//                finish();
//            }
//            @Override
//            public void signInFail(int loginType) {
//            }
//        });

    }

    @OnClick({R.id.btn_google_sign_in, R.id.btn_facebook_sign_in, R.id.btn_user_register, R.id.btn_login_email, R.id.btn_email_sign_in})
    public void onClickButton(View view) {
        switch (view.getId()) {
            case R.id.btn_google_sign_in:
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN);
//                signIn.googleSignIn();
                break;
            case R.id.btn_user_register:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
                break;
            case R.id.btn_login_email:
                startActivity(new Intent(LoginActivity.this, LoginEmailActivity.class));
                finish();
                break;
            case R.id.btn_facebook_sign_in:
                List<String> permissionsList = new ArrayList<>();
                permissionsList.add("public_profile");
                permissionsList.add("email");

                LoginManager loginManager = LoginManager.getInstance();
                loginManager.registerCallback(mFacebookCallbackManager, facebookCallBackListener);
                loginManager.logInWithReadPermissions(this, permissionsList);

                break;
            case R.id.btn_email_sign_in:

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mFacebookCallbackManager.onActivityResult(requestCode, resultCode, data);

        if ( requestCode == RC_GOOGLE_SIGN_IN ) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if ( result.isSuccess() ) {
                String token = result.getSignInAccount().getIdToken();
                AuthCredential credential = GoogleAuthProvider.getCredential(token, null);
                mFirebaseAuth.signInWithCredential(credential);

                LogUtil.d("로그인성공 ");
                startActivity(new Intent(LoginActivity.this, OguOguActivity.class));
                finish();
            }
            else {
                LogUtil.d("Google Login Failed." + result.getStatus());
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mFirebaseAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if ( mFirebaseAuthListener != null )
            mFirebaseAuth.removeAuthStateListener(mFirebaseAuthListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (signIn != null)
            signIn.onDestroy();;

    }

    /*
    구글 로그인Api 사용 준비
     */
    public void initGoogleSignIn() {
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

    public void initFacebookSignIn() {
        mFacebookCallbackManager = CallbackManager.Factory.create();

//        btnFacebookSignIn.setReadPermissions("public_profile", "email", "user_birthday");
//        btnFacebookSignIn.registerCallback(mFacebookCallbackManager, facebookCallBackListener);


    }

    FacebookCallback<LoginResult> facebookCallBackListener = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            LogUtil.d("onSuccess");

            AuthCredential credential = FacebookAuthProvider.getCredential(loginResult.getAccessToken().getToken());
            FirebaseAuth.getInstance().signInWithCredential(credential);
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
}
