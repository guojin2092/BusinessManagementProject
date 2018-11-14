package baselibrary.library.http;

import okhttp3.RequestBody;

/**
 * @author Administrator
 */
public class JsonUrlUtil {
    /**
     * 拼接jsonUrl
     *
     * @param url
     * @param requestBody
     * @return
     */
    public static String getJsonUrl(String url, RequestBody requestBody) {
        String jsonUrl;
        if (requestBody == null)
            jsonUrl = url;
        else {
            if (url.contains("?")) {

                jsonUrl = url + "&" + requestBody;
            } else {

                jsonUrl = url + "?" + requestBody;
            }
        }
        return jsonUrl;
    }
}
