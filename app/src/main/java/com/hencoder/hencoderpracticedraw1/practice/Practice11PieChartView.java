package com.hencoder.hencoderpracticedraw1.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class Practice11PieChartView extends View {
    private static final String TAG = "Practice11PieChartView";
    private static final String NAME = "饼图";
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private List<Data> dataList = new ArrayList<>(8);
    private String[] nameArray = {"Gingerbread", "Ice Cream Sandwich", "Jelly Bean", "KitKat", "Lollipop", "Marshmallow", "Nougat", "Oreo"};
    private float[] valueArray = {0.002f, 0.003f, 0.03f, 0.076f, 0.179f, 0.213f, 0.282f, 0.215f};
    private int[] colorArray = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW};
    private Rect bounds = new Rect();
    private RectF rectF = new RectF();

    public Practice11PieChartView(Context context) {
        super(context);
        init();
    }

    public Practice11PieChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Practice11PieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        for (int i = 0; i < nameArray.length; i++) {
            Data data = new Data(nameArray[i], valueArray[i], colorArray[i]);
            dataList.add(data);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float centerX = getWidth() / 7f * 3f;
        float centerY = getHeight() / 2f;
        float radius = centerX > centerY ? centerY / 3 * 2 : centerX / 3 * 2;
        Log.d(TAG, "onDraw: radius=" + radius);

        float lineStartPointX;
        float lineStartPointY;

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(0);
        paint.setColor(Color.WHITE);
        paint.setTextSize(50);
        paint.getTextBounds(NAME, 0, NAME.length(), bounds);
        canvas.drawText(NAME, centerX - bounds.width() / 2f, getHeight() - 30, paint);
        paint.setTextSize(30);

        canvas.translate(centerX, centerY);
        rectF.set(-radius, -radius, radius, radius);
        float startAngle = 0;
        for (int i = 0; i < dataList.size(); i++) {
            Data data = dataList.get(i);
            paint.setColor(data.color);
            paint.setStyle(Paint.Style.FILL);
            float sweepAngle = 360 * data.value;

            Log.d(TAG, "onDraw: startAngle=" + startAngle + ", sweepAngle=" + sweepAngle);
            lineStartPointX = (float) (Math.cos((startAngle + sweepAngle / 2f) / 180 * Math.PI) * radius);
            lineStartPointY = (float) (Math.sin((startAngle + sweepAngle / 2f) / 180 * Math.PI) * radius);
            Log.d(TAG, "onDraw: lineStartPointX=" + lineStartPointX + ", lineStartPointY=" + lineStartPointY);

            canvas.save();
            canvas.translate(lineStartPointX * 0.03f, lineStartPointY * 0.03f);
            canvas.drawArc(rectF, startAngle, sweepAngle, true, paint);

            paint.setColor(Color.LTGRAY);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(3);
            canvas.drawLine(lineStartPointX, lineStartPointY, lineStartPointX * 1.1f, lineStartPointY * 1.1f, paint);

            if (lineStartPointX >= 0) {
                float textY = paint.getTextSize() / 2f;
                // 文字在右边
                canvas.drawLine(lineStartPointX * 1.1f, lineStartPointY * 1.1f, radius + 60, lineStartPointY * 1.1f, paint);
                canvas.save();
                canvas.translate(radius + 60, lineStartPointY * 1.1f);
                paint.setStyle(Paint.Style.FILL);
                paint.setStrokeWidth(0);
                canvas.drawText(data.name, 5f, textY, paint);
                canvas.restore();
            } else {
                // 文字在左边
                canvas.drawLine(lineStartPointX * 1.1f, lineStartPointY * 1.1f, -(radius + 10), lineStartPointY * 1.1f, paint);
                canvas.save();
                canvas.translate(-(radius + 10), lineStartPointY * 1.1f);
                paint.setStyle(Paint.Style.FILL);
                paint.setStrokeWidth(0);
                canvas.drawText(data.name, -(5f + paint.measureText(data.name)), paint.getTextSize() / 2f, paint);
                canvas.restore();
            }

            canvas.restore();
            startAngle += sweepAngle;
        }

    }

    class Data {
        String name;
        float value;
        int color;

        Data(String name, float value, int color) {
            this.name = name;
            this.value = value;
            this.color = color;
        }
    }
}
