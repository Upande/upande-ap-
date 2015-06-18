package com.example.faith.upandeapp;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.preference.PreferenceActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

//import android.widget.AdapterView.OnItemClickListener;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnItemClickListener;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements OnItemClickListener {
    private android.support.v4.app.FragmentManager fragmentManager;
    private ContextMenuDialogFragment mMenuDialogFragment;
    public static final String FORM_DETAIL_KEY = "form";// to carry required data on to the FormPage class
    OnaApiClient client;
    private ListView lvForms;
    private FormsAdapter adapterForms;
    AlertDialogManager alert = new AlertDialogManager();

    String username;
    String password;
    String formUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mylistview);

       username = getIntent().getStringExtra("username");
       password = getIntent().getStringExtra("password");


        lvForms = (ListView) findViewById(R.id.lvForms);

        ArrayList<MyForm> Forms = new ArrayList<MyForm>();
        adapterForms = new FormsAdapter(this, Forms);
        lvForms.setAdapter(adapterForms);

        initToolbar();
        initMenuFragment();
        fragmentManager = getSupportFragmentManager();

        fetchForms();
        setupFormSelectedListener();

    }


    private void fetchForms() {

        client = new OnaApiClient(username,password,formUrl);

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

            // incase of failure, it could be coz of poor network, or wrong cresentials, for now, I don't care


            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //super.onFailure(statusCode, headers, throwable, errorResponse);

                Toast.makeText(getApplicationContext(),"Username/Password is incorrect",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getBaseContext(),LogInPage.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                //super.onFailure(statusCode, headers, throwable, errorResponse);
                alert.showAlertDialog(MainActivity.this, "Login failed..", "Username/Password is incorrect", false);
                Intent intent = new Intent(getBaseContext(),LogInPage.class);
                //startActivity(intent);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                //super.onFailure(statusCode, headers, responseString, throwable);
                alert.showAlertDialog(MainActivity.this, "Login failed..", "Username/Password is incorrect", false);
                Intent intent = new Intent(getBaseContext(),LogInPage.class);
                //startActivity(intent);
            }
        });
    } // end of fetchforms method

    // we need to add some onclick listener to the forms selected

    public void setupFormSelectedListener() {

        lvForms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View item, int position, long rowId) {


                // Launch the detail view passing form as an extra
                Intent i = new Intent(MainActivity.this, TablePage.class);
                i.putExtra(FORM_DETAIL_KEY, adapterForms.getItem(position));
                i.putExtra("formUrl", adapterForms.getItem(position).getFormUrl());
                i.putExtra("formTitle", adapterForms.getItem(position).getTitle());
                i.putExtra("username", username);
                i.putExtra("password", password);
                Toast.makeText(getBaseContext(), adapterForms.getItem(position).getTitle(), Toast.LENGTH_LONG).show();
                startActivity(i);
            }
        });
    }


 // the magic toolbar from that wasabeef guy

    private void initMenuFragment() {
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.tool_bar_height));
        menuParams.setMenuObjects(getMenuObjects());
        menuParams.setClosableOutside(false);
        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
    }

    private List<MenuObject> getMenuObjects() {
        List<MenuObject> menuObjects = new ArrayList<>();
        MenuObject close = new MenuObject();
        close.setResource(R.drawable.close);
        MenuObject profile = new MenuObject("Profile");
        profile.setResource(R.drawable.manageaccount);
        menuObjects.add(close);
        menuObjects.add(profile);
        return menuObjects;
    }

    private void initToolbar() {
        android.support.v7.widget.Toolbar mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        TextView mToolBarTextView = (TextView) findViewById(R.id.text_view_toolbar_title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        mToolBarTextView.setText("Published Forms");
    }




    protected void addFragment(android.support.v4.app.Fragment fragment, boolean addToBackStack, int containerId) {
        invalidateOptionsMenu();
        String backStackName = fragment.getClass().getName();
        boolean fragmentPopped = fragmentManager.popBackStackImmediate(backStackName, 0);
        if (!fragmentPopped) {
            android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(containerId, fragment, backStackName)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            if (addToBackStack)
                transaction.addToBackStack(backStackName);
            transaction.commit();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context_menu:
                if (fragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
                    mMenuDialogFragment.show(fragmentManager, ContextMenuDialogFragment.TAG);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

    }

    //end of that toolbar thing
}


