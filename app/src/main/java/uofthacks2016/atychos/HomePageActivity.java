package uofthacks2016.atychos;

import android.support.v7.app.ActionBarActivity;

import com.thalmic.myo.AbstractDeviceListener;
import com.thalmic.myo.DeviceListener;
import com.thalmic.myo.Hub;
import com.thalmic.myo.Myo;
import com.thalmic.myo.Vector3;

/**
 * Created by armanghassemi on 16-01-22.
 */

public class HomePageActivity extends ActionBarActivity {

    private DeviceListener mListener = new AbstractDeviceListener() {

        protected AccelerationDetector mAccelerationDetector = new AccelerationDetector();
        protected PositionDetector mPositionDetector = new PositionDetector();

        @Override
        public void onAccelerometerData(final Myo myo, long timestamp, Vector3 accel) {
            mAccelerationDetector.onData(timestamp, accel, myo);
        }

        @Override
        public void onGyroscopeData(final Myo myo, long timestamp, Vector3 gyro) {
            mPositionDetector.onData(timestamp, gyro, myo);
        }

    };



    void startMyo() {
        Hub.getInstance().addListener(mListener);
    }

}
