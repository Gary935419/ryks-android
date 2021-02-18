package com.inwhoop.qscx.qscxsj.activitys.logins;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.base.BaseActivity;
import com.inwhoop.qscx.qscxsj.activitys.main.SettingSexActivity;
import com.inwhoop.qscx.qscxsj.activitys.main.WebViewActivity;
import com.inwhoop.qscx.qscxsj.app.AsyncHttpHandler;
import com.inwhoop.qscx.qscxsj.business.DriverOrderService;
import com.inwhoop.qscx.qscxsj.business.GetBasicService;
import com.inwhoop.qscx.qscxsj.business.PublicUserService;
import com.inwhoop.qscx.qscxsj.constants.Constants;
import com.inwhoop.qscx.qscxsj.dialogs.AuthenticationDialog;
import com.inwhoop.qscx.qscxsj.entitys.CarTypeBean;
import com.inwhoop.qscx.qscxsj.myinterface.GlideImageLoader;
import com.inwhoop.qscx.qscxsj.utils.L;
import com.inwhoop.qscx.qscxsj.utils.LoginUserInfoUtil;
import com.inwhoop.qscx.qscxsj.utils.PicUtil;
import com.inwhoop.qscx.qscxsj.utils.TimeUtil;
import com.inwhoop.qscx.qscxsj.utils.ToastUtil;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class AuthenticationActivity extends BaseActivity implements View.OnClickListener {

    private TextView title_center_text;

    private EditText activity_authentication_name_edt;
    private EditText activity_authentication_idcard_num_edt;
    private TextView activity_authentication_sex;
    private TextView activity_authentication_date_edt;
    private EditText activity_authentication_car_number_edt;
    private ImageView activity_authentication_idcard_face_iv;
    private ImageView activity_authentication_idcard_side_iv;
    private ImageView activity_authentication_drive_iv;
    private ImageView activity_authentication_travel_iv;
    private ImageView activity_authentication_person_car;
    private ImageView activity_authentication_work;
    private ImageView title_back_img;
    private RelativeLayout activity_authentication_model_rl;
    private RelativeLayout activity_authentication_line_rl;
    private RelativeLayout activity_authentication_cartype_rl;
    private TextView activity_authentication_model_tv;
    private TextView activity_authentication_line_tv;
    private TextView activity_authentication_cartype_tv;
    private TextView activity_authentication_audition;
    private EditText activity_authentication_color_edt;
    private EditText activity_authentication_brand;
    private Button activity_authentication_ok_btn;
    private View layout_agreement;
    private View layoutIdCardFace;
    private View layoutTravel;
    private View layoutPersonCar;
    private View layoutIdCardSide;
    private View layoutDrive;
    private View layoutWork;
    private View layoutCarType;
    private View layoutCarNum;
    private ImageView ivIdCardFace;
    private ImageView ivTravel;
    private ImageView ivPersonCar;
    private ImageView ivIdCardSide;
    private ImageView ivDrive;
    private ImageView ivWork;
    private TextView tv_id_card_face;
    private TextView tv_drive;
    private TextView tv_car_person;
    private TextView tv_id_card_side;
    private TextView tv_travel;
    private TextView tv_work;
    private CheckBox activity_agree_service_check;
    private TextView activity_service_tv;

    private TimePickerView pvTime;

    private ImagePicker imagePicker;
    private List<CarTypeBean> carTypeBeanList;

    public static String type;
    public static String route_city_id1;
    public static String route_city_id2;
    public static String car_type_id;

    private String check;
    private String reason;
    private int imgType;
    private File cardFace;
    private File cardSide;
    private File drive;
    private File travel;
    private File drivePer;
    private File work;
    private String name;
    //1 男 2女
    private int sex = 1;
    private String cards;
    private String times;
    private String car_number;
    private String attribute;
    private String brand;
    private String carType = "";
    /**
     * true 代驾 false快送
     */
    private boolean isDrive;
    private final int REQUEST_SEX = 20001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        initView();
        initListener();
    }

    private void initView() {
        isDrive = getIntent().getBooleanExtra("isDrive", false);
        if (isDrive) {
            check = getIntent().getStringExtra("driving_check");
        } else {
            check = getIntent().getStringExtra("check");
        }

        reason = getIntent().getStringExtra("reason");

        title_center_text = (TextView) findViewById(R.id.tv_title_main);
        title_back_img = (ImageView) findViewById(R.id.iv_title_left);
        activity_service_tv = (TextView) findViewById(R.id.activity_service_tv);

        if (isDrive) {
            title_center_text.setText("代驾认证");
            activity_service_tv.setText("《信息服务协议》");
        } else {
            title_center_text.setText("快送认证");
            activity_service_tv.setText("《注册协议》");
        }
        boolean isBack = getIntent().getBooleanExtra("isBack", false);
        if (isBack) {
            title_back_img.setVisibility(View.VISIBLE);
        } else {
            title_back_img.setVisibility(View.GONE);
        }
        title_back_img.setOnClickListener(this);
        activity_authentication_name_edt = (EditText) findViewById(R.id.activity_authentication_name_edt);
        activity_authentication_idcard_num_edt = (EditText) findViewById(R.id.activity_authentication_idcard_num_edt);
        activity_authentication_sex = (TextView) findViewById(R.id.activity_authentication_sex);
        activity_authentication_date_edt = (TextView) findViewById(R.id.activity_authentication_date_edt);
        activity_authentication_car_number_edt = (EditText) findViewById(R.id.activity_authentication_car_number_edt);
        activity_authentication_idcard_face_iv = (ImageView) findViewById(R.id.activity_authentication_idcard_face_iv);
        activity_authentication_idcard_side_iv = (ImageView) findViewById(R.id.activity_authentication_idcard_side_iv);
        activity_authentication_drive_iv = (ImageView) findViewById(R.id.activity_authentication_drive_iv);
        activity_authentication_travel_iv = (ImageView) findViewById(R.id.activity_authentication_travel_iv);
        activity_authentication_model_rl = (RelativeLayout) findViewById(R.id.activity_authentication_model_rl);
        activity_authentication_line_rl = (RelativeLayout) findViewById(R.id.activity_authentication_line_rl);
        activity_authentication_cartype_rl = (RelativeLayout) findViewById(R.id.activity_authentication_cartype_rl);
        activity_authentication_model_tv = (TextView) findViewById(R.id.activity_authentication_model_tv);
        activity_authentication_line_tv = (TextView) findViewById(R.id.activity_authentication_line_tv);
        activity_authentication_cartype_tv = (TextView) findViewById(R.id.activity_authentication_cartype_tv);
        activity_authentication_audition = (TextView) findViewById(R.id.activity_authentication_audition);
        activity_authentication_color_edt = (EditText) findViewById(R.id.activity_authentication_color_edt);
        activity_authentication_brand = (EditText) findViewById(R.id.activity_authentication_brand);
        activity_authentication_ok_btn = (Button) findViewById(R.id.activity_authentication_ok_btn);
        activity_authentication_person_car = (ImageView) findViewById(R.id.activity_authentication_person_car);
        activity_authentication_work = (ImageView) findViewById(R.id.activity_authentication_work);
        layout_agreement = findViewById(R.id.layout_agreement);
        layoutIdCardFace = findViewById(R.id.layoutIdCardFace);
        layoutTravel = findViewById(R.id.layoutTravel);
        layoutPersonCar = findViewById(R.id.layoutPersonCar);
        layoutIdCardSide = findViewById(R.id.layoutIdCardSide);
        layoutDrive = findViewById(R.id.layoutDrive);
        layoutWork = findViewById(R.id.layoutWork);
        layoutCarType = findViewById(R.id.layoutCarType);
        layoutCarNum = findViewById(R.id.layoutCarNum);
        ivIdCardFace = (ImageView) findViewById(R.id.ivIdCardFace);
        ivTravel = (ImageView) findViewById(R.id.ivTravel);
        ivPersonCar = (ImageView) findViewById(R.id.ivPersonCar);
        ivIdCardSide = (ImageView) findViewById(R.id.ivIdCardSide);
        ivDrive = (ImageView) findViewById(R.id.ivDrive);
        ivWork = (ImageView) findViewById(R.id.ivWork);
        tv_id_card_face = (TextView) findViewById(R.id.tv_id_card_face);
        tv_drive = (TextView) findViewById(R.id.tv_drive);
        tv_car_person = (TextView) findViewById(R.id.tv_car_person);
        tv_id_card_side = (TextView) findViewById(R.id.tv_id_card_side);
        tv_travel = (TextView) findViewById(R.id.tv_travel);
        tv_work = (TextView) findViewById(R.id.tv_work);
        activity_agree_service_check = (CheckBox) findViewById(R.id.activity_agree_service_check);

        RelativeLayout activity_authentication_tip = (RelativeLayout) findViewById(R.id.activity_authentication_tip);
        TextView activity_authentication_tip2 = (TextView) findViewById(R.id.activity_authentication_tip2);
        layoutPersonCar.setVisibility(isDrive ? View.GONE : View.VISIBLE);
        layoutTravel.setVisibility(isDrive ? View.GONE : View.VISIBLE);
        layoutCarType.setVisibility(isDrive ? View.GONE : View.VISIBLE);
        layoutCarNum.setVisibility(isDrive ? View.GONE : View.VISIBLE);
        if (check != null && check.equals("2")) {
            new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setIcon(R.mipmap.icon_careful)
                    .setMessage("认证失败：" + reason)
                    .setNegativeButton("确认", null)
                    .create().show();
            activity_authentication_ok_btn.setBackgroundResource(R.drawable.button_selector);
            activity_authentication_ok_btn.setText("开始认证");
            activity_authentication_ok_btn.setClickable(true);
        } else if (check != null && check.equals("3")) {
            showAlertDialog("认证中...");
            activity_authentication_ok_btn.setBackgroundResource(R.drawable.renzhengzhong_shape);
            activity_authentication_ok_btn.setText("下一步");
            activity_authentication_ok_btn.setClickable(true);
        } else if (check != null && check.equals("1")) {
            layout_agreement.setVisibility(View.GONE);
            activity_authentication_ok_btn.setVisibility(View.GONE);
            activity_authentication_tip.setVisibility(View.GONE);
            activity_authentication_tip2.setVisibility(View.GONE);
            activity_authentication_name_edt.setEnabled(false);
            activity_authentication_idcard_num_edt.setEnabled(false);
            activity_authentication_date_edt.setEnabled(false);
            activity_authentication_car_number_edt.setEnabled(false);
            layoutIdCardFace.setEnabled(false);
            layoutDrive.setEnabled(false);
            layoutIdCardSide.setEnabled(false);
            layoutTravel.setEnabled(false);
            layoutPersonCar.setEnabled(false);
            layoutWork.setEnabled(false);
            activity_authentication_cartype_rl.setEnabled(false);
            activity_authentication_color_edt.setEnabled(false);
            activity_authentication_brand.setEnabled(false);
            if (isBack) {
                initCarTypeList();
                initData();
            }
        }

        initImagePicker();
        initCreateTimes();
        getAudition();
    }

    private void initData() {
        //TODO 获取认证信息
        showProgressDialog("");
        String user_id = LoginUserInfoUtil.getLoginUserInfoBean(this).getId();
        //type 1 跑腿  2代驾
        PublicUserService.getProbate(context, user_id, isDrive ? "2" : "1", new AsyncHttpHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(responseString);
                L.i(TAG, responseString);
                dismissProgressDialog();
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(responseString);
                    if (jsonObject.optInt("status") == 200) {
                        JSONObject result = new JSONObject(jsonObject.optString("result"));
                        String type = result.getString("type");
                        activity_authentication_name_edt.setText(isDrive ? result.getString("driving_name") : result.getString("name"));
                        activity_authentication_idcard_num_edt.setText(isDrive ? result.getString("driving_cards") : result.getString("cards"));
                        activity_authentication_date_edt.setText(isDrive ? result.getString("driving_times") : result.getString("times"));
                        activity_authentication_car_number_edt.setText(isDrive ? result.getString("driving_car_number") : result.getString("car_number"));
                        carType = isDrive ? result.getString("driving_car_type_id") : result.getString("car_type_id");
                        if (carTypeBeanList != null && carTypeBeanList.size() > 0) {
                            activity_authentication_cartype_tv.setText(getTypeName());
                        }
                        activity_authentication_color_edt.setText(result.getString("attribute"));
                        activity_authentication_brand.setText(result.getString("brand"));
                        if (isDrive) {
                            //代驾
                            ImageLoader.getInstance().displayImage(result.getString("driving_img_cards_face"), ivIdCardFace);
                            ImageLoader.getInstance().displayImage(result.getString("driving_img_cards_side"), ivIdCardSide);
                            ImageLoader.getInstance().displayImage(result.getString("driving_img_drivers"), ivDrive);
                            ImageLoader.getInstance().displayImage(result.getString("driving_img_worker"), ivWork);
                        } else {
                            //跑腿
                            ImageLoader.getInstance().displayImage(result.getString("img_cards_face"), ivIdCardFace);
                            ImageLoader.getInstance().displayImage(result.getString("img_cards_side"), ivIdCardSide);
                            ImageLoader.getInstance().displayImage(result.getString("img_drivers"), ivDrive);
                            ImageLoader.getInstance().displayImage(result.getString("img_vehicle"), ivTravel);
                            ImageLoader.getInstance().displayImage(result.getString("img_car_user"), ivPersonCar);
                            ImageLoader.getInstance().displayImage(result.getString("img_worker"), ivWork);
                        }
                    } else {
                        ToastUtil.showShortToast(context, jsonObject.optString("attribute"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getAudition() {
        DriverOrderService.getInfo(context, new AsyncHttpHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(responseString);
                dismissProgressDialog();
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(responseString);
                    if (jsonObject.optInt("status") == 200) {
                        JSONObject result = new JSONObject(jsonObject.optString("result"));
                        String company_name = result.getString("company_name");
                        String company_address = result.getString("company_address");
                        //TODO 面试地点
                        activity_authentication_audition.setText(company_name + "\n" + company_address);
                    } else {
                        ToastUtil.showShortToast(context, jsonObject.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private String getTypeName() {
        if (TextUtils.isEmpty(carType)) {
            return "";
        }
        if (carTypeBeanList != null && carTypeBeanList.size() > 0) {
            for (int i = 0; i < carTypeBeanList.size(); i++) {
                if (carType.equals(carTypeBeanList.get(i).getType())) {
                    return carTypeBeanList.get(i).getName();
                }
            }
        }
        return "";
    }

    private void initListener() {
        activity_authentication_ok_btn.setOnClickListener(this);
        activity_authentication_model_rl.setOnClickListener(this);
        activity_authentication_line_rl.setOnClickListener(this);
        activity_authentication_cartype_rl.setOnClickListener(this);
        activity_authentication_sex.setOnClickListener(this);
        activity_authentication_date_edt.setOnClickListener(this);
        layoutIdCardFace.setOnClickListener(this);
        layoutTravel.setOnClickListener(this);
        layoutPersonCar.setOnClickListener(this);
        layoutIdCardSide.setOnClickListener(this);
        layoutDrive.setOnClickListener(this);
        layoutWork.setOnClickListener(this);
        activity_service_tv.setOnClickListener(this);
    }

    public void showAlertDialog(String message) {
        alertDialog = new android.app.AlertDialog.Builder(this).create();// 创建ProgressDialog对象,关联本当前Activity
        alertDialog.setMessage(message);// 设置ProgressDialog提示信息
        alertDialog.setIcon(R.mipmap.icon_careful);// 设置标题图标；
        //alertDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //alertDialog.setIndeterminate(true);// 设置ProgressDialog的进度条是否不明确；
        // 这个属性对于ProgressDailog默认的转轮模式没有实际意义，
        // 默认下设置为true，它仅仅对带有ProgressBar的Dialog有作用。
        // 修改这个属性为false后可以实时更新进度条的进度。
        alertDialog.setCancelable(true);// 设置ProgressDialog 是否可以按返回键取消；
        alertDialog.setCanceledOnTouchOutside(false);// 按对话框以外的地方起作用。按返回键不起作用

        /** 设置透明度 */
        Window window = alertDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 0.5f;// 透明度
        lp.dimAmount = 0.5f;// 黑暗度
        window.setAttributes(lp);
        alertDialog.show();// 让ProgressDialog显示

        // 会报错：java.lang.IllegalArgumentException: Window type can not be
        // changed
        // after the window is added.
        // myDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);//屏蔽Home键,不过在这里貌似没有什么用

//        alertDialog.setOnKeyListener(new DialogInterface.OnKeyListener()// 为按键设置监听
//        {
//            @Override
//            public boolean onKey(DialogInterface dialog, int keyCode,
//                                 KeyEvent event)// dialog:该密钥该密钥已经被派往对话已经被派往对话;keyCode:对于被按下的物理键的代码;event:包含完整的信息关于这个事件的keyEvent的对象
//
//            {
//                switch (keyCode) {
//                    case KeyEvent.KEYCODE_BACK:// 返回键
//                        return true;
//                }
//                return false;
//            }
//        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_left:
                finish();
                break;
            case R.id.activity_authentication_sex:
                startActivityForResult(new Intent(context, SettingSexActivity.class), REQUEST_SEX);
                break;
            case R.id.activity_authentication_ok_btn:
                if (!activity_agree_service_check.isChecked()) {
                    ToastUtil.showShortToast(context, "请同意并遵守" + (isDrive ? "《信息服务协议》" : "《注册协议》"));
                    return;
                }
                if (check != null && check.equals("3")) {
                    startActivity(new Intent(context, AuthenticationInfoActivity.class));
                    return;
                }
                name = activity_authentication_name_edt.getText().toString().trim();
                cards = activity_authentication_idcard_num_edt.getText().toString().trim();
                times = activity_authentication_date_edt.getText().toString().trim();
                if (!isDrive) {
                    car_number = activity_authentication_car_number_edt.getText().toString().trim();
                    attribute = activity_authentication_color_edt.getText().toString().trim();
                    brand = activity_authentication_brand.getText().toString().trim();
                }
                probate();
                break;
            case R.id.activity_authentication_model_rl:
                new AuthenticationDialog(this, R.style.MyDialog, 1, activity_authentication_model_tv, activity_authentication_cartype_tv, activity_authentication_line_tv).show();
                break;
            case R.id.activity_authentication_line_rl:
                if (type.equals("1")) {
                    new AuthenticationDialog(this, R.style.MyDialog, 2, activity_authentication_model_tv, activity_authentication_cartype_tv, activity_authentication_line_tv).show();
                } else {
                    ToastUtil.showShortToast(this, "只有城际拼车可以选择路线");
                }
                break;
            case R.id.activity_authentication_cartype_rl:
                new AuthenticationDialog(this, R.style.MyDialog, 3, activity_authentication_model_tv, activity_authentication_cartype_tv, activity_authentication_line_tv).show();
                break;
            case R.id.layoutIdCardFace:
                //身份证正面
                imgType = 1;
                Intent intentIdCardFace = new Intent(context, ImageGridActivity.class);
                startActivityForResult(intentIdCardFace, 1);
                break;
            case R.id.layoutIdCardSide:
                //身份证反面
                imgType = 2;
                Intent intentIdCardSide = new Intent(context, ImageGridActivity.class);
                startActivityForResult(intentIdCardSide, 1);
                break;
            case R.id.layoutDrive:
                //驾驶证
                imgType = 3;
                Intent intentDrive = new Intent(context, ImageGridActivity.class);
                startActivityForResult(intentDrive, 1);
                break;
            case R.id.layoutTravel:
                //行驶证
                imgType = 4;
                Intent intentTravel = new Intent(context, ImageGridActivity.class);
                startActivityForResult(intentTravel, 1);
                break;
            case R.id.activity_authentication_date_edt:
                pvTime.show();
                break;
            case R.id.layoutPersonCar:
                //人车认证
                imgType = 5;
                Intent intentDrivePerson = new Intent(context, ImageGridActivity.class);
                startActivityForResult(intentDrivePerson, 1);
                break;
            case R.id.layoutWork:
                imgType = 6;
                Intent intentWork = new Intent(context, ImageGridActivity.class);
                startActivityForResult(intentWork, 1);
                break;
            case R.id.activity_service_tv:
                startActivity(new Intent(this, WebViewActivity.class).putExtra("url", isDrive ? Constants.URL_GENERATION : Constants.URL_DRIVER).putExtra("title", isDrive ? "信息服务协议" : "注册协议"));
                break;
        }
    }

    private void initCreateTimes() {

        //时间选择器
        pvTime = new TimePickerView(context, TimePickerView.Type.YEAR_MONTH_DAY);
        //控制时间范围
        Calendar calendar = Calendar.getInstance();
        pvTime.setRange(calendar.get(Calendar.YEAR) - 40, calendar.get(Calendar.YEAR));//要在setTime 之前才有效果哦
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                String now_time = TimeUtil.getTime();
                String select_time = TimeUtil.getTime(getTime(date), "yyyy-MM-dd");

                if (Long.parseLong(select_time) <= Long.parseLong(now_time)) {
                    activity_authentication_date_edt.setText(getTime(date));
                } else {
                    ToastUtil.showShortToast(context, "领证日期不能大于当前日期");
                }


            }
        });
        //弹出时间选择器
//        pvTime.show();

    }

    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }


    private void initImagePicker() {
        imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(false);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setMultiMode(false);
//        imagePicker.setStyle(CropImageView.Style.CIRCLE);
//        imagePicker.setSelectLimit(1);    //选中数量限制
//        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
//        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
//        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
//        imagePicker.setOutPutX(1000);//裁剪后保存文件的宽度。单位像素
//        imagePicker.setOutPutY(1000);//裁剪后保存文件的高度。单位像素

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 1) {


                final ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                showProgressDialog("加载中，请稍后");

                updateIcon(images);


            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        } else if (resultCode == RESULT_OK) {
            if (data != null && requestCode == REQUEST_SEX) {
                boolean isMan = data.getBooleanExtra("sex", false);
                sex = isMan ? 1 : 2;
                activity_authentication_sex.setText(isMan ? "男" : "女");
            }
        }
    }

    private void updateIcon(ArrayList<ImageItem> images) {
        String path = images.get(0).path;
//            if ("".equals(path) || mdata.contains(path)) {
        if ("".equals(path)) {
            ToastUtil.showShortToast(context, "添加失败，请重试");
            if (myDialog != null) {
                myDialog.dismiss();
            }
            return;
        }

        File file;
        try {
            file = PicUtil.zipImage(path);
            if (imgType == 1) {
                cardFace = PicUtil.zipImage(path);
            } else if (imgType == 2) {
                cardSide = PicUtil.zipImage(path);
            } else if (imgType == 3) {
                drive = PicUtil.zipImage(path);
            } else if (imgType == 4) {
                travel = PicUtil.zipImage(path);
            } else if (imgType == 5) {
                drivePer = PicUtil.zipImage(path);
            } else if (imgType == 6) {
                work = PicUtil.zipImage(path);
            }
        } catch (NullPointerException nullPointerException) {
            file = PicUtil.zipImage("file://" + path);
            if (imgType == 1) {
                cardFace = PicUtil.zipImage("file://" + path);
            } else if (imgType == 2) {
                cardSide = PicUtil.zipImage("file://" + path);
            } else if (imgType == 3) {
                drive = PicUtil.zipImage("file://" + path);
            } else if (imgType == 4) {
                travel = PicUtil.zipImage("file://" + path);
            } else if (imgType == 5) {
                drivePer = PicUtil.zipImage("file://" + path);
            } else if (imgType == 6) {
                work = PicUtil.zipImage("file://" + path);
            }
        }


        if (path.startsWith("file://")) {
            if (imgType == 1) {
//                PicUtil.displayImage(path, activity_authentication_idcard_face_iv);
                ImageLoader.getInstance().displayImage(path, ivIdCardFace);
                activity_authentication_idcard_face_iv.setVisibility(View.GONE);
                tv_id_card_face.setVisibility(View.GONE);
            } else if (imgType == 2) {
                ImageLoader.getInstance().displayImage(path, ivIdCardSide);
                activity_authentication_idcard_side_iv.setVisibility(View.GONE);
                tv_id_card_side.setVisibility(View.GONE);
            } else if (imgType == 3) {
                ImageLoader.getInstance().displayImage(path, ivDrive);
                activity_authentication_drive_iv.setVisibility(View.GONE);
                tv_drive.setVisibility(View.GONE);
            } else if (imgType == 4) {
                ImageLoader.getInstance().displayImage(path, ivTravel);
                activity_authentication_travel_iv.setVisibility(View.GONE);
                tv_travel.setVisibility(View.GONE);
            } else if (imgType == 5) {
                ImageLoader.getInstance().displayImage(path, ivPersonCar);
                activity_authentication_person_car.setVisibility(View.GONE);
                tv_car_person.setVisibility(View.GONE);
            } else if (imgType == 6) {
                ImageLoader.getInstance().displayImage(path, ivWork);
                activity_authentication_work.setVisibility(View.GONE);
                tv_work.setVisibility(View.GONE);
            }
        } else {
            if (imgType == 1) {
//            PicUtil.displayImage(path, activity_authentication_idcard_face_iv);
                ImageLoader.getInstance().displayImage("file://" + path, ivIdCardFace);
                activity_authentication_idcard_face_iv.setVisibility(View.GONE);
                tv_id_card_face.setVisibility(View.GONE);
            } else if (imgType == 2) {
                ImageLoader.getInstance().displayImage("file://" + path, ivIdCardSide);
                activity_authentication_idcard_side_iv.setVisibility(View.GONE);
                tv_id_card_side.setVisibility(View.GONE);
            } else if (imgType == 3) {
                ImageLoader.getInstance().displayImage("file://" + path, ivDrive);
                activity_authentication_drive_iv.setVisibility(View.GONE);
                tv_drive.setVisibility(View.GONE);
            } else if (imgType == 4) {
                ImageLoader.getInstance().displayImage("file://" + path, ivTravel);
                activity_authentication_travel_iv.setVisibility(View.GONE);
                tv_travel.setVisibility(View.GONE);
            } else if (imgType == 5) {
                ImageLoader.getInstance().displayImage("file://" + path, ivPersonCar);
                activity_authentication_person_car.setVisibility(View.GONE);
                tv_car_person.setVisibility(View.GONE);
            } else if (imgType == 6) {
                ImageLoader.getInstance().displayImage("file://" + path, ivWork);
                activity_authentication_work.setVisibility(View.GONE);
                tv_work.setVisibility(View.GONE);
            }
        }

        handler.sendEmptyMessage(0);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (myDialog != null) {
                myDialog.dismiss();
            }
        }
    };

    private void probate() {
        String user_id = LoginUserInfoUtil.getLoginUserInfoBean(this).getId();
        if (name == null || name.equals("")) {
            ToastUtil.showShortToast(this, "请填写姓名");
            return;
        }
        if (cards == null || cards.equals("")) {
            ToastUtil.showShortToast(this, "请填写身份证号");
            return;
        }
        if (times == null || times.equals("")) {
            ToastUtil.showShortToast(this, "请选择领证日期");
            return;
        }
        if (!isDrive) {
            if (car_number == null || car_number.equals("")) {
                ToastUtil.showShortToast(this, "请填写车牌号");
                return;
            }
            if (travel == null) {
                ToastUtil.showShortToast(this, "请选择行驶证照");
                return;
            }
            if (drivePer == null) {
                ToastUtil.showShortToast(this, "请选择人车合影");
                return;
            }
            if (car_type_id == null || car_type_id.equals("")) {
                ToastUtil.showShortToast(this, "请选择车辆类型");
                return;
            }
            if (attribute == null || attribute.equals("")) {
                ToastUtil.showShortToast(this, "请填写颜色");
                return;
            }
            if (TextUtils.isEmpty(brand)) {
                ToastUtil.showShortToast(this, "请填写品牌");
                return;
            }
        }
        if (cardFace == null) {
            ToastUtil.showShortToast(this, "请选择身份证正面照");
            return;
        }
        if (cardSide == null) {
            ToastUtil.showShortToast(this, "请选择身份证背面照");
            return;
        }

        if (drive == null) {
            ToastUtil.showShortToast(this, "请选择驾驶证照");
            return;
        }


        if (work == null) {
            ToastUtil.showShortToast(this, "请选择工作照");
            return;
        }
        showProgressDialog("");
        PublicUserService.probate(this, user_id, name, sex, cards, times, car_number, cardFace, cardSide,
                drive, travel, drivePer, work, type, route_city_id1, route_city_id2, car_type_id, attribute, brand, isDrive ? "2" : "1",
                new AsyncHttpHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        super.onSuccess(responseString);
                        dismissProgressDialog();

                        JSONObject jsonObject;
                        try {
                            jsonObject = new JSONObject(responseString);
                            if (jsonObject.optInt("status") == 200) {
                                ToastUtil.showShortToast(context, jsonObject.optString("msg"));
                                activity_authentication_ok_btn.setBackgroundResource(R.drawable.renzhengzhong_shape);
                                activity_authentication_ok_btn.setText("下一步");
                                activity_authentication_ok_btn.setClickable(true);
                                check = "3";
                                EventBus.getDefault().post("tag", "MainActivityAuthentication");
                                new AlertDialog.Builder(context)
                                        .setTitle("提示")
                                        .setIcon(R.mipmap.icon_careful)
                                        .setMessage("认证中")
                                        .setOnKeyListener(new DialogInterface.OnKeyListener()// 为按键设置监听
                                        {
                                            @Override
                                            public boolean onKey(DialogInterface dialog, int keyCode,
                                                                 KeyEvent event)// dialog:该密钥该密钥已经被派往对话已经被派往对话;keyCode:对于被按下的物理键的代码;event:包含完整的信息关于这个事件的keyEvent的对象

                                            {
                                                switch (keyCode) {
                                                    case KeyEvent.KEYCODE_BACK:// 返回键
//                                    Log.i("aaa", "KEYCODE_BACK");
//                                    myDialog.dismiss();
//                                    finish();
                                                        return true;
                                                }
                                                return false;
                                            }
                                        })
                                        .create().show();
                            } else {
                                ToastUtil.showShortToast(context, jsonObject.optString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void initCarTypeList() {
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
                        if (!TextUtils.isEmpty(carType)) {
                            activity_authentication_cartype_tv.setText(getTypeName());
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
