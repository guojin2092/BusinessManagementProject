package baseutil.library.util;

/**
 * 文字数字转换工具
 *
 * @author Administrator
 */
public class ParseUtils {
    /**
     * 处理字符串转数字,如果字符串不能强转,则返回0
     *
     * @param parseString 强转字符串
     * @return
     */
    public static int parseInt(String parseString) {
        int parseInt;
        try {
            parseInt = Integer.parseInt(parseString);
        } catch (NumberFormatException exception) {
            parseInt = 0;
        } catch (Exception e) {
            parseInt = 0;
        }
        return parseInt;
    }

    /**
     * 处理字符串转单精度浮点型,如果字符串不能强转,则返回0
     *
     * @param parseString 强转字符串
     * @return
     */
    public static Float parseFloat(String parseString) {
        Float parseFloat;
        try {
            parseFloat = Float.parseFloat(parseString);
        } catch (NumberFormatException exception) {
            parseFloat =0.0f;
        } catch (Exception e) {
            parseFloat = 0.0f;
        }
        return parseFloat;
    }

    /**
     * 整型转字符串
     *
     * @param parseInteger
     * @return
     */
    public static String parseString(int parseInteger) {
        String parseString;
        try {
            parseString = Integer.toString(parseInteger);
        } catch (Exception e) {
            parseString = "";
        }
        return parseString;
    }
}
