package com.inwhoop.qscx.qscxsj.activitys.main;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.base.BaseActivity;
import com.inwhoop.qscx.qscxsj.adapters.AgreementAdapter;
import com.inwhoop.qscx.qscxsj.base.adapter.ViewHolder;
import com.inwhoop.qscx.qscxsj.base.adapter.interfaces.OnItemClickListener;
import com.inwhoop.qscx.qscxsj.business.GetBasicService;
import com.inwhoop.qscx.qscxsj.business.callback.OnHttpCallbackListener;
import com.inwhoop.qscx.qscxsj.business.callback.TextHttpResCallback;
import com.inwhoop.qscx.qscxsj.entitys.AgreementBean;
import com.inwhoop.qscx.qscxsj.utils.L;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AgreementActivity extends BaseActivity {

    private TextView title_center_text;
    private ImageView title_back_img;
    private RecyclerView lv_agreement;
    private AgreementAdapter adapter;
    private List<AgreementBean> agreementBeanList;
    private String agreement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_agreement);
        initView();
        initData();
    }

    private void initView() {
        title_center_text = findViewById(R.id.tv_title_main);
        title_back_img = findViewById(R.id.iv_title_left);
        title_back_img.setVisibility(View.VISIBLE);
        lv_agreement = findViewById(R.id.lv_agreement);
        title_center_text.setText("司机服务协议");
        title_back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lv_agreement.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AgreementAdapter(context, new ArrayList<AgreementBean>());
        adapter.setOnItemClickListener(new OnItemClickListener<AgreementBean>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, AgreementBean data, int position) {
                AgreementContentActivity.startIntent(AgreementActivity.this, data);
            }
        });
        lv_agreement.setAdapter(adapter);
    }

    private void initData() {
        getAgreementList();
    }

    private void getAgreementList() {
        GetBasicService.get_agreement(this, new TextHttpResCallback(new OnHttpCallbackListener() {
            @Override
            public void onHttpSuccess(String result, String msg) {
                L.i(TAG, "getAgreementList: " + result);
                agreementBeanList = new Gson().fromJson(result,
                        new TypeToken<List<AgreementBean>>() {
                        }.getType());
                adapter.setNewData(agreementBeanList);
            }

            @Override
            public void onHttpFailure(int status, String msg) {

            }

            @Override
            public void onHttpError(String errorMsg) {

            }
        }));
    }
}
