package uofthacks2016.atychos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import com.thalmic.myo.AbstractDeviceListener;
import com.thalmic.myo.DeviceListener;
import com.thalmic.myo.Hub;
import com.thalmic.myo.Myo;
import com.thalmic.myo.scanner.ScanActivity;

import uofthacks2016.atychos.network.AtychosKeys;
import uofthacks2016.atychos.network.AtychosRequestQueue;
import uofthacks2016.atychos.network.AtychosURLEndPoints;
import uofthacks2016.atychos.resources.Member;
import uofthacks2016.atychos.resources.User;
import uofthacks2016.atychos.resources.SingletonUser;


public class MainActivity extends Activity {

    private AtychosRequestQueue requestQueue;
    private SingletonUser currentUser;
    //Databasehandler db;

    private DeviceListener mListener = new AbstractDeviceListener() {
        // onConnect() is called whenever a Myo has been connected.
        @Override
        public void onConnect(Myo myo, long timestamp) {
            Toast.makeText(getApplicationContext(), "Myo Connected!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, HomePageActivity.class);
            MainActivity.this.startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentUser.getInstance(getApplicationContext());

        // If user exists
        // set content view to activity_main
        //setContentView(R.layout.activity_login);
        setContentView(R.layout.activity_main);
        this.requestQueue = AtychosRequestQueue.getInstance(this);
        // Otherwise set content view to login


        //startMyo();
    }

    void startMyo() {
        Hub hub = Hub.getInstance();
        hub.setMyoAttachAllowance(1);
        if (hub.init(this)) {
            Log.d("ACCEL", "Initialized Myo");
            // TODO handle connection errors.
            hub.attachToAdjacentMyo();
            hub.addListener(mListener);
        } else {
            Log.d("NAO", "Could not initialize the Hub.");
        }
    }

    public boolean getUserInfo(View view) {
        currentUser = SingletonUser.getInstance(this);
        EditText phoneNum = (EditText)findViewById(R.id.phoneNumberField);

        String phoneNumber;
        try {
            phoneNumber = phoneNum.getText().toString();

            Log.i("LOG", "value of phoneNumber is: " + phoneNumber);

            JSONObject user = new JSONObject();
            user.put(AtychosKeys.NUMBER, phoneNumber);
            JSONObject param = new JSONObject();
            param.putOpt(AtychosKeys.USER, user);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                    AtychosURLEndPoints.USER_GET, param,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.i("Response", response.toString());

                            try {
                                User newuser = new User(response);
                                Log.i("LOG", "value of user " + newuser.getNumber());
                                currentUser.setUser(newuser);
                            } catch(Exception e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("error", error.toString());
                        }
                    }
            );
            this.requestQueue.addToRequestQueue(request);

        }
        catch(Exception e) {
            //log.e("Error");
            Log.i("error2", e.toString());
            e.printStackTrace();
            return false;
        }

        // Set singletonuser



        setContentView(R.layout.activity_entermember);

        return true;

    }

    public Boolean getContactInfo(View view) {
        EditText contactNum = (EditText)findViewById(R.id.contactNumberField);

        String contactNumber;

        try {

            contactNumber = contactNum.getText().toString();
            Log.i("LOG", "value of contactNumber is: " + contactNumber);

            JSONObject member = new JSONObject();
            member.put(AtychosKeys.NUMBER, contactNumber);
            member.put(AtychosKeys.USER_ID,this.currentUser.getUser().getId());
            JSONObject param = new JSONObject();
            param.putOpt(AtychosKeys.MEMBER, member);

            Log.i("LOG", "I am in this method");

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                    AtychosURLEndPoints.MEMBER_ADD, param,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.i("Response", response.toString());

                            try {
                                Member newmember = new Member(response);
                            } catch(Exception e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("error", error.toString());
                        }
                    }
            );
            this.requestQueue.addToRequestQueue(request);
            Intent intent = new Intent(MainActivity.this, SensorActivity.class);
            MainActivity.this.startActivity(intent);

        }
        catch(Exception e) {
            //log.e("Error");
            Log.i("error2", e.toString());
            e.printStackTrace();
            return false;
        }

        setContentView(R.layout.activity_app_main);

        return true;
    }

    public void goToLoginPage(View view) {
        setContentView(R.layout.activity_login);
    }

    public void goToMainPage(View view) {
        setContentView(R.layout.activity_main);
    }

    public void goToenterMemberpage(View view) {
        setContentView(R.layout.activity_entermember);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
