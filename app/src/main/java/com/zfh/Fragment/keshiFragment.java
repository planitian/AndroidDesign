package com.zfh.Fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.UiThread;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zfh.Activity.MainActivity;
import com.zfh.Activity.Myapplication;
import com.zfh.Fragment.keshiAdapter;
import com.example.administrator.guhao.R;
import com.zfh.Httputill.httputill;
import com.zfh.Po.Employee;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link keshiFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link keshiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class keshiFragment extends Fragment implements keshiAdapter.call{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View view;
    private ListView listView;
    private keshiAdapter keshiAdapter;
    private int n=0;
    private List<Employee> employees=new ArrayList<>();
    private String[] parama=new String[2];
    private JSONObject jsonObject=new JSONObject();
    private Context context;
    private DrawerLayout drawerLayout;

//    private  Myapplication myapplication=(Myapplication)getActivity().getApplication();
     Handler handler= new Handler(){
         @Override
         public void handleMessage(Message msg) {
             super.handleMessage(msg);

             switch (msg.what){
                 case 1: String resu=(String)msg.obj;
                    // System.out.println("handler里面的参数"+resu);
                         initlistview(resu);
                         break;
                 case 2:Boolean fenbian=(Boolean) msg.obj;//用于分辨是取消收藏还是添加收藏的  从onchange传来
                        if (msg.arg2==1){
                            int position=msg.arg1;
                            System.out.println("nn>>>>"+n);
                            n++;
                            System.out.println("position::"+position);
                            Log.d("changge","调用了");
                            int first=listView.getFirstVisiblePosition();
                            System.out.println("first::"+first);
                            int last=listView.getLastVisiblePosition();
                            System.out.println("last::"+last);
                            int count=listView.getChildCount();
                            System.out.println(count);
                            if (position>=first&&position<=last){
                                View view=listView.getChildAt(position-first);



//                       View view=listView.getRootView();
                                System.out.println("chilat"+String.valueOf(position-first));
                                if (view.getTag()instanceof keshiAdapter.ViewHolder){
                                    keshiAdapter.ViewHolder vh=(keshiAdapter.ViewHolder)view.getTag();
                                     if (fenbian){
                                            vh.xiai.setBackgroundResource(R.drawable.tab_fac_normal);
                                         Employee employee=employees.get(position);
                                         employee.setFavour(false);
                                         Toast.makeText(getActivity(),"从收藏列表删除"+employee.getName(),Toast.LENGTH_LONG).show();
                                     }else {
                                         vh.xiai.setBackgroundResource(R.drawable.tab_fav_pre);
                                         Employee employee=employees.get(position);
                                         employee.setFavour(true);
                                         Toast.makeText(getActivity(),"添加"+employee.getName()+"到收藏列表成功",Toast.LENGTH_LONG).show();
                                     }
                                }
                            }
                        }else {
                            if (fenbian){
                                Toast.makeText(getActivity(),"取消收藏失败，请联系开发者",Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(getActivity(),"收藏失败，请联系开发者",Toast.LENGTH_LONG).show();
                            }
                        }
                     break;
             }

         }
     };


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public keshiFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment keshiFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static keshiFragment newInstance(String param1, String param2) {
        keshiFragment fragment = new keshiFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);
        //这个会使菜单键失效，但是 菜单键 还是可见
//        setMenuVisibility(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_keshi, container, false);\

        view=inflater.inflate(R.layout.fragment_keshi,container,false);
        Toolbar toolbar=(Toolbar)view.findViewById(R.id.keshitoobar);
        toolbar.setTitle("科室");
//        toolbar.setNavigationIcon(R.drawable.tab_fav);
        //这个方法要在setSupportActionBar(toolbar); 之后才有用
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getActivity(),"点击了Navigation",Toast.LENGTH_SHORT).show();
//            }
//        });
//        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        AppCompatActivity appCompatActivity=(AppCompatActivity)getActivity();
         drawerLayout=(DrawerLayout)appCompatActivity.findViewById(R.id.mainDrawer);
        drawerLayout.setDrawerShadow(R.drawable.shaw, GravityCompat.END);
        ActionBarDrawerToggle drawerToggle=new ActionBarDrawerToggle(getActivity(),drawerLayout,toolbar,R.string.app_name,R.string.bottom_sheet_behavior);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView=(ListView)view.findViewById(R.id.list);


//        Employee eee=new Employee();
//        eee.setFavour(false);
//        eee.setName("hhhh");
//        eee.setJianjie("dasdasdasd");
//        eee.setEmployeeid("fsdfsfs");
//        eee.setTouxiang("454645");
//        employees.add(eee);
//        keshiAdapter=new keshiAdapter(getContext(),employees,this);
//        listView.setAdapter(keshiAdapter);
//        listView.setVisibility(View.INVISIBLE);
        parama[0]=getResources().getString(R.string.requestkeshi);
        try {
            jsonObject.put("nouser","asd");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        parama[1]=jsonObject.toString();

        mytask myta=new mytask();
        myta.execute(parama);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.leftmenu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){

            if (drawerLayout.isDrawerOpen(Gravity.START)){
                drawerLayout.closeDrawer(Gravity.START);
            }else {
                drawerLayout.openDrawer(Gravity.START);
            }
            Toast.makeText(getActivity(),"点击了optionItem",Toast.LENGTH_SHORT).show();
            return true;
        }
        if (item.getItemId()==R.id.qiehuan){
            Toast.makeText(getActivity(),"点击了optionItem",Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    //建立listview
    public void initlistview(String result){
//        Fragment fubuju=(Fragment)getActivity().findViewById(R.id.keshifubuju);
//        FrameLayout fubuju=(FrameLayout)getActivity().findViewById(R.id.keshifubuju);

        Gson gson=new Gson();
        Type type=new TypeToken<List<Employee>>(){}.getType();
        List<Employee> guodu=new ArrayList<>();
        employees.clear();
      //  employees.addAll(gson.fromJson(result,type));
        guodu=gson.fromJson(result,type);
        employees.addAll(guodu);
        if (keshiAdapter==null) {
            Log.d("keshiAdapter==null","true");
            keshiAdapter = new keshiAdapter(getContext(), employees, this);
        }else {
            keshiAdapter.notifyDataSetChanged();
        }
        //employees.clear();
       // employees.addAll(guodu);
        Log.d("ini","调用了");
        //刷新整个数据源，强制全部刷新
      //  keshiAdapter.notifyDataSetChanged();
        listView.setAdapter(keshiAdapter);
        listView.setVisibility(View.VISIBLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 Employee empl=(Employee)parent.getItemAtPosition(position);
                paizhenFragment paizhenFragment= com.zfh.Fragment.paizhenFragment.newInstance(empl.getEmployeeid(),null);
                FragmentManager fragmentManager=getFragmentManager();
                FragmentTransaction transition=fragmentManager.beginTransaction();
                transition.hide(keshiFragment.this);
                transition.add(R.id.content,paizhenFragment,"paizhenFragment");
                transition.addToBackStack(null).commit();
            }
        });

//        List<Employee> employees=new ArrayList<>();
//        for (int i=0;i<100;i++){
//            Employee eee=new Employee();
//            eee.setFavour(false);
//            eee.setName("hhhh");
//            eee.setJianjie("dasdasdasd");
//            eee.setEmployeeid("fsdfsfs");
//            eee.setTouxiang("454645");
//            employees.add(eee);
//        }


//        ListView test=new ListView(getActivity());
//        FrameLayout.LayoutParams layoutParams=new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT);
//        layoutParams.topMargin=60;
//        test.setLayoutParams(layoutParams);

//        fubuju.addView(test);
//       keshiAdapter=new keshiAdapter(getContext(),null,this);
//         listView.setAdapter(keshiAdapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.d("hdhdhhd","hhhhh");
//                Toast.makeText(getActivity(),"zixiangdianji",Toast.LENGTH_LONG).show();
//            }
//        });
//        fubuju.addView(listView);
    }
   private  void setlevelfragment(Context context){
       ((MainActivity)context).setLevel(1);

   }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
        setlevelfragment(context);

//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//            this.context=context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onStart() {
        System.out.println("onStart");
        setlevelfragment(context);
        super.onStart();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
//回调方法
    @Override
    public void change(final int position, final JSONObject jsonObject) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Message message=Message.obtain();
                    message.what=2;
                    message.arg1=position;
                    Myapplication myapplication=(Myapplication)getActivity().getApplication();
                   // Map map=myapplication.getMap();
                    Map map=Myapplication.getMapshuju();
                    jsonObject.put("personid",map.get("personid"));
                    System.out.println("myapp的传值"+myapplication.getTest());
                    System.out.println("收藏请求的发送参数"+jsonObject.toString());
                    httputill httputill=new httputill();
                    String url=getResources().getString(R.string.requestshoucang);
                    Boolean fav=jsonObject.getBoolean("fav");
                    String re=httputill.utill(url,jsonObject.toString());
                    System.out.println("re::"+re);
                    JSONObject jsonObject1=new JSONObject(re);
                    if (jsonObject1.getBoolean("success")){
                        message.arg2=1;
                        message.obj=fav;
                        handler.sendMessage(message);
                    }else {
                        message.arg2=0;
                        message.obj=fav;
                        handler.sendMessage(message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        }).start();
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

    class mytask extends AsyncTask<String,Void,List<String>> {
        @Override
        protected void onPostExecute(List<String> strings) {
            super.onPostExecute(strings);
            ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,strings);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
            Spinner spinner=(Spinner)view.findViewById(R.id.spring);
            spinner.setAdapter(arrayAdapter);
            spinner.setDropDownVerticalOffset(20);

            // listView.setVisibility(View.INVISIBLE);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //查询科室医生的参数 记得json格式
                      String keshiid=(String)parent.getItemAtPosition(position).toString();
                      System.out.println("科室医生参数"+keshiid);

                    final String url=getResources().getString(R.string.requestempl);
                       final JSONObject jsonObject1=new JSONObject();
                    try {
                        jsonObject1.put("keshiid",keshiid);
                        Myapplication myapplication=(Myapplication)getActivity().getApplication();
                        Map map=myapplication.getMap();
                        System.out.println(Myapplication.getGg());
                        if(map!=null) {
                            jsonObject1.put("userid", map.get("personid"));
                        }
                        else {
                            jsonObject1.put("userid","002");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            httputill httputill=new httputill();
                            Log.d("init","运行了");
                             String result=httputill.utill(url,jsonObject1.toString());
                           // System.out.println("返回的json::"+result);
                            Message message=Message.obtain();
                            message.obj=result;
                            message.what=1;
                            handler.sendMessage(message);

                        }
                    }).start();



                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }


        @Override
        protected List<String> doInBackground(String... params) {
            try {
                httputill httputill=new httputill();
                String result=httputill.utill(params[0],params[1]);
                JSONArray jsonArray=new JSONArray(result);
                List<String>list=new ArrayList<String>();
                for (int i=0;i<jsonArray.length();i++){
                    list.add(jsonArray.getString(i));
                }
//                List<String>list1=new ArrayList<>();
//                list1.add("测试数据1");
//                list1.add("测试数据2");
                return list;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
      }
    }

    @Override
    public void onResume() {
        System.out.println("onResume");
        super.onResume();
        setlevelfragment(context);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        setlevelfragment(context);
        if (!isHidden()){
            mytask myta=new mytask();
            myta.execute(parama);
            setMenuVisibility(false);
        }
        System.out.println("isHidden"+isHidden());

    }
}
