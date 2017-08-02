package com.oguogu.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.oguogu.GlobalApplication;
import com.oguogu.R;
import com.oguogu.custom.CustomBitmapPool;
import com.oguogu.util.StringUtil;
import com.oguogu.vo.VoFeedList;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by Administrator on 2016-12-26.
 */

public class FeedFragment extends BaseFragment {

    @Bind(R.id.feed_listview) ListView feed_listview;
    @Bind(R.id.swipeRefresh) SwipeRefreshLayout swipeRefresh;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        ButterKnife.bind(this, view);

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

        getFeedkList();

        return view;
    }

    private void getFeedkList() {
        String msg = null;
        try {
            msg = StringUtil.getData(getActivity(), "feed_list.json");

        } catch (IOException e) {
            e.printStackTrace();
        }

        VoFeedList feedList = GlobalApplication.getGson().fromJson(msg, VoFeedList.class);

        FeedListViewAdapter adapter = new FeedListViewAdapter(feedList.getData());
        feed_listview.setAdapter(adapter);

    }

    public class FeedListViewAdapter extends BaseAdapter {

        // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
        private ArrayList<VoFeedList.VoFeedInfo> feedList = new ArrayList<VoFeedList.VoFeedInfo>() ;

        // FeedListViewAdapter의 생성자
        public FeedListViewAdapter(ArrayList<VoFeedList.VoFeedInfo> items) {
            feedList = items;
        }

        // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
        @Override
        public int getCount() {
            return feedList.size();
        }

        // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final int pos = position;
            final Context context = parent.getContext();

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            VoFeedList.VoFeedInfo info = feedList.get(position);

            FeedListViewAdapter.ViewHolder viewHolder;
            // "listview_item" Layout을 inflate하여 convertView 참조 획득.
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.listview_feed_item, parent, false);

                viewHolder = new FeedListViewAdapter.ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (FeedListViewAdapter.ViewHolder)convertView.getTag();
            }

            Glide.with(context).load(info.getFeed_thumb_path()).bitmapTransform(new CropCircleTransformation(new CustomBitmapPool())).into(viewHolder.iv_feed_thumb);

            if (info.getFeed_type() == VoFeedList.TYPE_COMMENT) {
                viewHolder.tv_feed_content.setText(info.getFeed_nickname() + " 님이\n댓글을 남겼습니다.");
            } else {
                viewHolder.tv_feed_content.setText(info.getFeed_nickname() + " 님이\n회원님의 사진을 좋아요 하였습니다.");
            }

            viewHolder.tv_feed_date.setText(info.getFeed_date());

            Glide.with(context)
                    .load(info.getOrigin_thumb_path())
                    .crossFade()
                    .bitmapTransform(new RoundedCornersTransformation(new CustomBitmapPool(), 10, 5))
                    .into(viewHolder.iv_origin_thumb);

            viewHolder.iv_feed_thumb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "프로필로 이동! " , Toast.LENGTH_SHORT).show();
                }
            });

            viewHolder.tv_feed_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "게시글로 이동! " , Toast.LENGTH_SHORT).show();
                }
            });

            viewHolder.iv_origin_thumb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "게시글로 이동! " , Toast.LENGTH_SHORT).show();
                }
            });

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
            return feedList.get(position) ;
        }

        @Override
        public int getItemViewType(int position) {

            if (feedList.get(position).getFeed_type() == VoFeedList.TYPE_COMMENT)
                return VoFeedList.TYPE_COMMENT;
            else
                return VoFeedList.TYPE_LIKE;

        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public ImageView iv_feed_thumb;
            public TextView tv_feed_content;
            public ImageView iv_origin_thumb;
            public TextView tv_feed_date;

            public ViewHolder(View view) {
                super(view);
                iv_feed_thumb = (ImageView) view.findViewById(R.id.iv_feed_thumb);
                tv_feed_content = (TextView) view.findViewById(R.id.tv_feed_content);
                iv_origin_thumb = (ImageView) view.findViewById(R.id.iv_origin_thumb);
                tv_feed_date = (TextView) view.findViewById(R.id.tv_feed_date);
            }
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

}
