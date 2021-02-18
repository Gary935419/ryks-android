package com.inwhoop.qscx.qscxsj.base.adapter.base;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import com.inwhoop.qscx.qscxsj.base.adapter.ViewHolder;
import com.inwhoop.qscx.qscxsj.base.adapter.interfaces.OnItemChildClickListener;
import com.inwhoop.qscx.qscxsj.base.adapter.interfaces.OnMultiItemClickListeners;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Othershe
 * Time: 2016/9/9 16:21
 */
public abstract class MultiBaseAdapter<T> extends BaseAdapter<T> {
    private OnMultiItemClickListeners<T> mItemClickListener;

    private ArrayList<Integer> mItemChildIds = new ArrayList<>();
    private ArrayList<OnItemChildClickListener<T>> mItemChildListeners = new ArrayList<>();

    public MultiBaseAdapter(Context context, List<T> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    protected abstract void convert(ViewHolder holder, T data, int position, int viewType);

    protected abstract int getItemLayoutId(int viewType);

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (isCommonItemView(viewType)) {
            return ViewHolder.create(mContext, getItemLayoutId(viewType), parent);
        }
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (isNeedAnimation)
            runEnterAnimation(holder.itemView, position);
        int viewType = holder.getItemViewType();
        if (isCommonItemView(viewType)) {
            bindCommonItem(holder, position - getHeaderCount(), viewType);
        }
    }

    private void bindCommonItem(RecyclerView.ViewHolder holder, final int position, final int viewType) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        convert(viewHolder, getAllData().get(position), position, viewType);

        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(viewHolder, getAllData().get(position), position, viewType);
                }
            }
        });

        for (int i = 0; i < mItemChildIds.size(); i++) {
            final int tempI = i;
            if (viewHolder.getConvertView().findViewById(mItemChildIds.get(i)) != null) {
                viewHolder.getConvertView().findViewById(mItemChildIds.get(i)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mItemChildListeners.get(tempI).onItemChildClick(viewHolder, getAllData().get(position), position);
                    }
                });
            }
        }
    }

    public void setOnMultiItemClickListener(OnMultiItemClickListeners<T> itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public void setOnItemChildClickListener(int viewId, OnItemChildClickListener<T> itemChildClickListener) {
        mItemChildIds.add(viewId);
        mItemChildListeners.add(itemChildClickListener);
    }

    private boolean animationsLocked = false;
    private boolean delayEnterAnimation = true;
    private int lastAnimatedPosition = -1;

    private void runEnterAnimation(View view, int position) {
        if (animationsLocked) return;              //animationsLocked是布尔类型变量，一开始为false
        //确保仅屏幕一开始能够容纳显示的item项才开启动画
        if (position > lastAnimatedPosition) {//lastAnimatedPosition是int类型变量，默认-1，
            //这两行代码确保了recyclerview滚动式回收利用视图时不会出现不连续效果
            lastAnimatedPosition = position;
            view.setTranslationX(400);     //Item项一开始相对于原始位置下方500距离
            view.setAlpha(0.f);           //item项一开始完全透明
            //每个item项两个动画，从透明到不透明，从下方移动到原始位置
            view.animate()
                    .translationX(0).alpha(1.f)                                //设置最终效果为完全不透明
                    //并且在原来的位置
                    .setStartDelay(delayEnterAnimation ? 20 * (position) : 0)//根据item的位置设置延迟时间
                    //达到依次动画一个接一个进行的效果
                    .setInterpolator(new DecelerateInterpolator(0.5f))     //设置动画位移先快后慢的效果
                    .setDuration(400)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            animationsLocked = true;
                            //确保仅屏幕一开始能够显示的item项才开启动画
                            //也就是说屏幕下方还没有显示的item项滑动时是没有动画效果
                        }
                    })
                    .start();
        }
    }
}
