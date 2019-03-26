package com.fhh.technology.module.home.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.fhh.technology.utils.StringUtils;

import androidx.annotation.Nullable;

/**
 * Created by fanhenghao
 * desc:自定义基本几何图形
 */
public class CustomDraw extends View {

    private int mMeasuredWidth;
    private int mMeasuredHeight;
    private Context mContext;

    public CustomDraw(Context context) {
        this(context, null);
    }

    public CustomDraw(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomDraw(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpec = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpec = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        mMeasuredWidth = getMeasuredWidth();
        mMeasuredHeight = getMeasuredHeight();
        Log.e("fhh", "widthMeasureSpec:" + widthMeasureSpec + ",heightMeasureSpec:" + heightMeasureSpec + ",mMeasuredWidth:" +
                mMeasuredWidth + ",mMeasuredHeight:" + mMeasuredHeight + ",widthSpec:" + widthSpec + ",widthMode:" + widthMode + ",heightSpec:" + heightSpec + ",heightMode:" + heightMode);
    }

    /**
     * 绘制都在onDraw方法里
     *
     * @param canvas 画板
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setAntiAlias(true);//抗锯齿
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);//Paint.Style.FILL(填充内部);Paint.Style.FILL_AND_STROKE(填充内部和描边);Paint.Style.STROKE(仅描边)
        paint.setStrokeWidth(15);//设置画笔的宽度（单位px）

//        setShadowLayer (float radius, float dx, float dy, int color)    添加阴影
//        参数：
//        radius:阴影的倾斜度
//        dx:水平位移
//        dy:垂直位移
        paint.setShadowLayer(40, 10, 10, Color.BLACK);//设置阴影(对于几何图形似乎没有什么用)
        canvas.drawRGB(255, 255, 255);//设置画布的颜色
        canvas.drawCircle(mMeasuredWidth / 2, mMeasuredHeight / 4, mMeasuredHeight / 5, paint);

//        void drawLine (float startX, float startY, float stopX, float stopY, Paint paint)
//        参数：
//        startX:开始点X坐标
//        startY:开始点Y坐标
//        stopX:结束点X坐标
//        stopY:结束点Y坐标
        //绘制一条线
        Paint paintLine = new Paint();
        paintLine.setAntiAlias(true);
        paintLine.setStyle(Paint.Style.FILL);
        paintLine.setColor(Color.BLACK);
        paintLine.setStrokeWidth(5);
        canvas.drawLine(mMeasuredWidth / 3, mMeasuredHeight / 3, 0, mMeasuredHeight / 6, paintLine);

        //绘制一个点
        Paint paintPoint = new Paint();
        paintPoint.setColor(Color.RED);  //设置画笔颜色
        paintPoint.setStyle(Paint.Style.FILL);//设置填充样式
        paintPoint.setStrokeWidth(15);//设置画笔宽度
        canvas.drawPoint(mMeasuredWidth / 3, mMeasuredHeight / 20, paintPoint);

        //绘制矩形
        Paint paintRect = new Paint();
        paintRect.setColor(Color.BLUE);
        paintRect.setStyle(Paint.Style.FILL);
        paintRect.setStrokeWidth(10);
//        Rect rect = new Rect(mMeasuredWidth / 3 * 2, mMeasuredHeight/3, mMeasuredWidth, mMeasuredHeight/2);
        canvas.drawRect(mMeasuredWidth / 3 * 2, 30, mMeasuredWidth, mMeasuredHeight / 2, paintRect);

        //带圆角的矩形
        /*void drawRoundRect (RectF rect, float rx, float ry, Paint paint)
          参数：
          RectF rect:要画的矩形
          float rx:生成圆角的椭圆的X轴半径
          float ry:生成圆角的椭圆的Y轴半径*/
        Paint paintRect2 = new Paint();
        paintRect2.setAntiAlias(true);
        paintRect2.setStrokeWidth(10);
        paintRect2.setStyle(Paint.Style.FILL);
        paintRect2.setColor(Color.DKGRAY);
        RectF rect2 = new RectF(mMeasuredWidth / 20, mMeasuredHeight / 2, mMeasuredWidth / 5, mMeasuredHeight / 3 * 2);
        canvas.drawRoundRect(rect2, 20, 20, paintRect2);

        //椭圆
        Paint paintOval = new Paint();
        paintOval.setAntiAlias(true);
        paintOval.setColor(Color.RED);  //设置画笔颜色
        paintOval.setStyle(Paint.Style.STROKE);//填充样式改为描边
        paintOval.setStrokeWidth(5);//设置画笔宽度
        RectF rect = new RectF(mMeasuredWidth / 4, mMeasuredHeight / 5 * 2, mMeasuredWidth / 5 * 2, mMeasuredHeight / 7 * 6);
        canvas.drawOval(rect, paintOval);//同一个矩形画椭圆


        //画弧
        //void drawArc (RectF oval, float startAngle, float sweepAngle, boolean useCenter, Paint paint)
        //参数：
        //RectF oval:生成椭圆的矩形
        //float startAngle：弧开始的角度，以X轴正方向为0度
        //float sweepAngle：弧持续的角度
        //boolean useCenter:是否有弧的两边，True，还两边，False，只有一条弧
        Paint paintArc = new Paint();
        paintArc.setAntiAlias(true);
        paintArc.setColor(Color.LTGRAY);  //设置画笔颜色
        paintArc.setStyle(Paint.Style.STROKE);//填充样式改为描边
        paintArc.setStrokeWidth(5);//设置画笔宽度

        RectF rectArc = new RectF(mMeasuredWidth / 2, mMeasuredHeight / 2, mMeasuredWidth - 30, mMeasuredHeight / 3 * 2);
        canvas.drawArc(rectArc, 0, 90, true, paintArc);

        RectF rectArc2 = new RectF(mMeasuredWidth / 2, mMeasuredHeight / 2 + 50, mMeasuredWidth - 50, mMeasuredHeight - 20);
        canvas.drawArc(rectArc2, 0, 90, false, paintArc);

    }
}
