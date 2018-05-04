package com.zfh.Fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.guhao.R;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.zfh.Po.Time;

import java.util.List;

/**
 * Created by Administrator on 2018\3\10 0010.
 */

public class paizhenAdapeter extends BaseAdapter {
    private Context context;
    private List<Time> paizhen;
    private call call;
    public paizhenAdapeter(Context context, List<Time>paizhen, call call) {
        this.context=context;
        this.paizhen=paizhen;
        this.call=call;

    }

    @Override
    public int getCount() {
        return paizhen.size();
    }

    @Override
    public Object getItem(int position) {
        return paizhen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

       ViewHolder viewHolder=null;
        final Time time=paizhen.get(position);
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.paizhen,parent,false);
            viewHolder.date=(TextView)convertView.findViewById(R.id.date);
            viewHolder.xingqi=(TextView)convertView.findViewById(R.id.xingqi);
            viewHolder.am=(TextView)convertView.findViewById(R.id.am);
            viewHolder.pm=(TextView)convertView.findViewById(R.id.pm);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder) convertView.getTag();
        }
        viewHolder.date.setText(time.getDate());
        viewHolder.xingqi.setText(time.getXingqi());
        viewHolder.am.setText(time.getAm());
        if (!time.getAm().equals("无排诊"))
        {
            viewHolder.am.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    call.timedialog(time.getAm(),time.getDate(),time.getEmployeeid());


                }
            });
        }

        viewHolder.pm.setText(time.getPm());
        if (!time.getPm().equals("无排诊")){
            viewHolder.pm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    call.timedialog(time.getPm(),time.getDate(),time.getEmployeeid());
                }
            });
        }
        return convertView;
    }
    class ViewHolder{
        TextView date;
        TextView xingqi;
        TextView am;
        TextView pm;
    }
    public interface call{
        public void timedialog(String time,String date,String employeeid);
    }


}
