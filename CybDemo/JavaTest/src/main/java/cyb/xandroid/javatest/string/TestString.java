package cyb.xandroid.javatest.string;

public class TestString {

    public interface Test {
    }

    public static void main(String[] arg) {

//        String str = "aaabbaccbababccac";
        String str = "babad";
        String huiWen = getHuiWen(str,0,str.length()-1);
        System.out.println(huiWen);

    }

    /**
     * 获取较长的回文字符串
     *
     * @param str
     * @param start
     * @param end
     * @return
     */
    public static  String getHuiWen(String str,int start, int end) {
        if (isHuiWen(str,start,end)) {
            return str.substring(start,end+1);
        }

        String newHuiWen1 = "";
        String newHuiWen2 = "";

        if (start + 1 < end) {
            newHuiWen1 = getHuiWen(str, start + 1, end);
        }

        if (start < end - 1) {
            newHuiWen2 = getHuiWen(str, start, end - 1);
        }

        if(newHuiWen1.length() > newHuiWen2.length()){
            return newHuiWen1;
        }else{
            return newHuiWen2;
        }
    }


    /**
     * @param str
     * @param start
     * @param end
     * @return
     */
    public static boolean isHuiWen(String str, int start, int end) {

        if (str == null || str.length() < 2) return false;
        if (str.charAt(start) == str.charAt(end)) {
            if (start + 1 == end || start == end || start+1 == end) {
                return true;
            }
            return isHuiWen(str, start + 1, end - 1);
        }
        return false;
    }


}
