package com.oguogu.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.oguogu.GlobalApplication;
import com.oguogu.R;
import com.oguogu.activity.LoginActivity;
import com.oguogu.activity.MyPetsActivity;
import com.oguogu.activity.SettingActivity;
import com.oguogu.adapter.PhotoListAdapter;
import com.oguogu.custom.CustomBitmapPool;
import com.oguogu.util.LogUtil;
import com.oguogu.util.StringUtil;
import com.oguogu.vo.VoMyInfo;
import com.oguogu.vo.VoPhotoList;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Administrator on 2016-12-06.
 */

public class MyPageFragment extends BaseFragment  {

    @Bind(R.id.toolbar) Toolbar toolbar;

    @Bind(R.id.iv_userImg) ImageView iv_userImg;
    @Bind(R.id.tv_userid) TextView tv_userid;
    @Bind(R.id.tv_email) TextView tv_email;
    @Bind(R.id.btn_post_writing) ImageButton btn_post_writing;
    @Bind(R.id.btn_bookmark) ImageButton btn_bookmark;

    @Bind(R.id.swipeRefresh) SwipeRefreshLayout swipeRefresh;
    @Bind(R.id.recycler_view) RecyclerView recyclerView;

    private enum TabMenu {
        PostWrite,
        BookMark
    }

    private TabMenu currentMenu = TabMenu.PostWrite;
    private PhotoListAdapter adapter;
    private GridLayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_myinfo, container, false);
        ButterKnife.bind(this, view);

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        swipeRefresh.setColorSchemeResources(R.color.yellow, R.color.red, R.color.black, R.color.blue);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefresh.setRefreshing(false);
                    }
                },1000);
            }
        });

        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new PhotoListAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(recyOnScrollListener);

        String userImg = VoMyInfo.getInstance().getImgPath();

        LogUtil.i("userInfo : " + userImg);
        LogUtil.i("userInfo : " + VoMyInfo.getInstance().getId());
        LogUtil.i("userInfo : " + VoMyInfo.getInstance().getEmail());

        Glide.with(getContext()).load(userImg).bitmapTransform(new CropCircleTransformation(new CustomBitmapPool())).into(iv_userImg);

        tv_userid.setText(VoMyInfo.getInstance().getId());
        tv_email.setText(VoMyInfo.getInstance().getEmail());

        selectedTab();
        return view;
    }

    /**
     * 스크롤 제한
     */
    private RecyclerView.OnScrollListener recyOnScrollListener = new RecyclerView.OnScrollListener() {

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

            if(!isLoading && !isLastPage) {
                if((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= PAGE_SIZE) {
                    getDataList();
                }
            }
        }
    };

    private void selectedTab() {
        if(currentMenu == TabMenu.PostWrite) {
            btn_post_writing.setSelected(true);
            btn_bookmark.setSelected(false);
        }else if(currentMenu == TabMenu.BookMark) {
            btn_post_writing.setSelected(false);
            btn_bookmark.setSelected(true);
        }
        getDataList();
    }

    @OnClick({R.id.btn_post_writing, R.id.btn_bookmark, R.id.btn_mypet, R.id.btn_setting})
    public void onClickButton(View view) {
        switch (view.getId()) {
            case R.id.btn_post_writing:
                currentMenu = TabMenu.PostWrite;
                selectedTab();
                break;
            case R.id.btn_bookmark:
                currentMenu = TabMenu.BookMark;
                selectedTab();
                break;
            case R.id.btn_mypet:
                startActivity(new Intent(getContext(), MyPetsActivity.class));
                break;
            case R.id.btn_setting:
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
//                FirebaseAuth.getInstance().signOut();
//
//                startActivity(new Intent(getActivity(), LoginActivity.class));
//                getActivity().finish();
                break;
        }
    }

    /**
     *  getData
     */
    private void getDataList() {

        String msg = null;
        isLoading = true;

        if(currentMenu == TabMenu.BookMark) {
            try {
                msg = StringUtil.getData(getActivity(), "bookmark_list.json");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            try {
                msg = StringUtil.getData(getActivity(), "post_writing_list.json");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        isLoading = false;

        VoPhotoList homeList = GlobalApplication.getGson().fromJson(msg, VoPhotoList.class);
        adapter.setItems(homeList.getData());
    }

    @Override
    public void refresh() {
        selectedTab();
    }

    @Override
    public void willBeHidden() {

    }

    @Override
    public void willBeDisplayed() {

    }


}
