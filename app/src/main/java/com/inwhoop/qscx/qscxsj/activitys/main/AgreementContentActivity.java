package com.inwhoop.qscx.qscxsj.activitys.main;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.base.BaseFragmentActivity;
import com.inwhoop.qscx.qscxsj.entitys.AgreementBean;

public class AgreementContentActivity extends BaseFragmentActivity {

    private TextView tv_content;
    private AgreementBean mAgreementBean;

    public static void startIntent(Context context, AgreementBean bean) {
        Intent intent = new Intent(context, AgreementContentActivity.class);
        intent.putExtra("agreement", bean);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement_content);
        initView();
    }

    private void initView() {
        mAgreementBean = (AgreementBean) getIntent().getSerializableExtra("agreement");
        TextView tv_title_main = findViewById(R.id.tv_title_main);
        ImageView iv_title_left = findViewById(R.id.iv_title_left);
        iv_title_left.setVisibility(View.VISIBLE);
        iv_title_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_content = findViewById(R.id.tv_content);
        tv_title_main.setText(mAgreementBean.getTitle());
        tv_content.setText(mAgreementBean.getContent());
    }
}