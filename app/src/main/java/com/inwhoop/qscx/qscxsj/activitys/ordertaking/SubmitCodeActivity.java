package com.inwhoop.qscx.qscxsj.activitys.ordertaking;

import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.base.BaseActivity;
import com.inwhoop.qscx.qscxsj.app.AsyncHttpHandler;
import com.inwhoop.qscx.qscxsj.business.DriverOrderService;
import com.inwhoop.qscx.qscxsj.business.DriverPickService;
import com.inwhoop.qscx.qscxsj.constants.EventBusMsg;
import com.inwhoop.qscx.qscxsj.utils.LogUtils;
import com.inwhoop.qscx.qscxsj.utils.LoginUserInfoUtil;
import com.inwhoop.qscx.qscxsj.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;

import java.io.File;
import java.io.IOException;

import cz.msebera.android.httpclient.Header;

public class SubmitCodeActivity extends BaseActivity implements View.OnClickListener {
    private TextView title_center_text;
    private ImageView title_back_img;
    private EditText activity_code;
    //    private ImageView iv_submit_code;
    private Button btn_submit;
    private String orderId;
    private File goods_image;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_submit_code);
        orderId = getIntent().getStringExtra("orderId");
        initView();

//        initImagePicker();
    }

    private void initView() {
        title_center_text = findViewById(R.id.tv_title_main);
        title_center_text.setText("请输入验货码");
        title_back_img = findViewById(R.id.iv_title_left);
        title_back_img.setVisibility(View.VISIBLE);
        title_back_img.setOnClickListener(this);
        activity_code = findViewById(R.id.activity_code);
//        iv_submit_code = findViewById(R.id.iv_submit_code);
//        iv_submit_code.setOnClickListener(this);
        btn_submit = findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
    }

//    private void initImagePicker() {
//        imagePicker = ImagePicker.getInstance();
//        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
//        imagePicker.setShowCamera(true);  //显示拍照按钮
//        imagePicker.setCrop(false);        //允许裁剪（单选才有效）
//        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
//        imagePicker.setMultiMode(false);
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_left:
                finish();
                break;
            case R.id.btn_submit:
                String code = activity_code.getEditableText().toString();
                if (TextUtils.isEmpty(code)) {
                    ToastUtil.showShortToast(context, "请输入提货码");
                    return;
                }

//                if (goods_image == null) {
//                    ToastUtil.showShortToast(this, "请选择提货物品照片");
//                    return;
//                }
                doSubmit(code);
                break;
//            case R.id.iv_submit_code:
//                Intent intentCode = new Intent(context, ImageGridActivity.class);
//                startActivityForResult(intentCode, 1);
//                break;
            default:
                break;
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
//            if (data != null && requestCode == 1) {
//                final ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
//                showProgressDialog("加载中，请稍后");
//                String path = images.get(0).path;
//                if ("".equals(path)) {
//                    ToastUtil.showShortToast(context, "添加失败，请重试");
//                    if (myDialog != null) {
//                        myDialog.dismiss();
//                    }
//                    return;
//                }
//
//                try {
//                    goods_image = PicUtil.zipImage(path);
//                } catch (NullPointerException nullPointerException) {
//                    goods_image = PicUtil.zipImage("file://" + path);
//                }
//
//                if (path.startsWith("file://")) {
//                    ImageLoader.getInstance().displayImage(path, iv_submit_code);
//                } else {
//                    ImageLoader.getInstance().displayImage("file://" + path, iv_submit_code);
//                }
//                handler.sendEmptyMessage(0);
//
//            } else {
//                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            if (myDialog != null) {
//                myDialog.dismiss();
//            }
//        }
//    };

    private void doSubmit(String code) {
//        LogUtils.d(" post doSubmit" + goods_image.getPath());
        showProgressDialog("上传中...");
        DriverOrderService.checkCode(context, orderId, code,  new AsyncHttpHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                dismissProgressDialog();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(responseString);
                dismissProgressDialog();
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(responseString);
                    if (jsonObject.optInt("status") == 200) {
                        ToastUtil.showLongToast(context, "订单已完成，即将查询其它进行中订单");
                        EventBus.getDefault().post("", EventBusMsg.EVENT_UPLOAD_CODE);
//                        orderOk();
                        finish();
                    } else {
                        ToastUtil.showShortToast(context, jsonObject.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void orderOk() {

        String taker_type_id = LoginUserInfoUtil.getDriverPickBasicBean(this).getTaker_type_id();
        String order_id = LoginUserInfoUtil.getDriverPickBasicBean(this).getOrder_id();

        DriverPickService.small_ok(this, taker_type_id, order_id, new AsyncHttpHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(responseString);
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(responseString);
                    ToastUtil.showShortToast(SubmitCodeActivity.this, jsonObject.optString("msg"));

//                    ToastUtil.showShortToast(context, "请退出导航并提交取货码");
                    playMedia("order_finish.mp3");
                    LogUtils.playMusic("order_finish.mp3");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void playMedia(final String fileName) {
        LogUtils.playMusic("+++++++" + fileName);
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            //保险起见，设置报错监听
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {

                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    mediaPlayer.reset();
                    return false;
                }
            });
        } else {
            mediaPlayer.reset();//恢复
        }
        try {
            AssetFileDescriptor as = getAssets().openFd(fileName);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            if (as != null) { // 存在缓存文件
                mediaPlayer.setDataSource(as.getFileDescriptor(), as.getStartOffset(), as.getLength());
                mediaPlayer.prepare();
                mediaPlayer.start();
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            mediaPlayer.release();
        }
    }
}
