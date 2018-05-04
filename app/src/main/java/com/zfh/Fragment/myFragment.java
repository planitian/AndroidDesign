package com.zfh.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.renderscript.ScriptGroup;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.administrator.guhao.R;
import com.zfh.Activity.MainActivity;
import com.zfh.Activity.Myapplication;
import com.zfh.Activity.ZhuceActivity;
import com.zfh.Httputill.httputill;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link myFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link myFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class myFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Context context;
    private EditText sfz;
    private EditText mima;
    private EditText phohe;
    private EditText address;
    private EditText name;
    private RadioButton nan;
    private RadioButton nv;
    private Button reset;
    private Button zhuce;

    private OnFragmentInteractionListener mListener;

    public myFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment myFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static myFragment newInstance(String param1, String param2) {
        myFragment fragment = new myFragment();
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
    }
    Handler handler=new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_zhuce, container, false);
        sfz = (EditText) view.findViewById(R.id.zhuce_sfz);
        mima = (EditText) view.findViewById(R.id.zhuce_mima);
        phohe = (EditText) view.findViewById(R.id.zhuce_phone);
        address = (EditText) view.findViewById(R.id.zhuce_address);
        name = (EditText) view.findViewById(R.id.zhuce_name);
        nan = (RadioButton) view.findViewById(R.id.zhuce_nan);
        nv = (RadioButton) view.findViewById(R.id.zhuce_nv);
        reset = (Button) view.findViewById(R.id.zhuce_reset);
        zhuce = (Button) view.findViewById(R.id.zhuce_zhuce);
        zhuce.setText("修改");
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });
        zhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   final String path=getString(R.string.requestupdateuser);
                  final JSONObject jsonObject=new JSONObject();
                try {
                    String user = Myapplication.getMapshuju().get("personid").toString();
                    jsonObject.put("personid",user);
                    jsonObject.put("mima", mima.getText().toString().trim());
                    jsonObject.put("phone", phohe.getText().toString().trim());
                    jsonObject.put("address", address.getText().toString().trim());
                    jsonObject.put("name", name.getText().toString().trim());
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                              final String result=httputill.lianjie(path,jsonObject.toString());
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (result.equals("success")) {
                                        Toast.makeText(getActivity(), "修改成功", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getActivity(), "修改失败", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }).start();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        init();
        return view;
    }

    public void init() {
        Map<String, String> map = Myapplication.getMapshuju();
        sfz.setText(map.get("personid"));
        sfz.setKeyListener(null);
        mima.setText(map.get("password"));
        mima.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        phohe.setText(map.get("phone"));
        name.setText(map.get("username"));
        address.setText(map.get("address"));
        if (map.get("sex").equals("男")) {
            nan.setChecked(true);
            nan.setClickable(false);
            nv.setClickable(false);
        } else {
            nv.setChecked(true);
            nan.setClickable(false);
            nv.setClickable(false);
        }

    }
    public void reset() {
        if (!mima.getText().equals("")) {
            mima.setText(null);
        }
        if (!phohe.getText().equals("")) {
            phohe.setText(null);
        }
        if (!address.getText().equals("")) {
            address.setText(null);
        }
        if (!name.getText().equals("")) {
            name.setText(null);
        }

    }

    class Mythread extends Thread {
        @Override
        public void run() {
            super.run();
            Map<String, String> map = Myapplication.getMapshuju();
           String path=getResources().getString(R.string.requestupdateuser);
            JSONObject jsonObject=new JSONObject();
            try {
                jsonObject.put("personid",map.get("personid"));
                jsonObject.put("mima",mima.getText().toString().trim());
                jsonObject.put("phone",phohe.getText().toString().toString());
                jsonObject.put("address",address.getText().toString());
                jsonObject.put("name",name.getText().toString().trim());
                final String result= httputill.lianjie(path,jsonObject.toString());
                reset.post(new Runnable() {
                    @Override
                    public void run() {
                         reset.setText("view.post");
                    }
                });
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (result.equals("success")){
                            Toast.makeText(getActivity(),"修改成功",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getActivity(),"修改失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
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

    private void setlevelfragment(Context context) {
        ((MainActivity) context).setLevel(1);

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
