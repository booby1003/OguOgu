package com.oguogu.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.oguogu.GlobalApplication;
import com.oguogu.R;
import com.oguogu.adapter.PhotoListAdapter;
import com.oguogu.util.SharedPrefUtil;
import com.oguogu.util.StringUtil;
import com.oguogu.vo.VoHomeList;
import com.oguogu.vo.VoSearchList;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016-12-21.
 */

public class SearchActivity extends AppCompatActivity {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.editSearch) EditText editSearch;
    @Bind(R.id.btn_back) Button btn_back;
    @Bind(R.id.search_listview) ListView search_listview;
    @Bind(R.id.recycler_view) RecyclerView recycler_view;
    @Bind(R.id.btn_edit_delete) Button btnEditDelete;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        recycler_view.setHasFixedSize(true);

        // StaggeredGrid 레이아웃을 사용한다
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);

        // 지정된 레이아웃매니저를 RecyclerView에 Set 해주어야한다.
        recycler_view.setLayoutManager(layoutManager);

        editSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_SEARCH:
                        String inputText = v.getText().toString();
                        search(inputText);

                        break;
                    default:
                        // 기본 엔터키 동작
                        return false;
                }
                return true;
            }
        });

        btnEditDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editSearch.setText("");
            }
        });

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                String inputText = s.toString();
                if (inputText.length() > 0)
                    btnEditDelete.setVisibility(View.VISIBLE);
                else
                    btnEditDelete.setVisibility(View.GONE);
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }


        });

        getBestSearchList();
    }

    private void search(String searchName) {
        String searchWords = SharedPrefUtil.getStringSharedData(SearchActivity.this, SharedPrefUtil.KEY_MY_SEARCH_WORDS,"");
        String word = "_" + searchName + ";";

        if (searchWords.contains(word))
            searchWords = searchWords.replaceAll(word, "");

        searchWords = word + searchWords;
        SharedPrefUtil.setStringSharedData(SearchActivity.this, SharedPrefUtil.KEY_MY_SEARCH_WORDS, searchWords);

        search_listview.setVisibility(View.GONE);
        recycler_view.setVisibility(View.VISIBLE);

        getSearchList();

//        String eventData = new String();
//        BusProvider.getInstance().post(new BusEvent(searchName));
//
//        finishAfterTransition();
    }

    private void getSearchList() {
        String msg = null;
        try {
            msg = StringUtil.getData(this, "home_list.json");

        } catch (IOException e) {
            e.printStackTrace();
        }

        VoHomeList homeList = GlobalApplication.getGson().fromJson(msg, VoHomeList.class);

        homeList.getData().remove(0);
        homeList.getData().remove(1);
        PhotoListAdapter adapter = new PhotoListAdapter(homeList.getData(), this);
        recycler_view.setAdapter(adapter);
    }

    @OnClick({R.id.btn_back})
    public void onClickButton(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finishAfterTransition();
                break;
        }
    }

    private void getBestSearchList() {
        String msg = null;
        try {
            msg = StringUtil.getData(this, "best_search_list.json");

        } catch (IOException e) {
            e.printStackTrace();
        }

        VoSearchList bestSearchList = GlobalApplication.getGson().fromJson(msg, VoSearchList.class);

        ArrayList<VoSearchList.VoSearchInfo> list = new ArrayList<VoSearchList.VoSearchInfo>();
        list.add(new VoSearchList().new VoSearchInfo(VoSearchList.TYPE_TITLE, "인기검색어"));
        list.addAll(bestSearchList.getData());
        list.add(new VoSearchList().new VoSearchInfo(VoSearchList.TYPE_TITLE, "최근검색어"));

        String searchWords = SharedPrefUtil.getStringSharedData(SearchActivity.this, SharedPrefUtil.KEY_MY_SEARCH_WORDS,"");
        String[] mySearchWords = searchWords.split(";");

        if (!StringUtil.isNull(searchWords)) {
            for (String word : mySearchWords) {
                list.add(new VoSearchList().new VoSearchInfo(VoSearchList.TYPE_MY_SEARCH_INFO, word));
            }
        }

        SearchListViewAdapter adapter = new SearchListViewAdapter(list);
        search_listview.setAdapter(adapter);
    }
    
    public class SearchListViewAdapter extends BaseAdapter {

        // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
        private ArrayList<VoSearchList.VoSearchInfo> searchList = new ArrayList<VoSearchList.VoSearchInfo>() ;

        // SearchListViewAdapter의 생성자
        public SearchListViewAdapter(ArrayList<VoSearchList.VoSearchInfo> items) {
            searchList = items;
        }

        // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
        @Override
        public int getCount() {
            return searchList.size();
        }

        // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final int pos = position;
            final Context context = parent.getContext();

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            VoSearchList.VoSearchInfo info = searchList.get(position);

            ViewHolder viewHolder;
            // "listview_item" Layout을 inflate하여 convertView 참조 획득.
            if (convertView == null) {
                if (info.getType() == VoSearchList.TYPE_TITLE) {
                    convertView = inflater.inflate(R.layout.listview_search_title_item, parent, false);
                } else {
                    convertView = inflater.inflate(R.layout.listview_search_item, parent, false);
                }

                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder)convertView.getTag();
            }

            if (info.getType() == VoSearchList.TYPE_TITLE) {
                viewHolder.tv_title_name.setText(info.getName());
            } else {
                viewHolder.tv_search_name.setText(info.getName());
                viewHolder.tv_search_name.setTag(info.getName());
                viewHolder.tv_search_name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String searchName = (String)v.getTag();
                        search(searchName);
                    }
                });

                if (info.getType() == VoSearchList.TYPE_MY_SEARCH_INFO) {
                    viewHolder.tv_search_name.setText(info.getName().replace("_",""));
                    viewHolder.btn_delete.setVisibility(View.VISIBLE);
                    viewHolder.btn_delete.setTag(info);
                    viewHolder.btn_delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            VoSearchList.VoSearchInfo removeInfo = (VoSearchList.VoSearchInfo)v.getTag();
                            String deleteWord = removeInfo.getName();

                            String searchWords = SharedPrefUtil.getStringSharedData(SearchActivity.this, SharedPrefUtil.KEY_MY_SEARCH_WORDS,"");
                            searchWords = searchWords.replaceAll(deleteWord+";", "");
                            SharedPrefUtil.setStringSharedData(SearchActivity.this, SharedPrefUtil.KEY_MY_SEARCH_WORDS,searchWords);

                            searchList.remove(removeInfo);
                            notifyDataSetChanged();
                        }
                    });
                }
            }

            return convertView;
        }

        // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
        @Override
        public long getItemId(int position) {
            return position ;
        }

        // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
        @Override
        public Object getItem(int position) {
            return searchList.get(position) ;
        }

        @Override
        public int getItemViewType(int position) {

            if (searchList.get(position).getType() == VoSearchList.TYPE_TITLE)
                return VoSearchList.TYPE_TITLE;
            else if (searchList.get(position).getType() == VoSearchList.TYPE_BEST_SEARCH_INFO)
                return VoSearchList.TYPE_BEST_SEARCH_INFO;
            else
                return VoSearchList.TYPE_MY_SEARCH_INFO;

        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView tv_title_name;
            public TextView tv_search_name;
            public Button btn_delete;

            public ViewHolder(View view) {
                super(view);
                tv_title_name = (TextView) view.findViewById(R.id.tv_title_name);
                tv_search_name = (TextView) view.findViewById(R.id.tv_search_name);
                btn_delete = (Button) view.findViewById(R.id.btn_delete);
            }
        }
    }

}
