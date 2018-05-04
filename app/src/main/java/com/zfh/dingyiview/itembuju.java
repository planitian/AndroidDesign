package com.zfh.dingyiview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by Administrator on 2018\3\26 0026.
 */

public class itembuju extends LinearLayout {
    private Scroller scroller;
    private float downx;
    private float lastdownx;
    private float lastdowny;
    private float movex;
    private int mtouchslop;
    private int leftboder;
    private int rightboder;
    private Boolean lanjie=false;
    private Call call;
   private VelocityTracker velocityTracker;
    private int first=1;
    public itembuju(Context context) {
        super(context);
    }

    public itembuju(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        scroller = new Scroller(context);
        ViewConfiguration configuration = ViewConfiguration.get(context);
        mtouchslop = configuration.getScaledPagingTouchSlop();
//        System.out.println(" mtouchslop"+mtouchslop);
    }

    public void setCall(Call call) {
        this.call = call;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean result=super.dispatchTouchEvent(ev);
        switch (ev.getAction()){
            case MotionEvent.ACTION_UP:
//                result=true;
                break;
        }
//        System.out.println("Itembuju dispatchTouchEvent action " +ev.getAction()+" result "+result+" lanjie "+lanjie);
        return  result;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        System.out.println("onInterceptTouchEvent"+ev.getRawX()+">"+ev.getRawY()+">"+ev.getX()+">"+ev.getY()+"action"+ev.getAction());
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downx = ev.getX();
                lastdownx = downx;
                break;


            case MotionEvent.ACTION_MOVE:
                movex = ev.getX();
                float slop = Math.abs(movex - downx);
//                if (slop > mtouchslop) {
//                    return true;
//                }
                break;

        }
        boolean result=super.onInterceptTouchEvent(ev);

        lanjie=result;
        System.out.println("Itembuju onInterceptTouchEvent action " +ev.getAction()+" result "+result+" lanjie "+lanjie);
        return lanjie;
//        return true;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        super.onLayout(changed, l, t, r, b);
        if (getChildCount()>0){
            int total=getChildCount();
//            System.out.println("child总数"+total);
            int i=0;
            int oldx=0;
            int oldy=0;
            int guodu=0;
            for (i=0;i<total;i++){
                View view=getChildAt(i);
//                System.out.println("getMeasuredWidth():"+view.getMeasuredWidth());
//                System.out.println("getMeasuredHeight():"+view.getMeasuredHeight());
                guodu+=view.getMeasuredWidth();
                view.layout(oldx,0,guodu,view.getMeasuredHeight());
                oldx+=view.getMeasuredWidth();
                oldy=view.getMeasuredHeight();
//                System.out.println((i+1)+"oldx"+oldx);
//                System.out.println("guodu"+guodu);
            }
        }

        leftboder=getChildAt(0).getLeft();
//        System.out.println("itembuju"+leftboder);
        rightboder=getChildAt(getChildCount()-1).getRight();
//        System.out.println("itembuju"+rightboder);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Boolean result=super.onTouchEvent(event);
//        System.out.println("item x"+event.getX()+"rawx"+event.getRawX()+"lastdownx"+lastdownx);
//        System.out.println("getScrollX()"+getScrollX());
//        System.out.println("rawx"+event.getRawX()+"rawy"+event.getRawY());
        System.out.println("itembuju onTouchEvent 运行 rawx"+event.getRawX()+"rawy"+event.getRawY()+"action"+event.getAction());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (velocityTracker==null){
                    velocityTracker=VelocityTracker.obtain();
                    velocityTracker.addMovement(event);
                }else {
                    velocityTracker.clear();
                    velocityTracker.addMovement(event);
                }
                lastdownx = event.getRawX();
                lastdowny = event.getRawY();
                lanjie=true;
                break;
            case MotionEvent.ACTION_MOVE:
                velocityTracker.addMovement(event);
                velocityTracker.computeCurrentVelocity(1000);
                float y=Math.abs(velocityTracker.getYVelocity());
                float x=Math.abs(velocityTracker.getXVelocity());
                 System.out.println(x+y);
                movex=event.getRawX();
//                System.out.println("rawx"+event.getRawX()+"rawy"+event.getRawY());
                System.out.println("lastdownx"+lastdownx+"movex"+movex);
                int scrollx=(int)(lastdownx-movex);
                //防止左滑 越过边界
               if (getScrollX()+scrollx<leftboder){
                   System.out.println("左边界");
                   scrollTo(leftboder,0);
                   return true;
               }else if (getScrollX()+getWidth()+scrollx>rightboder){//防止右滑越过右边边界
                   System.out.println("右边界");
                   scrollTo(rightboder-getWidth(),0);
                   return true;
               }
               System.out.println("getScrollX()"+getScrollX());
                if (velocityTracker.getXVelocity()<-500&&first==1){
                    scroller.startScroll(getScrollX(),0,rightboder-getWidth()-getScrollX(),0);
                    first++;
                }else {
                    System.out.println("scrollx" + scrollx);
                    scrollBy(scrollx, 0);
                }
                lastdownx=movex;
                lanjie=true;
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("UPchufa");
                first=1;
//                out.println("MotionEvent.ACTION_UP","运行了");
//                out.println("rightboder",rightboder);
//                out.println("leftboder",leftboder);
//                out.println("getwidth",getWidth());
//                out.println("rightboder-getWidth()",((rightboder-getWidth())/2));
                  leftorright();
                 lanjie=true;
                if (lastdownx==event.getRawX()&&lastdowny==event.getRawY()){
                call.zhuangpaizhen(event.getRawX(), event.getRawY());
               }
                break;
            case MotionEvent.ACTION_CANCEL:
                System.out.println("canse"+event.getRawX()+event.getRawY());
                 lanjie=false;

        }
        invalidate();

        System.out.println("Itembuju onTouchEvent action " +event.getAction()+" result "+result+" lanjie "+lanjie);
//        lanjie=result;
//        System.out.println("item   super.onTouchEvent(event)"+result);
//        return result;
        return lanjie;
    }
//用来判断当滑倒一半时候手指离开了，应该滑到那一边
    public void leftorright(){
        // 当手指抬起时，根据当前的滚动值来判定应该滚动到哪个子控件的界面
        if (getScrollX()>((rightboder-getWidth())/2)){
            //getScrollX()看成当前偏移量 如果要把右边内容全部弄出来，就是要偏移rightboder-getWidth()量，当前已经偏移了getScrollX()量
            //所以 还需要偏移rightboder-getWidth()-getScrollX() 量   dx为正 向左偏移，dx为负数，向右偏移
            scroller.startScroll(getScrollX(),0,rightboder-getWidth()-getScrollX(),0);
        }else{
            //getScrollX()看成当前偏移量，如果要回到原来位置，就要偏移反方向的位置，因为一开始是向左偏移，所以现在向右偏移，为负数
            scroller.startScroll(getScrollX(),0,-getScrollX(),0);
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()){
//            out.println("scroller.getCurrX()",scroller.getCurrX());
            scrollTo(scroller.getCurrX(),scroller.getCurrY());
            invalidate();
        }
    }
    //用来在recycleview调用，把前一个item 复位
    public void fuyuan(){
        scroller.startScroll(getScrollX(),0,-getScrollX(),0);
        invalidate();
    }

    public interface Call{
        public void zhuangpaizhen(float x,float y);
    }

}
