package com.fhh.technology.module.discover.annular;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.RectF;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * desc:
 * Created by fhh on 2018/7/12
 */

public class CustomView extends View {
    private Context mContext;
    private Paint mPaint;
    private int mPaintWidth = 0;        // 画笔的宽
    private int topMargin;         // 上边距
    private int leftMargin;        // 左边距
    private Resources mRes;
    private DisplayMetrics dm;
    private int showRateSize = 10; // 展示文字的大小

    private int circleCenterX;     // 圆心点X  要与外圆半径相等
    private int circleCenterY;     // 圆心点Y  要与外圆半径相等

    private int ringOuterRidus;     // 外圆的半径
    private int ringInnerRidus;     // 内圆的半径
    private int ringPointRidus;    // 点所在圆的半径

    private float rate = 0.4f;     //点的外延距离  与  点所在圆半径的长度比率
    private float extendLineWidth;     //点外延后  折的横线的长度

    private RectF rectF;                // 外圆所在的矩形
    private RectF rectFPoint;           // 点所在的矩形

    private List<Integer> colorList;
    private List<Integer> valueList;
    private List<Float> rateList;
    private List<String> descList;
    private String percent;
    private boolean isRing;
    private boolean isShowCenterPoint;
    private boolean isShowRate;
    private Paint mTextPaint;

    public CustomView(Context context) {
        super(context, null);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
//        initView();

        mRes = mContext.getResources();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        dm = new DisplayMetrics();
    }

    public void setShow(List<Integer> colorList, List<Integer> valueList, List<Float> rateList, String percent) {
        setShow(colorList, valueList, rateList, descList, percent, false);
    }

    public void setShow(List<Integer> colorList, List<Integer> valueList, List<Float> rateList, List<String> descList, String percent, boolean isRing) {
        setShow(colorList, valueList, rateList, descList, percent, isRing, false);
    }

    public void setShow(List<Integer> colorList, List<Integer> valueList, List<Float> rateList, List<String> descList, String percent, boolean isRing, boolean isShowRate) {
        setShow(colorList, valueList, rateList, descList, percent, isRing, isShowRate, false);
    }

    public void setShow(List<Integer> colorList, List<Integer> valueList, List<Float> rateList, List<String> descList, String percent, boolean isRing, boolean isShowRate, boolean isShowCenterPoint) {
        this.colorList = colorList;
        this.valueList = valueList;
        this.rateList = rateList;
        this.descList = descList;
        this.percent = percent;
        this.isRing = isRing;
        this.isShowRate = isShowRate;
        this.isShowCenterPoint = isShowCenterPoint;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        topMargin = getMeasuredWidth() / 24;
        circleCenterX = getMeasuredWidth() / 18;
        circleCenterY = getMeasuredWidth() / 18;
        ringOuterRidus = getMeasuredWidth() / 18;
        ringInnerRidus = getMeasuredWidth() / 24;
        ringPointRidus = getMeasuredWidth() / 20;
        extendLineWidth = getMeasuredWidth() / 36;
        leftMargin = topMargin * 3 + circleCenterX + ringOuterRidus;

        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(dip2px(mPaintWidth));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);

        rectF = new RectF(dip2px(mPaintWidth + leftMargin),
                dip2px(mPaintWidth + topMargin),
                dip2px(circleCenterX + ringOuterRidus + mPaintWidth * 2 + leftMargin),
                dip2px(circleCenterY + ringOuterRidus + mPaintWidth * 2 + topMargin));

        rectFPoint = new RectF(dip2px(mPaintWidth + leftMargin + (ringOuterRidus - ringPointRidus)),
                dip2px(mPaintWidth + topMargin + (ringOuterRidus - ringPointRidus)),
                dip2px(circleCenterX + ringPointRidus + mPaintWidth * 2 + leftMargin),
                dip2px(circleCenterY + ringPointRidus + mPaintWidth * 2 + topMargin));

        Log.e("矩形点:", dip2px(circleCenterX + ringOuterRidus + mPaintWidth * 2)
                + " --- " + dip2px(circleCenterY + ringOuterRidus + mPaintWidth * 2));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        pointList.clear();
        if (colorList != null) {
            for (int i = 0; i < colorList.size(); i++) {
                mPaint.setColor(mRes.getColor(colorList.get(i)));
                mPaint.setStyle(Paint.Style.FILL);
                drawOuter(canvas, i);
            }
        }
        mPaint.setStyle(Paint.Style.FILL);
        if (isRing) {
            drawInner(canvas);
        }
        if (isShowCenterPoint) {
            drawCenterPoint(canvas);
        }

        mTextPaint.setTextSize(20);
        mPaint.setColor(Color.RED);
        canvas.drawText(percent + "%", dip2px(circleCenterX / 2 + leftMargin), dip2px(circleCenterY + topMargin + 2), mTextPaint);
    }

    private void drawCenterPoint(Canvas canvas) {
        mPaint.setColor(Color.RED);
//        Log.e("中心点:", dip2px(circleCenterX + mPaintWidth * 2 + leftMargin) + " --- " + dip2px(circleCenterY + mPaintWidth * 2 + topMargin));
        canvas.drawCircle(dip2px(circleCenterX + mPaintWidth * 2 + leftMargin), dip2px(circleCenterY + mPaintWidth * 2 + topMargin), dip2px(1), mPaint);
    }

    private void drawInner(Canvas canvas) {
        mPaint.setColor(Color.WHITE);
//        Log.e("内部圆点:", dip2px(circleCenterX + mPaintWidth * 2 + leftMargin) + " --- " + dip2px(circleCenterY + mPaintWidth * 2 + topMargin));
        canvas.drawCircle(dip2px(circleCenterX + mPaintWidth * 2 + leftMargin), dip2px(circleCenterY + mPaintWidth * 2 + topMargin), dip2px(ringInnerRidus), mPaint);
    }

    private float preRate;

    private void drawArcCenterPoint(Canvas canvas, int position) {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.TRANSPARENT);
        mPaint.setStrokeWidth(dip2px(1));
//        canvas.drawArc(rectFPoint, preAngle, (endAngle) / 2, true, mPaint);
        dealPoint(rectFPoint, preAngle, (endAngle) / 2, pointArcCenterList);
        Point point = pointArcCenterList.get(position);
        canvas.drawCircle(point.x, point.y, dip2px(2), mPaint);

        if (preRate / 2 + rateList.get(position) / 2 < 5) {
            extendLineWidth += 20;
            rate -= 0.05f;
        } else {
            extendLineWidth = 5;
            rate = 0.25f;
        }

        // 外延画折线
        float lineXPoint1 = (point.x - dip2px(leftMargin + ringOuterRidus)) * (1 + rate);
        float lineYPoint1 = (point.y - dip2px(topMargin + ringOuterRidus)) * (1 + rate);

        float[] floats = new float[8];
        floats[0] = point.x;
        floats[1] = point.y;
        floats[2] = dip2px(leftMargin + ringOuterRidus) + lineXPoint1;
        floats[3] = dip2px(topMargin + ringOuterRidus) + lineYPoint1;
        floats[4] = dip2px(leftMargin + ringOuterRidus) + lineXPoint1;
        floats[5] = dip2px(topMargin + ringOuterRidus) + lineYPoint1;
        if (point.x >= dip2px(leftMargin + ringOuterRidus)) {
            mPaint.setTextAlign(Paint.Align.LEFT);
            floats[6] = dip2px(leftMargin + ringOuterRidus) + lineXPoint1 + dip2px(extendLineWidth);
        } else {
            mPaint.setTextAlign(Paint.Align.RIGHT);
            floats[6] = dip2px(leftMargin + ringOuterRidus) + lineXPoint1 - dip2px(extendLineWidth);
        }
        floats[7] = dip2px(topMargin + ringOuterRidus) + lineYPoint1;
        mPaint.setColor(mRes.getColor(colorList.get(position)));
        canvas.drawLines(floats, mPaint);
        mPaint.setTextSize(dip2px(showRateSize));
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawText(descList.get(position) + valueList.get(position) + "件", floats[6], floats[7] + dip2px(showRateSize) / 3, mPaint);
        preRate = rateList.get(position);
    }

    List<Point> pointList = new ArrayList<>();
    List<Point> pointArcCenterList = new ArrayList<>();

    private void dealPoint(RectF rectF, float startAngle, float endAngle, List<Point> pointList) {
        Path orbit = new Path();
        //通过Path类画一个90度（180—270）的内切圆弧路径
        orbit.addArc(rectF, startAngle, endAngle);

        PathMeasure measure = new PathMeasure(orbit, false);
        Log.e("路径的测量长度:", "" + measure.getLength());

        float[] coords = new float[]{0f, 0f};
        //利用PathMeasure分别测量出各个点的坐标值coords
        int divisor = 1;
        measure.getPosTan(measure.getLength() / divisor, coords, null);
        Log.e("coords:", "x轴:" + coords[0] + " -- y轴:" + coords[1]);
        float x = coords[0];
        float y = coords[1];
        Point point = new Point(Math.round(x), Math.round(y));
        pointList.add(point);
    }

    private void drawOuter(Canvas canvas, int position) {
        if (rateList != null) {
            endAngle = getAngle(rateList.get(position));
        }
        canvas.drawArc(rectF, preAngle, endAngle, true, mPaint);
        if (isShowRate) {
            drawArcCenterPoint(canvas, position);
        }

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