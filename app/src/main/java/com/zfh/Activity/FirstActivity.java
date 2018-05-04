package com.zfh.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.administrator.guhao.R;

import java.util.ArrayList;
import java.util.List;

public class FirstActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private List<View> views = new ArrayList<>();
    private RadioButton one;
    private RadioButton two;
    private RadioButton three;
    private RadioButton tiaoguo;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences=getSharedPreferences("first",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        Integer count=sharedPreferences.getInt("count",0);
        int i=count;
        System.out.println("count"+count);
        editor.putInt("count",++i);
        editor.commit();
//        System.out.println("count!=0"+(count!=0)+(editor.commit())+count);
        if (!count.equals(0)){
            Intent intent=new Intent(FirstActivity.this,loginActivity.class);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.firstviewpager);
        views.add(addbiaoyu("高效0"));
        views.add(addbiaoyu("快捷1"));
        views.add(addbiaoyu("方便2"));
        inti();
        tiaoguo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FirstActivity.this,loginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        viewPager = (ViewPager) findViewById(R.id.first_viewpager);
        ViewPageradpter viewPageradpter = new ViewPageradpter();
        viewPager.setAdapter(viewPageradpter);
        one.setChecked(true);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        allnotchecke();
                        one.setChecked(true);
                        break;
                    case 1:
                        allnotchecke();
                        two.setChecked(true);
                        break;
                    case 2:
                        allnotchecke();
                        three.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void inti() {
        one = (RadioButton) findViewById(R.id.first_one);
        two = (RadioButton) findViewById(R.id.first_two);
        three = (RadioButton) findViewById(R.id.first_three);
        tiaoguo=(RadioButton)findViewById(R.id.first_tiaoguo);
    }

    public void allnotchecke() {
        one.setChecked(false);
        two.setChecked(false);
        three.setChecked(false);

    }

    public View addbiaoyu(@NonNull String biaoyu) {
        LinearLayout linearLayout = new LinearLayout(FirstActivity.this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        TextView textView = new TextView(FirstActivity.this);
        textView.setText(biaoyu);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(22);
        linearLayout.addView(textView, layoutParams);
        return linearLayout;
    }


    class ViewPageradpter extends PagerAdapter {

        @Override
        public int getCount() {
//            System.out.println("size"+views.size());
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            System.out.println("view == object" + (view == object) + "position " + view.getTag());

            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            System.out.println("instantiateItem " + position);
            container.addView(views.get(position));
            views.get(position).setTag(position);
            return views.get(position);

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            System.out.println("destroyItem " + position);
            container.removeView((View) object);
        }
    }
}
