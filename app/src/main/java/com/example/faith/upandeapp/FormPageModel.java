package com.example.faith.upandeapp;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by justus on 6/10/15.
 */
public class FormPageModel {


    private String question;
    private String answer;
    private String formUrl;

    public String getTitle() {
        return question;
    }

    public String getDescription() {
        return answer;
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

//    public static MyForm fromJson(JSONObject jsonObject) {
//        MyForm form = new MyForm();
//        try {
//            // Deserialize json into object fields
////            form.title = jsonObject.getString("title");
////            form.description = jsonObject.getString("description");
////            form.formUrl = jsonObject.getString("url");
//
//
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
//        // Return new object
//        return form;
//    }

}
