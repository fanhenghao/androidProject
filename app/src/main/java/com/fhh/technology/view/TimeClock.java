package com.fhh.technology.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.fhh.technology.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * desc:自定义时钟
 * Created by fhh on 2018/9/21
 */

public class TimeClock extends View {

    //外圆画笔
    private Paint paint;
    //文字画笔
    private Paint paintNum;
    //时钟画笔
    private Paint paintHour;
    //分钟画笔
    private Paint paintMinute;
    //秒钟画笔
    private Paint paintSecond;
    //外圆圆心
    private float x, y;
    //外圆半径
    private int r;

    public TimeClock(Context context) {
        super(context);
        initPaint();
    }

    public TimeClock(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public TimeClock(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }


    private void initPaint() {
        paint = new Paint();
        paint.setColor(getResources().getColor(R.color.colorPrimary));
        paint.setAntiAlias(true);
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.STROKE);

        paintNum = new Paint();
        paintNum.setColor(getResources().getColor(R.color.text_normal_color));
        paintNum.setAntiAlias(true);
        paintNum.setTextSize(35);
        paintNum.setStyle(Paint.Style.STROKE);
        paintNum.setTextAlign(Paint.Align.CENTER);

        paintSecond = new Paint();
        paintSecond.setColor(Color.RED);
        paintSecond.setAntiAlias(true);
        paintSecond.setStrokeWidth(5);
        paintSecond.setStyle(Paint.Style.FILL);

        paintMinute = new Paint();
        paintMinute.setColor(getResources().getColor(R.color.text_tab_s));
        paintMinute.setAntiAlias(true);
        paintMinute.setStrokeWidth(8);
        paintMinute.setStyle(Paint.Style.FILL);

        paintHour = new Paint();
        paintHour.setColor(getResources().getColor(R.color.text_tab_s));
        paintHour.setAntiAlias(true);
        paintHour.setStrokeWidth(13);
        paintHour.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = getMeasuredHeight();
        int width = getMeasuredWidth();
        x = width / 2;
        y = height / 2;
        r = (int) x - 5;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制外圆
        canvas.drawCircle(x, y, r, paint);

        //绘制圆心
        canvas.drawCircle(x, y, 15, paintMinute);

        //绘制刻度
        drawLines(canvas);

        //绘制整点
        drawText(canvas);

        try {
            initCurrentTime(canvas);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //每隔1s刷新界面
        postInvalidateDelayed(1000);
    }


    /**
     * 绘制时钟刻度和分钟刻度
     *
     * @param canvas 画布
     */
    private void drawLines(Canvas canvas) {
        for (int i = 0; i < 60; i++) {
            if (i % 5 == 0) {
                //绘制整点刻度
                paint.setStrokeWidth(8);
                canvas.drawLine(x, y - r, x, y - r + 40, paint);
            } else {
                //绘制分钟刻度
                paint.setStrokeWidth(3);
                canvas.drawLine(x, y - r, x, y - r + 30, paint);
            }
            //绕着(x,y)旋转6°
            canvas.rotate(6, x, y);
        }
    }

    /**
     * 绘制整点数字
     *
     * @param canvas 画布
     */
    private void drawText(Canvas canvas) {
        // 获取文字高度用于设置文本垂直居中
        float textSize = (paintNum.getFontMetrics().bottom - paintNum.getFontMetrics().top);
        // 数字离圆心的距离,40为刻度的长度,20文字大小
        int distance = r - 40 - 20;
        // 数字的坐标(a,b)
        float a, b;
        // 每30°写一个数字
        for (int i = 0; i < 12; i++) {
            a = (float) (distance * Math.sin(i * 30 * Math.PI / 180) + x);
            b = (float) (y - distance * Math.cos(i * 30 * Math.PI / 180));
            if (i == 0) {
                canvas.drawText("12", a, b + textSize / 3, paintNum);
            } else {
                canvas.drawText(String.valueOf(i), a, b + textSize / 3, paintNum);
            }
        }
    }

    /**
     * 获取当前系统时间
     *
     * @param canvas 画布
     */
    private void initCurrentTime(Canvas canvas) {
        //获取系统当前时间
        SimpleDateFormat format = new SimpleDateFormat("HH-mm-ss");
        String time = format.format(new Date(System.currentTimeMillis()));
        String[] split = time.split("-");
        int hour = Integer.parseInt(split[0]);
        int minute = Integer.parseInt(split[1]);
        int second = Integer.parseInt(split[2]);
        //时针走过的角度
        int hourAngle = hour * 30 + minute / 2;
        //分针走过的角度
        int minuteAngle = minute * 6 + second / 10;
        //秒针走过的角度
        int secondAngle = second * 6;

        //绘制时钟,以12整点为0°参照点
        canvas.rotate(hourAngle, x, y);
        canvas.drawLine(x, y, x, y - r + 150, paintHour);
        canvas.save();
        canvas.restore();
        //这里画好了时钟，我们需要再将画布转回来,继续以12整点为0°参照点
        canvas.rotate(-hourAngle, x, y);

        //绘制分钟
        canvas.rotate(minuteAngle, x, y);
        canvas.drawLine(x, y, x, y - r + 60, paintMinute);
        canvas.save();
        canvas.restore();
        //这里同上
        canvas.rotate(-minuteAngle, x, y);

        //绘制秒钟
        canvas.rotate(secondAngle, x, y);
        canvas.drawLine(x, y, x, y - r + 20, paintSecond);
    }

}
