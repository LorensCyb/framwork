package cyb.xandroid.javatest.okhttp;


import java.io.IOException;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class XOkHttpUtil {

    public static final MediaType JSON = MediaType.parse("application/json;chartset=utf-8");

    public static OkHttpClient okHttpClient = new OkHttpClient();


    /**
     * @param url
     * @return
     */
    public static Response post(String url, String json) {
        RequestBody body = RequestBody.create(JSON, json);
        Headers headers = new Headers.Builder()
                .add("")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .headers(headers)
                .tag(null)
                .build();
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }


}