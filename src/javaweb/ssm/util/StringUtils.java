package javaweb.ssm.util;

/**
 * @author ze
 * @creat 2022-10-26-22:35
 */
public class StringUtils {
    public static boolean isNotEmpty(String str) {
        return !(str == null || "".equals(str));
    }
}
