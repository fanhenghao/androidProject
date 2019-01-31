package com.fhh.technology.module.discover.annular;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.List;

/**
 * desc:自定义获取案由环形图
 * Created by fhh on 2018/11/14
 */
public class CustomView2 extends View {
    private Context mContext;
    private Paint mPaint;
    private Resources mRes;
    private RectF rectF;                // 外圆所在的矩形
    private DisplayMetrics dm;
    private List<Integer> colorList;
    private List<Float> rateList;
    private List<String> descList;
    private int measuredWidth;
    private int mMeasuredHeight;
    private float mInnerRadius;

    public CustomView2(Context context) {
        super(context, null);
    }

    public CustomView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        mRes = mContext.getResources();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dm = new DisplayMetrics();
    }

    public void setShow(List<Integer> colorList, List<Float> rateList, List<String> descList) {
        this.colorList = colorList;
        this.rateList = rateList;
        this.descList = descList;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measuredWidth = getMeasuredWidth();
        mMeasuredHeight = getMeasuredHeight();
        mInnerRadius = (float) (measuredWidth / 2.6);
        rectF = new RectF(0, 0, measuredWidth, mMeasuredHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (colorList != null) {
            for (int i = 0; i < colorList.size(); i++) {
                mPaint.setColor(mRes.getColor(colorList.get(i)));
                mPaint.setStyle(Paint.Style.FILL);
                mPaint.setAntiAlias(true);
                drawOuter(canvas, i);
            }
        }
        drawInner(canvas);
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(30);
        mPaint.setTextAlign(Paint.Align.CENTER);

        @SuppressLint("DrawAllocation") Point point = new Point(measuredWidth / 2, mMeasuredHeight / 2 + 15);
        textCenter(descList, mPaint, canvas, point);
    }

    private void textCenter(List<String> strings, Paint paint, Canvas canvas, Point point) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float top = fontMetrics.top;
        float bottom = fontMetrics.bottom;
        int size = strings.size();
        float total = (size - 1) * (-top + bottom) + (-fontMetrics.ascent + fontMetrics.descent);
        float offset = total / 2 - bottom;
        for (int i = 0; i < size; i++) {
            float yAxis = -(size - i - 1) * (-top + bottom) + offset;
            canvas.drawText(strings.get(i) + "", point.x, point.y + yAxis, paint);
        }
    }

    private void drawInner(Canvas canvas) {
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(measuredWidth / 2, mMeasuredHeight / 2, mInnerRadius, mPaint);
    }

    private void drawOuter(Canvas canvas, int position) {
        if (rateList != null) {
            endAngle = getAngle(rateList.get(position));
        }
        canvas.drawArc(rectF, preAngle, endAngle, true, mPaint);
        preAngle = preAngle + endAngle;
    }

    private float preAngle = -90;
    private float endAngle = -90;

    /**
     * @param percent 百分比
     * @return
     */
    private float getAngle(float percent) {
        float a = 360f / 100f * percent;
        return a;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dip2px(float dpValue) {
        return (int) (dpValue * getContext().getResources().getDisplayMetrics().density + 0.5f);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int px2dip(float pxValue) {
        return (int) (pxValue / getContext().getResources().getDisplayMetrics().density + 0.5f);
    }
}
