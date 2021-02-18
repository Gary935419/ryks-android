package com.inwhoop.qscx.qscxsj.base.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.inwhoop.qscx.qscxsj.utils.PicUtil;

/**
 * Author: Othershe
 * Time: 2016/8/29 09:49
 */
public class ViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;
    private View mConvertView;
    private Context mContext;

    private boolean isLayoutFinish;       //自定义字段,用于标记layout

    /**
     * 私有构造方法
     *
     * @param itemView
     */
    private ViewHolder(Context context, View itemView) {
        super(itemView);
        this.mContext = context;
        mConvertView = itemView;
        mViews = new SparseArray<>();
    }

    public static ViewHolder create(Context context, int layoutId, ViewGroup parent) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new ViewHolder(context, itemView);
    }

    public static ViewHolder create(Context context, View itemView) {
        return new ViewHolder(context, itemView);
    }

    /**
     * 通过id获得控件
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }

    public View getSwipeView() {
        ViewGroup itemLayout = ((ViewGroup) mConvertView);
        if (itemLayout.getChildCount() == 2) {
            return itemLayout.getChildAt(1);
        }
        return null;
    }

    public boolean isLayoutFinish() {
        return isLayoutFinish;
    }

    public void setLayoutFinish(boolean layoutFinish) {
        isLayoutFinish = layoutFinish;
    }

    /**
     * 设置TextView的值
     *
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    /**
     * 设置TextView的值
     *
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setText(int viewId, Spannable text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public ViewHolder setImageResource(int viewId, int resId) {
        ImageView view = getView(viewId);
        view.setImageResource(resId);
        return this;
    }

    public ViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    public ViewHolder setImageThumb(int viewId, String url) {
        ImageView view = getView(viewId);
        PicUtil.displayImage(url, view);
        return this;
    }

    public ViewHolder setBackgroundColor(int viewId, int color) {
        View view = getView(viewId);
        view.setBackgroundColor(mContext.getResources().getColor(color));
        return this;
    }

    public ViewHolder setBackgroundRes(int viewId, int backgroundRes) {
        View view = getView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    public ViewHolder setTextColor(int viewId, int textColor) {
        TextView view = getView(viewId);
        view.setTextColor(mContext.getResources().getColor(textColor));
        return this;
    }

    @SuppressLint("NewApi")
    public ViewHolder setAlpha(int viewId, float value) {
        getView(viewId).setAlpha(value);
        return this;
    }

    public ViewHolder setVisible(int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    public ViewHolder setVisible(int viewId, int visible) {
        View view = getView(viewId);
        view.setVisibility(visible);
        return this;
    }

    public ViewHolder linkify(int viewId) {
        TextView view = getView(viewId);
        Linkify.addLinks(view, Linkify.ALL);
        return this;
    }

    public ViewHolder setTypeface(Typeface typeface, int... viewIds) {
        for (int viewId : viewIds) {
            TextView view = getView(viewId);
            view.setTypeface(typeface);
            view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
        return this;
    }

    public ViewHolder setProgress(int viewId, int progress) {
        ProgressBar view = getView(viewId);
        view.setProgress(progress);
        return this;
    }

    public ViewHolder setProgress(int viewId, int progress, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        view.setProgress(progress);
        return this;
    }

    public ViewHolder setMax(int viewId, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        return this;
    }

    public ViewHolder setRating(int viewId, float rating) {
        RatingBar view = getView(viewId);
        view.setRating(rating);
        return this;
    }

    public ViewHolder setRating(int viewId, float rating, int max) {
        RatingBar view = getView(viewId);
        view.setMax(max);
        view.setRating(rating);
        return this;
    }

    public ViewHolder setTag(int viewId, Object tag) {
        View view = getView(viewId);
        view.setTag(tag);
        return this;
    }

    public ViewHolder setTag(int viewId, int key, Object tag) {
        View view = getView(viewId);
        view.setTag(key, tag);
        return this;
    }

    public ViewHolder setChecked(int viewId, boolean checked) {
        Checkable view = (Checkable) getView(viewId);
        view.setChecked(checked);
        return this;
    }

    public ViewHolder setChecked(int viewId, int checkId) {
        RadioGroup view = (RadioGroup) getView(viewId);
        view.check(checkId);
        return this;
    }

    /**
     * 关于事件的
     */
    public ViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    /**
     * 关于事件的
     */
    public ViewHolder setOnCheckedListener(int viewId, CompoundButton.OnCheckedChangeListener listener) {
        View view = getView(viewId);
        ((CheckBox) view).setOnCheckedChangeListener(listener);
        return this;
    }

    /**
     * 关于事件的
     */
    public ViewHolder setOnCheckedListener(int viewId, RadioGroup.OnCheckedChangeListener listener) {
        View view = getView(viewId);
        ((RadioGroup) view).setOnCheckedChangeListener(listener);
        return this;
    }

    public ViewHolder setOnTouchListener(int viewId, View.OnTouchListener listener) {
        View view = getView(viewId);
        view.setOnTouchListener(listener);
        return this;
    }

    public ViewHolder setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
        View view = getView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }

    public ViewHolder setOnTextChangedListener(int viewId, TextWatcher watcher) {
        View view = getView(viewId);
        if (view instanceof EditText) {
            ((EditText) view).addTextChangedListener(watcher);
        }
        return this;
    }

    public ViewHolder setOnFocusChangeListener(int viewId, View.OnFocusChangeListener onFocusChangeListener) {
        View view = getView(viewId);
        view.setOnFocusChangeListener(onFocusChangeListener);
        return this;
    }

    public ViewHolder setSelected(int viewId, boolean isSelected) {
        View v = getView(viewId);
        if (v instanceof TextView)
            ((TextView) v).setSelected(isSelected);
        return this;
    }

    public ViewHolder setEnabled(int viewId, boolean enabled) {
        View v = getView(viewId);
        v.setEnabled(enabled);
        return this;
    }

    public ViewHolder setLine(int viewId) {
        TextView tv = getView(viewId);
        tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        return this;
    }

    public boolean isChecked(int viewId) {
        Checkable view = (Checkable) getView(viewId);
        return view.isChecked();
    }
}
