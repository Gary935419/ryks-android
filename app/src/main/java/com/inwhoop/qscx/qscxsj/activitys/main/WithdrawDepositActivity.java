package com.inwhoop.qscx.qscxsj.activitys.main;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.base.BaseActivity;
import com.inwhoop.qscx.qscxsj.app.AsyncHttpHandler;
import com.inwhoop.qscx.qscxsj.business.UserBalanceService;
import com.inwhoop.qscx.qscxsj.utils.LoginUserInfoUtil;
import com.inwhoop.qscx.qscxsj.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class WithdrawDepositActivity extends BaseActivity implements View.OnClickListener {

    private TextView title_center_text;
    private ImageView title_back_img;

    private EditText activity_withdraw_deposit_bank;
    private EditText activity_withdraw_deposit_name;
    private EditText activity_withdraw_deposit_card_num;
    private EditText activity_withdraw_deposit_fee;

    private Button activity_withdraw_deposit_ok;

    private String open_bank;
    private String open_name;
    private String card_number;
    private String money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_deposit);

        initView();
        initListener();
    }

    private void initView() {
        title_center_text = (TextView) findViewById(R.id.tv_title_main);
        title_center_text.setText("提现");
        title_center_text.setVisibility(View.VISIBLE);
        title_back_img = (ImageView) findViewById(R.id.iv_title_left);
        title_back_img.setVisibility(View.VISIBLE);

        activity_withdraw_deposit_bank = (EditText) findViewById(R.id.activity_withdraw_deposit_bank);
        activity_withdraw_deposit_name = (EditText) findViewById(R.id.activity_withdraw_deposit_name);
        activity_withdraw_deposit_card_num = (EditText) findViewById(R.id.activity_withdraw_deposit_card_num);
        activity_withdraw_deposit_fee = (EditText) findViewById(R.id.activity_withdraw_deposit_fee);

        activity_withdraw_deposit_ok = (Button) findViewById(R.id.activity_withdraw_deposit_ok);
    }

    private void initListener() {
        title_back_img.setOnClickListener(this);
        activity_withdraw_deposit_ok.setOnClickListener(this);
    }

    private void takenMoney() {

        if (open_bank == null || open_bank.equals("")) {
            ToastUtil.showShortToast(this, "请填写开户行");
            return;
        }

        if (open_name == null || open_name.equals("")) {
            ToastUtil.showShortToast(this, "请填写姓名");
            return;
        }

        if (card_number == null || card_number.equals("")) {
            ToastUtil.showShortToast(this, "请填写卡号");
            return;
        }

        if (money == null || money.equals("")) {
            ToastUtil.showShortToast(this, "请填写金额");
            return;
        }

        String user_id = LoginUserInfoUtil.getLoginUserInfoBean(this).getId();

        UserBalanceService.postal(this, user_id, open_bank, open_name, card_number, money, new AsyncHttpHandler() {
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
                        ToastUtil.showShortToast(WithdrawDepositActivity.this, jsonObject.optString("msg"));
                        setResult(0x123);
                        WithdrawDepositActivity.this.finish();
                    } else {
                        ToastUtil.showShortToast(WithdrawDepositActivity.this, jsonObject.optString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_left:
                this.finish();
                break;
            case R.id.activity_withdraw_deposit_ok:
                open_bank = activity_withdraw_deposit_bank.getText().toString().trim();
                open_name = activity_withdraw_deposit_name.getText().toString().trim();
                card_number = activity_withdraw_deposit_card_num.getText().toString().trim();
                money = activity_withdraw_deposit_fee.getText().toString().trim();
                takenMoney();
                break;
        }
    }
}
