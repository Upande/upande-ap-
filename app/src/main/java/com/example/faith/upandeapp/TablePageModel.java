package com.example.faith.upandeapp;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by justus on 6/10/15.
 */
public class TablePageModel {


    private String question;
    private String answer;
    /*private String name;
    private String gender;
    private int age;
    private String location;
    private String pizza_type;
*/

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
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
    }*/

    public static ArrayList<TablePageModel> fromJson(JSONArray jsonArray) {
        ArrayList<TablePageModel> formDetails = new ArrayList<TablePageModel>(jsonArray.length());
        // Process each result in json array, decode and convert to formdetails
        // object
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject formdetailsJson = null;
            try {
                formdetailsJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            TablePageModel formDetail = TablePageModel.fromJson(formdetailsJson);
            if (formDetail != null) {
                formDetails.add(formDetail);
            }
        }

        return formDetails;
    }

    public static TablePageModel fromJson(JSONObject jsonObject) {
        TablePageModel details =new TablePageModel();

        for (int i = 0; i < jsonObject.length(); i++) {
            details = new TablePageModel();
            try {

                //Deserialize json into object fields
                details.question = jsonObject.getString("_notes");
                details.answer = jsonObject.getString("_bamboo_dataset_id");
                /*details.name=jsonObject.getString("name");
                details.gender=jsonObject.getString("gender");
                details.age=jsonObject.getInt("age");
                details.location=jsonObject.getString("location");
                details.pizza_type=jsonObject.getString("pizza_type");*/

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }


        }
        return details;

    }
}
