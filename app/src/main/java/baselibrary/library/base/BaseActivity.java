package baselibrary.library.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import baselibrary.library.http.MyHttpRequestManager;
import baselibrary.library.special.material.MyMaterialDialog;
import baselibrary.library.util.HideKeyBoardHelper;
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
    /**
     * 退出时之前的界面进入动画,可在finish();前通过改变它的值来改变动画效果
     */
    protected int enterAnim = R.anim.fade;
    /**
     * 退出时该界面动画,可在finish();前通过改变它的值来改变动画效果
     */
    protected int exitAnim = R.anim.right_push_out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        PushAgent.getInstance(context).onAppStart();
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);限制横屏显示
        initSystemBarTint();
        setView(savedInstanceState);
        isAlive = true;
        context = this;
        ButterKnife.bind(this);
//        JMessageClient.registerEventReceiver(this);
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


    /**
     * 打开新的Activity，向左滑入效果
     *
     * @param intent
     */
    public void toActivity(Intent intent) {
        toActivity(intent, true);
    }

    /**
     * 打开新的Activity
     *
     * @param intent
     * @param showAnimation
     */
    public void toActivity(Intent intent, boolean showAnimation) {
        toActivity(intent, -1, showAnimation);
    }

    /**
     * 打开新的Activity，向左滑入效果
     *
     * @param intent
     * @param requestCode
     */
    public void toActivity(Intent intent, int requestCode) {
        toActivity(intent, requestCode, true);
    }

    /**
     * 打开新的Activity
     *
     * @param intent
     * @param requestCode
     * @param showAnimation
     */
    public void toActivity(final Intent intent, final int requestCode, final boolean showAnimation) {
        runUiThread(new Runnable() {
            @Override
            public void run() {
                if (intent == null) {
                    Log.w(TAG, "toActivity  intent == null >> return;");
                    return;
                }
                //fragment中使用context.startActivity会导致在fragment中不能正常接收onActivityResult
                if (requestCode < 0) {
                    startActivity(intent);
                } else {
                    startActivityForResult(intent, requestCode);
                }
                if (showAnimation) {
                    overridePendingTransition(R.anim.right_push_in, R.anim.hold);
                } else {
                    overridePendingTransition(R.anim.null_anim, R.anim.null_anim);
                }
            }
        });
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

    /*public void onEvent(LoginStateChangeEvent event) {
        LoginStateChangeEvent.Reason reason = event.getReason();//获取变更的原因
        UserInfo myInfo = event.getMyInfo();//获取当前被登出账号的信息
        MyLog.e(this, "myInfo==" + myInfo);
        switch (reason) {
            case user_password_change:
                //用户密码在服务器端被修改
                break;
            case user_logout:
                if (isBackground(this)) {
                    //用户换设备登录
                    CookieManager
                            .deleteCookie(this);
                    MyHttpRequestManager.getInstance(
                            this)
                            .setHttpCookie(null);
                    // 清除用户信息
                    UserInfoManager
                            .deleteUserInfo(this);
                    MaterialDialog.Builder materialDialog = new MaterialDialog.Builder(this);
                    materialDialog.title(getResources().getString(R.string.dialog_login_out_hint))
                            .positiveText(getResources().getString(R.string.dialog_login_again))
                            .negativeText(getResources().getString(R.string.dialog_login_out))
                            .positiveColorRes(R.color.app_theme_bg_color)
                            .negativeColorRes(R.color.app_theme_bg_color)
                            .cancelable(false)
                            .autoDismiss(false)
                            .canceledOnTouchOutside(false)
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    startActivity(LoginActivity.class);
                                    dialog.dismiss();
                                }
                            })
                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    dialog.dismiss();
                                }
                            }).show();
                }
                break;
            case user_deleted:
                //用户被删除
                break;
        }
    }

    *//*判断类是否处于栈顶*//*
    public boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
                    MyLog.i(this, "后台--->" + appProcess.processName);
                    return false;
                } else {
                    MyLog.i(this, "前台--->" + appProcess.processName);
                    return true;
                }
            }
        }
        return false;
    }*/

    /**
     * 当加载结束可以点击
     */
    protected void canClick() {

    }

    // @Override
    // public void onRequestNetworkFailed(String methodName, String url,
    // int statusCode, String error) {
    // Toast.makeText(this, "没有网络!", Toast.LENGTH_SHORT).show();
    // }


    // 修改完成提交后的等待
    private MaterialDialog progressDialog;

    /**
     * 加载框,显示文字提示
     *
     * @param layoutResID 加载布局
     * @param message     显示加载提示文字
     */
    /*protected void showProgressDialog(int layoutResID, String message) {
        MyMaterialProgressDialog.createDialog()

        progressDialog.setMessage(message);
        progressDialog.show();
    }

    *//**
     * 加载框,不显示提示
     *
     * @param layoutResID 加载布局
     *//*
    protected void showProgressDialog(int layoutResID) {
        if (progressDialog == null) {
            progressDialog = MyMaterialProgressDialog.createDialog(this, layoutResID);
        }
        progressDialog.show();
    }*/

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
     * 记录一次分享数
     *
     * @param extra_id
     * @param type
     */
   /* public void requestInfoShareData(String extra_id, String type) {
        if (!TextUtils.isEmpty(extra_id)) {
            RequestParams requestParams = new RequestParams();
            requestParams.put("act", "like");
            requestParams.put("type", type);
            requestParams.put("id", extra_id);
            MyHttpRequestManager.getInstance(this).netGetRequest("like",
                    requestParams, ViewBean.class, this);
        }
    }*/

    /**
     * 记一次阅读数
     *
     * @param extra_id
     * @param type
     */
   /* public void requestInfoViewData(String extra_id, String type) {
        if (!TextUtils.isEmpty(extra_id)) {
            RequestParams requestParams = new RequestParams();
            requestParams.put("act", "view");
            requestParams.put("type", type);
            requestParams.put("id", extra_id);
            MyHttpRequestManager.getInstance(this).netGetRequest("view",
                    requestParams, ViewBean.class, this);
        }
    }*/


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
    public void finish() {
        super.finish();
        runUiThread(new Runnable() {
            @Override
            public void run() {
                if (enterAnim > 0 && exitAnim > 0) {
                    try {
                        overridePendingTransition(enterAnim, exitAnim);
                    } catch (Exception e) {
                        Log.e(TAG, "finish overridePendingTransition(enterAnim, exitAnim);" +
                                " >> catch (Exception e) {  " + e.getMessage());
                    }
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isAlive = false;
        context = null;
        MyHttpRequestManager.getInstance(this).cancelRequests(this, true);
    }
}
