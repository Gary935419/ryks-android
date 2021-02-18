package com.inwhoop.qscx.qscxsj.base;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.utils.DialogUtils;

import java.util.List;


public abstract class BaseFragment extends Fragment {

    public static final String TAG = BaseFragment.class.getSimpleName();

    private View mRootView;

    private Dialog cmd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(this.getLayoutId(), container, false);
        initView(mRootView);
        initListener();
        initAdapter();
        initData();
        return mRootView;
    }

    protected abstract int getLayoutId();

    protected abstract void initView(View view);

    protected abstract void initData();

    protected void initListener() {

    }

    protected void initAdapter() {

    }

    /**
     * 创建等待时的dialog
     */
    public void showLoadingDialog(String msg) {
        if (getActivity() == null || isDetached()) return;
        if (cmd != null && cmd.isShowing()) return;
        cmd = DialogUtils.showLoading(getActivity(), msg);
        cmd.setCancelable(false);
        DialogUtils.show(getActivity(), cmd);
    }

    /**
     * 创建等待时的dialog
     */
    public void showLoadingDialog(String msg, boolean canCancel) {
        if (getActivity() == null || isDetached()) return;
        if (cmd != null && cmd.isShowing()) return;
        cmd = DialogUtils.showLoading(getActivity(), msg);
        cmd.setCancelable(canCancel);
        DialogUtils.show(getActivity(), cmd);
    }

    /**
     * 消去等待时的dialog
     */
    public void dismissLoadingDialog() {
        DialogUtils.dismiss(cmd);
    }

    protected boolean isListNull(List<?> list) {
        return list == null;
    }

    protected boolean isListNullOrEmpty(List<?> list) {
        return list == null || list.size() == 0;
    }

    protected boolean isListLoadEnd(List<?> list) {
        return list == null || list.size() < 10;
    }
}
