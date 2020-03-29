package cyb.xandroid.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * Android 4.4之前，Android的状态栏是黑色的，无法修改，Android 4.4 推出了渐变和透明的状态栏效果
 * Android 5.0 提供了方法可以直接修改稿状态栏的颜色。
 * <p>
 * 状态栏的几种效果
 * 1.隐藏状态栏
 * 2.半透明效果（4.4 是渐变，5.0是半透明效果）
 * 3.透明效果状态栏
 * （1） 状态栏与Activity背景色相同
 * （2） 状态栏与Toolbar背景色相同
 * 4.设置状态栏颜色，5.0 （API 21）
 * <p>
 * <p>
 * 1.状态栏的显示隐藏
 * 2.状态栏的颜色（渐变，半透明，透明，颜色）
 * 3.状态栏图标是 暗色或灰色
 * 4.Activity 是否全屏显示
 */
public class StatusBarUtil {

    private static final int INVALID_VAL = -1;
    private static final int COLOR_DEFAULT = Color.WHITE;

    /**
     * 隐藏状态栏
     * <p>
     * 其它方法
     * 1.在 setContentView 之后调用：
     * getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
     * <p>
     * <p>
     * 2.定义主题：
     * android:theme=“@android:style/Theme.NoTitleBar.Fullscreen”
     *
     * @param activity 页面视图
     */
    public static void hideStatusBart(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(option);
    }

    /**
     * 设置状态栏半透明, Android 4.4（API 19）
     * <p>
     * 其他方法
     * 1.<item name=“android.windowTranslucentStatus”>true</item>
     *
     * @param activity 页面视图
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void setTranslucentStatus(Activity activity, boolean on) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                Window win = activity.getWindow();
                WindowManager.LayoutParams winParams = win.getAttributes();
                final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                if (on) {
                    winParams.flags |= bits;
                } else {
                    winParams.flags &= ~bits;
                }
                win.setAttributes(winParams);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 设置状态栏透明，Android 5.0 （API 21）
     * <p>
     * 特别注意：
     * <item name="android:windowTranslucentStatus">false</item> 不然还是半透明的效果
     *
     * @param activity 页面视图
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void setTransparentStatus(Activity activity) {
        setStatusBarColor(activity, Color.TRANSPARENT);
    }


    /**
     * 设置状态栏的颜色
     *
     * @param activity
     * @param color
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void setStatusBarColor(Activity activity, @ColorInt int color) {
        if (color == INVALID_VAL) {
            color = COLOR_DEFAULT;
        }

        // 5.0 及5.0以上的设置状态栏的颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            View decorView = window.getDecorView();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (decorView != null) {
                int vis = decorView.getSystemUiVisibility();
                // 如果是白色状态栏显示为黑色
                if (Color.WHITE == color) {
                    vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                }
                // 如果不是状态栏显示为白色
                else {
                    vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                }
                decorView.setSystemUiVisibility(vis);
            }
            window.setStatusBarColor(color);
            return;
        }
        // 4.4 版本设置状态栏的颜色
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            ViewGroup contentView = activity.findViewById(android.R.id.content);
            View statusBarView = new View(activity);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    getStatusBarHeight(activity));
            statusBarView.setBackgroundColor(color);
            contentView.addView(statusBarView, lp);
        }
    }

    /**
     * 状态栏设置
     *
     * @param activity                 页面视图
     * @param statusBarColor           是状态栏的颜色
     * @param fullScreen               是否全屏，即状ctivity是否延申到状态下面，让状态栏浮activity
     * @param withoutUseStatusBarColor 是否不需要使用状态栏为暗色调，设置状态栏图标的颜色
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void setStatusBar(Activity activity, int statusBarColor, boolean fullScreen,
                                    boolean withoutUseStatusBarColor) {
        Window window = activity.getWindow();
        //6.0及以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decorView = window.getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            if (!withoutUseStatusBarColor || statusBarColor == Color.WHITE) {
                option = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            if (fullScreen) {
                option = option | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            }
            decorView.setSystemUiVisibility(option);
            window.setStatusBarColor(statusBarColor);
        }
        //5.0和5.1
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = window.getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            if (!withoutUseStatusBarColor || statusBarColor == Color.WHITE) {
                option = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            if (fullScreen) {
                option = option | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            }
            decorView.setSystemUiVisibility(option);
            window.setStatusBarColor(statusBarColor);
        }
        //4.4
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (fullScreen) {
                WindowManager.LayoutParams localLayoutParams = window.getAttributes();
                localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                        | localLayoutParams.flags);
            } else {

            }
        }
    }

    /**
     * 获取状态栏的高度
     *
     * @param context 上下文
     */
    public static int getStatusBarHeight(Context context) {
        int height = 0;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int h = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            height = context.getResources().getDimensionPixelSize(h);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return height;
    }

}
