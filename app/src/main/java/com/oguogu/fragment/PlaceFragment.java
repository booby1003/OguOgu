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
import android.widget.ImageButton;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.oguogu.GlobalApplication;
import com.oguogu.R;
import com.oguogu.activity.SearchActivity;
import com.oguogu.adapter.ContentListAdapter;
import com.oguogu.adapter.PlaceListAdapter;
import com.oguogu.communication.ConstantCommURL;
import com.oguogu.communication.HttpRequest;
import com.oguogu.vo.VoHomeList;
import com.oguogu.vo.VoPlaceList;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016-12-26.
 */

public class PlaceFragment extends BaseFragment {

    HttpRequest mRequest = null;

    @Bind(R.id.toolbar) Toolbar toolbar;

    @Bind(R.id.editSearch) EditText editSearch;
    @Bind(R.id.btn_srch_cancel) Button btn_srch_cancel;
    @Bind(R.id.btn_toolbar_location) Button btn_toolbar_location;

    @Bind(R.id.btn_all) ImageButton btn_all;
    @Bind(R.id.btn_cafe) ImageButton btn_cafe;
    @Bind(R.id.btn_hospital) ImageButton btn_hospital;
    @Bind(R.id.btn_playground) ImageButton btn_playground;

    @Bind(R.id.swipeRefresh) SwipeRefreshLayout swipeRefresh;
    @Bind(R.id.recycler_view) RecyclerView recyclerView;

    private PlaceListAdapter photoListAdapter;
    private VoPlaceList voPlaceList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_place, container, false);
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

        // 지정된 레이아웃매니저를 RecyclerView에 Set 해주어야한다.
        recyclerView.setLayoutManager(layoutManager);

        btn_all.setSelected(true);
        getPlaceList();

        return view;
    }

    private void getPlaceList() {

//        String msg = null;
//        try {
//            msg = StringUtil.getData(getActivity(), "place_list.json");
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        bookmarkList = GlobalApplication.getGson().fromJson(msg, VoHomeList.class);
//
//        photoListAdapter = new ContentListAdapter(bookmarkList.getData(), getActivity());
//        recyclerView.setAdapter(photoListAdapter);

        showDialog();

        if(mRequest == null) mRequest = HttpRequest.getInstance(getContext());

        String url = ConstantCommURL.getURL(ConstantCommURL.URL_API, ConstantCommURL.REQUEST_GET_PLACE);
        Uri.Builder builder = Uri.parse(url).buildUpon();
        //builder.appendQueryParameter("", "");

        mRequest.StringRequest(ConstantCommURL.REQUEST_TAG_PLACE, Request.Method.GET, builder.toString(), "", new HttpRequest.ListenerHttpResponse() {

            @Override
            public void success(String response) {

                hideDialog();
                voPlaceList = GlobalApplication.getGson().fromJson(response, VoPlaceList.class);

                photoListAdapter = new PlaceListAdapter(voPlaceList.getData(), getActivity());
                recyclerView.setAdapter(photoListAdapter);

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

    @OnClick({R.id.btn_srch, R.id.btn_all, R.id.btn_cafe, R.id.btn_hospital, R.id.btn_playground})
    public void onClickButton(View view) {
        removeBtnSelected();
        view.setSelected(true);

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
            case R.id.btn_all:
                photoListAdapter.setItems(voPlaceList.getData());
                break;
            case R.id.btn_cafe:
                photoListAdapter.setItem(voPlaceList.getData(), VoHomeList.PLACE_TYPE_RESTAURANT);
                break;
            case R.id.btn_hospital:
                photoListAdapter.setItem(voPlaceList.getData(), VoHomeList.PLACE_TYPE_HOSPITAL);
                break;
            case R.id.btn_playground:
                photoListAdapter.setItem(voPlaceList.getData(), VoHomeList.PLACE_TYPE_PLAYGROUND);
                break;
        }
    }

    private void removeBtnSelected() {
        btn_all.setSelected(false);
        btn_cafe.setSelected(false);
        btn_hospital.setSelected(false);
        btn_playground.setSelected(false);
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

}
