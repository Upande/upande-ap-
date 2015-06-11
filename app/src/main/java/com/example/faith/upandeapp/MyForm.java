package com.example.faith.upandeapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by justus on 6/6/15.
 */
public class MyForm implements Serializable {
    private static final long serialVersionUID = -8959832007991513854L; // I need to pass on some data to formPage class
    private String title;
    private String description;
    private String formUrl;
   /* private String name;
    private String gender;
    private int age;
    private String location;
    private String pizza_type;*/

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getFormUrl() {
        return formUrl;
    }

  /*  public String getName(){
        return name;
    }
    public String getGender(){
        return gender;
    }
    public int getAge(){
        return age;
    }
    public String getLocation(){
        return location;

    }
    public String getPizza_type(){
        return  pizza_type;
    }
*/


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
            form.formUrl = jsonObject.getString("url");
           /* form.name=jsonObject.getString("name");
            form.gender=jsonObject.getString("gender");
            form.age=jsonObject.getInt("age");
            form.location=jsonObject.getString("location");
            form.pizza_type=jsonObject.getString("pizza_type");*/



        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return form;
    }
}
