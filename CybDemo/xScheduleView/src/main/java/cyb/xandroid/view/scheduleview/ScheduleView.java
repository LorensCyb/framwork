package cyb.xandroid.view.scheduleview;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;

import java.text.DecimalFormat;

/**
 * Created by asus on 2018/3/13.
 */
public class ScheduleView {

    private final int column = 7;
    private final int row = 12;

    private boolean hasChange = false;
    private boolean onTouch = false;
    private float fromX;
    private float fromY;
    private float space;

    //
    private float startX;
    private float endX;
    private float startY;
    private float endY;

    //
    public float startTime = 20;
    public float endTime = 300;
    private boolean[] weeks = new boolean[column];

    private RectF[] weekRectF = new RectF[column];
    private Paint rectPaint;
    private int repeatBgColor = 0x88A5D7FF;
    private int onceBgColor = 0x88FFCDE1;


    private RectF rectF;
    private Paint outlinePaint;
    private float outlineWidth = 3f;
    private int repeatOutLineColor = 0xFF82B8E0;
    private int onceOutLineColor = 0xFFC87896;

    private Paint txtPaint;
    private int repeatTxtColor = 0xFF517C9C;
    private int onceTxtColor = 0xFFA14B6E;
    private int txtSize = 20;
    private boolean isRepeat = true;

    public ScheduleView(float fromX, float fromY, float space) {
        this.fromX = fromX;
        this.fromY = fromY;
        this.space = space;
        init();
    }

    public void init(float fromX, float fromY, float space) {
        this.fromX = fromX;
        this.fromY = fromY;
        this.space = space;
        init();
    }

    private void init() {
        outlinePaint = new Paint();
        outlinePaint.setStyle(Paint.Style.STROKE);
        outlinePaint.setAntiAlias(true);
        outlinePaint.setStrokeWidth(outlineWidth);

        rectPaint = new Paint();
        rectPaint.setStrokeWidth(2f);
        txtPaint = new Paint();
        txtPaint.setTextSize(txtSize);
    }

    public void draw(Canvas canvas) {
        hasChange = false;
        calculationPosition();
        canvas.save();
        drawSchedule(canvas);
        drawTime(canvas);
        canvas.restore();
    }

    public boolean onTouchEvent(MotionEvent event) {
        boolean b = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                b = isOnTouch(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                b = isOnTouch(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_UP:
                b = false;
                break;
        }
        hasChange = onTouch == b;
        return onTouch = b;
    }

    private boolean isOnTouch(float x, float y) {
        if (x < startX || x > endX || y < startY || y > endY) {
            return false;
        }
        return true;
    }

    public boolean hasChange() {
        return hasChange;
    }

    /**
     * 计算需要绘制的部分的位置；
     ***/
    private void calculationPosition() {
        float width = space * column;
        float height = space * row;

        startY = fromY + startTime / (60 * 24) * height;
        endY = fromY + endTime / (60 * 24) * height;
        int startW = -1;
        int endW = -1;
        for (int i = 0; i < weeks.length; i++) {
            //判断从哪天开始
            if (weeks[i] && startW < 0) {
                startW = i;
                startX = fromX + space * startW;
            }
            // 判断从哪天结束
            if (weeks[weeks.length - 1 - i] && endW <= 0) {
                endW = weeks.length - 1 - i;
                endX = fromX + space * (endW + 1);
            }
        }

        float sX = fromX;
        float eX = space + sX;
        for (int i = 0; i < weeks.length; i++) {
            if (i >= startW && i <= endW) {
                weekRectF[i] = new RectF(sX, startY, eX, endY);
            } else {
                weekRectF[i] = null;
            }
            sX += space;
            eX += space;
        }

        //
        if (rectF == null) {
            rectF = new RectF();
        }
        rectF.set(startX, startY, endX, endY);
    }

    /**
     * 绘制schedule
     */
    private void drawSchedule(Canvas canvas) {
        float lineWidth = outlineWidth / 2;
        rectPaint.setColor(isRepeat ? repeatBgColor : onceBgColor);
        outlinePaint.setColor(isRepeat ? repeatOutLineColor : onceOutLineColor);
        for (int i = 0; i < weekRectF.length; i++) {
            if (weekRectF[i] != null) {
                if (weeks[i]) {
                    canvas.drawRect(weekRectF[i], rectPaint);
                } else {
                    canvas.drawLine(weekRectF[i].left, weekRectF[i].top + lineWidth,
                            weekRectF[i].right, weekRectF[i].top + lineWidth, rectPaint);
                    canvas.drawLine(weekRectF[i].left, weekRectF[i].bottom - outlineWidth,
                            weekRectF[i].right, weekRectF[i].bottom - outlineWidth, rectPaint);
                }
            }
        }

        if (onTouch) {
            canvas.drawRect(rectF, outlinePaint);
        }
    }

    /**
     * 绘制时间
     */
    private void drawTime(Canvas canvas) {
        DecimalFormat df = new DecimalFormat("00");
        String time = df.format(startTime / 60) + ":" + df.format(startTime % 60);
        time += "-" + df.format(endTime / 60) + ":" + df.format(endTime % 60);
        if (isRepeat) {
            txtPaint.setColor(repeatTxtColor);
        } else {
            txtPaint.setColor(onceTxtColor);
        }
        Rect rect = new Rect();
        txtPaint.getTextBounds(time, 0, time.length(), rect);
        canvas.drawText(time, startX + 10, startY + 10 + rect.height(), txtPaint);
        if (!isRepeat) {
            time = "only once";
            canvas.drawText(time, startX + 10, startY + 20 + rect.height() * 2, txtPaint);
        }
    }

    /**
     * 设置schudle的时间；
     **/
    public void setTime(boolean repeat, int startTime, int endTime, boolean... week) {
        this.isRepeat = repeat;
        this.startTime = startTime;
        this.endTime = endTime;
        for (int i = 0; i < column; i++) {
            if (week == null || week.length == 0) {
                weeks[i] = false;
            } else {
                weeks[i] = week[i];
            }
        }
    }

    /**
     * 设置schudle的时间；
     **/
    public void setTime(boolean repeat, int startTime, int endTime, int... week) {
        this.isRepeat = repeat;
        this.startTime = startTime;
        this.endTime = endTime;
        for (int i = 0; i < column; i++) {
            if (week == null || week.length == 0) {
                weeks[i] = false;
            } else {
                for (int w : week) {
                    if (i == w) {
                        weeks[i] = true;
                        break;
                    }
                }
            }
        }
    }
}
