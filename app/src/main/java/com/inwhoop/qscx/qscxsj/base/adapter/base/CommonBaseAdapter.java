package com.inwhoop.qscx.qscxsj.base.adapter.base;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.base.adapter.BaseListInfo;
import com.inwhoop.qscx.qscxsj.base.adapter.ViewHolder;
import com.inwhoop.qscx.qscxsj.base.adapter.interfaces.OnItemChildClickListener;
import com.inwhoop.qscx.qscxsj.base.adapter.interfaces.OnItemClickListener;
import com.inwhoop.qscx.qscxsj.base.adapter.interfaces.OnItemLongClickListener;
import com.inwhoop.qscx.qscxsj.base.adapter.interfaces.OnSwipeMenuClickListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Othershe
 * Time: 2016/9/9 15:52
 */
public abstract class CommonBaseAdapter<T> extends BaseAdapter<T> {

    protected OnItemClickListener<T> mItemClickListener;
    private OnItemLongClickListener<T> onItemLongClickListener;

    private ArrayList<Integer> mViewId = new ArrayList<>();
    private ArrayList<OnSwipeMenuClickListener<T>> mListener = new ArrayList<>();

    private ArrayList<Integer> mItemChildIds = new ArrayList<>();
    private ArrayList<OnItemChildClickListener<T>> mItemChildListeners = new ArrayList<>();

    protected ArrayList<View> mMoveViewList = new ArrayList<>();
    protected int mFixX;

    public CommonBaseAdapter(Context context, List<T> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    protected abstract void convert(ViewHolder holder, T data, int position, String payload);

    protected abstract int getItemLayoutId();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        if (isCommonItemView(viewType)) {
            return ViewHolder.create(mContext, getItemLayoutId(), parent);
        }
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (isNeedAnimation)
            runEnterAnimation(holder.itemView, position);
        int viewType = holder.getItemViewType();
        if (isCommonItemView(viewType)) {
            bindCommonItem(holder, position - getHeaderCount(), "");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (isNeedAnimation)
            runEnterAnimation(holder.itemView, position);
        int viewType = holder.getItemViewType();
        if (isCommonItemView(viewType)) {
            bindCommonItem(holder, position - getHeaderCount(), payloads.size() > 0 ? payloads.get(0).toString() : "");
        }
    }

    private void bindCommonItem(RecyclerView.ViewHolder holder, final int position, String payload) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        convert(viewHolder, getAllData().get(position), position, payload);
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(viewHolder, getAllData().get(position), position);
                }
            }
        });
        viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemLongClickListener != null)
                    return onItemLongClickListener.onItemLongClick(viewHolder, getAllData().get(position), position);
                return false;
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

        if (mViewId.size() > 0 && mListener.size() > 0 && viewHolder.getSwipeView() != null) {
            ViewGroup swipeView = (ViewGroup) viewHolder.getSwipeView();

            for (int i = 0; i < mViewId.size(); i++) {
                final int tempI = i;
                swipeView.findViewById(mViewId.get(i)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.get(tempI).onSwipMenuClick(viewHolder, getAllData().get(position), position);
                    }
                });
            }
        }
    }

    @Override
    protected int getViewType(int position, T data) {
        return TYPE_COMMON_VIEW;
    }

    public void setOnItemClickListener(OnItemClickListener<T> itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<T> onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public void setOnSwipMenuClickListener(int viewId, OnSwipeMenuClickListener<T> swipeMenuClickListener) {
        mViewId.add(viewId);
        mListener.add(swipeMenuClickListener);
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
            view.animate().translationX(0).alpha(1.f)                                //设置最终效果为完全不透明
                    //并且在原来的位置
                    .setStartDelay(delayEnterAnimation ? 100 * (position) : 0)//根据item的位置设置延迟时间
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

    public ArrayList<View> getMoveViewList() {
        return mMoveViewList;
    }

    public void setFixX(int fixX) {
        mFixX = fixX;
    }

    /**
     * 设置选中项
     *
     * @param position 位置
     */
    public boolean setSelectItem(int position) {
        for (int i = 0; i < mDatas.size(); i++) {
            BaseListInfo listInfo = (BaseListInfo) mDatas.get(i);
            if (listInfo.isSelect()) {
                listInfo.setSelect(false);
                notifyDataSetChanged();
                if (i == position)
                    return false;
            }
        }
        BaseListInfo listInfo = (BaseListInfo) mDatas.get(position);
        listInfo.setSelect(true);
        notifyDataSetChanged();
        return true;
    }

    /**
     * 清空所有选中状态
     */
    public void clearSelect() {
        for (int i = 0; i < mDatas.size(); i++) {
            ((BaseListInfo) mDatas.get(i)).setSelect(false);
        }
        notifyDataSetChanged();
    }

    public void setAnimationOn() {
        isNeedAnimation = true;
    }

    public void setBgChangeOn() {
        isNeedBgChange = true;
    }
}
