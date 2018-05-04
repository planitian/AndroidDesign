package com.zfh.Fragment;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.guhao.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zfh.Activity.MainActivity;
import com.zfh.Activity.Myapplication;
import com.zfh.Httputill.httputill;
import com.zfh.Po.Yuyue;
import com.zfh.dialog.guohaodiaofragment;
import com.zfh.dialog.*;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link guohaoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link guohaoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class guohaoFragment extends Fragment implements guohaodiaofragment.call {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
   private Boolean first=true;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ListView listView;
    private List<Yuyue> yuyueList;
    private guohaoAdapter base;
    private Context context;

    private OnFragmentInteractionListener mListener;

    public guohaoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment guohaoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static guohaoFragment newInstance(String param1, String param2) {
        guohaoFragment fragment = new guohaoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        first=false;
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
           switch (msg.what){
               case 1:yuyueList=(List<Yuyue>)msg.obj;
                   Toast.makeText(getActivity(),"zheliyunx",Toast.LENGTH_SHORT).show();
                    base=new guohaoAdapter(getActivity(),yuyueList);
                   listView.setAdapter(base);

                   listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                       @Override
                       public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                           Yuyue yuyue=(Yuyue)parent.getItemAtPosition(position);
//                           Toast.makeText(getActivity(),yuyue.getEmployee(),Toast.LENGTH_LONG).show();
                           Bundle bundle=new Bundle();
                           bundle.putSerializable("shuju",yuyue);
                           bundle.putInt("position",position);

                           guohaodiaofragment dia=new guohaodiaofragment();
                           dia.setArguments(bundle);
                           dia.setTargetFragment(guohaoFragment.this,1);
                           dia.show(getFragmentManager(),"guohaodiafragment");
                           return true;
                       }
                   });
                   break;
               case 2:yuyueList.remove(msg.arg1);
                     base.notifyDataSetChanged();
                   break;
           }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("requestCode:"+requestCode+" resultCode:"+resultCode);
        if (requestCode==1){
          if (resultCode==2){
              int weizhi=data.getIntExtra("position",-10);
              System.out.println("position"+weizhi);
              Message message=Message.obtain();
              message.what=2;
              message.arg1=weizhi;
              handler.sendMessage(message);
          }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_guohao,container,false);
       listView=(ListView)view.findViewById(R.id.guohaolist);
        Toolbar toolbar=(Toolbar)view.findViewById(R.id.guohaotoolbar);
        toolbar.setTitle("");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        AppCompatActivity appCompatActivity=(AppCompatActivity)getActivity();
         DrawerLayout   drawerLayout=(DrawerLayout)appCompatActivity.findViewById(R.id.mainDrawer);
        ActionBarDrawerToggle drawerToggle=new ActionBarDrawerToggle(getActivity(),drawerLayout,toolbar,R.string.app_name,R.string.bottom_sheet_behavior);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
         new  mythred().start();
        return view;
    }

    public  class  mythred extends Thread{
        @Override
        public void run() {
            super.run();
            String user= Myapplication.getMapshuju().get("personid").toString();
            JSONObject jsonObject=new JSONObject();
            try {
                jsonObject.put("personid",user);
                String path=getString(R.string.requestyuyue);
                String result= httputill.lianjie(path,jsonObject.toString());
              //  System.out.println("挂号返回的参数"+result);
                if(!result.equals("kong")){
                    Gson gson=new Gson();
                    Type type=new TypeToken<List<Yuyue>>(){}.getType();
                    List<Yuyue> yuyueList=gson.fromJson(result,type);
                  //  System.out.println("挂号返回的参数实体"+yuyueList.get(0).getEmployee());
                    Message message=Message.obtain();
                    message.what=1;
                    message.obj=yuyueList;
                    handler.sendMessage(message);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.leftmenu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void remove(int position,Boolean xianshi) {
//        if ((xianshi)){
//            yuyueList.remove(position);
//          base.notifyDataSetChanged();
//            Toast.makeText(getActivity(),"预约已取消",Toast.LENGTH_SHORT).show();
//        }else {
  //          Toast.makeText(getActivity(),"取消失败，请联系管理员",Toast.LENGTH_SHORT).show();
//        }


    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        setlevelfragment(context);
        if (!first){
            if (!hidden){
                Log.d("guohao onHiddenChanged","启用了");
              new mythred().start();
            }
        }

    }
    private  void setlevelfragment(Context context){
        ((MainActivity)context).setLevel(1);

    }
    @Override
    public void onResume() {
        System.out.println("onResume");
        super.onResume();
        setlevelfragment(context);
    }
    @Override
    public void onStart() {
        System.out.println("onStart");
        setlevelfragment(context);
        super.onStart();
    }

}
