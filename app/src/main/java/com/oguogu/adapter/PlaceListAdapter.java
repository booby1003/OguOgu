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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.oguogu.R;
import com.oguogu.activity.PlaceDetailActivity;
import com.oguogu.util.LogUtil;
import com.oguogu.vo.VoHomeList;
import com.oguogu.vo.VoPlaceList;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016-12-23.
 */

public class PlaceListAdapter extends RecyclerView.Adapter<PlaceListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<VoPlaceList.VoPlace> mItems;

    private int lastPosition = -1;

    public PlaceListAdapter(ArrayList<VoPlaceList.VoPlace> items, Context mContext) {
        mItems = items;
        context = mContext;
    }

    // 필수로 Generate 되어야 하는 메소드 1 : 새로운 뷰 생성
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_place_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    // 필수로 Generate 되어야 하는 메소드 2 : ListView의 getView 부분을 담당하는 메소드
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        VoPlaceList.VoPlace info = mItems.get(position);

        holder.tv_place_name.setText(info.getPlaceName());
        holder.tv_place_addr.setText(info.getPlaceAddr());

        int storeTypeResId=0;
        if (info.getBoardType() == VoHomeList.PLACE_TYPE_RESTAURANT)
            storeTypeResId = R.drawable.icon_type_cafe_s;
        else if (info.getBoardType() == VoHomeList.PLACE_TYPE_HOSPITAL)
            storeTypeResId = R.drawable.icon_type_hospital_s;
        else if (info.getBoardType() == VoHomeList.PLACE_TYPE_PLAYGROUND)
            storeTypeResId = R.drawable.icon_type_gowalk_s;

        Glide.with(context)
                .load(storeTypeResId)
                .into(holder.iv_place);

        //holder.tv_reg_date.setText(info.getRegDate());
        //Glide.with(context).load(info.getRegThunmbPath()).bitmapTransform(new CropCircleTransformation(new CustomBitmapPool())).into(holder.iv_reg_img);

//        holder.iv_reg_img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "유저 정보로 이동!!", Toast.LENGTH_SHORT).show();
//            }
//        });


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
                VoPlaceList.VoPlace tagInfo =  (VoPlaceList.VoPlace)v.getTag();

                Intent intent = new Intent(context, PlaceDetailActivity.class);
                intent.putExtra(PlaceDetailActivity.PLACE_IDX, tagInfo.getBoardIdx());
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

    public void setItems(ArrayList<VoPlaceList.VoPlace> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    public void setItem(ArrayList<VoPlaceList.VoPlace> items, int itemType) {
        mItems = new ArrayList<VoPlaceList.VoPlace>();

        for (VoPlaceList.VoPlace item : items) {
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
        public ImageView iv_place;
        public ImageButton btn_like;
        public TextView tv_place_name;
        public TextView tv_place_addr;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.card_view);
            imageView = (ImageView) view.findViewById(R.id.image);
            btn_like = (ImageButton) view.findViewById(R.id.btn_like);
            iv_place = (ImageView) view.findViewById(R.id.iv_place);
            tv_place_name = (TextView) view.findViewById(R.id.tv_place_name);
            tv_place_addr = (TextView) view.findViewById(R.id.tv_place_addr);
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