package cyb.xandroid.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.annotation.RequiresPermission;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import java.lang.reflect.Method;
import java.util.Locale;

/**
 * Created by asus on 2018/6/6.
 */

public class SystemUtil {
    /**
     * 获取程序的权限
     */
    public static String[] AppPremission(Context context, String packname) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo packinfo = pm.getPackageInfo(packname, PackageManager.GET_PERMISSIONS);
            // 获取到所有的权限
            return packinfo.requestedPermissions;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取程序的签名
     */
    public static String AppSignature(Context context, String packname) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo packinfo = pm.getPackageInfo(packname, PackageManager.GET_SIGNATURES);
            // 获取到所有的权限
            return packinfo.signatures[0].toCharsString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "No Search";
    }

    /**
     * 获得程序图标
     */
    public static Drawable AppIcon(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo info = pm.getApplicationInfo(context.getPackageName(), 0);
            return info.loadIcon(pm);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获得程序名称
     */
    public static String AppName(Context context, String packname) {
        try {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo info = pm.getApplicationInfo(packname, 0);
            return info.loadLabel(pm).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "No Search";
    }

    /**
     * 获得软件版本号
     */
    public static int VersionCode(Context context) {
        int versioncode = 0;
        try {
            versioncode = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versioncode;
    }

    /**
     * 获得软件版本名
     */
    public static String VersionName(Context context) {
        String versionname = "unknow";
        try {
            versionname = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionname;
    }

    /**
     * 得到软件包名
     */
    public static String PackgeName(Context context) {
        String packgename = "unknow";
        try {
            packgename = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).packageName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packgename;
    }

    /**
     * 获得imei号
     */
    public static String IMEI(Context context) {
        String imei = "NO Search";
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        imei = telephonyManager.getDeviceId();
        return imei;
    }

    /**
     * 获得imsi号
     */
    public static String IMSI(Context context) {
        String imsi = "NO Search";
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        imsi = telephonyManager.getSubscriberId();
        return imsi;
    }

    /**
     * 返回本机电话号码
     */
    public static String Num(Context context) {
        String num = "NO Search";
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        num = telephonyManager.getLine1Number();
        return num;
    }

    /**
     * 得到手机产品序列号
     */
    public static String SN() {
        String sn = "NO Search";
        String serial = android.os.Build.SERIAL;// 第二种得到序列号的方法
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class);
            sn = (String) get.invoke(c, "ro.serialno");
        } catch (Exception e) {

            e.printStackTrace();
        }
        return sn;
    }

    /**
     * 获得手机sim号
     */
    public static String SIM(Context context) {
        String sim = "NO Search";
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        sim = telephonyManager.getSimSerialNumber();
        return sim;
    }

    /**
     * 返回安卓设备ID
     */
    public static String ID(Context context) {
        String id = "NO Search";
        id = android.provider.Settings.Secure.getString(
                context.getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);

        return id;
    }

    /**
     * 得到设备mac地址
     */
    public static String MAC(Context context) {
        String mac = "NO Search";
//        WifiManager manager = (WifiManager) context
//                .getSystemService(Context.WIFI_SERVICE);
//        WifiInfo info = manager.getConnectionInfo();
//        mac = info.getMacAddress();
        return mac;
    }

    /**
     * 得到当前系统国家和地区
     */
    public static String Country(Context context) {
        String country = "NO Search";
        country = context.getResources().getConfiguration().locale.getCountry();
        return country;
    }

    /**
     * 得到当前系统语言
     */
    public static String Language(Context context) {
        String language = "NO Search";
        String country = context.getResources().getConfiguration().locale.getCountry();
        language = context.getResources().getConfiguration().locale.getLanguage();
        // 区分简体和繁体中文
        if (language.equals("zh")) {
            if (country.equals("CN")) {
                language = "Simplified Chinese";
            } else {
                language = "Traditional Chinese";
            }
        }
        return language;
    }

    /**
     * 获取时区
     */
    public static int TimeZone() {
        return 0;
    }

    /**
     * 获取系统版本号
     */
    public static String OS() {
        return "";
    }


    /**
     * 返回系统屏幕的高度（像素单位）
     */
    public static int Height(Context context) {
        int height = 0;
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        height = dm.heightPixels;
        return height;
    }

    /**
     * 返回系统屏幕的宽度（像素单位）
     */
    public static int Width(Context context) {
        int width = 0;
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        width = dm.widthPixels;
        return width;
    }

    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前系统上的语言列表(Locale列表)
     *
     * @return 语言列表
     */
    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }

    /**
     * 获取当前手机Android系统版本号
     *
     * @return Android系统版本号
     */
    public static String getSystemVersion() {
        return Build.VERSION.CODENAME;
    }


    /**
     * 获取手机IMEI(需要“android.permission.READ_PHONE_STATE”权限)
     *
     * @return 手机IMEI
     */
    public static String getIMEI(Context ctx) {
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Activity.TELEPHONY_SERVICE);
        if (tm != null) {
            return tm.getDeviceId();
        }
        return null;
    }


    /**********生产厂商信息**********/

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String BRAND() {
        return android.os.Build.BRAND;
    }

    /**
     * 设备名称
     */
    public static String DEVICE() {
        return android.os.Build.DEVICE;
    }

    /**
     * 产品名称
     */
    public static String PRODUCT() {
        return android.os.Build.PRODUCT;
    }

    /**
     * 设备用户名称
     */
    public static String USER() {
        return android.os.Build.USER;
    }

    /**********卡信息**********/

    /**********版本号**********/

    /**
     * 获取设备型号
     */
    public static String MODEL() {
        return android.os.Build.MODEL;
    }

    /**
     * 手机版本号{例如：BLL-AL20 8.0.0.172(C00)}
     */
    public static String PHONE_VERSION() {
        try {
            Class localClass = Class.forName("android.os.SystemProperties");
            Object object = localClass.newInstance();
            Object localObject = localClass.getMethod("get", new Class[]{String.class, String.class})
                    .invoke(object, new Object[]{"ro.build.display.id", ""});
            return String.valueOf(localObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 固件版本信息/基带版本
     * 基带版本就是手机的调制解调器使用的驱动版本号，
     * 调制解调器主要目的负责着手机的通信功能（打电话，发短信等）
     */
    public static String RADIO_VERSION() {
        return android.os.Build.getRadioVersion();
    }

    /**
     * Android 系统版本
     */
    public static String VERION() {
        return Build.VERSION.RELEASE;
    }

    /**
     * Android 系统版本值
     */
    public static String SDK() {
        return Build.VERSION.SDK;
    }

    /**
     * Android 系统API级别
     */
    public static int SDK_INT() {
        return Build.VERSION.SDK_INT;
    }

    /**
     *
     * */
    public static int PREVIEW_SDK_INT() {
        return Build.VERSION.PREVIEW_SDK_INT;
    }

    /**
     * 返回系统版本
     */
    @RequiresPermission(android.Manifest.permission.READ_PHONE_STATE)
    public static String getDeviceSoftwareVersion(Context context) {
        try {
            TelephonyManager phone = (TelephonyManager) context.getApplicationContext()
                    .getSystemService(Context.TELEPHONY_SERVICE);
            return phone.getDeviceSoftwareVersion();
        } catch (Exception e) {

        }
        return "";
    }


    /**************系统/固件信息******************/


    /***************wifi 信息****************************/

    /***************蓝牙 信息****************************/

    /***************其它硬件信息****************************/

    /****存储（手机存储/app存储）*****/

}
