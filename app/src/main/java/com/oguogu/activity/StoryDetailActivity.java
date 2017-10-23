package com.oguogu.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.oguogu.GlobalApplication;
import com.oguogu.R;
import com.oguogu.custom.CustomBitmapPool;
import com.oguogu.custom.CustomViewPager;
import com.oguogu.util.StringUtil;
import com.oguogu.vo.VoDetail;
import com.oguogu.vo.VoHomeList;
import com.oguogu.vo.VoMyInfo;
import com.oguogu.vo.VoPlaceDetail;
import com.oguogu.vo.VoStoryDetail;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Administrator on 2016-12-26.
 */

public class StoryDetailActivity extends AppCompatActivity {

    public static final String BOARD_IDX = "BOARD_IDX";

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.tv_store_name) TextView tv_store_name;
    @Bind(R.id.tv_register_userid) TextView tv_register_userid;
    @Bind(R.id.iv_register_user) ImageView iv_register_user;
    @Bind(R.id.btn_back) ImageButton btn_back;
    @Bind(R.id.btn_edit) ImageButton btn_edit;
    @Bind(R.id.view_pager) CustomViewPager viewPager;
    @Bind(R.id.btn_like) ImageButton btn_like;
    @Bind(R.id.btn_comment) ImageButton btn_comment;
    @Bind(R.id.tv_board_title) TextView tv_board_title;
    @Bind(R.id.tv_board_content) TextView tv_board_content;
    @Bind(R.id.tv_board_date) TextView tv_board_date;
    @Bind(R.id.iv_store_ico) ImageView iv_store_ico;
    @Bind(R.id.tv_store_name2) TextView tv_store_name2;
    @Bind(R.id.tv_store_addr) TextView tv_store_addr;
    @Bind(R.id.tv_like_count) TextView tv_like_count;
    @Bind(R.id.tv_comment_count) TextView tv_comment_count;
    @Bind(R.id.layoutComment) LinearLayout layoutComment;
    @Bind(R.id.layoutMyImg) LinearLayout layoutMyImg;

    private VoStoryDetail storyDetail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_detail);

        ButterKnife.bind(this);

        Intent intent = getIntent();

        String boardIdx = intent.getStringExtra(BOARD_IDX);

        getStoryDetail();
    }

    private void getStoryDetail() {

        String msg = null;
        try {
            msg = StringUtil.getData(this, "story_detail.json");

        } catch (IOException e) {
            e.printStackTrace();
        }

        storyDetail = GlobalApplication.getGson().fromJson(msg, VoStoryDetail.class);

        tv_store_name.setText(storyDetail.getStoreName());
        tv_board_title.setText(storyDetail.getBoard_title());
        tv_register_userid.setText(storyDetail.getRegister_userid());
        tv_board_date.setText(storyDetail.getBoard_date());
        tv_board_content.setText(storyDetail.getBoard_content());
        tv_store_name2.setText(storyDetail.getStoreName());
        tv_store_addr.setText(storyDetail.getStoreAddr());
        tv_like_count.setText(storyDetail.getTotal_like_cnt());
        tv_comment_count.setText(storyDetail.getTotal_comment_cnt());

        int storeTypeResId=0;
        if (storyDetail.getBoardType() == VoHomeList.TYPE_CAFE)
            storeTypeResId = R.drawable.icon_type_cafe_s;
        else if (storyDetail.getBoardType() == VoHomeList.TYPE_HOSPITAL)
            storeTypeResId = R.drawable.icon_type_hospital_s;
        else if (storyDetail.getBoardType() == VoHomeList.TYPE_PLAYGROUND)
            storeTypeResId = R.drawable.icon_type_gowalk_s;

        Glide.with(this).load(storeTypeResId).into(iv_store_ico);
        Glide.with(this).load(storyDetail.getRegUserThumbPath()).bitmapTransform(new CropCircleTransformation(new CustomBitmapPool())).into(iv_register_user);

        if (storyDetail.getRegister_userid().equals(VoMyInfo.getInstance().getId())) {
            btn_edit.setVisibility(View.VISIBLE);
        }

        ViewPagerImgAdapter adapter = new ViewPagerImgAdapter(StoryDetailActivity.this, storyDetail.getStore_img_list());
        viewPager.setAdapter(adapter);

        for (int idx=0; idx<storyDetail.getMy_img_list().size(); idx++) {
            VoPlaceDetail.VoImagePath imgInfo = storyDetail.getMy_img_list().get(idx);

            View view = LayoutInflater.from(this).inflate(R.layout.story_my_imageview, null);
            ImageView ivMyImg = (ImageView)view.findViewById(R.id.ivMyImg);
            Glide.with(this).load(imgInfo.getImg_path()).crossFade().into(ivMyImg);

            layoutMyImg.addView(view);
        }

        setCommentList();
    }

    private void setCommentList() {
        layoutComment.removeAllViews();

        for (int idx=0; idx<storyDetail.getComment_list().size(); idx++) {
            VoStoryDetail.VoComment commentInfo = storyDetail.getComment_list().get(idx);

            View view = LayoutInflater.from(this).inflate(R.layout.detail_comment_item, null);
            ImageView iv_comment_img = (ImageView)view.findViewById(R.id.iv_comment_img);
            TextView tv_comment_nickname = (TextView)view.findViewById(R.id.tv_comment_nickname);
            TextView tv_comment_date = (TextView)view.findViewById(R.id.tv_comment_date);
            TextView tv_comment_content = (TextView)view.findViewById(R.id.tv_comment_content);
            ImageButton btnDeleteComment = (ImageButton)view.findViewById(R.id.btnDeleteComment);

            Glide.with(this).load(commentInfo.getComment_user_thumb_path()).bitmapTransform(new CropCircleTransformation(new CustomBitmapPool())).into(iv_comment_img);
            tv_comment_nickname.setText(commentInfo.getComment_user_nickname());
            tv_comment_date.setText(commentInfo.getComment_date());
            tv_comment_content.setText(commentInfo.getComment());

            iv_comment_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(StoryDetailActivity.this, "유저 정보로 이동", Toast.LENGTH_SHORT).show();
                }
            });

            btnDeleteComment.setTag(commentInfo);
            if (commentInfo.is_my_comment()) {
                btnDeleteComment.setVisibility(View.VISIBLE);
                btnDeleteComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        VoDetail.VoComment deleteComment = (VoDetail.VoComment)v.getTag();
                        storyDetail.getComment_list().remove(deleteComment);
                        setCommentList();

                        Toast.makeText(StoryDetailActivity.this, "삭제!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            layoutComment.addView(view);
        }
    }

    public class ViewPagerImgAdapter extends PagerAdapter {

        private Context context;
        private ArrayList<VoPlaceDetail.VoImagePath> items;

        public ViewPagerImgAdapter(Context context, ArrayList<VoPlaceDetail.VoImagePath> items) {
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
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            VoPlaceDetail.VoImagePath info = items.get(position);

            Glide.with(context)
                    .load(info.getImg_path())
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
        overridePendingTransition(R.anim.slide_left_to_center, R.anim.slide_left_to_right);
    }

    @OnClick({R.id.btn_back, R.id.btn_edit, R.id.btn_like, R.id.btn_comment})
    public void onClickButton(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finishAnimation();
                break;
            case R.id.btn_edit:
                Toast.makeText(StoryDetailActivity.this, "글 수정페이지로 이동", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_comment:
                Toast.makeText(StoryDetailActivity.this, "코멘트 상세로 이동", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_like:
                Toast.makeText(StoryDetailActivity.this, "좋아요", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void finishAnimation() {
        finish();
        overridePendingTransition(R.anim.slide_left_to_center, R.anim.slide_left_to_right);
    }

}
