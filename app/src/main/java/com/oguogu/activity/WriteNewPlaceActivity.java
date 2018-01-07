package com.oguogu.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.oguogu.GlobalApplication;
import com.oguogu.R;
import com.oguogu.communication.AppHelper;
import com.oguogu.communication.ConstantCommURL;
import com.oguogu.communication.DataPart;
import com.oguogu.communication.HttpRequest;
import com.oguogu.cropper.CropImage;
import com.oguogu.cropper.CropImageView;
import com.oguogu.dialog.ListDialog;
import com.oguogu.util.LogUtil;
import com.oguogu.util.StringUtil;
import com.oguogu.util.UIUtil;
import com.oguogu.vo.VoExtraInfo;
import com.oguogu.vo.VoPlaceCategory;
import com.oguogu.vo.VoWriteCateList;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 김민정 on 2017-09-29.
 */

public class WriteNewPlaceActivity extends BaseActivity {

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
    @Bind(R.id.tv_image) TextView tv_image;
    @Bind(R.id.ll_add_info) LinearLayout ll_add_info;
    @Bind(R.id.btn_add_info) Button btn_add_info;
    @Bind(R.id.tv_addr_srch) EditText tv_addr_srch;
    @Bind(R.id.btn_addr_srch) Button btn_addr_srch;

    private SupportMapFragment mapFragment;
    private HttpRequest mRequest = null;
    private final float MAP_ZOOM_LEVEL = 14f;

    private float mWidth = 0;
    private VoWriteCateList writeCateList;
    private ArrayList<VoPlaceCategory> placeCateList = new ArrayList<>();
    private int placeType;
    private String cateType;
    private int imgCount = 0;
    private ImageView imageView = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_new_place);

        ButterKnife.bind(this);

        mWidth = (UIUtil.getScreenWidthPixcels(this) - UIUtil.dpToPixel(40, this)) / 5;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) mWidth);
        ll_imgs.setLayoutParams(params);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        //getCategoryInfo();
        setCategory();
    }

    private double Latitude;
    private double Longitude;
    private String mAddr;

    private void getPointFromGeoCoder() {
        Toast.makeText(this, mAddr, Toast.LENGTH_SHORT).show();

        Geocoder geocoder = new Geocoder(this);
        List<Address> listAddress = new ArrayList<>();

        try {
            listAddress = geocoder.getFromLocationName(mAddr, 1);
        }catch (IOException e) {
            LogUtil.e(e.toString());
            Longitude = -1;
            Latitude = -1;
        }

        Longitude = listAddress.get(0).getLongitude();
        Latitude = listAddress.get(0).getLatitude();

       mapFragment.getMapAsync(mapReadyCallback);
    }

    OnMapReadyCallback mapReadyCallback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            LatLng storeLocal = new LatLng(Latitude, Longitude);
            googleMap.addMarker(new MarkerOptions().position(storeLocal).title(mAddr));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(storeLocal, MAP_ZOOM_LEVEL));
        }
    };

    private void getCategoryInfo() {

        showDialog();
        if(mRequest == null) mRequest = HttpRequest.getInstance(this);

        String url = ConstantCommURL.getURL(ConstantCommURL.URL_API, ConstantCommURL.REQUEST_GET_STORY);

        mRequest.StringRequest(ConstantCommURL.REQUEST_TAG_STORY, Request.Method.GET, url, "", new HttpRequest.ListenerHttpResponse() {

            @Override
            public void success(String response) {
                hideDialog();
                setCategory();
            }

            @Override
            public void fail(JSONObject response) {
                hideDialog();
            }

            @Override
            public void exception(VolleyError error) {
                hideDialog();
            }

            @Override
            public void networkerror() {
                hideDialog();
            }
        });
    }

    private void writePlace() {

        if(mRequest == null) mRequest = HttpRequest.getInstance(this);

        String url = ConstantCommURL.getURL(ConstantCommURL.URL_API, ConstantCommURL.REQUEST_SET_WRITE_PLACE);
        url = "http://api.mm.moumou.co.kr/api/common/SetErrNotify";

        Map<String, String> params = new Hashtable<>();
        params.put("APPVER", "1.3.8");
        params.put("SYSGB", "101003");
        params.put("MODEL", "EMST");
        params.put("PCODE", "999999");
        params.put("ERRORCONTENTS", "a");
        params.put("ERRORGUBUN", "204001");
        params.put("CHACI", "00");
        params.put("USERID", "booby1003");

        Map<String, DataPart> dataParams = new Hashtable<>();
        dataParams.put("ATTACHFILE", new DataPart("file_avatar.jpg", AppHelper.getFileDataFromDrawable(getBaseContext(), imageView.getDrawable()), "image/jpeg"));

        mRequest.multipartRequest(url, params, dataParams, new HttpRequest.ListenerHttpResponse(){

            @Override
            public void success(String response) {
                LogUtil.i("response :: " + response);
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


    private void setCategory() {

        String msg = null;
        try {
            msg = StringUtil.getData(this, "write_category.json");

        } catch (IOException e) {
            e.printStackTrace();
        }

        writeCateList = GlobalApplication.getGson().fromJson(msg, VoWriteCateList.class);

        int placeType = 0;
        this.placeType = 1;
        for(VoPlaceCategory placeCategory : writeCateList.getCateTypeList()) {
            if(placeType != placeCategory.getPlaceType()) {
                placeCateList.add(placeCategory);
                placeType = placeCategory.getPlaceType();
            }
        }

        for(VoExtraInfo voExtraInfo : writeCateList.getExtraInfoList()) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_add_info, null);
            TextView tv_extra_conts = (TextView) view.findViewById(R.id.tv_extra_conts);
            tv_extra_conts.setText(voExtraInfo.getInfoConts());
            CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox_extra);
            checkBox.setTag(voExtraInfo.getExtraInfoType());
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Toast.makeText(WriteNewPlaceActivity.this, "isChecked : " + isChecked, Toast.LENGTH_SHORT).show();
                    if(buttonView.isChecked()) {
                        buttonView.setChecked(true);
                    }else{
                        buttonView.setChecked(false);
                        Toast.makeText(WriteNewPlaceActivity.this, (String) buttonView.getTag(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            ll_add_info.addView(view);
        }
    }

    @OnClick({R.id.btn_close, R.id.btn_wirte, R.id.btn_add_info, R.id.tv_place_type, R.id.tv_place_cate, R.id.btn_camera, R.id.btn_album, R.id.btn_addr_srch})
    public void onClickButton(View view) {
        switch (view.getId()) {
            case R.id.btn_close:
                finish();
                break;
            case R.id.btn_wirte:
                writePlace();
                break;
            case R.id.btn_add_info:
                View viewAddInfo = LayoutInflater.from(this).inflate(R.layout.item_add_info, null);
                viewAddInfo.findViewById(R.id.tv_extra_conts).setVisibility(View.GONE);
                viewAddInfo.findViewById(R.id.et_add_extra).setVisibility(View.VISIBLE);
                viewAddInfo.findViewById(R.id.btn_extra_delete).setVisibility(View.VISIBLE);
                viewAddInfo.findViewById(R.id.btn_extra_delete).setOnClickListener(deletAddInfoOnClickListener);
                ll_add_info.addView(viewAddInfo);
                break;
            case R.id.tv_place_type:
                showCateDialog();
                break;
            case R.id.tv_place_cate:
                showSubCateDialog();
                break;
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
            case R.id.btn_addr_srch:
                mAddr = tv_addr_srch.getText().toString();
                getPointFromGeoCoder();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        LogUtil.i("onActivityResult");

        if(resultCode != RESULT_OK) return;

        switch (requestCode) {

            case GalleryActivity.PICK_FROM_ALBUM: {
                LogUtil.i("PICK_FROM_ALBUM");

                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                addImage(result.getUri());


                //iv_img.setImageURI(result.getUri());
                //this.imageView = imageView;

                LogUtil.i("imageUrl :: " + result.getUri());
                break;
            }
            case GalleryActivity.PICK_FROM_CAMERA:{
                LogUtil.i("Gallery PICK_FROM_CAMERA");

                Uri cameraUri = null;

                String[] IMAGE_PROJECTION = {
                        MediaStore.Images.ImageColumns.DATA,
                        MediaStore.Images.ImageColumns._ID,
                };

                try {
                    Cursor cursorImages = getContentResolver().query(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            IMAGE_PROJECTION, null, null,null);

                    if (cursorImages != null && cursorImages.moveToLast()) {
                        cameraUri = Uri.parse("file://" + cursorImages.getString(0)); //경로
                        int id = cursorImages.getInt(1);                //아이디
                        cursorImages.close();   // 커서 사용이 끝나면 꼭 닫아준다.
                    }
                } catch(Exception e) {
                    e.printStackTrace();
                }

                if (cameraUri != null) {
                    LogUtil.i("imageUri :: " + cameraUri);
                    CropImage.activity(cameraUri)
                            .setGuidelines(CropImageView.Guidelines.OFF)
                            .setBorderLineColor(Color.WHITE)
                            .setBorderLineThickness(5)
                            .setCircleSize(40)
                            .setCircleColor(Color.WHITE)
                            .setBackgroundColor(Color.argb(170, 0, 0, 0))
                            .start(this);
                }
//                Uri cameraUri = data.getData();
//                LogUtil.i("cameraUri :: " + cameraUri);
//
//                if (cameraUri != null) {
//                    LogUtil.i("cameraUri :: " + cameraUri);
//                    CropImage.activity(cameraUri)
//                            .setGuidelines(CropImageView.Guidelines.OFF)
//                            .setBorderLineColor(Color.WHITE)
//                            .setBorderLineThickness(5)
//                            .setCircleSize(40)
//                            .setCircleColor(Color.WHITE)
//                            .setBackgroundColor(Color.argb(170, 0, 0, 0))
//                            .start(this);
//                }
                break;
            }
            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
            {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                addImage(result.getUri());

                LogUtil.i("imageUrl :: " + result.getUri());
                break;
            }
        }
        tv_image.setVisibility(View.GONE);
        imgCount++;
    }

    private void addImage(Uri imageUri) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) mWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        params.rightMargin = UIUtil.dpToPixel(5, this);

        View imageView = LayoutInflater.from(this).inflate(R.layout.item_image, null);
        imageView.setLayoutParams(params);

        ImageView iv_img = (ImageView) imageView.findViewById(R.id.iv_img);
        iv_img.setImageURI(imageUri);
        imageView.findViewById(R.id.iv_img).setOnClickListener(deleteImageOnClickListener);
        ll_imgs.addView(imageView);
    }

    View.OnClickListener deleteImageOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            View parent = (View) v.getParent();
            ll_imgs.removeView(parent);
            imgCount--;

            if(imgCount == 0) tv_image.setVisibility(View.VISIBLE);
        }
    };

    View.OnClickListener deletAddInfoOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            View parent = (View) v.getParent();
            ll_add_info.removeView(parent);
        }
    };

    private ListDialog listDialog = null;

    private void showCateDialog() {

        if(listDialog == null) {
            listDialog = new ListDialog(this);
        }
        listDialog.show();
        listDialog.setListType(ListDialog.ListType.PlaceCate);
        listDialog.setOnClickListener(cateOnClickListener);
        listDialog.setDataList(placeCateList);
    }

    private void showSubCateDialog() {

        ArrayList<VoPlaceCategory> arrayList = new ArrayList<>();

        for(VoPlaceCategory placeCategory : writeCateList.getCateTypeList()) {
            if(placeType == placeCategory.getPlaceType()) {
                arrayList.add(placeCategory);
            }
        }

        if(listDialog == null) {
            listDialog = new ListDialog(this);
        }
        listDialog.show();
        listDialog.setListType(ListDialog.ListType.PlaceSubCate);
        listDialog.setOnClickListener(subCateOnClickListener);
        listDialog.setDataList(arrayList);
    }

    View.OnClickListener cateOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            VoPlaceCategory category = (VoPlaceCategory) v.getTag();
            placeType = category.getPlaceType();
            iv_place_type.setImageResource(UIUtil.getBoardTypeDrawable(placeType));
            tv_place_type.setText(category.getPlaceName());
            listDialog.dismiss();
        }
    };

    View.OnClickListener subCateOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            VoPlaceCategory category = (VoPlaceCategory) v.getTag();
            cateType = category.getCateType();
            tv_place_cate.setText(category.getCateName());
            listDialog.dismiss();
        }
    };

}



























