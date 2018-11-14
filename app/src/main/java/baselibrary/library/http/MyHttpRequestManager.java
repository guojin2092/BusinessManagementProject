package baselibrary.library.http;

import android.content.Context;
import android.text.TextUtils;

import com.loopj.android.http.RequestParams;
import com.simplesoft.basesimplelibrary.manager.SimpleUserInfoManager;

import java.util.concurrent.TimeUnit;

import baselibrary.library.myview.MyLog;
import baselibrary.library.util.CommonUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 网络请求类
 *
 * @author Administrator
 */
public class MyHttpRequestManager {

    /**
     * 网络请求管理单例
     */
    private static MyHttpRequestManager httpRequestManager;
    /**
     * token存在内存
     */
    private static String tokenValue = "Basic c2ltcGxlOnNpbXBsZUAwMSE=";

    private static String authKey = "Basic c2ltcGxlOnNpbXBsZUAwMSE=";
    /**
     * 网络请求
     */
    private OkHttpClient okHttpClient;

    private Context context;
    private final static int NETWORK_ERROR = -1;

    //只处理一次无网络提示
    private boolean dealNetwork = false;
    //只处理一次需要登陆操作
    private boolean dealLogin = false;

    private String userAgent;

    public static MyHttpRequestManager getInstance(Context context) {
        if (httpRequestManager == null) {
            synchronized (MyHttpRequestManager.class) {
                if (httpRequestManager == null) {
                    httpRequestManager = new MyHttpRequestManager(context.getApplicationContext());
                }
            }
        }
        return httpRequestManager;
    }

    private MyHttpRequestManager(Context context) {
        this.context = context;
        /*tokenValue = HaloCollegeUserLoginManager.getValue(context, HaloCollegeUserLoginManager.LOGIN_TOKEN);*/
        /*tokenValue = HaloCollegeUserLoginManager.getValue(context, HaloCollegeUserLoginManager.LOGIN_TOKEN);*/
        /*okHttpClient    asyncHttpClient = new AsyncHttpClient(true, 80, 443);*/
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();
/*        okHttpClient.cancelAllRequests(true);
        okHttpClient.setTimeout(6000);*/
        /*asyncHttpClient.setSSLSocketFactory(MySSLSocketFactory.getFixedSocketFactory());*/
        userAgent = "SimpleSoft/android/" + CommonUtil.getVersionName(context) + " " + System.getProperty("http.agent");
//        userAgent = "College/android/" + CommonUtil.getVersionName(context) + " " + System.getProperty("http.agent");
    }

    /**
     * 网络请求(get)方法
     *
     * @param methodName                 请求方法名
     * @param requestBody                请求的参数
     * @param className                  json解析实体
     * @param httpRequestFinishInterface 监听回调解析实体
     *//*
    public void netGetRequest(String methodName, RequestBody requestBody,
                              Class<?> className,
                              MyHttpRequestFinishInterface httpRequestFinishInterface) {
        initRequestValue();
        netGetRequest(methodName, requestBody, ConfigData.rootUrl, className,
                httpRequestFinishInterface);
    }*/

    /**
     * 网络请求(get)方法
     *
     * @param methodName                 请求方法名
     * @param requestBody                请求参数
     * @param url                        请求的网址
     * @param className                  json解析实体
     * @param httpRequestFinishInterface 监听回调解析实体
     */
    public void netGetRequest(String methodName, RequestBody requestBody,
                              String url, Class<?> className,
                              MyHttpRequestFinishInterface httpRequestFinishInterface) {
        initRequestValue();
        netGetRequest(methodName, requestBody, url, false, className,
                httpRequestFinishInterface);
    }

    /**
     * 网络请求(get)方法
     *
     * @param methodName                 请求方法名
     * @param requestBody                请求参数
     * @param url                        请求的网址
     * @param className                  json解析实体
     * @param httpRequestFinishInterface 监听回调解析实体
     */
    public void netGetRequestMustLogin(String methodName, RequestBody requestBody,
                                       String url, Class<?> className,
                                       MyHttpRequestFinishInterface httpRequestFinishInterface) {
        initRequestValue();
        netGetRequest(methodName, requestBody, url, true, className,
                httpRequestFinishInterface);
    }

    /**
     * 网络请求(get)方法
     *
     * @param methodName                 请求方法名
     * @param requestBody                请求的参数名
     * @param url                        请求的网址
     * @param isLogin                    是否要登录
     * @param className                  json解析实体
     * @param httpRequestFinishInterface 监听回调解析实体
     */
    private void netGetRequest(String methodName, RequestBody requestBody,
                               String url, boolean isLogin, Class<?> className,
                               MyHttpRequestFinishInterface httpRequestFinishInterface) {
        MyLog.e(context, "methodName(" + methodName + ")" + "url = " + url);
        Request request;
        //同步锁
        synchronized (this) {
            //只要没有网络，就不继续执行
            if (!MyNetworkUtil.isNetworkConnected(context)) {
                if (!dealNetwork) {
                    httpRequestFinishInterface.onRequestFailed(methodName, NETWORK_ERROR,
                            null, null);
                    //已经执行过网络提示
                    dealNetwork = true;
                }
                return;
            }
            //有网络
            dealNetwork = false;
            // 是否需要登录
            if (isLogin) {
                if (!TextUtils.isEmpty(SimpleUserInfoManager.getValue(context, SimpleUserInfoManager.USER_ID))) {
                    if (requestBody != null) {
                        request = new Request.Builder()
                                .url(url)
                                .header("User-Agent", userAgent)
                                .header("Authorization", authKey)
                                .post(requestBody)
                                .build();
                    } else {
                        request = new Request.Builder()
                                .header("Authorization", authKey)
                                .url(url)
                                .build();
                    }
                } else {
                    if (!dealLogin) {
                        httpRequestFinishInterface.onRequestForLogin();
                        //已经执行过登录提示
//                        dealLogin = true;
                    }
                    return;
                }
                //已登陆
                dealLogin = false;
            } else {
                if (requestBody != null) {
                    request = new Request.Builder()
                            .url(url)
                            .header("User-Agent", userAgent)
                            .header("Authorization", authKey)
                            .post(requestBody)
                            .build();
                } else {
                    request = new Request.Builder()
                            .url(url)
//                            .header("User-Agent", userAgent)
                            .header("Authorization", authKey)
                            .build();
                }
            }
            okHttpClient.newCall(request).enqueue(new MyOkHttpResponseHandler(
                    context, methodName,
                    url, className,
                    httpRequestFinishInterface));
        }
    }

/*    *//**
     * 网络请求(post)方法
     *
     * @param methodName                 请求方法名
     * @param requestBody                请求的参数
     * @param className                  json解析实体
     * @param httpRequestFinishInterface 监听回调解析实体
     *//*

    public void netPostRequest(String methodName, RequestBody requestBody,
                               Class<?> className,
                               MyHttpRequestFinishInterface httpRequestFinishInterface) {
        initRequestValue();
        netPostRequest(methodName, requestBody, ConfigData.rootUrl, className,
                httpRequestFinishInterface);
    }*/

    /**
     * 网络请求(get)方法
     *
     * @param methodName                 请求方法名
     * @param requestBody                请求参数
     * @param url                        请求的网址
     * @param className                  json解析实体
     * @param httpRequestFinishInterface 监听回调解析实体
     */
    public void netPostRequest(String methodName, RequestBody requestBody,
                               String url, Class<?> className,
                               MyHttpRequestFinishInterface httpRequestFinishInterface) {
        initRequestValue();
        netPostRequest(methodName, requestBody, url, false, className,
                httpRequestFinishInterface);
    }


    /**
     * 网络请求(get)方法
     *
     * @param methodName                 请求方法名
     * @param requestBody                请求的参数名
     * @param url                        请求的网址
     * @param className                  json解析实体
     * @param httpRequestFinishInterface 监听回调解析实体
     */
    public void netPostRequestMustLogin(String methodName, RequestBody requestBody,
                                        String url, Class<?> className,
                                        MyHttpRequestFinishInterface httpRequestFinishInterface) {
        initRequestValue();
        netPostRequest(methodName, requestBody, url, true, className, httpRequestFinishInterface);
    }

    /**
     * 网络请求(get)方法
     *
     * @param methodName                 请求方法名
     * @param requestBody                请求的参数名
     * @param url                        请求的网址
     * @param isLogin                    是否要登录
     * @param className                  json解析实体
     * @param httpRequestFinishInterface 监听回调解析实体
     */
    public void netPostRequest(String methodName, RequestBody requestBody,
                               String url, boolean isLogin, Class<?> className,
                               MyHttpRequestFinishInterface httpRequestFinishInterface) {
        MyLog.e(context, "methodName(" + methodName + ")" + "url = " + url);
        Request request;
        //同步锁
        synchronized (this) {
            //只要没有网络，就不继续执行
            if (!MyNetworkUtil.isNetworkConnected(context)) {
                if (!dealNetwork) {
                    httpRequestFinishInterface.onRequestFailed(methodName, NETWORK_ERROR,
                            null, null);
                    //已经执行过网络提示
                    dealNetwork = true;
                }
                return;
            }
            //有网络
            dealNetwork = false;
            // 是否需要登录
            if (isLogin) {
                if (!TextUtils.isEmpty(SimpleUserInfoManager.getValue(context, SimpleUserInfoManager.USER_ID))) {
                    request = new Request.Builder()
                            .header("User-Agent", userAgent)
                            .header("Authorization", authKey)
                            .url(url)
                            .post(requestBody)
                            .build();
                } else {
                    if (!dealLogin) {
                        httpRequestFinishInterface.onRequestForLogin();
                        //已经执行过登录提示
//                        dealLogin = true;
                    }
                    return;
                }
                //已登陆
                dealLogin = false;
            } else {
                request = new Request.Builder()
                        .header("User-Agent", userAgent)
                        .header("Authorization", authKey)
                        .url(url)
                        .post(requestBody)
                        .build();
            }
            okHttpClient.newCall(request).enqueue(new MyOkHttpResponseHandler(
                    context, methodName,
                    url, className,
                    httpRequestFinishInterface));
        }
    }

    /**
     * 网络请求(post)方法
     *
     * @param methodName                 请求方法名
     * @param requestParams              请求的参数名
     * @param url                        请求的网址
     * @param className                  json解析实体
     * @param httpRequestFinishInterface 监听回调解析实体
     */
    public void netDeleteRequestMustLogin(String methodName, RequestParams requestParams,
                                          String url, Class<?> className,
                                          MyHttpRequestFinishInterface httpRequestFinishInterface) {
        initRequestValue();
        netDeleteRequest(methodName, requestParams, url, true, className, httpRequestFinishInterface);
    }

    /**
     * 网络请求(post)方法
     *
     * @param methodName                 请求方法名
     * @param requestParams              请求的参数名
     * @param url                        请求的网址
     * @param isLogin                    是否要登录
     * @param className                  json解析实体
     * @param httpRequestFinishInterface 监听回调解析实体
     */
    private void netDeleteRequest(String methodName, RequestParams requestParams,
                                  String url, boolean isLogin, Class<?> className,
                                  MyHttpRequestFinishInterface httpRequestFinishInterface) {
        MyLog.e(context, "methodName(" + methodName + ")" + "url = " + url);
        /*requestParams = requestParams;*/
        //同步锁
        synchronized (this) {
            //只要没有网络，就不继续执行
            if (!MyNetworkUtil.isNetworkConnected(context)) {
                if (!dealNetwork) {
                    httpRequestFinishInterface.onRequestFailed(methodName, NETWORK_ERROR,
                            null, null);
                    //已经执行过网络提示
                    dealNetwork = true;
                }
                return;
            }
            //有网络
            dealNetwork = false;
            // 是否需要登录
            if (isLogin) {
                if (!TextUtils.isEmpty(tokenValue)) {
//                    asyncHttpClient.addHeader("Authorization", "Bearer " + tokenValue);
                } else {
                    if (!dealLogin) {
                        httpRequestFinishInterface.onRequestForLogin();
                        //已经执行过登录提示
//                        dealLogin = true;
                    }
                    return;
                }
                //已登陆
                dealLogin = false;
            }

            MyAsyncHttpResponseHandler myAsyncHttpResponseHandler = new MyAsyncHttpResponseHandler(context, methodName, JsonUrlUtil
                    .getJsonUrl(url, null), className,
                    httpRequestFinishInterface);

//            asyncHttpClient.delete(context, url, paramsToEntity(requestParams, myAsyncHttpResponseHandler), null, myAsyncHttpResponseHandler);
        }
    }

    /**
     * 初始化请求
     */
    private void initRequestValue() {
        dealNetwork = false;
        dealNetwork = false;
    }

    /**
     * @param requestBody
     * @return
     */
    private RequestBody getRequestParams(RequestBody requestBody) {
    /*    if (requestParams != null)
            requestParams.put("mver", 4);*/
        return requestBody;
    }


    /**
     * 设置token
     *
     * @param tokenValue
     */
    public void setHttpCookie(String tokenValue) {
        this.tokenValue = tokenValue;
        /*okHttpClient.addHeader("Authorization", "Bearer " + tokenValue);*/
    }

    /**
     * 取消所有网络请求
     *
     * @param mayInterruptIfRunning
     */
    public void cancelRequests(Context context, boolean mayInterruptIfRunning) {
        /*okHttpClient.cancelRequests(context, mayInterruptIfRunning);*/
    }
}
