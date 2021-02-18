package com.inwhoop.qscx.qscxsj.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.app.AsyncHttpHandler;
import com.inwhoop.qscx.qscxsj.business.DriverOrderService;
import com.inwhoop.qscx.qscxsj.business.DriverPickService;
import com.inwhoop.qscx.qscxsj.entitys.DriverPickOrderBean;
import com.inwhoop.qscx.qscxsj.entitys.DriverPickTownOrderBean;
import com.inwhoop.qscx.qscxsj.tools.SystemManager;
import com.inwhoop.qscx.qscxsj.utils.LoginUserInfoUtil;
import com.inwhoop.qscx.qscxsj.utils.PicUtil;
import com.inwhoop.qscx.qscxsj.utils.ToastUtil;
import com.inwhoop.qscx.qscxsj.views.CircleImageView;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;

import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Lucky on 2016/12/7.
 */

public class ReceivedOrderAdapter extends BaseAdapter {

    private Context mContext;
    private List<DriverPickOrderBean.PassengerBean> mList;

    public ReceivedOrderAdapter(Context context, List<DriverPickOrderBean.PassengerBean> list) {
        mContext = context;
        mList = list;
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
        ViewHolder holder = null;
        if (convertView == null || convertView.getTag() == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_receved_order, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        getOrder(mList.get(position).getOrder_small_id());
        PicUtil.displayImage(mList.get(position).getHead_img(), holder.item_received_order_head_img);
        holder.item_received_order_name_tv.setText(mList.get(position).getName());
        holder.item_received_order_num_tv.setText(mList.get(position).getPeople_num() + "人");
        holder.item_received_order_address_tv.setText("由：" + mList.get(position).getLocation());
        holder.item_received_order_line_tv.setText(mList.get(position).getRoute());
        holder.item_received_order_arrival_position_tv.setText("到：" + mList.get(position).getArrival_position());
        holder.item_received_order_telephone_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SystemManager.callPhone(mContext, mList.get(position).getAccount());
            }
        });
        if (mList.get(position).getStatus().equals("2")) {
            holder.item_received_order_cancel_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cancel(mList.get(position));
                }
            });
            holder.item_received_order_tackcar_bnt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    aboard(mList.get(position));
                }
            });
        } else if (mList.get(position).getStatus().equals("3")) {
            holder.item_received_order_cancel_btn.setVisibility(View.GONE);
            holder.item_received_order_tackcar_bnt.setVisibility(View.GONE);
            holder.item_received_order_tackcar_bnt.setText("完成");
        } else if (mList.get(position).getStatus().equals("4")) {
            holder.item_received_order_cancel_btn.setVisibility(View.GONE);
            holder.item_received_order_tackcar_bnt.setText("完成");
            holder.item_received_order_tackcar_bnt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.showShortToast(mContext, "请等待乘客支付");
                }
            });
        } else if (mList.get(position).getStatus().equals("5")) {
            holder.item_received_order_cancel_btn.setVisibility(View.GONE);
            holder.item_received_order_tackcar_bnt.setText("完成");
            holder.item_received_order_tackcar_bnt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    smallOk(mList.get(position));
                }
            });
        } else if (mList.get(position).getStatus().equals("6")) {
            holder.item_received_order_cancel_btn.setVisibility(View.GONE);
            holder.item_received_order_tackcar_bnt.setText("已完成");
        }
        return convertView;
    }

    private void aboard(DriverPickOrderBean.PassengerBean passengerBean) {
        DriverPickService.aboard(mContext, LoginUserInfoUtil.getDriverPickBasicBean(mContext).getTaker_type_id(),
                passengerBean.getOrder_small_id(), new AsyncHttpHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        super.onSuccess(responseString);
                        JSONObject jsonObject;
                        try {
                            jsonObject = new JSONObject(responseString);
                            ToastUtil.showShortToast(mContext, jsonObject.optString("msg"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void cancel(DriverPickOrderBean.PassengerBean passengerBean) {


        String taker_type_id = LoginUserInfoUtil.getDriverPickBasicBean(mContext).getTaker_type_id();

        String order_small_id = passengerBean.getOrder_small_id();

        DriverPickService.cancel(mContext, taker_type_id, order_small_id, new AsyncHttpHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(responseString);
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(responseString);
                    getOrder(LoginUserInfoUtil.getDriverPickBasicBean(mContext).getOrder_id());
                    ToastUtil.showShortToast(mContext, jsonObject.optString("msg"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void smallOk(final DriverPickOrderBean.PassengerBean passengerBean) {

        String taker_type_id = LoginUserInfoUtil.getDriverPickBasicBean(mContext).getTaker_type_id();

        String order_small_id = passengerBean.getOrder_small_id();

        DriverPickService.small_ok(mContext, taker_type_id, order_small_id, new AsyncHttpHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(responseString);
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(responseString);
                    ToastUtil.showShortToast(mContext, jsonObject.optString("msg"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getOrder(String order_id) {

        DriverPickService.get_order(mContext, order_id, new AsyncHttpHandler() {
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
                        DriverPickTownOrderBean driverPickTownOrderBean = new Gson().fromJson(jsonObject.optString("result"),
                                new TypeToken<DriverPickTownOrderBean>() {
                                }.getType());

                        if (driverPickTownOrderBean.getStatus().equals("4") || driverPickTownOrderBean.getStatus().equals("5")) {
                            EventBus.getDefault().post("tag", "ReceivedOrderActivityFinish");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private class ViewHolder {

        public ViewHolder(View view) {
            item_received_order_head_img = (CircleImageView) view.findViewById(R.id.item_received_order_head_img);
            item_received_order_name_tv = (TextView) view.findViewById(R.id.item_received_order_name_tv);
            item_received_order_num_tv = (TextView) view.findViewById(R.id.item_received_order_num_tv);
            item_received_order_address_tv = (TextView) view.findViewById(R.id.item_received_order_address_tv);
            item_received_order_line_tv = (TextView) view.findViewById(R.id.item_received_order_line_tv);
            item_received_order_arrival_position_tv = (TextView) view.findViewById(R.id.item_received_order_arrival_position_tv);
            item_received_order_telephone_iv = (ImageView) view.findViewById(R.id.item_received_order_telephone_iv);
            item_received_order_cancel_btn = (Button) view.findViewById(R.id.item_received_order_cancel_btn);
            item_received_order_tackcar_bnt = (Button) view.findViewById(R.id.item_received_order_tackcar_bnt);
        }

        private CircleImageView item_received_order_head_img;
        private TextView item_received_order_name_tv;
        private TextView item_received_order_num_tv;
        private TextView item_received_order_address_tv;
        private TextView item_received_order_line_tv;
        private TextView item_received_order_arrival_position_tv;
        private ImageView item_received_order_telephone_iv;
        private Button item_received_order_cancel_btn;
        private Button item_received_order_tackcar_bnt;
    }
}
