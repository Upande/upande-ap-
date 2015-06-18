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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
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
    String string;


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

        Log.d("STRING", "LETS SEE IF IT WORKS");

        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        //webView.setBackgroundColor(Color.BLACK);
        String test = "<head>\n" +
                "    <script src=\"https://cdnjs.cloudflare.com/ajax/libs/d3/3.5.5/d3.min.js\" charset=\"utf-8\"></script>\n" +
                "    <script src='http://dimplejs.org/dist/dimple.v1.1.3.min.js' type='text/javascript'></script>\n" +
                "    </head>\n" +
                "    <body>\n" +
                "<h1>I am here </h1>" +
                "    <script type=\"text/javascript\">\n" +
                "    (function(elmSelector){\n" +
                "    var svg = dimple.newSvg(elmSelector, 590, 400);\n" +
                "    var data = [\n" +
                "    { \"Do you like pizza?\": \"Yes\", \"count\": \"2\" },\n" +
                "    { \"Do you like pizza?\": \"No\", \"count\": \"3\" },\n" +
                "    ];\n" +
                "    var chart = new dimple.chart(svg, data);\n" +
                "    chart.setBounds(60, 30, 510, 305)\n" +
                "    var categoryAxisLocation =  \"y\" ;\n" +
                "    var countAxisLocation =  \"x\" ;\n" +
                "    var categoryAxis = chart.addAxis(categoryAxisLocation, \"Do you like pizza?\", null);\n" +
                "    var countAxis = chart.addMeasureAxis(countAxisLocation, \"count\");\n" +
                "    chart.addSeries(null, dimple.plot.bar);\n" +
                "    chart.draw();\n" +
                "    countAxis.titleShape.style('font-size', '12px');\n" +
                "    categoryAxis.titleShape.style('font-size', '12px');\n" +
                "    })('body');\n" +
                "    </script>\n" +

                "    </body>";

        Log.d("STRING","SHOW ME STRING");
       // Log.d("STRING", string);
        webView.loadUrl("https://ona.io/api/v1/charts/57278.html?field_name=pizza_fan");




    }

     private void fetchFormsDetails() {



        client = new OnaApiClient(username,password,formUrl);

        client.getFormDetails(new JsonHttpResponseHandler() {

            // this is for debugging purpose only
            @Override
            public  Object parseResponse(byte[] responseBody) throws JSONException {
                 string = null;
                try {
                    string = new String(responseBody, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Log.d("STRING", string);


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

