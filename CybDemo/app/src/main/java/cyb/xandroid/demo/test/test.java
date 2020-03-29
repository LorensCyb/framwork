package cyb.xandroid.demo.test;

import android.os.AsyncTask;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import retrofit2.Retrofit;

/**
 * Created by _Lorens on 2018/1/17.
 */

public class test {

    public test(){
//        MAsyncTask mAsyncTask = new MAsyncTask();
//        Retrofit retrofit = new Retrofit.Builder();
//                .baseUrl("")
//                .addCallAdapterFactory(Gson)
//                .build();


    }

    private class  MAsyncTask extends AsyncTask<Object,Object,Object>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Object... objects) {
            return null;
        }

        @Override
        protected void onProgressUpdate(Object... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }


}
