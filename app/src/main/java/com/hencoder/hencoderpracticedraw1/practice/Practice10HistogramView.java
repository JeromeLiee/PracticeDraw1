package com.hencoder.hencoderpracticedraw1.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class Practice10HistogramView extends View {
    private static final String NAME = "直方图";
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private List<Data> dataList = new ArrayList<>(8);
    private String[] nameArray = {"GB", "ICS", "JB", "KitKat", "L", "M", "N", "Oreo"};
    private float[] valueArray = {0.002f, 0.003f, 0.030f, 0.076f, 0.179f, 0.213f, 0.282f, 0.215f};
    private Rect bounds = new Rect();

    public Practice10HistogramView(Context context) {
        super(context);
        init();
    }

    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        for (int i = 0; i < nameArray.length; i++) {
            Data data = new Data(nameArray[i], valueArray[i]);
            dataList.add(data);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float xUnit = getWidth() / 10f;
        float yUnit = getHeight() / 10f;

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.WHITE);
        canvas.drawLine(xUnit, yUnit, xUnit, yUnit * 8, paint);
        canvas.drawLine(xUnit, yUnit * 8, xUnit * 9, yUnit * 8, paint);

        paint.setTextSize(50);
        paint.getTextBounds(NAME, 0, NAME.length(), bounds);
        canvas.drawText(NAME, xUnit * 5 - bounds.width() / 2f, yUnit * 9f + bounds.height() / 2f, paint);

        float dataUnit = xUnit * 8 / dataList.size();
        float xDataUnitCenter;
        float yDataUnitCenter = yUnit * 8;
        for (int i = 0; i < dataList.size(); i++) {
            Data data = dataList.get(i);
            paint.setTextSize(30);
            paint.getTextBounds(data.name, 0, data.name.length(), bounds);
            xDataUnitCenter = xUnit + dataUnit * i + dataUnit / 2f;
            canvas.save();
            canvas.translate(xDataUnitCenter, yDataUnitCenter);

            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.WHITE);
            canvas.drawText(data.name, -bounds.width() / 2f, bounds.height() + 10, paint);

            String percent = data.value * 100 + "%";
            paint.getTextBounds(percent, 0, percent.length(), bounds);
            canvas.drawText(percent, -bounds.width() / 2f, -(yUnit * 7) * 3 * data.value - 10, paint);

            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.GREEN);
            canvas.drawRect(-dataUnit / 5f * 2, -(yUnit * 7) * 3 * data.value, dataUnit / 5f * 2, 0, paint);
            canvas.restore();
        }
    }

    class Data {
        String name;
        float value;

        Data(String name, float value) {
            this.name = name;
            this.value = value;
        }
    }
}
