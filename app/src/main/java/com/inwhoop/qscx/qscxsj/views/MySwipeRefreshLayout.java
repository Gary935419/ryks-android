package com.inwhoop.qscx.qscxsj.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import com.inwhoop.qscx.qscxsj.R;

/**
 * 刷新控件
 */
public class MySwipeRefreshLayout extends SwipeRefreshLayout {

    private int mTouchSlop;
    private float mPrevX;

    public MySwipeRefreshLayout(Context context) {
        super(context);
        init(context);
    }

    public MySwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        setColorSchemeResources(R.color.theme, R.color.theme, R.color.theme, R.color.theme);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    /**
     * 开始刷新
     */
    public void startRefresh(SwipeRefreshLayout.OnRefreshListener listener) {
        setRefreshing(true);
        listener.onRefresh();
    }

    /**
     * 停止刷新
     */
    public void stopRefresh() {
        setRefreshing(false);
    }

    @SuppressLint("Recycle")
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPrevX = MotionEvent.obtain(event).getX();
                break;
            case MotionEvent.ACTION_MOVE:
                final float eventX = event.getX();
                float xDiff = Math.abs(eventX - mPrevX);
                if (xDiff > mTouchSlop) {
                    return false;
                }
        }
        return super.onInterceptTouchEvent(event);
    }
}
