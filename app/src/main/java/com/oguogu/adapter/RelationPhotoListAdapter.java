package com.oguogu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.oguogu.R;
import com.oguogu.vo.VoPlaceDetail;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016-12-23.
 */

public class RelationPhotoListAdapter extends RecyclerView.Adapter<RelationPhotoListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<VoPlaceDetail.VoRelation> mItems;

    private int lastPosition = -1;

    public RelationPhotoListAdapter(ArrayList<VoPlaceDetail.VoRelation> items, Context mContext) {
        mItems = items;
        context = mContext;
    }

    // 필수로 Generate 되어야 하는 메소드 1 : 새로운 뷰 생성
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_relation_item, parent, false);

        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    // 필수로 Generate 되어야 하는 메소드 2 : ListView의 getView 부분을 담당하는 메소드
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        VoPlaceDetail.VoRelation info = mItems.get(position);

        Glide.with(context).load(info.getRelation_thumb_path()).crossFade().into(holder.iv_relation_thumb);
        holder.tv_relation_title.setText(info.getRelation_nickname());

        holder.iv_relation_thumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "해당 글로 이동", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setItems(ArrayList<VoPlaceDetail.VoRelation> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    // // 필수로 Generate 되어야 하는 메소드 3
    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView iv_relation_thumb;
        public TextView tv_relation_title;

        public ViewHolder(View view) {
            super(view);
            iv_relation_thumb = (ImageView) view.findViewById(R.id.iv_relation_thumb);
            tv_relation_title = (TextView) view.findViewById(R.id.tv_relation_title);
        }
    }
}