package baseutil.library.util;

import android.text.TextUtils;

import java.util.ArrayList;

/**
 * 字符工具类
 * 
 * @author Administrator
 * 
 */
public class StringUtil {
	// ArrayList<String> 转 String[]
	public static String[] trans(ArrayList<String> als) {
		String[] sa = new String[als.size()];
		als.toArray(sa);
		return sa;
	}

	// String[] 转 ArrayList<String>
	public static ArrayList<String> trans(String[] sa) {
		ArrayList<String> als = new ArrayList<String>(0);
		for (int i = 0; i < sa.length; i++) {
			als.add(sa[i]);
		}
		return als;
	}

	/**
	 * 获取字符串的长度，中文占一个字符,英文数字占半个字符
	 * 
	 * @param value
	 *            指定的字符串
	 * @return 字符串的长度
	 */
	public static int length(String value) {
		double valueLength = 0;
		String chinese = "[\u4e00-\u9fa5]";
		// 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
		for (int i = 0; i < value.length(); i++) {
			// 获取一个字符
			String temp = value.substring(i, i + 1);
			// 判断是否为中文字符
			if (temp.matches(chinese)) {
				// 中文字符长度为1
				valueLength += 1;
			} else {
				// 其他字符长度为0.5
				valueLength += 0.5;
			}
		}
		// 进位取整
		return (int) (Math.ceil(valueLength) * 2);
	}

	/**
	 * 分享内容的限制 大于100截取 否则 直接返回
	 * 
	 * @param content
	 * @return
	 */
	public static String getShareString(String content) {
		if (!TextUtils.isEmpty(content))
			return content.length() > 100 ? content.substring(0, 100) + "..."
					: content + "...";
		return "";
	}
}
