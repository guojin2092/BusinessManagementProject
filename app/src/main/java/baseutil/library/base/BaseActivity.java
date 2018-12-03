package baseutil.library.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.mvp.activity.BaseRxActivity;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import baseutil.library.special.material.MyMaterialDialog;
import baseutil.library.util.HideKeyBoardHelper;
import butterknife.ButterKnife;


/**
 * Activity的公用类
 *
 * @author guoj
 */

public abstract class BaseActivity extends BaseRxActivity implements OnClickListener {
    private static final String TAG = "BaseActivity";
    private boolean isAlive = false;
    protected BaseActivity context = null;
    public int epage = 10;

    // 修改完成提交后的等待
    private MaterialDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//限制横屏显示
        initSystemBarTint();
        setView(savedInstanceState);
        isAlive = true;
        context = this;
        ButterKnife.bind(this);
        initView();
        initData();

    }

    /**
     * 子类可以重写改变状态栏颜色
     */
    protected int setStatusBarColor() {
        return getColorPrimary();
    }

    /**
     * 子类可以重写决定是否使用透明状态栏
     */
    protected boolean translucentStatusBar() {
        return false;
    }

    /**
     * 设置状态栏颜色
     */
    protected void initSystemBarTint() {
        Window window = getWindow();
        if (translucentStatusBar()) {
            // 设置状态栏全透明
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(ContextCompat.getColor(this, R.color.transparency));
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
            return;
        }
        // 沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0以上使用原生方法
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(setStatusBarColor());
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //4.4-5.0使用三方工具类，有些4.4的手机有问题，这里为演示方便，不使用沉浸式
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintColor(setStatusBarColor());
        }
    }

    /**
     * 获取主题色
     */
    public int getColorPrimary() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    /**
     * 获取深主题色
     */
    public int getDarkColorPrimary() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
        return typedValue.data;
    }


    /**
     * 设置布局文件
     */
    public abstract void setView(Bundle savedInstanceState);

    /**
     * 初始化组件
     */
    public abstract void initView();

    /**
     * 初始化数据
     */
    public abstract void initData();

    /**
     * activity界面外调用启动(不含参数)
     *
     * @param className 要启动的界面
     */
    public void startActivity(Class<?> className) {
        Intent intent = new Intent(this, className);
        startActivity(intent);
    }

    /**
     * activity界面外调用启动(不含参数)
     *
     * @param context
     * @param className 要启动的界面
     */
    public void startActivity(Context context, Class<?> className) {
        Intent intent = new Intent(context, className);
        context.startActivity(intent);
    }

    /***
     * activity界面外调用启动
     *
     * @param context
     * @param className   要启动的界面
     * @param requestCode 请求码
     */
    public void startActivityForResult(Context context, Class<?> className, int requestCode) {
        Intent intent = new Intent(context, className);
        ((Activity) context).startActivityForResult(intent, requestCode);
    }


    //show short toast 方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**
     * 快捷显示short toast方法，需要long toast就用 Toast.makeText(string, Toast.LENGTH_LONG).show(); ---不常用所以这个类里不写
     *
     * @param stringResId
     */
    public void showShortToast(int stringResId) {
        try {
            showShortToast(getResources().getString(stringResId));
        } catch (Exception e) {
            Log.e(TAG, "showShortToast  context.getResources().getString(resId)" +
                    " >>  catch (Exception e) {" + e.getMessage());
        }
    }

    /**
     * 快捷显示short toast方法，需要long toast就用 Toast.makeText(string, Toast.LENGTH_LONG).show(); ---不常用所以这个类里不写
     *
     * @param string
     */
    public void showShortToast(String string) {
        showShortToast(string, false);
    }

    /**
     * 快捷显示short toast方法，需要long toast就用 Toast.makeText(string, Toast.LENGTH_LONG).show(); ---不常用所以这个类里不写
     *
     * @param string
     * @param isForceDismissProgressDialog
     */
    public void showShortToast(final String string, final boolean isForceDismissProgressDialog) {
        runUiThread(new Runnable() {
            @Override
            public void run() {
                if (isForceDismissProgressDialog) {
                    dismissProgressDialog();
                }
                Toast.makeText(BaseActivity.this, "" + string, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        try {
            Runtime.getRuntime().gc();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 当加载结束可以点击
     */
    protected void canClick() {

    }

    /**
     * 加载框
     *
     * @param message 显示提示信息
     */
    protected void showProgressDialog(String message) {
        progressDialog = MyMaterialDialog.createMaterialDialog(this, message);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    /**
     * 加载框
     *
     * @param message 显示提示信息不能取消
     */
    protected void showProgressDialogNoCancel(String message) {
        progressDialog = MyMaterialDialog.createMaterialDialog(this, message);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    /**
     * 显示新信息
     *
     * @param new_message
     */
    protected void setProgressContent(String new_message) {
        if (progressDialog != null)
            progressDialog.setContent(new_message);
    }

    /**
     * 不显示提示信息
     */
    protected void showProgressDialog() {
        progressDialog = MyMaterialDialog.createMaterialDialog(this, getResources().getString(R.string.common_please_wait));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    /**
     * 隐藏进度框
     */
    protected void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog.cancel();
            progressDialog = null;
        }
    }


    @Override
    public void onClick(View v) {
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                View view = getCurrentFocus();
                //调用方法判断是否需要隐藏键盘
                HideKeyBoardHelper.hideKeyboard(ev, view, BaseActivity.this);
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 显示popupwindow
     */
    public void showChoicePop(View llContent, View layout) {
        // 背景从不透明变半透明
        Animation alphaIn = AnimationUtils.loadAnimation(this,
                R.anim.back_alpha_in);
        llContent.setBackgroundColor(0xb0000000);
        llContent.startAnimation(alphaIn);
        llContent.setVisibility(View.VISIBLE);

        Animation transIn = AnimationUtils.loadAnimation(this,
                R.anim.activity_pop_in);
        layout.startAnimation(transIn);
        layout.setVisibility(View.VISIBLE);
    }

    /**
     * 关闭popupwindow
     */
    public void closeChoicePop(View llContent, View layout) {
        // 背景从半透明变不透明
        Animation alphaOut = AnimationUtils.loadAnimation(this,
                R.anim.back_alpha_out);
        llContent.setBackgroundColor(0xb0000000);
        llContent.startAnimation(alphaOut);
        llContent.setVisibility(View.GONE);

        //选择框开启退出动画
        Animation transOut = AnimationUtils.loadAnimation(this,
                R.anim.activity_pop_out);
        layout.startAnimation(transOut);
        layout.setVisibility(View.GONE);
    }

    public final boolean isAlive() {
        return isAlive && context != null;// & ! isFinishing();导致finish，onDestroy内runUiThread不可用
    }

    /**
     * 在UI线程中运行，建议用这个方法代替runOnUiThread
     *
     * @param action
     */
    public final void runUiThread(Runnable action) {
        if (isAlive() == false) {
            Log.w(TAG, "runUiThread  isAlive() == false >> return;");
            return;
        }
        runOnUiThread(action);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isAlive = false;
        context = null;
    }
}
