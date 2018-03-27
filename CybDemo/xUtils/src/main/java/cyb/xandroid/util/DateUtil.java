package cyb.xandroid.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by asus on 2018/3/23.
 */

public class DateUtil {


    /**
     * 获取今天是星期几
     *
     * @return 0:周日; 1:周一; 2:周二; 3:周三; 4:周四; 5:周五; 6:周六;
     */
    public static int getDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        return cal.get(Calendar.DAY_OF_WEEK) - 1;
    }

    /**
     * 获取今天是星期几
     *
     * @return 返回字符串格式与手机语言有关
     */
    public static String getDayOfWeekFormatEEE() {
        SimpleDateFormat formatter = new SimpleDateFormat("EEE");
        return formatter.format(new Date());
    }

    /**
     * 获取今天是星期几
     *
     * @return  返回周日到周六，返回字符串格式与手机语言有关
     */
    public static List<String> getWeeksFormatEEE() {
        SimpleDateFormat formatter = new SimpleDateFormat("EEE");
        List<String> weeks = new ArrayList<>();
        Date data = new Date();
        for (int i = 0; i < 7; i++) {
            //889032704 周日，86400000一天的时间
            data.setTime(889032704 + 86400000 * i);
            weeks.add(formatter.format(data));
        }
        return weeks;
    }


}
