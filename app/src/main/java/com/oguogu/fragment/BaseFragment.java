package com.oguogu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oguogu.dialog.LoadingDialog;

/**
 * Created by Administrator on 2016-12-02.
 */

public abstract class BaseFragment extends Fragment {

    public boolean isLoading = false;
    public boolean isLastPage = false;
    public int PAGE_SIZE = 20;

    public abstract void refresh();
    public abstract void willBeHidden();
    public abstract void willBeDisplayed();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private LoadingDialog mLoadingDialog = null;

    public void showDialog() {
        if(mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(getContext());
            mLoadingDialog.setCancelable(false);
            mLoadingDialog.show();
        }else{
            mLoadingDialog.show();
        }
    }

    public void hideDialog() {
        if(mLoadingDialog == null) return;
        if (mLoadingDialog.isShowing()) mLoadingDialog.dismiss();
    }
}
