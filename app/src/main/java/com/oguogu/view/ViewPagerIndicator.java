package com.oguogu.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.oguogu.R;

import java.util.jar.Attributes;

/**
 * Created by booby on 2018-03-08.
 */

public class ViewPagerIndicator extends LinearLayout {

    private Context mContext;
    private int itemMargin;
    //private int animDuration;
    private int selectedIdx;

    private ImageView[] imageIndicator;

    public ViewPagerIndicator(Context context) {
        super(context);
        this.mContext = context;
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

//    public void setAnimDuration(int duration) {
//        this.animDuration = duration;
//    }

    public void setItemMargin(int margin) {
        this.itemMargin = margin;
    }

    public void createIndicator(int totalCnt) {

        imageIndicator = new ImageView[totalCnt];

        for(int i = 0; i < totalCnt; i++) {
            imageIndicator[i] = new ImageView(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = itemMargin;
            params.rightMargin = itemMargin;
            params.gravity = Gravity.CENTER;

            imageIndicator[i].setLayoutParams(params);
            imageIndicator[i].setImageResource(R.drawable.indicator);
            imageIndicator[i].setTag(imageIndicator[i].getId(), false);
            this.addView(imageIndicator[i]);
        }

        this.selectedIdx = 0;
        selectedIndicator(selectedIdx);
    }

    public void selectedIndicator(int pos) {

        imageIndicator[selectedIdx].setImageResource(R.drawable.indicator);
        //defaultScaleAnim(imageIndicator[selectedIdx], 1.5f, 1f);

        imageIndicator[pos].setImageResource(R.drawable.indicator_s);
        //selectScaleAnim(imageIndicator[pos],1f,1.5f);

        this.selectedIdx = pos;
    }



//    /**
//     * 선택되지 않은 점의 애니메이션
//     * @param view
//     * @param startScale
//     * @param endScale
//     */
//    public void defaultScaleAnim(View view, float startScale, float endScale) {
//        Animation anim = new ScaleAnimation(
//                startScale, endScale,
//                startScale, endScale,
//                Animation.RELATIVE_TO_SELF, 0.5f,
//                Animation.RELATIVE_TO_SELF, 0.5f);
//        anim.setFillAfter(true);
//        anim.setDuration(animDuration);
//        view.startAnimation(anim);
//        view.setTag(view.getId(),false);
//    }
//
//    /**
//     * 선택된 점의 애니메이션
//     * @param view
//     * @param startScale
//     * @param endScale
//     */
//    public void selectScaleAnim(View view, float startScale, float endScale) {
//        Animation anim = new ScaleAnimation(
//                startScale, endScale,
//                startScale, endScale,
//                Animation.RELATIVE_TO_SELF, 0.5f,
//                Animation.RELATIVE_TO_SELF, 0.5f);
//        anim.setFillAfter(true);
//        anim.setDuration(animDuration);
//        view.startAnimation(anim);
//        view.setTag(view.getId(),true);
//    }


}
