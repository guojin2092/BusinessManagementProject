package baselibrary.library.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.africa.crm.businessmanagement.R;
import com.simplesoft.baselibrary.bean.BaseSimpleBean;

import baselibrary.library.http.MyHttpRequestFinishInterface;
import baselibrary.library.http.MyHttpRequestManager;
import baselibrary.library.http.MyNetworkUtil;
import baselibrary.library.special.material.MyMaterialDialog;
import baselibrary.common.util.ToastUtils;

public abstract class BaseFragmentActivity extends AppCompatActivity implements
        OnClickListener, MyHttpRequestFinishInterface {
    private FrameLayout progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* requestWindowFeature(Window.FEATURE_NO_TITLE);*/

/*        // 如果ImageLoader为空，则初始化
        if (MyImageLoader.imageLoader == null)
            MyImageLoader.imageLoader = ImageLoader.getInstance();*/

        setView(savedInstanceState);
//        MDStatusBarCompat.setOrdinaryToolBar(this);
//        JMessageClient.registerEventReceiver(this);
        initView();
        initData();
    }

    /**
     * 初始化进度条
     */
    protected void initProgressBar() {
        progressBar = (FrameLayout) findViewById(
                R.id.progressbar_wait);
        if (progressBar != null)
            progressBar.setVisibility(View.VISIBLE);
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

    public void onResume() {
        super.onResume();
       /* MobclickAgent.onResume(this); // 统计时长*/
        // onresume时，取消notification显示
        /*HXSDKHelper.getInstance().getNotifier().reset();*/
    }

    public void onPause() {
        super.onPause();
     /*   MobclickAgent.onPause(this);*/
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
     * activity界面外调用启动(不含参数)
     *
     * @param className 要启动的界面
     */
    public void startActivity(Class<?> className) {
        Intent intent = new Intent(this, className);
        startActivity(intent);
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
     * activity界面外调用启动
     *
     * @param context
     * @param className 要启动的界面
     */
    public static void startActivity(Context context, Class<?> className) {
        Intent intent = new Intent(context, className);
        context.startActivity(intent);
    }

    /**
     * http请求成功
     */
    @Override
    public void onRequestSuccess(String methodName, int statusCode, String msg,
                                 BaseSimpleBean baseSimpleBean) {
    }

    /**
     * 请求失败
     */
    @Override
    public void onRequestFailed(String methodName, int statusCode,
                                String error, BaseSimpleBean baseSimpleBean) {
        // Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestForLogin() {
//        Intent intent = new Intent(this, UserActivity.class);
//        startActivityForResult(intent, 100);
        // Toast.makeText(this, "Activity,亲，你需要登录哦!",
        // Toast.LENGTH_SHORT).show();
    }

    private MaterialDialog progressDialog;

    /**
     * 加载框,显示文字提示
     *
     * @param layoutResID 加载布局
     * @param message     显示加载提示文字
     */
   /* protected void showProgressDialog(int layoutResID, String message) {
        if (progressDialog == null) {
            progressDialog = MyMaterialProgressDialog.createDialog(this, layoutResID);
        }
        progressDialog.setMessage(message);
        progressDialog.show();
    }*/

    /**
     * 加载框,不显示提示
     *
     * @param layoutResID 加载布局
     */
   /* protected void showProgressDialog(int layoutResID) {
        if (progressDialog == null) {
            progressDialog = MyMaterialProgressDialog.createDialog(this, layoutResID);
        }
        progressDialog.show();
    }
*/

    /**
     * 加载框
     *
     * @param message 显示提示信息
     */
    protected void showProgressDialog(String message) {
        progressDialog = MyMaterialDialog.createMaterialDialog(this, message);
        progressDialog.show();
    }

    /**
     * 不显示提示信息
     */
    protected void showProgressDialog() {
        progressDialog = MyMaterialDialog.createMaterialDialog(this, getResources().getString(R.string.common_please_wait));
        progressDialog.show();
    }

    /**
     * 隐藏进度框
     */
    protected void dissmissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog.cancel();
            progressDialog = null;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_network_error:
                if (MyNetworkUtil.isNetworkConnected(getApplication())) {
                    //默认请求第一页
                    requestData(1);
                } else {
                    ToastUtils.show(getApplication(), "网络没有连接，请检查网络");
                }
                break;
        }
    }


    /**
     * 重新请求数据
     *
     * @param page
     */
    protected void requestData(int page) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyHttpRequestManager.getInstance(this).cancelRequests(this, true);
    }
}
