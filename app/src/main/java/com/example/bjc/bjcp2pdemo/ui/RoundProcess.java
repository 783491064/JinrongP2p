package com.example.bjc.bjcp2pdemo.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.bjc.bjcp2pdemo.R;
import com.example.bjc.bjcp2pdemo.util.UIUtils;

/**
 * Created by 毕静存 on 2016/11/30.
 */

public class RoundProcess extends View {

    private int measuredWidth;
    private Paint paint;
    private int roundColor;
    private int processColor;
    private int TextColor;
    private float ruondWidth;
    private float textSize;
    private int max;

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getProcess() {
        return process;
    }

    public void setProcess(int process) {
        this.process = process;
    }

    public int getCurrentProcess() {
        return currentProcess;
    }

    public void setCurrentProcess(int currentProcess) {
        this.currentProcess = currentProcess;
    }

    private int process;
    private int currentProcess=90;

    public RoundProcess(Context context) {
        this(context,null);
    }

    public RoundProcess(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RoundProcess(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //初始化画笔
        paint=new Paint();
        paint.setAntiAlias(true);//去除锯齿效果；
        //获取自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundProcess);
        roundColor = typedArray.getColor(R.styleable.RoundProcess_roundcolor, Color.GRAY);
        processColor = typedArray.getColor(R.styleable.RoundProcess_roundprogresscolor,Color.RED);
        TextColor = typedArray.getColor(R.styleable.RoundProcess_textcolor,Color.GREEN);
        ruondWidth = typedArray.getDimension(R.styleable.RoundProcess_roundWidth, UIUtils.dp2px(10));
        textSize = typedArray.getDimension(R.styleable.RoundProcess_textsize, UIUtils.dp2px(20));
        max = typedArray.getInteger(R.styleable.RoundProcess_max,100);
        process = typedArray.getInteger(R.styleable.RoundProcess_progress,60);

        //需要进行回收
        typedArray.recycle();



    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measuredWidth = this.getMeasuredWidth();
    }

    @Override
    protected void onDraw(Canvas canvas){
        //画圆环
        int cx=measuredWidth/2;
        int cy=measuredWidth/2;
        float radios=measuredWidth/2-ruondWidth/2;
        paint.setColor(roundColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(ruondWidth);
        canvas.drawCircle(cx,cy,radios,paint);
        //画圆弧
        RectF rectF=new RectF(ruondWidth/2,ruondWidth/2,measuredWidth-ruondWidth/2,measuredWidth-ruondWidth/2);
        paint.setColor(processColor);
        canvas.drawArc(rectF,0,process*360/max,false,paint);
        //画文本
        String text=process*100/max+"%";
        Rect rect=new Rect();
        paint.setColor(TextColor);
        paint.setTextSize(textSize);
        paint.setStrokeWidth(0);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.getTextBounds(text,0,text.length(),rect);
        int textLeft= (int) (measuredWidth/2-rect.width()/2);
        int textBottom= (int) (measuredWidth/2+rect.width()/2);
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        int baseline = (getMeasuredHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
        canvas.drawText(text,textLeft,baseline,paint);
    }
}
