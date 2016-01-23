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

    private SingletonUser(Context context) {
        ctx = context;

        /**
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);

        this.user = new User(telephonyManager.getLine1Number()); */

        //this.user = new User("9054443333");
    }

    public static SingletonUser getInstance(Context context) {
        if (instance == null){
            instance = new SingletonUser(context);
        }

        return instance;
    }

    public User getUser() throws JSONException {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Member> getMemberList() {
        return this.memberList;
    }

    public void setMemberList(List<Member> memberList){
        this.memberList = memberList;
    }
}