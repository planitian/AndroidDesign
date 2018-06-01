package com.zfh.Fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.guhao.R;
import com.zfh.Activity.Myapplication;
import com.zfh.Httputill.httputill;
import com.zfh.Po.Employee;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2018\2\28 0028.
 */

public class keshiAdapter extends BaseAdapter {
private List<Employee> employeeList;
    private Context context;
    private httputill httputill;
    private String xadizhi="";
    private  call call;
    private int n=0;
    public keshiAdapter(Context context,List<Employee> employeeList,call call){
        this.employeeList=employeeList;
        this.context=context;
        this.call=call;

    }





//    Handler handler1=new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//             Bundle bundle=msg.getData();
//            String gg=(String)msg.obj;
//            if (bundle.getBoolean("success")){
//                String shuchu=bundle.getString("name")+"已经添加到了收藏列表";
//                Toast.makeText(context,shuchu,Toast.LENGTH_LONG).show();
//            }
//        }
//    };



    @Override
    public int getCount() {
        return employeeList.size();

    }

    @Override
    public Object getItem(int position) {
        return employeeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
             ViewHolder viewHolder=null;
        final Employee employee=employeeList.get(position);
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView=LayoutInflater.from(context).inflate(R.layout.keshiitem,parent,false);
             viewHolder.touxiang=(ImageView)convertView.findViewById(R.id.image);
            viewHolder.name=(TextView)convertView.findViewById(R.id.empname);
            viewHolder.jianjie=(TextView)convertView.findViewById(R.id.jianjie);
            viewHolder.xiai=(ImageButton)convertView.findViewById(R.id.shoucang);
//            viewHolder.xiai.setTag(n);
           convertView.setTag(viewHolder);
        }else {
           viewHolder=(ViewHolder)convertView.getTag();
//            int guodu=(int)viewHolder.xiai.getTag();
            Log.d("getView","调用了");
//
//            if (guodu!=position){
//                System.out.println("tag"+guodu);
//                System.out.println("position"+position);
//                viewHolder.xiai.setBackgroundResource(R.drawable.tab_fac_normal);
//                Log.d("重新设置","调用了");
//            }
        }

        //viewHolder.touxiang.setImageBitmap(base64toBitmap(employee.getTouxiang()));
        viewHolder.name.setText(employee.getName());
        viewHolder.jianjie.setText(employee.getJianjie());
        if (employee.getFavour()){
            viewHolder.xiai.setBackgroundResource(R.drawable.tab_fac_normal);
        }else{
            viewHolder.xiai.setBackgroundResource(R.drawable.tab_fav_pre);
        }

        viewHolder.touxiang.setImageBitmap(base64toBitmap(employee.getTouxiang()));
        viewHolder.xiai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        JSONObject jsonObject=new JSONObject();
                        try {

                            jsonObject.put("employeeid",employee.getEmployeeid());
                            jsonObject.put("fav",employee.getFavour());
//                            String resultxi=httputill.utill(xadizhi,jsonObject.toString());

                            Log.d("点击事件","这里进行");
                            call.change(position,jsonObject);
//                            if (resultxi.contains("true")){


//                                Bundle bundle=new Bundle();
//                                bundle.putBoolean("success",true);
//                                bundle.putString("name",employee.getName());
//                                Message message=Message.obtain();
//                                message.setData(bundle);
//                                String hh="dsda";
//                                message.obj=hh;
//                                handler.sendMessage(message);

//                            }else {
//                                Bundle bundle=new Bundle();
//                                bundle.putBoolean("success",false);
//                                Message message=Message.obtain();
//                                handler.sendMessage(message);


//                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }.start();
            }
        });
         n++;
        return convertView;


    }
    class ViewHolder{
       ImageView touxiang;
        TextView name;
        TextView jianjie;
        ImageButton xiai;
    }
    public Bitmap base64toBitmap(String base64){
        byte[] bytes= Base64.decode(base64,Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
    }

   public interface call{
       void change(int position,JSONObject jsonObject );
   }
}

