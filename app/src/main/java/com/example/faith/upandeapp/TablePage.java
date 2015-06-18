package com.example.faith.upandeapp;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnItemClickListener;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;


public class TablePage extends ActionBarActivity implements OnItemClickListener {
    private android.support.v4.app.FragmentManager fragmentManager;
    private ContextMenuDialogFragment mMenuDialogFragment;

    OnaApiClient client;
    private ListView lvForms;
    private FormsAdapter adapterForms;
    AlertDialogManager alert = new AlertDialogManager();

    public String username;
    public String password;
    public String formUrl;
    public String formTitle;
    WebView webView;
    public   String webString = "the string";





    @Override
    protected void onCreate(Bundle savedInstanceState) {

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork() // or .detectAll() for all detectable problems
                .penaltyLog().build());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_page);

        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");
        formUrl=getIntent().getStringExtra("formUrl");
        formTitle=getIntent().getStringExtra("formTitle");

        initToolbar();
        initMenuFragment();
        fragmentManager = getSupportFragmentManager();

        fetchFormsDetails();

        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadDataWithBaseURL("https://cdnjs.cloudflare.com/ajax/libs/d3/3.5.5/d3.min.js", webString, "text/html", null, null);



    }

     private void fetchFormsDetails()  {



        client = new OnaApiClient(username,password,formUrl);

        client.getFormDetails(new JsonHttpResponseHandler() {



            // this is for debugging purpose only
            @Override
            public Object parseResponse(byte[] responseBody) throws JSONException {

                String result = null;


                try {
                    result = new String(responseBody, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }


                Log.d("STRING", result);
                webString = result;

                Log.d("STRING", webString);



                return super.parseResponse(responseBody);


            }

        });


    } // end of fetchformsdetails method


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
        mToolBarTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), MainActivity.class);

                i.putExtra("username", username);
                i.putExtra("password", password);
                startActivity(i);
                finish();
            }
        });

        mToolBarTextView.setText(formTitle);
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
}

