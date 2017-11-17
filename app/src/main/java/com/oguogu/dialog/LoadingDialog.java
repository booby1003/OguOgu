package com.oguogu.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.view.Window;
import android.view.WindowManager;

import com.oguogu.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 김민정 on 2017-11-16.
 */

public class LoadingDialog extends Dialog {

    private Context mContext;

    public LoadingDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}
