package com.example.doctor_appointment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.media.CamcorderProfile.get;

public class DataParser {
    private HashMap<String, String> getplaces(JSONObject googleplacesJson){
        HashMap<String, String> googleplacesmap = new HashMap<>();
        String placename = "-NA-";
        String vicinity = "-NA-";
        String latitude = "";
        String longitude = "";
        String reference = "";

        try {
            if(!googleplacesJson.isNull("name")){
                placename = googleplacesJson.getString("name");
            }
            if(!googleplacesJson.isNull("Vicinity")){
                vicinity = googleplacesJson.getString("Vicinity");
            }
            latitude = googleplacesJson.getJSONObject("geometrt").getJSONObject("location").getString("lat");
            longitude = googleplacesJson.getJSONObject("geometrt").getJSONObject("location").getString("lng");

            reference = googleplacesJson.getString("reference");

            googleplacesmap.put("Place name", placename);
            googleplacesmap.put("vicinity", vicinity);
            googleplacesmap.put("lat", latitude);
            googleplacesmap.put("lng", longitude);
            googleplacesmap.put("Reference", reference);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return googleplacesmap;
    }
    private List<HashMap<String, String>> getplace(JSONArray jsonArray){
        int count =jsonArray.length();
        List<HashMap<String, String>> placeList = new ArrayList<>();
        HashMap<String, String> placemap = null;

                for(int i=0;i<count;i++){
                    try {
                        placemap = getplaces((JSONObject) jsonArray.get(i));
                        placeList.add(placemap);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return placeList;
    }
    public List<HashMap<String,String>> parse(String jsonData){
        JSONObject jsonObject;
        JSONArray jsonArray = null;

        try {
            jsonObject = new JSONObject(jsonData);
            jsonArray = jsonObject.getJSONArray("result");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getplace(jsonArray);
    }
}
