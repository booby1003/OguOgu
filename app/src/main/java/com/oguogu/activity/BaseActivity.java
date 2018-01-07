package com.oguogu.activity;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.oguogu.R;
import com.oguogu.dialog.CommonDialog;
import com.oguogu.dialog.LoadingDialog;
import com.oguogu.util.LogUtil;

/**
 * Created by 김민정 on 2017-11-17.
 */

public class BaseActivity extends AppCompatActivity {

    private LoadingDialog mLoadingDialog = null;

    /**
     * 로딩바 show
     */
    public void showDialog() {
        if(mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this);
            mLoadingDialog.setCancelable(false);
            mLoadingDialog.show();
        }else{
            mLoadingDialog.show();
        }
    }

    /**
     * 로딩바 hide
     */
    public void hideDialog() {
        if(mLoadingDialog == null) return;
        if (mLoadingDialog.isShowing()) mLoadingDialog.dismiss();
    }

    private CommonDialog networkDialog = null;
    /**
     * 네트워크 오류
     */
    protected void showNetworkError() {
        hideDialog();

        if(networkDialog == null) {

            networkDialog = new CommonDialog.Builder(this)
                    .setTitle(getResources().getString(R.string.alt_network_error))
                    .setButtonText("아니오", "예")
                    .setListener(new CommonDialog.DialogButtonClick() {
                        @Override
                        public void onClick(Dialog dialog, int pos) {
                            if(pos == CommonDialog.DialogButtonClick.CANCEL_BUTTON) {
                                dialog.dismiss();
                            }else if(pos == CommonDialog.DialogButtonClick.CONFIRM_BUTTON) {
                                gotoSettingNetwork();
                            }
                        }
                    }).build();
        }
        if(!networkDialog.isShowing()) networkDialog.show();
    }

    /**
     * Go to Setting
     */
    private void gotoSettingNetwork() {

        final Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        final ComponentName cn = new ComponentName("com.android.settings","com.android.settings.wifi.WifiSettings");
        intent.setComponent(cn);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
