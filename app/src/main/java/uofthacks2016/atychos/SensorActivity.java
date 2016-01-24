package uofthacks2016.atychos;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import java.lang.Math;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import uofthacks2016.atychos.network.AtychosKeys;
import uofthacks2016.atychos.network.AtychosRequestQueue;
import uofthacks2016.atychos.network.AtychosURLEndPoints;
import uofthacks2016.atychos.resources.Member;
import uofthacks2016.atychos.resources.User;
import uofthacks2016.atychos.resources.SingletonUser;


public class SensorActivity extends Activity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mAccel;

    private SingletonUser currentUser;

    private static float THRESHOLDACCELERATIONLIMIT = 20;

    private float[] gravity = {0f, 0f, 0f};
    private float[] linear_acceleration = {0f, 0f, 0f};
    private AtychosRequestQueue requestQueue;

    private int lat;
    private int lng;

    private LocationManager locationManager;
    private String provider;

    private boolean shouldRun = true;
    private boolean isGPSEnabled;
    private boolean isNetworkEnabled;

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_sensor);

        currentUser.getInstance(getApplicationContext());
        this.requestQueue = AtychosRequestQueue.getInstance(this);

        /*// Get the location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Define the criteria how to select the locatioin provider -> use
        // default
        Criteria criteria = new Criteria();
        //provider = locationManager.getBestProvider(criteria, false);
        //Location location = locationManager.getLastKnownLocation(provider);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 0, this);
        Location location = locationManager
                .getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (location != null) {
            lat = (int) (location.getLatitude());
            lng = (int) (location.getLongitude());
            Log.d("LOCATION",String.format("%d, %d", lat, lng));
        } else {
            Log.d("ERROR", "Failed to get location");
        }*/
        LocationManager locationManager = (LocationManager) this.getSystemService(getApplicationContext().LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                lat = (int) (location.getLatitude());
                lng = (int) (location.getLongitude());

                Log.d("LOCATION",String.format("%d, %d", lat, lng));
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
        locationManager.requestLocationUpdates(locationManager.NETWORK_PROVIDER, 0, 0, locationListener);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccel = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        if (!this.shouldRun) {
            return;
        }

        // alpha is calculated as t / (t + dT),
        // where t is the low-pass filter's time-constant and
        // dT is the event delivery rate.

        final float alpha = 0.8f;

        // Isolate the force of gravity with the low-pass filter.
        gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
        gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
        gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

        // Remove the gravity contribution with the high-pass filter.
        linear_acceleration[0] = event.values[0] - gravity[0];
        linear_acceleration[1] = event.values[1] - gravity[1];
        linear_acceleration[2] = event.values[2] - gravity[2];

        Log.d("ACCEL",String.format("%f, %f, %f", linear_acceleration[0], linear_acceleration[1], linear_acceleration[2]));

        if(Math.abs(linear_acceleration[0]) > THRESHOLDACCELERATIONLIMIT || Math.abs(linear_acceleration[1]) > THRESHOLDACCELERATIONLIMIT || Math.abs(linear_acceleration[2]) > THRESHOLDACCELERATIONLIMIT) {
            JSONObject sendTxt = new JSONObject();
            try {
                Log.d("LOCATION",String.format("%d, %d", lat, lng));
                sendTxt.put(AtychosKeys.ID, SingletonUser.getInstance(this).getUser().getId());
                sendTxt.put(AtychosKeys.LAT, Integer.toString(lat));
                sendTxt.put(AtychosKeys.LON, Integer.toString(lng));

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                        AtychosURLEndPoints.USER_SEND_ALERT, sendTxt,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.i("Response", response.toString());
                                //Log.d("LOCATION", String.format("%d, %d", lat, lng));
                                shouldRun = false;
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




            } catch(Exception e) {

            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccel, SensorManager.SENSOR_DELAY_NORMAL);
        //locationManager.requestLocationUpdates(provider, 400, 1, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
        //locationManager.removeUpdates(this);
    }

    private float getAccelX(){
        return linear_acceleration[0];
    }

    private float getAccelY(){
        return linear_acceleration[1];
    }

    private float getAccelZ(){
        return linear_acceleration[2];
    }

   /* @Override
    public void onLocationChanged(Location location) {
        lat = (int) (location.getLatitude());
        lng = (int) (location.getLongitude());

        Log.d("LOCATION",String.format("%d, %d", lat, lng));

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }*/
}