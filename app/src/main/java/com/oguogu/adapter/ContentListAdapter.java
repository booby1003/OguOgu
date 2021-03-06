package com.oguogu.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.oguogu.R;
import com.oguogu.activity.PlaceDetailActivity;
import com.oguogu.activity.StoryDetailActivity;
import com.oguogu.custom.CustomBitmapPool;
import com.oguogu.util.LogUtil;
import com.oguogu.vo.VoHomeList;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Administrator on 2016-12-23.
 */

public class ContentListAdapter extends RecyclerView.Adapter<ContentListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<VoHomeList.VoHomeInfo> mItems;

    private int lastPosition = -1;

    public ContentListAdapter(ArrayList<VoHomeList.VoHomeInfo> items, Context mContext) {
        mItems = items;
        context = mContext;
    }

    // 필수로 Generate 되어야 하는 메소드 1 : 새로운 뷰 생성
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        if (viewType == VoHomeList.BOARD_TYPE_STORY) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_story_item, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_place_item, parent, false);
        }

        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    // 필수로 Generate 되어야 하는 메소드 2 : ListView의 getView 부분을 담당하는 메소드
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        VoHomeList.VoHomeInfo info = mItems.get(position);

        if (info.getBoardType() == VoHomeList.BOARD_TYPE_STORY) {
            holder.tv_title.setText(info.getTitle());
            holder.tv_userid.setText(info.getRegUserID());
            holder.tv_reg_date.setText(info.getRegDate());
            Glide.with(context).load(info.getRegThunmbPath()).bitmapTransform(new CropCircleTransformation(new CustomBitmapPool())).into(holder.iv_reg_img);

            holder.iv_reg_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "유저 정보로 이동!!", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            holder.tv_title.setText(info.getTitle());
//            holder.tv_store_name.setText(info.getStoreName());
            holder.tv_store_addr.setText(info.getAddr());

            int storeTypeResId=0;
            if (info.getBoardType() == VoHomeList.PLACE_TYPE_RESTAURANT)
                storeTypeResId = R.drawable.icon_type_cafe_s;
            else if (info.getBoardType() == VoHomeList.PLACE_TYPE_HOSPITAL)
                storeTypeResId = R.drawable.icon_type_hospital_s;
            else if (info.getBoardType() == VoHomeList.PLACE_TYPE_PLAYGROUND)
                storeTypeResId = R.drawable.icon_type_gowalk_s;

            Glide.with(context)
                    .load(storeTypeResId)
                    .into(holder.iv_store);

            holder.layout_store.setTag(info);
            holder.layout_store.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    VoHomeList.VoHomeInfo tagInfo =  (VoHomeList.VoHomeInfo)v.getTag();

                    Intent intent = new Intent(context, PlaceDetailActivity.class);
                    intent.putExtra(PlaceDetailActivity.PLACE_IDX, tagInfo.getBoard_idx());
                    context.startActivity(intent);
                    ((Activity)context).overridePendingTransition(R.anim.slide_right_to_left, R.anim.slide_center_to_left);
                }
            });
        }

        holder.btn_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "좋아요!!", Toast.LENGTH_SHORT).show();
            }
        });

        LogUtil.i("imagePath: " + info.getImgPath());

        Glide.with(context)
                .load(info.getImgPath())
                .crossFade()
//                .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                .into(holder.imageView);

//            Random rnd = new Random();
//            int nRandom = rnd.nextInt(resIds.length);
//            holder.imageView.setImageResource(resIds[nRandom]);


        holder.cardView.setTag(info);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VoHomeList.VoHomeInfo tagInfo =  (VoHomeList.VoHomeInfo)v.getTag();

                Intent intent = new Intent(context, StoryDetailActivity.class);
                intent.putExtra(PlaceDetailActivity.PLACE_IDX, tagInfo.getBoard_idx());
                context.startActivity(intent);
                ((Activity)context).overridePendingTransition(R.anim.slide_right_to_left, R.anim.slide_center_to_left);
            }
        });


//            holder.button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(context, "버튼 클릭!!", Toast.LENGTH_SHORT).show();
//                }
//            });

//            setAnimation(holder.imageView, position);
    }

    public void setItems(ArrayList<VoHomeList.VoHomeInfo> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    public void setItem(ArrayList<VoHomeList.VoHomeInfo> items, int itemType) {
        mItems = new ArrayList<VoHomeList.VoHomeInfo>();

        for (VoHomeList.VoHomeInfo item : items) {
            if (item.getBoardType() == itemType)
                mItems.add(item);
        }

        notifyDataSetChanged();
    }

    // // 필수로 Generate 되어야 하는 메소드 3
    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public CardView cardView;
        public ImageView imageView;
        public TextView tv_title;
        public ImageButton btn_like;
        public LinearLayout layout_store;
        public ImageView iv_store;
        public TextView tv_store_name;
        public TextView tv_store_addr;

        public ImageView iv_reg_img;
        public TextView tv_userid;
        public TextView tv_reg_date;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.card_view);
            imageView = (ImageView) view.findViewById(R.id.image);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            btn_like = (ImageButton) view.findViewById(R.id.btn_like);
            layout_store = (LinearLayout) view.findViewById(R.id.layout_store);
            iv_store = (ImageView) view.findViewById(R.id.iv_store);
            tv_store_name = (TextView) view.findViewById(R.id.tv_store_name);
            tv_store_addr = (TextView) view.findViewById(R.id.tv_store_addr);

            iv_reg_img = (ImageView) view.findViewById(R.id.iv_reg_img);
            tv_userid = (TextView) view.findViewById(R.id.tv_userid);
            tv_reg_date = (TextView) view.findViewById(R.id.tv_reg_date);
        }
    }

    //        private void setAnimation(View viewToAnimate, int position) {
//            // 새로 보여지는 뷰라면 애니메이션을 해줍니다
//            if (position > lastPosition) {
//                Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_center_to_left);
//                viewToAnimate.startAnimation(animation);
//                lastPosition = position;
//            }
//        }
//    @Override
//    public int getItemViewType(int position) {
//        if (mItems.get(position).getBoardType() == VoHomeList.TYPE_STORY)
//            return VoHomeList.TYPE_STORY;
//        else if (mItems.get(position).getBoardType() == VoHomeList.TYPE_CAFE)
//            return VoHomeList.TYPE_CAFE;
//        else if (mItems.get(position).getBoardType() == VoHomeList.TYPE_HOSPITAL)
//            return VoHomeList.TYPE_HOSPITAL;
//        else if (mItems.get(position).getBoardType() == VoHomeList.TYPE_PLAYGROUND)
//            return VoHomeList.TYPE_PLAYGROUND;
//
//        return VoHomeList.TYPE_STORY;
//    }

}