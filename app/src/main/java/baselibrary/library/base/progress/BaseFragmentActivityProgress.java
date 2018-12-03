package baselibrary.library.base.progress;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.main.LoginActivity;
import com.simplesoft.baselibrary.bean.BaseSimpleBean;

import baselibrary.library.base.BaseFragmentActivity;
import baselibrary.library.http.MyHttpRequestFinishInterface;
import baselibrary.common.util.ToastUtils;


/**
 * Created by Administrator on 2015/1/12.
 */
public abstract class BaseFragmentActivityProgress extends BaseFragmentActivity implements MyHttpRequestFinishInterface {
    /**
     * 登陆请求码
     */
    private final static int LOGIN_REQUEST_MSG = 1001;
    protected FrameLayout progressBar;
    protected FrameLayout frameLayout;
    protected LinearLayout networkErrorLayout;

    @Override
    public void initView() {
        frameLayout = (FrameLayout) findViewById(R.id.framelayout);
        progressBar = (FrameLayout) findViewById(R.id.progressbar_wait);
        if (progressBar != null)
            progressBar.setVisibility(View.VISIBLE);
    }


    @Override
    public void onRequestForLogin() {
        LoginActivity.startActivityForResult(this, LOGIN_REQUEST_MSG);
    }

    /**
     * 请求成功
     *
     * @param methodName     请求的方法名
     * @param statusCode
     * @param msg
     * @param baseSimpleBean 返回的json数据
     */
    @Override
    public void onRequestSuccess(String methodName, int statusCode, String msg, BaseSimpleBean baseSimpleBean) {
        showNetworkSuccess();

    }

    /**
     * 请求失败
     *
     * @param methodName
     * @param statusCode
     * @param error
     * @param baseSimpleBean
     */
    @Override
    public void onRequestFailed(String methodName, int statusCode,
                                String error, BaseSimpleBean baseSimpleBean) {
        ToastUtils.show(this, getString(R.string.no_network));
        showNetworkFailed();
    }


    /**
     * 显示网络连接成功
     */
    private void showNetworkSuccess() {
        //默认不显示
        if (networkErrorLayout != null) {
            networkErrorLayout.setVisibility(View.GONE);
        }
        if (frameLayout != null) {
            frameLayout.setVisibility(View.VISIBLE);
        }
        if (progressBar != null) {
            if (progressBar.getVisibility() == View.VISIBLE) {
                progressBar.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 显示网络连接失败
     */
    private void showNetworkFailed() {
        if (frameLayout == null) {
            frameLayout = (FrameLayout) findViewById(R.id.framelayout);
        }
        if (frameLayout != null)
            frameLayout.setVisibility(View.GONE);
        if (networkErrorLayout == null) {
            networkErrorLayout = (LinearLayout) findViewById(R.id.layout_network_error);
        }
        if (networkErrorLayout != null) {
            networkErrorLayout.setVisibility(View.VISIBLE);
            networkErrorLayout.setOnClickListener(this);
        }
        if (progressBar != null) {
            if (progressBar.getVisibility() == View.VISIBLE) {
                progressBar.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_network_error:
                //默认请求第一页
                requestData(1);
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
}
