package com.oguogu.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.oguogu.GlobalApplication;
import com.oguogu.R;
import com.oguogu.util.StringUtil;
import com.oguogu.vo.VoPlaceDetail;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 김민정 on 2017-09-28.
 */

public class WritePlaceDetailActivity extends AppCompatActivity {

    public static final String PLACE_IDX = "PLACE_IDX";
    private VoPlaceDetail detailInfo;

    @Bind(R.id.iv_place_type) ImageView iv_place_type;
    @Bind(R.id.tv_place_name) TextView tv_place_name;
    @Bind(R.id.tv_addr) TextView tv_addr;
    @Bind(R.id.tv_place_type) TextView tv_place_type;
    @Bind(R.id.tv_time) TextView tv_time;
    @Bind(R.id.tv_tel_no) TextView tv_tel_no;
    @Bind(R.id.tv_add_info) TextView tv_add_info;
    @Bind(R.id.tv_price) TextView tv_price;
    @Bind(R.id.et_title) EditText et_title;
    @Bind(R.id.et_conts) EditText et_conts;
    @Bind(R.id.btn_camera) ImageButton btn_camera;
    @Bind(R.id.btn_album) ImageButton btn_album;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_detail_place);

        ButterKnife.bind(this);
        getPlaceDetail();
    }

    private void getPlaceDetail() {

        String msg = null;
        try {
            msg = StringUtil.getData(this, "write_place_detail.json");
        }catch (IOException e) {
            e.printStackTrace();
        }

        detailInfo = GlobalApplication.getGson().fromJson(msg, VoPlaceDetail.class);

        Glide.with(this).load(StringUtil.getBoardTypeDrawable(detailInfo.getBoardType())).into(iv_place_type);
        tv_place_name.setText(detailInfo.getStoreName());
        tv_addr.setText(detailInfo.getAddr());
        tv_place_type.setText(detailInfo.getPlaceType());
        tv_time.setText(detailInfo.getTime());
        tv_tel_no.setText(detailInfo.getTel_no());

        StringBuffer stringBuffer = new StringBuffer();
        int i = 0;
        for(String addInfo : detailInfo.getStore_info_list()) {
            stringBuffer.append(addInfo);
            if(i < detailInfo.getStore_info_list().size())
                stringBuffer.append(" | ");
            i++;
        }
        tv_add_info.setText(stringBuffer.toString());

        stringBuffer.setLength(0);
        i = 0;

        for(String price : detailInfo.getPrice_list()) {
            stringBuffer.append(price);
            if(i < detailInfo.getStore_info_list().size())
                stringBuffer.append("\n");
            i++;
        }
        tv_price.setText(stringBuffer.toString());

    }
}














