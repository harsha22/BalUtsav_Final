package com.lawersinc.client.http;

import android.app.DownloadManager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mntiwari on 11/2/15.
 */
public class HttpOperations {

    private String url = "";

    public HttpOperations(){}

    public HttpOperations(String url) {
        this.url = url;
    }

    public JsonObjectRequest getRequest(String url) {
        this.url = url;
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                try {
                    response = response.getJSONObject("args");
                    String site = response.getString("site"),
                            network = response.getString("network");
                    System.out.println("");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        return jsonRequest;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}