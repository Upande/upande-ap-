package com.example.faith.upandeapp;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

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

    public OnaApiClient(String username, String password){
        this.client = new AsyncHttpClient();
        this.username = username;
        this.password = password;

        Log.d("STRING", "created MACLIENTELES");

    }

    private String getApiUrl(String relativeUrl){return  API_BASE_URL + relativeUrl; }
    private String getFomUrl(String formUrl){return  formUrl;}

    public void getMyForms(JsonHttpResponseHandler handler){

        String url = getApiUrl("data?format=json");

        client.setBasicAuth(username,password);
        client.get(url, new RequestParams(username,password), handler);



    }
     public void getFormDetails(JsonHttpResponseHandler handler){

        /* formUrl=getIntent().getStringExtra("formUrl");
*/
         String formdetailsurl = "https://ona.io/api/v1/data/55401";


        client.get(formdetailsurl, new RequestParams(username, password), handler);

         Log.d("STRING",formdetailsurl);



    }


}
