package com.example.faith.upandeapp;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class formdetails2 extends Activity {
    /*private TextView text1;
    private  TextView text2;
    private  TextView text3;
    private TextView text4;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formdetails2);
        /*text1=(TextView)findViewById(R.id.text1);
        text2=(TextView)findViewById(R.id.text2);
        text3= (TextView)findViewById(R.id.text3);
        text4=(TextView)findViewById(R.id.text4);*/
        //load form data
      /* MyForm form = (MyForm) getIntent().getSerializableExtra(MainActivity.FORM_DETAIL_KEY);
        loadform(form);
    }

    private void loadform(MyForm form) {
        if (android.os.Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.HONEYCOMB) {
            getActionBar().setTitle(form.getTitle());
        }*/
        //populate data
        /*text1.setText(form.getName());
        text2.setText(form.getAge());
        text3.setText(form.getGender());
        text4.setText(form.getLocation());*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_formdetails2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
