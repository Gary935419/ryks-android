package com.inwhoop.qscx.qscxsj.activitys.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.base.BaseActivity;

public class SettingSexActivity extends BaseActivity implements View.OnClickListener {
    private View layout_sex;
    private Button tv_man;
    private Button tv_woman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sex);
        layout_sex = findViewById(R.id.layout_sex);
        tv_man = findViewById(R.id.tv_man);
        tv_woman = findViewById(R.id.tv_woman);
        layout_sex.startAnimation(AnimationUtils.loadAnimation(context,
                R.anim.activity_translate_in));
        tv_man.setOnClickListener(this);
        tv_woman.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_man:
                layout_sex.startAnimation(AnimationUtils.loadAnimation(context,
                        R.anim.activity_translate_in));
                Intent intent = new Intent();
                intent.putExtra("sex", true);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.tv_woman:
                layout_sex.startAnimation(AnimationUtils.loadAnimation(context,
                        R.anim.activity_translate_in));
                Intent intent1 = new Intent();
                intent1.putExtra("sex", false);
                setResult(RESULT_OK, intent1);
                finish();
                break;
            case R.id.layout_sex:
                finish();
                break;
            default:
                break;
        }
    }
}
