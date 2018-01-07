package com.oguogu.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oguogu.R;
import com.oguogu.util.UIUtil;
import com.oguogu.vo.VoPlaceCategory;

import java.util.ArrayList;

/**
 * Created by 김민정 on 2017-12-21.
 */

public class ListDialog extends Dialog {

    public static enum ListType {
        PlaceCate,      //장소카테고리
        PlaceSubCate    //장소카테고리 서브
    }

    private Context mContext;
    private LinearLayout ll_list;
    private ListType currentListType = ListType.PlaceCate;
    private View.OnClickListener onClickListener = null;

    public ListDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_list);
        setCancelable(true);
        setCanceledOnTouchOutside(false);

        ll_list = (LinearLayout) findViewById(R.id.ll_list);
        findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void setListType(ListType listType) {
        this.currentListType = listType;
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.onClickListener = listener;
    }

    public void setDataList(ArrayList<VoPlaceCategory> placeCateList) {
        ll_list.removeAllViews();

        for(VoPlaceCategory category : placeCateList) {

            View view = null;

            if(ListType.PlaceCate == currentListType) {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_place_category, null);
                view.findViewById(R.id.iv_cate).setVisibility(View.VISIBLE);
                ImageView imageView = (ImageView) view.findViewById(R.id.iv_cate);
                imageView.setImageResource(UIUtil.getBoardTypeDrawable(category.getPlaceType()));
                TextView tv_conts = (TextView) view.findViewById(R.id.tv_conts);
                tv_conts.setText(category.getPlaceName());
                view.setTag(category);
            }else if(ListType.PlaceSubCate == currentListType) {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_place_category, null);
                view.findViewById(R.id.iv_cate).setVisibility(View.GONE);
                TextView tv_conts = (TextView) view.findViewById(R.id.tv_conts);
                tv_conts.setText(category.getCateName());
                view.setTag(category);
            }
            view.setOnClickListener(onClickListener);
            ll_list.addView(view);
        }
    }

}
















