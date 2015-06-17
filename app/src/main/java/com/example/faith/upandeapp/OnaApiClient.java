package com.example.faith.upandeapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.webkit.WebView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.auth.AuthScope;

/**
 * Created by justus on 6/5/15.
 */
public class OnaApiClient extends Activity{

    private String username;
    private String password;
    private String formUrl;

    private final String API_BASE_URL = "http://@ona.io/api/v1/";


    private AsyncHttpClient client;

    WebView webView;


    public OnaApiClient(String username, String password, String formUrl){
        this.client = new AsyncHttpClient();

        this.username = username;
        this.password = password;
        this.formUrl = formUrl;

        Log.d("STRING", "created MACLIENTELES");

    }

    private String getApiUrl(String relativeUrl){return  API_BASE_URL + relativeUrl; }
    private String getFormUrl(String formUrl){return  formUrl; }


    public void getMyForms(JsonHttpResponseHandler handler){

        String url = getApiUrl("data?format=json");

        client.setBasicAuth(username,password);
        client.get(url, new RequestParams(username,password), handler);



    }
     public void getFormDetails(JsonHttpResponseHandler handler){


         String formdetailsurl = getFormUrl(formUrl);
         String formChartsUrl = formdetailsurl.replaceAll("data","charts").concat(".html?field_name=pizza_fan");

         client.setBasicAuth(username, password);
         client.get(formChartsUrl, new RequestParams(username, password), handler);


         Log.d("STRING", formChartsUrl);

    }


}
