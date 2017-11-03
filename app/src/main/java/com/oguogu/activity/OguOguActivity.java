package com.oguogu.activity;

import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;
import com.facebook.FacebookSdk;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.oguogu.R;
import com.oguogu.demo.DemoViewPagerAdapter;
import com.oguogu.fragment.BaseFragment;
import com.oguogu.util.LogUtil;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OguOguActivity extends AppCompatActivity {

    private GoogleApiClient mGoogleApiClient;
    private BaseFragment currentFragment;
    private DemoViewPagerAdapter adapter;
    private AHBottomNavigationAdapter navigationAdapter;
    private ArrayList<AHBottomNavigationItem> bottomNavigationItems = new ArrayList<>();
    private boolean useMenuResource = true;
    private int[] tabColors;
    private Handler handler = new Handler();
//    AccessTokenTracker accessTokenTracker;

//    @Bind(R.id.btn_google_sign_out) Button btnGoogleSignOut;
//    @Bind(R.id.btn_kakao_sign_out) Button btnKakaoSignOut;

    @Bind(R.id.bottom_navigation) AHBottomNavigation bottomNavigation;
    //@Bind(R.id.toolbar) Toolbar toolbar;
   // @Bind(R.id.btn_search) Button btn_search;

//    @Bind(R.id.floating_action_button) FloatingActionButton floatingActionButton;
    @Bind(R.id.view_pager) AHBottomNavigationViewPager viewPager;
    @Bind(R.id.layoutWrite) LinearLayout layoutWrite;
    @Bind(R.id.ll_write_bg) LinearLayout ll_write_bg;
    @Bind(R.id.btn_write_place) ImageButton btn_write_place;
    @Bind(R.id.btn_write_mypet) ImageButton btn_write_mypet;
    //@Bind(R.id.btn_write_mine) ImageButton btn_write_mine;
    //@Bind(R.id.editSearch) EditText editSearch;
    //@Bind(R.id.btn_back) Button btn_back;
    //@Bind(R.id.btn_toolbar_location) Button btn_toolbar_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(this);

        setContentView(R.layout.activity_ogu_ogu);

        LogUtil.d("onCreate");

        ButterKnife.bind(this);

//        viewPager.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                Toast.makeText(OguOguActivity.this, "터치", Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });

        //setSupportActionBar(toolbar);

        //getSupportActionBar().setDisplayShowTitleEnabled(false);

//        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // 툴바 왼쪽 메뉴
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_light);

//        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
//        getSupportActionBar().setDisplayUseLogoEnabled(true);

//        accessTokenTracker = new AccessTokenTracker() {
//            @Override
//            protected void onCurrentAccessTokenChanged(
//                    AccessToken oldAccessToken,
//                    AccessToken currentAccessToken) {
//
//                LogUtil.d("aaaaaaaaaaaaaaaaaaaaaaaa : " + currentAccessToken.toString());
//            }
//        };

//        if (accessTokenTracker != null && !accessTokenTracker.isTracking()) {
//            accessTokenTracker.startTracking();
//        }

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        initGoogleSignIn();

//        if (VoMyInfo.getInstance().getLogin_type() == VoMyInfo.LOGIN_GOOGLE) {
//            btnGoogleSignOut.setVisibility(View.VISIBLE);
//        } else if (VoMyInfo.getInstance().getLogin_type() == VoMyInfo.LOGIN_KAKAO) {
//            btnKakaoSignOut.setVisibility(View.VISIBLE);
//        }

        initUI();
    }

    private void initUI() {
        tabColors = getApplicationContext().getResources().getIntArray(R.array.tab_colors);
        navigationAdapter = new AHBottomNavigationAdapter(this, R.menu.bottom_navigation_menu_5);
        navigationAdapter.setupWithBottomNavigation(bottomNavigation, tabColors);

//        bottomNavigation.manageFloatingActionButtonBehavior(floatingActionButton);
        bottomNavigation.setTranslucentNavigationEnabled(true);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_HIDE);

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {

                if (currentFragment == null) {
                    currentFragment = adapter.getCurrentFragment();
                }

                if (position == 2) {
                    ll_write_bg.setVisibility(View.VISIBLE);
                    layoutWrite.setVisibility(View.VISIBLE);
                    layoutWrite.setAlpha(0f);
                    layoutWrite.setScaleX(0f);
                    layoutWrite.setScaleY(0f);
                    layoutWrite.animate()
                            .alpha(1)
                            .scaleX(1)
                            .scaleY(1)
                            .setDuration(300)
                            .setInterpolator(new OvershootInterpolator())
                            .setListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                }

                                @Override
                                public void onAnimationCancel(Animator animation) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animation) {

                                }
                            })
                            .start();

                    return false;
                }

                if (wasSelected) {
                    currentFragment.refresh();
                    return true;
                }

                if (currentFragment != null) {
                    currentFragment.willBeHidden();
                }

                viewPager.setCurrentItem(position, false);
                currentFragment = adapter.getCurrentFragment();
                currentFragment.willBeDisplayed();

                if (position == 1) {
                    bottomNavigation.setNotification("", 1);

//                    floatingActionButton.setVisibility(View.VISIBLE);
//                    floatingActionButton.setAlpha(0f);
//                    floatingActionButton.setScaleX(0f);
//                    floatingActionButton.setScaleY(0f);
//                    floatingActionButton.animate()
//                            .alpha(1)
//                            .scaleX(1)
//                            .scaleY(1)
//                            .setDuration(300)
//                            .setInterpolator(new OvershootInterpolator())
//                            .setListener(new Animator.AnimatorListener() {
//                                @Override
//                                public void onAnimationStart(Animator animation) {
//
//                                }
//
//                                @Override
//                                public void onAnimationEnd(Animator animation) {
//                                    floatingActionButton.animate()
//                                            .setInterpolator(new LinearOutSlowInInterpolator())
//                                            .start();
//                                }
//
//                                @Override
//                                public void onAnimationCancel(Animator animation) {
//
//                                }
//
//                                @Override
//                                public void onAnimationRepeat(Animator animation) {
//
//                                }
//                            })
//                            .start();

                } else {
//                    if (floatingActionButton.getVisibility() == View.VISIBLE) {
//                        floatingActionButton.animate()
//                                .alpha(0)
//                                .scaleX(0)
//                                .scaleY(0)
//                                .setDuration(300)
//                                .setInterpolator(new LinearOutSlowInInterpolator())
//                                .setListener(new Animator.AnimatorListener() {
//                                    @Override
//                                    public void onAnimationStart(Animator animation) {
//
//                                    }
//
//                                    @Override
//                                    public void onAnimationEnd(Animator animation) {
//                                        floatingActionButton.setVisibility(View.GONE);
//                                    }
//
//                                    @Override
//                                    public void onAnimationCancel(Animator animation) {
//                                        floatingActionButton.setVisibility(View.GONE);
//                                    }
//
//                                    @Override
//                                    public void onAnimationRepeat(Animator animation) {
//
//                                    }
//                                })
//                                .start();
//                    }
                }

                return true;
            }
        });

        /*
		bottomNavigation.setOnNavigationPositionListener(new AHBottomNavigation.OnNavigationPositionListener() {
			@Override public void onPositionChange(int y) {
				Log.d("DemoActivity", "BottomNavigation Position: " + y);
			}
		});
		*/

        viewPager.setOffscreenPageLimit(4);

        adapter = new DemoViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        currentFragment = adapter.getCurrentFragment();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Setting custom colors for notification
                AHNotification notification = new AHNotification.Builder()
                        .setText(":)")
                        .setBackgroundColor(ContextCompat.getColor(OguOguActivity.this, R.color.color_notification_back))
                        .setTextColor(ContextCompat.getColor(OguOguActivity.this, R.color.color_notification_text))
                        .build();
                bottomNavigation.setNotification(notification, 1);
                Snackbar.make(bottomNavigation, "Snackbar with bottom navigation",
                        Snackbar.LENGTH_SHORT).show();

            }
        }, 3000);

        //bottomNavigation.setDefaultBackgroundResource(R.drawable.bottom_navigation_background);
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

    @OnClick({R.id.btn_write_place, R.id.btn_write_mypet})
    public void onClickButton(View view) {
        switch (view.getId()) {
            case R.id.btn_write_place:
                Toast.makeText(this, "버튼1 클릭", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(OguOguActivity.this, WritePlaceActivity.class));
                overridePendingTransition(R.anim.slide_right_to_left, R.anim.slide_center_to_left);
                break;
            case R.id.btn_write_mypet:
                Toast.makeText(this, "버튼2 클릭", Toast.LENGTH_SHORT).show();
                break;
//            case R.id.btn_write_mine:
//                Toast.makeText(this, "버튼3 클릭", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.btn_search:
////                    startActivity(new Intent(OguOguActivity.this, SearchActivity.class));
////                    overridePendingTransition(R.anim.hold, R.anim.slide_right_to_left);
//                break;
//            case R.id.btn_srch:
//                Intent intent = new Intent(this, SearchActivity.class);
//
//                Pair<View, String> p1 = Pair.create((View)editSearch, "edit");
//                //Pair<View, String> p2 = Pair.create((View)btn_back, "back");
//                Pair<View, String> p3 = Pair.create((View)btn_toolbar_location, "location");
//
//                ActivityOptionsCompat options = ActivityOptionsCompat.
//                        makeSceneTransitionAnimation(this, p1, p3);
//
//                startActivity(intent, options.toBundle());
//                break;
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
        startActivity(new Intent(OguOguActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {

        handler.removeCallbacksAndMessages(null);

        super.onDestroy();

    }

    /**
     * Update the bottom navigation colored param
     */
    public void updateBottomNavigationColor(boolean isColored) {
        bottomNavigation.setColored(isColored);
    }

    /**
     * Return if the bottom navigation is colored
     */
    public boolean isBottomNavigationColored() {
        return bottomNavigation.isColored();
    }

    /**
     * Add or remove items of the bottom navigation
     */
    public void updateBottomNavigationItems(boolean addItems) {

        if (useMenuResource) {
            if (addItems) {
                navigationAdapter = new AHBottomNavigationAdapter(this, R.menu.bottom_navigation_menu_5);
                navigationAdapter.setupWithBottomNavigation(bottomNavigation, tabColors);
                bottomNavigation.setNotification("1", 3);
            } else {
                navigationAdapter = new AHBottomNavigationAdapter(this, R.menu.bottom_navigation_menu_5);
                navigationAdapter.setupWithBottomNavigation(bottomNavigation, tabColors);
            }

        } else {
            if (addItems) {
                AHBottomNavigationItem item4 = new AHBottomNavigationItem(getString(R.string.tab_4),
                        ContextCompat.getDrawable(this, R.drawable.ic_maps_local_bar),
                        ContextCompat.getColor(this, R.color.color_tab_4));
                AHBottomNavigationItem item5 = new AHBottomNavigationItem(getString(R.string.tab_5),
                        ContextCompat.getDrawable(this, R.drawable.ic_maps_place),
                        ContextCompat.getColor(this, R.color.color_tab_5));

                bottomNavigation.addItem(item4);
                bottomNavigation.addItem(item5);
                bottomNavigation.setNotification("1", 3);
            } else {
                bottomNavigation.removeAllItems();
                bottomNavigation.addItems(bottomNavigationItems);
            }
        }
    }

    /**
     * Show or hide the bottom navigation with animation
     */
    public void showOrHideBottomNavigation(boolean show) {
        LogUtil.d("showOrHideBottomNavigation ; " + show);
        if (show) {
            bottomNavigation.restoreBottomNavigation(true);
        } else {
            bottomNavigation.hideBottomNavigation(true);
        }
    }

    /**
     * Show or hide selected item background
     */
    public void updateSelectedBackgroundVisibility(boolean isVisible) {
        bottomNavigation.setSelectedBackgroundVisible(isVisible);
    }

    /**
     * Show or hide selected item background
     */
    public void setForceTitleHide(boolean forceTitleHide) {
        AHBottomNavigation.TitleState state = forceTitleHide ? AHBottomNavigation.TitleState.ALWAYS_HIDE : AHBottomNavigation.TitleState.ALWAYS_SHOW;
        bottomNavigation.setTitleState(state);
    }

    /**
     * Reload activity
     */
    public void reload() {
        startActivity(new Intent(this, OguOguActivity.class));
        finish();
    }

    /**
     * Return the number of items in the bottom navigation
     */
    public int getBottomNavigationNbItems() {
        return bottomNavigation.getItemsCount();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        LogUtil.d("onActivityResult : " + requestCode + " / " + resultCode);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        layoutWrite.setPadding(0,0,0,bottomNavigation.getHeight());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        LogUtil.d("touch!!");

        if (ev.getAction() == MotionEvent.ACTION_UP ) {
            if (!isWriteHiding && layoutWrite.getVisibility() == View.VISIBLE)
                hideLayoutWrite();
        }

        return super.dispatchTouchEvent(ev);
    }

    private boolean isWriteHiding;
    private void hideLayoutWrite() {
        isWriteHiding = true;
        if (layoutWrite.getVisibility() == View.VISIBLE) {
            layoutWrite.animate()
                    .alpha(0)
                    .scaleX(0)
                    .scaleY(0)
                    .setDuration(300)
                    .setInterpolator(new LinearOutSlowInInterpolator())
                    .setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            layoutWrite.setVisibility(View.GONE);
                            ll_write_bg.setVisibility(View.GONE);
                            isWriteHiding = false;
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {
                            layoutWrite.setVisibility(View.GONE);
                            ll_write_bg.setVisibility(View.GONE);
                            isWriteHiding = false;
                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    })
                    .start();
        }
    }

}
