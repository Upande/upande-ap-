package com.example.faith.upandeapp;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by justus on 6/5/15.
 */
public class OnaApiClient {

    private String username;
    private String password;

    private String base_url;

    private AsyncHttpClient client;

    public OnaApiClient(String username, String password){
        this.client = new AsyncHttpClient();
        this.username = username;
        this.password = password;
        this.base_url = "http://"+username+":"+password+"@ona.io/api/v1/";
        Log.d("STRING", "created MACLIENTELES");

    }

    private String getApiUrl(String relativeUrl){
        return  base_url + relativeUrl;
    }

    public void getUserForms(JsonHttpResponseHandler handler){
        String url = getApiUrl("data?owner="+username);
        Log.d("STRING",url);

        client.get(url, new RequestParams(), handler);

    }
}
