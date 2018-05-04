package com.zfh.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.support.v4.app.FragmentManager;
import com.example.administrator.guhao.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.zfh.Activity.MainActivity;
import com.zfh.Httputill.httputill;
import com.zfh.Po.Employee;
import com.zfh.Po.Time;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link paizhenFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link paizhenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class paizhenFragment extends Fragment implements paizhenAdapeter.call{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;
    private ListView listView;
    private Context context;

    private OnFragmentInteractionListener mListener;

    public paizhenFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment paizhenFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static paizhenFragment newInstance(String param1, String param2) {
        paizhenFragment fragment = new paizhenFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      view=inflater.inflate(R.layout.fragment_paizhen,container,false);
        Toolbar toolbar=(Toolbar)view.findViewById(R.id.keshitoobar);
        toolbar.setTitle("");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        AppCompatActivity appCompatActivity=(AppCompatActivity)getActivity();
        DrawerLayout drawerLayout=(DrawerLayout)appCompatActivity.findViewById(R.id.mainDrawer);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(getActivity(),drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        listView=(ListView)view.findViewById(R.id.paizhen);
        mytask task=new mytask();
        String[] params=new String[2];
        params[0]=getResources().getString(R.string.requesttime);
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("employeeid",mParam1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        params[1]=jsonObject.toString();
        task.execute(params);
        return view;
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
        setlevelfragment(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void timedialog(String   time,String date,String employeeid) {
        Map<String,Long> map=xianzhi(time,date);
        mListener.onFragmentInteraction(map,employeeid);//回调给activity 重写  因为弹窗不能在fragment里面使用

    }

    public Map<String,Long> xianzhi(String time,String date)  {
        String[]fenge=time.split("-");
        String moren="2007-01-01 ";
        String min=date+" "+fenge[0]+":00";
        System.out.println("min:"+min);
        String max=date+" "+fenge[1]+":00";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date minmii= null;
        Date maxmil=null;
        try {
            minmii = format.parse(min);
            maxmil=format.parse(max);
            Map<String,Long> map=new HashMap<>();
            map.put("min",minmii.getTime());
            map.put("max",maxmil.getTime());
            return map;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

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
        void onFragmentInteraction(Map<String,Long> map,String employeeid);
    }
    class mytask extends AsyncTask<String,Void,List<Time>>{
        @Override
        protected void onPostExecute(List<Time> times) {
            super.onPostExecute(times);
            paizhenAdapeter paizhenAdapeter=new paizhenAdapeter(getActivity(),times,paizhenFragment.this);
            listView.setAdapter(paizhenAdapeter);
        }

        @Override
        protected List<Time> doInBackground(String... params) {
            try {
                //httputill httputill=new httputill();
                String result=httputill.lianjie(params[0],params[1]);
                Gson gson=new Gson();
                Type type=new TypeToken<List<Time>>(){}.getType();
                List<Time> times=gson.fromJson(result,type);
                return  times;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }
    private  void setlevelfragment(Context context){
        ((MainActivity)context).setLevel(2);

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

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        setlevelfragment(context);
    }
}
