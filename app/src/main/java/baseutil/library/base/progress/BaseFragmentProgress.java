package baseutil.library.base.progress;

import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.main.LoginActivity;
import com.simplesoft.baselibrary.bean.BaseSimpleBean;

import baseutil.library.base.BaseFragment;
import baseutil.library.http.MyHttpRequestFinishInterface;
import baseutil.library.util.NetUtil;
import baseutil.common.util.ToastUtils;


/**
 * Created by Administrator on 2015/1/12.
 */
public abstract class BaseFragmentProgress extends BaseFragment implements MyHttpRequestFinishInterface {

    public final static int LOGIN_REQUEST_MSG = 1001;
    protected FrameLayout progressBar;
    protected FrameLayout frameLayout;
    protected LinearLayout networkErrorLayout;
    //有网络后重新请求gridview数据
//    private MyReceiver myReceiver;

    @Override
    public void initView() {
//        registerReceiver();
        frameLayout = (FrameLayout) getView().findViewById(R.id.framelayout);
        progressBar = (FrameLayout) getView().findViewById(R.id.progressbar_wait);
        if (progressBar != null)
            progressBar.setVisibility(View.VISIBLE);
        networkErrorLayout = ((LinearLayout) getView().findViewById(R.id.layout_network_error));
        if (!NetUtil.isNetAvailable(getActivity())) {
            showNetworkFailed();
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
        ToastUtils.show(getActivity(), getString(R.string.no_network));
        showNetworkFailed();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOGIN_REQUEST_MSG) {
            if (resultCode == LoginActivity.LOGIN_SUCCESS) {
                requestData(1);
            }
        }
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
        if (progressBar != null)
            if (progressBar.getVisibility() == View.VISIBLE)
                progressBar.setVisibility(View.GONE);
    }

    /**
     * 显示网络连接失败
     */
    private void showNetworkFailed() {
        if (frameLayout == null) {
            frameLayout = (FrameLayout) getActivity().findViewById(R.id.framelayout);
        }
        if (frameLayout != null)
            frameLayout.setVisibility(View.GONE);
        if (networkErrorLayout == null) {
            networkErrorLayout = (LinearLayout) getActivity().findViewById(R.id.layout_network_error);
        }
        if (networkErrorLayout != null) {
            networkErrorLayout.setVisibility(View.VISIBLE);
            networkErrorLayout.setOnClickListener(this);
        }
        if (progressBar != null)
            if (progressBar.getVisibility() == View.VISIBLE)
                progressBar.setVisibility(View.GONE);
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

    /**
     * 初始化进度条
     *
     * @param view
     */
    protected void initProgressBar(View view) {
        progressBar = (FrameLayout) view.findViewById(
                R.id.progressbar_wait);
     /*   progressBar.setVisibility(View.VISIBLE);*/
    }

    protected void initProgressBar(View view, int layoutId) {
        progressBar = (FrameLayout) view.findViewById(
                layoutId);
      /*  progressBar.setVisibility(View.VISIBLE);*/
    }

   /* private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        myReceiver = new MyReceiver();
        getActivity().registerReceiver(myReceiver, intentFilter);

    }

    public void unregisterReceiver() {
        getActivity().unregisterReceiver(myReceiver);
    }


    public class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo == null) {
                ToastUtils.show(getActivity(), getString(R.string.no_network));
                showNetworkFailed();
            }
        }
    }*/


}
