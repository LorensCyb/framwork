package cyb.xandroid.view.ximage;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * DragBack
 * Created by Porster on 17/6/9.
 */

public class DragFrameLayout extends FrameLayout {
    public static final String TAG = "DragFramlayout";

    public interface OnDragListener {

        void startDrag();

        void endDrag();

        void onFinish();

    }

    private ViewDragHelper mDragHelper;

    //#00000000
    public int DEF_BG_COLOR = 0xff000000;

    /**
     * 拖动是否进行缩放变化
     */
    private boolean mDragScale = true;

    private OnDragListener onDragListener;

    private View mDragView;

    /**
     * 拖拽时是否进行缩放操作，默认TRUE
     *
     * @param dragScale TRUE：是 FALSE：否
     */
    public void setDragScale(boolean dragScale) {
        mDragScale = dragScale;
    }

    public DragFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setOnDragListener(OnDragListener listener) {
        this.onDragListener = listener;
    }

    private void init() {
        if (getContext() instanceof Activity) {
            ((Activity) getContext()).getWindow().getDecorView().setBackgroundColor(DEF_BG_COLOR);
        }

        mDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            boolean mNeedRelease;
            boolean needDrag;

            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return true;
            }


            @Override
            public void onViewDragStateChanged(int state) {
                super.onViewDragStateChanged(state);

                if (state == ViewDragHelper.STATE_DRAGGING) {
                    if (onDragListener != null) {
                        onDragListener.endDrag();
                    }
                }
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);

                if (mNeedRelease) {
                    if (getContext() instanceof Activity) {
                        if (onDragListener != null) {
                            onDragListener.onFinish();
                        }
                        ((Activity) getContext()).onBackPressed();
                    }
                } else {
                    needDrag = false;
                    mDragHelper.settleCapturedViewAt(finalLeft, finalTop);
                    if (mDragScale) {
                        releasedChild.setScaleX(1.0f);
                        releasedChild.setScaleY(1.0f);
                    }
                    invalidate();

                    if (onDragListener != null) {
                        onDragListener.endDrag();
                    }
                }

            }

            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);
                mDragView = changedView;
                mNeedRelease = top > getHeight() * 0.25;//Release

                //ChangeBg
                float present = 1 - (top * 1.0f) / (getHeight());
                if (getContext() instanceof Activity) {
                    int alpah = Math.min((int) (255 * present), 255);
                    ((Activity) getContext()).getWindow().getDecorView().setBackgroundColor(Color.argb(alpah, 0, 0, 0));
                }
                //ChangeScale
                if (mDragScale) {
                    float maxScale = Math.min(present, 1.0f);//Max,1.0f
                    float minScale = Math.max(0.5f, maxScale);//Min,5.0f;

                    if (present > 1.0f) {
                        present = 1.0f;
                    } else if (present < 0.5f) {
                        present = 0.5f;
                    }
                    changedView.setScaleX(minScale);
                    changedView.setScaleY(minScale);
                }


                Log.i(TAG, "Top=" + top + "Release=" + getHeight() * 0.25 + "Present=" + present);
            }


            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                if (needDrag) {
                    return top;
                }
                if (top < 0) {
                    top = 0;
                } else if (top > 100) {//释放允许任何方向拖拽
                    needDrag = true;
                }
                return top;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                return needDrag ? left : 0;
            }

            @Override
            public int getViewVerticalDragRange(View child) {
                return getHeight() / 2;
            }
        });
    }

    int finalLeft;
    int finalTop;

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        finalLeft = getChildAt(0).getLeft();
        finalTop = getChildAt(0).getTop();
    }

    @Override
    public void computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (event.getPointerCount() > 1) {
            mDragHelper.cancel();
        } else {
            return mDragHelper.shouldInterceptTouchEvent(event);
        }
        return super.onInterceptHoverEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public View getCurrentDragView() {
        return mDragView;
    }
}
