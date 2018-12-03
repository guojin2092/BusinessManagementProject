package baselibrary.library.http;

import com.simplesoft.baselibrary.bean.BaseSimpleBean;

/**
 * http请求接口
 *
 * @author Administrator
 */
public interface MyHttpRequestFinishInterface {
    /**
     * 请求成功后调用
     *  @param methodName 请求的方法名
     * @param baseSimpleBean 返回的json数据
     */
    void onRequestSuccess(String methodName, int statusCode, String msg,
                          BaseSimpleBean baseSimpleBean);

    /**
     * 请求失败后回调
     *
     * @param methodName
     * @param baseSimpleBean
     */
    void onRequestFailed(String methodName, int statusCode,
                         String error, BaseSimpleBean baseSimpleBean);

    /**
     * 弹出需要登录
     */
    void onRequestForLogin();

}
