package baseutil.library.util;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Project：resident_project
 * Author:  guojin
 * Version: 1.0.0
 * Description：
 * Date：2017/6/9 11:21
 * Modification  History:
 * Why & What is modified:
 */
public class ShowSoftInput {

    /**
     * EditText获取焦点并显示软键盘
     */
    public static void showSoftInputFromWindow(Activity activity, EditText editText) {
        //让编辑框弹出来，并显示对谁进行评论
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus ();
        //打开软键盘
        InputMethodManager imm = (InputMethodManager)activity.getSystemService(activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

}
