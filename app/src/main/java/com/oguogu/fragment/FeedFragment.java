package com.oguogu.fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.oguogu.GlobalApplication;
import com.oguogu.R;
import com.oguogu.adapter.ContentListAdapter;
import com.oguogu.common.Constant;
import com.oguogu.communication.ConstantCommURL;
import com.oguogu.communication.HttpRequest;
import com.oguogu.custom.CustomBitmapPool;
import com.oguogu.util.LogUtil;
import com.oguogu.util.StringUtil;
import com.oguogu.vo.VoFeedList;
import com.oguogu.vo.VoHomeList;

import org.json.JSONObject;

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

    HttpRequest mRequest = null;

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

        showDialog();
        if(mRequest == null) mRequest = HttpRequest.getInstance(getContext());

        String url = ConstantCommURL.getURL(ConstantCommURL.URL_API, ConstantCommURL.REQUEST_GET_FEED);
        Uri.Builder builder = Uri.parse(url).buildUpon();
        //builder.appendQueryParameter("", "");

        mRequest.StringRequest(ConstantCommURL.REQUEST_TAG_FEED, Request.Method.GET, builder.toString(), "", new HttpRequest.ListenerHttpResponse() {

            @Override
            public void success(String response) {

                hideDialog();
                VoFeedList feedList = GlobalApplication.getGson().fromJson(response, VoFeedList.class);
                FeedListViewAdapter adapter = new FeedListViewAdapter(feedList.getData());
                feed_listview.setAdapter(adapter);
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

            Glide.with(context).load(info.getRegThunmbPath()).bitmapTransform(new CropCircleTransformation(new CustomBitmapPool())).into(viewHolder.iv_feed_thumb);

            String str_place = "";
            String str_commt = "";
            boolean isPlace = false;

            if(info.getBoardType() == Constant.PLACE_TYPES.PLACE_TYPE_CAFE) {
                str_place = "강아지 카페";
                isPlace = true;
            }else if(info.getBoardType() == Constant.PLACE_TYPES.PLACE_TYPE_HOSPITAL) {
                str_place = "동물병원";
                isPlace = true;
            }else if(info.getBoardType() == Constant.PLACE_TYPES.PLACE_TYPE_GOWALK) {
                str_place = "산책/놀이터";
                isPlace = true;
            }else{
                str_place = "회원님의 글";
                isPlace = false;
            }

            if (info.getFeedType() == VoFeedList.TYPE_COMMENT) {
                str_commt = "에 댓글을 남겼습니다. ";
               // viewHolder.tv_feed_content.setText(info.getFeed_nickname() + " 님이 댓글을 남겼습니다.");
            } else if(info.getFeedType() == VoFeedList.TYPE_LIKE){
                str_commt = "에 좋아요 하였습니다. ";
                //viewHolder.tv_feed_content.setText(info.getFeed_nickname() + " 님이 회원님의 사진을 좋아요 하였습니다.");
            } else if(info.getFeedType() == VoFeedList.TYPE_STORAGE){
                str_commt = " 정보를 보관함에 담았습니다. ";
                //viewHolder.tv_feed_content.setText(info.getFeed_nickname() + " 님이 ");
            }

            String commt = info.getFeedUserId() + "님이 " + str_place + str_commt + info.getDate();

            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(commt);
            spannableStringBuilder.setSpan(new StyleSpan(Typeface.BOLD), 0, info.getFeedUserId().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableStringBuilder.setSpan(new ForegroundColorSpan(ResourcesCompat.getColor(getResources(), R.color.color_txt_90, null)),
                    commt.length() - info.getDate().length(), commt.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableStringBuilder.setSpan(new AbsoluteSizeSpan((int)getResources().getDimension(R.dimen.dimen_13sp)),
                    commt.length() - info.getDate().length(), commt.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            viewHolder.tv_feed_content.append(spannableStringBuilder);

            //viewHolder.tv_feed_date.setText(info.getFeed_date());

            if(isPlace) {
                viewHolder.iv_place.setVisibility(View.VISIBLE);
                viewHolder.iv_origin_thumb.setVisibility(View.GONE);

                if(info.getBoardType() == Constant.PLACE_TYPES.PLACE_TYPE_CAFE) {
                    viewHolder.iv_place.setBackgroundResource(R.drawable.icon_type_cafe);
                }else if(info.getBoardType() == Constant.PLACE_TYPES.PLACE_TYPE_HOSPITAL) {
                    viewHolder.iv_place.setBackgroundResource(R.drawable.icon_type_hospital);
                }else{
                    viewHolder.iv_place.setBackgroundResource(R.drawable.icon_type_gowalk);
                }
            }else{
                viewHolder.iv_origin_thumb.setVisibility(View.VISIBLE);
                viewHolder.iv_place.setVisibility(View.GONE);

                Glide.with(context)
                        .load(info.getRegThunmbPath())
                        .crossFade()
                        .bitmapTransform(new RoundedCornersTransformation(new CustomBitmapPool(), 10, 5))
                        .into(viewHolder.iv_origin_thumb);
            }

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

            if (feedList.get(position).getFeedType() == VoFeedList.TYPE_COMMENT)
                return VoFeedList.TYPE_COMMENT;
            else
                return VoFeedList.TYPE_LIKE;

        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public ImageView iv_feed_thumb;
            public TextView tv_feed_content;
            public ImageView iv_origin_thumb;
            public ImageView iv_place;
            //public TextView tv_feed_date;

            public ViewHolder(View view) {
                super(view);
                iv_feed_thumb = (ImageView) view.findViewById(R.id.iv_feed_thumb);
                tv_feed_content = (TextView) view.findViewById(R.id.tv_feed_content);
                iv_origin_thumb = (ImageView) view.findViewById(R.id.iv_origin_thumb);
                iv_place = (ImageView) view.findViewById(R.id.iv_place);
                //tv_feed_date = (TextView) view.findViewById(R.id.tv_feed_date);
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
