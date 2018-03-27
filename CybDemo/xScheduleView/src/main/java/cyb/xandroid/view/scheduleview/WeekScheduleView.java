package cyb.xandroid.view.scheduleview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cyb.xandroid.util.DateUtil;

/**
 * Created by asus on 2018/3/12.
 */
public class WeekScheduleView extends View {

    public interface OnSchduleClickListener {
        void onScheduleClick(List<ScheduleView> schedules, int x, int y);

        void onHourClick(ScheduleView schedules);
    }

    //
    private float marginLeft = 100;
    private float marginTop = 100;
    private float marginRight = 50;
    private float marginBottom = 50;

    //
    private final int column = 7;
    private final int row = 12;
    private float space = 0;
    private boolean isMove = false;
    private Gesture mGesture;

    //绘制网格
    private Paint mGridPaint;
    private int mLimeColor = 0XFFEEEEEE;

    //绘制小时参数
    private int mHourTextColor = 0XFFAAAAAA;
    private float mHourTextWidth = 0;
    private float mHourTextHeight = 0;
    private float mHourTextSize = 30;
    private Paint mHourTextPaint;

    //绘制星期参数
    private int mWeekTextColor = 0XFF999999;
    private int mCurrWeekTextColor = 0XFF2B92F9;
    private float mWeekTextWidth = 0;
    private float mWeekTextHeight = 0;
    private float mWeekTextSize = 40;
    private Paint mWeekTextPaint;

    //绘制点击事件
    private Paint mPressPaint;
    private int mPressColor = 0xFFCCCCCC;
    private float mEventX = -1;
    private float mEventY = -1;
    private float mEventPX = -1;
    private float mEventPY = -1;

    private List<ScheduleView> onTouchSchedules = null;
    private OnSchduleClickListener onClickListener;
    private List<Schedule> timeInfoList = new ArrayList<>();
    private List<ScheduleView> mScheduleViewList = new ArrayList<>();

    public WeekScheduleView(Context context) {
        this(context, null);
    }

    public WeekScheduleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeekScheduleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mGesture = new Gesture();
        //
        mHourTextPaint = new Paint();
        mHourTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mHourTextPaint.setTextAlign(Paint.Align.RIGHT);
        mHourTextPaint.setTextSize(mHourTextSize);
        mHourTextPaint.setColor(mHourTextColor);
        Rect rect = new Rect();
        mHourTextPaint.getTextBounds("00:00", 0, "00:00".length(), rect);
        mHourTextHeight = rect.height();
        mHourTextWidth = rect.width();

        //
        mWeekTextPaint = new Paint();
        mWeekTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mWeekTextPaint.setTextAlign(Paint.Align.RIGHT);
        mWeekTextPaint.setTextSize(mWeekTextSize);
        mWeekTextPaint.setColor(mWeekTextColor);
        Rect rect1 = new Rect();
        mWeekTextPaint.getTextBounds("00:00", 0, "00:00".length(), rect1);
        mWeekTextHeight = rect.height();
        mWeekTextWidth = rect.width();

        //绘制网格
        mGridPaint = new Paint();
        mGridPaint.setColor(mLimeColor);
        mGridPaint.setStrokeWidth(2f);

        //绘制点击时
        mPressPaint = new Paint();
        mPressPaint.setColor(mPressColor);

        //
        marginLeft = (int) mWeekTextWidth + 50;
        marginTop = (int) mWeekTextHeight + 50;
        marginRight = 50;
        marginBottom = 50;
        //
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //判断是否触碰到schedule
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mGesture.onTouch(event);
                onTouchSchedules = onScheduleTouch(event);
                if (onTouchSchedules.size() == 0) {
                    calculationPosition(event.getX(), event.getY());
                } else {
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                mGesture.onTouch(event);
                onTouchSchedules = onScheduleTouch(event);
                if (onTouchSchedules.size() == 0) {
                    calculationPosition(event.getX(), event.getY());
                } else {
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                onScheduleTouch(event);
                if (Gesture.ACTION_MOVE != mGesture.onTouch(event)) {
                    if (onTouchSchedules.size() == 0) {
                        onHourClick(event);
                    } else {
                        onScheduleClick(event, onTouchSchedules);
                    }
                }
                isMove = false;
                mEventPX = -1;
                mEventPY = 1;
                invalidate();
                break;
        }
        return true;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        //计算绘制相关参数
        calculation();
        //绘制小时
        drawHoursTxt(canvas);
        //绘制星期
        drawWeekTxt(canvas);
        //绘制表格
        drawHours(canvas);
        //绘制点击位置
        drawPressPosition(canvas);
        //绘制schedule
        drawSchedule(canvas);
    }

    private void calculation() {
        //计算绘制网格的区域
        float width = getWidth() - marginLeft - marginRight
                - getPaddingLeft() - getPaddingRight();
        float height = getHeight() - marginTop - marginBottom
                - getPaddingTop() - getPaddingBottom();

        //计算每个格子的宽度
        final float spaceWidth = width / column;   //长宽间隔
        final float spaceHeight = height / row;
        if (spaceWidth > spaceHeight) {
            space = spaceHeight;
        } else {
            space = spaceWidth;
        }

        //
        marginRight = getWidth() - marginLeft - space * column;
        if (marginRight > marginLeft - mHourTextWidth) {
            float b = marginRight;
            marginRight = (marginRight + marginLeft - mHourTextWidth) / 2;
            marginLeft += b - marginRight;
        }

        //
        marginBottom = getHeight() - marginTop - space * row;
        if (marginBottom > marginTop - mWeekTextHeight) {
            float b = marginBottom;
            marginBottom = (marginBottom + marginTop - mWeekTextHeight) / 2;
            marginTop += b - marginBottom;
        }

        //重新计算字体大小
        mHourTextSize = mWeekTextSize = space / 3;
        mHourTextPaint.setTextSize(mHourTextSize);
        mWeekTextPaint.setTextSize(mWeekTextSize);

        //重新计算
        for (ScheduleView schedule : mScheduleViewList) {
            schedule.init(marginLeft, marginTop, space);
        }
    }

    /**
     * 绘制左边小时
     */
    private void drawHoursTxt(Canvas canvas) {
        float x = marginLeft - 20;
        float y = marginTop + mHourTextHeight / 2;
        for (int i = 0; i <= 12; i++) {
            canvas.drawText(String.format("%s:00", i * 2), x, y, mHourTextPaint);
            y += space;
        }
    }

    /**
     * 绘制星期
     */
    private void drawWeekTxt(Canvas canvas) {
        float x = marginLeft;
        float y = marginTop - 20;
        Rect rect1 = new Rect();
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("EEE");
        String week = "";
        int currweek = DateUtil.getDayOfWeek();
        for (int i = 0; i < 7; i++) {
            //889032704 周日，86400000一天的时间
            currentTime.setTime(889032704 + 86400000 * i);
            week = formatter.format(currentTime);
            mWeekTextPaint.getTextBounds(week, 0, week.length(), rect1);
            mWeekTextPaint.setColor(currweek == i ? mCurrWeekTextColor : mWeekTextColor);
            canvas.drawText(week, x + (space - rect1.width()) / 2 + rect1.width(), y, mWeekTextPaint);
            x += space;
        }
    }

    /**
     * 绘制网格
     */
    private void drawHours(Canvas canvas) {
        float hortz = marginLeft;
        float vertz = marginTop;
        final float width = space * column;
        final float height = space * row;

        //draw vertical line
        for (int i = 0; i <= column; i++) {
            canvas.drawLine(hortz, marginTop, hortz, height + marginTop, mGridPaint);
            hortz += space;
        }

        //draw horizontal line
        for (int j = 0; j <= row; j++) {
            canvas.drawLine(marginLeft, vertz, width + marginLeft, vertz, mGridPaint);
            if (j < row) {
                canvas.drawLine(marginLeft, vertz + space / 2,
                        marginLeft + 20, vertz + space / 2, mGridPaint);
            }
            vertz += space;
        }
    }

    /**
     * 计数触碰的位置
     */
    private boolean calculationPosition(float eventX, float eventY) {
        if (eventX < marginLeft || eventX > (marginLeft + space * column)
                || eventY < marginTop || eventY > (marginTop + space * row)) {
            mEventPX = -1;
            mEventPY = 1;
            invalidate();
            return false;
        }
        int x = (int) ((eventX - marginLeft) / space);
        int y = (int) ((eventY - marginTop) / space);
        if (x != mEventPX || y != mEventPY) {
            mEventPX = x;
            mEventPY = y;
            invalidate();
        }
        return true;
    }

    /**
     * 绘制点击时的背景色
     */
    private void drawPressPosition(Canvas canvas) {
        if (mEventPY < 0 || mEventPY >= row || mEventPX < 0 || mEventPX >= column) {
            return;
        }
        canvas.save();
        RectF pressRectF = new RectF(marginLeft + space * mEventPX, marginTop + space * mEventPY,
                marginLeft + space * (mEventPX + 1), marginTop + space * (mEventPY + 1));
        canvas.drawRect(pressRectF, mPressPaint);
    }


    /**
     * 判断是否点击到Schedule;
     */
    private List<ScheduleView> onScheduleTouch(MotionEvent event) {
        List<ScheduleView> schedules = new ArrayList<>();
        for (ScheduleView schedule : mScheduleViewList) {
            if (schedule.onTouchEvent(event)) {
                schedules.add(schedule);
            }
        }
        return schedules;
    }

    /**
     * 判断是否有shedule 改变；
     */
    private boolean onScheduleChange() {
        int count = 0;
        for (ScheduleView schedule : mScheduleViewList) {
            if (schedule.hasChange()) {
                count++;
            }
        }
        return count > 0;
    }

    /**
     * 绘制schedule
     */
    private void drawSchedule(Canvas canvas) {
        for (ScheduleView schedule : mScheduleViewList) {
            schedule.draw(canvas);
        }
    }

    /**
     * 点击schedule
     */
    private void onScheduleClick(MotionEvent event, List<ScheduleView> schedules) {
        if (event.getAction() == MotionEvent.ACTION_UP && onClickListener != null) {
            int x = (int) ((event.getX() - marginLeft) / space);
            int y = (int) ((event.getY() - marginTop) / space);
            x = (int) (marginLeft + x * space + space / 2);
            y = (int) (marginTop + y * space + space / 2);
            onClickListener.onScheduleClick(schedules, x - getWidth() / 2, y - getHeight() / 2);
            onTouchSchedules = null;
        }
    }

    /**
     * 点击小时返回
     */
    private void onHourClick(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP && onClickListener != null
                && mEventPY >= 0 && mEventPX >= 0) {

//            onClickListener.onHourClick(schedule);
        }
    }

    public void setOnScheduleClickListener(OnSchduleClickListener listener) {
        onClickListener = listener;
    }


    class Gesture {
        public static final int ACTION_CLICK = -1;
        public static final int ACTION_LONG_CLICK = -2;
        public static final int ACTION_MOVE = 2;

        private float mDownX;
        private float mDownY;
        private float moveX;
        private float moveY;
        private long currentMS;

        public int onTouch(MotionEvent event) {
            int action = ACTION_CLICK;
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    action = MotionEvent.ACTION_DOWN;
                    mDownX = event.getX();//float DownX
                    mDownY = event.getY();//float DownY
                    moveX = 0;
                    moveY = 0;
                    //long currentMS     获取系统时间
                    currentMS = System.currentTimeMillis();
                    break;
                case MotionEvent.ACTION_MOVE:
                    action = ACTION_MOVE;
                    moveX += Math.abs(event.getX() - mDownX);//X轴距离
                    moveY += Math.abs(event.getY() - mDownY);//y轴距离
                    mDownX = event.getX();
                    mDownY = event.getY();
                    break;
                //移动时间
                case MotionEvent.ACTION_UP:
                    long moveTime = System.currentTimeMillis() - currentMS;
                    //判断是否继续传递信号
                    if (moveTime > 200 && (moveX > 20 || moveY > 20)) {
                        //不再执行后面的事件，在这句前可写要执行的触摸相关代码。点击事件是发生在触摸弹起后
                        action = ACTION_MOVE;
                    } else if (moveTime > 1000 && (moveX < 20 || moveY < 20)) {
                        action = ACTION_LONG_CLICK;
                    }
                    break;
            }
            return action;//继续执行后面的代码
        }
    }

}
