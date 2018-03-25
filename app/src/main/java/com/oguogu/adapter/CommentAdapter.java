package com.oguogu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.oguogu.R;
import com.oguogu.custom.CustomBitmapPool;
import com.oguogu.vo.VoCommentList;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by booby on 2018-03-25.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private Context context;
    private ArrayList<VoCommentList.VoComment> mItems;

    public CommentAdapter(ArrayList<VoCommentList.VoComment> items, Context context) {
        mItems = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        VoCommentList.VoComment info = mItems.get(position);

        holder.tv_comment_nickname.setText(info.getCommentUserId());
        holder.tv_comment_date.setText(info.getCommentDate());
        holder.tv_comment_content.setText(info.getComment());

        Glide.with(context).load(info.getCommentUserThumbPath()).bitmapTransform(new CropCircleTransformation(new CustomBitmapPool())).into(holder.iv_comment_img);

        holder.iv_comment_img.setOnClickListener(onClickListener);

        if(info.isMyComment()) {
            holder.btnDeleteComment.setVisibility(View.VISIBLE);
            holder.btnDeleteComment.setOnClickListener(onClickListener);
        }else{
            holder.btnDeleteComment.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    View.OnClickListener onClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.iv_comment_img:

                    break;
                case R.id.btnDeleteComment:

                    break;
                default:
                    break;
            }
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_comment_img;
        public TextView tv_comment_nickname;
        public TextView tv_comment_date;
        public TextView tv_comment_content;
        public ImageButton btnDeleteComment;

        public ViewHolder(View view) {
            super(view);
            iv_comment_img = (ImageView) view.findViewById(R.id.iv_comment_img);
            tv_comment_nickname = (TextView) view.findViewById(R.id.tv_comment_nickname);
            tv_comment_date = (TextView) view.findViewById(R.id.tv_comment_date);
            tv_comment_content = (TextView) view.findViewById(R.id.tv_comment_content);
            btnDeleteComment = (ImageButton) view.findViewById(R.id.btnDeleteComment);
        }
    }
}
