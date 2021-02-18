package com.inwhoop.qscx.qscxsj.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.activitys.logins.AuthenticationActivity;
import com.inwhoop.qscx.qscxsj.adapters.CarTypeAdapter;
import com.inwhoop.qscx.qscxsj.adapters.LineAdapter;
import com.inwhoop.qscx.qscxsj.adapters.ModelAdapter;
import com.inwhoop.qscx.qscxsj.app.AsyncHttpHandler;
import com.inwhoop.qscx.qscxsj.business.GetBasicService;
import com.inwhoop.qscx.qscxsj.business.PublicUserService;
import com.inwhoop.qscx.qscxsj.entitys.CarTypeBean;
import com.inwhoop.qscx.qscxsj.entitys.LineBean;
import com.inwhoop.qscx.qscxsj.entitys.ModelBean;
import com.inwhoop.qscx.qscxsj.entitys.UserBean;
import com.inwhoop.qscx.qscxsj.utils.LoginUserInfoUtil;
import com.inwhoop.qscx.qscxsj.utils.ToastUtil;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Lucky on 2016/11/22.
 */

public class AuthenticationDialog extends Dialog {

    private Context context;

    private int type;
    private TextView model;
    private TextView cartype;
    private TextView line;

    private TextView dialog_authentication_title;
    private ListView dialog_authentication_list;

    private List<CarTypeBean> carTypeBeanList;
    private CarTypeAdapter carTypeAdapter;

    private List<LineBean> lineBeanList;
    private LineAdapter lineAdapter;

    private List<ModelBean> modelBeenList;
    private ModelAdapter modelAdapter;

    private boolean isSet = false;


    public AuthenticationDialog(Context context) {
        super(context);
        this.context = context;
    }

    public AuthenticationDialog(Context context, int theme, int type, TextView model, TextView line) {
        super(context, theme);
        this.context = context;
        this.type = type;
        this.model = model;
        this.line = line;
        this.isSet = true;
    }

    public AuthenticationDialog(Context context, int theme, int type, TextView model, TextView cartype, TextView line) {
        super(context, theme);

        this.context = context;
        this.type = type;
        this.cartype = cartype;
        this.model = model;
        this.line = line;
        this.isSet = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_authentication);

        initView();
        initListener();
    }

    private void initView() {
        dialog_authentication_title = (TextView) findViewById(R.id.dialog_authentication_title);
        dialog_authentication_list = (ListView) findViewById(R.id.dialog_authentication_list);

        if (type == 1) {
            initModelList();
        } else if (type == 2) {
            initLineList();
        } else if (type == 3) {
            initCarTypeList();
        }

    }

    private void initListener() {
        dialog_authentication_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!isSet) {
                    if (type == 1) {
                        if (AuthenticationActivity.type == null || !AuthenticationActivity.type.equals(modelBeenList.get(position).getId())) {
                            AuthenticationActivity.type = modelBeenList.get(position).getId();
                            model.setText(modelBeenList.get(position).getName());
                            cartype.setHint("请选择车型");
                            cartype.setText("");
                            line.setHint("请选择接单路线");
                            line.setText("");
                            AuthenticationActivity.route_city_id1 = "";
                            AuthenticationActivity.route_city_id2 = "";
                            AuthenticationActivity.car_type_id = "";
                        }
                    } else if (type == 2) {
                        line.setText(lineBeanList.get(position).getRoute_city_name1() + "-" + lineBeanList.get(position).getRoute_city_name2());
                        AuthenticationActivity.route_city_id1 = lineBeanList.get(position).getRoute_city_id1();
                        AuthenticationActivity.route_city_id2 = lineBeanList.get(position).getRoute_city_id2();
                    } else if (type == 3) {
                        cartype.setText(carTypeBeanList.get(position).getName());
                        AuthenticationActivity.car_type_id = carTypeBeanList.get(position).getId();
                    }
                } else {
                    if (type == 1) {
                        if (!LoginUserInfoUtil.getLoginUserInfoBean(context).getTaker_type_id().equals(modelBeenList.get(position).getId())) {
                            UserBean userBean = LoginUserInfoUtil.getLoginUserInfoBean(context);
                            userBean.setTaker_type_id(modelBeenList.get(position).getId());
                            LoginUserInfoUtil.setLoginUserInfoBean(context, userBean);
                            model.setText(modelBeenList.get(position).getName());
                            line.setHint("请选择接单路线");
                            line.setText("");
                            if (modelBeenList.get(position).getName().equals("城际拼车")) {
                                line.setText(LoginUserInfoUtil.getLoginUserInfoBean(context).getRoute_city_font1() + "-"
                                        + LoginUserInfoUtil.getLoginUserInfoBean(context).getRoute_city_font2());
                            }
//                            personal();
                        }
                    } else if (type == 2) {
                        UserBean userBean = LoginUserInfoUtil.getLoginUserInfoBean(context);
                        userBean.setRoute_city_id1(lineBeanList.get(position).getRoute_city_id1());
                        userBean.setRoute_city_id2(lineBeanList.get(position).getRoute_city_id2());
                        LoginUserInfoUtil.setLoginUserInfoBean(context, userBean);
                        line.setText(lineBeanList.get(position).getRoute_city_name1() + "-" + lineBeanList.get(position).getRoute_city_name2());
//                        personal();
                    }
                }
                cancel();
            }
        });
    }

    private void initModelList() {
        dialog_authentication_title.setText("选择模式");
        String user_id = null;
        if (isSet) {
            user_id = LoginUserInfoUtil.getLoginUserInfoBean(context).getId();
        } else {
            user_id = "";
        }
        GetBasicService.taker_type(context, user_id, new AsyncHttpHandler() {
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
                        modelBeenList = new Gson().fromJson(jsonObject.optString("result"),
                                new TypeToken<List<ModelBean>>() {
                                }.getType());
                        modelAdapter = new ModelAdapter(context, modelBeenList);
                        dialog_authentication_list.setAdapter(modelAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initCarTypeList() {
        dialog_authentication_title.setText("选择车型");
        GetBasicService.car_type(context, AuthenticationActivity.type, new AsyncHttpHandler() {
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
                        carTypeBeanList = new Gson().fromJson(jsonObject.optString("result"),
                                new TypeToken<List<CarTypeBean>>() {
                                }.getType());
                        carTypeAdapter = new CarTypeAdapter(context, carTypeBeanList);
                        dialog_authentication_list.setAdapter(carTypeAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initLineList() {
        dialog_authentication_title.setText("选择路线");
        GetBasicService.route(context, new AsyncHttpHandler() {
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
                        lineBeanList = new Gson().fromJson(jsonObject.optString("result"),
                                new TypeToken<List<LineBean>>() {
                                }.getType());

                        lineAdapter = new LineAdapter(context, lineBeanList);
                        dialog_authentication_list.setAdapter(lineAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

//    private void personal() {
//
//        String user_id = LoginUserInfoUtil.getLoginUserInfoBean(context).getId();
//        String taker_type_id = null;
//        String route_city_id1 = null;
//        String route_city_id2 = null;
//        if (type == 1) {
//            taker_type_id = LoginUserInfoUtil.getLoginUserInfoBean(context).getTaker_type_id();
//        } else if (type == 2) {
//            route_city_id1 = LoginUserInfoUtil.getLoginUserInfoBean(context).getRoute_city_id1();
//            route_city_id2 = LoginUserInfoUtil.getLoginUserInfoBean(context).getRoute_city_id2();
//        }
//
//        PublicUserService.personal(context, user_id, null, null, taker_type_id, route_city_id1,
//                route_city_id2, new AsyncHttpHandler() {
//                    @Override
//                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//
//                    }
//
//                    @Override
//                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
//                        super.onSuccess(responseString);
//                        JSONObject jsonObject;
//                        try {
//                            jsonObject = new JSONObject(responseString);
//                            ToastUtil.showShortToast(context, jsonObject.optString("msg"));
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        } finally {
//                            cancel();
//                        }
//                    }
//                });
//    }
}
