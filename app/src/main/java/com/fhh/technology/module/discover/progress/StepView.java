package com.fhh.technology.module.discover.progress;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


/**
 * 自定义网上立案进度步骤的view
 */
public class StepView extends View {
    private onClickListener mListener;

    public interface onClickListener {
        void onClick(int position);
    }

    public void setListener(onClickListener listener) {
        mListener = listener;
    }

    private static final int minHorizontalSpace = 40;  //左右两侧保留的最小距离
    private int circleRadius = 20;  //圆半径
    private float circleMultiple = 1.5f;    //当前进度圆与正常圆的比例
    private int mWidth;
    private int paddingLeft;
    private int paddingRight;
    private int paddingBottom;
    private int paddingTop;
    private Paint circlePaint;  //画圆画笔
    private Paint textPaint;    //文字画笔
    private Paint linePaint;    //进度线画笔
    private int stepNum = 0;
    private int currentStep = 0;    //当前步骤
    private int lineDoneColor = Color.parseColor("#F68D38");
    private int lineDefaultColor = Color.parseColor("#CCCCCC");
    private int circleSelectColor = Color.parseColor("#F68D38");
    private int circleDefaultColor = Color.parseColor("#CCCCCC");
    private int circleTextColor = Color.parseColor("#FFFFFF");
    private String[] mTitleArray = {"第一步：准备起诉书，证据等图片。", "第二步：填写法院，当事人信息，诉讼请求。", "第三步：填写事实与理由，获取案由", "第四步：提交网上立案申请"};
    private String mTitle;
    float downX;
    float downY;
    float moveX;
    float moveY;


    public StepView(Context context) {
        super(context);
        init();
    }

    public StepView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StepView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        //初始化进度圆画笔
        circlePaint = new Paint();
        circlePaint.setColor(circleDefaultColor);
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.FILL);

        //初始化文字画笔
        textPaint = new Paint();
        textPaint.setColor(circleTextColor);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(20);

        //初始化进度线画笔
        linePaint = new Paint();
        linePaint.setColor(lineDefaultColor);
        linePaint.setAntiAlias(true);
        linePaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widhtSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        //左右两边要保留一定距离,否则显示不完全
        paddingLeft = Math.max(getPaddingLeft(), minHorizontalSpace);
        paddingRight = Math.max(getPaddingRight(), minHorizontalSpace);
        paddingBottom = getPaddingBottom();
        paddingTop = getPaddingTop();

        mWidth = widhtSize;
        //对整体View的高度控制,保证可以完整显示
        int mHeight = (int) Math.min(heightSize, 2 * circleMultiple * circleRadius + 60) + paddingTop + paddingBottom;
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(mHeight, MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mTitle == null) {
            return;
        }
        //①计算进度线的长度
        int space = (mWidth - stepNum * circleRadius * 2 - paddingLeft - paddingRight) / (stepNum - 1);

        //进度线和进度圆的Y坐标
        float mY = circleMultiple * circleRadius + paddingTop;

        //②画进度线
        float lineLeft; //左起点
        float lineRight;//右终点
        for (int i = 0; i < stepNum; i++) {
            linePaint.setColor(i <= currentStep ? lineDoneColor : lineDefaultColor);
            linePaint.setStrokeWidth(10);
            lineLeft = Math.max(i, 1) * 2 * circleRadius + Math.max(i - 1, 0) * space + paddingLeft;
            lineRight = Math.max(i, 1) * 2 * circleRadius + i * space + paddingLeft;
            RectF rectf = new RectF(lineLeft, mY - 4, lineRight, mY + 4);
            canvas.drawRect(rectf, linePaint);
        }

        //③画进度圆
        float x; //圆心横坐标
        for (int i = 0; i < stepNum; i++) {
            circlePaint.setColor(i <= currentStep ? circleSelectColor : circleDefaultColor);
            x = (float) ((i * 2 + 1) * circleRadius + i * space) + paddingLeft;
            canvas.drawCircle(x, mY, i == currentStep ? circleRadius * circleMultiple : circleRadius, circlePaint);
        }

        //④进度圆内的文字
        for (int i = 0; i < stepNum; i++) {
            textPaint.setColor(circleTextColor);
            textPaint.setTextSize(24);
            canvas.drawText(i == currentStep ? (i + 1) + "" : "", (i * 2 + 1) * circleRadius + i * space + paddingLeft - 6, mY + 10, textPaint);
        }

        //⑤具体进度文案
        float textY = mY + circleMultiple * circleRadius + 30;
        textPaint.setColor(Color.BLACK);
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.setTextSize(24);
        canvas.drawText(mTitle, 45, textY, textPaint);
    }

    //设置当前进度
    public void setCurrentStep(int currentStep) {
        mTitle = mTitleArray[currentStep];
        stepNum = 4;//由于共有4个步骤，就自己处理了
        this.currentStep = currentStep;
        postInvalidate();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = touchX;
                downY = touchY;
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = touchX - downX;
                moveY = touchY - downY;
                break;
            case MotionEvent.ACTION_UP:
                if (moveX == 0 && moveY == 0) {
                    float circleX = circleRadius * circleMultiple + paddingLeft;
                    float circleY = circleRadius * circleMultiple + paddingTop;
                    int space = (mWidth - paddingLeft - paddingRight - circleRadius * 2) / (stepNum - 1);
                    for (int i = 0; i < 4; i++) {
                        if (Math.abs(circleX + space * i - downX) < circleRadius * circleMultiple && (Math.abs(circleY - downY) < circleRadius * circleMultiple)) {
                            if (mListener != null) {
                                mListener.onClick(i);
                            }
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                //点击异常处理
                break;
        }
        return true;
    }
}