package com.example.basicversion;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JsonParse {
    private HashMap<String, String> parseJsonoObject(JSONObject object) {
        HashMap<String, String> list = new HashMap<>();

        try {
            String name = object.getString("name");

            String latitude = object.getJSONObject("geometry")
                    .getJSONObject("location").getString("lat");

            String longtitude = object.getJSONObject("geometry")
                    .getJSONObject("location").getString("lng");

            list.put("name", name);
            list.put("lat", latitude);
            list.put("lng", longtitude);

            Log.i("temp", list.get("name"));


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }


    private List<HashMap<String, String>> parseJsonObject(JSONArray jsonArray) {
        List<HashMap<String, String>> list = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                HashMap<String,String> data = parseJsonoObject((JSONObject) jsonArray.get(i));
                list.add(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public List<HashMap<String, String>> parseResult(JSONObject object) {

        JSONArray jsonArray = null;
        try {
            jsonArray = object.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return parseJsonObject(jsonArray);
    }
}
