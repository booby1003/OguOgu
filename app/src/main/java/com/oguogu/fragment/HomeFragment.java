package com.oguogu.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.oguogu.GlobalApplication;
import com.oguogu.R;
import com.oguogu.activity.SearchActivity;
import com.oguogu.adapter.ContentListAdapter;
import com.oguogu.bus.BusEvent;
import com.oguogu.bus.BusProvider;
import com.oguogu.communication.ConstantCommURL;
import com.oguogu.communication.HttpRequest;
import com.oguogu.dialog.LoadingDialog;
import com.oguogu.util.LogUtil;
import com.oguogu.util.StringUtil;
import com.oguogu.vo.VoHomeList;
import com.squareup.otto.Subscribe;

import org.json.JSONObject;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016-12-15.
 */

public class HomeFragment extends BaseFragment {

    HttpRequest mRequest = null;

    @Bind(R.id.toolbar) Toolbar toolbar;
   // @Bind(R.id.btn_search) Button btn_search;

    @Bind(R.id.editSearch) EditText editSearch;
   // @Bind(R.id.btn_back) Button btn_back;
    @Bind(R.id.btn_srch_cancel) Button btn_srch_cancel;
    @Bind(R.id.btn_toolbar_location) Button btn_toolbar_location;

    @Bind(R.id.swipeRefresh) SwipeRefreshLayout swipeRefresh;
    @Bind(R.id.recycler_view) RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BusProvider.getInstance().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
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

        // StaggeredGrid 레이아웃을 사용한다
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        //layoutManager = new LinearLayoutManager(this);
        //layoutManager = new GridLayoutManager(this,3);

        // 지정된 레이아웃매니저를 RecyclerView에 Set 해주어야한다.
        recyclerView.setLayoutManager(layoutManager);

        getHomeList();

        return view;
    }

    private void getHomeList() {

        showDialog();

        if(mRequest == null) mRequest = HttpRequest.getInstance(getContext());

        String url = ConstantCommURL.getURL(ConstantCommURL.URL_API, ConstantCommURL.REQUEST_GET_HOME);
        Uri.Builder builder = Uri.parse(url).buildUpon();
        //builder.appendQueryParameter("", "");

        mRequest.StringRequest(ConstantCommURL.REQUEST_TAG_HOME, Request.Method.GET, builder.toString(), "", new HttpRequest.ListenerHttpResponse() {

            @Override
            public void success(String response) {

                hideDialog();
                LogUtil.d("HomeFragment response : " + response.toString());

                VoHomeList homeList = GlobalApplication.getGson().fromJson(response, VoHomeList.class);
                ContentListAdapter adapter = new ContentListAdapter(homeList.getData(), getActivity());
                recyclerView.setAdapter(adapter);

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

    @OnClick({R.id.btn_srch, R.id.btn_toolbar_location})
    public void onClickButton(View view) {
        switch (view.getId()) {
            case R.id.btn_srch:
                Intent intent = new Intent(getActivity(), SearchActivity.class);

                Pair<View, String> p1 = Pair.create((View)editSearch, "edit");
                //Pair<View, String> p2 = Pair.create((View)btn_back, "back");
                Pair<View, String> p2 = Pair.create((View)btn_srch_cancel, "srch_cancel");
                Pair<View, String> p3 = Pair.create((View)btn_toolbar_location, "location");

                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(getActivity(), p1, p2, p3);

                startActivity(intent, options.toBundle());

                break;
        }
    }

    @Override
    public void refresh() {

    }

    @Override
    public void willBeHidden() {

    }

    @Override
    public void willBeDisplayed() {

    }

    @Override
    public void onDestroy() {

        BusProvider.getInstance().unregister(this);

        super.onDestroy();
    }

    @Subscribe
    public void FinishSearch(BusEvent busEvent) {

        Toast.makeText(getActivity(), "검색어 : " + busEvent.getEventData(), Toast.LENGTH_SHORT).show();
    }

}
