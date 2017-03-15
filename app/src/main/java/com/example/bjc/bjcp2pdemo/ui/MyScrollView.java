package com.example.bjc.bjcp2pdemo.ui;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

/**
 * Created by 毕静存 on 2016/12/1.
 */

public class MyScrollView extends ScrollView {

    private int lastY;
    private int eventY;
    private View childView;
    private int detalY;
    private Rect rect=new Rect();//用来保存临界点的childView的上下左右；
    private boolean isFinish=true;
    private int lastix;
    private int lastiy;

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if(getChildCount()!=0){
            childView = getChildAt(0);//是scrollView中的线性布局；
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean isIntercept=false;
        int eventX = (int) ev.getX();
        int eventY = (int) ev.getY();
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastix = eventX;
                lastiy = eventY;
                break;
            case MotionEvent.ACTION_MOVE:
                int distacneX=Math.abs(eventX- lastix);
                int distanceY=Math.abs(eventY-lastY);
                if(distanceY>distacneX&&distanceY>10){
                    isIntercept=true;
                }
                lastix = eventX;
                lastiy = eventY;
                break;
        }
        return isIntercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(childView==null){
            return super.onTouchEvent(ev);
        }
        eventY = (int) ev.getY();//执行时先记录动作的Y值；
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                //按下时的Y值；
                lastY=eventY;
                break;
            case MotionEvent.ACTION_MOVE:
                detalY = eventY-lastY;
                if(isNeedMove()){

                    if(rect.isEmpty()){
                        rect.set(childView.getLeft(),childView.getTop(),childView.getRight(),childView.getBottom());
                    }
                    childView.layout(childView.getLeft(),(int)(childView.getTop()+detalY/2),childView.getRight(),(int)(childView.getBottom()+detalY/2));
                }
                lastY=eventY;
                break;
            case MotionEvent.ACTION_UP:
                if(isNeedAnimation()){
                TranslateAnimation animation=new TranslateAnimation(0,0,0,rect.bottom-childView.getBottom());
                    animation.setDuration(200);
                    childView.startAnimation(animation);
                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            isFinish = false;
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            isFinish=true;
                            childView.clearAnimation();
                            childView.layout(rect.left,rect.top,rect.right,rect.bottom);
                            rect.setEmpty();
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 是否需要移动的判断
     * @return
     */
    public boolean isNeedMove() {
        int measuredHeight = childView.getMeasuredHeight();//这个是线性布局的测量高度
        int scrollHeight = this.getHeight();
        int diatanceHeight=measuredHeight-scrollHeight;
        int scrollY = childView.getScrollY();//线性布局滑动的距离；
        if(scrollY<=0||scrollY>=diatanceHeight){
            return true;
        }
        return false;
    }

    /**
     * 是否需要平移动画
     * @return
     */
    public boolean isNeedAnimation() {
        return !rect.isEmpty();
    }
}
