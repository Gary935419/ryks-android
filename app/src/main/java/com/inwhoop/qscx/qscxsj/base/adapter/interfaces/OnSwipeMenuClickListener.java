package com.inwhoop.qscx.qscxsj.base.adapter.interfaces;

import com.inwhoop.qscx.qscxsj.base.adapter.ViewHolder;

/**
 * Author: Othershe
 * Time: 2016/8/29 10:48
 */
public interface OnSwipeMenuClickListener<T> {
    void onSwipMenuClick(ViewHolder viewHolder, T data, int position);
}
