package com.zfh.Activity;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.administrator.guhao.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zfh.Httputill.httputill;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class loginActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private EditText username;
    private EditText password;
    private CheckBox jilu;
    private TextView wangji;
    private CheckBox xianshi;
    private TextView zhuce;
    private Button denglu;
    private httputill httputill = new httputill();

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle date = msg.getData();
            Toast.makeText(getApplicationContext(), date.getString("name"), Toast.LENGTH_LONG).show();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login);
        sharedPreferences = getSharedPreferences("userinfo", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        jilu = (CheckBox) findViewById(R.id.jilu);
        wangji = (TextView) findViewById(R.id.wangji);
        denglu = (Button) findViewById(R.id.denglu);
        xianshi = (CheckBox) findViewById(R.id.xianshi);
        zhuce = (TextView) findViewById(R.id.zhuce);
        //这是开发时候用来不输入密码直接进入的
//        Button jinru = (Button) findViewById(R.id.jinru);
//        jinru.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                HashMap map = new HashMap();
//                //      map.put("personid",json.getString("personid"));
//                map.put("personid", "001");
//                HashMap map1 = new HashMap();
//                map1.put("personid", "001");
//                Myapplication.setMapshuju(map1);
//                String gg = "001";
//                String aa = "撒大三大四的";
//                Myapplication.setGg(aa);
//                Myapplication myapplication = (Myapplication) getApplication();
//
//                myapplication.setTest(gg);
//                myapplication.setMap(map);
//                Intent intent = new Intent(loginActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });

        if (sharedPreferences.getBoolean("jilu", false)) {
            String usernam = sharedPreferences.getString("personid", null);
            String userpass = sharedPreferences.getString("password", null);
            username.setText(usernam);
            password.setText(userpass);
            jilu.setChecked(true);
        }
        denglu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                new Thread() {
                    @Override
                    public void run() {

                        try {
                            String userpan = username.getText().toString().trim();
                            String passpan = password.getText().toString().trim();
                            if (userpan.length() == 0 || userpan == null || passpan.length() == 0 || passpan == null) {
                                Bundle bundle = new Bundle();
                                bundle.putString("name", "请检查用户名和密码是否为空");
                                Message message = Message.obtain();
                                message.setData(bundle);
                                handler.sendMessage(message);
                                return;
                            }
                            //只适用于 get方式链接
                            //String params="username="+ URLEncoder.encode(username.toString(),"UTF-8")+"&password="+URLEncoder.encode(password.toString(),"UTF-8");
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("username", userpan);
                            jsonObject.put("password", passpan);

                            String path = getResources().getString(R.string.loginuser);
                            Log.d("path", path);
                            Log.d("path", jsonObject.toString());
                            String result = httputill.utill(path, jsonObject.toString());

                            if (result == "error") {
                                Bundle bundle = new Bundle();
                                bundle.putString("name", "服务器异常");
                                Message message = Message.obtain();
                                message.setData(bundle);
                                handler.sendMessage(message);
                            } else {
                                JSONObject json = new JSONObject(result);
                                Log.d("jieguo", json.getString("cunzai"));
                                if (json.getBoolean("cunzai")) {

                                    if (jilu.isChecked()) {
                                        editor.putString("username", json.getString("username"));
                                        editor.putString("personid", json.getString("personid"));
                                        editor.putString("password", json.getString("password"));
                                        editor.putBoolean("jilu", true);
                                        editor.commit();
                                    }
                                    Gson gson = new Gson();
                                    Type type = new TypeToken<Map<String, String>>() {
                                    }.getType();
                                    Map<String, String> map = new HashMap();
                                    map = gson.fromJson(result, type);
//                                    map.put("personid",json.getString("personid"));
//                                    map.put("personid","001");
//                                    map.put("name",json.getString("username"));
//                                    map.put("phone",json.getString("phone"));
//                                    map.put("address",json.getString("address"));
                                       Myapplication.setMapshuju(map);
                                    System.out.println(" Myapplication."+Myapplication.getMapshuju().toString());
                                    Intent intent = new Intent(loginActivity.this, MainActivity.class);
                                    intent.putExtra("name", json.getString("username"));
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Bundle bundle = new Bundle();
                                    bundle.putString("name", "密码错误");
                                    Message message = Message.obtain();
                                    message.setData(bundle);
                                    handler.sendMessage(message);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();

            }
        });

        xianshi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        jilu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!b) {
                    editor.putBoolean("jilu", false);
                    editor.commit();
                }
            }
        });

        wangji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //转到忘记密码页面
                Intent intent = new Intent(loginActivity.this, WangjiActivity.class);
                startActivity(intent);
            }
        });

        zhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //转到注册页面
                Intent intent = new Intent(loginActivity.this, ZhuceActivity.class);
                startActivityForResult(intent, 1);
                Toast.makeText(loginActivity.this, "点击了", Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (requestCode == RESULT_OK) {
                    String extra = data.getStringExtra("test");
                    Toast.makeText(loginActivity.this, extra, Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }
}
