package com.oguogu.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.oguogu.GlobalApplication;
import com.oguogu.R;
import com.oguogu.adapter.RelationPhotoListAdapter;
import com.oguogu.communication.ConstantCommURL;
import com.oguogu.communication.HttpRequest;
import com.oguogu.custom.CustomBitmapPool;
import com.oguogu.util.StringUtil;
import com.oguogu.util.UIUtil;
import com.oguogu.view.ViewPagerIndicator;
import com.oguogu.vo.VoCommentList;
import com.oguogu.vo.VoPlaceDetail;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Administrator on 2016-12-26.
 */

public class PlaceDetailActivity extends BaseActivity {

    public static final String PLACE_IDX = "PLACE_IDX";
    HttpRequest mRequest = null;

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.tv_store_name) TextView tv_store_name;
    @Bind(R.id.btn_back) ImageButton btn_back;
    //@Bind(R.id.viewPager) ViewPager viewPager;
    @Bind(R.id.btn_like) ImageButton btn_like;
    @Bind(R.id.btn_bookmark) ImageButton btn_bookmark;
    @Bind(R.id.btn_comment) ImageButton btn_comment;
    @Bind(R.id.tv_register) TextView tv_register;
    @Bind(R.id.tv_store_name2) TextView tv_store_name2;
    @Bind(R.id.tv_content) TextView tv_content;
    @Bind(R.id.iv_store_type) ImageView iv_store_type;
    @Bind(R.id.tv_addr) TextView tv_addr;
    @Bind(R.id.tv_tel) TextView tv_tel;
    @Bind(R.id.tv_time) TextView tv_time;
    @Bind(R.id.tv_store_info) TextView tv_store_info;
    @Bind(R.id.tv_like_count) TextView tv_like_count;
    @Bind(R.id.tv_bookmark_count) TextView tv_bookmark_count;
    @Bind(R.id.tv_comment_count) TextView tv_comment_count;
    @Bind(R.id.layoutComment) LinearLayout layoutComment;
    @Bind(R.id.tv_show_comment_all) TextView tv_show_comment_all;
    @Bind(R.id.recycler_view) RecyclerView recyclerView;


    ViewPager viewPager;
    ViewPagerIndicator indicator;

//    @Bind(R.id.scrollView) ScrollView scrollView;

    private VoPlaceDetail placeDetail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);

        ButterKnife.bind(this);

        Intent intent = getIntent();

        String placeIdx = intent.getStringExtra(PLACE_IDX);

        indicator = (ViewPagerIndicator) findViewById(R.id.indicator);

//        recyclerView.setHasFixedSize(true);

        // StaggeredGrid 레이아웃을 사용한다
//        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
//        layoutManager = new LinearLayoutManager(this);

        GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        layoutManager.setAutoMeasureEnabled(true);
        // 지정된 레이아웃매니저를 RecyclerView에 Set 해주어야한다.
        recyclerView.setLayoutManager(layoutManager);

        viewPager = (ViewPager) findViewById(R.id.viewPager);

        placeIdx = "5a4d512024e37e97ed71d34d";
        getStoreDetail(placeIdx);


    }

    private void getStoreDetail(String placeIdx) {

//        String msg = null;
//        try {
//            msg = StringUtil.getData(this, "place_detail.json");
//
//            placeDetail = GlobalApplication.getGson().fromJson(msg, VoPlaceDetail.class);
//            setPlaceDetail();
//            setCommentList();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        if(mRequest == null) mRequest = HttpRequest.getInstance(this);

        String url = ConstantCommURL.getURL(ConstantCommURL.URL_API, ConstantCommURL.REQUEST_GET_PLACE_DETAIL);
        url = url + "/5a4d512024e37e97ed71d351/mynaman";
//        url = url + "/mynaman";
//        url = url + "/" + placeIdx;
        //builder.appendQueryParameter("", "");

        mRequest.StringRequest(ConstantCommURL.REQUEST_TAG_PLACE, Request.Method.GET, url, "", new HttpRequest.ListenerHttpResponse() {

            @Override
            public void success(String response) {

                hideDialog();
                placeDetail = GlobalApplication.getGson().fromJson(response, VoPlaceDetail.class);
                setPlaceDetail();
                setCommentList();
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

    private  void setPlaceDetail() {

        ViewPagerImgAdapter viewPagerImgAdapter = new ViewPagerImgAdapter(PlaceDetailActivity.this, placeDetail.getImgList());
        viewPager.setAdapter(viewPagerImgAdapter);
        viewPager.addOnPageChangeListener(onPageChangeListener);

        indicator.setItemMargin(20);
        indicator.createIndicator(placeDetail.getImgList().size());


        tv_store_name.setText(placeDetail.getPlaceName());
        tv_register.setText("By " + placeDetail.getRegUserId());
        tv_store_name2.setText(placeDetail.getPlaceName());
        Glide.with(this).load(UIUtil.getPlaceTypeDrawable(placeDetail.getPlaceType())).into(iv_store_type);
        //Glide.with(this).load(placeDetail.getImgThumbPath()).into(holder.iv_img);
        tv_addr.setText(placeDetail.getPlaceAddr());
        tv_content.setText(placeDetail.getConts());
        tv_tel.setText(placeDetail.getTelNo());
        tv_time.setText(placeDetail.getTime());

        String storeInfo="";
        for (int idx=0; idx<placeDetail.getExtraInfoList().size(); idx++) {

            String info = placeDetail.getExtraInfoList().get(idx);

            if (idx > 0)
                storeInfo += " | ";

            storeInfo += info;
        }
        tv_store_info.setText(storeInfo);

        tv_like_count.setText(placeDetail.getTotalLikeCnt());
        tv_bookmark_count.setText(placeDetail.getTotalBookmarkCnt());
        tv_comment_count.setText(placeDetail.getTotalCommentCnt());

        //setCommentList();



        RelationPhotoListAdapter relationPhotoListAdapter = new RelationPhotoListAdapter(placeDetail.getRelationList(), this);
        recyclerView.setAdapter(relationPhotoListAdapter);
    }

    private void setCommentList() {
        layoutComment.removeAllViews();

        for (int idx=0; idx<placeDetail.getCommentList().size(); idx++) {
            VoCommentList.VoComment commentInfo = placeDetail.getCommentList().get(idx);

            View view = LayoutInflater.from(this).inflate(R.layout.item_comment, null);
            ImageView iv_comment_img = (ImageView)view.findViewById(R.id.iv_comment_img);
            TextView tv_comment_nickname = (TextView)view.findViewById(R.id.tv_comment_nickname);
            TextView tv_comment_date = (TextView)view.findViewById(R.id.tv_comment_date);
            TextView tv_comment_content = (TextView)view.findViewById(R.id.tv_comment_content);
            ImageButton btnDeleteComment = (ImageButton)view.findViewById(R.id.btnDeleteComment);

            Glide.with(this).load(commentInfo.getCommentUserThumbPath()).bitmapTransform(new CropCircleTransformation(new CustomBitmapPool())).into(iv_comment_img);
            tv_comment_nickname.setText(commentInfo.getCommentUserId());
            tv_comment_date.setText(commentInfo.getCommentDate());
            tv_comment_content.setText(commentInfo.getComment());

            iv_comment_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(PlaceDetailActivity.this, "유저 정보로 이동", Toast.LENGTH_SHORT).show();
                }
            });

            btnDeleteComment.setTag(commentInfo);
            if (commentInfo.isMyComment()) {
                btnDeleteComment.setVisibility(View.VISIBLE);
                btnDeleteComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        VoCommentList.VoComment deleteComment = (VoCommentList.VoComment)v.getTag();
                        placeDetail.getCommentList().remove(deleteComment);
                        setCommentList();

                        Toast.makeText(PlaceDetailActivity.this, "삭제!", Toast.LENGTH_SHORT).show();
                    }
                });
            }


            layoutComment.addView(view);
        }
    }

    /**
     * Top 이미지 Adapter
     */
    public class ViewPagerImgAdapter extends PagerAdapter {

        private Context context;
        private ArrayList<String> items;

        public ViewPagerImgAdapter(Context context, ArrayList<String> items) {
            this.context = context;
            this.items = items;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((ImageView) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            String imgPath = items.get(position);

            Glide.with(context)
                    .load(imgPath)
                    .crossFade()
                    .into(imageView);

            ((ViewPager) container).addView(imageView, 0);

            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((ImageView) object);
        }

    }

    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            indicator.selectedIndicator(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finishAnimation();
    }

    @OnClick({R.id.btn_back, R.id.tv_show_comment_all, R.id.btn_like, R.id.btn_bookmark, R.id.tv_addr, R.id.tv_tel})
    public void onClickButton(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finishAnimation();
                break;
            case R.id.tv_show_comment_all:
                startActivity(new Intent(this, CommentActivity.class));
                break;
            case R.id.btn_like:
                Toast.makeText(this, "좋아요", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_bookmark:
                Toast.makeText(this, "북마크", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_addr:
                Toast.makeText(this, "지도로 이동", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_tel:
                Toast.makeText(this, "전화걸기", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void finishAnimation() {
        finish();
        overridePendingTransition(R.anim.slide_left_to_center, R.anim.slide_left_to_right);
    }

//    private int getBoardTypeDrawable() {
//        int boardTypeDrawable = 0;
//        if (storeDetail.getBoardType() == VoPlaceDetail.TYPE_CAFE)
//            boardTypeDrawable = R.drawable.icon_type_cafe;
//        else if (storeDetail.getBoardType() == VoPlaceDetail.TYPE_HOSPITAL)
//            boardTypeDrawable = R.drawable.icon_type_hospital;
//        else if (storeDetail.getBoardType() == VoPlaceDetail.TYPE_PLAYGROUND)
//            boardTypeDrawable = R.drawable.icon_type_gowalk;
//
//        return boardTypeDrawable;
//    }
}
