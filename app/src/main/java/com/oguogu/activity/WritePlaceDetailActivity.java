package com.oguogu.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.google.android.gms.location.places.Place;
import com.oguogu.GlobalApplication;
import com.oguogu.R;
import com.oguogu.communication.ConstantCommURL;
import com.oguogu.communication.HttpRequest;
import com.oguogu.cropper.CropImage;
import com.oguogu.cropper.CropImageView;
import com.oguogu.util.LogUtil;
import com.oguogu.util.StringUtil;
import com.oguogu.util.UIUtil;
import com.oguogu.vo.VoPlaceDetail;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @Bind(R.id.ll_extrnInfo) LinearLayout ll_extrnInfo;
    @Bind(R.id.tv_extrnInfo) TextView tv_extrnInfo;
    @Bind(R.id.ll_price) LinearLayout ll_price;
    @Bind(R.id.tv_price) TextView tv_price;
    @Bind(R.id.et_title) EditText et_title;
    @Bind(R.id.et_content) EditText et_content;
    @Bind(R.id.btn_camera) ImageButton btn_camera;
    @Bind(R.id.btn_album) ImageButton btn_album;
    @Bind(R.id.ll_imgs) LinearLayout ll_imgs;
    @Bind(R.id.line_more_info) View line_more_info;

    private float mWidth = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_detail_place);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        String placeIdx = intent.getStringExtra(PLACE_IDX);
        placeIdx = "P0000000002";

        mWidth = (UIUtil.getScreenWidthPixcels(this) - UIUtil.dpToPixel(40, this)) / 5;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) mWidth);
        ll_imgs.setLayoutParams(params);

        getPlaceInfo(placeIdx);
    }

    private void getPlaceInfo(String placeIdx) {

        if(mRequest == null) mRequest = HttpRequest.getInstance(this);

        String url = ConstantCommURL.getURL(ConstantCommURL.URL_API, ConstantCommURL.REQUEST_GET_WRITE_PLACE);
        url = url + "/" + placeIdx;
        //Uri.Builder builder = Uri.parse(url).buildUpon();
        //builder.appendQueryParameter("", "");

        mRequest.StringRequest(ConstantCommURL.REQUEST_TAG_WRITE_PLACE, Request.Method.GET, url, "", new HttpRequest.ListenerHttpResponse() {

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
        LogUtil.i("boardType : " + placeInfo.getBoardType());
        int boardTypeId = UIUtil.getBoardTypeDrawable(placeInfo.getBoardType());
        Glide.with(this).load(boardTypeId).into(iv_place_type);

        tv_place_name.setText(placeInfo.getPlaceName());
        tv_addr.setText(placeInfo.getPlaceAddr());
        tv_place_type.setText(placeInfo.getPlaceTypeStr());
        tv_time.setText(placeInfo.getTime());
        tv_tel_no.setText(placeInfo.getTelNo());

        if(placeInfo.getExtraInfoList().size() > 0) {
            line_more_info.setVisibility(View.VISIBLE);
            ll_extrnInfo.setVisibility(View.VISIBLE);

            int size = 0;
            for(String extraInfo : placeInfo.getExtraInfoList()) {
                tv_extrnInfo.append(extraInfo);
                if(size > placeInfo.getExtraInfoList().size()) tv_extrnInfo.append(" | ");
            }
        }

        if(placeInfo.getPriceList().size() > 0) {
            line_more_info.setVisibility(View.VISIBLE);
            ll_extrnInfo.setVisibility(View.VISIBLE);
            ll_price.setVisibility(View.VISIBLE);

            int size = 0;
            for(String price : placeInfo.getPriceList()) {
                tv_price.append(price);
                if(size > placeInfo.getPriceList().size()) tv_price.append(" | ");
            }
        }

    }

    @OnClick({R.id.btn_camera, R.id.btn_album})
    public void onClickButton(View view) {
        switch (view.getId()) {
            case R.id.btn_camera:
                if(imgCount > 4) Toast.makeText(this, R.string.toast_image_max, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString());
                intent.putExtra("return-date", true);
                startActivityForResult(intent, GalleryActivity.PICK_FROM_CAMERA);
                break;

            case R.id.btn_album:
                if(imgCount > 4) Toast.makeText(this, R.string.toast_image_max, Toast.LENGTH_SHORT).show();
                startActivityForResult(new Intent(this, GalleryActivity.class), GalleryActivity.PICK_FROM_ALBUM);
                break;
        }
    }

    int imgCount = 0;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        LogUtil.i("onActivityResult");

        if(resultCode != RESULT_OK) return;

        switch (requestCode) {

            case GalleryActivity.PICK_FROM_ALBUM: {
                LogUtil.i("PICK_FROM_ALBUM");

                CropImage.ActivityResult result = CropImage.getActivityResult(data);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) mWidth, LinearLayout.LayoutParams.MATCH_PARENT);
                params.rightMargin = UIUtil.dpToPixel(5, this);

                ImageView imageView = new ImageView(this);
                imageView.setLayoutParams(params);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setImageURI(result.getUri());

                ll_imgs.addView(imageView);
                //iv_img.setImageURI(result.getUri());

                LogUtil.i("imageUrl :: " + result.getUri());
                break;
            }
            case GalleryActivity.PICK_FROM_CAMERA:{
                LogUtil.i("Gallery PICK_FROM_CAMERA");

//                Bundle newExtras = new Bundle();
//                if (mCropValue.equals("circle")) {
//                    newExtras.putString("circleCrop", "true");
//                }
//                if (mSaveUri != null) {
//                    newExtras.putParcelable(MediaStore.EXTRA_OUTPUT, mSaveUri);
//                } else {
//                    newExtras.putBoolean("return-data", true);
//                }


                Uri cameraUri = data.getData();
                LogUtil.i("cameraUri :: " + cameraUri);

                if (cameraUri != null) {
                    LogUtil.i("cameraUri :: " + cameraUri);
                    CropImage.activity(cameraUri)
                            .setGuidelines(CropImageView.Guidelines.OFF)
                            .setBorderLineColor(Color.WHITE)
                            .setBorderLineThickness(5)
                            .setCircleSize(40)
                            .setCircleColor(Color.WHITE)
                            .setBackgroundColor(Color.argb(170, 0, 0, 0))
                            .start(this);

//                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) mWidth, LinearLayout.LayoutParams.MATCH_PARENT);
//                    params.rightMargin = UIUtil.dpToPixel(5, this);
//
//                    ImageView imageView = new ImageView(this);
//                    imageView.setLayoutParams(params);
//                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                    imageView.setImageURI(carameUri);
//
//                    ll_imgs.addView(imageView);
                }
                break;
            }
        }

        imgCount++;
    }

}
