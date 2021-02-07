package com.uottawahack.chefswipe;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.Executor;

public class SwipeRepository {
    private static SwipeRepository instance;
    public RequestQueue requestQueue;
    private static Context ctx;




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
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }
    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}
