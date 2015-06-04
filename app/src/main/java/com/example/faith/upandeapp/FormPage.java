package com.example.faith.upandeapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class FormPage extends ActionBarActivity {
    TextView htextview[] = new TextView[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_page);

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

        for (int i = 0; i < 6; i++) {
            htextview[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(FormPage.this, EntryPage.class));

                }
            });

        }

    }
}
