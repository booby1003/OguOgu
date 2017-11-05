package com.oguogu.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.oguogu.GlobalApplication;
import com.oguogu.R;
import com.oguogu.communication.ConstantCommURL;
import com.oguogu.communication.HttpRequest;
import com.oguogu.vo.VoPlaceDetail;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 김민정 on 2017-09-28.
 */

public class WritePlaceDetailActivity extends AppCompatActivity {

    public static final String PLACE_IDX = "PLACE_IDX";
    HttpRequest mRequest = null;
    VoPlaceDetail placeInfo = null;

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.btn_close) ImageButton btn_close;
    @Bind(R.id.btn_wirte) ImageButton btn_wirte;
    @Bind(R.id.iv_place_type) ImageView iv_place_type;
    @Bind(R.id.tv_place_name) TextView tv_place_name;
    @Bind(R.id.tv_addr) TextView tv_addr;
    @Bind(R.id.tv_place_type) TextView tv_place_type;
    @Bind(R.id.tv_time) TextView tv_time;
    @Bind(R.id.tv_tel_no) TextView tv_tel_no;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_detail_place);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        String placeIdx = intent.getStringExtra(PLACE_IDX);
        placeIdx = "P0000000002";

        getPlaceInfo(placeIdx);
    }

    private void getPlaceInfo(String placeIdx) {

        if(mRequest == null) mRequest = HttpRequest.getInstance(this);

        String url = ConstantCommURL.getURL(ConstantCommURL.URL_API, ConstantCommURL.REQUEST_GET_WRITE_PLACE);
        Uri.Builder builder = Uri.parse(url).buildUpon();
        url = url + "/" + placeIdx;
        //builder.appendQueryParameter("", "");

        mRequest.StringRequest(ConstantCommURL.REQUEST_TAG_WRITE_PLACE, Request.Method.GET, builder.toString(), "", new HttpRequest.ListenerHttpResponse() {

            @Override
            public void success(String response) {

                placeInfo = GlobalApplication.getGson().fromJson(response, VoPlaceDetail.class);
                setPlaceInfo();
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
    }

    private void setPlaceInfo() {

        //Glide.with(this).load(StringUtil.getBoardTypeDrawable(storeDetail.getBoardType())).into(iv_store_type);





    }
}
