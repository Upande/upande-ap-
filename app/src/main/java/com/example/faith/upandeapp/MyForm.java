package com.example.faith.upandeapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by justus on 6/6/15.
 */
public class MyForm {
    private String title;
    private String description;
    private String formUrl;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getFormUrl() {
        return formUrl;
    }



    public static ArrayList<MyForm> fromJson(JSONArray jsonArray) {
        ArrayList<MyForm> forms = new ArrayList<MyForm>(jsonArray.length());
        // Process each result in json array, decode and convert to form
        // object
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject formsJson = null;
            try {
                formsJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            MyForm form = MyForm.fromJson(formsJson);
            if (form != null) {
                forms.add(form);
            }
        }

        return forms;
    }

    public static MyForm fromJson(JSONObject jsonObject) {
        MyForm form = new MyForm();
        try {
            // Deserialize json into object fields
            form.title = jsonObject.getString("title");
            form.description = jsonObject.getString("description");
            form.formUrl = jsonObject.getString("formurl");



        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return form;
    }
}
