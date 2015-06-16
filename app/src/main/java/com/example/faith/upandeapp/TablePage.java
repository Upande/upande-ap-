package com.example.faith.upandeapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


public class TablePage extends Activity {
    TextView htextview[] = new TextView[10];

    OnaApiClient client1;
    private ListView lvForms;
    private FormsAdapter adapterForms;
    AlertDialogManager alert = new AlertDialogManager();

    public String username;
    public String password;
    public String formUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_page);

        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");
        formUrl=getIntent().getStringExtra("formUrl");

        // time to test some code




        fetchFormsDetails();
    }

     private void fetchFormsDetails() {

        client1 = new OnaApiClient(username,password,formUrl);

        client1.getFormDetails(new JsonHttpResponseHandler() {

            // this is for debugging purpose only
            @Override
            protected Object parseResponse(byte[] responseBody) throws JSONException {
                String string = null;
                try {
                    string = new String(responseBody, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Log.d("STRING", string);
                return super.parseResponse(responseBody);

            }


            //where the magic of fetching forms details happen

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                JSONArray items = response;
                Log.d("STRING", "I have the array of each individual data now");

                Log.d("STRING", items.toString());
            }


        });
    } // end of fetchformsdetails method

    // where the magic of fetching forms details happen









}

