package baselibrary.library.base.progress;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.main.LoginActivity;
import com.simplesoft.baselibrary.bean.BaseSimpleBean;

import baselibrary.library.base.BaseActivity;
import baselibrary.library.http.MyHttpRequestFinishInterface;
import baselibrary.library.http.MyNetworkUtil;
import baselibrary.library.util.CommonUtils;
import baselibrary.common.util.ToastUtils;


/**
 * Created by Administrator on 2015/1/12.
 */
public abstract class BaseActivityProgress extends BaseActivity implements MyHttpRequestFinishInterface {
    /**
     * 登陆请求码
     */
    public final static int LOGIN_REQUEST_MSG = 1001;
    protected FrameLayout frameLayout;
    protected FrameLayout progressBar;
    protected LinearLayout networkErrorLayout;
    protected Button btn_net_refresh;
    protected LinearLayout rlNoData;
    protected ImageView iv_return;
    protected Button btn_no_data_back;

    @Override
    public void initView() {
        frameLayout = (FrameLayout) findViewById(R.id.framelayout);
        progressBar = (FrameLayout) findViewById(R.id.progressbar_wait);
        if (progressBar != null)
            progressBar.setVisibility(View.VISIBLE);
        rlNoData = (LinearLayout) findViewById(R.id.layout_no_data);
        iv_return = (ImageView) findViewById(R.id.iv_return);
        btn_no_data_back = (Button) findViewById(R.id.btn_no_data_back);

        if (iv_return != null) {
            iv_return.setOnClickListener(this);
        }
        if (btn_no_data_back != null) {
            btn_no_data_back.setOnClickListener(this);
        }
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
            if (progressBar.getVisibility() == View.VISIBLE)
                progressBar.setVisibility(View.GONE);
        }
    }

    /**
     * 显示网络连接失败
     */
    protected void showNetworkFailed() {
        if (frameLayout == null) {
            frameLayout = (FrameLayout) findViewById(R.id.framelayout);
        }
        if (frameLayout != null)
            frameLayout.setVisibility(View.GONE);
        if (btn_net_refresh == null) {
            btn_net_refresh = (Button) findViewById(R.id.btn_net_refresh);
            btn_net_refresh.setOnClickListener(this);
        }
        if (networkErrorLayout == null) {
            networkErrorLayout = (LinearLayout) findViewById(R.id.layout_network_error);
        }
        if (networkErrorLayout != null) {
            networkErrorLayout.setVisibility(View.VISIBLE);
            networkErrorLayout.setOnClickListener(this);
        }
        if (progressBar != null) {
            if (progressBar.getVisibility() == View.VISIBLE)
                progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        if (CommonUtils.isFastDoubleClick(800)) {
            return;
        }
        switch (v.getId()) {
            case R.id.btn_net_refresh:
            case R.id.layout_network_error:
                if (MyNetworkUtil.isNetworkConnected(getApplication())) {
                    //默认请求第一页
                    requestData(1);
                } else {
                    ToastUtils.show(getApplication(), "网络没有连接，请检查网络");
                }
                break;
            case R.id.iv_return:
            case R.id.btn_no_data_back:
                finish();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOGIN_REQUEST_MSG) {
            if (resultCode == LoginActivity.LOGIN_SUCCESS) {
                requestData(1);
            }
        }
    }
}
