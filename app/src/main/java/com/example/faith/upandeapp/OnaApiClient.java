package com.example.faith.upandeapp;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.auth.AuthScope;

/**
 * Created by justus on 6/5/15.
 */
public class OnaApiClient {

    private String username = "justus";
    private String password = "12345678";

    private final String API_BASE_URL = "http://@ona.io/api/v1/";
    public String url;

    private AsyncHttpClient client;

    public OnaApiClient(String username, String password){
        this.client = new AsyncHttpClient();

        this.username = username;
        this.password = password;

        Log.d("STRING", "created MACLIENTELES");

    }

    private String getApiUrl(String relativeUrl){
        return  API_BASE_URL + relativeUrl;
    }

    public void getMyForms(JsonHttpResponseHandler handler){
        String url = getApiUrl("data?format=json");
        Log.d("STRING",url);

        RequestParams params = new RequestParams("username", username);

        client.get(url, params, handler);

        Log.d("STRING", url.toUpperCase());

    }
}
