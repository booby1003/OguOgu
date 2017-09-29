package com.oguogu.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.oguogu.R;
import com.oguogu.util.StringUtil;

import butterknife.ButterKnife;

/**
 * Created by 김민정 on 2017-09-28.
 */

public class WritePlaceDetailActivity extends AppCompatActivity {

    public static final String PLACE_IDX = "PLACE_IDX";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_detail_place);

        ButterKnife.bind(this);


        getPlaceDetail();
    }

    private void getPlaceDetail() {

        //Glide.with(this).load(StringUtil.getBoardTypeDrawable(storeDetail.getBoardType())).into(iv_store_type);
    }
}
