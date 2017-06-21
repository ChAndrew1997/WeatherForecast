package com.chopik_andrew.weatherforecast.managers;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by Andrew on 20.06.2017.
 */

public class CurrentLocationManager {

    private static CurrentLocationManager mInstance;
    private Context mContext;
    private double mLatitude;
    private double mLongitude;
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;

    private CurrentLocationManager() {

    }

    public static synchronized CurrentLocationManager getInstance() {
        if (mInstance == null) {
            mInstance = new CurrentLocationManager();
        }
        return mInstance;
    }

    public void init(Context context) {
        mContext = context;

        mLocationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
    }

    public void findMyLocation(final FindLocationListener listener) {

        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                if (location != null) {
                    mLatitude = location.getLatitude();
                    mLongitude = location.getLongitude();

                    if (listener != null)
                        listener.success();
                }

                mLocationManager.removeUpdates(mLocationListener);
            }

            @Override
            public void onProviderDisabled(String provider) {
            }

            @Override
            public void onProviderEnabled(String provider) {
                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    if (listener != null) {
                        listener.failure();
                    }
                    return;
                }
                Location location = mLocationManager.getLastKnownLocation(provider);
                mLatitude = location.getLatitude();
                mLongitude = location.getLongitude();

                if (listener != null)
                    listener.success();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }
        };

        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (listener != null) {
                listener.failure();
            }
            return;
        }

        if (listener != null) {
            listener.start();
        }
        mLocationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 1000 * 10, 10, mLocationListener);
        mLocationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 1000 * 10, 10, mLocationListener);
    }

    public double getLatitude() {
        return mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public interface FindLocationListener {
        void start();

        void success();

        void failure();
    }
}
