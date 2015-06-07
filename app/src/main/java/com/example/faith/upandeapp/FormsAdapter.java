package com.example.faith.upandeapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by justus on 6/6/15.
 */
public class FormsAdapter extends ArrayAdapter<MyForm> {
    public FormsAdapter(Context context, ArrayList<MyForm> forms) {
        super(context, 0, forms);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        MyForm form = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.activity_main, parent, false);
        }
        // Lookup views within item layout
        TextView formtitle = (TextView) convertView.findViewById(R.id.formtitle);
        TextView description = (TextView) convertView.findViewById(R.id.description);
        //TextView formurl = (TextView) convertView.findViewById(R.id.formurl);
       // Populate the data into the template view using the data object
        formtitle.setText(form.getTitle());
        description.setText("Description: "+form.getDescription());
        //formurl.setText(form.getFormUrl());

        // Return the completed view to render on screen
        return convertView;
    }
}
