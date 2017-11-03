package com.oguogu.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.oguogu.R;
import com.oguogu.activity.PlaceDetailActivity;
import com.oguogu.activity.StoryDetailActivity;
import com.oguogu.util.UIUtil;
import com.oguogu.vo.VoHomeList;
import com.oguogu.vo.VoPhoto;

import java.util.ArrayList;

/**
 * Created by 김민정 on 2017-10-10.
 */

public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<VoPhoto> mItems;

    private int itemWidth = -1;

    public PhotoListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_photo_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //레이아웃
        if(itemWidth == -1) itemWidth = (UIUtil.getScreenWidthPixcels(context) / 3) - 20;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, itemWidth);
        if((position + 1) % 3 != 0) params.rightMargin = 10;
        params.bottomMargin = 10;
        holder.items.setLayoutParams(params);

        VoPhoto info = mItems.get(position);

        int storeTypeResId = 0;
        if (info.getBoardType() == VoHomeList.TYPE_CAFE)
            storeTypeResId = R.drawable.icon_type_cafe_s;
        else if (info.getBoardType() == VoHomeList.TYPE_HOSPITAL)
            storeTypeResId = R.drawable.icon_type_hospital_s;
        else if (info.getBoardType() == VoHomeList.TYPE_PLAYGROUND)
            storeTypeResId = R.drawable.icon_type_gowalk_s;
        else storeTypeResId = 0;

        if(storeTypeResId == 0)
            holder.iv_type.setVisibility(View.GONE);
        else {
            holder.iv_type.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(storeTypeResId)
                    .into(holder.iv_type);
        }

        Glide.with(context).load(info.getImgThumbPath()).into(holder.iv_img);

        holder.items.setTag(info);
        holder.items.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                VoPhoto info = (VoPhoto) v.getTag();
                if(info.getPlaceIdx() == null) {
                    //보드로 이동
                    context.startActivity(new Intent(context, StoryDetailActivity.class));
                }
                else{
                    //장소로 이동
                    context.startActivity(new Intent(context, PlaceDetailActivity.class));
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setItems(ArrayList<VoPhoto> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    public void addItems(ArrayList<VoPhoto> items) {
        mItems.addAll(items);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View items;
        public ImageView iv_img;
        public ImageView iv_type;

        public ViewHolder(View view) {
            super(view);
            items = view.findViewById(R.id.items);
            iv_img = (ImageView) view.findViewById(R.id.iv_img);
            iv_type = (ImageView) view.findViewById(R.id.iv_type);
        }
    }
}
