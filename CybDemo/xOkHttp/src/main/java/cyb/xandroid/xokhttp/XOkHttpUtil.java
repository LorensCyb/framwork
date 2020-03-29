package cyb.xandroid.xokhttp;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class XOkHttpUtil {

    public static final MediaType JSON = MediaType.parse("application/json;chartset=utf-8");

    public static OkHttpClient okHttpClient = new OkHttpClient();


    /**
     *
     * @param url
     * @return
     */
    public static Object post(String url,String json){
        RequestBody body =  null;
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .headers(null)
                .build();
        Response response = null;
        try {
             response = okHttpClient.newCall(request).execute();
             okHttpClient.newCall(request).enqueue(new Callback() {
                 @Override
                 public void onFailure(Call call, IOException e) {

                 }

                 @Override
                 public void onResponse(Call call, Response response) throws IOException {

                 }
             });
        } catch (IOException e) {
            e.printStackTrace();
        }

        Retrofit retrofit =new Retrofit.Builder()
                .baseUrl("")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Call call = retrofit.create(null);
//        call.execute(null);
//        ThreadPoolExecutor
//        SparseArray

        return response;
    }


}
