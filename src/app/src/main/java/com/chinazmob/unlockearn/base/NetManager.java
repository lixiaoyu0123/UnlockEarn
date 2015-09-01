package com.chinazmob.unlockearn.base;

import com.chinazmob.unlockearn.AppConfig;
import com.chinazmob.unlockearn.Interface.IPostCallback;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.util.List;

/**
 * Created by 晓宇 on 2015/3/2.
 */
public class NetManager {
    private static String TOKEN = null;
    private static volatile NetManager INSTANCE = new NetManager();

    public static NetManager getInstance() {
        return INSTANCE;
    }

    public static void post(String key, String value, final IPostCallback callback) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put(key, value);
        client.post(AppConfig.SERVER_URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                callback.success(new String(bytes));
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                callback.failed();
            }
        });
    }

    public static void putParam(List<String> params, String key,String value) {
        if(!params.isEmpty()){
            params.add(",");
        }
        params.add(String.format("\"%s\":\"%s\"",key,value));
    }

    public static String getFunRequestData(String funCode,String parms){
      return String.format("{\"FUNC\":\"%s\",\"PARAM\":{%s}}",funCode,parms);
    }
}