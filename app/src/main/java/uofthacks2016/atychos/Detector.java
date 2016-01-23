package uofthacks2016.atychos;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.thalmic.myo.Myo;
import com.thalmic.myo.Vector3;


abstract public class Detector  {

    protected double accelX = 0;
    protected double accelY = 0;
    protected  double accelZ = 0;

    protected double posX = 0;
    protected double posY = 0;
    protected double posZ = 0;


    public double getAccelX() {
        return accelX;
    }

    public double getAccelY() {
        return accelY;
    }

    public double getAccelZ() {
        return accelZ;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public double getPosZ() {
        return posZ;
    }

    protected abstract void checkForGestureMatch(Vector3 accel, Myo myo);

}
