package com.uottawahack.chefswipe;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class SwipeRepository {
    private static SwipeRepository instance;
    public RequestQueue requestQueue;
    private final Context ctx;

    private SwipeRepository(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();
    }
    public static synchronized SwipeRepository getInstance(Context context)
    {
        if (null == instance)
            instance = new SwipeRepository(context);
        return instance;
    }
    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }
    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}
