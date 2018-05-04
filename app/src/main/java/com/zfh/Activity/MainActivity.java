package com.zfh.Activity;

import android.app.Application;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import com.jzxiang.pickerview.TimePickerDialog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenuItemView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.guhao.R;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.zfh.Fragment.keshiAdapter;
import com.zfh.Fragment.keshiFragment;
import com.zfh.Fragment.guohaoFragment;
import com.zfh.Fragment.favFragment;
import com.zfh.Fragment.myFragment;
import com.zfh.Fragment.paizhenFragment;
import com.zfh.Httputill.httputill;
import com.zfh.Po.Time;
import com.zfh.Service.naozhongService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements paizhenFragment.OnFragmentInteractionListener {
    private keshiFragment keshiFragment;
    private guohaoFragment guohaoFragment;
    private favFragment favFragment;
    private myFragment myFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
   private TextView keshi;
    private TextView guahao;
    private TextView fav;
    private TextView my;
    private DrawerLayout drawer;
    private int level;
    private Notifybroadcast notifybroadcast;
    private long olddianji=0;

    public void setLevel(int level) {
        this.level = level;
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case 0: String text=(String)msg.obj;
                    Toast.makeText(MainActivity.this,text,Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maindrawer);
        //注册内部类广播
          notifybroadcast=new Notifybroadcast();
        IntentFilter  intentFilter=new IntentFilter("notify");
        registerReceiver(notifybroadcast,intentFilter);
        //开启前台服务
        Intent intent=new Intent(this,naozhongService.class);
        startService(intent);
//        Toolbar toolbar=(Toolbar)findViewById(R.id.maintoolbar);
//        setSupportActionBar(toolbar);
//        DrawerLayout drawerLayout=(DrawerLayout)findViewById(R.id.mainDrawer);
//        ActionBarDrawerToggle drawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.bottom_sheet_behavior);
//        drawerLayout.addDrawerListener(drawerToggle);
//        drawerToggle.syncState();
        drawer = (DrawerLayout) findViewById(R.id.mainDrawer);
//        drawer.setScrimColor(Color.TRANSPARENT);
        drawer.setDrawerShadow(R.drawable.shaw, GravityCompat.END);
        fragmentManager=getFragmentManager();
        keshi=(TextView)findViewById(R.id.keshi);
        guahao=(TextView)findViewById(R.id.guahao);
        fav=(TextView)findViewById(R.id.fav);
        my=(TextView)findViewById(R.id.my);
        //左边的菜单选项点击事件
        NavigationView navigationView=(NavigationView)findViewById(R.id.nav_view) ;
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.xiugaixinxin:Toast.makeText(MainActivity.this,"点击了修改信息",Toast.LENGTH_LONG).show();
                                               break;
                    case R.id.exit:Toast.makeText(MainActivity.this,"点击了退出",Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(MainActivity.this,naozhongService.class);
                        stopService(intent);
                        finish();
                        System.exit(0);
                        break;
                }
                return true;
            }
        });
        Myapplication myapplication=(Myapplication)getApplication();
        String aa=myapplication.getTest();
        Map map=Myapplication.getMapshuju();
        System.out.println("静态方法测试"+aa+"map shuju:::"+map.get("tt"));

        keshi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction=fragmentManager.beginTransaction();
                hidefragments();
                if (keshiFragment==null){
                    keshiFragment=new keshiFragment();
                    transaction.add(R.id.content,keshiFragment).commit();

                }else {
                  showfragment(keshiFragment);
                }
//                if (first!=0){
//                    lastfragment=currentfragment;
//                    currentfragment=keshiFragment;
//                }else {
//                    currentfragment=keshiFragment;
//                }
                Toast.makeText(MainActivity.this,"dianji",Toast.LENGTH_LONG).show();
                allselec(keshi);
            }
        });
        guahao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                transaction=fragmentManager.beginTransaction();

                hidefragments();
                if (guohaoFragment==null){
                    guohaoFragment=new guohaoFragment();
                    transaction.add(R.id.content,guohaoFragment,"guohaofragment").commit();
                }else {
                    showfragment(guohaoFragment);
                }
                allselec(guahao);
            }
        });
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction=fragmentManager.beginTransaction();
                hidefragments();
                if (favFragment==null){
                    favFragment=new favFragment();
                    transaction.add(R.id.content,favFragment).commit();
                }else {
                    showfragment(favFragment);
                }
                allselec(fav);
            }
        });
        my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction=fragmentManager.beginTransaction();
                hidefragments();
                if (myFragment==null){
                   myFragment=new myFragment();
                    transaction.add(R.id.content,myFragment).commit();

                }else {
                   showfragment(myFragment);
                }
                allselec(my);
            }

        });
        keshi.performClick();
    }
   public void allselec(TextView textView){
       keshi.setSelected(false);
       guahao.setSelected(false);
       fav.setSelected(false);
       my.setSelected(false);
       textView.setSelected(true);
//       transaction.show(fragment);
//       transaction.commit();
   }
   public void hidefragments(){

       if ( Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
           List<Fragment> fragments=fragmentManager.getFragments();
           for (Fragment fragment:fragments){
               transaction.hide(fragment);
           }
       }else {
           if (keshiFragment!=null) transaction.hide(keshiFragment);
           if (guohaoFragment!=null)transaction.hide(guohaoFragment);
           if (favFragment!=null)transaction.hide(favFragment);
           if (myFragment!=null)transaction.hide(myFragment);
           if (fragmentManager.findFragmentByTag("paizhenFragment")!=null)transaction.hide(fragmentManager.findFragmentByTag("paizhenFragment"));
       }


   }
   public void showfragment(Fragment fragment){
              transaction.show(fragment);
              transaction.commit();
   }


    @Override
    public void onFragmentInteraction(Map<String,Long> map, final String employeeid) {
                TimePickerDialog timePickerDialog=new TimePickerDialog.Builder()
                .setCallBack(new OnDateSetListener() {
                    @Override
                    public void onDateSet(TimePickerDialog timePickerView, final long millseconds) {
                        final Timestamp timestamp=new Timestamp(millseconds);
                        System.out.println("选择的时间"+timestamp.toString());
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                String path=getResources().getString(R.string.requestaddyuyue);
                                JSONObject jsonObject=new JSONObject();
                                try {
                                    Map map=Myapplication.getMapshuju();
                                    jsonObject.put("employeeid",employeeid);
                                    jsonObject.put("time",timestamp.toString());
                                    jsonObject.put("userid",map.get("personid"));
                                    String result=httputill.lianjie(path,jsonObject.toString());
                                    System.out.println("timePickerDialog的返回"+result);
                                    JSONObject jsonObject1=new JSONObject(result);
                                    Message message=Message.obtain();
                                    message.what=0;
                                    if (jsonObject1.getBoolean("success")){
                                       String car="添加预约成功";
                                       message.obj=car;
                                        handler.sendMessage(message);
                                    }else {
                                        if (jsonObject1.getBoolean("cunzai")){
                                            String car="所选择的日期已有预约，请返回";
                                            message.obj=car;
                                            handler.sendMessage(message);
                                        }else {
                                            String car="添加预约失败";
                                            message.obj=car;
                                            handler.sendMessage(message);
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }).start();

                    }
                }).setTitleStringId("选择预约时间").setWheelItemTextSize(20).setType(Type.HOURS_MINS).setCancelStringId("取消").setSureStringId("确定")
                .setHourText("时").setMinuteText("分").setMinMillseconds(map.get("min")).setMaxMillseconds(map.get("max")).setCyclic(false).build();
                  timePickerDialog.show(getSupportFragmentManager(), "HOURS_MINS");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.leftmenu,menu);
        return true;
    }


    @Override
    public void invalidateOptionsMenu() {
        super.invalidateOptionsMenu();
    }


    @Override
    public void onBackPressed() {
        long nowdianji=System.currentTimeMillis();
        if (nowdianji-olddianji<2000){
            Intent intent=new Intent(this,naozhongService.class);
            stopService(intent);
            finish();
            System.exit(0);
        }else {
            Toast.makeText(MainActivity.this,"再次点击将退出应用",Toast.LENGTH_SHORT).show();
            olddianji=nowdianji;
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                if (level==1){
//                    finish();
                }else {
                    super.onBackPressed();
                }
            }
        }



    }

    //内部广播 用于 接受notify 发送来的  把挂号fragment 显示
   class Notifybroadcast extends BroadcastReceiver{
       @Override
       public void onReceive(Context context, Intent intent) {
           System.out.println("Notifybroadcast 收到广播");
           if (intent.getStringExtra("biaoshi").equals("notify")){
               hidefragments();
               guahao.performClick();
           }

       }
   }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(notifybroadcast);

    }
}
