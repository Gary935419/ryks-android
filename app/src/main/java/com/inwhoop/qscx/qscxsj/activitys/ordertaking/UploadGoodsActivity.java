package com.inwhoop.qscx.qscxsj.activitys.ordertaking;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.base.BaseActivity;
import com.inwhoop.qscx.qscxsj.business.DriverOrderService;
import com.inwhoop.qscx.qscxsj.business.callback.OnHttpCallbackListener;
import com.inwhoop.qscx.qscxsj.business.callback.TextHttpResCallback;
import com.inwhoop.qscx.qscxsj.constants.Constants;
import com.inwhoop.qscx.qscxsj.entitys.DriverPickTownOrderBean;
import com.inwhoop.qscx.qscxsj.utils.FileUtils;
import com.inwhoop.qscx.qscxsj.utils.ToastUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * 提交货物信息页
 */
public class UploadGoodsActivity extends BaseActivity {

    private TextView tv_title_main;
    private ImageView iv_title_left;
    private ImageView iv_1, iv_2, iv_3;
    private Button btn_submit;

    private DriverPickTownOrderBean orderBean;

    private int index = 1;
    private String fileNameGoods1 = "", path1 = "";
    private String fileNameGoods2 = "", path2 = "";
    private String fileNameGoods3 = "", path3 = "";

    /**
     * 跳转方法
     */
    public static void startIntent(Context context, DriverPickTownOrderBean orderBean) {
        Intent intent = new Intent(context, UploadGoodsActivity.class);
        intent.putExtra("order", orderBean);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_goods);
        initView();
        initListener();
        initData();
    }

    private void initView() {
        tv_title_main = findViewById(R.id.tv_title_main);
        iv_title_left = findViewById(R.id.iv_title_left);
        iv_1 = findViewById(R.id.iv_1);
        iv_2 = findViewById(R.id.iv_2);
        iv_3 = findViewById(R.id.iv_3);
        btn_submit = findViewById(R.id.btn_submit);
        iv_title_left.setVisibility(View.VISIBLE);
    }

    private void initListener() {
        iv_title_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        iv_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = 1;
                fileNameGoods1 = FileUtils.FILENAME_GOODS + System.currentTimeMillis() + ".jpg";
                FileUtils.startCamera(UploadGoodsActivity.this, new File(FileUtils.IMAGE_PATH, fileNameGoods1));
            }
        });
        iv_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = 2;
                fileNameGoods2 = FileUtils.FILENAME_GOODS + System.currentTimeMillis() + ".jpg";
                FileUtils.startCamera(UploadGoodsActivity.this, new File(FileUtils.IMAGE_PATH, fileNameGoods2));
            }
        });
        iv_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = 3;
                fileNameGoods3 = FileUtils.FILENAME_GOODS + System.currentTimeMillis() + ".jpg";
                FileUtils.startCamera(UploadGoodsActivity.this, new File(FileUtils.IMAGE_PATH, fileNameGoods3));
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(path1) || TextUtils.isEmpty(path2) || TextUtils.isEmpty(path3)) {
                    ToastUtil.showShortToast(UploadGoodsActivity.this, "请完善图片信息才可以提交");
                    return;
                }
                //TODO upload api
                requestSubmitGoodsPicture();
            }
        });
    }

    private void initData() {
        tv_title_main.setText("上传货物图片");
        orderBean = (DriverPickTownOrderBean) getIntent().getSerializableExtra("order");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //相机返回数据
        if (resultCode == RESULT_OK && requestCode == FileUtils.PICK_FROM_CAMERA) {
            switch (index) {
                case 1:
                    path1 = FileUtils.IMAGE_PATH + "/" + fileNameGoods1;
                    Picasso.with(UploadGoodsActivity.this).load("file://" + path1).into(iv_1);
//                    ImageLoader.getInstance().displayImage(path1, iv_1);
                    break;
                case 2:
                    path2 = FileUtils.IMAGE_PATH + "/" + fileNameGoods2;
                    Picasso.with(UploadGoodsActivity.this).load("file://" + path2).into(iv_2);
//                    ImageLoader.getInstance().displayImage(path2, iv_2);
                    break;
                case 3:
                    path3 = FileUtils.IMAGE_PATH + "/" + fileNameGoods3;
                    Picasso.with(UploadGoodsActivity.this).load("file://" + path3).into(iv_3);
//                    ImageLoader.getInstance().displayImage(path3, iv_3);
                    break;
            }
        }
    }

    /**
     * 提交货物图片数据
     */
    private void requestSubmitGoodsPicture() {
        showAlertDialog("正在提交货物信息...");
        DriverOrderService.pickUp(context, orderBean.getOrder_small_id(), "", new File(path1), new File(path2), new File(path3), new TextHttpResCallback(new OnHttpCallbackListener() {
            @Override
            public void onHttpSuccess(String result, String msg) {
                dismissAlertDialog();
                ToastUtil.showShortToast(context, "上传成功");
                finish();
            }

            @Override
            public void onHttpFailure(int status, String msg) {
                dismissAlertDialog();
                ToastUtil.showShortToast(context, msg);
            }

            @Override
            public void onHttpError(String errorMsg) {
                dismissAlertDialog();
                ToastUtil.showShortToast(context, errorMsg);
            }
        }));
    }
}