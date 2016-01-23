package uofthacks2016.atychos.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by vitaliy on 2016-01-23.
 */
public class AtychosRequestQueue {

    private static AtychosRequestQueue instance;

    // Instance Variables
    private RequestQueue requestQueue;
    private static Context ctx;

    private AtychosRequestQueue(Context context){
        ctx = context;
    }

    public static synchronized AtychosRequestQueue getInstance(Context context){
        if (instance == null){
            instance = new AtychosRequestQueue(context);
        }

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