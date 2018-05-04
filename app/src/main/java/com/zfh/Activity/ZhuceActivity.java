package com.zfh.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.administrator.guhao.R;
import com.zfh.Httputill.httputill;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

public class ZhuceActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText sfz;
    private EditText mima;
    private EditText phohe;
    private EditText address;
    private EditText name;
    private RadioButton nan;
    private RadioButton nv;
    private Button reset;
    private Button zhuce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuce);
        Toolbar toolbar = (Toolbar) findViewById(R.id.zhuce_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        init();
        reset.setOnClickListener(this);
        zhuce.setOnClickListener(this);
        Intent intent = new Intent();
        intent.putExtra("test", "我是返回的数据");
        setResult(RESULT_OK, intent);
    }

    public void init() {
        sfz = (EditText) findViewById(R.id.zhuce_sfz);
        mima = (EditText) findViewById(R.id.zhuce_mima);
        phohe = (EditText) findViewById(R.id.zhuce_phone);
        address = (EditText) findViewById(R.id.zhuce_address);
        name = (EditText) findViewById(R.id.zhuce_name);
        nan = (RadioButton) findViewById(R.id.zhuce_nan);
        nv = (RadioButton) findViewById(R.id.zhuce_nv);
        reset = (Button) findViewById(R.id.zhuce_reset);
        zhuce = (Button) findViewById(R.id.zhuce_zhuce);
    }

    public void reset() {
        if (!sfz.getText().equals("")) {
            sfz.setText(null);
        }
        if (!mima.getText().equals("")) {
            mima.setText(null);
        }
        if (!phohe.getText().equals("")) {
            phohe.setText(null);
        }
        if (!address.getText().equals("")) {
            address.setText(null);
        }
        if (!name.getText().equals("")) {
            name.setText(null);
        }
        nan.setChecked(true);
        nv.setChecked(false);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.zhuce_reset:
                reset();
                System.out.println("sfz" + sfz.getText().toString());
                break;
            case R.id.zhuce_zhuce:
                final String path = getString(R.string.requestadduser);
                final JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("sfz", sfz.getText().toString().trim());
                    jsonObject.put("mima", mima.getText().toString().trim());
                    jsonObject.put("phone", phohe.getText().toString().trim());
                    jsonObject.put("address", address.getText().toString().trim());
                    jsonObject.put("name", name.getText().toString().trim());
                    if (nan.isChecked()) {
                        jsonObject.put("sex", "男");
                    } else {
                        jsonObject.put("sex", "女");
                    }
                    EventBus.getDefault().post("Event");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            final String result = httputill.lianjie(path, jsonObject.toString());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (result.equals("success")) {
                                        Toast.makeText(ZhuceActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                        Toast.makeText(ZhuceActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }).start();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void change(String result) {
        zhuce.setText(result);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
