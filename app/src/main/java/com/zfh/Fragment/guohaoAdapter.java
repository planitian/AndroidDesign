package com.zfh.Fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.guhao.R;
import com.zfh.Po.Yuyue;

import java.util.List;

/**
 * Created by Administrator on 2018\3\19 0019.
 */

public class guohaoAdapter extends BaseAdapter {
  private List<Yuyue> yuyueList;
    private Context context;
    private Yuyue yuyue;
    public guohaoAdapter(Context context,List<Yuyue>yuyueList) {
        this.context= context;
        this.yuyueList=yuyueList;
    }

    public List<Yuyue> getYuyueList() {
        return yuyueList;
    }

    @Override
    public int getCount() {
        return yuyueList.size();
    }

    @Override
    public Object getItem(int position) {
        return yuyueList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        yuyue=yuyueList.get(position);
     ViewHolder viewHolder=null;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView=LayoutInflater.from(context).inflate(R.layout.guohaoitem,parent,false);
            viewHolder.yuyuema=(TextView)convertView.findViewById(R.id.guohaoyuyuema);
            viewHolder.employee=(TextView)convertView.findViewById(R.id.guohaoemp);
            viewHolder.keshi=(TextView)convertView.findViewById(R.id.guohaokeshi);
            viewHolder.riqi=(TextView)convertView.findViewById(R.id.guohaodate);
            viewHolder.xingqi=(TextView)convertView.findViewById(R.id.guohaoxingqi);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder) convertView.getTag();
        }
      //  System.out.println(yuyue.getEmployee());
        String yuma=yuyue.getYuyuema().substring(21);
        viewHolder.yuyuema.setText(yuma);
        viewHolder.employee.setText(yuyue.getEmployee());
        viewHolder.keshi.setText(yuyue.getKeshi());
        viewHolder.riqi.setText(yuyue.getTime());
        viewHolder.xingqi.setText(yuyue.getXingqi());
        return convertView;
    }
    class ViewHolder{
        TextView yuyuema;
        TextView employee;
        TextView riqi;
        TextView  keshi;
        TextView xingqi;
    }
}
