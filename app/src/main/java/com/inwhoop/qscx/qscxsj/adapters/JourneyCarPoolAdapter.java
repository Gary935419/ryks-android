package com.inwhoop.qscx.qscxsj.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.activitys.ordertaking.OrderDetailActivity;
import com.inwhoop.qscx.qscxsj.app.AsyncHttpHandler;
import com.inwhoop.qscx.qscxsj.business.DriverOrderService;
import com.inwhoop.qscx.qscxsj.constants.Constants;
import com.inwhoop.qscx.qscxsj.entitys.InterCityOrderBean;
import com.inwhoop.qscx.qscxsj.entitys.TownOrderBean;
import com.inwhoop.qscx.qscxsj.tools.SystemManager;
import com.inwhoop.qscx.qscxsj.utils.LoginUserInfoUtil;
import com.inwhoop.qscx.qscxsj.utils.PicUtil;
import com.inwhoop.qscx.qscxsj.views.CircleImageView;

import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;

import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Administrator on 2016/11/30.
 */

public class JourneyCarPoolAdapter extends BaseAdapter {

    private Context mContext;
    private List<InterCityOrderBean> mList;
    private LayoutInflater mInflater;
    private int taker_type_id;

    public JourneyCarPoolAdapter(Context context, List<InterCityOrderBean> list, int type) {
        this.mContext = context;
        mList = list;
        taker_type_id = type;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_travel_records_car_pool_list, null);
            vh = new ViewHolder();
            vh.layoutItem = convertView.findViewById(R.id.layoutItem);
            vh.civ_orderPicture = convertView.findViewById(R.id.civ_orderPicture);
            vh.iv_orderCondition = convertView.findViewById(R.id.iv_orderCondition);
            vh.tv_carCondition = convertView.findViewById(R.id.tv_order_time);
            vh.tv_people_num = convertView.findViewById(R.id.tv_people_num);
            vh.tv_cost = convertView.findViewById(R.id.tv_cost);
            vh.tv_destination = convertView.findViewById(R.id.tv_destination);
            vh.tv_driverName = convertView.findViewById(R.id.tv_driverName);
            vh.tv_location = convertView.findViewById(R.id.tv_location);
            vh.tv_orderTime = convertView.findViewById(R.id.tv_orderTime);
            vh.im_userTelephone = convertView.findViewById(R.id.im_userTelephone);
            vh.item_travel_records_car_pool_list_message_tv = convertView.findViewById(R.id.item_travel_records_car_pool_list_message_tv);
            vh.item_travel_records_car_pool_list_ok_btn = convertView.findViewById(R.id.item_travel_records_car_pool_list_ok_btn);
            vh.item_travel_records_car_pool_list_bottom_ll = convertView.findViewById(R.id.item_travel_records_car_pool_list_bottom_ll);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        //司机头像
        PicUtil.displayImage(mList.get(position).getHead_img(), vh.civ_orderPicture);
        vh.item_travel_records_car_pool_list_bottom_ll.setVisibility(View.GONE);
        vh.item_travel_records_car_pool_list_message_tv.setText("");
        vh.item_travel_records_car_pool_list_ok_btn.setBackgroundResource(R.drawable.button_selector);
        //订单状态
        if (taker_type_id == 1) {
            if (mList.get(position).getIs_appointment().equals("1")) {
                vh.tv_people_num.setText("预约:" + mList.get(position).getPeople_num() + "人");
            } else if (mList.get(position).getIs_appointment().equals("2")) {
                vh.tv_people_num.setText("现在出发:" + mList.get(position).getPeople_num() + "人");
            }
            if (mList.get(position).getStatus().equals("5")) {
                vh.iv_orderCondition.setVisibility(View.VISIBLE);
                vh.iv_orderCondition.setImageResource(R.mipmap.icon_finish);
                vh.item_travel_records_car_pool_list_bottom_ll.setVisibility(View.GONE);
            } else if (mList.get(position).getStatus().equals("6")) {
                vh.iv_orderCondition.setVisibility(View.VISIBLE);
                vh.iv_orderCondition.setImageResource(R.mipmap.icon_finish);
                vh.item_travel_records_car_pool_list_bottom_ll.setVisibility(View.GONE);
            } else if (mList.get(position).getStatus().equals("7")) {
                vh.iv_orderCondition.setVisibility(View.VISIBLE);
                vh.iv_orderCondition.setImageResource(R.mipmap.icon_cancel);
                vh.item_travel_records_car_pool_list_bottom_ll.setVisibility(View.GONE);
            } else if (mList.get(position).getStatus().equals("8")) {
                vh.iv_orderCondition.setVisibility(View.VISIBLE);
                vh.iv_orderCondition.setImageResource(R.mipmap.icon_revoke);
                vh.item_travel_records_car_pool_list_bottom_ll.setVisibility(View.GONE);
            } else if (mList.get(position).getStatus().equals("9")) {
                vh.iv_orderCondition.setVisibility(View.VISIBLE);
                vh.iv_orderCondition.setImageResource(R.mipmap.icon_nofinish);
                vh.item_travel_records_car_pool_list_bottom_ll.setVisibility(View.VISIBLE);
                vh.item_travel_records_car_pool_list_message_tv.setText("请在预约时间接乘客上车!");
                vh.item_travel_records_car_pool_list_ok_btn.setBackgroundResource(R.drawable.button_selector);
                vh.item_travel_records_car_pool_list_ok_btn.setClickable(true);
                vh.item_travel_records_car_pool_list_ok_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lineOnCar(position);
                    }
                });
            } else if (mList.get(position).getStatus().equals("10")) {
                vh.iv_orderCondition.setVisibility(View.VISIBLE);
                vh.iv_orderCondition.setImageResource(R.mipmap.icon_nofinish);
                vh.item_travel_records_car_pool_list_bottom_ll.setVisibility(View.VISIBLE);
                vh.item_travel_records_car_pool_list_message_tv.setText("");
                vh.item_travel_records_car_pool_list_ok_btn.setBackgroundResource(R.drawable.button_gray_shap);
                vh.item_travel_records_car_pool_list_ok_btn.setClickable(false);
            } else if (mList.get(position).getStatus().equals("11")) {
                vh.iv_orderCondition.setVisibility(View.VISIBLE);
                vh.iv_orderCondition.setImageResource(R.mipmap.icon_nofinish);
                vh.item_travel_records_car_pool_list_bottom_ll.setVisibility(View.VISIBLE);
                vh.item_travel_records_car_pool_list_message_tv.setText("");
                vh.item_travel_records_car_pool_list_ok_btn.setText("订单完成");
                vh.item_travel_records_car_pool_list_ok_btn.setBackgroundResource(R.drawable.button_selector);
                vh.item_travel_records_car_pool_list_ok_btn.setClickable(true);
                vh.item_travel_records_car_pool_list_ok_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lineOnCarOk(position);
                    }
                });
            } else {
                vh.iv_orderCondition.setVisibility(View.VISIBLE);
                vh.iv_orderCondition.setImageResource(R.mipmap.icon_nofinish);
                vh.item_travel_records_car_pool_list_bottom_ll.setVisibility(View.GONE);
            }
        } else if (taker_type_id == 2) {
            if (mList.get(position).getStatus().equals("5")) {
                vh.iv_orderCondition.setVisibility(View.VISIBLE);
                vh.iv_orderCondition.setImageResource(R.mipmap.icon_finish);
            } else if (mList.get(position).getStatus().equals("6")) {
                vh.iv_orderCondition.setVisibility(View.VISIBLE);
                vh.iv_orderCondition.setImageResource(R.mipmap.icon_finish);
            } else if (mList.get(position).getStatus().equals("7")) {
                vh.iv_orderCondition.setVisibility(View.VISIBLE);
                vh.iv_orderCondition.setImageResource(R.mipmap.icon_cancel);
            }
//            else {
//                vh.iv_orderCondition.setVisibility(View.VISIBLE);
//                vh.iv_orderCondition.setImageResource(R.mipmap.icon_nofinish);
//            }
        } else if (taker_type_id == 3) {
            if (mList.get(position).getStatus().equals("2")) {
                vh.iv_orderCondition.setVisibility(View.VISIBLE);
                vh.iv_orderCondition.setImageResource(R.mipmap.icon_finish);
            } else if (mList.get(position).getStatus().equals("3")) {
                vh.iv_orderCondition.setVisibility(View.VISIBLE);
                vh.iv_orderCondition.setImageResource(R.mipmap.icon_revoke);
            } else {
                vh.iv_orderCondition.setVisibility(View.VISIBLE);
                vh.iv_orderCondition.setImageResource(R.mipmap.icon_nofinish);
            }
        }
        //车辆状况
        vh.tv_carCondition.setText(mList.get(position).getTimes());
        //价格
        vh.tv_cost.setText("￥" + mList.get(position).getPrice());
        //目的地
        vh.tv_destination.setText(mList.get(position).getRoute_city_font2());
        //司机名字
        vh.tv_driverName.setText(mList.get(position).getName());
        //当前位置
        vh.tv_location.setText(mList.get(position).getRoute_city_font1());
        //下单时间
        vh.tv_orderTime.setText(mList.get(position).getTimes());

        vh.im_userTelephone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SystemManager.callPhone(mContext, mList.get(position).getAccount());
            }
        });

        vh.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InterCityOrderBean orderBean = mList.get(position);
                TownOrderBean bean = new TownOrderBean();
                bean.setAccount(orderBean.getAccount());
                bean.setHead_img(orderBean.getHead_img());
                bean.setName(orderBean.getName());
                bean.setPrice(orderBean.getPrice());
                bean.setStatus(orderBean.getStatus());
                OrderDetailActivity.startIntent(mContext, bean, Constants.TAKER_TYPE_ID_DRIVE);
            }
        });
        return convertView;
    }

    private void lineOnCar(int position) {

        String id = LoginUserInfoUtil.getLoginUserInfoBean(mContext).getId();

        DriverOrderService.line_on_car(mContext, LoginUserInfoUtil.getLoginUserInfoBean(mContext).getId(),
                mList.get(position).getOrder_small_id(), new AsyncHttpHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        super.onSuccess(responseString);
                        JSONObject jsonObject;
                        try {
                            jsonObject = new JSONObject(responseString);
                            if (jsonObject.optInt("status") == 200) {
                                EventBus.getDefault().post("TravelRecordsCarPoolFragmentUpData", "TravelRecordsCarPoolFragmentUpData");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    private void lineOnCarOk(int position) {
        DriverOrderService.line_on_car_ok(mContext, LoginUserInfoUtil.getLoginUserInfoBean(mContext).getId(),
                mList.get(position).getOrder_small_id(), new AsyncHttpHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        super.onSuccess(responseString);
                        JSONObject jsonObject;
                        try {
                            jsonObject = new JSONObject(responseString);
                            if (jsonObject.optInt("status") == 200) {
                                EventBus.getDefault().post("TravelRecordsCarPoolFragmentUpData", "TravelRecordsCarPoolFragmentUpData");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private class ViewHolder {
        View layoutItem;
        //驾驶员
        TextView tv_driverName;
        TextView tv_people_num;
        //车况
        TextView tv_carCondition;
        //目的地
        TextView tv_destination;
        //价格
        TextView tv_cost;
        //当前位置
        TextView tv_location;
        //下单时间
        TextView tv_orderTime;
        //订单状况
        ImageView iv_orderCondition;
        //司机头像
        CircleImageView civ_orderPicture;
        ImageView im_userTelephone;
        TextView item_travel_records_car_pool_list_message_tv;
        Button item_travel_records_car_pool_list_ok_btn;
        RelativeLayout item_travel_records_car_pool_list_bottom_ll;
    }
}
