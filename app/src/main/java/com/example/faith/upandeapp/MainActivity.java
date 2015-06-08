package com.example.faith.upandeapp;

import android.app.Activity;
import android.content.Intent;
import android.preference.PreferenceActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import android.widget.AdapterView.OnItemClickListener;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


public class MainActivity extends Activity {
    public static final String FORM_DETAIL_KEY = "form";// to carry required data on to the FormPage class
    OnaApiClient client;
    private ListView lvForms;
    private FormsAdapter adapterForms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mylistview);
        lvForms = (ListView) findViewById(R.id.lvForms);
        ArrayList<MyForm> Forms = new ArrayList<MyForm>();
        adapterForms = new FormsAdapter(this, Forms);
        lvForms.setAdapter(adapterForms);

        fetchForms();
        setupFormSelectedListener();



    }

    private void fetchForms() {
        client = new OnaApiClient("justus", "12345678");
        client.getMyForms(new JsonHttpResponseHandler() {

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
        });
    } // end of fetchforms method

    // we need to add some onclick listener to the forms selected

    public void setupFormSelectedListener() {
        Log.d("STRANGE","We are soo in..");
        lvForms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View item, int position, long rowId) {

                Log.d("STRANGE","and in work");

                Toast.makeText(getBaseContext(),"Does it work?",Toast.LENGTH_LONG).show();
                // Launch the detail view passing form as an extra
                Intent i = new Intent(MainActivity.this, FormPage.class);
                i.putExtra(FORM_DETAIL_KEY, adapterForms.getItem(position));
                startActivity(i);
            }
        });
    }
}


