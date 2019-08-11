package com.example.paginglibraryimplementation;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

public interface VolleyResultCallback<T> {
    void jsonResponse(JSONObject response) throws JSONException;
    void responseError(VolleyError error);
}
