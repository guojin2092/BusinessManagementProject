package baselibrary.library.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;

import com.afollestad.materialdialogs.MaterialDialog;
import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.mvp.fragment.BaseRxFragment;

import baselibrary.library.http.MyHttpRequestManager;
import baselibrary.library.myview.MyLog;
import baselibrary.library.special.material.MyMaterialDialog;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Fragment界面需要继承的基类<br />
 * 这个类包含网络请求方法
 */
public abstract class BaseFragment extends BaseRxFragment implements OnClickListener {
    private Unbinder binder;

    /**
     * 在 Activity.onCreate 方法调用后会立刻调用此方法，表示窗口已经初始化完毕，此时可以调用控件了
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binder = ButterKnife.bind(this, view);
        initView();
        initData();
    }

    /**
     * 初始化组件
     */
    public abstract void initView();

    /**
     * 初始化组件
     */
    public void initView(View layout) {

    }

    /**
     * 初始化数据
     */
    public abstract void initData();

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
     * activity界面外调用启动
     *
     * @param className 要启动的界面
     */
    public void startActivity(Class<?> className) {
        Intent intent = new Intent(getActivity(), className);
        startActivity(intent);
    }

    /***
     * activity界面外调用启动
     *
     * @param className   要启动的界面
     * @param requestCode 请求码
     */
    public void startActivityForResult(Class<?> className, int requestCode) {
        Intent intent = new Intent(getActivity(), className);
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onClick(View view) {
    }


    @Override
    public void onResume() {
        super.onResume();
        /*  MobclickAgent.onPageStart("MainScreen"); // 统计页面*/
    }

    @Override
    public void onPause() {
        super.onPause();
        /*   MobclickAgent.onPageEnd("MainScreen");*/
    }

    private MaterialDialog progressDialog;

    /**
     * 加载框,显示文字提示
     *
     * @param layoutResID 加载布局
     * @param message     显示加载提示文字
     */
  /*  protected void showProgressDialog(int layoutResID, String message) {
        if (progressDialog == null) {
            progressDialog = MyMaterialProgressDialog.createDialog(getActivity(),
                    layoutResID);
        }
        progressDialog.setMessage(message);
        progressDialog.show();
    }*/

    /**
     * 加载框,不显示提示
     *
     * @param layoutResID 加载布局
     */
  /*  protected void showProgressDialog(int layoutResID) {
        if (progressDialog == null) {
            progressDialog = MyMaterialProgressDialog.createDialog(getActivity(),
                    layoutResID);
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
        progressDialog = MyMaterialDialog.createMaterialDialog(getActivity(), message);
        progressDialog.show();
    }

    /**
     * 不显示提示信息
     */
    protected void showProgressDialog() {
        progressDialog = MyMaterialDialog.createMaterialDialog(getActivity(), getResources().getString(R.string.common_please_wait));
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
    public void onStop() {
        super.onStop();
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }

    // @Override
    // public void onRequestNetworkFailed(String url) {
    // Toast.makeText(getActivity(), "没有网络!", Toast.LENGTH_SHORT).show();
    //
    // }
    @Override
    public void onDestroy() {
        super.onDestroy();
        MyLog.e(this, "释放");
        binder.unbind();
        MyHttpRequestManager.getInstance(getActivity()).cancelRequests(getActivity(), true);
        release();
    }

    /**
     * 释放资源
     */
    public abstract void release();


}
