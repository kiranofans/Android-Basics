package com.example.paginglibraryimplementation;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class VolleySingleton {
    private static VolleySingleton volleySingleton;
    private Context mContext;

    private RequestQueue requestQueue;

    private VolleySingleton(Context context) {
        this.mContext = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized VolleySingleton getInstance(Context context){
        return volleySingleton = new VolleySingleton(context);
    }

    public void volleyGETRequest(String requestURL,final VolleyResultCallback mResultCallback){
        JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.GET, requestURL, null, new
                Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(response != null){
                            mResultCallback.jsonResponse(response);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error != null){
                    mResultCallback.responseError(error);
                }
            }
        });
        requestQueue.add(jsonObj);
    }

    public void volleyPOSTRequest(String requestUrl,final VolleyResultCallback mResultCallback){
        //RequestQueue queue = Volley.newRequestQueue(mContext);
        JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.GET, requestUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (mResultCallback != null)
                    mResultCallback.jsonResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (mResultCallback != null)
                    mResultCallback.responseError(error);
            }
        });
        requestQueue.add(jsonObj);
    }

    private RequestQueue getRequestQueue(){
        if (requestQueue == null) {
            /**getApplicationContext() is key,it keeps you from leaking the
             activity or BroadcastReceiver if someone passes one in.*/
            requestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return requestQueue;
    }
}
