package uofthacks2016.atychos;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.thalmic.myo.Myo;
import com.thalmic.myo.Vector3;


public class PositionDetector extends Detector {


    public void onData(long timestamp, Vector3 gyro, Myo myo) {
        Log.v("GYRO",
                String.format("%d, %f, %f, %f", timestamp, gyro.x(), gyro.y(), gyro.z()));
        checkForGestureMatch(gyro, myo);
    }

    protected void checkForGestureMatch(Vector3 gyro, Myo myo){
        posX = gyro.x();
        posY = gyro.y();
        posZ = gyro.z();
    }


}
