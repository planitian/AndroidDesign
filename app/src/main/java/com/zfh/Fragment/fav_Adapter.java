package com.zfh.Fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.guhao.R;
import com.zfh.Activity.Myapplication;
import com.zfh.Httputill.httputill;
import com.zfh.Po.Favourite;
import com.zfh.dingyiview.Myrecy;
import com.zfh.dingyiview.itembuju;
import com.zfh.dingyiview.yuanxing;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2018\3\27 0027.
 */

public class fav_Adapter extends RecyclerView.Adapter {
    private List<Favourite> favourites;
    private Context context;
    private callback callback;
    private Myrecy myrecy;
    public fav_Adapter(Context context, List<Favourite> favourites, Myrecy myrecy) {
        super();
        this.context=context;
        this.favourites=favourites;
        this.callback=(com.zfh.Fragment.fav_Adapter.callback)myrecy;
        this.myrecy=myrecy;
    }
    //用于向Myrecy 返回它所要求的employeeid号
    public String getemployeeid(int position){
        if (position<0){
            throw new ArrayIndexOutOfBoundsException("fac_adapert  postion chucuo ");
        }
        return favourites.get(position).getEmployeeid();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itembuju view= (itembuju) LayoutInflater.from(context).inflate(R.layout.fav_list_item,parent,false);
        view.setCall(myrecy);
        Favviewholder favviewholder=new Favviewholder(view);

        return favviewholder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
          Favviewholder favviewholder=(Favviewholder)holder;
         favviewholder.touxiang.setImageBitmap(base64toBitmap(favourites.get(position).getEmploimage()));
        favviewholder.keshi.setText(favourites.get(position).getEmployeeid()+position+getemployeeid(position));
        favviewholder.employee.setText(favourites.get(position).getEmployeename());
        favviewholder.quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Handler handler=new Handler(Looper.getMainLooper()){
                            @Override
                            public void handleMessage(Message msg) {
                                super.handleMessage(msg);
                                Toast.makeText(context,"取消收藏",Toast.LENGTH_SHORT).show();
                                favourites.remove(position);
                                callback.setlastposition(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position,favourites.size()-position);
                            }
                        };
                            String empl=favourites.get(position).getEmployeeid();
                            String userid= (String) Myapplication.getMapshuju().get("personid");
                        JSONObject jsonObject=new JSONObject();
                        try {
                            jsonObject.put("userid",userid);
                            jsonObject.put("employeeid",empl);
                            String path=context.getResources().getString(R.string.requestremovefav);
                            String result= httputill.lianjie(path,jsonObject.toString());
                            System.out.println("fav_Adapter 的返回结果"+result);
                            if (result.equals("sucess")){
                                handler.sendEmptyMessage(1);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();
            }
        });
    }

    @Override
    public int getItemCount() {
        return favourites.size();
    }


    class Favviewholder extends RecyclerView.ViewHolder{
        private yuanxing touxiang;
        private TextView keshi;
        private TextView employee;
        private Button quxiao;

        public Favviewholder(View itemView) {
            super(itemView);
            touxiang=(yuanxing)itemView.findViewById(R.id.fav_list_touxiang);
            keshi=(TextView)itemView.findViewById(R.id.fav_list_keshi);
            employee=(TextView)itemView.findViewById(R.id.fav_list_employee);
            quxiao=(Button) itemView.findViewById(R.id.fav_list_quxiao);

        }
    }
    public Bitmap base64toBitmap(String base64){
        byte[] bytes= Base64.decode(base64,Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
    }
   public interface callback{
        public  void setlastposition(int position);
    }
}
