package com.oguogu.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oguogu.R;
import com.oguogu.activity.WritePlaceDetailActivity;
import com.oguogu.util.LogUtil;
import com.oguogu.vo.VoPlaceList;

import java.util.ArrayList;

/**
 * Created by 김민정 on 2017-09-28.
 */

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> {

    private Context context;
    private ArrayList<VoPlaceList.VoPlace> mItems = new ArrayList<>();

    public PlaceAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_place_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        VoPlaceList.VoPlace info = mItems.get(position);

        holder.tv_place.setText(info.getStoreName());
        holder.tv_place_type.setText(info.getPlaceTypeStr());
        holder.tv_addr.setText(info.getAddr());

        holder.ll_place.setTag(info);
        holder.ll_place.setOnClickListener(placeClickListener);

        LogUtil.i("position :: " + position);
    }

    public void setItems(ArrayList<VoPlaceList.VoPlace> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    public void removeItems() {
        mItems.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    View.OnClickListener placeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            VoPlaceList.VoPlace info = (VoPlaceList.VoPlace) v.getTag();

            Intent intent = new Intent(context, WritePlaceDetailActivity.class);
            intent.putExtra(WritePlaceDetailActivity.PLACE_IDX, info.getPlace_idx());
            context.startActivity(intent);
            ((Activity)context).overridePendingTransition(R.anim.slide_right_to_left, R.anim.slide_center_to_left);
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout ll_place;
        public TextView tv_place;
        public TextView tv_place_type;
        public TextView tv_addr;

        public ViewHolder(View view) {
            super(view);
            ll_place = (LinearLayout) view.findViewById(R.id.ll_place);
            tv_place = (TextView) view.findViewById(R.id.tv_place);
            tv_place_type = (TextView) view.findViewById(R.id.tv_place_type);
            tv_addr = (TextView) view.findViewById(R.id.tv_addr);
        }
    }
}
