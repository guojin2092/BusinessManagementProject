package com.africa.crm.businessmanagement.network.retrofit;

import com.africa.crm.businessmanagement.network.encryption.AES;
import com.africa.crm.businessmanagement.network.encryption.RSACoder;
import com.africa.crm.businessmanagement.widget.LogUtil;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/24 0024 18:16
 * Modification  History:
 * Why & What is modified:
 */
public class RequestEncryptInterceptor implements Interceptor {
    String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC8bRMtUE+JteWcz640pjqqc98H3n+fG+KDi2o23nlmX2mMcXNgImmGoG1NeR1WMPN54XHUYZMOVFu746Mg2EqHvNdzbpPChrcYIWOcQT3GJU89V/MLvom1bMhzD3vwPBElLv5oSPqwcZ5NIBkE3xbBjov+r9QQoZS1nMQbx/9KdQIDAQAB";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        RequestBody body = request.body();
        Buffer buffer = new Buffer();
        body.writeTo(buffer);
        Charset charset = Charset.forName("UTF-8");
        MediaType contentType = body.contentType();
        if (contentType != null) {
            charset = contentType.charset(charset);
        }
        String paramsStr = buffer.readString(charset);
        String aesKey;
        String encodedData;
        String result = "";
        try {
            //1.生成AES密钥
            String AesKey = AES.generateKeyString();
            LogUtil.w("guoj", "AESKEY==" + AesKey);
            //2.使用RSA公钥加密刚刚生成的AES密钥
            aesKey = AES.byte2hex(RSACoder.encryptByPublicKey(AesKey.getBytes(), publicKey));
            LogUtil.w("guoj", "RSA加密---->" + RSACoder.encryptByPublicKey(AesKey.getBytes(), publicKey));
            LogUtil.w("guoj", "RSA+AES---->" + aesKey);
            //3.使用第1步生成的AES密钥，通过AES加密需要提交给服务端的数据；
            encodedData = AES.encrypt(paramsStr, AesKey);
            LogUtil.w("guoj", "AES加密---->" + encodedData);
            result = aesKey + "=" + encodedData;
            LogUtil.w("guoj", "KEY=VALUE---->" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain; charset=utf-8"), result);
        request = request
                .newBuilder()
                .post(requestBody)
                .build();
        return chain.proceed(request);
    }
}
