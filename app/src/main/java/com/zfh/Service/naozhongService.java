package com.zfh.Service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.IntDef;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.administrator.guhao.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zfh.Activity.MainActivity;
import com.zfh.Activity.Myapplication;
import com.zfh.Httputill.httputill;
import com.zfh.Po.Yuyue;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

public class naozhongService extends Service {
    private Caozuo caozuo;
    private String result;

    public naozhongService() {
    }

    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 10:
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<Yuyue>>() {
                    }.getType();
                    List<Yuyue> yuyueList = gson.fromJson((String) msg.obj, type);

                    Intent intent = new Intent();
                    intent.putExtra("biaoshi", "notify");
                    intent.setAction("notify");
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, intent, FLAG_UPDATE_CURRENT);
                    RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notify);
                    remoteViews.setOnClickPendingIntent(R.id.notify_shijian, pendingIntent);
                    for (Yuyue yu : yuyueList) {
                        System.out.println("xiaoyuliangxiaoshi" + (xiaoyuliangxiaoshi(yu.getTime())) + yu.getTime());
                        if (xiaoyuliangxiaoshi(yu.getTime())) {
                            remoteViews.setTextViewText(R.id.notify_jieguo, "有");
                            remoteViews.setTextViewText(R.id.notify_shijian, yu.getTime());
                            break;
                        } else {
                            remoteViews.setTextViewText(R.id.notify_jieguo, "无");
                            remoteViews.setTextViewText(R.id.notify_shijian, null);
                        }
                    }

                    Notification notification = new NotificationCompat.Builder(getApplicationContext(),"1").setSmallIcon(getApplicationContext().getApplicationInfo().icon)
                            .setWhen(System.currentTimeMillis())
                            .setAutoCancel(true).setContentText("neirong")
                            .setContentTitle("biaoti")
                            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.aa2014))
                            .setDefaults(Notification.DEFAULT_ALL)
                            .setContent(remoteViews)
                            .setContentIntent(pendingIntent)
                            .build();
                    startForeground(1, notification);

            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String user = Myapplication.getMapshuju().get("personid").toString();
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("personid", user);
                    String path = getString(R.string.requestyuyue);
                    result = httputill.lianjie(path, jsonObject.toString());
                    if (!result.equals("kong")) {
                        System.out.println("result" + result);
                        Message message = Message.obtain();
                        message.obj = result;
                        message.what = 10;
                        handler.sendMessage(message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public boolean xiaoyuliangxiaoshi(String time) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String wanzhen = "2018-" + time + ":00";
            Date yuyuetime = simpleDateFormat.parse(wanzhen);
            long shicha = yuyuetime.getTime() - System.currentTimeMillis();
            System.out.println("now time"+simpleDateFormat.format(System.currentTimeMillis()));
            if (shicha > 0) {
                int hours = (int) shicha / (1000 * 60 * 60);
                System.out.println("hours" + hours);
                if (hours < 2)
                    return true;
                else
                    return false;
            } else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

    }


    @Override
    public void onDestroy() {
        System.out.println("onDestroy");
        super.onDestroy();
        stopForeground(true);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return caozuo;
    }

    class Caozuo extends Binder {
        public void test() {
            Log.d("后台服务", "运行了  test方法");
        }
    }
}
