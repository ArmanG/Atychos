package uofthacks2016.atychos;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.thalmic.myo.Myo;
import com.thalmic.myo.Vector3;


public class AccelerationDetector extends Detector {

    protected final static int THRESHOLD = 10;

    public void onData(long timestamp, Vector3 accel, Myo myo) {
        Log.v("ACCEL",
                String.format("%d, %f, %f, %f", timestamp, accel.x(), accel.y(), accel.z()));
        checkForGestureMatch(accel, myo);
    }

    protected void checkForGestureMatch(Vector3 accel, Myo myo) {
        accelX = accel.x();
        accelY = accel.y();
        accelZ = accel.z();
        if (accelX > THRESHOLD || accelY > THRESHOLD || accelZ > THRESHOLD) {
            Log.v("XXXXXXXXX", String.format("THRESHOLD REACHED AT, %f, %f, %f", accel.x(), accel.y(), accel.z()));
            //send gyroscope coordinates
        }
    }


}
