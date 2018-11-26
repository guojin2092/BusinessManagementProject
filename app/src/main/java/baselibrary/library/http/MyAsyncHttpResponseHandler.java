package baselibrary.library.http;

import android.content.Context;

import com.africa.crm.businessmanagement.R;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.simplesoft.basesimplelibrary.bean.BaseSimpleBean;

import baselibrary.library.myview.MyLog;
import cz.msebera.android.httpclient.Header;

/**
 * 封装异步的http请求
 *
 * @author Administrator
 */

public class MyAsyncHttpResponseHandler extends AsyncHttpResponseHandler {

    private MyHttpRequestFinishInterface httpRequestFinishInterface;
    private Class<?> classname;
    private String methodName;
    private String jsonUrl;
    private Context context;
    private Gson gson;
    //只处理一次需要登陆操作
    private boolean dealLogin = false;

    public MyAsyncHttpResponseHandler(Context context, String methodName,
                                      String jsonUrl, Class<?> classname,
                                      MyHttpRequestFinishInterface httpRequestFinishInterface) {
        this.context = context;
        this.methodName = methodName;
        this.gson = new Gson();
        this.jsonUrl = jsonUrl;
        this.classname = classname;
        this.httpRequestFinishInterface = httpRequestFinishInterface;
    }

    @Override
    public void onStart() {
        super.onStart();
        // 开始请求（可以用来显示请求滚动圈）
    }

    @Override
    public void onFinish() {
        super.onFinish();
        // 请求完成（可取消显示滚动圈）
    }


    /**
     * Fired when a request returns successfully, override to handle in your own code
     *
     * @param statusCode   the status code of the response
     * @param headers      return headers, if any
     * @param responseBody the body of the HTTP response from the server
     */
    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        MyLog.e(context, "methodName(" + methodName + ")" + new String(responseBody));
        switch (statusCode) {
            case 200:
                // 在此处判断用户有没有登录
                // 如果已经登录返回成功
                String json = new String(responseBody);
                Object object;
                try {
                    object = gson.fromJson(json, Class.forName(classname.getName()));
                    if (object != null) {
                        synchronized (this) {
                            if (!dealLogin) {
                                showLoginOverTint((BaseSimpleBean) object);
                                //已经执行过登录过期提示
                                dealLogin = true;
                            }
                        }
                        //已登陆
                        dealLogin = false;
                        httpRequestFinishInterface.onRequestSuccess(methodName, statusCode,
                                "请求成功", (BaseSimpleBean) object);
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                    MyLog.e(context, "Exception(" + e.getMessage() + ")");
                }
                break;
            default:
                break;
        }
    }

    /**
     * Fired when a request fails to complete, override to handle in your own code
     *
     * @param statusCode   return HTTP status code
     * @param headers      return headers, if any
     * @param responseBody the response body, if any
     * @param error        the underlying cause of the failure
     */
    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        MyLog.e(context, "methodName(" + methodName + ")" + "statusCode = " + statusCode + "Throwable = " + error);
        // 判断请求出错的原因，并回调
        switch (statusCode) {
            case 0:
                MyLog.e(context, error.getMessage());
                // 返回失败的编码或者返回重新定义的标志
                try {
                    httpRequestFinishInterface.onRequestFailed(methodName, statusCode,
                            context.getString(R.string.no_network), null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 显示登陆过期提示
     *
     * @param baseSimpleBean
     */
    private void showLoginOverTint(BaseSimpleBean baseSimpleBean) {
//        if (baseSimpleBean.iRet != null && baseSimpleBean.iRet.equals("-1")) {
//            HaloCollegeUserLoginManager.deleteUserInfo(context);
//            Intent intent = new Intent(UserCenterFragment.LOGIN_OTHER_PLACE);
//            context.sendBroadcast(intent);
//            httpRequestFinishInterface.onRequestForLogin();
//            ToastUtils.show(context, "登陆已过期或已在其它设备登录");
//            return;
//        }
    }
}
