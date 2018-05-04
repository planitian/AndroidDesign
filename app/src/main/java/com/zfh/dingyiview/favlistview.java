package com.zfh.dingyiview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by Administrator on 2018\3\23 0023.
 */

public class favlistview extends ListView implements GestureDetector.OnGestureListener{
    private Context context;
    private GestureDetector gestureDetector;
    public favlistview(Context context) {

        super(context);
        this.context=context;
    }

    public favlistview(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        gestureDetector=new GestureDetector(context,this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
         return gestureDetector.onTouchEvent(ev);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}
