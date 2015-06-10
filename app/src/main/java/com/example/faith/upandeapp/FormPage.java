package com.example.faith.upandeapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


public class FormPage extends Activity {
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
//
//        htextview[0] = (TextView) findViewById(R.id.text1);
//        htextview[1] = (TextView) findViewById(R.id.text2);
//        htextview[2] = (TextView) findViewById(R.id.text3);
//        htextview[3] = (TextView) findViewById(R.id.text4);
//        htextview[4] = (TextView) findViewById(R.id.text5);
//        htextview[5] = (TextView) findViewById(R.id.text6);
//        htextview[6] = (TextView) findViewById(R.id.text7);
//        htextview[7] = (TextView) findViewById(R.id.text8);
//        htextview[8] = (TextView) findViewById(R.id.text9);
//        htextview[9] = (TextView) findViewById(R.id.text10);
//
//        for (int i = 0; i < 10; i++) {
//            htextview[i].setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    startActivity(new Intent(FormPage.this, EntryPage.class));
//
//                }
//            });
//
//        }


        fetchForms();
    }

     private void fetchForms() {

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


            // where the magic of fetching forms details happen

//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
//                JSONArray items = response;
//                Log.d("String", "Data is coming in");
//
//                // Parse json array into array of model objects
//                ArrayList<MyForm> forms = MyForm.fromJson(items);
//
//
//                // Load model objects into the adapter
//                for (MyForm form : forms) {
//                    adapterForms.add(form); // add form through the adapter
//                }
//                adapterForms.notifyDataSetChanged();
//
//            }


        });
    } // end of fetchformsdetails method
}
