package com.zfh.Fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.guhao.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zfh.Activity.MainActivity;
import com.zfh.Activity.Myapplication;
import com.zfh.Httputill.httputill;
import com.zfh.Po.Favourite;
import com.zfh.dingyiview.Myrecy;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link favFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link favFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class favFragment extends Fragment implements Myrecy.Callback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Myrecy myrecy;
    private Boolean first=true;
    private String[] parama=new String[2];
    private mytask mytask;
    private fav_Adapter fav_adapter;
    private Context context;

    private OnFragmentInteractionListener mListener;

    public favFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment favFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static favFragment newInstance(String param1, String param2) {
        favFragment fragment = new favFragment();
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
        parama[0]=getResources().getString(R.string.requestrequestfav);
        parama[1]=(String) Myapplication.getMapshuju().get("personid");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_fav,container,false);
        Toolbar toolbar=(Toolbar)view.findViewById(R.id.favtoolbar);
        toolbar.setTitle("");
        AppCompatActivity appCompatActivity=(AppCompatActivity)getActivity();
        appCompatActivity.setSupportActionBar(toolbar);
        DrawerLayout drawerLayout=(DrawerLayout)appCompatActivity.findViewById(R.id.mainDrawer);
        ActionBarDrawerToggle actionBarDrawerToggle=new ActionBarDrawerToggle(getActivity(),drawerLayout,toolbar,R.string.app_name,R.string.bottom_sheet_behavior);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
         myrecy=(Myrecy)view.findViewById(R.id.fav_list);
        myrecy.setCallback(favFragment.this);
        myrecy.scheduleLayoutAnimation();
        myrecy.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        mytask=new mytask();
        mytask.execute(parama);
        return view;
    }

    @Override
    public void shengchengpaizhen(String employeeid) {
        paizhenFragment paizhenFragment= com.zfh.Fragment.paizhenFragment.newInstance(employeeid,null);
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction transition=fragmentManager.beginTransaction();
        transition.hide(favFragment.this);
        transition.add(R.id.content,paizhenFragment,"paizhenFragment");
        transition.addToBackStack(null).commit();
//        transition.commit();
    }

    class mytask extends AsyncTask<String,Void,List<Favourite>>{
        @Override
        protected void onPostExecute(List<Favourite> favourites) {
            super.onPostExecute(favourites);
            if (favourites==null||favourites.size()==0){

            }else {
                fav_adapter=new fav_Adapter(getActivity(),favourites,myrecy);
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                myrecy.setLayoutManager(linearLayoutManager);
                myrecy.setAdapter(fav_adapter);

            }
        }

        @Override
        protected List<Favourite> doInBackground(String... params) {
//            String userid=(String) Myapplication.getMapshuju().get("personid");
//            String path=getString(R.string.requestrequestfav);
            JSONObject jsonObject=new JSONObject();
            try {
                jsonObject.put("userid",params[1]);
                String result= httputill.lianjie(params[0],jsonObject.toString());
                if (!result.equals("kong")){
                    Gson gson=new Gson();
                    Type type= new TypeToken<List<Favourite>>(){}.getType();
                    List<Favourite> favourites=gson.fromJson(result,type);
                   return favourites;
                }else {
                    return null;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        return null;
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
                Log.d("guohao onHiddenChanged","启用了"+isVisible());
                 mytask=new mytask();
                mytask.execute(parama);
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
