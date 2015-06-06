package com.example.faith.upandeapp;

import android.app.Activity;
import android.content.Intent;
import android.preference.PreferenceActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends Activity {
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
    }

    private void fetchForms() {
        client = new OnaApiClient("justus","12345678");
        client.getMyForms(new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject responseBody) {
                JSONArray items = null;
                try {
                    // Get forms json array
                    items = responseBody.getJSONArray("forms");
                    // Parse json array into array of model objects
                    ArrayList<MyForm> forms = MyForm.fromJson(items);
                    // Load model objects into the adapter
                    for (MyForm form : forms) {
                        adapterForms.add(form); // add form through the adapter
                    }
                    adapterForms.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
