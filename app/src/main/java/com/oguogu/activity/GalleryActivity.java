package com.oguogu.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.oguogu.R;
import com.oguogu.cropper.CropImage;
import com.oguogu.cropper.CropImageView;
import com.oguogu.util.LogUtil;
import com.oguogu.util.StringUtil;
import com.oguogu.util.UIUtil;
import com.oguogu.view.GridSpacingItemDecoration;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import butterknife.ButterKnife;

/**
 * Created by 김민정 on 2017-11-06.
 */

public class GalleryActivity extends AppCompatActivity {

    public static final int PICK_FROM_CAMERA = 0;
    public static final int PICK_FROM_ALBUM = 1;
    public static final int CROP_FROM_IMAGE = 2;

    private ArrayList<String> thumbsDataList = new ArrayList<>();
    private RecyclerView galleryList;
    private GalleryAdapter mAdapter;
    private StaggeredGridLayoutManager mLayoutManager;

    public static final int CONTENT_PADDING = 90;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ButterKnife.bind(this);

        getStoreImage();

        mAdapter = new GalleryAdapter(this);
        mAdapter.addAllDataList(thumbsDataList);

        mLayoutManager = new StaggeredGridLayoutManager(3, 1);
        galleryList = (RecyclerView) findViewById(R.id.gallery_list);
        galleryList.setLayoutManager(mLayoutManager);
        galleryList.addItemDecoration(new GridSpacingItemDecoration(3, UIUtil.pixelToDp(CONTENT_PADDING, this)));
        galleryList.setAdapter(mAdapter);
    }


    Cursor imageCursor = null;
    private void getStoreImage() {

        String[] proj = {MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA,
                        MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.SIZE};

        imageCursor =managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, proj, null, null, null);

        if (imageCursor != null && imageCursor.moveToFirst()){
            String title;
            String thumbsID;
            String thumbsImageID;
            String thumbsData;
            String imgSize;

            int thumbsIDCol = imageCursor.getColumnIndex(MediaStore.Images.Media._ID);
            int thumbsDataCol = imageCursor.getColumnIndex(MediaStore.Images.Media.DATA);
            int thumbsImageIDCol = imageCursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME);
            int thumbsSizeCol = imageCursor.getColumnIndex(MediaStore.Images.Media.SIZE);
            int num = 0;
            do {
                thumbsID = imageCursor.getString(thumbsIDCol);
                thumbsData = imageCursor.getString(thumbsDataCol);
                thumbsImageID = imageCursor.getString(thumbsImageIDCol);
                imgSize = imageCursor.getString(thumbsSizeCol);
                num++;

                if (thumbsImageID != null){
                    //thumbsIDList.add(thumbsID);
                    thumbsDataList.add(thumbsData);
                }
            }while (imageCursor.moveToNext());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(!imageCursor.isClosed()) {
            imageCursor.close();
            LogUtil.i("imageCursor closed");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode !=  RESULT_OK) return;

        switch (requestCode) {
            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
            {
                LogUtil.i("Gallery PICK_FROM_ALBUM");
                //setResult(CROP_FROM_IMAGE, data);

//                Intent intent = new Intent();
//                intent.putExtra(CROP_FROM_IMAGE, data);
                setResult(RESULT_OK, data);

                break;
            }
//            case PICK_FROM_CAMERA:
//            {
//                LogUtil.i("Gallery PICK_FROM_CAMERA");
//                Uri carameUri = data.getData();
//
//                if (carameUri != null) {
//                    LogUtil.i("carameUri :: " + carameUri);
//                    CropImage.activity(carameUri)
//                            .setGuidelines(CropImageView.Guidelines.OFF)
//                            .setBorderLineColor(Color.WHITE)
//                            .setBorderLineThickness(5)
//                            .setCircleSize(40)
//                            .setCircleColor(Color.WHITE)
//                            .setBackgroundColor(Color.argb(170, 0, 0, 0))
//                            .start(this);
//                }
//                return;
//            }
        }
        finish();
    }

    /**
     * GalleryAdapter
     */
    public class GalleryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private Context context;
        private ArrayList<Object> mDataList = new ArrayList<>();

        public GalleryAdapter(Context context) {
            this.context = context;
        }

        public void addAllDataList(Collection<? extends Object> _list) {
            this.mDataList.addAll(_list);
        }

        @Override
        public int getItemCount() {
            return mDataList.size();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(context).inflate(R.layout.listview_item_gallery, parent, false);
            return new PhotoItemHolder(itemView);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            PhotoItemHolder listHolder = (PhotoItemHolder) holder;
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            float img_w = (metrics.widthPixels - (CONTENT_PADDING * 2)) / 3;

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (int) img_w);
            listHolder.iv_photo.setLayoutParams(params);

            final File file = new File(thumbsDataList.get(position));
            final Uri imageUri = Uri.fromFile(file);
            Glide.with(context).load(imageUri).into(listHolder.iv_photo);
            listHolder.iv_photo.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    /*Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imageUri, "image*//*");
                    intent.putExtra("scale", true);
                    intent.putExtra("return-data", true);
                    startActivityForResult(intent, PICK_FROM_CAMERA);*/

                    CropImage.activity(imageUri)
                            .setGuidelines(CropImageView.Guidelines.OFF)
                            .setBorderLineColor(Color.WHITE)
                            .setBorderLineThickness(5)
                            .setCircleSize(40)
                            .setCircleColor(Color.WHITE)
                            .setBackgroundColor(Color.argb(170, 0, 0, 0))
                            .start((Activity) context);
                }
            });

        }
    }

    public final static class PhotoItemHolder extends RecyclerView.ViewHolder {

        ImageView iv_photo;

        public PhotoItemHolder(View itemView) {
            super(itemView);
            iv_photo = (ImageView) itemView.findViewById(R.id.iv_photo);
        }
    }
}





















