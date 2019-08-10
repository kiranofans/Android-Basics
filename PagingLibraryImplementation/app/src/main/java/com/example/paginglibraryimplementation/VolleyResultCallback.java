package com.example.paginglibraryimplementation;

import com.android.volley.VolleyError;

public interface VolleyResultCallback<T> {
    void jsonResponse(T response);
    void responseError(VolleyError error);
}
