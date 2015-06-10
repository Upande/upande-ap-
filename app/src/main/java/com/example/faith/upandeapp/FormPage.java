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

    OnaApiClient client;
    private ListView lvForms;
    private FormsAdapter adapterForms;
    AlertDialogManager alert = new AlertDialogManager();

    String username;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_page);

        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");

        htextview[0] = (TextView) findViewById(R.id.text1);
        htextview[1] = (TextView) findViewById(R.id.text2);
        htextview[2] = (TextView) findViewById(R.id.text3);
        htextview[3] = (TextView) findViewById(R.id.text4);
        htextview[4] = (TextView) findViewById(R.id.text5);
        htextview[5] = (TextView) findViewById(R.id.text6);
        htextview[6] = (TextView) findViewById(R.id.text7);
        htextview[7] = (TextView) findViewById(R.id.text8);
        htextview[8] = (TextView) findViewById(R.id.text9);
        htextview[9] = (TextView) findViewById(R.id.text10);

        for (int i = 0; i < 10; i++) {
            htextview[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(FormPage.this, EntryPage.class));

                }
            });

        }


        fetchForms();
    }

     private void fetchForms() {

        client = new OnaApiClient(username,password);

        client.getFormDetails(new JsonHttpResponseHandler() {

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

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                JSONArray items = response;
                Log.d("String", "Data is coming in");

                // Parse json array into array of model objects
                ArrayList<MyForm> forms = MyForm.fromJson(items);


                // Load model objects into the adapter
                for (MyForm form : forms) {
                    adapterForms.add(form); // add form through the adapter
                }
                adapterForms.notifyDataSetChanged();

            }

            // incase of failure, it could be coz of poor network, or wrong cresentials, for now, I don't care


//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                //super.onFailure(statusCode, headers, throwable, errorResponse);
//
//                Toast.makeText(getApplicationContext(), "Username/Password is incorrect", Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(getBaseContext(), LogInPage.class);
//                startActivity(intent);
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
//                //super.onFailure(statusCode, headers, throwable, errorResponse);
//                alert.showAlertDialog(FormPage.this, "Login failed..", "Username/Password is incorrect", false);
//                Intent intent = new Intent(getBaseContext(), LogInPage.class);
//                //startActivity(intent);
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                //super.onFailure(statusCode, headers, responseString, throwable);
//                alert.showAlertDialog(FormPage.this, "Login failed..", "Username/Password is incorrect", false);
//                Intent intent = new Intent(getBaseContext(), LogInPage.class);
//                //startActivity(intent);
//            }
        });
    } // end of fetchformsdetails method
}
