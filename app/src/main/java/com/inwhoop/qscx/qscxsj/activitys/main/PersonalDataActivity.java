package com.inwhoop.qscx.qscxsj.activitys.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.base.BaseActivity;
import com.inwhoop.qscx.qscxsj.activitys.logins.AuthenticationActivity;
import com.inwhoop.qscx.qscxsj.activitys.logins.AuthenticationInfoActivity;
import com.inwhoop.qscx.qscxsj.business.PublicUserService;
import com.inwhoop.qscx.qscxsj.business.callback.OnHttpCallbackListener;
import com.inwhoop.qscx.qscxsj.business.callback.TextHttpResCallback;
import com.inwhoop.qscx.qscxsj.entitys.UserBean;
import com.inwhoop.qscx.qscxsj.myinterface.GlideImageLoader;
import com.inwhoop.qscx.qscxsj.sp.SaveInfo;
import com.inwhoop.qscx.qscxsj.utils.LoginUserInfoUtil;
import com.inwhoop.qscx.qscxsj.utils.PicUtil;
import com.inwhoop.qscx.qscxsj.utils.ToastUtil;
import com.inwhoop.qscx.qscxsj.views.CircleImageView;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.util.ArrayList;


public class PersonalDataActivity extends BaseActivity implements View.OnClickListener {

    private TextView title_center_text;
    private ImageView title_back_img;

    private CircleImageView activity_personal_data_headimg_iv;

    private RelativeLayout activity_personal_data_headimg_rl;
    private RelativeLayout runVerifyRl;
    private RelativeLayout driveVerifyRl;
    private RelativeLayout layout_invite_code;
    private TextView runVerifyTv;
    private TextView driveVerifyTv;
    private TextView phoneTv;
    private TextView tv_invite_person_code;

    private Button activity_personal_data_ok_btn;
    private ImagePicker imagePicker;
    private File headimg;

    private UserBean userBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);
        initView();
        initListener();
        initImagePicker();
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestUserInfo();
    }

    private void initView() {
        title_center_text = findViewById(R.id.tv_title_main);
        title_center_text.setText("个人资料");
        title_back_img = findViewById(R.id.iv_title_left);
        title_back_img.setVisibility(View.VISIBLE);
        activity_personal_data_headimg_iv = findViewById(R.id.activity_personal_data_headimg_iv);
        activity_personal_data_headimg_rl = findViewById(R.id.activity_personal_data_headimg_rl);
        runVerifyRl = findViewById(R.id.run_verify_rl);
        driveVerifyRl = findViewById(R.id.drive_verify_rl);
        layout_invite_code = findViewById(R.id.layout_invite_code);
        runVerifyTv = findViewById(R.id.run_verify_status_tv);
        driveVerifyTv = findViewById(R.id.drive_verify_status_tv);
        tv_invite_person_code = findViewById(R.id.tv_invite_person_code);
        activity_personal_data_ok_btn = findViewById(R.id.activity_personal_data_ok_btn);
        phoneTv = findViewById(R.id.phone_num_tv);
        userBean = LoginUserInfoUtil.getLoginUserInfoBean(this);
        bindData(userBean);
    }

    private void initListener() {
        title_back_img.setOnClickListener(this);
        runVerifyRl.setOnClickListener(this);
        driveVerifyRl.setOnClickListener(this);
        activity_personal_data_headimg_rl.setOnClickListener(this);
        activity_personal_data_ok_btn.setOnClickListener(this);
        layout_invite_code.setOnClickListener(this);
    }

    private void bindData(UserBean userBean) {
        this.userBean = userBean;
        PicUtil.displayImage(LoginUserInfoUtil.getLoginUserInfoBean(this).getHead_img(), activity_personal_data_headimg_iv);
        phoneTv.setText(userBean.getAccount());
        String invitePersonCode = userBean.getInvitation_code2_up();
        tv_invite_person_code.setText(TextUtils.isEmpty(invitePersonCode) ? "" : invitePersonCode);
        //快送认证 1通过 2未通过 3审核中 0未认证
        switch (userBean.getUser_check()) {
            case "1":
                runVerifyTv.setText("通过");
                break;
            case "2":
                runVerifyTv.setText("未通过");
                break;
            case "3":
                runVerifyTv.setText("审核中");
                break;
            case "0":
                runVerifyTv.setText("未认证");
                break;
        }
        //代驾认证 1通过 2未通过 3审核中 0未认证
        switch (userBean.getDriving_check()) {
            case "1":
                driveVerifyTv.setText("通过");
                break;
            case "2":
                driveVerifyTv.setText("未通过");
                break;
            case "3":
                driveVerifyTv.setText("审核中");
                break;
            case "0":
                driveVerifyTv.setText("未认证");
                break;
        }
        if (headimg != null) {
            ImageLoader.getInstance().displayImage("file://" + headimg.getPath(), activity_personal_data_headimg_iv);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_left:
                finish();
                break;
            case R.id.run_verify_rl:
                if (userBean.getUser_check().equals("0") || userBean.getUser_check().equals("1") || userBean.getUser_check().equals("2")) {
                    startActivity(new Intent(PersonalDataActivity.this, AuthenticationActivity.class)
                            .putExtra("isDrive", false)
                            .putExtra("check", userBean.getUser_check())
                            .putExtra("isBack", true)
                            .putExtra("reason", userBean.getReason()));
                } else if (userBean.getUser_check().equals("3")) {
                    //审核中
                    startActivity(new Intent(context, AuthenticationInfoActivity.class));
                }
                break;
            case R.id.drive_verify_rl:
                if (userBean.getDriving_check().equals("0") || userBean.getDriving_check().equals("1") || userBean.getDriving_check().equals("2")) {
                    startActivity(new Intent(PersonalDataActivity.this, AuthenticationActivity.class)
                            .putExtra("isDrive", true)
                            .putExtra("driving_check", userBean.getDriving_check())
                            .putExtra("isBack", true)
                            .putExtra("reason", userBean.getReason()));
                } else if (userBean.getDriving_check().equals("3")) {
                    //审核中
                    startActivity(new Intent(context, AuthenticationInfoActivity.class));
                }
                break;
            case R.id.activity_personal_data_headimg_rl:
                //TODO 个人页面
                Intent intentIdCardFace = new Intent(context, ImageGridActivity.class);
                startActivityForResult(intentIdCardFace, 1);
                break;
            case R.id.activity_personal_data_ok_btn:
                requestSaveUserInfo();
                break;
            case R.id.layout_invite_code:
                InputActivity.startIntentForResult(this);
                break;
        }
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
                showLoadingDialog("加载中，请稍后...", true);
                updateIcon(images);
            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == InputActivity.REQUEST_CODE && resultCode == RESULT_OK) {
            String content = data.getStringExtra("result");
            if (!TextUtils.isEmpty(content)) {
                tv_invite_person_code.setText(content);
            }
        }
    }

    private void updateIcon(ArrayList<ImageItem> images) {
        String path = images.get(0).path;
        if (TextUtils.isEmpty(path)) {
            ToastUtil.showShortToast(context, "添加失败，请重试");
            dismissLoadingDialog();
            return;
        }
        try {
            headimg = PicUtil.zipImage(path);
        } catch (NullPointerException nullPointerException) {
            headimg = PicUtil.zipImage("file://" + path);
        }
        if (path.startsWith("file://")) {
            ImageLoader.getInstance().displayImage(path, activity_personal_data_headimg_iv);
        } else {
            ImageLoader.getInstance().displayImage("file://" + path, activity_personal_data_headimg_iv);
        }
        handler.sendEmptyMessage(0);
    }

    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            dismissLoadingDialog();
        }
    };

    /**
     * 提交个人资料修改
     */
    private void requestSaveUserInfo() {
        showLoadingDialog("正在保存修改...", false);
        String user_id = userBean.getId();
        String taker_type_id = userBean.getTaker_type_id();
        String route_city_id1 = "";
        String route_city_id2 = "";
        if (taker_type_id.equals("1")) {
            route_city_id1 = userBean.getRoute_city_id1();
            route_city_id2 = userBean.getRoute_city_id2();
        }
        String invitePersonCode = tv_invite_person_code.getText().toString();
        PublicUserService.personal(context, user_id, headimg, null, taker_type_id, route_city_id1, route_city_id2, invitePersonCode, new TextHttpResCallback(new OnHttpCallbackListener() {
            @Override
            public void onHttpSuccess(String result, String msg) {
                dismissLoadingDialog();
                ToastUtil.showShortToast(PersonalDataActivity.this, msg);
                requestUserInfo();
            }

            @Override
            public void onHttpFailure(int status, String msg) {
                dismissLoadingDialog();
                ToastUtil.showShortToast(PersonalDataActivity.this, msg);
            }

            @Override
            public void onHttpError(String errorMsg) {
                dismissLoadingDialog();
                ToastUtil.showShortToast(PersonalDataActivity.this, errorMsg);
            }
        }));
    }

    /**
     * 获取用户信息
     */
    private void requestUserInfo() {
        String id = SaveInfo.getInstance(this).getLoginUserInfo().getId();
        PublicUserService.info(this, id, new TextHttpResCallback(new OnHttpCallbackListener() {
            @Override
            public void onHttpSuccess(String result, String msg) {
                userBean = new Gson().fromJson(result, UserBean.class);
                bindData(userBean);
                SaveInfo.getInstance(PersonalDataActivity.this).setLoginUserInfo(userBean);
            }

            @Override
            public void onHttpFailure(int status, String msg) {
                ToastUtil.showShortToast(PersonalDataActivity.this, msg);
            }

            @Override
            public void onHttpError(String errorMsg) {
                ToastUtil.showShortToast(PersonalDataActivity.this, errorMsg);
            }
        }));
    }
}
