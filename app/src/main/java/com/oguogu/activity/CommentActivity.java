package com.oguogu.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.android.gms.wearable.MessageApi;
import com.oguogu.GlobalApplication;
import com.oguogu.R;
import com.oguogu.adapter.CommentAdapter;
import com.oguogu.communication.ConstantCommURL;
import com.oguogu.communication.HttpRequest;
import com.oguogu.vo.VoCommentList;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by booby on 2018-03-25.
 */

public class CommentActivity extends BaseActivity {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.btn_back) ImageButton btn_back;
    @Bind(R.id.recycler_view) RecyclerView recycler_view;
    @Bind(R.id.edit_commt) EditText edit_commt;
    @Bind(R.id.btn_confirm) Button btn_confirm;

    private CommentAdapter adapter;
    HttpRequest mRequest = null;
    VoCommentList commentList = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));

        getCommentList();

    }

    private void getCommentList() {

        showDialog();

        if(mRequest == null) mRequest = HttpRequest.getInstance(this);

        String url = ConstantCommURL.getURL(ConstantCommURL.URL_API, ConstantCommURL.REQUEST_GET_COMMENT);
        url = url + "/5a4d512024e37e97ed71d351/mynaman";


        mRequest.StringRequest(ConstantCommURL.REQUEST_TAG_COMMENT, Request.Method.GET, url, "", new HttpRequest.ListenerHttpResponse() {

            @Override
            public void success(String response) {
                hideDialog();
                commentList = GlobalApplication.getGson().fromJson(response, VoCommentList.class);
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

    private void setCommentList() {

        adapter = new CommentAdapter(commentList.getData(), this);
        recycler_view.setAdapter(adapter);
        recycler_view.scrollToPosition(commentList.getData().size() - 1);

    }


    private void setWriteCommt() {
        //CommentUserId, Comment

        showDialog();

        String commt = edit_commt.getText().toString();
        Map<String, String> data = new HashMap<>();
        data.put("", commt);

        if(mRequest == null) mRequest = HttpRequest.getInstance(this);

        String url = ConstantCommURL.getURL(ConstantCommURL.URL_API, ConstantCommURL.REQUEST_SET_WRITE_COMMT);
        url = url + "/5a4d512024e37e97ed71d351";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("CommentUserId", "mynaman");
            jsonObject.put("Comment", edit_commt.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }


        mRequest.JsonObjectRequest(ConstantCommURL.REQUEST_TAG_SET_WRITE_COMMT, Request.Method.POST, url, jsonObject, new HttpRequest.ListenerHttpResponse() {
            @Override
            public void success(String response) {
                hideDialog();
                commentList = GlobalApplication.getGson().fromJson(response, VoCommentList.class);
                edit_commt.setText("");
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



    @OnClick({R.id.btn_back, R.id.btn_confirm})
    public void onClickButton(View view) {

        switch (view.getId()) {
            case R.id.btn_back:
                onBackPressed();
                break;
            case R.id.btn_confirm:
                setWriteCommt();
                break;

        }
    }


}
