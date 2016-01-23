package uofthacks2016.atychos.resources;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import uofthacks2016.atychos.network.AtychosKeys;
import uofthacks2016.atychos.network.AtychosRequestQueue;
import uofthacks2016.atychos.network.AtychosURLEndPoints;

/**
 * Created by vitaliy on 2016-01-23.
 */
public class SingletonUser {

    private static SingletonUser instance;

    // instance variable
    private User user;
    private List<Member> memberList;
    private static Context ctx;
    private static AtychosRequestQueue requestQueue;

    private SingletonUser(Context context) {
        ctx = context;

        /**
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);

        this.user = new User(telephonyManager.getLine1Number()); */

        this.user = new User("9054443333");
        requestQueue = AtychosRequestQueue.getInstance(context);
    }

    public static SingletonUser getInstance(Context context) {
        if (instance == null){
            instance = new SingletonUser(context);
        }

        return instance;
    }

    public User getUser() throws JSONException {
        JSONObject user = new JSONObject();
        user.put(AtychosKeys.NUMBER, this.user.getNumber());
        JSONObject param = new JSONObject();
        param.putOpt(AtychosKeys.USER, user);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                AtychosURLEndPoints.USER_GET, param,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("Response", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("error", error.toString());
                    }
                }
        );

        requestQueue.addToRequestQueue(request);
        return this.user;
    }

    public List<Member> getMemberList() {
        return this.memberList;
    }
}