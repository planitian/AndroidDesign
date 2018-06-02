package com.zfh.dingyiview;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;

import com.example.administrator.guhao.R;
import com.zfh.Fragment.fav_Adapter;
import com.zfh.Fragment.keshiFragment;
import com.zfh.Fragment.paizhenFragment;

/**
 * Created by Administrator on 2018\3\26 0026.
 */

public class Myrecy extends RecyclerView implements fav_Adapter.callback, GestureDetector.OnGestureListener ,itembuju.Call{
    private int lastposition = -1;
    private float lastdownx;
    private float lastdowny;
    private int mtouchslop;
    private boolean lanjie = false;
    private int first = 0;
    public Callback callback;
    private GestureDetector gestureDetector;
    private boolean interceptlanjie=false;
    private boolean interfirstfanhui=false;//用于记录onInterceptTouchEvent中第一次是横向滑动还是纵向滑动
    private VelocityTracker velocityTracker;

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public Myrecy(Context context) {
        super(context);
        ViewConfiguration configuration = ViewConfiguration.get(context);
        mtouchslop = configuration.getScaledPagingTouchSlop();
        gestureDetector = new GestureDetector(context, this);
    }

    public Myrecy(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        ViewConfiguration configuration = ViewConfiguration.get(context);
        mtouchslop = configuration.getScaledPagingTouchSlop();
        gestureDetector = new GestureDetector(context, this);
    }


    //
//    @Override
//    public boolean onTouchEvent(MotionEvent e) {
////        System.out.println("my raxY"+e.getRawY());
//        System.out.println("Myrecy onTouchEvent 运行 " + "action" + e.getAction());
//        System.out.println("Myrecdianji" + e.getX() + "sdsd" + e.getRawX());
//        switch (e.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                View view = findChildViewUnder(e.getX(), e.getY());
//                if (view == null) {
//                    System.out.println("view==null");
//                }
//                System.out.println("Myrecdianji" + e.getX() + "sdsd" + e.getRawX());
//                break;
//        }
//        Boolean result = super.onTouchEvent(e);
//        System.out.println("super.onTouchEvent(e)" + result);
//        return result;
//
////        return false;
//    }


//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent e) {
//        System.out.println("my raxY" + e.getRawY() + "my rawx" + e.getRawX() + "action" + e.getAction());
//        switch (e.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                itembuju view1 = (itembuju) findChildViewUnder(e.getX(), e.getY());
////            System.out.println("getVerticalScrollbarPosition()"+getChildAdapterPosition(view1));
//                int position = getChildAdapterPosition(view1);
////                if (lastposition != position && lastposition != -1) {
////                    ((itembuju) getChildAt(lastposition)).fuyuan();
////
////                }
//                int n=getChildCount();
//                for (int i=0;i<getChildCount();i++){
//                    ((itembuju) getChildAt(i)).fuyuan();
//                }
//                fav_Adapter fav_adapter=(fav_Adapter) getAdapter();
//                String employeeid= fav_adapter.getemployeeid(position);
//                callback.shengchengpaizhen(employeeid);
//                lastposition = position;
//                lastdownx = e.getRawX();
//                lastdowny = e.getRawY();
//                lanjie=super.onInterceptTouchEvent(e);
//                break;
//            case MotionEvent.ACTION_MOVE:
//                int xslop = (int) Math.abs(lastdownx - e.getRawX());
//                int yslop=(int)Math.abs(lastdowny-e.getRawY());
//                System.out.println("lastdowny"+lastdowny+"e.getRawY()"+e.getRawY());
//                System.out.println("yslop"+yslop+"mtouchslop"+mtouchslop);
//                System.out.println("xslop"+xslop+"mtouchslop"+mtouchslop);
////                if (yslop>mtouchslop){
////                      lanjie=true;
////
////                }else if (xslop>mtouchslop){
////                System.out.println("xslop"+xslop+"mtouchslop"+mtouchslop);
////                    lanjie=false;
////                }
//                first++;
//                System.out.println("first"+first);
//                System.out.println("first==1"+(first==1));
//                System.out.println("xslop > yslop"+(xslop > yslop));
//                if (first==1) {
//                    if (xslop > yslop) {
//                        lanjie = false;
//                    }else {
//                        lanjie=true;
//                    }
//                }
//
//            case MotionEvent.ACTION_UP:
//                first=0;
//                break;
//
//        }
//
//
////        if (e.getAction() == MotionEvent.ACTION_DOWN) {
////            itembuju view1 = (itembuju) findChildViewUnder(e.getX(), e.getY());
//////            System.out.println("getVerticalScrollbarPosition()"+getChildAdapterPosition(view1));
////            int position = getChildAdapterPosition(view1);
////            if (lastposition != position && lastposition != -1) {
////                ((itembuju) getChildAt(lastposition)).fuyuan();
////            }
////            lastposition = position;
////        }
////        Boolean result = super.onInterceptTouchEvent(e);
////        System.out.println("super.onInterceptTouchEvent(e)" + result);
//        System.out.println("lanjie"+lanjie);
////        return result;
//        return lanjie;
//    }


    @Override
    public void setlastposition(int lastposition) {
//        ((itembuju) getChildAt(lastposition)).fuyuan();
        lastposition = -1;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
//        Boolean result = gestureDetector.onTouchEvent(e);
         Boolean result=super.onTouchEvent(e);
        lanjie=result;
        switch (e.getAction()){
            case MotionEvent.ACTION_UP:
                if (lastdownx==e.getRawX()&&lastdowny==e.getRawY()){
                    itembuju view1 = (itembuju) findChildViewUnder(e.getRawX(), e.getRawY());
                    int position;
                    if (view1!=null){
                        position = getChildAdapterPosition(view1);
                    }else {
                        position=((fav_Adapter)getAdapter()).getItemCount();
                    }

                        fav_Adapter fav_adapter = (fav_Adapter) getAdapter();
                        String employeeid = fav_adapter.getemployeeid(position);
                        callback.shengchengpaizhen(employeeid);


                }
                first = 0;
                lanjie=true;
                interfirstfanhui=false;
                break;
        }
        System.out.println("Myrecy  onTouchEvent action " + e.getAction() + " result " + result + " lanjie " + lanjie);
        return lanjie;
//        return super.onTouchEvent(e);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
//        System.out.println("raxy"+e.getRawY()+" y "+e.getY());
//        itembuju view1 = (itembuju) findChildViewUnder(e.getX(), e.getY());
//        int position1 = getChildAdapterPosition(view1);
//        System.out.println("x y"+position1);
//        itembuju view = (itembuju) findChildViewUnder(e.getRawX(), e.getRawY());
//        int position = getChildAdapterPosition(view);
//        System.out.println("rawx  rawy"+position);
        boolean result= super.onInterceptTouchEvent(e);
        interceptlanjie=result;
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (velocityTracker==null){
                    velocityTracker=VelocityTracker.obtain();
                    velocityTracker.addMovement(e);
                }else {
                    velocityTracker.clear();
                    velocityTracker.addMovement(e);
                }

                lastdownx = e.getRawX();
                lastdowny = e.getRawY();
                for (int i = 0; i < getChildCount(); i++) {
                    ((itembuju) getChildAt(i)).fuyuan();
                }
                interceptlanjie=false;
                break;
            case MotionEvent.ACTION_MOVE:
//                int xslop = (int) Math.abs(lastdownx - e.getRawX());
//                int yslop = (int) Math.abs(lastdowny - e.getRawY());
                System.out.println("lastdowny " + lastdowny +" lastdownx "+lastdownx+"e.getRawX()"+e.getRawX() +" e.getRawY() " + e.getRawY()+"Y"+e.getY());
//                System.out.println("yslop" + yslop + "mtouchslop" + mtouchslop);
//                System.out.println("xslop" + xslop + "mtouchslop" + mtouchslop);
//                if (yslop>mtouchslop){
//                      lanjie=true;
//
//                }else if (xslop>mtouchslop){
//                System.out.println("xslop"+xslop+"mtouchslop"+mtouchslop);
//                    lanjie=false;
//                }
                velocityTracker.addMovement(e);
                velocityTracker.computeCurrentVelocity(1000);
                float y=Math.abs(velocityTracker.getYVelocity());
                float x=Math.abs(velocityTracker.getXVelocity());
                first++;
                System.out.println("first" + first+"getYVelocity()"+velocityTracker.getYVelocity()+"getXVelocity()"+velocityTracker.getXVelocity());
                if (first==2){
                    if (x+0.1>y){
                        interceptlanjie=false;
                    }else {
                        interceptlanjie=true;
                    }
                    interfirstfanhui=interceptlanjie;
                }else {
                    interceptlanjie=interfirstfanhui;
                }
                break;

//                System.out.println("first==1" + (first == 1));
//                System.out.println("xslop > yslop" + (xslop > yslop));
//                System.out.println("first == 1"+(first == 1)+"xslop > yslop"+(xslop > yslop)+xslop+">"+yslop);
//                if (first == 1) {
//                    if (xslop >=yslop) {
//                        interceptlanjie = false;
//                        interfirstfanhui=interceptlanjie;
//                    } else {
//                        interceptlanjie = true;
//                        interfirstfanhui=interceptlanjie;
//                    }
//                }else {
//                    interceptlanjie = interfirstfanhui;
//                }
//                break;
            case MotionEvent.ACTION_UP:
                first = 0;
                if (velocityTracker!=null){
                    velocityTracker.recycle();
                    velocityTracker=null;
                }
                interceptlanjie=interfirstfanhui;
                interfirstfanhui=false;
                break;
        }

        System.out.println("Myrecy  onInterceptTouchEvent action " + e.getAction() + " result " + result + " interceptlanjie " + interceptlanjie);
        return interceptlanjie;
    }
    @Override
    public void zhuangpaizhen(float x, float y) {
        itembuju view1 = (itembuju) findChildViewUnder(x, y);
        int position = getChildAdapterPosition(view1);
        if (position<0){
           position=((fav_Adapter)getAdapter()).getItemCount()-1;
        }
            fav_Adapter fav_adapter = (fav_Adapter) getAdapter();
            String employeeid = fav_adapter.getemployeeid(position-1);
            System.out.println("employeeid"+employeeid+(position-1));
            callback.shengchengpaizhen(employeeid);
    }

    public interface Callback {
        //用于在实现Myrecy的fragment界面，往Myrecy回调生成paizhenfragment界面
        public void shengchengpaizhen(String employeeid);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        System.out.println("onDown触发 action " + e.getAction());
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        System.out.println("onShowPress触发 action " + e.getAction());
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        System.out.println("onSingleTapUp action " + e.getAction());
        itembuju view1 = (itembuju) findChildViewUnder(e.getX(), e.getY());
        int position = getChildAdapterPosition(view1);
        fav_Adapter fav_adapter = (fav_Adapter) getAdapter();
        String employeeid = fav_adapter.getemployeeid(position);
        callback.shengchengpaizhen(employeeid);
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        System.out.println("e1==null"+(e1==null));
        if (e1!=null&&e2!=null) {
            System.out.println(" onScroll触发action1 " + e1.getAction() + "  action2 " + e2.getAction());
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        System.out.println("onLongPress触发 action " + e.getAction());
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (e1!=null&&e2!=null) {
            System.out.println(" onFling触发action1 " + e1.getAction() + "  action2 " + e2.getAction());
        }
        return false;
    }


}
