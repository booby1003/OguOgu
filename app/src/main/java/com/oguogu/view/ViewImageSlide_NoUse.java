package com.oguogu.view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.oguogu.R;
import com.oguogu.util.LogUtil;

import java.util.ArrayList;

/**
 * Created by booby on 2018-03-14.
 */

public class ViewImageSlide_NoUse extends RelativeLayout {

    private Context mContext;

    ViewPager viewPager;
    ViewPagerIndicator indicator;

    public ViewImageSlide_NoUse(Context context, AttributeSet attributeSet) {
        super(context);
        this.mContext = context;
        initLayout();
    }

    public void initLayout() {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view = inflater.inflate(R.layout.item_slider_img, null);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        indicator = (ViewPagerIndicator) findViewById(R.id.viewPager);

        LogUtil.i("initLayout");

        this.addView(view);
    }

    public void setHight(int hight) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, hight);
        viewPager.setLayoutParams(params);
    }


    public void createImageSlide(ArrayList<String> imgList, int indicatorMargin){
        //ViewPagerImgAdapter viewPagerImgAdapter = new ViewPagerImgAdapter(mContext, imgList);
        //viewPager.setAdapter(viewPagerImgAdapter);
//        viewPager.addOnPageChangeListener(onPageChangeListener);
//        indicator.setItemMargin(indicatorMargin);
//        indicator.createIndicator(imgList.size());
    }


    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            indicator.selectedIndicator(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public class ViewPagerImgAdapter extends PagerAdapter {

        private Context context;
        private ArrayList<String> items;

        public ViewPagerImgAdapter(Context context, ArrayList<String> items) {
            this.context = context;
            this.items = items;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((ImageView) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            String imgPath = items.get(position);

            Glide.with(context)
                    .load(imgPath)
                    .crossFade()
                    .into(imageView);

            ((ViewPager) container).addView(imageView, 0);

            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((ImageView) object);
        }

    }
}
