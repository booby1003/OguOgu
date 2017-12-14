package com.oguogu.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oguogu.R;
import com.oguogu.dialog.LoadingDialog;
import com.oguogu.util.UIUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 김민정 on 2017-09-29.
 */

public class WriteNewPlaceActivity extends AppCompatActivity {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.btn_close) ImageButton btn_close;
    @Bind(R.id.btn_wirte) ImageButton btn_wirte;
    @Bind(R.id.et_place_nm) EditText et_place_nm;
    @Bind(R.id.iv_place_type) ImageView iv_place_type;
    @Bind(R.id.tv_place_type) TextView tv_place_type;
    @Bind(R.id.tv_place_cate) TextView tv_place_cate;
    @Bind(R.id.et_num) EditText et_num;
    @Bind(R.id.et_time) EditText et_time;
    @Bind(R.id.et_content) EditText et_content;
    @Bind(R.id.btn_camera) ImageButton btn_camera;
    @Bind(R.id.btn_album) ImageButton btn_album;
    @Bind(R.id.ll_imgs) LinearLayout ll_imgs;
    @Bind(R.id.ll_add_info) LinearLayout ll_add_info;
    @Bind(R.id.btn_add_info) Button btn_add_info;
    @Bind(R.id.tv_addr_srch) TextView tv_addr_srch;
    @Bind(R.id.btn_addr_srch) Button btn_addr_srch;
    //@Bind(R.id.mapFragment) Fragment mapFragment;

    private float mWidth = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_new_place);

        ButterKnife.bind(this);

        mWidth = (UIUtil.getScreenWidthPixcels(this) - UIUtil.dpToPixel(40, this)) / 5;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) mWidth);
        ll_imgs.setLayoutParams(params);

        getPlaceInfo();
    }

    private void getPlaceInfo() {


    }
}



























