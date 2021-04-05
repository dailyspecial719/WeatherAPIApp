package com.generally2.weatherapiapp;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherDataService {

    public static final String QUERY_FOR_CITY_ID = "https://www.metaweather.com/api/location/search/?query=";

    Context context;
    String cityId;

    public WeatherDataService(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener{
        void onError(String message);

        void onResponse(String cityID);
    }

    public void getCityID(String cityName,VolleyResponseListener volleyResponseListener ){
        String url = QUERY_FOR_CITY_ID + cityName;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                cityId = "";
                try {
                    JSONObject cityInfo = response.getJSONObject(0);
                    cityId  = cityInfo.getString("woeid");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //this woked but didnt return in main activity
                //Toast.makeText(context, "City Id " + cityId, Toast.LENGTH_SHORT).show();
                volleyResponseListener.onResponse(cityId);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
                volleyResponseListener.onError("Something Wrong!");
            }
        });


        MySingleton.getInstance(context).addToRequestQueue(request);
        //returned null in main activity
       // return cityId;

   }


  /*  public List<WeatherReportModel> getCityForecastByID(String cityID){

    }
    public List<WeatherReportModel> getCityForecastByName(String cityName){

    }*/

}
