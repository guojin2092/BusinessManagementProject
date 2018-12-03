package baselibrary.library.http;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.widget.OaPortUrl;
import com.google.gson.Gson;
import com.simplesoft.baselibrary.bean.BaseSimpleBean;

import java.io.IOException;

import baselibrary.library.myview.MyLog;
import baselibrary.common.util.ToastUtils;
import okhttp3.Call;
import okhttp3.Callback;
import simple.project.tool.aes.BankAesCoder;


/**
 * 封装异步的http请求
 *
 * @author Administrator
 */

public class MyOkHttpResponseHandler implements Callback {

    private MyHttpRequestFinishInterface httpRequestFinishInterface;
    private Class<?> classname;
    private String methodName;
    private String jsonUrl;
    private Context context;
    private Gson gson;
    //只处理一次需要登陆操作
    private boolean dealLogin = false;
    public Object object;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    httpRequestFinishInterface.onRequestSuccess(methodName, 200,
                            "请求成功", (BaseSimpleBean) object);
                    break;
                case 2:
                    ToastUtils.show(context, "登陆已过期，请重新登陆");
                    break;
            }
        }
    };

    public MyOkHttpResponseHandler(Context context, String methodName,
                                   String jsonUrl, Class<?> classname,
                                   MyHttpRequestFinishInterface httpRequestFinishInterface) {
        this.context = context;
        this.methodName = methodName;
        this.gson = new Gson();
        this.jsonUrl = jsonUrl;
        this.classname = classname;
        this.httpRequestFinishInterface = httpRequestFinishInterface;
    }

    /**
     * Fired when a request returns successfully, override to handle in your own code
     *
     * @param statusCode   the status code of the response
     * @param headers      return headers, if any
     * @param responseBody the body of the HTTP response from the server
     */
  /*  @Override
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
                    MyLog.e(context, "Exception(" + e.getMessage() + ")");
                } catch (Exception e) {
                    e.printStackTrace();
                    MyLog.e(context, "Exception(" + e.getMessage() + ")");
                }
                break;
            default:
                break;
        }
    }*/

    /**
     * Called when the request could not be executed due to cancellation, a connectivity problem or
     * timeout. Because networks can fail during an exchange, it is possible that the remote server
     * accepted the request before the failure.
     *
     * @param call
     * @param e
     */
    @Override
    public void onFailure(Call call, IOException e) {
             /*   MyLog.e(context, "methodName(" + methodName + ")" + "statusCode = " + statusCode + "Throwable = " + error);
        // 判断请求出错的原因，并回调
        switch (statusCode) {
            case 0:
                MyLog.e(context, error.getMessage());*/
        // 返回失败的编码或者返回重新定义的标志
        try {
            httpRequestFinishInterface.onRequestFailed(methodName, e.getCause().getMessage().hashCode(),
                    context.getString(R.string.no_network), null);
        } catch (Exception ex) {
            ex.printStackTrace();
            MyLog.e(context, "Exception(" + e.getMessage() + ")");
        }
   /*             break;
            default:
                break;
        }*/
    }

    /**
     * Called when the HTTP response was successfully returned by the remote server. The callback may
     * proceed to read the response body with {@link Response#body}. The response is still live until
     * its response body is {@linkplain ResponseBody closed}. The recipient of the callback may
     * consume the response body on another thread.
     * <p>
     * <p>Note that transport-layer success (receiving a HTTP response code, headers and body) does
     * not necessarily indicate application-layer success: {@code response} may still indicate an
     * unhappy HTTP response code like 404 or 500.
     *
     * @param call
     * @param response
     */
    @Override
    public void onResponse(Call call, okhttp3.Response response) throws IOException {
/*        MyLog.e(context, "methodName(" + methodName + ")" + new String(responseBody));
        switch (statusCode) {
            case 200:*/
        // 在此处判断用户有没有登录
        // 如果已经登录返回成功
                /*String json = new String(responseBody);*/
        try {
            BankAesCoder coder = new BankAesCoder();
            String getResponse = coder.unEnCodeData(OaPortUrl.AUTH_PASSWORD, response.body().string().toString());
            object = gson.fromJson(getResponse, Class.forName(classname.getName()));
            MyLog.e(this, methodName + "-->" + getResponse);
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
                Message message = new Message();
                message.what = 1;
                mHandler.sendMessage(message);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            MyLog.e(context, "Exception(" + e.getMessage() + ")");
        } catch (Exception e) {
            e.printStackTrace();
            MyLog.e(context, "Exception(" + e.getMessage() + ")");
        }
  /*              break;
            default:
                break;
        }*/
    }

    /**
     * Fired when a request fails to complete, override to handle in your own code
     *
     * @param statusCode   return HTTP status code
     * @param headers      return headers, if any
     * @param responseBody the response body, if any
     * @param error        the underlying cause of the failure
     */
/*    @Override
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
                    MyLog.e(context, "Exception(" + e.getMessage() + ")");
                }
                break;
            default:
                break;
        }
    }*/


    /**
     * 显示网络异常提示
     *
     * @param statusCode
     */
 /*   private synchronized void showNetworkTint(int statusCode) {
        if (statusCode == -1) {
            ToastUtils.show(context, context.getString(R.string.no_network));
            return;
        }
    }*/


    /**
     * 显示登陆过期提示
     *
     * @param baseSimpleBean
     */
    private void showLoginOverTint(BaseSimpleBean baseSimpleBean) {
//        if (baseSimpleBean.iRet != null && baseSimpleBean.iRet.equals("-1")) {
//            HaloCollegeUserLoginManager.deleteUserInfo(context);
//            MemberStateManager.deleteMemberState(context);
//            Intent intent = new Intent(UserCenterFragment.LOGIN_OTHER_PLACE);
//            context.sendBroadcast(intent);
//            Intent msgIntent = new Intent(PUSH_COLLEGE_MESSAGE_NUMBER);
//            context.sendBroadcast(msgIntent);
//            httpRequestFinishInterface.onRequestForLogin();
//            Message message = new Message();
//            message.what = 2;
//            mHandler.sendMessage(message);
//            return;
//        }
    }
}
