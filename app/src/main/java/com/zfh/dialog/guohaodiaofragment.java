package com.zfh.dialog;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.guhao.R;
import com.zfh.Httputill.httputill;
import com.zfh.Po.Yuyue;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018\3\20 0020.
 */

public class guohaodiaofragment extends DialogFragment implements View.OnClickListener {
    private Yuyue yuyue;
    private Handler handler;
    private String result;
    private int postion;
    private ListView listView;
    private call call;

    public void setCall(guohaodiaofragment.call call) {
        this.call = call;
    }

    public guohaodiaofragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        yuyue = (Yuyue) getArguments().getSerializable("shuju");
        postion = (int) getArguments().getInt("position");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.guohaodialog, null);

        TextView shanchu = (TextView) view.findViewById(R.id.guohaodiashanchu);
        TextView quxiao = (TextView) view.findViewById(R.id.guohaodiaquxiao);

        shanchu.setOnClickListener(this);
        quxiao.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.guohaodiashanchu:
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        String path = getActivity().getResources().getString(R.string.requestremoveyuyue);
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("yuyuema", yuyue.getYuyuema());
                            result = httputill.lianjie(path, jsonObject.toString());
                            System.out.println("fasongde " + result);
//                             Message message=new Message();
//                            message.obj=result;
//                            handler.sendMessage(message);
                            if (result.equals("sucess")) {
//                                            call.remove(postion,true);
                                setResult(2, postion);
                            } else {
//                                            call.remove(postion,false);
                                setResult(3, postion);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                       getActivity().runOnUiThread(new Runnable() {
//                           @Override
//                           public void run() {
//                               Looper.prepare();
//                                handler =new Handler(){
//                                   @Override
//                                   public void handleMessage(Message msg) {
//                                       super.handleMessage(msg);
//                                       String result=(String)msg.obj;
//                                       if (result.equals("sucess")){
//                                           Toast.makeText(getActivity(),"预约已取消",Toast.LENGTH_SHORT).show();
//                                       }else {
//                                           Toast.makeText(getActivity(),"取消失败，请联系管理员",Toast.LENGTH_SHORT).show();
//                                       }
//                                   }
//
//                               };
//                               Looper.loop();
//                               if (result.equals("sucess")){
//                                          Toast.makeText(getActivity(),"预约已取消",Toast.LENGTH_SHORT).show();
//
//                                      }else {
//                                           Toast.makeText(getActivity(),"取消失败，请联系管理员",Toast.LENGTH_SHORT).show();
//                                      }
//                           }
//                       });
                    }
                }).start();
                guohaodiaofragment.this.dismiss();
                break;
            case R.id.guohaodiaquxiao:
                guohaodiaofragment.this.dismiss();
                break;
        }
    }
//    @Override
//    public void onStart() {
//        Window win = getDialog().getWindow();
//        // 一定要设置Background，如果不设置，window属性设置无效
//        win.setBackgroundDrawable( new ColorDrawable(getResources().getColor(R.color.colorAccent)));
//        getDialog().getWindow().setGravity(Gravity.BOTTOM);
//        // 没有背景颜色
//        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        WindowManager.LayoutParams layoutParams=getDialog().getWindow().getAttributes();
//        layoutParams.alpha=1.0;
////        后面的背景不会变暗
//        layoutParams.dimAmount =0;
//        layoutParams.height=350;
//        layoutParams.width=WindowManager.LayoutParams.MATCH_PARENT;
//        win.setAttributes(layoutParams);
//        super.onStart();
//    }

    public void setResult(int result_ma, int postion) {
        Log.d("setResult方法调用", "s");
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra("position", postion);
        getTargetFragment().onActivityResult(1, result_ma, intent);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof call) {
            call = (com.zfh.dialog.guohaodiaofragment.call) context;
            Log.d("onAttach", "运行了");

        }
    }

    @Override
    public void onStart() {
        Window window = getDialog().getWindow();
        window.setBackgroundDrawableResource(R.drawable.dialog);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.dimAmount = 0;
        window.setAttributes(layoutParams);
        super.onStart();
    }

    public interface call {
        void remove(int position, Boolean xianshi);
    }
}
