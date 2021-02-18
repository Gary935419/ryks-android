package com.inwhoop.qscx.qscxsj.activitys.main;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.base.BaseActivity;
import com.inwhoop.qscx.qscxsj.utils.ToastUtil;

public class InputActivity extends BaseActivity {

    public static final int REQUEST_CODE = 2222;

    private EditText et_content;
    private ImageView iv_title_left;
    private TextView tv_title_main;
    private Button btn_submit;

    public static void startIntentForResult(Activity activity) {
        Intent intent = new Intent(activity, InputActivity.class);
        activity.startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        initView();
        initData();
    }

    private void initView() {
        et_content = findViewById(R.id.et_content);
        iv_title_left = findViewById(R.id.iv_title_left);
        tv_title_main = findViewById(R.id.tv_title_main);
        btn_submit = findViewById(R.id.btn_submit);
        iv_title_left.setVisibility(View.VISIBLE);
    }

    private void initData() {
        tv_title_main.setText("请输入邀请人码");
        iv_title_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = et_content.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    ToastUtil.showShortToast(InputActivity.this, "请输入邀请人码");
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("result", content);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}