package com.example.flexdaily;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {
    private RequestQueue requestQueue;
    private static VolleySingleton instance;

    private VolleySingleton(Context context){
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    static synchronized VolleySingleton getInstance(Context context){
        if(instance == null){
            instance = new VolleySingleton(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue(){
        return requestQueue;
    }
}
