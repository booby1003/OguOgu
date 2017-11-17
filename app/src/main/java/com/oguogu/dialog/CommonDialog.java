package com.oguogu.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.oguogu.R;

/**
 * Created by 김민정 on 2017-11-17.
 */

public class CommonDialog extends Dialog {

    private Context mContext;

    public CommonDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_common);
    }
}
