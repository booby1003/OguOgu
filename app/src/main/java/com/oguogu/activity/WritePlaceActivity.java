package com.oguogu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.TransitionManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.oguogu.GlobalApplication;
import com.oguogu.R;
import com.oguogu.adapter.WritePlaceAdapter;
import com.oguogu.communication.ConstantCommURL;
import com.oguogu.communication.HttpRequest;
import com.oguogu.util.LogUtil;
import com.oguogu.util.StringUtil;
import com.oguogu.vo.VoBase;
import com.oguogu.vo.VoStoryDetail;
import com.oguogu.vo.VoWritePlaceList;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 김민정 on 2017-09-26.
 */

public class WritePlaceActivity extends BaseActivity {

    HttpRequest mRequest = null;

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.ll_place) LinearLayout ll_place;
    @Bind(R.id.ll_srch) LinearLayout ll_srch;
    @Bind(R.id.btn_close) ImageButton btn_close;
    @Bind(R.id.btn_cafe) ImageButton btn_cafe;
    @Bind(R.id.btn_hospital) ImageButton btn_hospital;
    @Bind(R.id.btn_playground) ImageButton btn_playground;
    @Bind(R.id.tv_place) TextView tv_place;
    @Bind(R.id.et_srch) EditText et_srch;
    @Bind(R.id.btn_srch_cancel) Button btn_srch_cancel;
    @Bind(R.id.ll_write_explain) LinearLayout ll_write_explain;
    @Bind(R.id.rl_lists) RelativeLayout rl_lists;
    @Bind(R.id.recycler_view) RecyclerView recycler_view;

    private int selectedPlace = VoBase.TYPE_CAFE;

    private Timer timer = null;
    private final long DELAY_TIME = 1000;

    private WritePlaceAdapter adapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_place);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        selectedPlace(btn_cafe, place_title[0]);

        recycler_view.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycler_view.setLayoutManager(linearLayoutManager);
        adapter = new WritePlaceAdapter(this);
        recycler_view.setAdapter(adapter);

        et_srch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(timer != null)
                    timer.cancel();
            }

            @Override
            public void afterTextChanged(Editable s) {

                //글자수 2 이상
                if(s.length() > 2) {
                    timer = new Timer();
                    timer.schedule(new TimerTask() {

                        @Override
                        public void run() {
                            searchPlace(et_srch.getText().toString());
                        }

                    }, DELAY_TIME);
                }

            }
        });


        et_srch.setOnEditorActionListener(new TextView.OnEditorActionListener(){

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                switch (actionId) {
                    case EditorInfo.IME_ACTION_SEARCH:
                        searchPlace(v.getText().toString());
                        break;
                    default:
                        return false;
                }
                return false;
            }
        });

        et_srch.setOnFocusChangeListener(new TextView.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                focusInSearch(hasFocus);
            }
        });
    }

    /**
     * EditText FocusIn/out 애니메이션
     */
    private void focusInSearch(boolean hasFocus) {
        TransitionManager.beginDelayedTransition(ll_srch);
        TransitionManager.beginDelayedTransition(ll_place);
        TransitionManager.beginDelayedTransition(toolbar);
        toolbar.setVisibility(hasFocus ? View.GONE : View.VISIBLE);
        ll_place.setVisibility(hasFocus ? View.GONE : View.VISIBLE);
        btn_srch_cancel.setVisibility(hasFocus ? View.VISIBLE : View.GONE);
        rl_lists.setVisibility(hasFocus ? View.VISIBLE : View.GONE);
        ll_write_explain.setVisibility(hasFocus ? View.GONE : View.VISIBLE);
        if(!hasFocus) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(et_srch.getWindowToken(), 0);
            et_srch.clearFocus();
        }
    }

    int[] place_title = {R.string.place_cafe, R.string.place_hospital, R.string.place_walk};
    @OnClick({R.id.btn_close, R.id.btn_cafe, R.id.btn_hospital, R.id.btn_playground, R.id.btn_srch_cancel, R.id.btn_new_place})
    public void onClickButton(View view) {
        switch (view.getId()) {
            case R.id.btn_close:
                onBackPressed();
                break;
            case R.id.btn_cafe:
                selectedPlace(view, place_title[0]);
                selectedPlace = VoBase.TYPE_CAFE;
                break;
            case R.id.btn_hospital:
                selectedPlace(view, place_title[1]);
                selectedPlace = VoBase.TYPE_HOSPITAL;
                break;
            case R.id.btn_playground:
                selectedPlace(view, place_title[2]);
                selectedPlace = VoBase.TYPE_PLAYGROUND;
                break;
            case R.id.btn_srch_cancel:
                focusInSearch(false);
                et_srch.setText("");
                adapter.removeItems();
                break;
            case R.id.btn_new_place:
                startActivity(new Intent(this, WriteNewPlaceActivity.class));
                break;
        }
    }

    /**
     * 장소선택
     */
    private void selectedPlace(View view, int title){
        btn_cafe.setSelected(false);
        btn_hospital.setSelected(false);
        btn_playground.setSelected(false);
        view.setSelected(true);
        tv_place.setText(getResources().getString(title));
    }

    /**
     * 장소검색
     */
    private void searchPlace(final String place) {

        LogUtil.i("place : " + place);
        if(mRequest == null) mRequest = HttpRequest.getInstance(this);

        String url = ConstantCommURL.getURL(ConstantCommURL.URL_API, ConstantCommURL.REQUEST_GET_WRITE_PLACE);

        //url = url + "/" + place;
        //builder.appendQueryParameter("", "");

        mRequest.StringRequest(ConstantCommURL.REQUEST_TAG_WRITE_PLACE, Request.Method.GET, url, "", new HttpRequest.ListenerHttpResponse() {

            @Override
            public void success(String response) {

                VoWritePlaceList placeList = GlobalApplication.getGson().fromJson(response, VoWritePlaceList.class);
                adapter.setItems(placeList.getData(), place);
            }

            @Override
            public void fail(JSONObject response) {

            }

            @Override
            public void exception(VolleyError error) {

            }

            @Override
            public void networkerror() {

            }
        });

//        String msg = null;
//
//        try {
//            msg = StringUtil.getData(this, "write_place_list.json");
//        }catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        VoWritePlaceList placeList = GlobalApplication.getGson().fromJson(msg, VoWritePlaceList.class);
//        setTestListView(placeList.getData(), place);
    }

}
